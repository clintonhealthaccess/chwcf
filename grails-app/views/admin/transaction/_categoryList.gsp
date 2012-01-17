<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="entity.list.name.label" default="Name" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
			<th><g:message code="entity.list.type.label" default="Type" /></th>
			<th><g:message code="entity.list.order.label" default="Order" /></th>
			<th><g:message code="list.header.category.total.number.of.costing.type.label" default="Total Number Costing Types" /></th>
			<th><g:message code="list.header.category.total.number.of.transactions.type.label" default="Total Number Transactions" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="category">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<shiro:hasPermission permission="category:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'category', action:'edit', params:[id: category?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="category:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'category', action:'delete', params:[id: category?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td><g:i18n field="${category.names}" /></td>
				<td><g:i18n field="${category.descriptions}" /></td>
				<td><g:i18n field="${category.type.names}" /></td>
				<td>${category.order}</td>
				<td>${category.costingTypes.size()}</td>
				<td>${category.transactions.size()}</td>
			</tr>
		</g:each>
	</tbody>
</table>