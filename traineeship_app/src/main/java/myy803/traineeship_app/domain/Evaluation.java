package myy803.traineeship_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="evaluations")
public class Evaluation {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="evaluation_type")
    private EvaluationType evaluationType;
	
	@Column(name="motivation")
	int motivation;
	
	@Column(name="efficiency")
	Integer efficiency;
	
	@Column(name="effectiveness")
	Integer effectiveness;

	@Column(name="company_facilities")
	Integer companyFacilities;

	@Column(name="company_guidance")
	Integer companyGuidance;

	public Integer getId() { return id; }

	public EvaluationType getEvaluationType() { return evaluationType; }
	public void setEvaluationType(EvaluationType evaluationType) { this.evaluationType = evaluationType; }

	public int getMotivation() { return motivation; }
	public void setMotivation(int motivation) { this.motivation = motivation; }

	public int getEfficiency() { return efficiency; }
	public void setEfficiency(int efficiency) { this.efficiency = efficiency; }

	public int getEffectiveness() { return effectiveness; }
	public void setEffectiveness(int effectiveness) { this.effectiveness = effectiveness; }

	public int getCompanyFacilities() { return companyFacilities; }
	public void setCompanyFacilities(int companyFacilities) { this.companyFacilities = companyFacilities; }

	public int getCompanyGuidance() { return companyGuidance; }
	public void setCompanyGuidance(int companyGuidance) { this.companyGuidance = companyGuidance; }
}
