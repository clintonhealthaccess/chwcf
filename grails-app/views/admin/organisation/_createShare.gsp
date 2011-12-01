<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require module="datepicker"/>
<div id="add-share" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Share</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'share', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${share.cooperative.name}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${share.cooperative.id}"/>
		<div class="clear"></div>
	</div>
	<div class="row">
	    <label>Period</label>
		<g:inputDate name="startDate" id="start-date" bean="${share}" value="${Utils.formatDate(share?.startDate)}" label="Start Date" field="startDate" />
		<g:inputDate name="endDate" id="end-date" bean="${share}" value="${Utils.formatDate(share?.endDate)}" label="End Date:" field="endDate" />
		<div class="clear"></div>
	</div>
	<div class="row ${hasErrors(bean:share,field:current,'errors')}">
		<label>Current</label>
		<g:checkBox name="current" value="${share.current}" />
		<div class="error-list"><g:renderErrors bean="${share}" field="current" /></div>
	</div>
	<g:input name="share" bean="${share}" value="${share?.share}" label="Share" field="share" />
	<g:i18nTextarea name="names" bean="${share}" value="${share?.names}" label="Descriptions" field="names" />
    <g:input name="order" label="Order" value="${share?.order}" bean="${share}" field="order"/>
	<g:if test="${share.id != null}">
		<input type="hidden" name="id" value="${share.id}"/>
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