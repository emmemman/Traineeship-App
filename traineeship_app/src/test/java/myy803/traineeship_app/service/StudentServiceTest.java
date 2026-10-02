package myy803.traineeship_app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.mappers.StudentMapper;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student("testUser");
    }

    @Test
    void testFindStudentByUsername_Found() {
        when(studentMapper.findByUsername("testUser")).thenReturn(testStudent);

        Student result = studentService.findStudentByUsername("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void testFindStudentByUsername_NotFound_CreatesNew() {
        when(studentMapper.findByUsername("newUser")).thenReturn(null);

        Student result = studentService.findStudentByUsername("newUser");
        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        // Should return a new student object
    }

    @Test
    void testSaveStudent() {
        studentService.saveStudent(testStudent);
        assertTrue(testStudent.isLookingForTraineeship());
        verify(studentMapper, times(1)).save(testStudent);
    }

    @Test
    void testSaveStudentLogbook() {
        when(studentMapper.findByUsername("testUser")).thenReturn(testStudent);

        studentService.saveStudentLogbook("testUser", "My Log");

        assertEquals("My Log", testStudent.getLogbook());
        verify(studentMapper, times(1)).save(testStudent);
    }

}
