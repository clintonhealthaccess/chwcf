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

import org.chai.chwcf.AbstractEntityController;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.chai.chwcf.security.PermissionHelper;
import org.chai.chwcf.security.PermissionHelperService;
/**
 * @author Jean Kahigiso M.
 *
 */
@SuppressWarnings("deprecation")
class PermissionHelperController extends AbstractEntityController {
	PermissionHelperService permissionHelperService

	def getEntity(def id) {
		return PermissionHelper.get(id)
	}

	def createEntity() {
		return new PermissionHelper()
	}

	def getLabel() {
		return 'admin.permission.label'
	}
	
	def getTemplate() {
		return "/admin/security/createPermission"
	}
	
	def getModel(def entity) {
		[
			permission:entity
		]
	}

	def bindParams(def entity) {
		entity.properties = params	
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : ConfigurationHolder.config.site.entity.list.max, 20)
		params.offset = params.offset ? params.int('offset'): 0
		List<PermissionHelper> permissions = PermissionHelper.list(params);

		render (view: '/admin/list', model:[
			template:"/security/permissionList",
			entities: permissions,
			entityCount: PermissionHelper.count(),
			targetURI: getTargetURI(),
			code: getLabel()
		])
	}
	
	def getAjaxData = {
		List<PermissionHelper> permissions = permissionHelperService.searchPermissionHelper(params['term']);
		render(contentType:"text/json") {
			elements = array {
				permissions.each { permission ->
					elem (
						id: permission.id,
						permission: permission.permission
					)
				}
			}
		}
	}
}
