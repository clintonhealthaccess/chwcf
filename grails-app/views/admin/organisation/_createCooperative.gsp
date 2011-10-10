<div id="add-cooperative" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Create Cooperative</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'objective', action:'save']" useToken="true">
	<div class="row">
			<input type="hidden" name="organisationUnit.id" value="${cooperative.organisationUnit.id}" />
			<label>Facility:</label> {cooperative.organisationUnit.name}
	</div>
	<g:i18nInput name="name" bean="${cooperative}" value="${cooperative?.name}" label="Name" field="name" />
	<g:i18nRichTextarea name="description" bean="${cooperative}" value="${cooperative?.description}" label="Description" field="description" height="100"  width="300" maxHeight="100" />
	
	</g:form>
	<div class="clear"></div>
</div>