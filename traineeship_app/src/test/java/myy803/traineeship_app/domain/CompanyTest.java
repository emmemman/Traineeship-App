package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    void defaultConstructor_shouldCreateObject() {
        Company c = new Company();
        assertNotNull(c);
    }

    @Test
    void usernameConstructor_shouldSetUsername() {
        Company c = new Company("company1");
        assertEquals("company1", c.getUsername());
    }

    @Test
    void fullConstructor_shouldSetAllFields() {
        List<TraineeshipPosition> positions = new ArrayList<>();
        Company c = new Company("u1", "ACME", "Heraklion", positions);

        assertEquals("u1", c.getUsername());
        assertEquals("ACME", c.getCompanyName());
        assertEquals("Heraklion", c.getCompanyLocation());
        assertSame(positions, c.getPositions()); // ίδιο reference
    }

    @Test
    void settersAndGetters_shouldWork() {
        Company c = new Company();

        c.setUsername("u2");
        c.setCompanyName("Name2");
        c.setCompanyLocation("Athens");

        List<TraineeshipPosition> positions = new ArrayList<>();
        c.setPositions(positions);

        assertEquals("u2", c.getUsername());
        assertEquals("Name2", c.getCompanyName());
        assertEquals("Athens", c.getCompanyLocation());
        assertSame(positions, c.getPositions());
    }

    @Test
    void addPosition_shouldAddToList() {
        Company c = new Company("u3");
        c.setPositions(new ArrayList<>());

        TraineeshipPosition p = new TraineeshipPosition();
        c.addPosition(p);

        assertEquals(1, c.getPositions().size());
        assertSame(p, c.getPositions().get(0));
    }

    @Test
    void getAvailablePositions_shouldReturnOnlyNotAssigned() {
        Company c = new Company("u4");
        c.setPositions(new ArrayList<>());

        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setAssigned(false);

        TraineeshipPosition p2 = new TraineeshipPosition();
        p2.setAssigned(true);

        TraineeshipPosition p3 = new TraineeshipPosition();
        p3.setAssigned(false);

        c.addPosition(p1);
        c.addPosition(p2);
        c.addPosition(p3);

        List<TraineeshipPosition> available = c.getAvailablePositions();

        assertEquals(2, available.size());
        assertTrue(available.contains(p1));
        assertTrue(available.contains(p3));
        assertFalse(available.contains(p2));
    }

    @Test
    void getAssignedPositions_shouldReturnOnlyAssigned() {
        Company c = new Company("u5");
        c.setPositions(new ArrayList<>());

        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setAssigned(false);

        TraineeshipPosition p2 = new TraineeshipPosition();
        p2.setAssigned(true);

        c.addPosition(p1);
        c.addPosition(p2);

        List<TraineeshipPosition> assigned = c.getAssignedPositions();

        assertEquals(1, assigned.size());
        assertTrue(assigned.contains(p2));
        assertFalse(assigned.contains(p1));
    }

    @Test
    void getAvailablePositions_whenNoPositions_shouldReturnEmptyList() {
        Company c = new Company("u6");
        c.setPositions(new ArrayList<>());

        List<TraineeshipPosition> available = c.getAvailablePositions();
        assertNotNull(available);
        assertTrue(available.isEmpty());
    }

    @Test
    void getAssignedPositions_whenNoPositions_shouldReturnEmptyList() {
        Company c = new Company("u7");
        c.setPositions(new ArrayList<>());

        List<TraineeshipPosition> assigned = c.getAssignedPositions();
        assertNotNull(assigned);
        assertTrue(assigned.isEmpty());
    }
}
