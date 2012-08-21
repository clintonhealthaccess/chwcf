<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require module="datepicker"/>
<div id="add-pbfpbf" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">PBF</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'pbf', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${pbf.cooperative.serviceName}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${pbf.cooperative.id}"/>
		<div class="clear"></div>
	</div>
	<g:selectFromList name="type.id" label="Type" field="type" optionKey="id" multiple="false"
			from="${types}" value="${pbf.type?.id}" bean="${pbf}" values="${types.collect {i18n(field:it.names)}}" />
	<div class="row">
	    <label>Period</label>
	    <g:inputDate name="startDate" precision="day" id="start-date" value="${pbf?.startDate}" label="Start Month" bean="${pbf}" field="startDate"/>
	     <g:inputDate name="endDate" precision="day" id="end-date" value="${pbf?.endDate}" label="End Month" bean="${pbf}" field="endDate"/>
	    <div class="clear"></div>
	</div>
	<g:input name="amoutMohToHC" bean="${pbf}" value="${pbf?.amoutMohToHC}" label="Amount found from MoH to Health Center" field="amoutMohToHC" />
	<g:input name="amountSousCompte" bean="${pbf}" value="${pbf?.amountSousCompte}" label="Amount Sous Copmpte" field="amountSousCompte" />
	<g:input name="amountHCtoCoop" bean="${pbf}" value="${pbf?.amountHCtoCoop}" label="Amount found from Health Center to the Cooperative" field="amountHCtoCoop" />
	<g:i18nTextarea name="descriptions" bean="${pbf}" value="${pbf?.descriptions}" label="Descriptions" field="descriptions" />
	<g:if test="${pbf.id != null}">
		<input type="hidden" name="id" value="${pbf.id}"/>
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