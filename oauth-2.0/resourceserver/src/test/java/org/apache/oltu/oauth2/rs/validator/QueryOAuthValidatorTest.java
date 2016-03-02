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

package org.apache.oltu.oauth2.rs.validator;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class QueryOAuthValidatorTest {

    @Test
    public void testValidateWrongVersion() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_VERSION_DIFFER + "=HMAC-SHA1&"
                + OAuth.OAUTH_BEARER_TOKEN
                + "=access_token");
        replay(request);
        try {
            BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
            qov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testValidateNoQuery() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(null);
        replay(request);
        try {
            BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
            qov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertTrue(OAuthUtils.isEmpty(e.getError()));
            assertEquals("Missing OAuth token.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testValidateMultipleTokens() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=access_token1&"
                + OAuth.OAUTH_BEARER_TOKEN
                + "=access_token2");

        replay(request);
        try {
            BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
            qov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            assertEquals("Multiple tokens attached.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testValidateToken() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getQueryString()).andStubReturn(OAuth.OAUTH_BEARER_TOKEN + "=access_token1");

        replay(request);
        BearerQueryOAuthValidator qov = new BearerQueryOAuthValidator();
        qov.performAllValidations(request);
        verify(request);
    }
}
