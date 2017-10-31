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
        <small>Step 3. Get Resource</small></h2>
      </div>

      <c:if test="${!empty oauthParams.errorMessage}">
        <div class="alert alert-danger">${oauthParams.errorMessage}</div>
      </c:if>

      <c:url var="actionUrl" value="/get_resource"/>

      <div class="container">
      <form:form class="form-horizontal clearfix" role="form" commandName="oauthParams" action="${actionUrl}">
        <div class="form-group">
          <label for="resourceUrl" class="col-lg-3 control-label">Resource URL</label>
          <div class="col-lg-9"><form:input class="form-control" id="resourceUrl" path="resourceUrl" /></div>
        </div>
        <div class="form-group">
          <label for="requestType" class="col-lg-3 control-label">Authenticated Request Type</label>
          <div class="col-lg-9">
            <form:select class="form-control" id="requestType" path="requestType">
              <form:option value="headerField" />
              <form:option value="queryParameter" />
              <form:option value="bodyParameter" />
            </form:select>
          </div>
        </div>
        <div class="form-group">
          <label for="requestMethod" class="col-lg-3 control-label">Request Method</label>
          <div class="col-lg-9">
            <form:select class="form-control" id="requestMethod" path="requestMethod">
                <form:option value="GET" />
                <form:option value="POST" />
            </form:select>
          </div>
        </div>
        <div class="form-group">
          <label for="accessToken" class="col-lg-3 control-label">Access Token</label>
          <div class="col-lg-9"><form:input class="form-control" id="accessToken" path="accessToken" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="expiresIn" class="col-lg-3 control-label">Expires In</label>
          <div class="col-lg-9"><form:input class="form-control" id="expiresIn" path="expiresIn" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="refreshToken" class="col-lg-3 control-label">Refresh Token</label>
          <div class="col-lg-9"><form:input class="form-control" id="refreshToken" path="refreshToken" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="scope" class="col-lg-3 control-label">Requested Access Scope</label>
          <div class="col-lg-9"><form:input class="form-control" id="scope" path="scope" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="authzEndpoint" class="col-lg-3 control-label">End-User Authorization URL</label>
          <div class="col-lg-9"><form:input class="form-control" id="authzEndpoint" path="authzEndpoint" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="tokenEndpoint" class="col-lg-3 control-label">Token Endpoint</label>
          <div class="col-lg-9"><form:input class="form-control" id="tokenEndpoint" path="tokenEndpoint" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="clientId" class="col-lg-3 control-label">Client ID</label>
          <div class="col-lg-9"><form:input class="form-control" id="clientId" path="clientId" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="clientSecret" class="col-lg-3 control-label">Client Secret</label>
          <div class="col-lg-9"><form:input class="form-control" id="clientSecret" path="clientSecret" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="redirectUri" class="col-lg-3 control-label">Redirect URI</label>
          <div class="col-lg-9"><form:input class="form-control" id="redirectUri" path="redirectUri" readonly="readonly" /></div>
        </div>
        <div class="form-group">
          <label for="state" class="col-lg-3 control-label">Client State</label>
          <div class="col-lg-9"><form:input class="form-control" id="state" path="state" readonly="readonly" /></div>
        </div>

        <form:hidden path="application" />
        <input type="submit" class="btn btn-primary pull-right" value="Get Resource" />
      </form:form>
      </div>

      <div class="container">

        <c:if test="${!oauthParams.idTokenValid}">
          <div class="panel panel-danger">
            <div class="panel-heading">
              <h3 class="panel-title">OpenId Connect</h3>
            </div>
            <div class="panel-body">ID Token is NOT valid</div>
          </div>
        </c:if>

        <c:if test="${oauthParams.idTokenValid}">
          <div class="panel panel-success">
            <div class="panel-heading">
              <h3 class="panel-title">OpenId Connect</h3>
            </div>
            <div class="panel-body">ID Token is valid</div>
          </div>
        </c:if>

        <c:if test="${!empty oauthParams.header}">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h3 class="panel-title">Header</h3>
            </div>
            <div class="panel-body"><pre><c:out value="${oauthParams.header}"/></pre></div>
          </div>
        </c:if>

        <c:if test="${!empty oauthParams.claimsSet}">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h3 class="panel-title">Claims Set</h3>
            </div>
            <div class="panel-body"><pre><c:out value="${oauthParams.claimsSet}"/></pre></div>
          </div>
        </c:if>
      </div>

    </div>
  </body>

</html>
