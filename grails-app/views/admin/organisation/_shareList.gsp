<%@ page import="org.chai.chwcf.utils.Utils" %>
<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="entity.list.start.date.label" default="Start Date" /></th>
			<th><g:message code="entity.list.end.date.label" default="End Date" /></th>
			<th><g:message code="list.header.share.share.label" default="Share" /></th>
			<th><g:message code="list.heade.share.current.label" default="Current" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="share">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
					<shiro:hasPermission permission="share:edit">
						<li>
							<a class="edit-link" href="${createLinkWithTargetURI(controller: 'share', action:'edit', params:[id: share?.id])}">
								<g:message code="default.link.edit.label" default="Edit" />
							</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission permission="share:delete">
						<li>
							<a class="delete-link" href="${createLinkWithTargetURI(controller: 'share', action:'delete', params:[id: share?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
								<g:message code="default.link.delete.label" default="Delete" />
							</a>
						</li>
					</shiro:hasPermission>
					</ul>
				</td>
				<td>${Utils.formatDate(share?.startDate)}</td>
				<td>${Utils.formatDate(share?.endDate)}</td>
				<td>${share.share}</td>
				<td>${share?.current?'\u2713':'X'}</td>
				<td><g:i18n field="${share.names}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>
