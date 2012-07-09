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
<%--@elvariable id="oauthParams" type="org.apache.amber.oauth2.client.demo.model.OAuthParams"--%>

<html>
<head>
    <title>OAuth V2.0 Client Application</title>
</head>

<body>
<h1>Sample OAuth V2.0 Client Application</h1>

<h2>Web Server Flow</h2>

<h3>Step 1. Get User's Authorization</h3></p>

<c:if test="${!empty oauthParams.errorMessage}">
    <font color="red"><p>Error: ${oauthParams.errorMessage}</p></font>
</c:if>

<form:form commandName="oauthParams" action="/authorize">
    <table>
        <tr>
            <td>Required OAuth parameters:</td>
        </tr>
        <tr>
            <td>Requested Access Scope</td>
            <td><form:input size="70" path="scope"/></td>
        </tr>
        <tr>
            <td>End-User Authorization URL:</td>
            <td><form:input size="70" path="authzEndpoint"/></td>
        </tr>
        <tr>
            <td>Token Endpoint:</td>
            <td><form:input size="70" path="tokenEndpoint"/></td>
        </tr>
        <tr>
            <td>Client ID:</td>
            <td><form:input size="70" path="clientId"/></td>
        </tr>
        <tr>
            <td>Client Secret:</td>
            <td><form:input size="70" path="clientSecret"/></td>
        </tr>
        <tr>
            <td>Redirect URI:</td>
            <td><form:input size="70" path="redirectUri"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <form:hidden path="application"/>
                <input type="submit" value="Get Authorization"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
