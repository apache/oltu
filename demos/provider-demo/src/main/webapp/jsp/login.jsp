<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="description" content="" />
  <meta name="author" content="" />

  <title>Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/client/css/bootstrap.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/client/css/style.css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/client/js/lib/jquery.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/client/js/lib/bootstrap.min.js"></script>
</head>

<body>

<div class="head">
  <img src="${pageContext.request.contextPath}/client/img/logo_oltu.png"/>
</div>

<div class="main">
  <div class="full">
    <div class="page-header">
      <h1>Login</h1>
    </div>

    <form class="form-horizontal" id="UserAuthorization" method="get"
      action="${actionUri}">
      <fieldset>
        <div class="control-group">
          <label class="control-label">Username</label>
          <div class="controls">
            <input type="text" class="input-xlarge" id="username"
              name="username" rel="popover"
              data-content="Enter your username."
              data-original-title="Username" />
            <p class="help-block">Hint: Resource Owner's Username</p>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">Password</label>
          <div class="controls">
            <input type="password" class="input-xlarge" id="password"
              name="password" rel="popover"
              data-content="Enter your password."
              data-original-title="Password" />
            <p class="help-block">Hint: Resource Owner's Password</p>
          </div>
        </div>
        <input type="hidden" name="scope" value="<%= request.getParameter("scope") %>" />
        <input type="hidden" name="response_type" value="<%= request.getParameter("response_type") %>" />
        <input type="hidden" name="redirect_uri" value="<%= request.getParameter("redirect_uri") %>" />
        <input type="hidden" name="state" value="<%= request.getParameter("state") %>" />
        <input type="hidden" name="client_id" value="<%= request.getParameter("client_id") %>" />
      </fieldset>

      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Login</button>
      </div>
    </form>
  </div>
</div>

</body>
</html>