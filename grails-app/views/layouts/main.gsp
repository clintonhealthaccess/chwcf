<%@ page import="org.chai.chwcf.utils.LanguageUtils" %>
<!DOCTYPE html>
<html>
<head>
	<title><g:layoutTitle default="Grails" /></title>
	<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
	<link href="${resource(dir:'css',file:'screen.css')}" type="text/css" rel="stylesheet" media="screen, projection" />

	<g:layoutHead />	
	<r:require module="core"/>
	<r:layoutResources/>
	<ga:trackPageview />
</head>
<body>
  
	<div id="spinner" class="spinner" style="display: none;">
		<img src="${resource(dir:'images',file:'ajax-loader.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
	</div>

	<div id="header">
	  <div class="wrapper">
	    	<h1 id="logo">CHWCF</h1>
			<ul class="locales" id="switcher">
				<g:each in="${LanguageUtils.availableLanguages}" var="language" status="i">
					<% params['lang'] = language %>
					<li><a class="${LanguageUtils.currentLanguage==language?'no-link':''}" href="${createLink(controller: controllerName, action: actionName, params: params)}">${language}</a></li>
				</g:each>
			</ul>	
			<h2>
				<span class="right"><img src="${resource(dir:'images',file:'rwanda.png')}" alt='Rwanda coat of arms' width='33' /></span>
				<span><g:message code="header.labels.moh" default="Rwanda Ministry Of Health"/></span>
				<g:message code="header.labels.chwcf" default="Community Health Worker Cooperative Financial Resource Tracking Database"/>
			</h2>
			<ul id="logout">
					<li>
						<a class="redmine follow" href="${createLink(uri:'/helpdesk')}"><g:message code="header.labels.helpdesk" default="Questions? Call the Helpdesk 114"/></a>
					</li>
					<shiro:user>
						<li>
							<a class="follow" href="${createLink(controller: 'auth', action: 'signOut')}"><g:message code="header.labels.logout" default="Logout"/></a>
						</li>
					</shiro:user>
			</ul>
		</div>	
	</div>
	</div>	

	<div id="navigation">
	  <div class="wrapper">
	  <shiro:user>
  		<ul id="main-menu" class="menu">
  		   	<shiro:hasPermission permission="menu:entry">
  				<li><a href="${createLink(controller: 'cooperative', action: 'list')}"><g:message code="header.menu.main.data.entry.label" default="Data Entry"/></a></li>
  			</shiro:hasPermission>
  			<shiro:hasPermission permission="menu:cooperative">
  				<li><a href="${createLink(controller: 'cooperative', action: 'list')}"><g:message code="header.menu.main.cooperative.label" default="Cooperative"/></a></li>
  			</shiro:hasPermission>
  			<shiro:hasPermission permission="menu:report">
           		<li><a href="${createLink(controller: 'report', action: 'report')}"><g:message code="header.menu.main.reports.label" default="Reports"/></a></li>
           	</shiro:hasPermission>
           	<shiro:hasPermission permission="menu:myaccount">
	           	<li>
	           		<a href="#"><g:message code="header.menu.main.myacount.label" default="My Account"/></a>
	           		<ul class="submenu">
	           			<shiro:hasPermission permission="menu:changePassword">
		            		<li><a href="${createLink(controller: 'user', action: 'newPassword')}"><g:message code="header.menu.sub.change.password.label" default="Change Password"/></a></li>
		            	</shiro:hasPermission>
		            </ul>
	           	</li>
           	</shiro:hasPermission>
           	<shiro:hasPermission permission="menu:admin">
	           	<li>
	           		<a href="#"><g:message code="header.menu.main.administration.label" default="Administration"/></a>
	            	<ul class="submenu">
	            		<shiro:hasPermission permission="menu:activity">
		            		<li><a href="${createLink(controller: 'activity', action: 'list')}"><g:message code="header.menu.sub.activity.label" default="Activity"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:categoryType">
		            		<li><a href="${createLink(controller: 'categoryType', action: 'list')}"><g:message code="header.menu.sub.transaction.category.type.label" default="Transaction Category Type"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:costingType">
		            		<li><a href="${createLink(controller: 'costingType', action: 'list')}"><g:message code="header.menu.sub.costing.type.label" default="Costing Type"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:memberCategory">
		            		<li><a href="${createLink(controller: 'memberCategory', action: 'list')}"><g:message code="header.menu.sub.member.category.label" default="Category of Member"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:registrationLevel">
		            		<li><a href="${createLink(controller: 'registrationLevel', action: 'list')}"><g:message code="header.menu.sub.registration.level.label" default="Registration Level"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:user">
		            		<li><a href="${createLink(controller: 'user', action: 'list')}"><g:message code="header.menu.sub.user.label" default="User"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:role">
		            		<li><a href="${createLink(controller: 'role', action: 'list')}"><g:message code="header.menu.sub.role.label" default="Role"/></a></li>
		            	</shiro:hasPermission>
		            	<shiro:hasPermission permission="menu:permissionHelper">
		            		<li><a href="${createLink(controller: 'permissionHelper', action: 'list')}"><g:message code="header.menu.sub.permission.label" default="Permission"/></a></li>
		            	</shiro:hasPermission>
	            	</ul>
	           	</li>
           	</shiro:hasPermission>
           	
  		</ul>
  		</shiro:user>
  	</div>
	</div>
			
	<div id="content">
	  <div class="wrapper">
			<g:layoutBody />
			<div class=clear></div>
		</div>
	</div>

	<div id="footer">
	  <div class="wrapper">
		  &copy; <g:message code="footer.labels.chai" default="Clinton Health Access Initiative"/> <br />
		  <a href="${createLink(controller:'home', action:'about')}"><g:message code="footer.labels.about" default="About"/></a> | 
		  <a href="${createLink(controller:'home', action:'contact')}"><g:message code="footer.labels.contact" default="Contact"/></a> | 
		  <a href="${createLink(controller:'home', action:'helpdesk')}"><g:message code="footer.labels.helpdesk" default="HelpDesk"/></a>
		</div>
	</div>
	<r:script>
		//Styling the main menu
		$('#main-menu > li').hover(
			function () {
				//show its submenu
				if (!$('ul', this).hasClass('open')) {
					$('ul', this).addClass('open');
					$('ul', this).show();
				}
	 
			}, 
			function () {
				var self = this;
				//hide its submenu
				$('ul', this).slideUp(10, function(){
					$('ul', self).removeClass('open');
				});	
			}
		);
	</r:script>
	<r:layoutResources/>
</body>
</html>