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
package org.chai.chwcf.organisation

import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.organisationunit.OrganisationUnit
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.organisation.OrganisationService;
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class CooperativeService {
	def log = GroovyLog.newInstance("LogExample");
	static transactional = true;
	def localeService;
	def sessionFactory;
	
	OrganisationService organisationService;
	OrganisationUnitService organisationUnitService;
	int facilityLevel = ConfigurationHolder.config.facility.level;


	List<Cooperative> getCooperative(OrganisationUnit district){
		List<Cooperative> cooperatives = new ArrayList<Cooperative>();
		Set<OrganisationUnit> facilities = organisationUnitService.getOrganisationUnitsAtLevel(facilityLevel, district);	
			
		for(OrganisationUnit facility:facilities)
			cooperatives.addAll(this.getCooperativeOfFacility(facility))
			
		return cooperatives;
	}
	
	List<Cooperative> getCooperativeOfFacility(OrganisationUnit facility){
		List<Cooperative> cooperatives = new ArrayList<Cooperative>();
		List<Cooperative> allCooperatives = this.getAllCooperatives();		
		
		for(Cooperative cooperative: allCooperatives)
			if(cooperative.getOrganisationUnit().equals(facility))
				cooperatives.add(cooperative)
		return cooperatives;
	}

	List<Cooperative> getAllCooperatives(){
		return Cooperative.list();
	}
}
