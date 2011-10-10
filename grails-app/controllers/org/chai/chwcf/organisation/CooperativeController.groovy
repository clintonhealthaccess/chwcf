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

import org.chai.chwcf.AbstractEntityController;
import org.chai.chwcf.CooperativeSorter
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.chai.chwcf.organisation.Organisation;
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.organisation.CooperativeService;
import org.hisp.dhis.organisationunit.OrganisationUnit

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class CooperativeController extends AbstractEntityController {
	def log = GroovyLog.newInstance("LogExample");
	OrganisationService organisationService;
	CooperativeService cooperativeService;

	def getEntity(def id){
		return Cooperative.get(id);
	}
	def createEntity(){
		def entity = new Cooperative();
		if(!params['facilityId']) entity.organisationUnit = OrganisationUnit.get(params.int('facilityId'));
		return entity;
	}
	def getModel(def entity) {
		
		[ 
			cooperative: entity,
			activities: Activity.list(),
			levels: RegistrationLevel.list()
			]
	}

	def getTemplate() {
		return "/admin/organisation/createCooperative"
	}
	def validateEntity(def entity) {
		return entity.validate()
	}

	def saveEntity(def entity) {
		entity.save();
	}
	def deleteEntity(def entity) {
		entity.delete()
	}
	def bindParams(def entity) {
		entity.properties = params
	}

	def list = {

		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0

		OrganisationUnit district = OrganisationUnit.get(params.int('districtId'));
		log.sayHello("District====>"+district)

		List<Cooperative> cooperatives = cooperativeService.getCooperative(district)
		Collections.sort(cooperatives, new CooperativeSorter());

		def max = Math.min(params['offset']+params['max'],cooperatives.size())

		render (view: '/admin/list', model:[
					template: "organisation/listCooperatives",
					entities: cooperatives.subList(params['offset'], max),
					entityCount: cooperatives.size(),
					entityName: "Cooperative",
					code: "admin.cooperative.label"
				])
	}
}




