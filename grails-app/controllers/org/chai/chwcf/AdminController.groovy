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
package org.chai.chwcf
import org.chai.chwcf.organisation.Organisation;
import org.chai.chwcf.organisation.OrganisationService;
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class AdminController {

	OrganisationService organisationService;
	def log;

	def regions={

		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0

		Organisation country = organisationService.getRootOrganisation();
		organisationService.loadChildren(country);

		List<Organisation> regions = country.children;

		for(Organisation region: regions)
			organisationService.loadChildren(region);

		Collections.sort(regions,new OrganisationSorter())

		def max = Math.min(params['offset']+params['max'],regions.size());

		render (view: '/admin/list', model:[
					template: "organisation/listRegions",
					entities: regions.subList(params['offset'], max),
					entityCount: regions.size(),
					code: "admin.regions.label"
				])
	}

	def districts = {

		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0

		Organisation region = organisationService.getOrganisation(params.int('regionId'));
		organisationService.loadChildren(region);

		List<Organisation> districts = region.children;

		for(Organisation district: districts)
			organisationService.loadChildren(district)

		Collections.sort(districts,new OrganisationSorter())

		def max = Math.min(params['offset']+params['max'],districts.size())

		render (view: '/admin/list', model:[
					template: "organisation/listDistricts",
					entities: districts.subList(params['offset'], max),
					entityCount: districts.size(),
					code: "admin.districts.label"
				])
	}

	def facilities = {

		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0

		Organisation district= organisationService.getOrganisation(params.int('districtId'));
		organisationService.loadChildren(district,ConfigurationHolder.config.facility.level);

		List<Organisation> facilities = district.children;

		for(Organisation facility: facilities)
			organisationService.loadChildren(facility);

		Collections.sort(facilities,new OrganisationSorter())

		def max = Math.min(params['offset']+params['max'],facilities.size())

		render (view: '/admin/list', model:[
					template: "organisation/listFacilities",
					entities: facilities.subList(params['offset'], max),
					entityCount: facilities.size(),
					code: "admin.facilities.label"
				])
	}
}
