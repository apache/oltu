/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oltu.oauth2.as;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.OAuth;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;

public class OauthMockRequestBuilder {

    private HttpServletRequest request;

    public OauthMockRequestBuilder() {
        request = createMock(HttpServletRequest.class);
    }

    public OauthMockRequestBuilder expectOauthResponseType(String oauthResponseType) {
        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(oauthResponseType);

        return this;
    }

    public OauthMockRequestBuilder expectRedirectUri(String redirectUri) {
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn(redirectUri);

        return this;
    }

    public OauthMockRequestBuilder expectParam(String paramName, String paramValue) {
        expect(request.getParameter(paramName)).andStubReturn(paramValue);

        return this;
    }

    public HttpServletRequest build() {
        return request;
    }

    public OauthMockRequestBuilder expectContentType(String contentType) {
        expect(request.getContentType()).andStubReturn(contentType);

        return this;
    }

    public OauthMockRequestBuilder expectHttpMethod(String method) {
        expect(request.getMethod()).andStubReturn(method);

        return this;
    }

    public OauthMockRequestBuilder expectClientId(String clientId) {
        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn(clientId);

        return this;
    }

    public OauthMockRequestBuilder expectClientSecret(String secret) {
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn(secret);

        return this;
    }

    public OauthMockRequestBuilder expectGrantType(String grantType) {
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE)).andStubReturn(grantType);

        return this;
    }

    public OauthMockRequestBuilder expectBasicAuthHeader(String authorizationHeader) {
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(authorizationHeader);

        return this;
    }

    public OauthMockRequestBuilder expectAccessGrant(String accessGrant) {
        expect(request.getParameter(OAuth.OAUTH_CODE)).andStubReturn(accessGrant);

        return this;
    }

    public OauthMockRequestBuilder expectOauthUsername(String oauthUsername) {
        expect(request.getParameter(OAuth.OAUTH_USERNAME)).andStubReturn(oauthUsername);

        return this;
    }

    public OauthMockRequestBuilder expectOauthPassword(String secret) {
        expect(request.getParameter(OAuth.OAUTH_PASSWORD)).andStubReturn(secret);

        return this;
    }

    public OauthMockRequestBuilder expectOauthRefreshToken(String refreshToken) {
        expect(request.getParameter(OAuth.OAUTH_REFRESH_TOKEN)).andStubReturn(refreshToken);

        return this;
    }

    public OauthMockRequestBuilder expectScopes(String scopes) {
        expect(request.getParameter(OAuth.OAUTH_SCOPE)).andStubReturn(scopes);

        return this;
    }
}
