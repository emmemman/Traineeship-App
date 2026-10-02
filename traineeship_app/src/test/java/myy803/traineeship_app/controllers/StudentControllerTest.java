package myy803.traineeship_app.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    @WithMockUser(username = "student1")
    void testRetrieveStudentProfile() throws Exception {
        Student student = new Student("student1");
        when(studentService.findStudentByUsername("student1")).thenReturn(student);

        mockMvc.perform(get("/student/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/profile"))
                .andExpect(model().attributeExists("student"));
    }

    // Example valid post
    @Test
    @WithMockUser(username = "student1")
    void testSaveProfile() throws Exception {
        mockMvc.perform(post("/student/save_profile")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("username", "student1")
                .param("studentName", "Test Name"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/dashboard"));

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }
}
