<table class="listing">
	<thead>
		<tr>
		    <th></th>
			<th>Family Name</th>
			<th>Other Names</th>
			<th>Gender</th>
			<th>Date of Birth</th>
			<th>National ID Number</th>
			<th>Phone Number</th>
			<th>Email</th>
			<th>Join Date</th>
			<th>Active</th>
			<th>Left Date</th>
			<th>Category</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="member">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			  <td>
				<ul class="horizontal">
					<shiro:hasPermission permission="member:edit">
						<li>
							<a class="edit-link" href="${createLinkWithTargetURI(controller: 'member', action:'edit', params:[id: member?.id])}">
								<g:message code="default.link.edit.label" default="Edit" />
							</a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission permission="member:delete">
						<li>
							<a class="delete-link" href="${createLinkWithTargetURI(controller: 'member', action:'delete', params:[id: member?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
								<g:message code="default.link.delete.label" default="Delete" />
							</a>
						</li>
					</shiro:hasPermission>
					</ul>
				</td>
				<td>${member.familyName}</td>
				<td>${member.otherNames}</td>
				<td>${member.gender}</td>
				<td>${member.dob}</td>
				<td>${member.idNumber}</td>
				<td>${member.phoneNumber}</td>
				<td>${member.email}</td>
				<td>${member.joinDate}</td>
				<td>${member?.active?'\u2713':'X'}</td>
				<td>${member.leftDate}</td>
				<td><g:i18n field="${member.category.names}" /></td>
			</tr>
		</g:each>
	</tbody>
</table>