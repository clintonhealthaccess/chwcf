package org.chai.chwcf.reports;

import java.util.Date;
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.transaction.TransactionService;

public class CostingReportFormula extends ReportFormula {

	private CostingType costingType;

	public CostingReportFormula(CostingType costingType) {
		super();
		this.costingType = costingType;
	}

	@Override
	public Double getValue(Date date, Cooperative cooperative, TransactionService transactionService) {
		return transactionService.getTransactionSum(date, cooperative, costingType);
	}
	
}
