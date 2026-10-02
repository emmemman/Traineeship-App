package myy803.traineeship_app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.ProfessorMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

    @Mock
    private ProfessorMapper professorMapper;

    @Mock
    private TraineeshipPositionsMapper positionsMapper;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    @Test
    void testSaveEvaluation_New() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setEvaluations(new ArrayList<>());
        when(positionsMapper.findById(1)).thenReturn(Optional.of(position));

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVALUATION);

        professorService.saveEvaluation(1, evaluation);

        assertEquals(1, position.getEvaluations().size());
        verify(positionsMapper, times(1)).save(position);
    }

    @Test
    void testSaveEvaluation_Update() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setEvaluations(new ArrayList<>());

        Evaluation existing = new Evaluation();
        existing.setEvaluationType(EvaluationType.PROFESSOR_EVALUATION);
        existing.setMotivation(1);
        position.getEvaluations().add(existing);

        when(positionsMapper.findById(1)).thenReturn(Optional.of(position));

        Evaluation update = new Evaluation();
        update.setEvaluationType(EvaluationType.PROFESSOR_EVALUATION);
        update.setMotivation(5);

        professorService.saveEvaluation(1, update);

        assertEquals(1, position.getEvaluations().size());
        assertEquals(5, position.getEvaluations().get(0).getMotivation());
        verify(positionsMapper, times(1)).save(position);
    }

}
