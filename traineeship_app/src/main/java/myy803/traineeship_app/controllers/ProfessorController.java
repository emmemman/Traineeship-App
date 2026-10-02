package myy803.traineeship_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.service.ProfessorService;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @RequestMapping("/professor/dashboard")
    public String getProfessorDashboard() {
        return "professor/dashboard";
    }

    @RequestMapping("/professor/profile")
    public String retrieveProfessorProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Professor professor = professorService.findProfessorByUsername(username);

        model.addAttribute("professor", professor);

        return "professor/profile";
    }

    @RequestMapping("/professor/save_profile")
    public String saveProfile(@ModelAttribute("profile") Professor professor, Model theModel) {
        professorService.saveProfessor(professor);
        return "professor/dashboard";
    }

    @RequestMapping("/professor/list_traineeships")
    public String listTraineeships(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<TraineeshipPosition> traineeships = professorService.getTraineeshipsBySupervisor(username);

        model.addAttribute("traineeships", traineeships);

        return "professor/list_traineeships";
    }

    @RequestMapping("/professor/evaluate")
    public String showProfessorEvaluationForm(@RequestParam("position_id") Integer positionId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        TraineeshipPosition position = professorService.getTraineeshipPosition(positionId);
        if (position == null)
            return "redirect:/professor/list_traineeships";

        // supervisor check
        if (position.getSupervisor() == null || !position.getSupervisor().getUsername().equals(username))
            return "redirect:/professor/list_traineeships";

        if (!position.isAssigned())
            return "redirect:/professor/list_traineeships";

        // Find existing evaluation logic handles in view preparation?
        Evaluation existing = null;
        for (Evaluation e : position.getEvaluations()) {
            if (e.getEvaluationType() == EvaluationType.PROFESSOR_EVALUATION) {
                existing = e;
                break;
            }
        }

        Evaluation evaluation;
        if (existing != null) {
            evaluation = existing;
        } else {
            evaluation = new Evaluation();
            evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVALUATION);
            evaluation.setMotivation(3);
            evaluation.setEfficiency(3);
            evaluation.setEffectiveness(3);
            evaluation.setCompanyFacilities(3);
            evaluation.setCompanyGuidance(3);
        }

        model.addAttribute("position", position);
        model.addAttribute("evaluation", evaluation);

        return "professor/evaluation_form";
    }

    @RequestMapping("/professor/save_evaluation")
    public String saveProfessorEvaluation(
            @RequestParam("position_id") Integer positionId,
            @RequestParam("motivation") int motivation,
            @RequestParam("efficiency") int efficiency,
            @RequestParam("effectiveness") int effectiveness,
            @RequestParam("companyFacilities") int companyFacilities,
            @RequestParam("companyGuidance") int companyGuidance) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        TraineeshipPosition position = professorService.getTraineeshipPosition(positionId);
        if (position == null)
            return "redirect:/professor/list_traineeships";

        if (position.getSupervisor() == null || !position.getSupervisor().getUsername().equals(username))
            return "redirect:/professor/list_traineeships";

        if (!position.isAssigned())
            return "redirect:/professor/list_traineeships";

        // clamp 1..5
        motivation = clamp1to5(motivation);
        efficiency = clamp1to5(efficiency);
        effectiveness = clamp1to5(effectiveness);
        companyFacilities = clamp1to5(companyFacilities);
        companyGuidance = clamp1to5(companyGuidance);

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVALUATION);
        evaluation.setMotivation(motivation);
        evaluation.setEfficiency(efficiency);
        evaluation.setEffectiveness(effectiveness);
        evaluation.setCompanyFacilities(companyFacilities);
        evaluation.setCompanyGuidance(companyGuidance);

        professorService.saveEvaluation(positionId, evaluation);

        return "redirect:/professor/dashboard";
    }

    private int clamp1to5(int v) {
        if (v < 1)
            return 1;
        if (v > 5)
            return 5;
        return v;
    }
}
