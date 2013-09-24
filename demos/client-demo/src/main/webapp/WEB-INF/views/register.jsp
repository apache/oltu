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
    <link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <title>Apache Oltu - OAuth V2.0 Client Application</title>
  </head>

  <body>
    <div class="container">
      <h1>Apache Oltu - Sample OAuth V2.0 Client Application</h1>

      <h2>Dynamic Registration</h2>

      <h3>Step 0. Dynamically Register Application</h3>

      <c:if test="${!empty oauthRegParams.errorMessage}">
      <div class="alert alert-danger">${oauthRegParams.errorMessage}</div>
      </c:if>

      <form:form commandName="oauthRegParams" action="/register">
        <table class="table table-striped">
          <tr>
            <td>Required OAuth Dynamic Registration parameters:</td>
          </tr>
          <tr>
            <td>Registration Type:</td>
            <td>
              <form:select path="registrationType">
                <form:option value="push"/>
                <form:option value="pull"/>
              </form:select>
            </td>
          </tr>
          <tr>
            <td>Application Name:</td>
            <td><form:input size="70" path="name" /></td>
          </tr>
          <tr>
            <td>Application URL:</td>
            <td><form:input size="70" path="url" /></td>
          </tr>
          <tr>
            <td>Application Description:</td>
            <td><form:input size="70" path="description" /></td>
          </tr>
          <tr>
            <td>Application Redirect URI:</td>
            <td><form:input size="70" path="redirectUri" /></td>
          </tr>
          <tr>
            <td>Application Icon URL:</td>
            <td><form:input size="70" path="icon" /></td>
          </tr>
          <tr>
            <td>OAuth Registration Endpoint:</td>
            <td><form:input size="70" path="registrationEndpoint" /></td>
          </tr>
          <tr>
            <td colspan="2">
              <form:hidden path="authzEndpoint" />
              <form:hidden path="tokenEndpoint" />
              <form:hidden path="application" />
              <input type="submit" class="btn btn-primary" value="Register Application" />
            </td>
          </tr>
        </table>
      </form:form>

    </div>
  </body>
</html>
