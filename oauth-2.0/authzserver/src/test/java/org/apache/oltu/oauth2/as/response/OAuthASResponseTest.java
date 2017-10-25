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

package org.apache.oltu.oauth2.as.response;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.junit.Test;

/**
 *
 *
 *
 */
public class OAuthASResponseTest {

    @Test
    public void testAuthzResponse() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(request,200)
            .location("http://www.example.com")
            .setCode("code")
            .setState("ok")
            .setParam("testValue", "value2")
            .buildQueryMessage();

        String url = oAuthResponse.getLocationUri();

        assertEquals("http://www.example.com?code=code&state=ok&testValue=value2", url);
        assertEquals(200, oAuthResponse.getResponseStatus());

    }

    @Test
    public void testAuthzResponseWithState() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_STATE)).andStubReturn("ok");
        replay(request);
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(request,200)
            .location("http://www.example.com")
            .setCode("code")
            .setParam("testValue", "value2")
            .buildQueryMessage();

        String url = oAuthResponse.getLocationUri();

        assertEquals("http://www.example.com?code=code&state=ok&testValue=value2", url);
        assertEquals(200, oAuthResponse.getResponseStatus());

    }

    @Test
    public void testAuthzImplicitResponseWithState() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_STATE)).andStubReturn("ok");
        replay(request);
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(request, 200)
                .location("http://www.example.com")
                .setAccessToken("access_111")
                .setTokenType("bearer")
                .setExpiresIn("400")
                .setParam("testValue", "value2")
                .buildQueryMessage();

        String url = oAuthResponse.getLocationUri();
        assertEquals("http://www.example.com#access_token=access_111&state=ok&token_type=bearer&expires_in=400&testValue=value2", url);
        assertEquals(200, oAuthResponse.getResponseStatus());
    }

    @Test
    public void testTokenResponse() throws Exception {

        OAuthResponse oAuthResponse = OAuthASResponse.tokenResponse(200).setAccessToken("access_token")
            .setTokenType("bearer").setExpiresIn("200").setRefreshToken("refresh_token2")
            .buildBodyMessage();

        String body = oAuthResponse.getBody();
        assertEquals(
            "access_token=access_token&refresh_token=refresh_token2&token_type=bearer&expires_in=200",
            body);

    }

    @Test
    public void testTokenResponseAdditionalParam() throws Exception {

        OAuthResponse oAuthResponse = OAuthASResponse.tokenResponse(200).setAccessToken("access_token")
            .setTokenType("bearer").setExpiresIn("200").setRefreshToken("refresh_token2").setParam("some_param", "new_param")
            .buildBodyMessage();

        String body = oAuthResponse.getBody();

        assertEquals(
            "access_token=access_token&refresh_token=refresh_token2&some_param=new_param&token_type=bearer&expires_in=200",
            body);

    }

    @Test
    public void testErrorResponse() throws Exception {

        OAuthProblemException ex = OAuthProblemException
            .error(OAuthError.CodeResponse.ACCESS_DENIED, "Access denied")
            .setParameter("testparameter", "testparameter_value")
            .scope("album")
            .uri("http://www.example.com/error");

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(400).error(ex).buildJSONMessage();
        assertEquals(
            "{\"error_description\":\"Access denied\",\"error\":\"access_denied\",\"error_uri\":\"http://www.example.com/error\"}",
            oAuthResponse.getBody());


        oAuthResponse = OAuthResponse.errorResponse(500)
            .location("http://www.example.com/redirect?param2=true").error(ex).buildQueryMessage();
        assertEquals(
            "http://www.example.com/redirect?param2=true&error_description=Access+denied&error=access_denied&error_uri=http%3A%2F%2Fwww.example.com%2Ferror",
            oAuthResponse.getLocationUri());
    }

    @Test
    public void testErrorResponse2() throws Exception {
        OAuthProblemException ex = OAuthProblemException
            .error(OAuthError.CodeResponse.ACCESS_DENIED, "Access denied")
            .setParameter("testparameter", "testparameter_value")
            .scope("album")
            .uri("http://www.example.com/error");

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(500)
            .location("http://www.example.com/redirect?param2=true").error(ex).buildQueryMessage();

        assertEquals(
            "http://www.example.com/redirect?param2=true&error_description=Access+denied&error=access_denied&error_uri=http%3A%2F%2Fwww.example.com%2Ferror",
            oAuthResponse.getLocationUri());
    }

    @Test
    public void testHeaderResponse() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(request,400).setCode("oauth_code")
            .setState("state_ok")
            .buildHeaderMessage();

        String header = oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE);
        assertEquals("Bearer code=\"oauth_code\",state=\"state_ok\"", header);

        header = oAuthResponse.getHeaders().get(OAuth.HeaderType.WWW_AUTHENTICATE);
        assertEquals("Bearer code=\"oauth_code\",state=\"state_ok\"", header);
    }

}
