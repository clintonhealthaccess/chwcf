<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main" />
  <title><g:message code="login.chwcf.login.label" default="CHWCF-Login" /></title>
</head>
<body>
<h3 class="subnav center"><g:message code="login.login.label" default="Log in" /></h3>
  <g:if test="${flash.message}">
    <div class="message login">${flash.message}</div>
  </g:if>
  <g:form action="signIn" class="nice-form">
    <input type="hidden" name="targetUri" value="${targetUri}" />
    <table class="listing login">
      <tbody>
        <tr><td><label class="login-label"><g:message code="login.username.label" default="Username" /></label></td></tr>
        <tr><td><input class="login-field" type="text" name="username" value="${username}" /></td></tr>
        <tr><td><label class="login-label"><g:message code="login.password.label" default="Password" /></label></td></tr>
        <tr><td><input class="login-field" type="password" name="password" value="" /></td></tr>
        <tr><td><g:checkBox name="rememberMe" value="${rememberMe}" /> <label class="login-label"><g:message code="login.remember.me.label" default="Remember me?" /></label></td></tr>
        <tr><td><input type="submit" value="${message(code:'login.sign.in.label', default:'Sign in')}" /></td></tr>
      </tbody>
    </table>
  </g:form>
</body>
</html>
