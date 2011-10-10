<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>score</th>
			<th>Amount From HC to Cooperative</th>
			<th>Manage</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="score">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td><g:i18n field="${score.names}" /></td>
				<td><g:i18n field="${score.descriptions}" /></td>
				<td>${score.startDate}</td>
				<td>${score.endDate}</td>
				<td>${score.score}</td>
				<td>${score.amountHCtoCoop}</td>
				<td><a href="#">Manage</a></td>
			</tr>
		</g:each>
	</tbody>
</table>
