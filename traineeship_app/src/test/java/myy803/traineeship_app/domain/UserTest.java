package myy803.traineeship_app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserTest {

    @Test
    void defaultConstructor_shouldCreateObject() {
        User u = new User();
        assertNotNull(u);
    }

    @Test
    void settersAndGetters_shouldWork() {
        User u = new User();

        u.setUsername("user1");
        u.setPassword("enc_pass");
        u.setRole(Role.STUDENT);

        assertEquals("user1", u.getUsername());
        assertEquals("enc_pass", u.getPassword());
        assertEquals(Role.STUDENT, u.getRole());
    }

    @Test
    void getAuthorities_shouldReturnSingleAuthorityWithRoleName() {
        User u = new User();
        u.setRole(Role.COMPANY);

        Collection<? extends GrantedAuthority> authorities = u.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("COMPANY", authorities.iterator().next().getAuthority());
    }

    @Test
    void userDetailsFlags_shouldAlwaysBeTrue() {
        User u = new User();

        assertTrue(u.isAccountNonExpired());
        assertTrue(u.isAccountNonLocked());
        assertTrue(u.isCredentialsNonExpired());
        assertTrue(u.isEnabled());
    }
}
