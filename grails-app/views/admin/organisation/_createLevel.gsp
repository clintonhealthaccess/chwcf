<div id="add-level" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Registration level</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'registrationLevel', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:i18nInput name="names" bean="${level}" value="${level?.names}" label="Name" field="names" />
	<g:i18nTextarea name="descriptions" bean="${level}" value="${level?.descriptions}" label="Descriptions" field="descriptions" />
    <g:input name="order" label="Order" value="${level?.order}"bean="${level}" field="order"/>
	<g:if test="${level.id != null}">
		<input type="hidden" name="id" value="${level.id}"/>
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