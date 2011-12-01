<div id="add-activity" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Activity</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'activity', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:i18nInput name="names" bean="${activity}" value="${activity?.names}" label="Name" field="names" />
	<g:i18nTextarea name="descriptions" bean="${activity}" value="${activity?.descriptions}" label="Descriptions" field="descriptions"  />
    <g:input name="order" label="Order" value="${activity?.order}" bean="${activity}" field="order"/>
	<g:if test="${activity.id != null}">
		<input type="hidden" name="id" value="${activity.id}"/>
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