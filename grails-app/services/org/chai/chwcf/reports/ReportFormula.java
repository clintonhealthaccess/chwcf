package org.chai.chwcf.reports;

import java.util.Date;

import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.transaction.TransactionService;

public abstract class ReportFormula {

	public static enum Operand {SUM, SUBSTRACTION, DIVISION, MULTIPLICATION}
	
	public abstract Double getValue(Date date, Cooperative cooperative, TransactionService transactionService);
	
}
