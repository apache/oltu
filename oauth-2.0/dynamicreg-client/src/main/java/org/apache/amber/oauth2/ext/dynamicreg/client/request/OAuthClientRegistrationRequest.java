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

package org.apache.amber.oauth2.ext.dynamicreg.client.request;

import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.parameters.JSONBodyParametersApplier;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;


/**
 * OAuth Registration Request
 *
 *
 *
 *
 */
public class OAuthClientRegistrationRequest extends OAuthClientRequest {

    protected OAuthClientRegistrationRequest(String url) {
        super(url);
    }

    public static OAuthRegistrationRequestBuilder location(String url, String type) {
        return new OAuthRegistrationRequestBuilder(url, type);
    }

    public static class OAuthRegistrationRequestBuilder extends OAuthRequestBuilder {

        public OAuthRegistrationRequestBuilder(String url, String type) {
            super(url);
            this.parameters.put(OAuthRegistration.Request.TYPE, type);
        }

        public OAuthRegistrationRequestBuilder setName(String value) {
            this.parameters.put(OAuthRegistration.Request.CLIENT_NAME, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setUrl(String value) {
            this.parameters.put(OAuthRegistration.Request.CLIENT_URL, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setDescription(String value) {
            this.parameters.put(OAuthRegistration.Request.CLIENT_DESCRIPTION, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setIcon(String value) {
            this.parameters.put(OAuthRegistration.Request.CLIENT_ICON, value);
            return this;
        }

        public OAuthRegistrationRequestBuilder setRedirectURL(String uri) {
            this.parameters.put(OAuthRegistration.Request.REDIRECT_URL, uri);
            return this;
        }

        public OAuthClientRequest buildJSONMessage() throws OAuthSystemException {
            OAuthClientRequest request = new OAuthClientRegistrationRequest(url);
            this.applier = new JSONBodyParametersApplier();
            return (OAuthClientRequest)applier.applyOAuthParameters(request, parameters);
        }

    }
}
