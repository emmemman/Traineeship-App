package myy803.traineeship_app.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.traineeship_app.domain.TraineeshipPosition;

@Repository
public interface TraineeshipPositionsMapper extends JpaRepository<TraineeshipPosition, Integer> {
	List<TraineeshipPosition> findByTopicsContaining(String username);

	List<TraineeshipPosition> findByTopicsContainingAndIsAssignedFalse(String username);

	List<TraineeshipPosition> findBySkillsContainingAndIsAssignedFalse(String skills);

	// Με βάση preferred location (company location)
	List<TraineeshipPosition> findByCompany_CompanyLocationAndIsAssignedFalse(String companyLocation);

	List<TraineeshipPosition> findBySupervisor_Username(String username);

	// gia user story 9
	List<TraineeshipPosition> findByCompanyUsernameAndIsAssignedTrue(String companyUsername);

	/*
	 * =========================
	 * US20 – In-progress
	 * =========================
	 */

	// Assigned αλλά όχι ολοκληρωμένα (pass_fail = NULL)
	List<TraineeshipPosition> findByIsAssignedTrue();

	/*
	 * =========================
	 * US21 – Monitor / finalize
	 * =========================
	 */

	// (Το findById υπάρχει ήδη από JpaRepository)
}
