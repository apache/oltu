<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>Consent</title>
  <!-- Le styles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/client/css/bootstrap.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/client/css/style.css" />

  <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->
</head>
<body>

<div class="head">
  <img src="${pageContext.request.contextPath}/client/img/logo_oltu.png" width="100" height="100" />
</div>
<div class="main">
  <div class="full">

    <div class="page-header">
      <h1>The web app <strong><%= request.getParameter("client_id") %></strong> wants to retrieve data
      	(<strong> User Info </strong>)
        from the resource server (<strong> idQ Server </strong>)</h1>
    </div>

    <div class="consent">
      <img alt="${client.name}" title="${client.name}" src="${pageContext.request.contextPath}/client/img/logo_client.png" />
      <img src="${pageContext.request.contextPath}/client/img/arrow.png" />
      <img alt="${client.resourceServer.name}"
        title="${client.resourceServer.name}"
        src="${pageContext.request.contextPath}/client/img/logo_idq.png" />
    </div>

    <form id="accept" method="get" action="${actionUri}">

      <input type="hidden" name="scope" value="<%= request.getParameter("scope") %>" />
      <input type="hidden" name="response_type" value="<%= request.getParameter("response_type") %>" />
      <input type="hidden" name="redirect_uri" value="<%= request.getParameter("redirect_uri") %>" />
      <input type="hidden" name="state" value="<%= request.getParameter("state") %>" />
      <input type="hidden" name="client_id" value="<%= request.getParameter("client_id") %>" />
      <input type="hidden" name="username" value="<%= request.getParameter("username") %>" />
      <input type="hidden" name="password" value="<%= request.getParameter("password") %>" />

      <h2>This data will be shared</h2>

      <fieldset>
        <c:forEach items="${client.scopes}" var="availableScope">
          <c:set var="checked" value="" />
          <c:forEach var="requestedScope" items="${requestedScopes}">
            <c:if test="${requestedScope eq availableScope}">
              <c:set var="checked" value="CHECKED" />
            </c:if>
          </c:forEach>
          <input type="checkbox" id="GRANTED_SCOPES" name="GRANTED_SCOPES" <c:out value="${checked}"/>
                 value="${availableScope}"/>
          <span class="consent-label">${availableScope}</span><br/>
        </c:forEach>
      </fieldset>
	  
      <fieldset>
        <div class="form-actions">
          <button id="user_oauth_approval" name="user_oauth_approval" value="true" type="submit"
                  class="btn btn-success">Grant permission</button>
          &nbsp;&nbsp;&nbsp;<em>or</em>&nbsp;&nbsp;&nbsp;
          <button type="submit" name="user_oauth_approval" value="false"
                  class="btn btn-danger">Deny permission</button>
        </div>
      </fieldset>
    
	</form>
	
  </div>
</div>

</body>
</html>