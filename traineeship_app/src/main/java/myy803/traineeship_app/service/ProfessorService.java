package myy803.traineeship_app.service;

import java.util.List;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface ProfessorService {
    Professor findProfessorByUsername(String username);

    void saveProfessor(Professor professor);

    List<TraineeshipPosition> getTraineeshipsBySupervisor(String username);

    TraineeshipPosition getTraineeshipPosition(Integer positionId);

    void saveEvaluation(Integer positionId, Evaluation evaluation);
}
