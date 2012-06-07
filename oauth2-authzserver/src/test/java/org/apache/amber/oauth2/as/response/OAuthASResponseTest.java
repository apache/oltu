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

package org.apache.amber.oauth2.as.response;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.junit.Assert;
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
            .setAccessToken("access_111")
            .setExpiresIn(400l)
            .setState("ok")
            .setParam("testValue", "value2")
            .buildQueryMessage();

        String url = oAuthResponse.getLocationUri();
         
        Assert.assertEquals("http://www.example.com?testValue=value2&code=code"
            + "#access_token=access_111&state=ok&expires_in=400", url);
        Assert.assertEquals(200, oAuthResponse.getResponseStatus());

    }
    
    @Test
    public void testAuthzResponseWithState() throws Exception {
    	HttpServletRequest request = createMock(HttpServletRequest.class);
    	expect(request.getParameter(OAuth.OAUTH_STATE)).andStubReturn("ok");
    	replay(request);
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(request,200)
            .location("http://www.example.com")
            .setCode("code")
            .setAccessToken("access_111")
            .setExpiresIn("400")
            .setParam("testValue", "value2")
            .buildQueryMessage();

        String url = oAuthResponse.getLocationUri();
 
        Assert.assertEquals("http://www.example.com?testValue=value2&code=code"
            + "#access_token=access_111&state=ok&expires_in=400", url);
        Assert.assertEquals(200, oAuthResponse.getResponseStatus());

    }


    @Test
    public void testTokenResponse() throws Exception {

        OAuthResponse oAuthResponse = OAuthASResponse.tokenResponse(200).setAccessToken("access_token")
            .setExpiresIn("200").setRefreshToken("refresh_token2")
            .buildBodyMessage();

        String body = oAuthResponse.getBody();
        Assert.assertEquals(
            "expires_in=200&refresh_token=refresh_token2&access_token=access_token",
            body);

    }

    @Test
    public void testTokenResponseAdditionalParam() throws Exception {

        OAuthResponse oAuthResponse = OAuthASResponse.tokenResponse(200).setAccessToken("access_token")
            .setExpiresIn("200").setRefreshToken("refresh_token2").setParam("some_param", "new_param")
            .buildBodyMessage();

        String body = oAuthResponse.getBody();
        Assert.assertEquals(
            "some_param=new_param&expires_in=200&refresh_token=refresh_token2&access_token=access_token",
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

        Assert.assertEquals(
            "{\"error_uri\":\"http:\\/\\/www.example.com\\/error\",\"error\":\"access_denied\",\""
                + "error_description\":\"Access denied\"}",
            oAuthResponse.getBody());


        oAuthResponse = OAuthResponse.errorResponse(500)
            .location("http://www.example.com/redirect?param2=true").error(ex).buildQueryMessage();
        Assert.assertEquals(
            "http://www.example.com/redirect?param2=true&error_uri=http%3A%2F%2Fwww.example.com%2Ferror"
                + "&error=access_denied&error_description=Access+denied",
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
        Assert.assertEquals(
            "http://www.example.com/redirect?param2=true&error_uri=http%3A%2F%2Fwww.example.com%2Ferror"
                + "&error=access_denied&error_description=Access+denied",
            oAuthResponse.getLocationUri());
    }

    @Test
    public void testHeaderResponse() throws Exception {
    	HttpServletRequest request = createMock(HttpServletRequest.class);
        OAuthResponse oAuthResponse = OAuthASResponse.authorizationResponse(request,400).setCode("oauth_code")
            .setState("state_ok")
            .buildHeaderMessage();

        String header = oAuthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE);
        Assert.assertEquals("Bearer state=\"state_ok\",code=\"oauth_code\"", header);

        header = oAuthResponse.getHeaders().get(OAuth.HeaderType.WWW_AUTHENTICATE);
        Assert.assertEquals("Bearer state=\"state_ok\",code=\"oauth_code\"", header);
    }

}
