<table class="list-table">
	<thead>
		<tr>
		    <th>Health Facility</th>
			<th>Cooperative Name</th>
			<th>Description</th>
			<th>Create Date</th>
			<th>Members Number</th>
			<th>Reg. Number</th>
			<th>Reg. Level</th>
			<th>Manage</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="cooperative">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>${cooperative.organisationUnit.name}</td>
				<td>${cooperative.name}</td>
				<td>${cooperative.description}</td>
				<td>${cooperative.createDate}</td>
				<td>${cooperative.members.size()}</td>
				<td>${cooperative.registrationNumber}</td>
				<td><g:i18n field="${cooperative.registrationLevel.names}" /></td>
				<td>
					<a href="${createLink(controller:'cooperative', action:'create', params:[facilityId: cooperative.organisationUnit?.id])}">
						Manage
					</a>
				</td>
			</tr>
		</g:each>
	</tbody>
</table>
