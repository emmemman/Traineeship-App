package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void defaultConstructor_shouldCreateObject() {
        Student s = new Student();
        assertNotNull(s);
    }

    @Test
    void usernameConstructor_shouldSetUsername_andDefaultLookingForTraineeshipTrue() {
        Student s = new Student("student1");

        assertEquals("student1", s.getUsername());
        assertTrue(s.isLookingForTraineeship());
    }

    @Test
    void fullConstructor_shouldSetAllFields() {
        TraineeshipPosition position = new TraineeshipPosition();

        Student s = new Student(
                "u1",
                "John Doe",
                "AM123",
                8.75,
                "Heraklion",
                "Databases",
                "Java, SQL",
                "Week 1 log",
                true,
                position
        );

        assertEquals("u1", s.getUsername());
        assertEquals("John Doe", s.getStudentName());
        assertEquals("AM123", s.getAM());
        assertEquals(8.75, s.getAvgGrade(), 0.000001);
        assertEquals("Heraklion", s.getPreferredLocation());
        assertEquals("Databases", s.getInterests());
        assertEquals("Java, SQL", s.getSkills());
        assertEquals("Week 1 log", s.getLogbook());
        assertTrue(s.isLookingForTraineeship());
        assertSame(position, s.getAssignedTraineeship());
    }

    @Test
    void settersAndGetters_shouldWork() {
        Student s = new Student();

        s.setUsername("u2");
        s.setStudentName("Name2");
        s.setAM("AM999");
        s.setAvgGrade(6.1);
        s.setPreferredLocation("Athens");
        s.setInterests("AI");
        s.setSkills("Python");
        s.setLogbook("Log");
        s.setLookingForTraineeship(false);

        TraineeshipPosition pos = new TraineeshipPosition();
        s.setAssignedTraineeship(pos);

        assertEquals("u2", s.getUsername());
        assertEquals("Name2", s.getStudentName());
        assertEquals("AM999", s.getAM());
        assertEquals(6.1, s.getAvgGrade(), 0.000001);
        assertEquals("Athens", s.getPreferredLocation());
        assertEquals("AI", s.getInterests());
        assertEquals("Python", s.getSkills());
        assertEquals("Log", s.getLogbook());
        assertFalse(s.isLookingForTraineeship());
        assertSame(pos, s.getAssignedTraineeship());
    }

    @Test
    void assignedTraineeship_canBeNull() {
        Student s = new Student("u3");
        s.setAssignedTraineeship(null);
        assertNull(s.getAssignedTraineeship());
    }

    @Test
    void logbook_canBeNull() {
        Student s = new Student("u4");
        s.setLogbook(null);
        assertNull(s.getLogbook());
    }
}
