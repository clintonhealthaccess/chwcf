<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Number of Districts</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="region">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>${region.name}</td>
				<td><a href="${createLink(controller:'admin', action:'districts', params:[regionId:region?.id])}">
						${region.children.size()} 
					</a>
			    </td>
			</tr>
		</g:each>
	</tbody>
</table>