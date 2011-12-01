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
package org.chai.chwcf.reports

import org.chai.chwcf.AbstractEntityController;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.transaction.Category;
import org.chai.chwcf.reports.CostingType;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class CostingTypeController extends AbstractEntityController {
	
	def getEntity(def id){
		return CostingType.get(id);
	}
	def createEntity(){
		return new CostingType();
	}
	def getModel(def entity) {
		
		[
			costingType: entity,
			categories: Category.list()
			]
	}

	
	def getTemplate() {
		return "/admin/transaction/createCostingType"	
	}
	def getLabel() {
		return "admin.reports.costing.type.label"
	}
	def bindParams(def entity) {
		
		for(def category : entity.categories) {
			if (!params.list('categories').contains(category.id)){
				category.costingTypes.remove(entity)
			}
		}
		entity.properties = params
		for(def category: params.list('categories')){
			Category cat = Category.get(category)
			if (!cat.costingTypes.contains(entity))
				cat.addCostingType(entity)
		}
		// FIXME GRAILS-6967 makes this necessary
		// http://jira.grails.org/browse/GRAILS-6967
		if (params.names!=null) entity.names = params.names
		if (params.descriptions!=null) entity.descriptions = params.descriptions
	}
	
	def list={
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0
		
		List<CostingType> costingTypes = CostingType.list(params);
		
		render (view: '/admin/list', model:[
			template: "/transaction/costingTypeList",
			entities: costingTypes,
			showLocation: false,
			entityCount: CostingType.count(),
			targetURI: getTargetURI(),
			code: getLabel()
			])
		
		}

}
