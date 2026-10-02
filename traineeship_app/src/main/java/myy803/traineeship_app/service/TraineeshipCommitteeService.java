package myy803.traineeship_app.service;

import java.util.List;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface TraineeshipCommitteeService {
    List<Student> findTraineeshipApplications();

    List<TraineeshipPosition> searchAvailablePositions(String studentUsername, String strategyName);

    void assignPosition(Integer positionId, String studentUsername);

    List<TraineeshipPosition> findAssignedTraineeships();

    TraineeshipPosition getTraineeshipPosition(Integer positionId);

    void finalizeTraineeship(Integer positionId, boolean passFail);
}
