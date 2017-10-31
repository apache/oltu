<%--

          Copyright 2010 Newcastle University

             http://research.ncl.ac.uk/smart/

    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with
    this work for additional information regarding copyright ownership.
    The ASF licenses this file to You under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with
    the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%--@elvariable id="oauthParams" type="org.apache.oltu.oauth2.client.demo.model.OAuthParams"--%>

<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
    <title>Apache Oltu - OAuth V2.0 Client Application</title>
  </head>

  <body>
    <jsp:include page="components/_header.jsp"/>

    <div class="container">
      <div class="page-header">
        <h2>Web Server Flow
        <small>Step 1. Get User's Authorization, specify required OAuth parameters</small></h2>
      </div>

      <c:if test="${!empty oauthParams.errorMessage}">
        <div class="alert alert-danger">Error: ${oauthParams.errorMessage}</div>
      </c:if>

      <c:url var="actionUrl" value="/authorize"/>
      <form:form class="form-horizontal clearfix" role="form" commandName="oauthParams" action="${actionUrl}">
        <div class="form-group">
          <label for="scope" class="col-lg-3 control-label">Requested Access Scope</label>
          <div class="col-lg-9"><form:input class="form-control" id="scope" path="scope" /></div>
        </div>
        <div class="form-group">
          <label for="authzEndpoint" class="col-lg-3 control-label">End-User Authorization URL</label>
          <div class="col-lg-9"><form:input class="form-control" id="authzEndpoint" path="authzEndpoint" /></div>
        </div>
        <div class="form-group">
          <label for="tokenEndpoint" class="col-lg-3 control-label">Token Endpoint</label>
          <div class="col-lg-9"><form:input class="form-control" id="tokenEndpoint" path="tokenEndpoint" /></div>
        </div>
        <div class="form-group">
          <label for="clientId" class="col-lg-3 control-label">Client ID</label>
          <div class="col-lg-9"><form:input class="form-control" id="clientId" path="clientId" /></div>
        </div>
        <div class="form-group">
          <label for="clientSecret" class="col-lg-3 control-label">Client Secret</label>
          <div class="col-lg-9"><form:input class="form-control" id="clientSecret" path="clientSecret" /></div>
        </div>
        <div class="form-group">
          <label for="redirectUri" class="col-lg-3 control-label">Redirect URI</label>
          <div class="col-lg-9"><form:input class="form-control" id="redirectUri" path="redirectUri" /></div>
        </div>
        <div class="form-group">
          <label for="state" class="col-lg-3 control-label">Client State</label>
          <div class="col-lg-9"><form:input class="form-control" id="state" path="state" /></div>
        </div>

        <form:hidden path="application" />
        <input type="submit" class="btn btn-primary pull-right" value="Get Authorization" />
      </form:form>

    </div>
  </body>

</html>
