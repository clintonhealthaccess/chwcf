<%@ page import="org.chai.chwcf.utils.Utils" %>
<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th>Date</th>
			<th>Supervisor</th>
			<th>From</th>
			<th>Location</th>
			<th>Descriptions</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="supervision">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
					 	<shiro:hasPermission permission="supervision:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'supervision', action:'edit', params:[id: supervision?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
					 	<shiro:hasPermission permission="supervision:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'supervision', action:'delete', params:[id: supervision?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td>${Utils.formatDate(supervision?.date)}</td>
				<td>${supervision?.supervisor}</td>
				<td>${supervision?.source}</td>
				<td>${supervision?.location}</td>
				<td><g:i18n field="${supervision.descriptions}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>
