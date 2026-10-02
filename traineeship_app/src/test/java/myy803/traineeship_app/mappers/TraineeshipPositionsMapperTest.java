package myy803.traineeship_app.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import myy803.traineeship_app.domain.TraineeshipPosition;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TraineeshipPositionsMapperTest {

    @Autowired
    private TraineeshipPositionsMapper positionsMapper;

    @Test
    void testFindBySkillsContainingAndIsAssignedFalse() {
        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setSkills("Java, Spring");
        p1.setAssigned(false);
        positionsMapper.save(p1);

        TraineeshipPosition p2 = new TraineeshipPosition();
        p2.setSkills("Python, Django");
        p2.setAssigned(false);
        positionsMapper.save(p2);

        TraineeshipPosition p3 = new TraineeshipPosition();
        p3.setSkills("Java, JavaFX");
        p3.setAssigned(true); // Assigned, should be ignored
        positionsMapper.save(p3);

        List<TraineeshipPosition> results = positionsMapper.findBySkillsContainingAndIsAssignedFalse("Java");

        assertEquals(1, results.size(), "Should find 1 assigned=false position with Java skills");
        assertEquals("Java, Spring", results.get(0).getSkills());
    }
}
