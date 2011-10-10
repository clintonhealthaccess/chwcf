<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Number of cooperative</th>
			<th>Manage</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="activity">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td><g:i18n field="${activity.names}" /></td>
				<td><g:i18n field="${activity.descriptions}" /></td>
				<td>${activity.cooperatives.size()}</td>
				<td><a href="#">Manage</a></td>
			</tr>
		</g:each>
	</tbody>
</table>
