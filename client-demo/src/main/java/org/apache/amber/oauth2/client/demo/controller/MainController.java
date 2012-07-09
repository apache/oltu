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
import javax.servlet.http.HttpServletResponse;

import org.apache.amber.oauth2.client.demo.Utils;
import org.apache.amber.oauth2.client.demo.model.OAuthRegParams;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.amber.oauth2.client.demo.model.OAuthParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 *
 *
 */
@Controller
@RequestMapping("/")
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

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
        if (Utils.GITHUB.equals(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.GITHUB_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.GITHUB_TOKEN);

        } else if (Utils.FACEBOOK.equals(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.FACEBOOK_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.FACEBOOK_TOKEN);
        } else if (Utils.GOWALLA.equals(app)) {
            selected = true;
            oauthParams.setAuthzEndpoint(Utils.GOWALLA_AUTHZ);
            oauthParams.setTokenEndpoint(Utils.GOWALLA_TOKEN);
        } else if (Utils.SMART_GALLERY.equals(app)) {
            selected = true;
            oauthRegParams.setAuthzEndpoint(Utils.SMART_GALLERY_AUTHZ);
            oauthRegParams.setTokenEndpoint(Utils.SMART_GALLERY_TOKEN);
            oauthRegParams.setRegistrationEndpoint(Utils.SMART_GALLERY_REGISTER);
            oauthRegParams.setApplication(app);
            oauthRegParams.setRedirectUri(Utils.REDIRECT_URI);
            return new ModelAndView("register");
        }
        if (selected) {
            oauthParams.setApplication(app);
            oauthParams.setRedirectUri(Utils.REDIRECT_URI);
            return new ModelAndView("get_authz");
        }

        return new ModelAndView("index");
    }
}
