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

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.organisationunit.OrganisationUnit
import org.hisp.dhis.organisationunit.OrganisationUnitGroup
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.organisation.OrganisationService;
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.chai.chwcf.utils.Utils;
import org.apache.commons.lang.StringUtils;

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
	int districtLevel = ConfigurationHolder.config.district.level;
	def facilityGroups =ConfigurationHolder.config.facility.group.type;
	
	List<Cooperative> getCooperative(Organisation organisation){
		List<Cooperative> cooperatives = new ArrayList<Cooperative>();
		Set<OrganisationUnit> facilities = organisationUnitService.getOrganisationUnitsAtLevel(facilityLevel, organisation.organisationUnit);	
			
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
		
	List<Cooperative> searchCooperative(String text){
		List<Cooperative> cooperatives = Cooperative.list();
		StringUtils.split(text).each { chunk ->
			cooperatives.retainAll { cooperative ->
				Utils.matches(chunk, cooperative.name) ||
				Utils.matches(chunk, cooperative.organisationUnit.name)
			}
		}
		return cooperatives.sort{
			it.name
		}
	}
	
	List<Organisation> searchOrganisation(String text){
		List<Organisation> orgs = organisationService.getOrganisation(districtLevel,facilityLevel,facilityGroups);
		List<Organisation> organisations= organisationService.searchOrganisation(orgs,text);
		return organisations.sort{
			it.organisationUnit.name
		}
	}

	
	
}
