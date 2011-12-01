package org.chai.chwcf.reports;

import java.util.Date;

import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.transaction.TransactionService;

public class ConstantReportFormula extends ReportFormula {

	private Double constant;
	
	public ConstantReportFormula(Double constant) {
		super();
		this.constant = constant;
	}

	@Override
	public Double getValue(Date date, Cooperative cooperative, TransactionService transactionService) {
		return constant;
	}
	
}
