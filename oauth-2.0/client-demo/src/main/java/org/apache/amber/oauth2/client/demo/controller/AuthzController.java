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

package org.apache.amber.oauth2.client.demo.controller;


import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.amber.oauth2.client.demo.exception.ApplicationException;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.amber.oauth2.client.demo.model.OAuthParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import org.apache.amber.oauth2.client.demo.Utils;

/**
 * Handles requests for the application welcome page.
 *
 */
@Controller
@RequestMapping("/")
public class AuthzController {


    private Logger logger = LoggerFactory.getLogger(AuthzController.class);

    @RequestMapping("/authorize")
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req,
                                  HttpServletResponse res)
        throws OAuthSystemException, IOException {

        try {

            Utils.validateAuthorizationParams(oauthParams);

            res.addCookie(new Cookie("clientId", oauthParams.getClientId()));
            res.addCookie(new Cookie("clientSecret", oauthParams.getClientSecret()));
            res.addCookie(new Cookie("authzEndpoint", oauthParams.getAuthzEndpoint()));
            res.addCookie(new Cookie("tokenEndpoint", oauthParams.getTokenEndpoint()));
            res.addCookie(new Cookie("redirectUri", oauthParams.getRedirectUri()));
            res.addCookie(new Cookie("scope", oauthParams.getScope()));
            res.addCookie(new Cookie("app", oauthParams.getApplication()));

            OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(oauthParams.getAuthzEndpoint())
                .setClientId(oauthParams.getClientId())
                .setRedirectURI(oauthParams.getRedirectUri())
                .setResponseType(ResponseType.CODE.toString())
                .setScope(oauthParams.getScope())
                .buildQueryMessage();

            return new ModelAndView(new RedirectView(request.getLocationUri()));

        } catch (ApplicationException e) {
            oauthParams.setErrorMessage(e.getMessage());
            return new ModelAndView("get_authz");
        }
    }


}
