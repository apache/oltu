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

package org.apache.amber.oauth2.ext.dynamicreg.server.response;

import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;

/**
 *
 *
 *
 */
public class OAuthServerRegistrationResponse extends OAuthASResponse {

    protected OAuthServerRegistrationResponse(String url, int responseStatus) {
        super(url, responseStatus);
    }

    public static OAuthRegistrationResponseBuilder status(int code) {
        return new OAuthRegistrationResponseBuilder(code);
    }

    public static class OAuthRegistrationResponseBuilder extends OAuthResponseBuilder {

        public OAuthRegistrationResponseBuilder(int responseCode) {
            super(responseCode);
        }

        public OAuthRegistrationResponseBuilder setClientId(String value) {
            this.parameters.put(OAuthRegistration.Response.CLIENT_ID, value);
            return this;
        }

        public OAuthRegistrationResponseBuilder setClientSecret(String value) {
            this.parameters.put(OAuthRegistration.Response.CLIENT_SECRET, value);
            return this;
        }

        public OAuthRegistrationResponseBuilder setIssuedAt(String value) {
            this.parameters.put(OAuthRegistration.Response.ISSUED_AT, value);
            return this;
        }

        public OAuthRegistrationResponseBuilder setExpiresIn(String value) {
            this.parameters.put(OAuthRegistration.Response.EXPIRES_IN, Long.parseLong(value));
            return this;
        }
        
        public OAuthRegistrationResponseBuilder setExpiresIn(Long value) {
            this.parameters.put(OAuthRegistration.Response.EXPIRES_IN, value);
            return this;
        }

        public OAuthRegistrationResponseBuilder setParam(String key, String value) {
            this.parameters.put(key, value);
            return this;
        }

        public OAuthRegistrationResponseBuilder location(String location) {
            this.location = location;
            return this;
        }
    }
}
