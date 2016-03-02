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
<%--@elvariable id="oauthRegParams" type="org.apache.oltu.oauth2.client.demo.model.OAuthRegParams"--%>

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
        <h2>Dynamic Registration
        <small>Step 0. Dynamically Register Application</small></h2>
      </div>

      <c:if test="${!empty oauthRegParams.errorMessage}">
      <div class="alert alert-danger">${oauthRegParams.errorMessage}</div>
      </c:if>

      <c:url var="actionUrl" value="/register"/>
      <form:form class="form-horizontal" role="form" commandName="oauthRegParams" action="${actionUrl}">
        <div class="form-group">
          <label for="registrationType" class="col-lg-3 control-label">Registration Type</label>
          <div class="col-lg-9">
            <form:select class="form-control" id="registrationType" path="registrationType">
              <form:option value="push"/>
              <form:option value="pull"/>
            </form:select>
          </div>
        </div>
        <div class="form-group">
          <label for="name" class="col-lg-3 control-label">Application Name</label>
          <div class="col-lg-9"><form:input class="form-control" id="name" path="name" /></div>
        </div>
        <div class="form-group">
          <label for="url" class="col-lg-3 control-label">Application URL</label>
          <div class="col-lg-9"><form:input class="form-control" id="url" path="url" /></div>
        </div>
        <div class="form-group">
          <label for="description" class="col-lg-3 control-label">Application Description</label>
          <div class="col-lg-9"><form:input class="form-control" id="description" path="description" /></div>
        </div>
        <div class="form-group">
          <label for="redirectUri" class="col-lg-3 control-label">Application Redirect URI</label>
          <div class="col-lg-9"><form:input class="form-control" id="redirectUri" path="redirectUri" /></div>
        </div>
        <div class="form-group">
          <label for="icon" class="col-lg-3 control-label">Application Icon URL</label>
          <div class="col-lg-9"><form:input class="form-control" id="icon" path="icon" /></div>
        </div>
        <div class="form-group">
          <label for="registrationEndpoint" class="col-lg-3 control-label">OAuth Registration Endpoint</label>
          <div class="col-lg-9"><form:input class="form-control" id="registrationEndpoint" path="registrationEndpoint" /></div>
        </div>

        <form:hidden path="authzEndpoint" />
        <form:hidden path="tokenEndpoint" />
        <form:hidden path="application" />
        <input type="submit" class="btn btn-primary pull-right" value="Register Application" />
      </form:form>

    </div>
  </body>
</html>
