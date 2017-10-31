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

import org.apache.oltu.oauth2.client.demo.Utils;
import org.apache.oltu.oauth2.client.demo.model.OAuthParams;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RedirectController {
    private Logger logger = LoggerFactory.getLogger(RedirectController.class);

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView handleRedirect(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        logger.debug("creating OAuth authorization response wrapper (/redirect)");

        try {
            // Get OAuth Info
            String clientId = Utils.findCookieValue(request, "clientId");
            String clientSecret = Utils.findCookieValue(request, "clientSecret");
            String authzEndpoint = Utils.findCookieValue(request, "authzEndpoint");
            String tokenEndpoint = Utils.findCookieValue(request, "tokenEndpoint");
            String redirectUri = Utils.findCookieValue(request, "redirectUri");
            String scope = Utils.findCookieValue(request, "scope");
            String state = Utils.findCookieValue(request, "state");

            oauthParams.setClientId(clientId);
            oauthParams.setClientSecret(clientSecret);
            oauthParams.setAuthzEndpoint(authzEndpoint);
            oauthParams.setTokenEndpoint(tokenEndpoint);
            oauthParams.setRedirectUri(redirectUri);
            oauthParams.setScope(Utils.isIssued(scope));
            oauthParams.setState(Utils.isIssued(state));
            
            // Create the response wrapper
            OAuthAuthzResponse oar = null;
            oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);

            // Get Authorization Code
            String code = oar.getCode();
            oauthParams.setAuthzCode(code);

            String app = Utils.findCookieValue(request, "app");
            response.addCookie(new Cookie("app", app));

            oauthParams.setApplication(app);

        } catch (OAuthProblemException e) {
            logger.error("failed to create OAuth authorization response wrapper", e);
            StringBuffer sb = new StringBuffer();
            sb.append("<br />");
            sb.append("Error code: ").append(e.getError()).append("<br />");
            sb.append("Error description: ").append(e.getDescription()).append("<br />");
            sb.append("Error uri: ").append(e.getUri()).append("<br />");
            sb.append("State: ").append(e.getState()).append("<br />");
            oauthParams.setErrorMessage(sb.toString());
            return new ModelAndView("get_authz");
        }

        return new ModelAndView("request_token");

    }
}
