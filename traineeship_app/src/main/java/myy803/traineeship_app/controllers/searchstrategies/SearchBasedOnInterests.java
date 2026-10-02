package myy803.traineeship_app.controllers.searchstrategies;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Component
public class SearchBasedOnInterests extends AbstractPositionsSearchStrategy {
	@Autowired
	private TraineeshipPositionsMapper positionsMapper;

	@Override
	protected void doSearch(Student applicant, Set<TraineeshipPosition> matchingPositionsSet) {
		String[] interests = applicant.getInterests().split("[,\\s+\\.]");
		for (int i = 0; i < interests.length; i++) {
			List<TraineeshipPosition> positions = positionsMapper.findByTopicsContainingAndIsAssignedFalse(
					interests[i]);
			matchingPositionsSet.addAll(positions);
		}
	}

}
