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

package org.apache.amber.oauth2.common.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 *
 */
public class OAuthUtilsTest {
    @Test
    public void testFormat() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("movie", "Kiler");
        parameters.put("director", "Machulski");


        String format = OAuthUtils.format(parameters.entrySet(), "UTF-8");
        Assert.assertEquals("movie=Kiler&director=Machulski", format);
    }

    @Test
    public void testSaveStreamAsString() throws Exception {
        String sampleTest = "It is raining again today";

        InputStream is = new ByteArrayInputStream(sampleTest.getBytes("UTF-8"));
        Assert.assertEquals(sampleTest, OAuthUtils.saveStreamAsString(is));
    }

    @Test
    public void testHandleOAuthProblemException() throws Exception {
        OAuthProblemException exception = OAuthUtils.handleOAuthProblemException("missing parameter");

        Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, exception.getError());
        Assert.assertEquals("missing parameter", exception.getDescription());
    }

    @Test
    public void testHandleMissingParameters() throws Exception {
        Set<String> missingParameters = new HashSet<String>();
        missingParameters.add(OAuth.OAUTH_CLIENT_ID);
        missingParameters.add(OAuth.OAUTH_CLIENT_SECRET);

        OAuthUtils.handleMissingParameters(missingParameters);
    }

    @Test
    public void testHandleNotAllowedParametersOAuthException() throws Exception {

    }

    @Test
    public void testDecodeForm() throws Exception {

    }

    @Test
    public void testIsFormEncoded() throws Exception {

    }

    @Test
    public void testDecodePercent() throws Exception {

    }

    @Test
    public void testPercentEncode() throws Exception {

    }

    @Test
    public void testInstantiateClass() throws Exception {

    }

    @Test
    public void testInstantiateClassWithParameters() throws Exception {

    }

    @Test
    public void testGetAuthHeaderField() throws Exception {

    }

    @Test
    public void testDecodeOAuthHeader() throws Exception {

    }

    @Test
    public void testEncodeOAuthHeader() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {

    }

    @Test
    public void testHasEmptyValues() throws Exception {

    }

    @Test
    public void testGetAuthzMethod() throws Exception {

    }

    @Test
    public void testHandleOAuthError() throws Exception {

    }

    @Test
    public void testDecodeScopes() throws Exception {

    }

    @Test
    public void testEncodeScopes() throws Exception {

    }

    @Test
    public void testIsExpired() throws Exception {

    }

    @Test
    public void testGetIssuedTimeInSec() throws Exception {

    }

    @Test
    public void testIsMultipart() throws Exception {

    }

    @Test
    public void testHasContentType() throws Exception {

    }
}
