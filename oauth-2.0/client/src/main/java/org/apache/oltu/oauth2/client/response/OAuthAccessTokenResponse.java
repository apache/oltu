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

package org.apache.oltu.oauth2.client.response;

import org.apache.oltu.oauth2.client.validator.TokenValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.token.OAuthToken;

import java.util.List;
import java.util.Map;

/**
 *
 *
 *
 */
public abstract class OAuthAccessTokenResponse extends OAuthClientResponse {

    public OAuthAccessTokenResponse() {
        validator = new TokenValidator();
    }

    public abstract String getAccessToken();

    public abstract String getTokenType();
    
    public abstract Long getExpiresIn();

    public abstract String getRefreshToken();

    public abstract String getScope();

    public abstract OAuthToken getOAuthToken();

    public String getBody() {
        return body;
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        super.init(body, contentType, responseCode);
    }

    @Override
    protected void init(String body, String contentType, int responseCode, Map<String, List<String>> headers) throws OAuthProblemException {
        super.init(body, contentType, responseCode, headers);
    }
}
