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

package org.apache.amber.oauth2.integration;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.junit.Test;


/**
 * This tests against Section 5 of the OAuth 2.0 Draft 10 implementation
 *
 *
 *
 *
 */
public class ResourceTest extends ClientResourceOAuthTest {

    @Test
    public void testResourceAccessBodyValidToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_BODY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection
                .setRequestProperty("Content-Length", Integer.toString(Common.BODY_OAUTH2.length()));
            OutputStream ost = httpURLConnection.getOutputStream();
            PrintWriter pw = new PrintWriter(ost);
            pw.print(Common.BODY_OAUTH2);
            pw.flush();
            pw.close();

            testValidTokenResponse(httpURLConnection);
        }
    }

    @Test
    public void testResourceAccessBodyInvalidToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_BODY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",
                Integer.toString("access_token=randominvalidtoken".length()));
            OutputStream ost = httpURLConnection.getOutputStream();
            PrintWriter pw = new PrintWriter(ost);
            pw.print("access_token=randominvalidtoken");
            pw.flush();
            pw.close();

            testInvalidTokenResponse(httpURLConnection);

        }
    }


    @Test
    public void testResourceAccessBodyNoToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_BODY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            testNoTokenResponse(httpURLConnection);
        }
    }


    @Test
    public void testResourceAccessBodyOAuthWrongVersionToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_BODY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",
                Integer.toString("access_token=randominvalidtoken&oauth_signature_method=HMAC-SHA1".length()));
            OutputStream ost = httpURLConnection.getOutputStream();
            PrintWriter pw = new PrintWriter(ost);
            pw.print("access_token=randominvalidtoken&oauth_signature_method=HMAC-SHA1");
            pw.flush();
            pw.close();

            testWrongOAuthVersionResponse(httpURLConnection);
        }
    }

    @Test
    public void testResourceAccessBodyExpiredToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_BODY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection
                .setRequestProperty("Content-Length", Integer.toString(Common.BODY_OAUTH2_EXPIRED.length()));
            OutputStream ost = httpURLConnection.getOutputStream();
            PrintWriter pw = new PrintWriter(ost);
            pw.print(Common.BODY_OAUTH2_EXPIRED);
            pw.flush();
            pw.close();

            testExpiredTokenResponse(httpURLConnection);

        }
    }


    @Test
    public void testResourceAccessBodyInsufficientToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_BODY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",
                Integer.toString(Common.BODY_OAUTH2_INSUFFICIENT.length()));
            OutputStream ost = httpURLConnection.getOutputStream();
            PrintWriter pw = new PrintWriter(ost);
            pw.print(Common.BODY_OAUTH2_INSUFFICIENT);
            pw.flush();
            pw.close();

            testInsufficientScopeResponse(httpURLConnection);


        }
    }


    @Test
    public void testResourceAccessQueryValidToken() throws Exception {

        URL url = new URL(
            Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_QUERY + "?" + Common.QUERY_OAUTH2);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("GET");

            InputStream inputStream = null;
            if (httpURLConnection.getResponseCode() == 400) {
                inputStream = httpURLConnection.getErrorStream();
            } else {
                inputStream = httpURLConnection.getInputStream();
            }

            String responseBody = OAuthUtils.saveStreamAsString(inputStream);
            assertEquals(Common.ACCESS_TOKEN_VALID, responseBody);
        }

    }

    @Test
    public void testResourceAccessQueryInvalidToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_QUERY + "?"
            + "access_token=randominvalidtoken");
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("GET");

            testInvalidTokenResponse(httpURLConnection);
        }

    }

    @Test
    public void testResourceAccessQueryNoToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_QUERY);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("GET");

            testNoTokenResponse(httpURLConnection);
        }

    }

    @Test
    public void testResourceAccessHeaderValidToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_HEADER);
        URLConnection c = url.openConnection();
        c.addRequestProperty(Common.HEADER_AUTHORIZATION, Common.AUTHORIZATION_HEADER_OAUTH2);

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("GET");

            testValidTokenResponse(httpURLConnection);
        }

    }

    @Test
    public void testResourceAccessHeaderNoToken() throws Exception {

        URL url = new URL(Common.RESOURCE_SERVER + Common.PROTECTED_RESOURCE_HEADER);
        URLConnection c = url.openConnection();

        if (c instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection)c;
            httpURLConnection.setRequestMethod("GET");

            testNoTokenResponse(httpURLConnection);
        }

    }

    void testInvalidTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
        // For the invalid token the response should be
        // - 401
        // - WWW-Authenticate header should be there
        // - realm included
        // - error=invalid_token
        assertEquals(401, httpURLConnection.getResponseCode());
        String wwwAuthHeader = httpURLConnection.getHeaderField(Common.HEADER_WWW_AUTHENTICATE);
        assertNotNull(wwwAuthHeader);
        Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
        String realm = headerValues.get("realm");
        assertNotNull(realm);
        String error = headerValues.get("error");
        assertEquals(OAuthError.ResourceResponse.INVALID_TOKEN, error);
    }


    void testValidTokenResponse(HttpURLConnection httpURLConnection) throws Exception {

        InputStream inputStream = null;
        if (httpURLConnection.getResponseCode() == 400) {
            inputStream = httpURLConnection.getErrorStream();
        } else {
            inputStream = httpURLConnection.getInputStream();
        }
        String responseBody = OAuthUtils.saveStreamAsString(inputStream);
        assertEquals(Common.ACCESS_TOKEN_VALID, responseBody);
    }

    private void testNoTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
        // For the request with no token the response should be
        // - 401
        // - WWW-Authenticate header should be there
        // - only realm should be included
        assertEquals(401, httpURLConnection.getResponseCode());
        String wwwAuthHeader = httpURLConnection.getHeaderField(Common.HEADER_WWW_AUTHENTICATE);
        assertNotNull(wwwAuthHeader);
        Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
        assertEquals(1, headerValues.size());
        String realm = headerValues.get("realm");
        assertNotNull(realm);
    }


    private void testExpiredTokenResponse(HttpURLConnection httpURLConnection) throws Exception {
        // For the invalid token the response should be
        // - 401
        // - WWW-Authenticate header should be there
        // - realm included
        // - error=expired_token
        assertEquals(401, httpURLConnection.getResponseCode());
        String wwwAuthHeader = httpURLConnection.getHeaderField(Common.HEADER_WWW_AUTHENTICATE);
        assertNotNull(wwwAuthHeader);
        Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
        String realm = headerValues.get("realm");
        assertNotNull(realm);
        String error = headerValues.get("error");
        assertEquals(OAuthError.ResourceResponse.EXPIRED_TOKEN, error);
    }

    private void testInsufficientScopeResponse(HttpURLConnection httpURLConnection) throws Exception {
        // For the invalid token the response should be
        // - 403
        // - WWW-Authenticate header should be there
        // - realm included
        // - error=insufficient_scope
        assertEquals(403, httpURLConnection.getResponseCode());
        String wwwAuthHeader = httpURLConnection.getHeaderField(Common.HEADER_WWW_AUTHENTICATE);
        assertNotNull(wwwAuthHeader);
        Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
        String realm = headerValues.get("realm");
        assertNotNull(realm);
        String error = headerValues.get("error");
        assertEquals(OAuthError.ResourceResponse.INSUFFICIENT_SCOPE, error);
    }

    private void testWrongOAuthVersionResponse(HttpURLConnection httpURLConnection) throws Exception {
        // For the wrong OAuth version response
        // - 400
        // - WWW-Authenticate header should be there
        // - error=invalid_request
        assertEquals(400, httpURLConnection.getResponseCode());
        String wwwAuthHeader = httpURLConnection.getHeaderField(Common.HEADER_WWW_AUTHENTICATE);
        assertNotNull(wwwAuthHeader);
        Map<String, String> headerValues = OAuthUtils.decodeOAuthHeader(wwwAuthHeader);
        String realm = headerValues.get("realm");
        assertNotNull(realm);
        String error = headerValues.get("error");
        assertEquals(OAuthError.CodeResponse.INVALID_REQUEST, error);
    }
}
