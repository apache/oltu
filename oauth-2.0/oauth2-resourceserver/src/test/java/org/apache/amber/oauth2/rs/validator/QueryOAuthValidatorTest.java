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

package org.apache.amber.oauth2.rs.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.junit.Test;

/**
 *
 *
 *
 */
public class QueryOAuthValidatorTest {

    @Test
    public void testValidateWrongVersion() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_VERSION_DIFFER + "=HMAC-SHA1&"
            + OAuth.OAUTH_BEARER_TOKEN
            + "=access_token");
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn("HMAC-SHA1");
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"access_token"});
        replay(request);
        try {
            BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
            qov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateNoQuery() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(null);
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_TOKEN)).andStubReturn(null);
        replay(request);
        try {
            BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
            qov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            org.junit.Assert.assertTrue(OAuthUtils.isEmpty(e.getError()));
            Assert.assertEquals("Missing OAuth token.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateMultipleTokens() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=access_token1&"
            + OAuth.OAUTH_BEARER_TOKEN
            + "=access_token2");
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"access_token1",
        //                                                                                                 "access_token2"});
        replay(request);
        try {
            BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
            qov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Multiple tokens attached.", e.getDescription());
        }
        verify(request);

    }

    @Test
    public void testValidateToken() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=access_token1");
        //        expect(request.getParameter(OAuth.OAUTH_VERSION_DIFFER)).andStubReturn(null);
        //        expect(request.getParameterValues(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(new String[] {"access_token1"});
        replay(request);
        BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
        qov.performAllValidations(request);
        verify(request);

    }

}
