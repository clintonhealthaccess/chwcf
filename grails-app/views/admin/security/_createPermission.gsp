<div id="add-permission" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Permission</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'permissionHelper', action:'save', params: [targetURI: targetURI]]" useToken="true">
    <g:textarea name="permission" bean="${permission}" value="${permission?.permission}" label="Permission" field="permission" rows="6" />
	<g:textarea name="description" bean="${permission}" value="${permission?.description}" label="Description" field="description" rows="8" />
	
	<g:if test="${permission.id != null}">
		<input type="hidden" name="id" value="${permission.id}"/>
	</g:if>
		
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
