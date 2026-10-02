package myy803.traineeship_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.CompanyMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private TraineeshipPositionsMapper positionsMapper;

    @Override
    public Company findCompanyByUsername(String username) {
        Company company = companyMapper.findByUsername(username);
        if (company == null) {
            company = new Company(username);
        }
        return company;
    }

    @Override
    public void saveCompany(Company company) {
        companyMapper.save(company);
    }

    @Override
    public void addPosition(TraineeshipPosition position, String companyUsername) {
        Company company = findCompanyByUsername(companyUsername);
        position.setCompany(company);
        company.addPosition(position);
        companyMapper.save(company);
    }

    @Override
    public List<TraineeshipPosition> getAvailablePositions(String companyUsername) {
        Company company = findCompanyByUsername(companyUsername);
        return company.getAvailablePositions();
    }

    @Override
    public List<TraineeshipPosition> getAssignedPositions(String companyUsername) {
        Company company = findCompanyByUsername(companyUsername);
        if (company == null)
            return List.of();
        return company.getAssignedPositions();
    }

    @Override
    public void deletePosition(Integer positionId, String companyUsername) {
        TraineeshipPosition position = positionsMapper.findById(positionId).orElse(null);
        if (position != null && position.getCompany() != null
                && position.getCompany().getUsername().equals(companyUsername)) {
            positionsMapper.deleteById(positionId);
        }
    }

    @Override
    public TraineeshipPosition getTraineeshipPosition(Integer positionId) {
        return positionsMapper.findById(positionId).orElse(null);
    }

    @Override
    public void saveEvaluation(Integer positionId, Evaluation evaluation) {
        TraineeshipPosition position = getTraineeshipPosition(positionId);
        if (position != null) {
            Evaluation existing = null;
            for (Evaluation e : position.getEvaluations()) {
                if (e.getEvaluationType() == EvaluationType.COMPANY_EVALUATION) {
                    existing = e;
                    break;
                }
            }

            if (existing != null) {
                existing.setMotivation(evaluation.getMotivation());
                existing.setEfficiency(evaluation.getEfficiency());
                existing.setEffectiveness(evaluation.getEffectiveness());
            } else {
                // Ensure Type is set
                if (evaluation.getEvaluationType() == null)
                    evaluation.setEvaluationType(EvaluationType.COMPANY_EVALUATION);
                position.getEvaluations().add(evaluation);
            }

            positionsMapper.save(position);
        }
    }

}
