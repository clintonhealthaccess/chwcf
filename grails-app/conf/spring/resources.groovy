import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.chai.chwcf.organisation.OrganisationService;
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
	
}
