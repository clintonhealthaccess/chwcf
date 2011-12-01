<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require module="datepicker"/>
<div id="add-pbfscore" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">PBF Score</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'pbfScore', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${score.cooperative.name}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${score.cooperative.id}"/>
		<div class="clear"></div>
	</div>
	<div class="row">
	    <label>Period</label>
	    <g:inputDate name="startDate" id="start-date" bean="${score}" value="${Utils.formatDate(score?.startDate)}" label="Start Date" field="startDate" />
	    <g:inputDate name="endDate" id="end-date" bean="${score}" value="${Utils.formatDate(score?.endDate)}" label="End Date" field="endDate" />
	    <div class="clear"></div>
	</div>
	<g:input name="score" bean="${score}" value="${score?.score}" label="Score" field="score" />
	<g:input name="amountHCtoCoop" bean="${score}" value="${score?.amountHCtoCoop}" label="Amount found from Health Center to the Cooperative" field="amountHCtoCoop" />
	<g:i18nTextarea name="names" bean="${score}" value="${score?.names}" label="Descriptions" field="names" />
    <g:input name="order" label="Order" bean="${score}" value="${score?.order}" field="order"/>
	<g:if test="${score.id != null}">
		<input type="hidden" name="id" value="${score.id}"/>
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