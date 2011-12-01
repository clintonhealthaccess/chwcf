<table class="listing">
	<thead>
		<tr>
		    <th></th>
			<th>UserName</th>
			<th>Email</th>
			<th>Roles</th>
			<th>Active</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="user">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			  <td>
				<ul class="horizontal">
						<li>
							<a class="edit-link" href="${createLinkWithTargetURI(controller: 'user', action:'edit', params:[id: user?.id])}">
								<g:message code="default.link.edit.label" default="Edit" />
							</a>
						</li>
						<li>
							<a class="delete-link" href="${createLinkWithTargetURI(controller: 'user', action:'delete', params:[id: user?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
								<g:message code="default.link.delete.label" default="Delete" />
							</a>
						</li>
					</ul>
				</td>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>
					<g:each in="${user.roles}" var="role">
						${role.name}
					</g:each>
				</td>
				<td>${user?.active?'\u2713':'X'}</td>			
			</tr>
		</g:each>
	</tbody>
</table>