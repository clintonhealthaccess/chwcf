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
		<input value="${training.cooperative.name}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${training.cooperative.id}"/>
		<div class="clear"></div>
	</div>
	<div class="row">
	    <label>Period</label>
		<g:inputDate name="startDate" id="start-date" bean="${training}" value="${Utils.formatDate(training?.startDate)}" label="Start Date" field="startDate" />
		<g:inputDate name="endDate" id="end-date" bean="${training}" value="${Utils.formatDate(training?.endDate)}" label="End Date:" field="endDate" />
		<div class="clear"></div>
	</div>
	<g:input name="provider" bean="${training}" value="${training?.provider}" label="Organiser" field="provider" />
	<g:input name="location" bean="${training}" value="${training?.location}" label="Location" field="location" />
	<g:i18nTextarea name="names" bean="${training}" value="${training?.names}" label="Descriptions" field="names" />
    <g:input name="order" label="Order" value="${training?.order}" bean="${training}" field="order"/>
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
<script type="text/javascript">
	$(document).ready(function() {				
		$('#start-date').glDatePicker({
			onChange : function(target, newDate) {
				target.val(newDate.getDate() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getFullYear());
				target.trigger('change')
			},
			zIndex : "10"
		});
		$('#end-date').glDatePicker({
			onChange : function(target, newDate) {
				target.val(newDate.getDate() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getFullYear());
				target.trigger('change')
			},
			zIndex : "10"
		});
	});					
</script>