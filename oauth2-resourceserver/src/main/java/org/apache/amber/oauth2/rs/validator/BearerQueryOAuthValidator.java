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

package org.apache.amber.oauth2.rs.validator;

import static org.apache.amber.oauth2.rs.ResourceServer.getQueryParameterValues;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.common.validators.AbstractValidator;
import org.apache.amber.oauth2.rs.ResourceServer;

/**
 *
 *
 *
 */
public class BearerQueryOAuthValidator extends AbstractValidator {

    @Override
    public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
    }

    @Override
    public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
    }

    @Override
    public void validateRequiredParameters(HttpServletRequest request) throws OAuthProblemException {

        String[] tokens = getQueryParameterValues(request, OAuth.OAUTH_BEARER_TOKEN);
        if (OAuthUtils.hasEmptyValues(tokens)) {
            tokens = getQueryParameterValues(request, OAuth.OAUTH_TOKEN);
            if (OAuthUtils.hasEmptyValues(tokens)) {
                throw OAuthProblemException.error(null, "Missing OAuth token.");
            }
        }

        if (tokens != null && tokens.length > 1) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST, "Multiple tokens attached.");
        }

        String oauthVersionDiff = ResourceServer.getQueryParameterValue(request, OAuth.OAUTH_VERSION_DIFFER);
        if (!OAuthUtils.isEmpty(oauthVersionDiff)) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST,
                                              "Incorrect OAuth version. Found OAuth V1.0.");
        }
    }
}
