<table class="listing">
	<thead>
		<tr>
		    <th></th>
			<th>Permission</th>
			<th>Description</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="permission">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			  <td>
				<ul class="horizontal">
					<shiro:hasPermission permission="permissionHelper:edit">
						<li>
							<a class="edit-link" href="${createLinkWithTargetURI(controller: 'permissionHelper', action:'edit', params:[id: permission?.id])}">
								<g:message code="default.link.edit.label" default="Edit" />
							</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission permission="permissionHelper:delete">
						<li>
							<a class="delete-link" href="${createLinkWithTargetURI(controller: 'permissionHelper', action:'delete', params:[id: permission?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
								<g:message code="default.link.delete.label" default="Delete" />
							</a>
						</li>
					</shiro:hasPermission>
					</ul>
				</td>
				<td>${permission.permission}</td>
				<td>${permission.description}</td>		
			</tr>
		</g:each>
	</tbody>
</table>