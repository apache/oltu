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

package org.apache.oltu.oauth2.client.demo.controller;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.demo.Utils;
import org.apache.oltu.oauth2.client.demo.exception.ApplicationException;
import org.apache.oltu.oauth2.client.demo.model.OAuthParams;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.jwt.JWT;
import org.apache.oltu.oauth2.jwt.io.JWTClaimsSetWriter;
import org.apache.oltu.oauth2.jwt.io.JWTHeaderWriter;
import org.apache.oltu.openidconnect.client.response.OpenIdConnectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

@Controller
public class TokenController {
    private Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private Utils utils;

    @RequestMapping("/get_token")
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) throws OAuthSystemException, IOException {
        logger.debug("authorizing");

        try {
            utils.validateTokenParams(oauthParams);

            OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(oauthParams.getTokenEndpoint())
                .setClientId(oauthParams.getClientId())
                .setClientSecret(oauthParams.getClientSecret())
                .setRedirectURI(oauthParams.getRedirectUri())
                .setCode(oauthParams.getAuthzCode())
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .buildBodyMessage();

            OAuthClient client = new OAuthClient(new URLConnectionClient());
            String app = Utils.findCookieValue(req, "app");

            Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;

            if (Utils.FACEBOOK.equalsIgnoreCase(app)) {
                oauthParams.setResourceUrl(Utils.FACEBOOK_RESOURCE_URL);
            } else if (Utils.GITHUB.equalsIgnoreCase(app)) {
                cl = GitHubTokenResponse.class;
                oauthParams.setResourceUrl(Utils.GITHUB_RESOURCE_URL);
            } else if (Utils.GOOGLE.equalsIgnoreCase(app)) {
                cl = OpenIdConnectResponse.class;
                oauthParams.setResourceUrl(Utils.GOOGLE_RESOURCE_URL);
            } else if (Utils.LINKEDIN.equalsIgnoreCase(app)) {
                oauthParams.setResourceUrl(Utils.LINKEDIN_RESOURCE_URL);
            }

            OAuthAccessTokenResponse oauthResponse = client.accessToken(request, cl);

            oauthParams.setAccessToken(oauthResponse.getAccessToken());
            oauthParams.setExpiresIn(oauthResponse.getExpiresIn());
            oauthParams.setRefreshToken(Utils.isIssued(oauthResponse.getRefreshToken()));

            if (Utils.GOOGLE.equalsIgnoreCase(app)) {
                OpenIdConnectResponse openIdConnectResponse = ((OpenIdConnectResponse) oauthResponse);
                JWT idToken = openIdConnectResponse.getIdToken();
                if (idToken != null) {
                    oauthParams.setIdToken(idToken.getRawString());

                    oauthParams.setHeader(new JWTHeaderWriter().write(idToken.getHeader()));
                    oauthParams.setClaimsSet(new JWTClaimsSetWriter().write(idToken.getClaimsSet()));

                    URI uri = URI.create(oauthParams.getTokenEndpoint());
                    oauthParams.setIdTokenValid(openIdConnectResponse.checkId(uri.getHost(), oauthParams.getClientId()));
                }
            }

            return new ModelAndView("get_resource");

        } catch (ApplicationException e) {
            logger.error("failed to validate OAuth token request parameters", e);
            oauthParams.setErrorMessage(e.getMessage());
            return new ModelAndView("request_token");
        } catch (OAuthProblemException e) {
            logger.error("failed to acquire OAuth access token", e);
            StringBuffer sb = new StringBuffer();
            sb.append("<br />");
            sb.append("Error code: ").append(e.getError()).append("<br />");
            sb.append("Error description: ").append(e.getDescription()).append("<br />");
            sb.append("Error uri: ").append(e.getUri()).append("<br />");
            sb.append("State: ").append(e.getState()).append("<br />");
            oauthParams.setErrorMessage(sb.toString());
            return new ModelAndView("get_authz");
        }
    }
}
