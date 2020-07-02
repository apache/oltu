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
import java.io.PrintWriter;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.provider.demo.Common;

/**
 *
 *
 *
 */

public class TokenEndpoint extends HttpServlet {
	public static final String INVALID_CLIENT_DESCRIPTION = "Client authentication failed (e.g., unknown client, no client authentication included, or unsupported authentication method).";

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		try {
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

			// check if clientid is valid
			if (!Common.CLIENT_ID.equals(oauthRequest.getClientId())) {
				OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildJSONMessage();
				response.sendError(401);
				return;
			}

			// check if client_secret is valid
			if (!Common.CLIENT_SECRET.equals(oauthRequest.getClientSecret())) {
				OAuthResponse resp = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildJSONMessage();
				response.sendError(401);
				return;
			}

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					.equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!Common.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
					OAuthResponse resp = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid authorization code")
							.buildJSONMessage();
					response.sendError(401);
					return;
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					.equals(GrantType.PASSWORD.toString())) {
				if (!Common.PASSWORD.equals(oauthRequest.getPassword())
						|| !Common.USERNAME.equals(oauthRequest.getUsername())) {
					OAuthResponse resp = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid username or password")
							.buildJSONMessage();
					response.sendError(401);
					return;
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
					.equals(GrantType.REFRESH_TOKEN.toString())) {
				// refresh token is not supported in this implementation
				OAuthResponse resp = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT)
						.setErrorDescription("invalid username or password")
						.buildJSONMessage();
				response.sendError(401);
				return;
			}

			OAuthResponse resp = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(Common.ACCESS_TOKEN_VALID)
					.setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString())
					.setExpiresIn("3600")
					.buildJSONMessage();

			response.setStatus(resp.getResponseStatus());
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println(resp.getBody().toString());
			pw.flush();
			pw.close();
			return;

			//if something goes wrong


		} catch (OAuthSystemException e) {
			System.out.println("Caught OAuthSystemException: " + e.getMessage());
			return;
		} catch (OAuthProblemException e) {
			System.out.println("Caught OAuthProblemException: " + e.getMessage());
			return;
		}
	}
}

