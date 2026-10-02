package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    void getValue_shouldReturnExpectedStrings() {
        assertEquals("Student", Role.STUDENT.getValue());
        assertEquals("Professor", Role.PROFESSOR.getValue());
        assertEquals("Company", Role.COMPANY.getValue());
        assertEquals("Committee", Role.COMMITTEE.getValue());
    }

    @Test
    void values_shouldContainAllRoles() {
        Role[] all = Role.values();
        assertEquals(4, all.length);
    }
}
