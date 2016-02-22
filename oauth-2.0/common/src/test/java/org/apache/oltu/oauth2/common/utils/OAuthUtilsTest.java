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

package org.apache.oltu.oauth2.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 *
 *
 */
public class OAuthUtilsTest {

    private static final String BASIC_PREFIX = "Basic ";

    @Test
    @Ignore
    // TODO what are testing here?
    public void testBuildJSON() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(OAuthError.OAUTH_ERROR, OAuthError.TokenResponse.INVALID_REQUEST);

        String json = JSONUtils.buildJSON(params);

        /* JSONObject obj = new JSONObject(json);

        AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);

        assertEquals(XMLStreamReader.START_ELEMENT, reader.next());
        assertEquals(OAuthError.OAUTH_ERROR, reader.getName().getLocalPart());

        assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, reader.getText());
        assertEquals(XMLStreamReader.CHARACTERS, reader.next());
        assertEquals(XMLStreamReader.END_ELEMENT, reader.next());
        assertEquals(XMLStreamReader.END_DOCUMENT, reader.next()); */
    }

    @Test
    public void testFormat() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("movie", "Kiler");
        parameters.put("director", "Machulski");


        String format = OAuthUtils.format(parameters.entrySet(), "UTF-8");
        assertEquals("movie=Kiler&director=Machulski", format);
    }

    @Test
    public void testSaveStreamAsString() throws Exception {
        String sampleTest = "It is raining again today";

        InputStream is = new ByteArrayInputStream(sampleTest.getBytes("UTF-8"));
        assertEquals(sampleTest, OAuthUtils.saveStreamAsString(is));
    }

    @Test
    public void testHandleOAuthProblemException() throws Exception {
        OAuthProblemException exception = OAuthUtils.handleOAuthProblemException("missing parameter");

        assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, exception.getError());
        assertEquals("missing parameter", exception.getDescription());
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
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("realm", "example");

        ///rfc6750#section-3
        String header = OAuthUtils.encodeOAuthHeader(parameters);
        assertEquals("Bearer realm=\"example\"", header);

    }

    @Test
    public void testEncodeOAuthHeaderWithError() throws Exception {

        Map<String, Object> entries = new HashMap<String, Object>();
        entries.put("realm", "Some Example Realm");
        entries.put("error", "invalid_token");

        String header = OAuthUtils.encodeOAuthHeader(entries);
        assertEquals("Bearer realm=\"Some Example Realm\",error=\"invalid_token\"", header);
    }

    @Test
    public void testEncodeAuthorizationBearerHeader() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("accessToken", "mF_9.B5f-4.1JqM");

        //rfc6749#section-7.1
        String header = OAuthUtils.encodeAuthorizationBearerHeader(parameters);
        assertEquals("Bearer mF_9.B5f-4.1JqM", header);

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

    @Test
    public void testDecodeValidClientAuthnHeader() throws Exception {
        String header = "clientId:secret";
        String encodedHeader = BASIC_PREFIX + encodeHeader(header);

        String[] credentials = OAuthUtils.decodeClientAuthenticationHeader(encodedHeader);

        assertNotNull(credentials);
        assertEquals("clientId", credentials[0]);
        assertEquals("secret", credentials[1]);
    }

    @Test
    public void testDecodeValidClientAuthnHeaderWithColonInPassword() throws Exception {
        String header = "clientId:sec:re:t";
        String encodedHeader = BASIC_PREFIX + encodeHeader(header);

        String[] credentials = OAuthUtils.decodeClientAuthenticationHeader(encodedHeader);

        assertNotNull(credentials);
        assertEquals("clientId", credentials[0]);
        assertEquals("sec:re:t", credentials[1]);
    }

    @Test
    public void testDecodeEmptyClientAuthnHeader() throws Exception {
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(null));
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(""));
    }

    @Test
    public void testDecodeInvalidClientAuthnHeader() throws Exception {
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(BASIC_PREFIX));
        assertNull(OAuthUtils.decodeClientAuthenticationHeader("invalid_header"));
        assertNull(OAuthUtils.decodeClientAuthenticationHeader("Authorization dXNlcm5hbWU6cGFzc3dvcmQ="));
    }

    @Test
    public void testDecodeClientAuthnHeaderNoClientIdOrSecret() throws Exception {
        String header = ":";
        String encodedHeader = BASIC_PREFIX + encodeHeader(header);
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(encodedHeader));
    }

    @Test
    public void testDecodeClientAuthnHeaderNoClientId() throws Exception {
        String header = ":secret";
        String encodedHeader = BASIC_PREFIX + encodeHeader(header);
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(encodedHeader));
    }

    @Test
    public void testDecodeClientAuthnHeaderNoSecret() throws Exception {
        String header = "clientId:";
        String encodedHeader = BASIC_PREFIX + encodeHeader(header);
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(encodedHeader));
    }

    @Test
    public void testDecodeClientAuthnHeaderNoSeparator() throws Exception {
        String header = "clientId";
        String encodedHeader = BASIC_PREFIX + encodeHeader(header);
        assertNull(OAuthUtils.decodeClientAuthenticationHeader(encodedHeader));
    }

    private String encodeHeader(String header) {
        return new String(Base64.encodeBase64(header.getBytes()));
    }
}
