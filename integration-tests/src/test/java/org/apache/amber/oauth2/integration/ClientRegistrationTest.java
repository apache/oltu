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

import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.ext.dynamicreg.client.OAuthRegistrationClient;
import org.apache.amber.oauth2.ext.dynamicreg.client.request.OAuthClientRegistrationRequest;
import org.junit.Test;
import org.apache.amber.oauth2.ext.dynamicreg.client.response.OAuthClientRegistrationResponse;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;

import org.apache.amber.oauth2.common.exception.OAuthProblemException;

/**
 *
 *
 *
 */
public class ClientRegistrationTest extends ClientServerOAuthTest {

    @Test
    public void testPushMetadataRegistration() throws Exception {

        OAuthClientRequest request = OAuthClientRegistrationRequest
            .location(CommonExt.REGISTRATION_ENDPOINT, OAuthRegistration.Type.PUSH)
            .setName(CommonExt.APP_NAME)
            .setUrl(CommonExt.APP_URL)
            .setDescription(CommonExt.APP_DESCRIPTION)
            .setIcon(CommonExt.APP_ICON)
            .setRedirectURL(CommonExt.APP_REDIRECT_URI)
            .buildJSONMessage();

        OAuthRegistrationClient oauthclient = new OAuthRegistrationClient(new URLConnectionClient());
        OAuthClientRegistrationResponse response = oauthclient.clientInfo(request);

        assertEquals(CommonExt.CLIENT_ID, response.getClientId());
        assertEquals(CommonExt.CLIENT_SECRET, response.getClientSecret());
        assertEquals(CommonExt.EXPIRES_IN, response.getExpiresIn());
        assertEquals(CommonExt.ISSUED_AT, response.getIssuedAt());

    }

    @Test
    public void testInvalidType() throws Exception {

        OAuthClientRequest request = OAuthClientRegistrationRequest
            .location(CommonExt.REGISTRATION_ENDPOINT, "unknown_type")
            .setName(CommonExt.APP_NAME)
            .setUrl(CommonExt.APP_URL)
            .setDescription(CommonExt.APP_DESCRIPTION)
            .setIcon(CommonExt.APP_ICON)
            .setRedirectURL(CommonExt.APP_REDIRECT_URI)
            .buildBodyMessage();

        OAuthRegistrationClient oauthclient = new OAuthRegistrationClient(new URLConnectionClient());
        try {
            OAuthClientRegistrationResponse response = oauthclient.clientInfo(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertNotNull(e.getError());
        }

    }

}
