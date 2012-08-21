<%@ page import="org.chai.chwcf.utils.Utils" %>
<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="list.header.pbf.type.label" default="Type" /></th>
			<th><g:message code="entity.list.period.label" default="Period" /></th>
			<th><g:message code="list.header.pbf.amount.to.cooperative.label" default="Amount From MoH To Health Center" /></th>
			<th><g:message code="list.header.pbf.amount.to.cooperative.label" default="Amount Sous Compte" /></th>
			<th><g:message code="list.header.pbf.amount.to.cooperative.label" default="Amount From Health Center to Cooperative" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="pbf">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
						<shiro:hasPermission permission="pbf:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'pbf', action:'edit', params:[id: pbf.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="pbf:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'pbf', action:'delete', params:[id: pbf?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td><g:i18n field="${pbf.type.names}" /></td>
				<td>${Utils.formatDate(pbf?.startDate)} - ${Utils.formatDate(pbf?.endDate)}</td>
				<td>${pbf.amoutMohToHC}</td>
				<td>${pbf.amountSousCompte}</td>
				<td>${pbf.amountHCtoCoop}</td>
				<td><g:i18n field="${pbf.descriptions}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>
