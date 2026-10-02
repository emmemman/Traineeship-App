package myy803.traineeship_app.controllers.searchstrategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionsSearchFactory {
	@Autowired
	SearchBasedOnLocation searchBasedOnLocation;

	@Autowired
	SearchBasedOnInterests searchBasedOnInterests;

	@Autowired
	SearchBasedOnSkills searchBasedOnSkills;

	public PositionsSearchStrategy create(String strategy) {
		switch (strategy) {
			case "location":
				return searchBasedOnLocation;
			case "interests":
				return searchBasedOnInterests;
			case "skills":
				return searchBasedOnSkills;
			default:
				return searchBasedOnLocation;
		}
	}
}
