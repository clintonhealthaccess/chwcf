<!-- Template goes here -->
<g:if test="${!entities.isEmpty()}">
	<g:render template="/admin/${template}" />
	<!-- End of template -->
	<div class="paginateButtons">
		<g:paginate total="${entityCount}" params="${params}" />
	</div>
</g:if>
<g:else>
	<div>List is Empty</div>
</g:else>