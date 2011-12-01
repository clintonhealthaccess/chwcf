<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="entity.list.name.label" default="Name" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
			<th><g:message code="list.header.activity.number.of.cooperative.label" default="Number of Cooperative With this Activity" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="activity">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			   <td>					
					<ul class="horizontal">
						<shiro:hasPermission permission="activity:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'activity', action:'edit', params:[id: activity?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="activity:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'activity', action:'delete', params:[id: activity?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td><g:i18n field="${activity.names}" /></td>
				<td><g:i18n field="${activity.descriptions}" /></td>
				<td>${activity.cooperatives.size()}</td>
			</tr>
		</g:each>
	</tbody>
</table>
