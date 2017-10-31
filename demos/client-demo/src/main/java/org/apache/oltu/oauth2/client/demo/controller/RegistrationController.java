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

import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.demo.Utils;
import org.apache.oltu.oauth2.client.demo.exception.ApplicationException;
import org.apache.oltu.oauth2.client.demo.model.OAuthParams;
import org.apache.oltu.oauth2.client.demo.model.OAuthRegParams;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.ext.dynamicreg.client.OAuthRegistrationClient;
import org.apache.oltu.oauth2.ext.dynamicreg.client.request.OAuthClientRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.client.response.OAuthClientRegistrationResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.common.OAuthRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private Utils utils;

    @RequestMapping(value = "/register")
    public ModelAndView authorize(@ModelAttribute("oauthRegParams") OAuthRegParams oauthRegParams,
                                  @ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) throws OAuthSystemException, IOException {

        logger.debug("start processing /register request");

        try {
            utils.validateRegistrationParams(oauthRegParams);

            OAuthClientRequest request = getoAuthClientRequest(oauthRegParams);

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
            logger.error("failed to validate OAuth authorization request parameters", e);
            oauthRegParams.setErrorMessage(e.getMessage());
            return new ModelAndView("register");
        } catch (OAuthProblemException e) {
            logger.error("failed to acquire OAuth client registration info", e);
            oauthRegParams.setErrorMessage(e.getMessage());
            return new ModelAndView("register");
        }
    }

    private OAuthClientRequest getoAuthClientRequest(OAuthRegParams oauthRegParams) throws OAuthSystemException {
        OAuthClientRequest request;
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
        return request;
    }

}
