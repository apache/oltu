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
      <div class="page-header"><h2>Web Server Flow <small>Choose Application</small></h2></div>

      <nav class="navbar navbar-default" role="navigation">
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav">
            <li><a href="<c:url value="/main/generic"/>">Generic OAuth2 Application</a></li>
            <li><a href="<c:url value="/main/facebook"/>">Facebook</a></li>
            <li><a href="<c:url value="/main/google"/>">Google</a></li>
            <li><a href="<c:url value="/main/github"/>">Github</a></li>
            <li><a href="<c:url value="/main/linkedin"/>">LinkedIn</a></li>
          	<li><a href="<c:url value="/main/microsoft"/>">Microsoft</a></li>
          	<li><a href="<c:url value="/main/instagram"/>">Instagram</a></li>
          	<li><a href="<c:url value="/main/smart_gallery"/>">Smart Gallery</a></li>
          </ul>
        </div>
      </nav>

      <h2>JWT decoder</h2>

      <c:if test="${!empty oauthParams.errorMessage}">
        <div class="alert alert-danger">${oauthParams.errorMessage}</div>
      </c:if>

      <c:url var="actionUrl" value="/decode"/>
      <form:form role="form" commandName="oauthParams" cssClass="form-horizontal clearfix" action="${actionUrl}">
        <form:textarea path="jwt" id="jwt" rows="15" class="form-control" />
        <form:hidden path="application" />
        <div class="clearfix">&nbsp;<br/></div>
        <input type="submit" class="btn btn-primary pull-right" value="Decode" />
      </form:form>

      <c:if test="${!empty oauthParams.header}">
        <div class="clearfix">&nbsp;<br/>&nbsp;</div>

        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Header</h3>
          </div>
          <div class="panel-body">${oauthParams.header}</div>
        </div>
      </c:if>

      <c:if test="${!empty oauthParams.claimsSet}">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Claims Set</h3>
          </div>
          <div class="panel-body">${oauthParams.claimsSet}</div>
        </div>
      </c:if>

    </div>
  </body>
</html>
