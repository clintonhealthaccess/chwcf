<div id="add-transaction-category-type" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Transaction type Type</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'categoryType', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:i18nInput name="names" bean="${type}" value="${type?.names}" label="Name" field="names" />
	<g:i18nTextarea name="descriptions" bean="${type}" value="${type?.descriptions}" label="Descriptions" field="descriptions" />
    <g:input name="order" label="Order" value="${type?.order}" bean="${type}" field="order"/>
	<g:if test="${type.id != null}">
		<input type="hidden" name="id" value="${type.id}"/>
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