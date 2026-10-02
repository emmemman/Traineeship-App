package myy803.traineeship_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchFactory;
import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchStrategy;
import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.StudentMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
@Transactional
public class TraineeshipCommitteeServiceImpl implements TraineeshipCommitteeService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TraineeshipPositionsMapper positionsMapper;

    @Autowired
    private PositionsSearchFactory positionsSearchFactory;

    @Override
    public List<Student> findTraineeshipApplications() {
        return studentMapper.findByLookingForTraineeshipTrue();
    }

    @Override
    public List<TraineeshipPosition> searchAvailablePositions(String studentUsername, String strategyName) {
        PositionsSearchStrategy searchStrategy = positionsSearchFactory.create(strategyName);
        return searchStrategy.search(studentUsername);
    }

    @Override
    public void assignPosition(Integer positionId, String studentUsername) {
        Student student = studentMapper.findByUsername(studentUsername);
        TraineeshipPosition position = positionsMapper.findById(positionId).orElse(null);

        if (student != null && position != null) {
            position.setAssigned(true);
            position.setStudent(student);

            student.setAssignedTraineeship(position);
            student.setLookingForTraineeship(false);

            positionsMapper.save(position);

        }
    }

    @Override
    public List<TraineeshipPosition> findAssignedTraineeships() {
        return positionsMapper.findByIsAssignedTrue();
    }

    @Override
    public TraineeshipPosition getTraineeshipPosition(Integer positionId) {
        return positionsMapper.findById(positionId).orElse(null);
    }

    @Override
    public void finalizeTraineeship(Integer positionId, boolean passFail) {
        TraineeshipPosition position = positionsMapper.findById(positionId).orElse(null);
        if (position != null) {
            position.setPassFailGrade(passFail);
            positionsMapper.save(position);
        }
    }

}
