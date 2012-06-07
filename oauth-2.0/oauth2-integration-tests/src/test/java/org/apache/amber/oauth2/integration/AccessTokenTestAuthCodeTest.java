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

import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.junit.Test;

/**
 *
 *
 *
 */
public class AccessTokenTestAuthCodeTest extends ClientServerOAuthTest {


    @Test
    public void testSuccessfullAccesToken() throws Exception {

        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(GrantType.AUTHORIZATION_CODE)
            .setCode(Common.AUTHORIZATION_CODE)
            .setRedirectURI(Common.REDIRECT_URL)
            .setClientId(Common.CLIENT_ID)
            .setClientSecret(Common.CLIENT_SECRET)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthAccessTokenResponse response = oAuthClient.accessToken(request);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getExpiresIn());


    }

    @Test
    public void testSuccessfullAccesTokenGETMethod() throws Exception {

        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(GrantType.AUTHORIZATION_CODE)
            .setCode(Common.AUTHORIZATION_CODE)
            .setRedirectURI(Common.REDIRECT_URL)
            .setClientId(Common.CLIENT_ID)
            .setClientSecret(Common.CLIENT_SECRET)
            .buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthAccessTokenResponse response = oAuthClient.accessToken(request, OAuth.HttpMethod.GET);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getExpiresIn());


    }

    @Test
    public void testNoneGrantType() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(null)
            .setClientId(Common.CLIENT_ID)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());


        try {
            oAuthClient.accessToken(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
    }

    @Test
    public void testInvalidRequest() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setClientId(Common.CLIENT_ID)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());


        try {
            oAuthClient.accessToken(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
    }

    @Test
    public void testInvalidClient() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(GrantType.AUTHORIZATION_CODE)
            .setCode(Common.AUTHORIZATION_CODE)
            .setClientId("unknownid")
            .setRedirectURI(Common.REDIRECT_URL)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());


        try {
            oAuthClient.accessToken(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }
    }

    @Test
    public void testInvalidGrantType() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setParameter(OAuth.OAUTH_GRANT_TYPE, "unknown_grant_type")
            .setCode(Common.AUTHORIZATION_CODE)
            .setRedirectURI(Common.REDIRECT_URL)
            .setClientId(Common.CLIENT_ID)
            .buildBodyMessage();

        OAuthClient oAuthclient = new OAuthClient(new URLConnectionClient());


        try {
            oAuthclient.accessToken(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

    }

    @Test
    public void testInvalidCode() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(GrantType.AUTHORIZATION_CODE)
            .setRedirectURI(Common.REDIRECT_URL)
            .setCode("unknown_code")
            .setClientId(Common.CLIENT_ID)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        try {
            oAuthClient.accessToken(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }

    }
}