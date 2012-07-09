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

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.rs.validator.BearerHeaderOAuthValidator;
import org.junit.Test;
import org.apache.amber.oauth2.common.utils.OAuthUtils;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 *
 *
 *
 */
public class HeaderOAuthValidatorTest {


    @Test
    public void testValidateNoHeader() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(null);
        replay(request);
        try {
            BearerHeaderOAuthValidator bov = new BearerHeaderOAuthValidator();
            bov.performAllValidations(request);
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            org.junit.Assert.assertTrue(OAuthUtils.isEmpty(e.getError()));
            Assert.assertEquals("Missing authorization header.", e.getDescription());
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
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            org.junit.Assert.assertTrue(OAuthUtils.isEmpty(e.getError()));
            Assert.assertEquals("Incorrect authorization method.", e.getDescription());
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
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Missing required parameter.", e.getDescription());
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
            Assert.fail("Exception not thrown.");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
            Assert.assertEquals("Incorrect OAuth version. Found OAuth V1.0.", e.getDescription());
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
