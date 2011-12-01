<r:require module="chosen"/>
<div id="add-costing-type" class="entity-form-container togglable">
	<div class="entity-form-header">
		<h3 class="title">Costing Type</h3>
		<g:locales />
		<div class="clear"></div>
	</div>
	<g:form url="[controller:'costingType', action:'save', params: [targetURI: targetURI]]" useToken="true">
	<g:i18nInput name="names" bean="${costingType}" value="${costingType?.names}" label="Name" field="names" />
	<g:i18nTextarea name="descriptions" bean="${costingType}" value="${costingType?.descriptions}" label="Descriptions" field="descriptions" />  
    <g:input name="order" label="Order" bean="${costingType}" value="${costingType?.order}" field="order"/>
     <g:input name="code" label="Code" bean="${costingType}" value="${costingType?.code}" field="code"/>
   	
	<div class="row ${hasErrors(bean:costingType, field:'categories', 'errors')}">
		<label>Categories </label>
	    <select id="categories-list" name="categories" multiple="true" class="ajax-search-field">
			<g:if test="${costingType.categories.size() != 0}">
				<g:each in="${costingType.categories}" var="category">
					<option value="${category.id}" selected>
						<g:i18n field="${category.names}" />
					</option>
				</g:each>
			</g:if>
		</select>
		<div class="error-list"><g:renderErrors bean="${costingType}" field="categories" /></div>
	</div>
	
	<g:if test="${costingType.id != null}">
		<input type="hidden" name="id" value="${costingType.id}"/>
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
		$("#categories-list").ajaxChosen({
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
	});					
</script>