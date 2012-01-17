<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="entity.list.name.label" default="Name" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
			<th><g:message code="entity.list.order.label" default="Order" /></th>
			<th><g:message code="list.header.category.total.number.of.category.label" default="Total Number of Category" /></th>
			<th><g:message code="entity.list.manage.label" default="Manage" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="type">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<shiro:hasPermission permission="categoryType:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'categoryType', action:'edit', params:[id: type?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="categoryType:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'categoryType', action:'delete', params:[id: type?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td><g:i18n field="${type.names}" /></td>
				<td><g:i18n field="${type.descriptions}" /></td>
				<td>${type.order}</td>
				<td>${type.categories.size()}</td>
				<td>
					<div class="dropdown subnav-dropdown"> 
						<a class="selected" href="#" data-type="cooperative"><g:message code="entity.list.manage.label" default="Manage"/></a>
						<div class="hidden dropdown-list">
							<ul>
								<li>
									<a href="${createLinkWithTargetURI(controller: 'category', action:'list', params:[typeId: type?.id])}">
									  <g:message code="admin.transaction.category.label " default="Category"/>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
		</g:each>
	</tbody>
</table>