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
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.chai.chwcf.utils.Utils;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class PbfScoreController extends AbstractEntityController {
	CooperativeService cooperativeService;
	
	def getEntity(def id){
		return PbfScore.get(id);
	}
	def createEntity(){
		def entity = new PbfScore();
		if(!params['cooperative.id']) entity.cooperative= Cooperative.get(params.int('cooperative'));
		return entity;
	}
	def getModel(def entity) {
		
		[
			score: entity
			]
	}

	def getTemplate() {
		return "/admin/organisation/createPbfScore"
	}
	
	def getLabel() {
		return "admin.organisation.pbf.score.label"
	}

	def bindParams(def entity) {
		bindData(entity,params,[exclude:['startDate','endDate']])
				
		if(params.startDate!='' && params.startDate!=null){
			entity.startDate=Utils.parseDate(params.startDate);
		}else
			entity.startDate=null;
			
		if(params.endDate!='' && params.endDate!=null){
			entity.endDate=Utils.parseDate(params.endDate);
		}else
			entity.endDate=null;
		
		// FIXME GRAILS-6967 makes this necessary
		// http://jira.grails.org/browse/GRAILS-6967
		if (params.names!=null) entity.names = params.names
	}
	
	def list = {
		int districtLevel = ConfigurationHolder.config.district.level;
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0
		
		Cooperative cooperative = Cooperative.get(params.cooperative)
		List<PbfScore> scores = cooperative.scores;
		
		def max = Math.min(params['offset']+params['max'],scores.size())
		
		render (view: '/admin/list', model:[
			template: "/organisation/scoreList",
			entities: scores.subList(params['offset'], max),
			showLocation: false,
			entityCount: scores.size(),
			targetURI: getTargetURI(),
			code: getLabel()
			])
	}


}
