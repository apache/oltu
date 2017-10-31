/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
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

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;


public class OAuthRequestTest {
    public static final String REDIRECT_URI = "http://www.example.com/callback";
    public static final String CLIENT_ID = "test_client";
    public static final String ACCESS_GRANT = "test_code";
    public static final String SECRET = "secret";
    public static final String USERNAME = "test_username";
    public static final String PASSWORD = "test_password";
    public static final String REFRESH_TOKEN = "refresh_token";

    @Test
    public void testWrongResponseGetRequestParam() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectOauthResponseType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectParam("param", "someparam")
                .build();

        replay(request);

        assertInvalidOAuthRequest(request);

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectOauthResponseType(null)
                .expectRedirectUri(REDIRECT_URI)
                .expectParam("param", "someparam")
                .build();
        replay(request);

        assertInvalidOAuthRequest(request);
        verify(request);
    }

    private void assertInvalidOAuthRequest(HttpServletRequest request) throws OAuthSystemException {
        try {
            new OAuthAuthzRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
    }

    @Test
    public void testCodeRequestInvalidMethod() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectHttpMethod(OAuth.HttpMethod.PUT)
                .expectOauthResponseType(ResponseType.CODE.toString())
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .build();

        replay(request);

        assertInvalidOAuthRequest(request);
        verify(request);
    }


    @Test
    public void testCodeRequestMissingParameter() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectHttpMethod(OAuth.HttpMethod.GET)
                .expectOauthResponseType(ResponseType.CODE.toString())
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(null)
                .build();

        replay(request);

        assertInvalidOAuthRequest(request);
        verify(request);
    }

    @Test
    public void testValidCodeRequest() throws Exception {
        assertValidCodeRequest(OAuth.HttpMethod.GET);

        assertValidCodeRequest(OAuth.HttpMethod.POST);
    }

    private void assertValidCodeRequest(String httpMethod) throws OAuthSystemException {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectHttpMethod(httpMethod)
                .expectOauthResponseType(ResponseType.CODE.toString())
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .build();

        replay(request);

        try {
            new OAuthAuthzRequest(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        verify(request);
    }

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
            new OAuthTokenRequest(request);
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
                .expectBasicAuthHeader(null)
                .expectGrantType(OAuth.OAUTH_GRANT_TYPE)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(null)
                .expectClientSecret(SECRET)
                .expectAccessGrant(ACCESS_GRANT)
                .build();

        replay(request);
        assertInvalidTokenRequest(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectBasicAuthHeader(null)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectClientSecret(SECRET)
                .expectRedirectUri(null)
                .expectAccessGrant(null)
                .build();

        replay(request);
        assertInvalidTokenRequest(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectBasicAuthHeader(null)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectClientSecret(SECRET)
                .expectAccessGrant(null)
                .build();

        replay(request);
        assertInvalidTokenRequest(request);
    }

    @Test
    public void testTokenAuthCodeRequestWithBasicAuthenticationMissingParameter() throws Exception {
        HttpServletRequest request = mockOAuthTokenRequestBasicAuth(CLIENT_ID, null);
        assertInvalidTokenRequest(request);

        request = mockOAuthTokenRequestBasicAuth(null, SECRET);
        assertInvalidTokenRequest(request);


        // Don't allow to mix basic auth header and body params.
        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId(CLIENT_ID)
                .expectClientSecret(null)
                .expectRedirectUri(REDIRECT_URI)
                .expectAccessGrant(ACCESS_GRANT)
                .expectBasicAuthHeader(createBasicAuthHeader(null, SECRET))
                .build();

        replay(request);

        assertInvalidTokenRequest(request);

        verify(request);
    }

    private HttpServletRequest mockOAuthTokenRequestBasicAuth(String clientId, String clientSecret) {
        HttpServletRequest request = new OauthMockRequestBuilder()
                    .expectGrantType(GrantType.AUTHORIZATION_CODE.toString())
                    .expectHttpMethod(OAuth.HttpMethod.POST)
                    .expectContentType(OAuth.ContentType.URL_ENCODED)
                    .expectClientId(null)
                    .expectClientSecret(null)
                    .expectRedirectUri(REDIRECT_URI)
                    .expectAccessGrant(ACCESS_GRANT)
                    .expectBasicAuthHeader(createBasicAuthHeader(clientId, clientSecret))
                    .build();

        replay(request);
        return request;
    }

    private String createBasicAuthHeader(String clientId, String clientSecret) {
        clientSecret = OAuthUtils.isEmpty(clientSecret) ? "" : clientSecret;
        clientId = OAuthUtils.isEmpty(clientId) ? "" : clientId;
        final String authString = clientId + ":" + clientSecret;
        return "basic " + Base64.encodeBase64String(authString.getBytes());
    }

    @Test
    public void testTokenPasswordRequestMissingParameter() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.PASSWORD.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectRedirectUri(REDIRECT_URI)
                .expectClientId(CLIENT_ID)
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(createBasicAuthHeader(null, SECRET))
                .expectOauthUsername(null)
                .expectOauthPassword(SECRET)
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
                .expectBasicAuthHeader(createBasicAuthHeader(null, SECRET))
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
                .expectClientSecret("")
                .expectBasicAuthHeader(null)
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
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(null)
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
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(null)
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
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(SECRET)
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
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(null)
                .expectAccessGrant(ACCESS_GRANT)
                .expectRedirectUri(REDIRECT_URI)
                .build();
        replay(request);

        OAuthTokenRequest req = null;
        try {
            req = new OAuthTokenRequest(request);

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
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(null)
                .expectOauthUsername(USERNAME)
                .expectOauthPassword(PASSWORD)
                .build();
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        assertEquals(CLIENT_ID, req.getClientId());
        assertEquals(USERNAME, req.getUsername());
        assertEquals(PASSWORD, req.getPassword());

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.CLIENT_CREDENTIALS.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectBasicAuthHeader(createBasicAuthHeader(CLIENT_ID, SECRET))
                .build();
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.REFRESH_TOKEN.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId(CLIENT_ID)
                .expectClientSecret(SECRET)
                .expectBasicAuthHeader(null)
                .expectOauthRefreshToken(REFRESH_TOKEN)
                .build();
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        assertEquals(CLIENT_ID, req.getClientId());
        assertEquals(REFRESH_TOKEN, req.getRefreshToken());
        assertEquals(SECRET, req.getClientSecret());

        verify(request);

        request = new OauthMockRequestBuilder()
                .expectGrantType(GrantType.REFRESH_TOKEN.toString())
                .expectHttpMethod(OAuth.HttpMethod.POST)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId("")
                .expectClientSecret("")
                .expectBasicAuthHeader(createBasicAuthHeader(CLIENT_ID, SECRET))
                .expectOauthRefreshToken(REFRESH_TOKEN)
                .build();
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        assertEquals(CLIENT_ID, req.getClientId());
        assertEquals(REFRESH_TOKEN, req.getRefreshToken());
        assertEquals(SECRET, req.getClientSecret());

        verify(request);
    }


    @Test
    public void testScopes() throws Exception {
        HttpServletRequest request = new OauthMockRequestBuilder()
                .expectOauthResponseType(ResponseType.CODE.toString())
                .expectHttpMethod(OAuth.HttpMethod.GET)
                .expectContentType(OAuth.ContentType.URL_ENCODED)
                .expectClientId(CLIENT_ID)
                .expectRedirectUri(REDIRECT_URI)
                .expectScopes("album photo")
                .build();
        replay(request);

        OAuthRequest req = null;
        try {
            req = new OAuthAuthzRequest(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Set<String> scopes = req.getScopes();

        assertTrue(findScope(scopes, "album"));
        assertTrue(findScope(scopes, "photo"));

        verify(request);
    }

    private boolean findScope(Set<String> scopes, String scope) {
        for (String s : scopes) {
            if (s.equals(scope)) {
                return true;
            }
        }
        return false;
    }
}
