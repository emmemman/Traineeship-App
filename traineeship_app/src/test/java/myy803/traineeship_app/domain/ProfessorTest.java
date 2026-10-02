package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ProfessorTest {

    @Test
    void testMatch() {
        Professor professor = new Professor("prof1");
        professor.setInterests("Java, Spring, AI");

        String[] topicsMatch = { "Spring", "Hibernate" };
        assertTrue(professor.match(topicsMatch), "Should match because 'Spring' is in interests");

        String[] topicsNoMatch = { "C++", "Python" };
        assertFalse(professor.match(topicsNoMatch), "Should not match");
    }

    @Test
    void testCompareLoad() {
        Professor p1 = new Professor("p1");
        p1.setSupervisedPositions(new ArrayList<>());
        // 0 positions

        Professor p2 = new Professor("p2");
        p2.setSupervisedPositions(new ArrayList<>());
        p2.addPosition(new TraineeshipPosition());
        // 1 position

        // p1 has 0, p2 has 1. p1 < p2.
        // compareLoad returns -1 if candidate (param) < current (this)?
        // Let's check logic:
        // if(candidateSupervisor.supervisedPositions.size() <
        // supervisedPositions.size()) return -1;

        // p2.compareLoad(p1): candidate=p1(0), this=p2(1). 0 < 1 => returns -1.
        assertEquals(-1, p2.compareLoad(p1));

        // p1.compareLoad(p2): candidate=p2(1), this=p1(0). 1 < 0 is false. returns 0.
        assertEquals(0, p1.compareLoad(p2));
    }

}
