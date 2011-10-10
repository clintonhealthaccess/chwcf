<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Number of Cooperatives</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="district">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>${district.name}</td>
				<td>
					<a href="${createLink(controller:'cooperative', action:'list', params:[districtId:district?.id])}">
				   		${district.children.size()}
				   	</a>
			   </td>
			</tr>
		</g:each>
	</tbody>
</table>