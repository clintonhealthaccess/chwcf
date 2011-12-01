<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th>Name</th>
			<th>Descriptions</th>
			<th>Number of Cooperatives</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="level">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			    <td>
				    <ul class="horizontal">
				    	<shiro:hasPermission permission="registrationLevel:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'registrationLevel', action:'edit', params:[id: level?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="registrationLevel:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'registrationLevel', action:'delete', params:[id: level?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
			    </td>
				<td><g:i18n field="${level.names}" /></td>
				<td><g:i18n field="${level.descriptions}" /></td>
				<td>${level.cooperatives.size()}</td>
			</tr>
		</g:each>
	</tbody>
</table>
