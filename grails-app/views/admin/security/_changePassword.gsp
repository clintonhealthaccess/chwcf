<div id="add-password" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Change Password</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'user', action:'saveNewPassword', params: [targetURI: targetURI]]" useToken="true">
    	<input type="hidden" name="user" value="${user.id}"/>
    	<div class="error-list"></div>
		<div class="row">
			<label>Password</label>
			<input type="password" name="passwordHash" class="idle-field"/>
			<g:hasErrors bean="${cmd?.errors}">
	       		<div class="error-list">
	       		 	<g:renderErrors bean="${cmd}" field="passwordHash"/>
	       		</div>
   			</g:hasErrors>
		</div>
		<div class="row">
			<label>Confirm Password</label>
			<input type="password" name="confirmpass" class="idle-field"/>
			<g:hasErrors bean="${cmd?.errors}">
	       		<div class="error-list">
	       		 	<g:renderErrors bean="${cmd}" field="confirmpass"/>
	       		</div>
   			</g:hasErrors>
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
