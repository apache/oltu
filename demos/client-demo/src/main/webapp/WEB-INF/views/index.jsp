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
    <link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <title>Apache Oltu - OAuth V2.0 Client Application</title>
  </head>

  <body>
    <div class="container">
      <h1>Apache Oltu - Sample OAuth V2.0 Client Application</h1>

      <h2>Web Server Flow</h2>

      <h3>Choose Application</h3>

      <nav class="navbar navbar-default" role="navigation">
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav">
            <li><a href="/main/generic">Generic OAuth2 Application</a></li>
            <li><a href="/main/smart_gallery">Smart Gallery</a></li>
            <li><a href="/main/facebook">Facebook</a></li>
            <li><a href="/main/google">Google</a></li>
            <li><a href="/main/github">Github</a></li>
            <li><a href="/main/linkedin">LinkedIn</a></li>
          </ul>
        </div>
      </nav>

      <h2>JWT decoder</h2>

      <c:if test="${!empty oauthParams.errorMessage}">
        <div class="alert alert-danger">${oauthParams.errorMessage}</div>
      </c:if>

      <form:form commandName="oauthParams" action="/decode">
        <table class="table table-striped">
          <tr>
            <td>JWT</td>
            <td><form:textarea rows="15" cols="80" path="jwt" /></td>
          </tr>
          <tr>
            <td colspan="2">
              <form:hidden path="application" />
              <input type="submit" class="btn btn-primary" value="Decode" />
            </td>
          </tr>
          <tr>
            <td>Header:</td>
            <td><textarea rows="15" cols="80" disabled="true">${oauthParams.header}</textarea></td>
          </tr>
          <tr>
            <td>Claims Set:</td>
            <td><textarea rows="15" cols="80" disabled="true">${oauthParams.claimsSet}</textarea></td>
          </tr>
        </table>
      </form:form>

    </div>
  </body>
</html>
