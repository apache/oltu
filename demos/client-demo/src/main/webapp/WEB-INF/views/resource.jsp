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
        <small>Your Resource</small></h2>
      </div>

      <c:if test="${!empty oauthParams.errorMessage}">
      <div class="alert alert-danger">${oauthParams.errorMessage}</div>
      </c:if>

      <pre><c:out value="${oauthParams.resource}"/></pre>

      <hr/>
      <a class="btn btn-default" role="button" href="<c:url value="/"/>">Restart flow</a>
    </div>
  </body>
</html>
