package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvaluationTest {

    @Test
    void defaultConstructor_shouldCreateObject_withPrimitiveDefaults() {
        Evaluation e = new Evaluation();

        assertNotNull(e);
        assertEquals(0, e.getMotivation());
        assertEquals(0, e.getEfficiency());
        assertEquals(0, e.getEffectiveness());
        assertEquals(0, e.getCompanyFacilities());
        assertEquals(0, e.getCompanyGuidance());
        assertNull(e.getEvaluationType());
    }

    @Test
    void settersAndGetters_shouldWork() {
        Evaluation e = new Evaluation();

        e.setEvaluationType(EvaluationType.COMPANY_EVALUATION);
        e.setMotivation(4);
        e.setEfficiency(5);
        e.setEffectiveness(3);
        e.setCompanyFacilities(4);
        e.setCompanyGuidance(5);

        assertEquals(EvaluationType.COMPANY_EVALUATION, e.getEvaluationType());
        assertEquals(4, e.getMotivation());
        assertEquals(5, e.getEfficiency());
        assertEquals(3, e.getEffectiveness());
        assertEquals(4, e.getCompanyFacilities());
        assertEquals(5, e.getCompanyGuidance());
    }

    @Test
    void evaluationType_canBeNull() {
        Evaluation e = new Evaluation();
        e.setEvaluationType(null);
        assertNull(e.getEvaluationType());
    }

    @Test
    void valuesOutsideRange_areStillStored_domainDoesNotValidate() {
        Evaluation e = new Evaluation();

        e.setMotivation(10);
        e.setEfficiency(-1);

        assertEquals(10, e.getMotivation());
        assertEquals(-1, e.getEfficiency());
    }
}
