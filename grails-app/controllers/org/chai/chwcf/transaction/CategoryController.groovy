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
package org.chai.chwcf.transaction

import org.chai.chwcf.AbstractEntityController;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.reports.CostingType;
import org.chai.chwcf.transaction.CategoryService;
import org.apache.commons.collections.*;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class CategoryController extends AbstractEntityController {
	CategoryService categoryService;

	def getEntity(def id){
		return Category.get(id);
	}
	def createEntity(){
		def entity = new Category();
		if(!params.int('type.id')) entity.type = CategoryType.get(params.int('typeId'));
		return entity;
	}
	def getModel(def entity) {
		[
					category: entity,
					types: CategoryType.list(),
					costingTypes: CostingType.list()
				]
	}

	def getTemplate() {
		return "/admin/transaction/createCategory"
	}
	def getLabel() {
		return "admin.transaction.category.label"
	}
	def bindParams(def entity) {
		entity.properties = params

		// FIXME GRAILS-6967 makes this necessary
		// http://jira.grails.org/browse/GRAILS-6967
		if (params.names!=null) entity.names = params.names
		if (params.descriptions!=null) entity.descriptions = params.descriptions
		if(!params.list('costingTypes.id').isEmpty())
			for(String type: params.list('costingTypes.id')){
				def costingType = CostingType.get(Integer.parseInt(type));
				if(!entity.costingTypes.contains(costingType)) entity.addCostingType(costingType);
			}
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0

		CategoryType type = CategoryType.get(params.int('typeId'));
		List<Category> categories = type.categories;
		if(!categories.isEmpty())
			Collections.sort(categories);

		def max = Math.min(params['offset']+params['max'],categories.size())

		render (view: '/admin/list', model:[
					template:"/transaction/categoryList",
					entities: categories.subList(params['offset'],max),
					showLocation: false,
					entityCount:  categories.size(),
					targetURI: getTargetURI(),
					code: getLabel()
				])
	}
	
	def getAjaxData = {
		def categories = categoryService.searchCategory(params['term']);
		render(contentType:"text/json") {
			elements = array {
				categories.each { category ->
					elem (
						id: category.id,
						category: g.i18n(field: category.names)+' &rarr; ['+ g.i18n(field: category.type.names)+']'
					)
				}
			}
		}
	}
}
