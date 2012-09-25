<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require modules="chosen,datepicker"/>
<div id="add-cooperative" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title"><g:message code="admin.cooperative.label" default="Cooperative"/></h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'cooperative', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<input type="hidden" name="district" value="${district.id}"/>
	<div class="row ${hasErrors(bean:cooperative, field:'organisationUnit', 'errors')}">
		<label for="organisationUnit.id">Facility</label> 
		<select name="organisationUnit.id">
			<option value="null">-- <g:message code="admin.select.facility.label" default="Select a Facility"/> --</option>
			<g:each in="${facilities}" var="facility">
				<option value="${facility.getId()}" ${facility.getId()==cooperative?.organisationUnit?.id?'selected="selected"':''}>
					${facility.getName()}
				</option>
			</g:each>
		</select>
		<div class="error-list">
			<g:renderErrors bean="${cooperative}" field="organisationUnit" />
		</div>
	</div>
	<g:inputDate name="createDate" precision="day" id="create-date" value="${cooperative?.createDate}" label="Created on" bean="${cooperative}" field="createDate"/>
    <g:input name="serviceName" label="Service Name" bean="${cooperative}" value="${cooperative.serviceName}" field="serviceName"/>
    <g:input name="commercialName" label="Commercial Name" bean="${cooperative}" value="${cooperative.commercialName}" field="commercialName"/>
    <g:textarea name="description" bean="${cooperative}" rows="10" value="${cooperative?.description}" label="Description" field="description" />
	
	<div class="row ${hasErrors(bean:cooperative, field:'registrationLevel', 'errors')}">
		<label for="registrationLevel.id">Registration Level</label> 
		<select name="registrationLevel.id">
			<option value="null">-- Select a Level --</option>
			<g:each in="${levels}" var="level">
				<option value="${level.id}" ${level.id==cooperative?.registrationLevel?.id?'selected="selected"':''}>
					<g:i18n field="${level.names}" />
				</option>
			</g:each>
		</select>
		<div class="error-list">
			<g:renderErrors bean="${cooperative}" field="registrationLevel" />
		</div>
	</div>
	<g:input name="registrationNumber" bean="${cooperative}" value="${cooperative?.registrationNumber}" label="Registration Number" field="registrationNumber" />
	<g:input name="numberOfVillages" label="Number Of Villages in Catchment area" bean="${cooperative}" value="${cooperative.numberOfVillages}" field="numberOfVillages"/>
	<g:input name="numberOfCells" label="Number of Cells in Catchment Area" bean="${cooperative}" value="${cooperative.numberOfCells}" field="numberOfCells"/>
	<div class="row ${hasErrors(bean:cooperative, field:'activities', 'errors')}">
		<label><g:message code="admin.cooperative.activities.label" default="Activities"/></label>
		    <select id="activities-list" name="activities" multiple="true" class="ajax-search-field">
				<g:if test="${!cooperative.activities.isEmpty()}">
					<g:each in="${cooperative.activities}" var="activity">
						<option value="${activity.id}" selected>
							<g:i18n field="${activity.names}" />
						</option>
					</g:each>
				</g:if>
			</select>
		<div class="error-list"><g:renderErrors bean="${cooperative}" field="activities" /></div>
	</div>

	<g:if test="${cooperative.id != null}">
		<input type="hidden" name="id" value="${cooperative.id}"/>
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
		$("#activities-list").ajaxChosen({
			type : 'GET',
			dataType: 'json',
			url : "${createLink(controller:'activity', action:'getAjaxData')}"
		}, function (data) {
			var terms = {};
			$.each(data.elements, function (i, val) {
				terms[val.id] = val.activity;
			});
			return terms;
		});	
	});					
</script>