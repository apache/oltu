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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.provider.demo.Common;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;

/**
 *
 *
 *
 */

public class ResourceBodyEndpoint extends HttpServlet 
{
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException 
	{
        try {
            // Make the OAuth Request out of this request and validate it
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request,
                ParameterStyle.BODY);

            // Get the access token
            String accessToken = oauthRequest.getAccessToken();

            // Check if the token is valid
            if (Common.ACCESS_TOKEN_VALID.equals(accessToken)) {

                // Respond the resource
				response.setStatus(200);
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter pw = response.getWriter();
				pw.println(Common.USERNAME);
				pw.flush();
				pw.close();	
				return;
            }


            // Check if the token is not expired
            if (Common.ACCESS_TOKEN_EXPIRED.equals(accessToken)) {

                // Return the OAuth error message
                OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm(Common.RESOURCE_SERVER_NAME)
                    .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                    .buildJSONMessage();

                // Return the error message
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
            }


            // Check if the token is sufficient
            if (Common.ACCESS_TOKEN_INSUFFICIENT.equals(accessToken)) {

                // Return the OAuth error message
                OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_FORBIDDEN)
                    .setRealm(Common.RESOURCE_SERVER_NAME)
                    .setError(OAuthError.ResourceResponse.INSUFFICIENT_SCOPE)
                    .buildJSONMessage();

                // Return the error message
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
            }

            // Return the OAuth error message
            OAuthResponse oauthResponse = OAuthRSResponse
                .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setRealm(Common.RESOURCE_SERVER_NAME)
                .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                .buildJSONMessage();

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;	

        } catch (OAuthSystemException e) {
			System.out.println("Caught exception: " + e.getMessage());
		} catch (OAuthProblemException e) {

            // Check if the error code has been set
            String errorCode = e.getError();
			try {
				if (OAuthUtils.isEmpty(errorCode)) {

					// Return the OAuth error message
					OAuthResponse oauthResponse = OAuthRSResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(Common.RESOURCE_SERVER_NAME)
						.buildJSONMessage();

					// If no error code then return a standard 401 Unauthorized response
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}

				OAuthResponse oauthResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setRealm(Common.RESOURCE_SERVER_NAME)
					.setError(e.getError())
					.setErrorDescription(e.getDescription())
					.setErrorUri(e.getUri())
					.buildJSONMessage();

				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;

			} catch (OAuthSystemException e2) {
				System.out.println("Caught exception: " + e2.getMessage());
			}
        }
    }
}
