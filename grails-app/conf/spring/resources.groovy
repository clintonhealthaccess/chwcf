import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.transaction.TransactionService;
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.organisation.CooperativeService;
import org.chai.chwcf.reports.ReportsService;

// Place your Spring DSL code here


def config = CH.config

int organisationLevel = config.facility.level
String facilityTypeGroup = config.facility.type.group

beans = {
	
	organisationService(OrganisationService) {
		group = facilityTypeGroup
		organisationUnitService = ref("organisationUnitService")
		organisationUnitGroupService = ref("organisationUnitGroupService")
		facilityLevel = organisationLevel
	}
	reportsService(ReportsService){
		organisationService=ref("organisationService")
		cooperativeService=ref("cooperativeService")
		transactionService=ref("transactionService")
	}
	
}
