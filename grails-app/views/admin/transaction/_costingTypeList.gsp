<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th>Name</th>
			<th>Descriptions</th>
			<th>Order</th>
			<th>Total Number of Category</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="type">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			  <td>
				<ul class="horizontal">
						<li>
							<a class="edit-link" href="${createLinkWithTargetURI(controller: 'costingType', action:'edit', params:[id: type?.id])}">
								<g:message code="default.link.edit.label" default="Edit" />
							</a>
						</li>
						<li>
							<a class="delete-link" href="${createLinkWithTargetURI(controller: 'costingType', action:'delete', params:[id: type?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
								<g:message code="default.link.delete.label" default="Delete" />
							</a>
						</li>
					</ul>
				</td>
				<td><g:i18n field="${type.names}" /></td>
				<td><g:i18n field="${type.descriptions}" /></td>
				<td>${type.order}</td>
				<td>${type.categories.size()}</td>
			</tr>
		</g:each>
	</tbody>
</table>