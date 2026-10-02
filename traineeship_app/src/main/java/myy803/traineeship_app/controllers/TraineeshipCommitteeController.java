package myy803.traineeship_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.service.TraineeshipCommitteeService;

@Controller
public class TraineeshipCommitteeController {

    @Autowired
    private TraineeshipCommitteeService committeeService;

    @RequestMapping("/committee/dashboard")
    public String getCommitteeDashboard() {
        return "committee/dashboard";
    }

    @RequestMapping("/committee/list_traineeship_applications")
    public String listTraineeshipApplications(Model model) {
        List<Student> traineeshipApplications = committeeService.findTraineeshipApplications();
        model.addAttribute("traineeship_applications", traineeshipApplications);
        return "committee/traineeship_applications";
    }

    @RequestMapping("/committee/find_positions")
    public String findPositions(
            @RequestParam("selected_student_id") String studentUsername,
            @RequestParam("strategy") String strategy, Model model) {

        List<TraineeshipPosition> positions = committeeService.searchAvailablePositions(studentUsername, strategy);

        model.addAttribute("positions", positions);
        model.addAttribute("student_username", studentUsername);

        return "committee/available_positions";
    }

    @RequestMapping("/committee/assign_position")
    public String assignPosition(
            @RequestParam("selected_position_id") Integer positionId,
            @RequestParam("applicant_username") String studentUsername,
            Model model) {

        committeeService.assignPosition(positionId, studentUsername);

        return "/committee/dashboard";
    }

    @RequestMapping("/committee/list_assigned_traineeships")
    public String listInProgressTraineeships(Model model) {
        List<TraineeshipPosition> inProgress = committeeService.findAssignedTraineeships();
        model.addAttribute("positions", inProgress);
        return "committee/list_assigned_traineeships";
    }

    @RequestMapping("/committee/monitor_traineeship")
    public String monitorTraineeship(
            @RequestParam("position_id") Integer positionId,
            Model model) {

        TraineeshipPosition position =
                committeeService.getTraineeshipPosition(positionId);

        if (position == null) {
            return "redirect:/committee/list_assigned_traineeships";
        }

        model.addAttribute("position", position);
        model.addAttribute("evaluations", position.getEvaluations());

        return "committee/monitor_traineeship";
    }


    @RequestMapping("/committee/finalize_traineeship")
    public String finalizeTraineeship(
            @RequestParam("position_id") Integer positionId,
            @RequestParam("passFail") boolean passFail,
            Model model) {

        committeeService.finalizeTraineeship(positionId, passFail);

        return "/committee/list_assigned_traineeships";
    }
}
