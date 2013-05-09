/**
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

package org.apache.oltu.oauth2.as.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.validator.PasswordValidator;
import org.apache.oltu.oauth2.as.validator.UnauthenticatedAuthorizationCodeValidator;
import org.apache.oltu.oauth2.as.validator.UnauthenticatedPasswordValidator;
import org.apache.oltu.oauth2.as.validator.UnauthenticatedRefreshTokenValidator;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.common.validators.OAuthValidator;

/**
 * The Unauthenticated OAuth Authorization Server class that validates whether a given HttpServletRequest is a valid
 * OAuth Token request.
 * <p/>
 * This class accepts requests that are NOT authenticated, that is requests that do not contain a client_secret.
 * <p/>
 * IMPORTANT: The ClientCredentials Grant Type is NOT supported by this class since client authentication is required
 * for this grant type. In order to support the client credentials grant type please use the {@link OAuthTokenRequest}
 * class.
 */
public class OAuthUnauthenticatedTokenRequest extends AbstractOAuthTokenRequest {

    public OAuthUnauthenticatedTokenRequest(HttpServletRequest request) throws OAuthSystemException,
            OAuthProblemException {
        super(request);
    }

    @Override
    protected OAuthValidator<HttpServletRequest> initValidator() throws OAuthProblemException, OAuthSystemException {
        validators.put(GrantType.PASSWORD.toString(), UnauthenticatedPasswordValidator.class);
        validators.put(GrantType.AUTHORIZATION_CODE.toString(), UnauthenticatedAuthorizationCodeValidator.class);
        validators.put(GrantType.REFRESH_TOKEN.toString(), UnauthenticatedRefreshTokenValidator.class);
        return super.initValidator();
    }
}
