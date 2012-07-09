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

package org.apache.amber.oauth2.as;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.amber.oauth2.as.request.OAuthAuthzRequest;
import org.apache.amber.oauth2.as.request.OAuthRequest;
import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.junit.Test;


/**
 *
 *
 *
 */
public class OAuthRequestTest {

    @Test
    public void testWrongResponseGetRequestParam() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");
        expect(request.getParameter("param")).andStubReturn("someparam");
        replay(request);

        try {
            new OAuthAuthzRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(null);
        expect(request.getParameter("param")).andStubReturn("someparam");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");
        replay(request);

        try {
            new OAuthAuthzRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }

    @Test
    public void testCodeRequestInvalidMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(ResponseType.CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.PUT);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        OAuthRequest req = null;
        try {
            new OAuthAuthzRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }


    @Test
    public void testCodeRequestMissingParameter() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(ResponseType.CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        OAuthRequest req = null;
        try {
            new OAuthAuthzRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }

    @Test
    public void testValidCodeRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(ResponseType.CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        OAuthRequest req = null;
        try {
            new OAuthAuthzRequest(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(ResponseType.CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
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
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE)).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");
        expect(request.getParameter("param")).andStubReturn("someparam");
        replay(request);

        OAuthRequest req = null;
        try {
            req = new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");
        expect(request.getParameter("param")).andStubReturn("someparam");
        replay(request);

        try {
            req = new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }

    @Test
    public void testTokenRequestInvalidMethod() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.AUTHORIZATION_CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE)).andStubReturn("authorization_code");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
        verify(request);

        reset(request);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.PASSWORD.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.REFRESH_TOKEN.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
        verify(request);
        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(null);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

    }

    @Test
    public void testTokenRequestInvalidContentType() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.AUTHORIZATION_CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
        verify(request);
        reset(request);

        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE)).andStubReturn("authorization_code");

        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.PASSWORD.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.REFRESH_TOKEN.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(null);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }

    @Test
    public void testTokenAuthCodeRequestMissingParameter() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.AUTHORIZATION_CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_CODE)).andStubReturn("test_code");
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.AUTHORIZATION_CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_CODE)).andStubReturn("test_code");
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.AUTHORIZATION_CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_CODE)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

    }

    @Test
    public void testTokenPasswordRequestMissingParameter() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.PASSWORD.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_USERNAME)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_PASSWORD)).andStubReturn("test_password");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.PASSWORD.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_USERNAME)).andStubReturn("test_username");
        expect(request.getParameter(OAuth.OAUTH_PASSWORD)).andStubReturn("");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.PASSWORD.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_USERNAME)).andStubReturn("test_username");
        expect(request.getParameter(OAuth.OAUTH_PASSWORD)).andStubReturn("test_password");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }


    @Test
    public void testRefreshTokenRequestMissingParameter() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.REFRESH_TOKEN.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_REFRESH_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.REFRESH_TOKEN.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("");
        expect(request.getParameter(OAuth.OAUTH_REFRESH_TOKEN)).andStubReturn("refresh_token");
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);

        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.REFRESH_TOKEN.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://www.example.com/red");

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_REFRESH_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            new OAuthTokenRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

        verify(request);
    }

    @Test
    public void testValidTokenRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.AUTHORIZATION_CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_CODE)).andStubReturn("test_code");
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("test_secret");
        replay(request);

        OAuthTokenRequest req = null;
        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        Assert.assertEquals(GrantType.AUTHORIZATION_CODE.toString(), req.getGrantType());
        Assert.assertEquals("test_client", req.getClientId());
        Assert.assertEquals("http://example.com/callback", req.getRedirectURI());
        Assert.assertEquals("test_code", req.getCode());

        verify(request);
        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.PASSWORD.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_USERNAME)).andStubReturn("username_test");
        expect(request.getParameter(OAuth.OAUTH_PASSWORD)).andStubReturn("test_password");
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        Assert.assertEquals("client_id", req.getClientId());
        Assert.assertEquals("username_test", req.getUsername());
        Assert.assertEquals("test_password", req.getPassword());

        verify(request);
        reset(request);

        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE)).andStubReturn(GrantType.CLIENT_CREDENTIALS.toString());
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
//        Assert.assertEquals("test_assertion", req.getAssertion());
//        Assert.assertEquals("test_type", req.getAssertionType());

        verify(request);
        reset(request);

        expect(request.getParameter(OAuth.OAUTH_GRANT_TYPE))
            .andStubReturn(GrantType.REFRESH_TOKEN.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);

        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("client_id");
        expect(request.getParameter(OAuth.OAUTH_REFRESH_TOKEN)).andStubReturn("refresh_token");
        expect(request.getParameter(OAuth.OAUTH_CLIENT_SECRET)).andStubReturn("secret");
        replay(request);

        try {
            req = new OAuthTokenRequest(request);

        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        Assert.assertEquals("client_id", req.getClientId());
        Assert.assertEquals("refresh_token", req.getRefreshToken());
        Assert.assertEquals("secret", req.getClientSecret());

        verify(request);


    }

    @Test
    public void testScopes() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);

        expect(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).andStubReturn(ResponseType.CODE.toString());
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameter(OAuth.OAUTH_CLIENT_ID)).andStubReturn("test_client");
        expect(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).andStubReturn("http://example.com/callback");
        expect(request.getParameter(OAuth.OAUTH_SCOPE)).andStubReturn("album photo");
        replay(request);

        OAuthRequest req = null;
        try {
            req = new OAuthAuthzRequest(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Set<String> scopes = req.getScopes();

        Assert.assertTrue(findScope(scopes, "album"));
        Assert.assertTrue(findScope(scopes, "photo"));

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
