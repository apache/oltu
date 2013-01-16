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
 
import javax.servlet.http.HttpServletRequest;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.URLConnectionClient;
import org.apache.amber.oauth2.client.demo.Utils;
import org.apache.amber.oauth2.client.demo.model.OAuthParams;
import org.apache.amber.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthResourceResponse;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 *
 *
 */
@Controller
@RequestMapping("/get_resource")
public class ResourceController {

    @RequestMapping
    public ModelAndView authorize(@ModelAttribute("oauthParams") OAuthParams oauthParams,
                                  HttpServletRequest req) {

        try {
			OAuthClientRequest request=null; 
			
			if (Utils.REQUEST_TYPE_QUERY.equals(oauthParams.getRequestType())){
				request= new OAuthBearerClientRequest(oauthParams.getResourceUrl()).setAccessToken(oauthParams.getAccessToken()).buildQueryMessage();
			}else if (Utils.REQUEST_TYPE_HEADER.equals(oauthParams.getRequestType())){
				request= new OAuthBearerClientRequest(oauthParams.getResourceUrl()).setAccessToken(oauthParams.getAccessToken()).buildHeaderMessage();
			}else if (Utils.REQUEST_TYPE_BODY.equals(oauthParams.getRequestType())){
				request= new OAuthBearerClientRequest(oauthParams.getResourceUrl()).setAccessToken(oauthParams.getAccessToken()).buildBodyMessage();
			}			
			
			OAuthClient client = new OAuthClient(new URLConnectionClient());
			OAuthResourceResponse resourceResponse= client.resource(request, oauthParams.getRequestMethod(), OAuthResourceResponse.class);
			
			if (resourceResponse.getResponseCode()==200){			
				oauthParams.setResource(resourceResponse.getBody());
			}else{
				oauthParams.setErrorMessage(
	                    "Could not access resource: " + resourceResponse.getResponseCode() + " " + resourceResponse.getBody());
			}
		} catch (OAuthSystemException e) {
			 oauthParams.setErrorMessage(e.getMessage());
		} catch (OAuthProblemException e) {
			 oauthParams.setErrorMessage(e.getMessage());
		}
  
        return new ModelAndView("resource");


    }
}
