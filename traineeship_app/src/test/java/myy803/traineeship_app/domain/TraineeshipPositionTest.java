package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TraineeshipPositionTest {

    @Test
    void defaultConstructor_shouldInitializeEvaluations() {
        TraineeshipPosition p = new TraineeshipPosition();
        assertNotNull(p.getEvaluations());
        assertTrue(p.getEvaluations().isEmpty());
    }

    @Test
    void settersAndGetters_shouldWork() {
        TraineeshipPosition p = new TraineeshipPosition();

        LocalDate from = LocalDate.of(2025, 3, 1);
        LocalDate to = LocalDate.of(2025, 6, 30);

        p.setId(10);
        p.setTitle("Backend Intern");
        p.setDescription("Spring Boot internship");
        p.setFromDate(from);
        p.setToDate(to);
        p.setTopics("Backend, Databases");
        p.setSkills("Java, Spring");
        p.setAssigned(true);
        p.setStudentLogbook("Week 1: ...");
        p.setPassFailGrade(Boolean.TRUE);

        assertEquals(10, p.getId());
        assertEquals("Backend Intern", p.getTitle());
        assertEquals("Spring Boot internship", p.getDescription());
        assertEquals(from, p.getFromDate());
        assertEquals(to, p.getToDate());
        assertEquals("Backend, Databases", p.getTopics());
        assertEquals("Java, Spring", p.getSkills());
        assertTrue(p.isAssigned());
        assertEquals("Week 1: ...", p.getStudentLogbook());
        assertTrue(p.isPassFailGrade());
    }

    @Test
    void setPassFailGrade_shouldAcceptNull() {
        TraineeshipPosition p = new TraineeshipPosition();
        p.setPassFailGrade(null);
        assertNull(p.isPassFailGrade());
    }

    @Test
    void relations_shouldBeSettable() {
        TraineeshipPosition p = new TraineeshipPosition();

        Student s = new Student();
        Professor prof = new Professor();
        Company c = new Company("company1");

        p.setStudent(s);
        p.setSupervisor(prof);
        p.setCompany(c);

        assertSame(s, p.getStudent());
        assertSame(prof, p.getSupervisor());
        assertSame(c, p.getCompany());
    }

    @Test
    void evaluations_setterShouldReplaceList() {
        TraineeshipPosition p = new TraineeshipPosition();

        List<Evaluation> evals = new ArrayList<>();
        evals.add(new Evaluation());

        p.setEvaluations(evals);

        assertSame(evals, p.getEvaluations());
        assertEquals(1, p.getEvaluations().size());
    }

    @Test
    void fullConstructor_shouldSetAllFields() {
        Student s = new Student();
        Professor prof = new Professor();
        Company c = new Company("company1");

        List<Evaluation> evals = new ArrayList<>();
        evals.add(new Evaluation());

        LocalDate from = LocalDate.of(2025, 1, 10);
        LocalDate to = LocalDate.of(2025, 2, 10);

        TraineeshipPosition p = new TraineeshipPosition(
                7,
                "Title",
                "Desc",
                from,
                to,
                "Topics",
                "Skills",
                true,
                "Logbook",
                Boolean.FALSE,
                s,
                prof,
                c,
                evals
        );

        assertEquals(7, p.getId());
        assertEquals("Title", p.getTitle());
        assertEquals("Desc", p.getDescription());
        assertEquals(from, p.getFromDate());
        assertEquals(to, p.getToDate());
        assertEquals("Topics", p.getTopics());
        assertEquals("Skills", p.getSkills());
        assertTrue(p.isAssigned());
        assertEquals("Logbook", p.getStudentLogbook());
        assertEquals(Boolean.FALSE, p.isPassFailGrade());
        assertSame(s, p.getStudent());
        assertSame(prof, p.getSupervisor());
        assertSame(c, p.getCompany());
        assertSame(evals, p.getEvaluations());
    }
}
