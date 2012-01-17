<%@ page import="org.chai.chwcf.utils.Utils" %>
<table class="listing">
	<thead>
		<tr>
		 	<th></th>
		    <th><g:message code="list.header.cooperative.health.facility.label" default="Health Facility" /></th>
			<th><g:message code="entity.list.name.label" default="Name" /></th>
			<th><g:message code="entity.list.description.label" default="Description" /></th>
			<th><g:message code="list.header.cooperative.create.date.label" default="Create Date" /></th>
			<th><g:message code="list.header.cooperative.registration.number.label" default="Registration Number" /></th>
			<th><g:message code="list.header.cooperative.registration.level.label" default="Registration Level" /></th>
			<th><g:message code="list.header.cooperative.number.of.villages.label" default="Number Of Villages in Catchment Area" /></th>
			<th><g:message code="list.header.cooperative.number.cells.label" default="Number Of Cells in Catchment Area" /></th>
			<th><g:message code="entity.list.manage.label" default="Manage" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="cooperative">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>					
					<ul class="horizontal">
						<shiro:hasPermission permission="cooperative:edit">
							<li>
								<a class="edit-link" href="${createLinkWithTargetURI(controller: 'cooperative', action:'edit', params:[id: cooperative.id])}">
									<g:message code="default.link.edit.label" default="Edit" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="cooperative:delete">
							<li>
								<a class="delete-link" href="${createLinkWithTargetURI(controller: 'cooperative', action:'delete', params:[id: cooperative.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message', default: 'Are you sure?')}');">
									<g:message code="default.link.delete.label" default="Delete" />
								</a>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission permission="cooperative:view">
							<li>
								<a class="view-link" href="${createLinkWithTargetURI(controller: 'cooperative', action:'view', params:[cooperative: cooperative.id])}">
									<g:message code="default.link.view.label" default="View" />
								</a>
							</li>
						</shiro:hasPermission>
					</ul>
				</td>
				<td>${cooperative.organisationUnit.name}</td>
				<td>${cooperative.name}</td>
				<td>${cooperative.description}</td>
				<td>${Utils.formatDate(cooperative?.createDate)}</td>
				<td>${cooperative.registrationNumber}</td>
				<td><g:i18n field="${cooperative.registrationLevel.names}" /></td>
				<td>${cooperative.numberOfVillages}</td>
				<td>${cooperative.numberOfCells}</td>
				<td>					
					<div class="dropdown subnav-dropdown"> 
						<a class="selected" href="#" data-type="cooperative"><g:message code="entity.list.manage.label" default="Manage"/></a>
						<div class="hidden dropdown-list">
							<ul>
								<shiro:hasPermission permission="transaction:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'transaction', action:'list',params:[cooperative:cooperative.id])}">
											<g:message code="list.manage.transactions.label" default="Transactions" />
										</a>
									</li>
								</shiro:hasPermission>
								<shiro:hasPermission permission="pbfScore:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'pbfScore', action:'list',params:[cooperative:cooperative?.id])}">
											<g:message code="list.manage.pbf.scores.label" default="PBF Scores" />										
										</a>
									</li>
								</shiro:hasPermission>
								<shiro:hasPermission permission="share:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'share', action:'list',params:[cooperative:cooperative?.id])}">
											<g:message code="list.manage.shares.label" default="Shares" />										
										</a>
									</li>
								</shiro:hasPermission>
								<shiro:hasPermission permission="activity:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'activity', action:'list',params:[cooperative:cooperative?.id])}">
											<g:message code="list.manage.activities.label" default="Activities" />
										</a>
									</li>
								</shiro:hasPermission>
								<shiro:hasPermission permission="member:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'member', action:'list',params:[cooperative:cooperative?.id])}">
											<g:message code="list.manage.members.label" default="Members" />
										</a>
									</li>
								</shiro:hasPermission>
								<shiro:hasPermission permission="supervision:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'supervision', action:'list',params:[cooperative:cooperative?.id])}">
											<g:message code="list.manage.supervisions.label" default="Supervisions" />										
										</a>
									</li>
								</shiro:hasPermission>
								<shiro:hasPermission permission="training:list">
									<li>
										<a href="${createLinkWithTargetURI(controller:'training', action:'list',params:[cooperative:cooperative?.id])}">
											<g:message code="list.manage.training.label" default="Training" />	
										</a>
									</li>
								</shiro:hasPermission>
							</ul>
						</div>
					</div>
				</td>
			</tr>
		</g:each>
	</tbody>
</table>
