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
import org.chai.chwcf.organisation.ActivityService;
import org.chai.chwcf.organisation.Activity;
import org.apache.commons.collections.*;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class ActivityController extends AbstractEntityController {
	ActivityService activityService;
	
	def getEntity(def id){
		return Activity.get(id);
	}
	def createEntity(){
		return new Activity();
	}
	def getModel(def entity) {
		
		[
			activity: entity
			]
	}

	def getTemplate() {
		return "/admin/organisation/createActivity"
	}
	def getLabel() {
		return "admin.organisation.activity.label"
	}
	def bindParams(def entity) {
		entity.properties = params
		
		// FIXME GRAILS-6967 makes this necessary
		// http://jira.grails.org/browse/GRAILS-6967
		if (params.names!=null) entity.names = params.names
		if (params.descriptions!=null) entity.descriptions = params.descriptions
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0
		List<Activity> activities=[];
		def entityCount
		def hideNewBar=true;
			
		if(!params.int('cooperative')){
			activities = Activity.list(params);
			entityCount = Activity.count();
			hideNewBar = false;
		}else{
			Cooperative cooperative = Cooperative.get(params.cooperative);
			def max = Math.min(params['offset']+params['max'],cooperative.activities.size());
			activities =cooperative.activities.subList(params['offset'], max)
			entityCount=activities.size()
		}
		if(!activities.isEmpty())
			Collections.sort(activities)
			
		render (view: '/admin/list', model:[
			template:"/organisation/activityList",
			entities: activities,
			showLocation: false,
			entityCount: entityCount,
			targetURI: getTargetURI(),
			hideNewBar: hideNewBar,
			code: getLabel()
			])
	}
	
	def getAjaxData = {
		def activities = activityService.searchActivity(params['term']);
		render(contentType:"text/json") {
			elements = array {
				activities.each { activity ->
					elem (
						id: activity.id,
						activity: g.i18n(field: activity.names)
					)
				}
			}
		}
	}

}
