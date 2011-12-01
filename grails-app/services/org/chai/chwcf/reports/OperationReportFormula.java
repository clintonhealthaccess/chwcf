package org.chai.chwcf.reports;

import java.util.Date;
import java.util.List;
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.transaction.TransactionService;

public class OperationReportFormula extends ReportFormula {

	private Operand operand;
	private List<ReportFormula> reportFormulas;

	public OperationReportFormula(Operand operand,List<ReportFormula> reportFormulas) {
		super();
		this.operand = operand;
		this.reportFormulas = reportFormulas;
	}

	@Override
	public Double getValue(Date date, Cooperative cooperative,TransactionService transactionService) {
		Double value = null;
		switch (operand) {
		case SUM:
			value = 0d;
			for (ReportFormula reportFormula : reportFormulas)
				value += reportFormula.getValue(date, cooperative,transactionService);
			break;
		case SUBSTRACTION:
			for (ReportFormula reportFormula : reportFormulas)
				if (value == null) value = reportFormula.getValue(date, cooperative,transactionService);
				else value -= reportFormula.getValue(date, cooperative,transactionService);
			break;
		case MULTIPLICATION:
			value = 1d;
			for (ReportFormula reportFormula : reportFormulas)
				value *= reportFormula.getValue(date, cooperative,transactionService);
			break;
		case DIVISION:
			value =  1d;
			for (ReportFormula reportFormula : reportFormulas)
				if(value==null)	value=reportFormula.getValue(date, cooperative,transactionService);
				else value /= reportFormula.getValue(date, cooperative,transactionService);
		default:
			break;
		}
		return value;
	}

}
