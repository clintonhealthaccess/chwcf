<table class="listing">
	<thead>
		<tr>
		    <th></th>
			<th>Name</th>
			<th>Users</th>
			<th>Permissions</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="role">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				  <td>
					<ul class="horizontal">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'role', action:'edit', params:[id: role?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'role', action:'delete', params:[id: role?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</ul>
					</td>
					<td>${role.name}</td>
					<td>
						<g:each in="${role.users}" var="user">
							${user.username}
						</g:each>
					</td>
					<td>
						<g:each in="${role.permissions}" var="permission">
							${permission}
						</g:each>
					</td>		
				</tr>
		</g:each>
	</tbody>
</table>