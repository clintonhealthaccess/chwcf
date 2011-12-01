package org.chai.chwcf.reports;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.chai.chwcf.organisation.Cooperative;

public class ReportTable {

	private List<Cooperative> cooperatives;
	private List<Date> months;
	private Map<Cooperative, Map<Date, Double>> values;
	
	public ReportTable(List<Cooperative> cooperatives, List<Date> months,
			Map<Cooperative, Map<Date, Double>> values) {
		super();
		this.cooperatives = cooperatives;
		this.months = months;
		this.values = values;
	}
	
	public List<Cooperative> getCooperatives() {
		return cooperatives;
	}
	
	public List<Date> getMonths() {
		return months;
	}
	
	public Double getValue(Cooperative cooperative, Date month) {
		return values.get(cooperative).get(month);
	}
		
}
