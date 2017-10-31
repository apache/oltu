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

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
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
        List<String> notAllowedParametersList = new LinkedList<String>();
        notAllowedParametersList.add("Parameter1");
        notAllowedParametersList.add("Parameter2");

        OAuthProblemException exception = OAuthUtils.handleNotAllowedParametersOAuthException(notAllowedParametersList);
        assertEquals("Not allowed parameters: Parameter1 Parameter2", exception.getDescription());
    }

    @Test
    public void testDecodeForm() throws Exception {
        String formUrlEncoded = "MyVariableOne=ValueOne&MyVariableTwo=ValueTwo";
        Map<String, Object> formDecoded = OAuthUtils.decodeForm(formUrlEncoded);

        assertEquals(2, formDecoded.size());
        assertEquals("ValueOne", formDecoded.get("MyVariableOne"));
        assertEquals("ValueTwo", formDecoded.get("MyVariableTwo"));
    }

    @Test
    public void testIsFormEncoded() throws Exception {
        String anotherContentType = "text/html; charset=ISO-8859-4";
        String urlEncodedType = "application/x-www-form-urlencoded; charset=UTF-8";

        Boolean falseExpected = OAuthUtils.isFormEncoded(anotherContentType);
        Boolean trueExpected = OAuthUtils.isFormEncoded(urlEncodedType);

        assertEquals(false, falseExpected);
        assertEquals(true, trueExpected);
    }

    @Test
    public void testDecodePercent() throws Exception {
        String encoded = "It%20is%20sunny%20today%2C%20spring%20is%20coming!%3A)";
        String decoded = OAuthUtils.decodePercent(encoded);

        assertEquals("It is sunny today, spring is coming!:)", decoded);
    }

    @Test
    public void testPercentEncode() throws Exception {

        String decoded = "some!@#%weird\"value1";

        String encoded = OAuthUtils.percentEncode(decoded);

        assertEquals("some%21%40%23%25weird%22value1", encoded);
    }

    @Test
    public void testInstantiateClass() throws Exception {
        StringBuilder builder = OAuthUtils.instantiateClass(StringBuilder.class);

        assertNotNull(builder);
    }

    @Test
    public void testInstantiateClassWithParameters() throws Exception {
        StringBuilder builder = OAuthUtils.instantiateClassWithParameters(StringBuilder.class, new Class[]{String.class}, new Object[]{"something"});

        assertNotNull(builder);
        assertEquals("something", builder.toString());
    }

    @Test
    public void testGetAuthHeaderField() throws Exception {
        String token = OAuthUtils.getAuthHeaderField("Bearer 312ewqdsad");

        assertEquals("312ewqdsad", token);
    }

    @Test
    public void testDecodeOAuthHeader() throws Exception {
        Map<String, String> parameters = OAuthUtils.decodeOAuthHeader("Bearer realm=\"example\"");

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("realm", "example");

        assertEquals(expected, parameters);
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

        Boolean trueExpected = OAuthUtils.isEmpty("");
        Boolean trueExpected2 = OAuthUtils.isEmpty(null);

        Boolean falseExpected = OAuthUtils.isEmpty(".");

        assertEquals(true, trueExpected);
        assertEquals(true, trueExpected2);
        assertEquals(false, falseExpected);
    }

    @Test
    public void testHasEmptyValues() throws Exception {

        Boolean trueExpected = OAuthUtils.hasEmptyValues(new String[]{"", "dsadas"});
        Boolean trueExpected2 = OAuthUtils.hasEmptyValues(new String[]{null, "dsadas"});
        Boolean trueExpected3 = OAuthUtils.hasEmptyValues(new String[]{});

        Boolean falseExpected = OAuthUtils.hasEmptyValues(new String[]{"qwerty", "dsadas"});

        assertEquals(true, trueExpected);
        assertEquals(true, trueExpected2);
        assertEquals(true, trueExpected3);
        assertEquals(false, falseExpected);

    }

    @Test
    public void testGetAuthzMethod() throws Exception {

        String authzMethod = OAuthUtils.getAuthzMethod("Basic dXNlcjpwYXNzd29yZA==");

        assertEquals("Basic", authzMethod);

    }

    @Test
    public void testHandleOAuthError() throws Exception {

    }

    @Test
    public void testDecodeScopes() throws Exception {

        Set<String> expected = new HashSet<String>();
        expected.add("email");
        expected.add("full_profile");

        Set<String> scopes = OAuthUtils.decodeScopes("email full_profile");

        assertEquals(expected, scopes);

    }

    @Test
    public void testEncodeScopes() throws Exception {
        Set<String> actual = new HashSet<String>();
        actual.add("photo");
        actual.add("birth_date");

        String actualString = OAuthUtils.encodeScopes(actual);

        assertEquals("birth_date photo", actualString);

    }

    @Test
    public void testIsExpired() throws Exception {

    }

    @Test
    public void testGetIssuedTimeInSec() throws Exception {

    }

    @Test
    public void testIsMultipart() throws Exception {

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getContentType()).andStubReturn("multipart/form-data");
        expect(request.getMethod()).andStubReturn("POST");
        replay(request);

        Boolean actual = OAuthUtils.isMultipart(request);

        assertEquals(true, actual);

        verify(request);

        request = createMock(HttpServletRequest.class);
        expect(request.getContentType()).andStubReturn("multipart/form-data");
        expect(request.getMethod()).andStubReturn("GET");
        replay(request);

        actual = OAuthUtils.isMultipart(request);

        assertEquals(false, actual);

        request = createMock(HttpServletRequest.class);
        expect(request.getContentType()).andStubReturn("application/json");
        expect(request.getMethod()).andStubReturn("POST");
        replay(request);

        actual = OAuthUtils.isMultipart(request);

        assertEquals(false, actual);
    }

    @Test
    public void testHasContentType() throws Exception {

        Boolean falseExpected = OAuthUtils.hasContentType("application/x-www-form-urlencoded; charset=UTF-8", "application/json");
        Boolean trueExpected = OAuthUtils.hasContentType("application/json; charset=UTF-8", "application/json");

        assertEquals(false, falseExpected);
        assertEquals(true, trueExpected);

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
