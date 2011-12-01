<r:require module="chosen"/>
<div id="add-role" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Role</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'role', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:input name="name" label="Name" value="${role.name}" bean="${role}" field="name"/>
	<div class="row ${hasErrors(bean:role, field:'permissions', 'errors')}">
	 <label>Permissions </label>
	    <select id="permission-list" name="permissions" multiple="true" class="ajax-search-field">
			<g:if test="${role?.permissions != null}">
				<g:each in="${permissions}" var="permission">
					<g:if test="${role?.permissions.contains(permission.permission)}">
						<option value="${permission.permission}" selected>
							${permission.permission}
						</option>
					</g:if>
				</g:each>
			</g:if>
		</select>
		<div class="error-list"><g:renderErrors bean="${role}" field="permissions" /></div>
	</div>	
	
	<g:if test="${role?.id!=null}">
		<input type="hidden" name="id" value="${role.id}"/>
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
<script type="text/javascript">
	$(document).ready(function() {	
		$("#permission-list").ajaxChosen({
			type : 'GET',
			dataType: 'json',
			url : "${createLink(controller:'permissionHelper', action:'getAjaxData')}"
		}, function (data) {
			var terms = {};
			$.each(data.elements, function (i, val) {
				terms[val.id] = val.permission;
			});
			return terms;
		});
	});					
</script>