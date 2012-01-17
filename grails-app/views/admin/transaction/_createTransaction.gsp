<%@ page import="org.chai.chwcf.utils.Utils" %>
<%@ page import="org.chai.chwcf.security.User" %>
<r:require modules="chosen,datepicker"/>
<div id="add-transaction-category" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Cooperative Transaction Entry Form</h3>
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'transaction', action:'save', params: [targetURI: '/transaction/create/'+transaction.cooperative.id]]" useToken="true">
		<div class="row">
			<g:if test="${transaction.id != null}">
				<label>Entered By</label>
				<input value="${User.get(transaction?.enteredBy).username}" class="idle-field" disabled/>
				<input type="hidden" name="enteredBy" value="${transaction.enteredBy}"/>
			</g:if>
		</div>
		
		<div class="row">
			<label>Cooperative</label>
			<input value="${transaction.cooperative.name}" class="idle-field" disabled/>
			<input type="hidden" name="cooperative.id" value="${transaction.cooperative.id}"/>
		</div>
		
		<div class="row">
			<label>Date of Transaction</label>
			<input type="text" id="transaction-date" class="idle-field input" name="transactionDate" value="${Utils.formatDate(transaction?.transactionDate)}" ${readonly?'disabled':''}/>
			<div class="error-list">
				<g:renderErrors bean="${transaction}" field="transactionDate" />
			</div>
			<div class="clear"></div>
		</div>
				
		<div class="row ${hasErrors(bean:transaction, field:'category', 'errors')}">
			<label for="category.id"><g:message code="admin.create.category.label" default="Category"/></label>
		    <select id="elements-list" name="category.id" class="ajax-search-field" ${readonly?'disabled':''}>
				<g:if test="${transaction.category?.id != null}">
					<option value="${transaction.category.id}" selected>
						<g:i18n field="${transaction.category.names}" /> &rarr; [<g:i18n field="${transaction.category.type.names}" />]
					</option>
				</g:if>
			</select>
			<div class="error-list"><g:renderErrors bean="${transaction}" field="category" /></div>
		</div>
	 	<g:textarea name="description" bean="${transaction}" rows="10" value="${transaction?.description}" label="Description" field="description" readonly="${readonly}"/>
	  	<g:input name="amount" label="Amount" value="${transaction?.amount}" bean="${transaction}" field="amount" readonly="${readonly}"/>
	  	
	  	<shiro:hasPermission permission="transaction:approve">
		  	<div class="row ${hasErrors(bean:transaction,field:approval,'errors')}">
				<label>Approval</label>
				<g:checkBox name="approval" value="${transaction.approval}" />
				<div class="error-list"><g:renderErrors bean="${transaction}" field="approval" /></div>
			</div>
		</shiro:hasPermission>
		<g:if test="${transaction.approval}">
			<div class="row ${hasErrors(bean:transaction,field:validatedBy,'errors')}">
				<label>Approved By</label>
				<input value="${User.get(transaction?.validatedBy)?.username}" class="idle-field" disabled/>
				<input type="hidden" name="validatedBy" value="${transaction.validatedBy}"/>
			</div>
			<div class="row ${hasErrors(bean:transaction,field:validatedBy,'errors')}">
				<label>On</label>
				<input value="${Utils.formatDate(transaction?.approvalDate)}" class="idle-field" disabled/>
			</div>
		</g:if>
	  	<g:if test="${transaction.id != null}">
	  		<input type="hidden" name="id" value="${transaction.id}"/>
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
	<div class="main">
		<div class="row">
			<g:if test="${!entities.isEmpty()}">
		 		<g:render template="${listTemplate}" model="[entities: entities,cooperative: transaction.cooperative]"/>
			</g:if>
		</div>
	</div>
	<div class="clear"></div>
</div>
<script type="text/javascript">
	$(document).ready(function() {		
		$("#elements-list").ajaxChosen({
			type : 'GET',
			dataType: 'json',
			url : "${createLink(controller:'category', action:'getAjaxData')}"
		}, function (data) {
			var terms = {};
			$.each(data.elements, function (i, val) {
				terms[val.id] = val.category;
			});
			return terms;
		});
		
		$('#transaction-date').glDatePicker({
			onChange : function(target, newDate) {
				target.val(newDate.getDate() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getFullYear());
				target.trigger('change')
			},
			zIndex : "10"
		});
	});					
</script>