<%@ page import="org.chai.chwcf.utils.Utils" %>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title><g:message code="admin.Page.label" default="CHWCF-Resource Tracking Database" /></title>	
		<r:require modules="form,datepicker"/>
</head>
<body>
  	<div>
 		<div class="entity-form-header">
			<h3 class="title"><g:message code="admin.cooperative.description.label" default="Cooperative Description"/></h3>
			<div class="clear"></div>
		</div>
		<div class="main">
			<div class="cooperative-identifier">
				<h6>Identification</h6>
				<div class="left-coop-id"><label>Service Name:</label> ${cooperative.serviceName}</div>
				<div class="right-coop-id"><label>Facility:</label> ${cooperative.organisationUnit.name}</div>
				<div class="left-coop-id"><label>Commercial Name:</label> ${cooperative.commercialName}</div>
				<div class="right-coop-id"><label>Created On:</label> ${cooperative.createDate}</div>
				<div class="left-coop-id"><label>RegistrationNumber:</label> ${cooperative.registrationNumber}</div>
				<div class="right-coop-id"><label>Description:</label> ${cooperative.description}</div>
				<div class="left-coop-id"><label>Registration Level:</label> <g:i18n field="${cooperative.registrationLevel.names}" /></div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="cooperative-identifier-list">
				<h6>Activities (${cooperative.activities.size()})</h6>
				<g:if test="${!cooperative.activities.isEmpty()}">
					<table class="listing">
						<thead>
							<tr>
								<th>Name</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${cooperative.activities}" status="i" var="activity">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
									<td><g:i18n field="${activity.names}" /></td>
									<td><g:i18n field="${activity.descriptions}" /></td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</g:if>
				<g:else>
					<div class="list-is-empty">There is no Activity associated to this cooperative</div>
				</g:else>
			</div>
			<div class="cooperative-identifier-list">
				<h6>PBF Scores (${cooperative.scores.size()})</h6>
				<g:if test="${!cooperative.scores.isEmpty()}">
					<table class="listing">
						<thead>
							<tr>
								<th>Type</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Score</th>
								<th>Amount From HC to Cooperative</th>
								<th>Description</th>
								
							</tr>
						</thead>
						<tbody>
							<g:each in="${cooperative.scores}" status="i" var="pbf">
								<tr>
									<td><g:i18n field="${pbf.type.names}" /></td>
									<td>${Utils.formatDate(pbf?.startDate)}</td>
									<td>${Utils.formatDate(pbf?.endDate)}</td>
									<td>${pbf.amoutMohToHC}</td>
									<td>${pbf.amountSousCompte}</td>
									<td>${pbf.amountHCtoCoop}</td>
									
								</tr>
							</g:each>
						</tbody>
					</table>
				</g:if>
				<g:else>
					<div class="list-is-empty">There is no PBF Score associated to this cooperative</div>
				</g:else>
			</div>
			<div class="cooperative-identifier-list">
				<h6>Shares (${cooperative.shares.size()})</h6>
				<g:if test="${!cooperative.shares.isEmpty()}">
					<table class="listing">
						<thead>
							<tr>
								<th>Year</th>
								<th>Share</th>
								<th>Current</th>
								<th>Descriptions</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${cooperative.shares}" status="i" var="share">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
									<td>${Utils.formatDateYear(share?.year)}</td>
									<td>${share.share}</td>
									<td>${share?.current?'\u2713':'X'}</td>
									<td><g:i18n field="${share.descriptions}" /></td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</g:if>
				<g:else>
					<div class="list-is-empty">There is no Shares associated to this cooperative</div>
				</g:else>
			</div>
			<div class="cooperative-identifier-list">
				<h6>Supervisions (${cooperative.supervisions.size()})</h6>
				<g:if test="${!cooperative.supervisions.isEmpty()}">
					<table class="listing">
						<thead>
							<tr>
								<th>Date</th>
								<th>Supervisor</th>
								<th>From</th>
								<th>Location</th>
								<th>Descriptions</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${cooperative.supervisions}" status="i" var="supervision">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
									<td>${Utils.formatDate(supervision?.date)}</td>
									<td>${supervision?.supervisor}</td>
									<td>${supervision?.source}</td>
									<td>${supervision?.location}</td>
									<td><g:i18n field="${supervision.descriptions}" /></td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</g:if>
				<g:else>
					<div class="list-is-empty">There is no Supervisions associated with this cooperative</div>
				</g:else>
			</div>
			<div class="cooperative-identifier-list">
				<h6>Trainings (${cooperative.trainings.size()})</h6>
				<g:if test="${!cooperative.trainings.isEmpty()}">
					<table class="listing">
						<thead>
							<tr>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Trainer</th>
								<th>Location</th>
								<th>Descriptions</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${cooperative.trainings}" status="i" var="training">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
									<td>${Utils.formatDate(training?.startDate)}</td>
									<td>${Utils.formatDate(training?.endDate)}</td>
									<td>${training?.provider}</td>
									<td>${training?.location}</td>
									<td><g:i18n field="${training.descriptions}" /></td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</g:if>
				<g:else>
					<div class="list-is-empty">There is no Trainings associated with this cooperative</div>
				</g:else>
			</div>
			<div class="cooperative-identifier-list">
				<h6>Members (${cooperative.members.size()})</h6>
				<g:if test="${!cooperative.members.isEmpty()}">
					<table class="listing">
						<thead>
							<tr>
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
							<g:each in="${cooperative.members}" status="i" var="member">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
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
				</g:if>
				<g:else>
					<div class="list-is-empty">There is no Members associated with this cooperative</div>
				</g:else>
			</div>
		</div>
	</div>
</body>
</html>
<html>