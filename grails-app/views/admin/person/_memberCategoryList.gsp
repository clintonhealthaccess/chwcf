<table class="listing">
	<thead>
		<tr>
			<th></th>
			<th>Name</th>
			<th>Descriptions</th>
			<th>Order</th>
			<th>Total Number of Member</th>
			<th>Manager</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="category">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
						<shiro:hasPermission permission="category:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'memberCategory', action:'edit', params:[id: category?.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="category:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'memberCategory', action:'delete', params:[id: category?.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td><g:i18n field="${category.names}" /></td>
				<td><g:i18n field="${category.descriptions}" /></td>
				<td>${category.order}</td>
				<td>${category.members.size()}</td>
				<td>					
					<div class="dropdown subnav-dropdown"> 
						<a class="selected" href="#" data-type="cooperative"><g:message code="entity.list.manage.label" default="Manage"/></a>
						<div class="hidden dropdown-list">
							<ul>
								<li>
									<a href="${createLinkWithTargetURI(controller:'member', action:'list',params:[category:category?.id])}">
										Members
									</a>
								</li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
		</g:each>
	</tbody>
</table>