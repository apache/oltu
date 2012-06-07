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
import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.demo.Utils;
import org.apache.amber.oauth2.client.demo.model.OAuthParams;
import org.apache.amber.oauth2.client.demo.model.OAuthRegParams;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.ext.dynamicreg.client.OAuthRegistrationClient;
import org.apache.amber.oauth2.ext.dynamicreg.client.request.OAuthClientRegistrationRequest;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;
import org.apache.amber.oauth2.ext.dynamicreg.client.response.OAuthClientRegistrationResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.apache.amber.oauth2.client.demo.exception.ApplicationException;

/**
 *
 *
 *
 */
@Controller
@RequestMapping("/")
public class RegistrationController {

    @RequestMapping(value = "/register")
    public ModelAndView authorize(@ModelAttribute("oauthRegParams") OAuthRegParams oauthRegParams,
                                  @ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) throws OAuthSystemException, IOException {


        try {

            Utils.validateRegistrationParams(oauthRegParams);

            OAuthClientRequest request = null;
            if (Utils.REG_TYPE_PULL.equals(oauthRegParams.getRegistrationType())) {
                request = OAuthClientRegistrationRequest
                    .location(oauthRegParams.getRegistrationEndpoint(), OAuthRegistration.Type.PULL)
                    .setUrl(oauthRegParams.getUrl())
                    .buildBodyMessage();
            } else {
                request = OAuthClientRegistrationRequest
                    .location(oauthRegParams.getRegistrationEndpoint(), OAuthRegistration.Type.PUSH)
                    .setName(oauthRegParams.getName())
                    .setUrl(oauthRegParams.getUrl())
                    .setDescription(oauthRegParams.getDescription())
                    .setRedirectURL(oauthRegParams.getRedirectUri())
                    .setIcon(oauthRegParams.getIcon())
                    .buildBodyMessage();
            }


            OAuthRegistrationClient client = new OAuthRegistrationClient(new URLConnectionClient());
            OAuthClientRegistrationResponse response = client.clientInfo(request);

            oauthParams.setClientId(response.getClientId());
            oauthParams.setClientSecret(response.getClientSecret());
            oauthParams.setAuthzEndpoint(oauthRegParams.getAuthzEndpoint());
            oauthParams.setTokenEndpoint(oauthRegParams.getTokenEndpoint());
            oauthParams.setApplication(oauthRegParams.getApplication());
            oauthParams.setRedirectUri(oauthRegParams.getRedirectUri());

            return new ModelAndView("get_authz");


        } catch (ApplicationException e) {
            oauthRegParams.setErrorMessage(e.getMessage());
            return new ModelAndView("register");
        } catch (OAuthProblemException e) {
            oauthRegParams.setErrorMessage(e.getMessage());
            return new ModelAndView("register");
        }
    }

}
