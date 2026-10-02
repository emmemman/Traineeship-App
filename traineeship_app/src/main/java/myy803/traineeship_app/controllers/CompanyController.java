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

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.service.CompanyService;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/company/dashboard")
    public String getCompanyDashboard() {
        return "company/dashboard";
    }

    @RequestMapping("/company/profile")
    public String retrieveCompanyProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // System.err.println("Logged user: " + username);

        Company company = companyService.findCompanyByUsername(username);

        model.addAttribute("company", company);

        return "company/profile";
    }

    @RequestMapping("/company/save_profile")
    public String saveProfile(@ModelAttribute("profile") Company company, Model theModel) {
        companyService.saveCompany(company);
        return "company/dashboard";
    }

    @RequestMapping("/company/list_available_positions")
    public String listAvailablePositions(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<TraineeshipPosition> positions = companyService.getAvailablePositions(username);

        model.addAttribute("positions", positions);

        return "company/available_positions";
    }

    @RequestMapping("/company/show_position_form")
    String showPositionForm(Model model) {
        TraineeshipPosition position = new TraineeshipPosition();
        model.addAttribute("position", position);
        return "company/position";
    }

    @RequestMapping("/company/save_position")
    public String savePosition(@ModelAttribute("position") TraineeshipPosition position, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        companyService.addPosition(position, username);

        return "redirect:/company/dashboard";
    }

    // US 9
    @RequestMapping("/company/list_assigned_positions")
    public String listAssignedPositions(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<TraineeshipPosition> positions = companyService.getAssignedPositions(username);

        model.addAttribute("positions", positions);

        return "company/assigned_positions";
    }

    // US 11
    @RequestMapping("/company/delete_position")
    public String deletePosition(@RequestParam("position_id") Integer positionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        companyService.deletePosition(positionId, username);

        return "/company/available_positions";
    }

    // US 12
    @RequestMapping("/company/evaluate")
    public String showCompanyEvaluationForm(@RequestParam("position_id") Integer positionId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        TraineeshipPosition position = companyService.getTraineeshipPosition(positionId);
        if (position == null)
            return "redirect:/company/list_assigned_positions";

        if (position.getCompany() == null || !position.getCompany().getUsername().equals(username))
            return "redirect:/company/list_assigned_positions";

        if (!position.isAssigned())
            return "redirect:/company/list_available_positions";

        Evaluation evaluation = new Evaluation();
        // Check existing?
        // Controller logic in original code created new evaluation unless editing?
        // Original code:
        /*
         * Evaluation evaluation = new Evaluation();
         * evaluation.setEvaluationType(EvaluationType.COMPANY_EVALUATION);
         * model.addAttribute("position", position);
         * model.addAttribute("evaluation", evaluation);
         */
        // It seems it always passed a new evaluation object to the form, but let's see
        // if save handles existing.

        evaluation.setEvaluationType(EvaluationType.COMPANY_EVALUATION);

        model.addAttribute("position", position);
        model.addAttribute("evaluation", evaluation);

        return "company/evaluation_form";
    }

    @RequestMapping("/company/save_evaluation")
    public String saveCompanyEvaluation(
            @RequestParam("position_id") Integer positionId,
            @RequestParam("motivation") int motivation,
            @RequestParam("efficiency") int efficiency,
            @RequestParam("effectiveness") int effectiveness) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        TraineeshipPosition position = companyService.getTraineeshipPosition(positionId);
        if (position == null)
            return "/company/assigned_positions";

        if (position.getCompany() == null || !position.getCompany().getUsername().equals(username))
            return "/company/assigned_positions";

        if (!position.isAssigned())
            return "/company/available_positions";

        // clamp 1..5
        motivation = clamp1to5(motivation);
        efficiency = clamp1to5(efficiency);
        effectiveness = clamp1to5(effectiveness);

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.COMPANY_EVALUATION);
        evaluation.setMotivation(motivation);
        evaluation.setEfficiency(efficiency);
        evaluation.setEffectiveness(effectiveness);

        companyService.saveEvaluation(positionId, evaluation);

        return "/company/dashboard";
    }

    private int clamp1to5(int v) {
        if (v < 1)
            return 1;
        if (v > 5)
            return 5;
        return v;
    }

}
