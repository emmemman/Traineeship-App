package myy803.traineeship_app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchFactory;
import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchStrategy;
import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.StudentMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@ExtendWith(MockitoExtension.class)
class TraineeshipCommitteeServiceTest {

    @Mock
    private PositionsSearchFactory searchFactory;

    @Mock
    private PositionsSearchStrategy searchStrategy;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private TraineeshipPositionsMapper positionsMapper;

    @InjectMocks
    private TraineeshipCommitteeServiceImpl committeeService;

    @Test
    void testSearchAvailablePositions() {
        when(searchFactory.create("interests")).thenReturn(searchStrategy);
        when(searchStrategy.search("student1")).thenReturn(List.of(new TraineeshipPosition()));

        List<TraineeshipPosition> results = committeeService.searchAvailablePositions("student1", "interests");
        assertEquals(1, results.size());
    }

    @Test
    void testAssignPosition() {
        Student student = new Student("student1");
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(1);

        when(studentMapper.findByUsername("student1")).thenReturn(student);
        when(positionsMapper.findById(1)).thenReturn(Optional.of(position));

        committeeService.assignPosition(1, "student1");

        assertTrue(position.isAssigned());
        assertEquals(student, position.getStudent());
        assertEquals(position, student.getAssignedTraineeship());
        assertFalse(student.isLookingForTraineeship());

        verify(positionsMapper, times(1)).save(position);
    }

}
