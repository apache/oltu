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

package org.apache.amber.oauth2.rs;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.rs.request.OAuthAccessResourceRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 */
public class OAuthAccessResourceRequestTest {

    public static final String AUTHORIZATION_HEADER_OAUTH2 = "Bearer sometoken";

    @Test
    public void testCreateNoHeaderRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(null);
        replay(request);

        OAuthAccessResourceRequest req = null;
        try {
            new OAuthAccessResourceRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals("Missing authorization header.", e.getDescription());
        }

        verify(request);

    }

    @Test
    public void testCreateWrongHeaderRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Basic assdafasfd");
        replay(request);

        try {
            new OAuthAccessResourceRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals("Incorrect authorization method.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testCreateHeaderMissingFieldRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Bearer ");
        replay(request);

        try {
            new OAuthAccessResourceRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateHeaderWrongVersionRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION))
            .andStubReturn("Bearer sadfasfd,oauth_signature_method=\"HMAC-SHA1\"");
        replay(request);

        try {
            new OAuthAccessResourceRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateValidHeaderRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Bearer token");
        replay(request);
        try {
            new OAuthAccessResourceRequest(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        verify(request);

    }

    @Test
    public void testCreateBodyWrongMethod() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateBodyInvalidEncoding() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);
        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateBodyWrongOAuthVersion() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn("HMAC-SHA1");
        replay(request);
        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
            Assert.assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
    }

    @Test
    public void testCreateBodyHeaderMixedTokens() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Bearer sadfasfd");
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY, ParameterStyle.HEADER);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateBodyHeaderMixedTokensAndWrongVersion() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION))
            .andStubReturn("Bearer sadfasfd,oauth_signature_method=\"HMAC-SHA1\"");
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY, ParameterStyle.HEADER);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateBodyNoToken() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e);
        }
        verify(request);
    }

    @Test
    public void testCreateBodyMultipleTokens() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken",
                                                                                                 "othertoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateBodyValidRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        verify(request);

    }

    @Test
    public void testCreateQueryNoToken() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getQueryString()).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(null);
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNull(e.getError());
        }
        verify(request);

    }

    @Test
    public void testCreateQueryWrongVersion() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_VERSION_DIFFER + "=HMAC-SHA1&"
            + OAuth.OAUTH_BEARER_TOKEN
            + "=sometoken");
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn("HMAC-SHA1");
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateQueryMultipleTokens() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=sometoken&"
            + OAuth.OAUTH_BEARER_TOKEN
            + "=othertoken");
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN))
        //            .andStubReturn(new String[] {"sometoken", "othertoken"});
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }
        verify(request);
    }

    @Test
    public void testCreateQueryValidRequest() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=sometoken");
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        verify(request);

    }

    @Test
    public void testGetAccessTokenWrongQueryRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(null);
        //        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(null);        
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN))
        //            .andStubReturn(new String[] {null});

        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNull(e.getError());
        }
        verify(request);
    }

    @Test
    public void testGetAccessTokenWrongHeaderRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getHeader("Authorization")).andStubReturn(null);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {null});

        replay(request);

        try {
            new OAuthAccessResourceRequest(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals("Missing authorization header.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testGetAccessTokenWrongBodyRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {null});
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(new String[] {null});
        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals("Missing OAuth token.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testGetAccessCorrectRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);

        //test body
        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn("sometoken");
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});

        replay(request);
        OAuthAccessResourceRequest req = null;
        try {
            req = new OAuthAccessResourceRequest(request, ParameterStyle.BODY);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals("sometoken", req.getAccessToken());
        verify(request);

        reset(request);
        //test header
        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn("sometoken");
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getHeader("Authorization")).andStubReturn(AUTHORIZATION_HEADER_OAUTH2);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});

        replay(request);
        try {
            req = new OAuthAccessResourceRequest(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals("sometoken", req.getAccessToken());
        verify(request);

        reset(request);
        //test uri query
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=sometoken");

        //        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn("sometoken");
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});

        replay(request);
        req = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

        Assert.assertEquals("sometoken", req.getAccessToken());
        verify(request);
    }

    @Test
    public void testMultipleStylesValidRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(null);

        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {null});
        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(new String[] {null});
        expect(request.getHeader("Authorization")).andStubReturn(AUTHORIZATION_HEADER_OAUTH2);

        replay(request);

        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.QUERY, ParameterStyle.BODY, ParameterStyle.HEADER);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        verify(request);
    }

    @Test
    public void testMultipleStylesInvalidRequest() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(null);

        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn("sometoken");
        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);
        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"sometoken"});
        expect(request.getHeader("Authorization")).andStubReturn(AUTHORIZATION_HEADER_OAUTH2);

        replay(request);

        OAuthAccessResourceRequest req = null;
        try {
            new OAuthAccessResourceRequest(request, ParameterStyle.BODY, ParameterStyle.QUERY, ParameterStyle.HEADER);
            fail("Exception expeted");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(OAuthError.TokenResponse.INVALID_REQUEST.equals(e.getError()));
        }

        verify(request);
    }
}
