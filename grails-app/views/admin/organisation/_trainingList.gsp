<%@ page import="org.chai.chwcf.utils.Utils" %>
<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Trainer</th>
			<th>Location</th>
			<th>Descriptions</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="training">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
				 		<shiro:hasPermission permission="training:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'training', action:'edit', params:[id: training?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="training:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'training', action:'delete', params:[id: training?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td>${Utils.formatDate(training?.startDate)}</td>
				<td>${Utils.formatDate(training?.endDate)}</td>
				<td>${training?.provider}</td>
				<td>${training?.location}</td>
				<td><g:i18n field="${training.descriptions}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>
