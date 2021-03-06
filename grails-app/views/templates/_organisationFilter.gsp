<div class="filter">
	<span class="bold"><g:message code="filter.organisation.label" default="Location"/></span>
	<span class="dropdown dropdown-organisation subnav-dropdown">
		<g:if test="${currentOrganisation != null}">
			<a class="selected" href="#" data-type="organisation" data-organisation="${currentOrganisation.id}">${currentOrganisation.name}</a>
		</g:if>
		<g:else>
			<a class="selected" href="#" data-type="organisation">
				<g:message code="filter.organisation.noselection.label" default="No location selected"/>
			</a>
		</g:else> 
		<div class="hidden dropdown-list">
			<ul>
				<g:render template="/templates/organisationTree" model="[
					controller: controllerName, 
					action: actionName, 
					organisation: organisationTree, 
					current: currentOrganisation, 
					displayLinkUntil: displayLinkUntil,
					linkParams: linkParams
				]" />
			</ul>
		</div>
	</span>
</div>