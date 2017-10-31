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

public class HeaderOAuthValidatorTest {


    @Test
    public void testValidateNoHeader() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(null);
        replay(request);
        try {
            BearerHeaderOAuthValidator bov = new BearerHeaderOAuthValidator();
            bov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertTrue(OAuthUtils.isEmpty(e.getError()));
            assertEquals("Missing authorization header.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testValidateInvalidHeader() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Basic arawersadf");
        replay(request);
        try {
            BearerHeaderOAuthValidator bov = new BearerHeaderOAuthValidator();
            bov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertTrue(OAuthUtils.isEmpty(e.getError()));
            assertEquals("Incorrect authorization method.", e.getDescription());
        }
        verify(request);
    }


    @Test
    public void testValidateValidHeaderMissingField() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Bearer  ");
        replay(request);
        try {
            BearerHeaderOAuthValidator bov = new BearerHeaderOAuthValidator();
            bov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            assertEquals("Missing required parameter.", e.getDescription());
        }
        verify(request);
    }


    @Test
    public void testValidateValidHeaderWrongVersion() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION))
            .andStubReturn("Bearer sdfsadfsadf,oauth_signature_method=\"HMAC-SHA1\"");
        replay(request);
        try {
            BearerHeaderOAuthValidator bov = new BearerHeaderOAuthValidator();
            bov.performAllValidations(request);
            fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
        }
        verify(request);
    }

    @Test
    public void testValidateValidHeader() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Bearer sdfsadfsadf");
        replay(request);
        BearerHeaderOAuthValidator bov = new BearerHeaderOAuthValidator();
        bov.performAllValidations(request);

        verify(request);
    }

}
