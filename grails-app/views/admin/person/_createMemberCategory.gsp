<div id="add-member-category" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Member Category</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'memberCategory', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:i18nInput name="names" bean="${category}" value="${category?.names}" label="Name" field="names" />
	<g:i18nTextarea name="descriptions" bean="${category}" value="${category?.descriptions}" label="Descriptions" field="descriptions"/>
    <g:input name="order" label="Order" bean="${category}" value="${category?.order}" field="order"/>
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