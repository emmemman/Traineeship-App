package myy803.traineeship_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/student/dashboard")
    public String getStudentDashboard() {
        return "student/dashboard";
    }

    @RequestMapping("/student/profile")
    public String retrieveStudentProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentUsername = authentication.getName();
        // System.err.println("Logged user: " + studentUsername);

        Student student = studentService.findStudentByUsername(studentUsername);

        model.addAttribute("student", student);

        return "student/profile";
    }

    @RequestMapping("/student/save_profile")
    public String saveProfile(@ModelAttribute("student") Student student, Model theModel) {
        studentService.saveStudent(student);
        return "student/dashboard";
    }

    @RequestMapping("/student/logbook")
    public String showStudentLogbook(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentUsername = authentication.getName();

        Student student = studentService.findStudentByUsername(studentUsername);
        if (student == null) // Should not happen if service returns new Student, but logical check
            return "redirect:/student/profile";

        // έλεγχος αν έχει ανατεθειμένη πρακτική
        if (student.getAssignedTraineeship() == null) {
            model.addAttribute("no_assigned_traineeship", true);
            return "student/logbook";
        }

        if (student.getLogbook() == null)
            student.setLogbook("");

        model.addAttribute("student", student);

        return "student/logbook";
    }

    @RequestMapping("/student/save_logbook")
    public String saveStudentLogbook(@RequestParam("logbook") String logbook) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentUsername = authentication.getName();

        Student student = studentService.findStudentByUsername(studentUsername);
        // Controller logic: check if assigned
        if (student.getAssignedTraineeship() == null)
            return "redirect:/student/dashboard";

        studentService.saveStudentLogbook(studentUsername, logbook);

        return "redirect:/student/logbook";
    }
}
