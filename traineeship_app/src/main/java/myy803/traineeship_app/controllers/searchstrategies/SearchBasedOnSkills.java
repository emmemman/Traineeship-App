package myy803.traineeship_app.controllers.searchstrategies;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Component
public class SearchBasedOnSkills extends AbstractPositionsSearchStrategy {

    @Autowired
    private TraineeshipPositionsMapper positionsMapper;

    @Override
    protected void doSearch(Student applicant, Set<TraineeshipPosition> matchingPositionsSet) {
        if (applicant == null) return;

        String raw = applicant.getSkills();
        if (raw == null || raw.trim().isEmpty()) return;

        String[] skills = raw.split("[,\\s\\.]+");   // + ώστε να μη βγάζει κενά tokens

        for (String skill : skills) {
            if (skill == null) continue;

            String token = skill.trim();
            if (token.isEmpty()) continue;

            List<TraineeshipPosition> positions =
                    positionsMapper.findBySkillsContainingAndIsAssignedFalse(token);

            matchingPositionsSet.addAll(positions);
        }
    }
}
