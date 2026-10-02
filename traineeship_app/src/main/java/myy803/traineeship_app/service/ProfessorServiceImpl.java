package myy803.traineeship_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.ProfessorMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private TraineeshipPositionsMapper positionsMapper;

    @Override
    public Professor findProfessorByUsername(String username) {
        Professor professor = professorMapper.findByUsername(username);
        if (professor == null) {
            professor = new Professor(username);
        }
        return professor;
    }

    @Override
    public void saveProfessor(Professor professor) {
        professorMapper.save(professor);
    }

    @Override
    public List<TraineeshipPosition> getTraineeshipsBySupervisor(String username) {
        return positionsMapper.findBySupervisor_Username(username);
    }

    @Override
    public TraineeshipPosition getTraineeshipPosition(Integer positionId) {
        return positionsMapper.findById(positionId).orElse(null);
    }

    @Override
    public void saveEvaluation(Integer positionId, Evaluation evaluation) {
        TraineeshipPosition position = getTraineeshipPosition(positionId);
        if (position != null) {
            // Logic to find existing evaluation and update, or add new
            Evaluation existing = null;
            for (Evaluation e : position.getEvaluations()) {
                if (e.getEvaluationType() == EvaluationType.PROFESSOR_EVALUATION) {
                    existing = e;
                    break;
                }
            }

            if (existing != null) {
                existing.setMotivation(evaluation.getMotivation());
                existing.setEfficiency(evaluation.getEfficiency());
                existing.setEffectiveness(evaluation.getEffectiveness());
                existing.setCompanyFacilities(evaluation.getCompanyFacilities());
                existing.setCompanyGuidance(evaluation.getCompanyGuidance());
            } else {
                position.getEvaluations().add(evaluation);
            }
            positionsMapper.save(position);
        }
    }
}
