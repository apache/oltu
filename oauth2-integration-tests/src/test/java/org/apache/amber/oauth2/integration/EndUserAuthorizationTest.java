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

import java.net.HttpURLConnection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthAuthzResponse;
import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.junit.Test;

/**
 *
 *
 *
 */
@Path("/")
public class EndUserAuthorizationTest extends ClientServerOAuthTest {

    @Test
    public void testWrongParametersEndUserAuthorization() throws Exception {


        OAuthClientRequest request = OAuthClientRequest
            .authorizationLocation(Common.AUTHORIZATION_ENPOINT)
            .setClientId(Common.CLIENT_ID)
            .setRedirectURI(Common.REDIRECT_URL)
            .buildQueryMessage();

        Common.doRequest(request);

    }

    @Test
    public void testCorrectParametersEndUserAuthorization() throws Exception {

        OAuthClientRequest request = OAuthClientRequest
            .authorizationLocation(Common.AUTHORIZATION_ENPOINT)
            .setClientId(Common.CLIENT_ID)
            .setRedirectURI(Common.REDIRECT_URL + "1")
            .setResponseType(ResponseType.CODE.toString())
            .buildQueryMessage();

        Common.doRequest(request);

    }

    @Test
    public void testTokenResponse() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
            .authorizationLocation(Common.AUTHORIZATION_ENPOINT)
            .setClientId(Common.CLIENT_ID)
            .setRedirectURI(Common.REDIRECT_URL + "2")
            .setResponseType(ResponseType.TOKEN.toString())
            .buildQueryMessage();

        HttpURLConnection c = Common.doRequest(request);
        String fragment = c.getURL().toURI().getFragment();
        Map<String, Object> map = OAuthUtils.decodeForm(fragment);

        assertNotNull(map.get(OAuth.OAUTH_EXPIRES_IN));
        assertNotNull(map.get(OAuth.OAUTH_ACCESS_TOKEN));

    }

    @GET
    @Path("/redirect")
    public Response callback(@Context HttpServletRequest request) throws Exception {

        OAuthClientResponse resp = null;
        try {
            OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            fail("exception expected");
        } catch (OAuthProblemException e) {
            assertEquals(OAuthError.CodeResponse.INVALID_REQUEST, e.getError());
        }


        return Response.ok().build();
    }

    @GET
    @Path("/redirect1")
    public Response callback1(@Context HttpServletRequest request) throws Exception {

        OAuthClientResponse resp = null;
        try {
            OAuthAuthzResponse.oauthCodeAuthzResponse(request);
        } catch (OAuthProblemException e) {
            fail("exception not expected");
        }


        return Response.ok().build();
    }

    @GET
    @Path("/redirect2")
    public Response callback2(@Context HttpServletRequest request, @Context HttpServletResponse r)
        throws Exception {
        return Response.ok().build();
    }

}