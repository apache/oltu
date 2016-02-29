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
import org.apache.oltu.oauth2.client.demo.model.OAuthRegParams;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.jwt.JWT;
import org.apache.oltu.oauth2.jwt.io.JWTClaimsSetWriter;
import org.apache.oltu.oauth2.jwt.io.JWTHeaderWriter;
import org.apache.oltu.oauth2.jwt.io.JWTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private final JWTReader jwtReader = new JWTReader();

    @Autowired
    private Utils utils;

    @RequestMapping("/index")
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams)
        throws OAuthSystemException, IOException {
        return new ModelAndView("index");
    }

    @RequestMapping("/main/{app}")
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  @ModelAttribute("oauthRegParams") OAuthRegParams oauthRegParams,
                                  @PathVariable("app") String app,
                                  HttpServletResponse res)
        throws OAuthSystemException, IOException {

        boolean selected = false;
        if (Utils.GENERIC.equalsIgnoreCase(app)) {
            selected = true;
        } else if (Utils.GITHUB.equalsIgnoreCase(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.GITHUB_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.GITHUB_TOKEN);
        } else if (Utils.FACEBOOK.equalsIgnoreCase(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.FACEBOOK_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.FACEBOOK_TOKEN);
        } else if (Utils.GOOGLE.equalsIgnoreCase(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.GOOGLE_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.GOOGLE_TOKEN);
        } else if (Utils.LINKEDIN.equalsIgnoreCase(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.LINKEDIN_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.LINKEDIN_TOKEN);
        } else if (Utils.SMART_GALLERY.equalsIgnoreCase(app)) {
            selected = true;
            oauthRegParams.setAuthzEndpoint(Utils.SMART_GALLERY_AUTHZ);
            oauthRegParams.setTokenEndpoint(Utils.SMART_GALLERY_TOKEN);
            oauthRegParams.setRegistrationEndpoint(Utils.SMART_GALLERY_REGISTER);
            oauthRegParams.setApplication(app);
            oauthRegParams.setRedirectUri(utils.getRedirectUri());
            return new ModelAndView("register");
        }
        if (selected) {
            oauthParams.setApplication(app);
            oauthParams.setRedirectUri(utils.getRedirectUri());
            return new ModelAndView("get_authz");
        }

        return new ModelAndView("index");
    }

    @RequestMapping("/decode")
    public ModelAndView decode(@ModelAttribute("oauthParams") OAuthParams oauthParams){
        try {
            JWT jwt = jwtReader.read(oauthParams.getJwt());

            oauthParams.setHeader(new JWTHeaderWriter().write(jwt.getHeader()));
            oauthParams.setClaimsSet(new JWTClaimsSetWriter().write(jwt.getClaimsSet()));
        } catch (Exception e){
            logger.error("Error while decoding the token", e);
            oauthParams.setErrorMessage("Error while decoding the token: " + e);
        }

        return new ModelAndView("index");
    }

}
