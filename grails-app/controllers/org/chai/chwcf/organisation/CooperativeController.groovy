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

import org.apache.shiro.SecurityUtils;
import org.chai.chwcf.AbstractEntityController;
import org.chai.chwcf.CooperativeSorter
import org.chai.chwcf.organisation.Organisation;
import org.chai.chwcf.organisation.CooperativeService;
import org.hisp.dhis.organisationunit.OrganisationUnit
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.utils.Utils;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class CooperativeController extends AbstractEntityController {

	CooperativeService cooperativeService;
	
	int facilityLevel = ConfigurationHolder.config.facility.level;
	int districtLevel = ConfigurationHolder.config.district.level;
	def facilityGroups =ConfigurationHolder.config.facility.group.type;

	def getEntity(def id){
		return Cooperative.get(id);
	}
	def createEntity(){
		return new Cooperative()
	}
	def getModel(def entity) {
		
		def district;
		if(entity.organisationUnit!=null){
			def organisation = organisationService.createOrganisation(entity.organisationUnit);
			district = organisationService.getParentOfLevel(organisation, districtLevel);
		}else{
			if(params.int('district')){
				district = organisationService.createOrganisation(OrganisationUnit.get(params.int('district')))
			}
			if(params.int('organisation')){
				district = organisationService.createOrganisation(OrganisationUnit.get(params.int('organisation')))	
				organisationService.loadChildren(district);
			}
			
		}
		def organisationGroups = organisationService.getOrganisationUnitGroups(facilityGroups);
		     
			[
				facilities: organisationService.getChildrenOfLevelAndGroups(district, facilityLevel,organisationGroups),
				district: district,
				cooperative: entity,
				activities: Activity.list(),
				levels: RegistrationLevel.list()
			]
	}

	def getTemplate() {
		return "/admin/organisation/createCooperative"
	}

	def getLabel() {
		return "admin.organisation.cooperative.label"
	}

	def bindParams(def entity) {
		bindData(entity,params,[exclude:['createDate']])
				
		if(params.createDate!='' && params.createDate!=null){
			entity.createDate=Utils.parseDate(params.createDate);
		}else
			entity.createDate=null;
	}
	
	def list = {
		Organisation currentOrganisation;
		List<Cooperative> cooperatives = [];
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0
		def organisationTree = organisationService.getOrganisationTreeUntilLevel(districtLevel)
		
		// branch depending on user
		if (getUser().organisation != null) {
			def organisationForUser = organisationService.getOrganisation(getUser().organisation);
			currentOrganisation = getOrganisation(false)
			
			if (currentOrganisation == null) currentOrganisation = organisationForUser
			else if (!organisationService.isAncestor(organisationForUser, currentOrganisation)) {
				// check if user has access, if not redirect to forbidden page
				response.sendError(403)
				return	
			}
		}
		else {
			log.debug("currentOrganisation1"+currentOrganisation)
			currentOrganisation = getOrganisation(false)
			log.debug("currentOrganisation2"+currentOrganisation)
		}
		
		if (currentOrganisation != null) {
			log.debug("currentOrganisation3"+currentOrganisation)
			organisationService.getLevel(currentOrganisation)
			cooperatives=cooperativeService.getCooperative(currentOrganisation);
		}
		
		if(!cooperatives.isEmpty()) Collections.sort(cooperatives, new CooperativeSorter());
			
		def max = Math.min(params['offset']+params['max'],cooperatives.size())
			
		render (view: '/admin/list', model:[
				template: "/organisation/cooperativeList",
				organisation: currentOrganisation,
				entities: cooperatives.subList(params['offset'], max),
				showLocation: true,
				entityCount: cooperatives.size(),
				districtLevel: districtLevel,
				organisationTree: organisationTree,
				targetURI: getTargetURI(),
				code: getLabel()
		])

	}
	def view ={
		Cooperative cooperative = Cooperative.get(params.int('cooperative'));
		render (view: '/admin/view', model:[
			cooperative: cooperative,
			targetURI: getTargetURI()
		])
	}
	def getAjaxData = {
		List<Organisation> organisations = cooperativeService.searchOrganisation(params['term']);
		render(contentType:"text/json") {
			elements = array {
				organisations.each { organisation ->
					elem (
						id: organisation.organisationUnit.id,
						organisation: organisation.organisationUnit.name
					)
				}
			}
		}
	}

}





