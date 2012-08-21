<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require module="datepicker"/>
<div id="add-training" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Training</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'training', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${training.cooperative.serviceName}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${training.cooperative.id}"/>
		<div class="clear"></div>
	</div>	
	<div class="row">
	    <label>Period</label>
	    <g:inputDate name="startDate" precision="day" id="start-date" value="${training?.startDate}" label="Start Date" bean="${training}" field="startDate"/>
	     <g:inputDate name="endDate" precision="day" id="end-date" value="${training?.endDate}" label="End Date" bean="${training}" field="endDate"/>
	    <div class="clear"></div>
	</div>
	<g:input name="provider" bean="${training}" value="${training?.provider}" label="Organiser" field="provider" />
	<g:input name="location" bean="${training}" value="${training?.location}" label="Location" field="location" />
	<g:i18nTextarea name="descriptions" bean="${training}" value="${training?.descriptions}" label="Descriptions" field="descriptions" />
	<g:if test="${training.id != null}">
		<input type="hidden" name="id" value="${training.id}"/>
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
