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

package org.apache.amber.oauth2.client.response;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.client.validator.CodeTokenValidator;
import org.apache.amber.oauth2.client.validator.CodeValidator;
import org.apache.amber.oauth2.client.validator.OAuthClientValidator;
import org.apache.amber.oauth2.client.validator.TokenValidator;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;


/**
 *
 *
 *
 */
public class OAuthAuthzResponse extends OAuthClientResponse {

    private HttpServletRequest request;

    protected OAuthAuthzResponse(HttpServletRequest request, OAuthClientValidator validator) {
        this.request = request;
        Map<String, String[]> params = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (!OAuthUtils.hasEmptyValues(values)) {
                parameters.put(key, values[0]);
            }
        }
        this.validator = validator;
    }

    public static OAuthAuthzResponse oauthCodeAuthzResponse(HttpServletRequest request)
        throws OAuthProblemException {
        OAuthAuthzResponse response = new OAuthAuthzResponse(request, new CodeValidator());
        response.validate();
        return response;
    }

    public static OAuthAuthzResponse oAuthCodeAndTokenAuthzResponse(HttpServletRequest request)
        throws OAuthProblemException {
        OAuthAuthzResponse response = new OAuthAuthzResponse(request, new CodeTokenValidator());
        response.validate();
        return response;
    }

    public static OAuthAuthzResponse oauthTokenAuthzResponse(HttpServletRequest request)
        throws OAuthProblemException {
        OAuthAuthzResponse response = new OAuthAuthzResponse(request, new TokenValidator());
        response.validate();
        return response;
    }

    public String getAccessToken() {
        return getParam(OAuth.OAUTH_ACCESS_TOKEN);
    }

    public Long getExpiresIn() {
        String value = getParam(OAuth.OAUTH_EXPIRES_IN);
        return value == null? null: Long.valueOf(value);
    }

    public String getScope() {
        return getParam(OAuth.OAUTH_SCOPE);
    }

    public String getCode() {
        return getParam(OAuth.OAUTH_CODE);
    }

    public String getState() {
        return getParam(OAuth.OAUTH_STATE);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    protected void setBody(String body) {
        this.body = body;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

}
