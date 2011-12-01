<div id="add-transaction-category" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Transaction Category</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'category', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:i18nInput name="names" bean="${category}" value="${category?.names}" label="Name" field="names" />
	<g:i18nTextarea name="descriptions" bean="${category}" value="${category?.descriptions}" label="Descriptions" field="descriptions" />
    	
	<div class="row ${hasErrors(bean:category, field:'type', 'errors')}">
		<label for="type.id">Category Type</label> 
		<select name="type.id">
			<option value="">-- <g:message code="admin.create.category.select.a.category.label" default="Select a Category"/> --</option>
			<g:each in="${types}" var="type">
				<option value="${type.id}" ${type.id==category?.type?.id?'selected="selected"':''}>
					<g:i18n field="${type.names}" />
				</option>
			</g:each>
		</select>
		<div class="error-list">
			<g:renderErrors bean="${category}" field="type" />
		</div>
	</div>
	<div class="row ${hasErrors(bean:category, field:'costingTypes', 'errors')}">
	 	<label for="costingTypes.id">Costing Types</label> 
		<select name="costingTypes.id" multiple="multiple" size="8">
			<g:each in="${costingTypes}" var="type">
				<option value="${type.id}" ${category.costingTypes.contains(type)?'selected="selected"':''}>
					<g:i18n field="${type.names}" />
				</option>
			</g:each>
		</select>
		<div class="error-list">
			<g:renderErrors bean="${category}" field="costingTypes" />
		</div>
	</div>
    <g:input name="order" label="Order" value="${category?.order}" bean="${category}" field="order"/>
	<g:if test="${category.id != null}">
		<input type="hidden" name="id" value="${category.id}"/>
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