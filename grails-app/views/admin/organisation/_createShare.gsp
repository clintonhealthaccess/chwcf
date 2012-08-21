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
		<input value="${share.cooperative.serviceName}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${share.cooperative.id}"/>
		<div class="clear"></div>
	</div>
	<g:inputDate name="year" precision="year" id="year" value="${share?.year}" label="Year" bean="${share}" field="year"/>
	
	<div class="row ${hasErrors(bean:share,field:current,'errors')}">
		<label>Current (Check this box if this Share currently being paid)</label>
		<g:checkBox name="current" value="${share.current}" />
		<div class="error-list"><g:renderErrors bean="${share}" field="current" /></div>
	</div>
	<g:input name="share" bean="${share}" value="${share?.share}" label="Share" field="share" />
	<g:i18nTextarea name="descriptions" bean="${share}" value="${share?.descriptions}" label="Descriptions" field="descriptions" />
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