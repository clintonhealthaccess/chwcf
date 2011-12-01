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
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.apache.commons.logging.Log;
import org.apache.shiro.SecurityUtils;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.organisation.Cooperative
import org.chai.chwcf.organisation.Organisation
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.reports.ReportsService;
import org.chai.chwcf.utils.Utils;
import org.chai.chwcf.reports.ReportTable;
import org.chai.chwcf.security.User;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletOutputStream

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.chai.chwcf.transaction.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class ReportController {
	
	OrganisationService organisationService;
	ReportsService reportsService;
	int startingYear = ConfigurationHolder.config.report.start.year;
	def reportTypes = ConfigurationHolder.config.report.types;
	def districtLevel =ConfigurationHolder.config.district.level
	def now = new Date();
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private static FILE_NAME ="chwcf-reports";
	private static FILE_TYPE=".xls";

	def report = { 
       this.getModel(null)
	}

	// TODO copy-pasted from AbstractEntityController, put in Utils 
	protected def getUser() {
		return User.findByUsername(SecurityUtils.subject.principal)
	}
	
	def download = { ReportCommand cmd ->
		if (!cmd.hasErrors()) {
			// let's first check that the user has the right to see this report
			if (getUser().organisation != null) {
				def organisation = organisationService.getOrganisation(getUser().organisation)
				
				if (!organisationService.isAncestor(organisation, organisationService.getOrganisation(cmd.organisation))) {
					response.sendError(403)
					return
				}
			}
			
			Map <String,ReportTable> reports = new HashMap<String,ReportTable>();
			reports =getReports();
			
			this.writeExcel(reports);
		} else {
			this.getModel(cmd)
		}
	}
	
	def getTargetURI() {
		return params.targetURI?: "/"
	}
	
	def getModel(def cmd) {
		def organisations = organisationService.getOrganisationsOfLevel(districtLevel);		
		def reportOrganisation = null;
		if (getUser().organisation != null) {
			// TODO adapt when organisation not a facility
			reportOrganisation = organisationService.getOrganisation(getUser().organisation)
		}
		
		render (view: '/admin/edit', model:[
					template: "/report/reportForm",
					startYear: startingYear,
					endYear: now.year+1900,
					reportTypes:reportTypes,
					targetURI: getTargetURI(),
					reportOrganisation: reportOrganisation,
					organisations: organisations,
					cmd: cmd
		])
	}
	
	public Map <String,ReportTable> getReports(){
		Map <String,ReportTable> reports = new HashMap<String,ReportTable>();
		Date startDate = Utils.getDate(params.int('startYear'), params.int('startMonth'), 1);
		Date endDate = Utils.getDate(params.int('endYear'), params.int('endMonth'), 1);
		Organisation organisation = organisationService.getOrganisation(params.int("organisation"));
		for(String reportType: params.reportTypes )
			reports.put(reportTypes[Integer.parseInt(reportType)-1], generateReport(startDate,endDate,organisation,Integer.parseInt(reportType)))
		return reports;
	}
	
	public ReportTable generateReport(Date startDate,  Date endDate,Organisation organisation,int reportType){
		ReportTable reportTable=null;
		if(reportType==1)
			reportTable= reportsService.getReportTable(organisation, startDate, endDate, reportsService.getNetIncomeFormula())
		if(reportType==2)
			reportTable= reportsService.getReportTable(organisation, startDate, endDate,reportsService.getRetainedEarnings())
		if(reportType==3)
			reportTable= reportTable= reportsService.getReportTable(organisation, startDate, endDate,reportsService.getCashFlow())
		if(reportType==4)
			reportTable= reportTable= reportsService.getReportTable(organisation, startDate, endDate,reportsService.getTotalAsset())
		if(reportType==5)
			reportTable= reportTable= reportsService.getReportTable(organisation, startDate, endDate,reportsService.getProfitabilityRatio())
		return reportTable;
	}
	
	
	private void addDataToCell(WritableSheet sheet, int column, int row, String data) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, data, times);
		sheet.addCell(label);
	}
	
	
	
	public writeExcel(Map <String,ReportTable> reports){
		File file = File.createTempFile(FILE_NAME,FILE_TYPE);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		WritableFont timesFpt = new WritableFont(WritableFont.TIMES, 10);
		times = new WritableCellFormat(timesFpt);
		times.setWrap(true);
		
		Integer i=0;
		for(Map.Entry<String, ReportTable> entry: reports.entrySet()){
			workbook.createSheet(entry.getKey(), i);
			WritableSheet excelSheet = workbook.getSheet(i);
			ReportTable reportTable = entry.getValue();
			
			for(int j=0;j<reportTable.getMonths().size();j++)
				this.addDataToCell(excelSheet, j+1, 0,Utils.REPORT_DATE_FORMAT.format(reportTable.getMonths()[j]));
				
			for(int c=0;c<reportTable.getCooperatives().size();c++){
				this.addDataToCell(excelSheet, 0, c+1,reportTable.getCooperatives()[c].name);
				for(int m=0;m<reportTable.getMonths().size();m++){
					Double value = reportTable.getValue(reportTable.getCooperatives()[c], reportTable.getMonths()[m]);
					this.addDataToCell(excelSheet, m+1, c+1, Double.toString(value));
				}
			}
			i++;
		}
		workbook.write();
		workbook.close();
		response.setHeader("Content-disposition", "filename=${file.getName()}")
		response.contentType="application/vnd.ms-excel";
		response.outputStream << file.newInputStream()
		file.deleteOnExit();
	}
	
	
}

class ReportCommand {
	Integer startMonth;
	Integer endMonth;
	Integer startYear;
	Integer endYear;
	Integer organisation;
	List<Integer> reportTypes = [];
	
	static constraints={
		startMonth blank:false,nullable:false, validator: {  val, obj -> return (obj.startYear==obj.endYear)?(val<=obj.endMonth):null}
		endMonth blank:false,nullable:false
		startYear blank:false,nullable:false, validator: {  val, obj -> return (val<=obj.endYear)}
		endYear blank:false,nullable:false
		organisation blank:false,nullable:false
		reportTypes validator: {val, obj -> return !val.isEmpty() }
	}
}
