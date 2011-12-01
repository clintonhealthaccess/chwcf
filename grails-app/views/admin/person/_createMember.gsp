<%@ page import="org.chai.chwcf.utils.Utils" %>
<r:require module="datepicker"/>
<div id="add-member" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Member</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'member', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<div class="row">
		<label>Cooperative</label>
		<input value="${member.cooperative.name}" class="idle-field" disabled/>
		<input type="hidden" name="cooperative.id" value="${member.cooperative.id}"/>
	</div>
	<g:input name="idNumber" label="National ID Number" value="${member.idNumber}" bean="${member}" field="idNumber"/>
	<g:input name="familyName" label="Family Name" value="${member.familyName}" bean="${member}" field="familyName"/>
	<g:input name="otherNames" label="Other Names" value="${member.otherNames}" bean="${member}" field="otherNames"/>
	<g:inputDate name="joinDate" id="join-date" label="Member Since" bean="${member}" value="${Utils.formatDate(member?.joinDate)}" field="joinDate"/>
	
	<div class="row ${hasErrors(bean:member,field:active,'errors')}">
		<label>Active</label>
		<g:checkBox name="active" value="${member.active}" />
	<div class="error-list"><g:renderErrors bean="${member}" field="active" /></div>
	</div>
	
	<g:inputDate name="leftDate" id="left-date" label="Left Since" value="${Utils.formatDate(member?.leftDate)}"  bean="${member}" field="leftDate"/>
	<g:inputDate name="dob" id="dob" label="Date of Birth" value="${Utils.formatDate(member?.dob)}" bean="${member}" field="dob"/>
	
	<div class="row ${hasErrors(bean:member, field:'gender', 'errors')}">
		<label for="gender">Gender</label> 
		<select name="gender">
			<option value="">-- Select a Gender --</option>
			<g:each in="${genders}" var="gender">
				<option value="${gender.name()}" ${gender.name()==member.gender?.name()?'selected="selected"':''}>
					${gender.name()}
				</option>
			</g:each>
		</select>
		<div class="error-list">
			<g:renderErrors bean="${member}" field="gender" />
		</div>
	</div>
	
	<div class="row ${hasErrors(bean:member, field:'category', 'errors')}">
		<label for="category.id">Category</label> 
		<select name="category.id">
			<option value="null">-- Select a Category --</option>
			<g:each in="${categories}" var="category">
				<option value="${category.id}" ${category.id==member?.category?.id?'selected="selected"':''}>
					<g:i18n field="${category.names}" />
				</option>
			</g:each>
		</select>
		<div class="error-list">
			<g:renderErrors bean="${member}" field="category" />
		</div>
	</div>
	
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
<script type="text/javascript">
	$(document).ready(function() {				
		$('#join-date').glDatePicker({
			onChange : function(target, newDate) {
				target.val(newDate.getDate() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getFullYear());
				target.trigger('change')
			},
			zIndex : "10"
		});
		$('#left-date').glDatePicker({
			onChange : function(target, newDate) {
				target.val(newDate.getDate() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getFullYear());
				target.trigger('change')
			},
			zIndex : "10"
		});
		$('#dob').glDatePicker({
			onChange : function(target, newDate) {
				target.val(newDate.getDate() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getFullYear());
				target.trigger('change')
			},
			zIndex : "10"
		});
	});					
</script>