<%@ page import="org.chai.chwcf.utils.Utils" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title><g:message code="cooperative.summaryPage.label" default="CHWCF Resource Tracking Database" /></title>
		<r:require module="dropdown"/>
	</head>
	<body>
		<div id="entities">
			<g:if test="${showLocation}">
				<div id="cooperative-header" class="subnav">
						<g:render template="/templates/organisationFilter" model="[currentOrganisation: organisation,linkParams:[cooperative: cooperative?.id]]"/>
					<div class="clear"></div>
				</div>
			</g:if>
		    <g:if test="${!showLocation || organisation?.level==districtLevel}">
		    	<g:if test="${!showLocation}">
				    <div class="subnav">
				    	<a href="${createLink(uri: targetURI)}">
							<g:message code="default.link.back.label" default="Back" />
						</a>
				    </div>
			    </g:if>
			   <g:if test="${!hideNewBar}">
		    	<h5 class="subnav">
			     	<g:message code="default.list.label" args="[message(code:code)]" />
			     	<shiro:hasPermission permission="${params['controller']}:create">
				     	<span class="right">
			  				<a href="${createLinkWithTargetURI(controller: params['controller'], action:'create', params: params)}">
			  					<g:message code="default.new.label" args="[message(code:code)]"/>
			  				</a>
				     	</span>
			     	</shiro:hasPermission>
				</h5>
				</g:if>
		    </g:if>
			<div class="main">
				<g:if test="${showLocation && organisation == null}">
					<p class="help"><g:message code="list.message.to.see.list.of.cooperative.text" default="Please select a district to see a list of cooperative."/></p>
				</g:if>
				<g:else>
					<g:if test="${!entities.isEmpty()}">
					 	<g:render template="/admin/${template}" model="[currentOrganisation: organisation,entities: entities,template: template,linkParams:[cooperative: cooperative?.id]]"/>
						<div class="paginateButtons main">
							<g:paginate total="${entityCount}" params="${params}" action="${actionName}"/>
						</div>
					</g:if>
					<g:else>
						<div><g:message code="list.is.empty" default="List is Empty"/></div>
					</g:else>
				</g:else>
			</div>
		</div>
	</body>
</html>