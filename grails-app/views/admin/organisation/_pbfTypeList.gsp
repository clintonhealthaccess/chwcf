<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th>Name</th>
			<th>Descriptions</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="type">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			    <td>
				    <ul class="horizontal">
				    	<shiro:hasPermission permission="pbfType:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'pbfType', action:'edit', params:[id: type?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="pbfType:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'pbfType', action:'delete', params:[id: type?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
			    </td>
				<td><g:i18n field="${type.names}" /></td>
				<td><g:i18n field="${type.descriptions}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>
