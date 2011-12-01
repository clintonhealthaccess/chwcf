<%@ page import="org.chai.chwcf.utils.Utils" %>
<%@ page import="org.chai.chwcf.security.User" %>
<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th><g:message code="list.header.transaction.cooperative.label" default="Cooperative" /></th>
			<th><g:message code="list.header.transaction.category.label" default="Category" /></th>
			<th><g:message code="list.header.transaction.transaction.date.label" default="Transaction Date" /></th>
			<th><g:message code="list.header.transaction.recorded.date.label" default="Recorded Date" /></th>
			<th><g:message code="list.header.transaction.amount.label" default="Amount" /></th>
			<th><g:message code="list.header.transaction.description.label" default="Description" /></th>
			<th><g:message code="list.header.transaction.recorded.by.label" default="Recorded By" /></th>
			<th><g:message code="list.header.transaction.approved.label" default="Approved" /></th>
			<th><g:message code="list.header.transaction.approved.by.label" default="Approved By" /></th>
			<th><g:message code="list.header.transaction.approved.date.label" default="Approved Date" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="transaction">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<shiro:hasPermission permission="transaction:edit">
						<li>
							<a class="edit-link" href="${createLinkWithTargetURI(controller: 'transaction', action:'edit', params:[id: transaction?.id])}">
								<g:message code="default.link.edit.label" default="Edit" />
							</a>
						</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="transaction:delete">
						<li>
							<a class="delete-link" href="${createLinkWithTargetURI(controller: 'transaction', action:'delete', params:[id: transaction?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
								<g:message code="default.link.delete.label" default="Delete" />
							</a>
						</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td>${transaction.cooperative.name}</td>
				<td><g:i18n field="${transaction.category.names}" /></td>
				<td>${Utils.formatDate(transaction?.transactionDate)}</td>
				<td>${Utils.formatDate(transaction?.recordedDate)}</td>
				<td>${transaction.amount}</td>
				<td>${transaction.description}</td>
				<td>${User.get(transaction?.enteredBy).username}</td>
				<td>${transaction?.approval?'\u2713':'X'}</td>
				<td>${(transaction.validatedBy)?User.get(transaction?.validatedBy).username:'-'}</td>
				<td>${Utils.formatDate(transaction?.approvalDate)}</td>
			</tr>
		</g:each>
	</tbody>
</table>