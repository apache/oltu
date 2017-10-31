/**
 * Copyright 2010 Newcastle University
 * <p>
 * http://research.ncl.ac.uk/smart/
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import org.apache.oltu.oauth2.client.demo.model.OAuthParams;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @RequestMapping("/get_resource")
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) {

        logger.debug("start processing /get_resource request");

        try {
            OAuthClientRequest request = getoAuthClientRequest(oauthParams);

            OAuthClient client = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse = client.resource(request, oauthParams.getRequestMethod(), OAuthResourceResponse.class);

            if (resourceResponse.getResponseCode() == 200) {
                oauthParams.setResource(resourceResponse.getBody());
            } else {
                oauthParams.setErrorMessage(
                        "Could not access resource: " + resourceResponse.getResponseCode() + " " + resourceResponse.getBody());
            }
        } catch (OAuthSystemException e) {
            logger.error("Failed to process get_resource request", e);
            oauthParams.setErrorMessage(e.getMessage());
        } catch (OAuthProblemException e) {
            logger.error("Invalid get_resource request", e);
            oauthParams.setErrorMessage(e.getMessage());
        }

        return new ModelAndView("resource");
    }

    private OAuthClientRequest getoAuthClientRequest(OAuthParams oauthParams) throws OAuthSystemException {
        OAuthClientRequest request = null;

        OAuthBearerClientRequest oAuthBearerClientRequest =
                new OAuthBearerClientRequest(oauthParams.getResourceUrl())
                        .setAccessToken(oauthParams.getAccessToken());
        String requestType = oauthParams.getRequestType();
        if (Utils.REQUEST_TYPE_QUERY.equals(requestType)) {
            request = oAuthBearerClientRequest.buildQueryMessage();
        } else if (Utils.REQUEST_TYPE_HEADER.equals(requestType)) {
            request = oAuthBearerClientRequest.buildHeaderMessage();
        } else if (Utils.REQUEST_TYPE_BODY.equals(requestType)) {
            request = oAuthBearerClientRequest.buildBodyMessage();
        }
        return request;
    }
}
