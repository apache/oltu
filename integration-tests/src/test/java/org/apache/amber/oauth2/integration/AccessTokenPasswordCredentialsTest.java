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
import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.amber.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.amber.oauth2.common.error.OAuthError;


/**
 *
 *
 *
 */
public class AccessTokenPasswordCredentialsTest extends ClientServerOAuthTest {
    private static Logger logger = LoggerFactory.getLogger(AccessTokenPasswordCredentialsTest.class);

    static {
        logger.info("Test class: " + AccessTokenPasswordCredentialsTest.class);
    }

    @Test
    public void testSuccessfullAccesToken() throws Exception {

        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(GrantType.PASSWORD)
            .setClientId(Common.CLIENT_ID)
            .setClientSecret(Common.CLIENT_SECRET)
            .setUsername(Common.USERNAME)
            .setPassword(Common.PASSWORD)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthJSONAccessTokenResponse response = oAuthClient.accessToken(request);

        assertNotNull(response.getAccessToken());
    }

    @Test
    public void testInvalidRequest() throws Exception {

        OAuthClientRequest request = OAuthClientRequest
            .tokenLocation(Common.ACCESS_TOKEN_ENDPOINT)
            .setGrantType(GrantType.PASSWORD)
            .setClientId(Common.CLIENT_ID)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthClientResponse response = null;

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
            .setGrantType(GrantType.PASSWORD)
            .setClientId("wrong_client_id")
            .setClientSecret(Common.CLIENT_SECRET)
            .setUsername(Common.USERNAME)
            .setPassword(Common.PASSWORD)
            .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthClientResponse response = null;

        try {
            oAuthClient.accessToken(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.TokenResponse.INVALID_CLIENT, e.getError());
        }

    }
}