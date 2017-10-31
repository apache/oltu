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

package org.apache.oltu.oauth2.ext.dynamicreg.client.response;

import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.utils.JSONUtils;
import org.apache.oltu.oauth2.ext.dynamicreg.client.validators.RegistrationValidator;
import org.apache.oltu.oauth2.ext.dynamicreg.common.OAuthRegistration;

import java.util.List;
import java.util.Map;


/**
 *
 *
 *
 */
public class OAuthClientRegistrationResponse extends OAuthClientResponse {

    public OAuthClientRegistrationResponse() {
        validator = new RegistrationValidator();
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        super.init(body, contentType, responseCode);
    }

    @Override
    protected void init(String body, String contentType, int responseCode, Map<String, List<String>> headers) throws OAuthProblemException {
        super.init(body, contentType, responseCode, headers);
    }

    protected void setBody(String body) throws OAuthProblemException {
        try {
            this.body = body;
            parameters = JSONUtils.parseJSON(body);
        } catch (Throwable e) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
                "Invalid response! Response body is not application/json encoded");
        }
    }

    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getClientId() {
        return getParam(OAuthRegistration.Response.CLIENT_ID);
    }

    public String getClientSecret() {
        return getParam(OAuthRegistration.Response.CLIENT_SECRET);
    }

    public String getIssuedAt() {
        return getParam(OAuthRegistration.Response.ISSUED_AT);
    }

    public Long getExpiresIn() {
        String value = getParam(OAuthRegistration.Response.EXPIRES_IN);
        return value == null? null: Long.valueOf(value);
    }

}
