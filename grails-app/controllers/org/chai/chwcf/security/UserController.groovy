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
package org.chai.chwcf.security

import java.util.List;

import org.chai.chwcf.AbstractEntityController
import org.chai.chwcf.organisation.Organisation
import org.chai.chwcf.organisation.OrganisationService;
import org.chai.chwcf.security.User
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.apache.shiro.crypto.hash.Sha256Hash
import org.chai.chwcf.transaction.Transaction;
/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class UserController extends AbstractEntityController {
	OrganisationService organisationService
	int districtLevel =ConfigurationHolder.config.district.level
	int facilityLevel = ConfigurationHolder.config.facility.level;
	def facilityGroups =ConfigurationHolder.config.facility.group.type;

	def getEntity(def id) {
		return User.get(id)
	}

	def createEntity() {
		return new User()
	}

	def getLabel() {
		return 'admin.user.label'
	}
	
	def getTemplate() {
		return "/admin/security/createUser"
	}
	def deleteEntity(def entity) {
		if(!Transaction.findByEnteredBy(entity.id) && !Transaction.findByValidatedBy(entity.id))
			entity.delete();
	}
	
	def getModel(def entity) {
		List<Organisation> organisations = organisationService.getOrganisation(districtLevel,facilityLevel,facilityGroups);
		[
			user:entity, 
			organisations: organisations,
			permissions: PermissionHelper.list(),
			roles: Role.list()
		]
	}

	def bindParams(def entity) {
		bindData(entity,params,[exclude:['cooperative','organisation','passwordHash']])
		
		if(params.passwordHash!=null && params.passwordHash!='' && params.confirmpass!=null && params.confirmpass!='')
			if(params.confirmpass.equals(params.passwordHash))
				entity.passwordHash = new Sha256Hash(params.passwordHash).toHex()
			else
				entity.passwordHash=null

		
		if(params.cooperative){
			if (!params.organisation)
				entity.organisation=params.int('cooperative')
			else{
				entity.organisation=params.int('organisation')
				}
		}else{
			entity.organisation=params.int('organisation')
		}
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 30)
		params.offset = params.offset ? params.int('offset'): 0
		List<User> users = User.list(params);

		render (view: '/admin/list', model:[
			template:"/security/userList",
			entities: users,
			entityCount: User.count(),
			targetURI: getTargetURI(),
			code: getLabel()
		])
	}
	
	def newPassword ={
		this.getNewPasswordModel(null,user);
	}
	
	def saveNewPassword={ NewPasswordCommand  cmd ->
		def user=null
		if(params.int('user'))
			user= User.get(params.int('user'))
		else
			user = getUser();
			
		if (!cmd.hasErrors()) {
			def username=user.username
			user.setPasswordHash(new Sha256Hash(params.passwordHash).toHex())
			user.save()
			if(getTargetURI())
				redirect(url:getTargetURI())
		}else{
			this.getNewPasswordModel(cmd,user);
		}
	}
	
	
	def getNewPasswordModel(def cmd,user){
		render (view: '/admin/edit', model:[
			template:"/admin/security/changePassword",
			user: user,
			cmd: cmd,
			targetURI: getTargetURI()
		])
	}
		
}

class NewPasswordCommand {
	String passwordHash
	String confirmpass
	
	static constraints = {
		passwordHash(nullable: false, blank: false, minSize: 5)
		confirmpass(validator: {val, obj ->
			val == obj.passwordHash
		})
	}
}