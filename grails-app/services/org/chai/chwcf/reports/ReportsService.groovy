package org.chai.chwcf.reports
/**
 * Copyright (c) 2011, Clinton Health Access Initiative.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS and ConTRIBUTORS "as IS" and
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY and FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR ConSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTIon) HOWEVER CAUSED and
 * on ANY THEORY OF LIABILITY, WHETHER IN ConTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.chai.chwcf.CooperativeSorter
import org.chai.chwcf.organisation.Cooperative
import org.chai.chwcf.organisation.CooperativeService;
import org.chai.chwcf.organisation.Organisation;
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.reports.CostingType;
import org.chai.chwcf.reports.ReportFormula.Operand;
import org.chai.chwcf.transaction.CategoryType
import org.chai.chwcf.transaction.Transaction;
import org.chai.chwcf.transaction.TransactionService;
import org.chai.chwcf.utils.Utils;
import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.period.Cal;
/**
 * @author Jean Kahigiso M.
 *
 */
class ReportsService {
	
	private static final String SALES_CODE = 'sales';
	private static final String TOTAL_COST_CODE = 'totalCost';
	private static final String DIVIDEND = 'dividend';
	private static final String TOTAL_ASSET_CODE = 'totalAsset';
	private static final String INVESTMENT_CODE ='investment';
	private static final String EXPENSE_CODE = 'expense';
	private static final String INCOME_CODE = 'income';
	
	OrganisationService organisationService;
	CooperativeService cooperativeService;
	TransactionService transactionService;
	
	
	public ReportFormula getNetIncomeFormula() {
		CostingReportFormula salesFormula = new CostingReportFormula(CostingType.findByCode(SALES_CODE));
		CostingReportFormula totalCostFormula = new CostingReportFormula(CostingType.findByCode(TOTAL_COST_CODE));
		return new OperationReportFormula(Operand.SUBSTRACTION, [salesFormula, totalCostFormula])
	}
	
	public ReportFormula getRetainedEarnings() {
		CostingReportFormula dividendFormula = new CostingReportFormula(CostingType.findByCode(DIVIDEND))
		return new OperationReportFormula(Operand.SUBSTRACTION, [this.getNetIncomeFormula(), dividendFormula])
	}
	
	public ReportFormula getCashFlow(){
		CostingReportFormula expenseFormula = new CostingReportFormula(CostingType.findByCode(EXPENSE_CODE));
		CostingReportFormula incomeFormula = new CostingReportFormula(CostingType.findByCode(INCOME_CODE));
		return new OperationReportFormula(Operand.SUBSTRACTION, [incomeFormula, expenseFormula])
		
	}
	public ReportFormula getProfitabilityRatio(){
		CostingReportFormula investmentFormula = new CostingReportFormula(CostingType.findByCode(INVESTMENT_CODE));
		return 
			new OperationReportFormula(Operand.MULTIPLICATION, [
				new OperationReportFormula(Operand.DIVISION, [
					this.getNetIncomeFormula(),
					investmentFormula
				]),
				new ConstantReportFormula(100)
			])
	}
	
	public ReportFormula getTotalAsset(){
		CostingReportFormula totalAsset = new CostingReportFormula(CostingType.findByCode(TOTAL_ASSET_CODE));
		return new OperationReportFormula(Operand.SUM, [totalAsset])
	}

	
	public ReportTable getReportTable(Organisation organisation, Date startDate, Date endDate, ReportFormula reportFormula) {
		List<Cooperative> cooperatives = cooperativeService.getCooperative(organisation);
		if(!cooperatives.isEmpty())
			Collections.sort(cooperatives, new CooperativeSorter());
		List<Date> months = getMonths(startDate, endDate)
		Map<Cooperative, Map<Date, Double>> values = new HashMap<Cooperative, Map<Date,Double>>();
		for (Cooperative cooperative : cooperatives) {
			Map<Date, Double> monthMap = new HashMap<Date, Double>();
			for (Date month : months) 
				monthMap.put(month, reportFormula.getValue(month, cooperative, transactionService));
			values.put(cooperative, monthMap);
		}
		return new ReportTable(cooperatives, months, values);
	}
	
	// returns the first day of each month between startDate and endDate
	public static List<Date> getMonths(Date startDate, Date endDate) {
		List<Date> result = new ArrayList<Date>();
		Calendar calendar = Utils.getFirstOfMonthCalendar(startDate)
		Date month = calendar.getTime()
		while (!month.equals(endDate)) {
			result.add(month);
			calendar.add(Calendar.MONTH, 1)
			month = calendar.getTime()
		}
		result.add(Utils.getFirstOfMonthCalendar(endDate).getTime());
		return result;
	}  
	
}
