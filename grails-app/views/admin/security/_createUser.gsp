<r:require module="chosen"/>
<div id="add-user" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">User</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'user', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:input name="familyName" label="Family Name" value="${user.familyName}" bean="${user}" field="familyName"/>
	<g:input name="otherName" label="Other Name" value="${user.otherName}" bean="${user}" field="otherName"/>
	<g:input name="email" label="Email" value="${user.email}" bean="${user}" field="email"/>
	<g:input name="affiliation" label="Organistion" value="${user.affiliation}" bean="${user}" field="affiliation"/>
	
	<g:input name="username" label="Username" value="${user.username}" bean="${user}" field="username"/>
	
    <g:if test="${user.id == null}">
		<div class="row">
			<label>Password</label>
			<input type="password" name="passwordHash" value="${user.passwordHash}" class="idle-field"/>
		</div>
		<div class="row">
			<label>Confirm Password</label>
			<input type="password" name="confirmpass" class="idle-field"/>
		</div>
	</g:if>
	<g:else>
		<div class="row">
			<input type="hidden" name="id" value="${user.id}"/>
			<a class="new-password-link" href="${createLinkWithTargetURI(controller: 'user', action:'newPassword', params:[user: user?.id])}">
				<g:message code="default.link.change.password.label" default="Change Password" />
			</a>
		</div>
	</g:else>
	
	<div class="row ${hasErrors(bean:user,field:active,'errors')}">
		<label>Active</label>
		<g:checkBox name="active" value="${user.active}" />
		<div class="error-list"><g:renderErrors bean="${user}" field="active" /></div>
	</div>
	<div class="row ${hasErrors(bean:user, field:'roles', 'errors')}">
	 <label>Roles </label>
	    <select id="roles-list" name="roles" class="ajax-search-field">
		    <g:if test="${!user.roles?.isEmpty()}">
				<g:each in="${user.roles}" var="role">
					<option value="${role.id}" selected>
						${role.name}
					</option>
				</g:each>
			</g:if>
		</select>
		<div class="error-list"><g:renderErrors bean="${user}" field="roles" /></div>
	</div>
	<div class="row ${hasErrors(bean:user, field:'organisation', 'errors')}">
	 <label>Organisation Unit </label>
	    <select id="organisation-list" name="organisation" class="ajax-search-field">
			<g:if test="${user?.organisation != null}">
				<g:each in="${organisations}" var="organisation">
					<g:if test="${organisation.organisationUnit.id==user.organisation}">
						<option value="${user.organisation}" selected>
							${organisation.organisationUnit.name}
						</option>
					</g:if>
				</g:each>
			</g:if>
		</select>
		<div class="error-list"><g:renderErrors bean="${user}" field="organisation" /></div>
	</div>	
	<div class="row">
		<button type="submit">
			<g:message code="default.button.save.label" default="Save" />
		</button>
		<a href="${createLink(uri: targetURI)}">
			<g:message code="default.link.cancel.label" default="Cancel" />
		</a>
	</div>
	</g:form>
	<div class="clear"></div>
</div>
<script type="text/javascript">
	$(document).ready(function() {	
		$("#organisation-list").ajaxChosen({
			type : 'GET',
			dataType: 'json',
			url : "${createLink(controller:'cooperative', action:'getAjaxData')}"
		}, function (data) {
			var terms = {};
			$.each(data.elements, function (i, val) {
				terms[val.id] = val.organisation;
			});
			return terms;
		});
		$("#roles-list").ajaxChosen({
			type : 'GET',
			dataType: 'json',
			url : "${createLink(controller:'role', action:'getAjaxData')}"
		}, function (data) {
			var terms = {};
			$.each(data.elements, function (i, val) {
				terms[val.id] = val.role;
			});
			return terms;
		});
	});					
</script>