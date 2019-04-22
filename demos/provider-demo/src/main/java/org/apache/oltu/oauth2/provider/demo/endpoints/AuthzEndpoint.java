/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 *		 Author : Yang Hong
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

package org.apache.oltu.oauth2.provider.demo.endpoints;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.provider.demo.Common;

public class AuthzEndpoint extends HttpServlet 
{ 
	private static final String USER_OAUTH_APPROVAL = "user_oauth_approval";
	protected void doGet(HttpServletRequest request, 
		HttpServletResponse response) throws ServletException, IOException 
	{
		try {
			// Dynamically recognize an OAuth profile based on request characteristic (params,
			// method, content type etc.), perform validation
    	
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        
			String username = oauthRequest.getParam(OAuth.OAUTH_USERNAME);        
			String password = oauthRequest.getParam(OAuth.OAUTH_PASSWORD);
        
			if (username == null || password == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				return;
			}	 
 	
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
    	
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
					.authorizationResponse(request,HttpServletResponse.SC_FOUND);

			if (Common.USERNAME.equals(username) && Common.PASSWORD.equals(password)) {
				String oauthApproval = oauthRequest.getParam(USER_OAUTH_APPROVAL);
        	
				if (oauthApproval == null) {
					request.getRequestDispatcher("/jsp/user_approve.jsp").forward(request, response);
					return;
				} else if (Boolean.valueOf(oauthApproval)) {
					if (responseType.equals(ResponseType.CODE.toString())) {
						builder.setCode(Common.AUTHORIZATION_CODE);
					}
					if (responseType.equals(ResponseType.TOKEN.toString())) {
						builder.setAccessToken(oauthIssuerImpl.accessToken());
						builder.setExpiresIn(3600l);
					}        	   	
				} else {
					response.sendRedirect("/jsp/user_deny_access.jsp");
					return;
				}
	        	
			} else {
        		builder.setCode(Common.ACCESS_TOKEN_INSUFFICIENT);
			}
        
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

			//build OAuth response
			final OAuthResponse resp = builder.location(redirectURI).buildQueryMessage();
			response.sendRedirect(resp.getLocationUri());
			return;
			//if something goes wrong
		} catch (OAuthSystemException e) {
			System.out.println("Caught exception: " + e.getMessage());
		} catch (OAuthProblemException e) {
			final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);
			String redirectUri = e.getRedirectUri();

			if (OAuthUtils.isEmpty(redirectUri)) {
				throw new WebApplicationException(
					responseBuilder.entity("OAuth callback url needs to be provided by client!").build());
			}        
        
			try {
				final OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
														.error(e)
														.location(redirectUri).buildQueryMessage();
				response.sendRedirect(resp.getLocationUri());
				return;
			} catch (OAuthSystemException e2) {
				System.out.println("Caught exception: " + e2.getMessage());
			} 
		} 
	}
}
