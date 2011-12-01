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
package org.chai.chwcf.person

import org.apache.commons.logging.Log;
import org.chai.chwcf.AbstractEntityController;
import org.chai.chwcf.organisation.Cooperative;
import org.chai.chwcf.person.Member.Gender;
import org.chai.chwcf.person.MemberCategory;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.utils.Utils;

/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class MemberController extends AbstractEntityController {
	
	def getEntity(def id){
		return Member.get(id);
	}
	def createEntity(){
		def entity = new Member ();
		if(!params['cooperative.id']) entity.cooperative = Cooperative.get(params.int('cooperative'));
		return entity;
	}
	def getModel(def entity) {
		[
		member: entity,
		categories: MemberCategory.list(),
		genders: Gender.values()
		]
	}

	def getTemplate() {
		return "/admin/person/createMember"
	}
	def getLabel() {
		return "admin.person.member.label"
	}
	def bindParams(def entity) {	
		bindData(entity,params,[exclude:['joinDate','leftDate','dob']])
		
		//FIXME If you find better solution to do this please feel free to fix
		
		if(params.joinDate!='' && params.joinDate!=null){
			entity.joinDate=Utils.parseDate(params.joinDate);
		}else
			entity.joinDate=null;
			
		if(params.leftDate!='' && params.leftDate!=null){
			entity.leftDate=Utils.parseDate(params.leftDate);
		}else
			entity.leftDate=null;
			
		if(params.dob!='' && params.dob!=null){
			entity.dob=Utils.parseDate(params.dob);
		}else
			entity.dob=null;
				
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0
		List<Member> members = []
		def hideNewBar = false
		
		if(params.cooperative){
			Cooperative cooperative = Cooperative.get(params.cooperative)
			members = cooperative.members;
		}
		if(params.category){
			MemberCategory category = MemberCategory.get(params.category)
			members = category.members;
            hideNewBar= true;
		}
		def max = Math.min(params['offset']+params['max'],members.size())
		
		render (view: '/admin/list', model:[
			template: "/person/memberList",
			entities: members.subList(params['offset'], max),
			showLocation: false,
			entityCount: members.size(),
			hideNewBar: hideNewBar,
			targetURI: getTargetURI(),
			code: getLabel()
			])
	}

}
