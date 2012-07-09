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

package org.apache.amber.oauth2.client.response;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.client.utils.TestUtils;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.junit.Assert;
import org.junit.Test;
import org.apache.amber.oauth2.common.error.OAuthError;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.fail;

/**
 *
 *
 *
 */
public class OAuthAuthorizationResponseImplTest {


    @Test
    public void testGetAccessToken() throws Exception {

        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        //check valid request
        TestUtils.expectNoErrorParameters(request);
        Map<String, String[]> parameters = new HashMap<String, String[]>();
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"test_access_token"});
        parameters.put(OAuth.OAUTH_CODE, null);
        parameters.put(OAuth.OAUTH_SCOPE, null);
        parameters.put(OAuth.OAUTH_EXPIRES_IN, null);

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        OAuthAuthzResponse r = null;
        try {
            r = OAuthAuthzResponse.oauthTokenAuthzResponse(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        String token = r.getAccessToken();
        Assert.assertNotNull(token);

        verify(request);

        reset(request);

        //both parameters code and access token
        TestUtils.expectNoErrorParameters(request);
        parameters.put(OAuth.OAUTH_CODE, new String[] {"code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, null);
        parameters.put(OAuth.OAUTH_SCOPE, null);
        parameters.put(OAuth.OAUTH_EXPIRES_IN, null);

        expect(request.getParameterMap()).andReturn(parameters);

        replay(request);

        r = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
        token = r.getAccessToken();
        Assert.assertNull(token);
        verify(request);

        reset(request);

        //both parameters code and access token
        TestUtils.expectNoErrorParameters(request);

        parameters.put(OAuth.OAUTH_CODE, new String[] {"test_code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"test_access_token"});
        parameters.put(OAuth.OAUTH_SCOPE, null);
        parameters.put(OAuth.OAUTH_EXPIRES_IN, null);

        expect(request.getParameterMap()).andReturn(parameters);

        replay(request);

        try {
            OAuthAuthzResponse.oauthTokenAuthzResponse(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(
                e.getError().equals(OAuthError.TokenResponse.INVALID_REQUEST));
        }


    }


    @Test
    public void testGetExpiresIn() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        TestUtils.expectNoErrorParameters(request);
        Map<String, String[]> parameters = new HashMap<String, String[]>();
        parameters.put(OAuth.OAUTH_CODE, new String[] {"test_code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, null);
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {"test_scope"});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {"test_expires_in"});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        OAuthAuthzResponse r = null;
        try {
            OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(
                e.getError().equals(OAuthError.TokenResponse.INVALID_REQUEST));
        }

        verify(request);

        reset(request);

        TestUtils.expectNoErrorParameters(request);
        parameters.put(OAuth.OAUTH_CODE, null);
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"token"});
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {"test_scope"});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {"3600"});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        try {
            r = OAuthAuthzResponse.oauthTokenAuthzResponse(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertNotNull(r.getExpiresIn());
        verify(request);
    }

    @Test
    public void testGetScope() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        TestUtils.expectNoErrorParameters(request);

        Map<String, String[]> parameters = new HashMap<String, String[]>();
        parameters.put(OAuth.OAUTH_CODE, new String[] {"test_code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, null);
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {"test_scope"});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {"3600"});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        OAuthAuthzResponse r = null;
        try {
            OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(
                e.getError().equals(OAuthError.TokenResponse.INVALID_REQUEST));

        }

        verify(request);

        reset(request);
        TestUtils.expectNoErrorParameters(request);

        parameters.put(OAuth.OAUTH_CODE, null);
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"token"});
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {"test_scope"});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {"3600"});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        try {
            r = OAuthAuthzResponse.oauthTokenAuthzResponse(request);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }
        Assert.assertNotNull(r.getScope());
        verify(request);

    }

    @Test
    public void testGetCode() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        //check valid request
        TestUtils.expectNoErrorParameters(request);
        Map<String, String[]> parameters = new HashMap<String, String[]>();
        parameters.put(OAuth.OAUTH_CODE, null);
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"test_access_token"});
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {null});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {null});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        OAuthAuthzResponse r = OAuthAuthzResponse.oauthTokenAuthzResponse(request);
        String code = r.getCode();
        Assert.assertNull(code);

        verify(request);

        reset(request);

        //both parameters code and access token
        TestUtils.expectNoErrorParameters(request);

        parameters.put(OAuth.OAUTH_CODE, new String[] {"code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, null);
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {null});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {null});

        expect(request.getParameterMap()).andStubReturn(parameters);
        replay(request);

        r = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
        code = r.getCode();
        Assert.assertNotNull(code);
        verify(request);

        reset(request);

        //both parameters code and access token
        TestUtils.expectNoErrorParameters(request);

        parameters.put(OAuth.OAUTH_CODE, new String[] {"test_code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"test_access_token"});
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {null});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {null});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);


        try {
            OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertTrue(
                e.getError().equals(OAuthError.TokenResponse.INVALID_REQUEST));

        }

    }

    @Test
    public void testGetState() throws Exception {

        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        //check valid request
        TestUtils.expectNoErrorParameters(request);

        Map<String, String[]> parameters = new HashMap<String, String[]>();
        parameters.put(OAuth.OAUTH_CODE, new String[] {"test_code"});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, null);
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {null});
        parameters.put(OAuth.OAUTH_STATE, new String[] {"test_state"});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {null});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        OAuthAuthzResponse r = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
        String state = r.getState();
        Assert.assertNotNull(state);

        verify(request);

        reset(request);
        //check valid request
        TestUtils.expectNoErrorParameters(request);

        parameters.put(OAuth.OAUTH_CODE, new String[] {null});
        parameters.put(OAuth.OAUTH_ACCESS_TOKEN, new String[] {"test_access_token"});
        parameters.put(OAuth.OAUTH_SCOPE, new String[] {null});
        parameters.put(OAuth.OAUTH_STATE, new String[] {"test_state"});
        parameters.put(OAuth.OAUTH_EXPIRES_IN, new String[] {null});

        expect(request.getParameterMap()).andStubReturn(parameters);

        replay(request);

        r = OAuthAuthzResponse.oauthTokenAuthzResponse(request);
        state = r.getState();
        Assert.assertNotNull(state);

        verify(request);
    }

}
