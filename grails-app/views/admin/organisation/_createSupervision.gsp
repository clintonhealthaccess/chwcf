<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require module="datepicker"/>
<div id="add-supervision" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Supervision</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'supervision', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${supervision.cooperative.serviceName}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${supervision.cooperative.id}"/>
		<div class="clear"></div>
	</div>
	<g:inputDate name="date" precision="day" id="date" value="${supervision?.date}" label="Date" bean="${supervision}" field="date"/>
	<g:input name="supervisor" bean="${supervision}" value="${supervision?.supervisor}" label="Supervisor" field="supervisor" />
	<g:input name="source" bean="${supervision}" value="${supervision?.source}" label="Where The Supervisor Come from" field="source" />
	<g:input name="location" bean="${supervision}" value="${supervision?.location}" label="Location" field="location" />
	<g:i18nTextarea name="descriptions" bean="${supervision}" value="${supervision?.descriptions}" label="Descriptions" field="descriptions" />
	<g:if test="${supervision.id != null}">
		<input type="hidden" name="id" value="${supervision.id}"/>
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