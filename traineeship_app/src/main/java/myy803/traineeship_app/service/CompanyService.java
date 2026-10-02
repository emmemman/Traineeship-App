package myy803.traineeship_app.service;

import java.util.List;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface CompanyService {
    Company findCompanyByUsername(String username);

    void saveCompany(Company company);

    void addPosition(TraineeshipPosition position, String companyUsername);

    List<TraineeshipPosition> getAvailablePositions(String companyUsername);

    List<TraineeshipPosition> getAssignedPositions(String companyUsername);

    void deletePosition(Integer positionId, String companyUsername);

    TraineeshipPosition getTraineeshipPosition(Integer positionId);

    void saveEvaluation(Integer positionId, Evaluation evaluation);
}
