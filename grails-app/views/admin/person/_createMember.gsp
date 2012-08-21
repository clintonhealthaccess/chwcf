<%@ page import="org.chai.chwcf.utils.Utils" %>
<%@ page import="org.chai.chwcf.person.Member.Gender" %>
<r:require module="datepicker"/>
<div id="add-member" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Member</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'member', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${member.cooperative.serviceName}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${member.cooperative.id}"/>
	</div>
	<g:input name="idNumber" label="National ID Number" value="${member.idNumber}" bean="${member}" field="idNumber"/>
	<g:input name="familyName" label="Family Name" value="${member.familyName}" bean="${member}" field="familyName"/>
	<g:input name="otherNames" label="Other Names" value="${member.otherNames}" bean="${member}" field="otherNames"/>
	<g:inputDate name="dob" precision="day" id="dob" value="${member?.dob}" label="Date of Birth" bean="${member}" field="dob"/>
	<g:selectFromEnum name="gender" bean="${member}" values="${Gender.values()}" field="gender" label="Gender"/>
	<g:inputDate name="joinDate" precision="day" id="join-date" value="${member?.joinDate}" label="Member Since" bean="${member}" field="joinDate"/>
	<div class="row ${hasErrors(bean:member,field:active,'errors')}">
		<label>Active</label>
		<g:checkBox name="active" value="${member.active}" />
	<div class="error-list"><g:renderErrors bean="${member}" field="active" /></div>
	</div>
	 <g:inputDate name="leftDate" precision="day" id="left-date" noSelection="['':'-Choose-']" value="${member?.leftDate}" label="Left Since (Default date wont be saved if member is still active)" bean="${member}" field="leftDate"/>	
	<g:selectFromList name="category.id" label="Category" field="category" optionKey="id" multiple="false"
			from="${categories}" value="${member.category?.id}" bean="${member}" values="${categories.collect {i18n(field:it.names)}}" />
	
	<g:input name="phoneNumber" label="Phone" value="${member.phoneNumber}" bean="${member}" field="phoneNumber"/>
	<g:input name="email" label="Email" bean="${member}" value="${member.email}" field="email"/>
	
	<g:if test="${member.id != null}">
		<input type="hidden" name="id" value="${member.id}"/>
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