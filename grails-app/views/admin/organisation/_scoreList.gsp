<%@ page import="org.chai.chwcf.utils.Utils" %>
<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="entity.list.start.date.label" default="Start Date" /></th>
			<th><g:message code="entity.list.end.date.label" default="End Date" /></th>
			<th><g:message code="list.header.pbf.score.score.label" default="Score" /></th>
			<th><g:message code="list.header.pbf.score.amount.to.cooperative.label" default="Amount From Health Center to Cooperative" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="score">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
						<shiro:hasPermission permission="pbfScore:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'pbfScore', action:'edit', params:[id: score?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="pbfScore:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'pbfScore', action:'delete', params:[id: score?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td>${Utils.formatDate(score?.startDate)}</td>
				<td>${Utils.formatDate(score?.endDate)}</td>
				<td>${score?.score}%</td>
				<td>${score.amountHCtoCoop}</td>
				<td><g:i18n field="${score.names}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>
