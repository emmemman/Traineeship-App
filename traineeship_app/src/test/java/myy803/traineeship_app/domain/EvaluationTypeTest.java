package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvaluationTypeTest {

    @Test
    void getValue_shouldReturnExpectedStrings() {
        assertEquals("Professor Evaluation", EvaluationType.PROFESSOR_EVALUATION.getValue());
        assertEquals("Company", EvaluationType.COMPANY_EVALUATION.getValue());
    }

    @Test
    void values_shouldContainBothEnums() {
        EvaluationType[] all = EvaluationType.values();
        assertEquals(2, all.length);
        assertTrue(java.util.Arrays.asList(all).contains(EvaluationType.PROFESSOR_EVALUATION));
        assertTrue(java.util.Arrays.asList(all).contains(EvaluationType.COMPANY_EVALUATION));
    }
}
