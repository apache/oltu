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

import org.apache.oltu.oauth2.as.request.OAuthUnauthenticatedTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.junit.Test;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OAuthUnauthenticatedRequestTest {
    public static final String REDIRECT_URI = "http://www.example.com/callback";
    public static final String CLIENT_ID = "test_client";
    public static final String ACCESS_GRANT = "test_code";
    public static final String USERNAME = "test_username";
    public static final String PASSWORD = "test_password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String SECRET = "";

    @Test
    public void testTokenWrongGrantType() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectGrantType(OAuth.ContentType.URL_ENCODED)
                .expectParam("param", "someparam")
                .build();

        replay(request);
        assertInvalidTokenRequest(request);

        request = new OauthMockRequestBuilder()
                .expectRedirectUri(REDIRECT_URI)
                .expectGrantType(null)
                .expectParam("param", "someparam")
                .build();

        replay(request);
        assertInvalidTokenRequest(request);
    }

    private void assertInvalidTokenRequest(HttpServletRequest request) throws OAuthSystemException {
        try {
            new OAuthUnauthenticatedTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }

    @Test
    public void testTokenRequestInvalidMethod() throws Exception {
        HttpServletRequest request = mockTokenRequestInvalidMethod(GrantType.AUTHORIZATION_CODE.toString());
        assertInvalidTokenRequest(request);

        request = mockTokenRequestInvalidMethod(GrantType.PASSWORD.toString());
        assertInvalidTokenRequest(request);

        request = mockTokenRequestInvalidMethod(GrantType.REFRESH_TOKEN.toString());
        assertInvalidTokenRequest(request);

        request = mockTokenRequestInvalidMethod(null);
        assertInvalidTokenRequest(request);
    }

    private HttpServletRequest mockTokenRequestInvalidMethod(String grantType) {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectHttpMethod(OAuth.HttpMethod.GET)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectGrantType(grantType)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .build();
        replay(request);

        return request;
    }

    @Test
    public void testTokenRequestInvalidContentType() throws Exception {
        HttpServletRequest request = mockTokenRequestInvalidContentType(GrantType.AUTHORIZATION_CODE.toString());
        assertInvalidTokenRequest(request);

        request = mockTokenRequestInvalidContentType(GrantType.PASSWORD.toString());
        assertInvalidTokenRequest(request);

        request = mockTokenRequestInvalidContentType(GrantType.REFRESH_TOKEN.toString());
        assertInvalidTokenRequest(request);

        request = mockTokenRequestInvalidContentType(null);
        assertInvalidTokenRequest(request);
    }

    private HttpServletRequest mockTokenRequestInvalidContentType(String grantType) {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectGrantType(grantType)
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.JSON)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .build();

        replay(request);
        return request;
    }

    @Test
    public void testTokenAuthCodeRequestMissingParameter() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectGrantType(OAuth.OAUTH_GRANT_TYPE)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(null)
                .expectAccessGrant(ACCESS_GRANT)
                .build();

        replay(request);
        assertInvalidTokenRequest(request);
        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectRedirectUri(null)
                .expectAccessGrant(null)
                .build();

        replay(request);

        assertInvalidTokenRequest(request);
        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectAccessGrant(null)
                .build();

        replay(request);
        assertInvalidTokenRequest(request);
        verify(request);
    }

    @Test
    public void testTokenPasswordRequestMissingParameter() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.PASSWORD.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectOauthUsername(null)
                .expectOauthPassword(PASSWORD)
                .build();
        replay(request);

        assertInvalidTokenRequest(request);
        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.PASSWORD.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectOauthUsername(USERNAME)
                .expectOauthPassword("")
                .build();
        replay(request);

        assertInvalidTokenRequest(request);

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.PASSWORD.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(null)
                .expectOauthUsername(USERNAME)
                .expectOauthPassword(PASSWORD)
                .build();
        replay(request);

        assertInvalidTokenRequest(request);

        verify(request);
    }

    @Test
    public void testRefreshTokenRequestMissingParameter() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.REFRESH_TOKEN.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectOauthRefreshToken(null)
                .build();
        replay(request);

        assertInvalidTokenRequest(request);
        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.REFRESH_TOKEN.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId("")
                .expectOauthRefreshToken(REFRESH_TOKEN)
                .build();

        replay(request);

        assertInvalidTokenRequest(request);
        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.REFRESH_TOKEN.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(null)
                .expectOauthRefreshToken(null)
                .build();
        replay(request);

        assertInvalidTokenRequest(request);
        verify(request);
    }

    @Test
    public void testValidTokenRequest() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId(CLIENT_ID)
                .expectAccessGrant(ACCESS_GRANT)
                .expectRedirectUri(REDIRECT_URI)
                .expectBasicAuthHeader(null)
                .build();
        replay(request);

        OAuthUnauthenticatedTokenRequest req = null;
        try {
            req = new OAuthUnauthenticatedTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        assertEquals(GrantType.AUTHORIZATION_CODE.toString(), req.getGrantType());
        assertEquals(CLIENT_ID, req.getClientId());
        assertEquals(REDIRECT_URI, req.getRedirectURI());
        assertEquals(ACCESS_GRANT, req.getCode());

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.PASSWORD.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId(CLIENT_ID)
                .expectBasicAuthHeader(null)
                .expectOauthUsername(USERNAME)
                .expectOauthPassword(PASSWORD)
                .build();
        replay(request);

        try {
            req = new OAuthUnauthenticatedTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        assertEquals(CLIENT_ID, req.getClientId());
        assertEquals(USERNAME, req.getUsername());
        assertEquals(PASSWORD, req.getPassword());

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.REFRESH_TOKEN.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId(CLIENT_ID)
                .expectOauthRefreshToken(REFRESH_TOKEN)
                .expectBasicAuthHeader(null)
                .build();
        replay(request);

        try {
            req = new OAuthUnauthenticatedTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        assertEquals(CLIENT_ID, req.getClientId());
        assertEquals(REFRESH_TOKEN, req.getRefreshToken());

        verify(request);
    }
}
