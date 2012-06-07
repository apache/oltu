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

package org.apache.amber.oauth2.common.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.parameters.BodyURLEncodedParametersApplier;
import org.apache.amber.oauth2.common.parameters.JSONBodyParametersApplier;
import org.apache.amber.oauth2.common.parameters.OAuthParametersApplier;
import org.apache.amber.oauth2.common.parameters.QueryParameterApplier;
import org.apache.amber.oauth2.common.parameters.WWWAuthHeaderParametersApplier;

/**
 *
 *
 *
 */
public class OAuthResponse implements OAuthMessage {

    protected int responseStatus;
    protected String uri;
    protected String body;

    protected Map<String, String> headers = new HashMap<String, String>();

    protected OAuthResponse(String uri, int responseStatus) {
        this.uri = uri;
        this.responseStatus = responseStatus;
    }

    public static OAuthResponseBuilder status(int code) {
        return new OAuthResponseBuilder(code);
    }

    public static OAuthErrorResponseBuilder errorResponse(int code) {
        return new OAuthErrorResponseBuilder(code);
    }

    @Override
    public String getLocationUri() {
        return uri;
    }

    @Override
    public void setLocationUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    @Override
    public void addHeader(String name, String header) {
        headers.put(name, header);
    }

    public static class OAuthResponseBuilder {

        protected OAuthParametersApplier applier;
        protected Map<String, Object> parameters = new HashMap<String, Object>();
        protected int responseCode;
        protected String location;

        public OAuthResponseBuilder(int responseCode) {
            this.responseCode = responseCode;
        }

        public OAuthResponseBuilder location(String location) {
            this.location = location;
            return this;
        }

        public OAuthResponseBuilder setScope(String value) {
            this.parameters.put(OAuth.OAUTH_SCOPE, value);
            return this;
        }

        public OAuthResponseBuilder setParam(String key, String value) {
            this.parameters.put(key, value);
            return this;
        }

        public OAuthResponse buildQueryMessage() throws OAuthSystemException {
            OAuthResponse msg = new OAuthResponse(location, responseCode);
            this.applier = new QueryParameterApplier();
            return (OAuthResponse)applier.applyOAuthParameters(msg, parameters);
        }

        public OAuthResponse buildBodyMessage() throws OAuthSystemException {
            OAuthResponse msg = new OAuthResponse(location, responseCode);
            this.applier = new BodyURLEncodedParametersApplier();
            return (OAuthResponse)applier.applyOAuthParameters(msg, parameters);
        }

        public OAuthResponse buildJSONMessage() throws OAuthSystemException {
            OAuthResponse msg = new OAuthResponse(location, responseCode);
            this.applier = new JSONBodyParametersApplier();
            return (OAuthResponse)applier.applyOAuthParameters(msg, parameters);
        }

        public OAuthResponse buildHeaderMessage() throws OAuthSystemException {
            OAuthResponse msg = new OAuthResponse(location, responseCode);
            this.applier = new WWWAuthHeaderParametersApplier();
            return (OAuthResponse)applier.applyOAuthParameters(msg, parameters);
        }
    }

    public static class OAuthErrorResponseBuilder extends OAuthResponseBuilder {

        public OAuthErrorResponseBuilder(int responseCode) {
            super(responseCode);
        }

        public OAuthErrorResponseBuilder error(OAuthProblemException ex) {
            this.parameters.put(OAuthError.OAUTH_ERROR, ex.getError());
            this.parameters.put(OAuthError.OAUTH_ERROR_DESCRIPTION, ex.getDescription());
            this.parameters.put(OAuthError.OAUTH_ERROR_URI, ex.getUri());
            this.parameters.put(OAuth.OAUTH_STATE, ex.getState());
            return this;
        }

        public OAuthErrorResponseBuilder setError(String error) {
            this.parameters.put(OAuthError.OAUTH_ERROR, error);
            return this;
        }

        public OAuthErrorResponseBuilder setErrorDescription(String desc) {
            this.parameters.put(OAuthError.OAUTH_ERROR_DESCRIPTION, desc);
            return this;
        }

        public OAuthErrorResponseBuilder setErrorUri(String state) {
            this.parameters.put(OAuthError.OAUTH_ERROR_URI, state);
            return this;
        }

        public OAuthErrorResponseBuilder setState(String state) {
            this.parameters.put(OAuth.OAUTH_STATE, state);
            return this;
        }

        public OAuthErrorResponseBuilder setRealm(String realm) {
            this.parameters.put(OAuth.WWWAuthHeader.REALM, realm);
            return this;
        }

        public OAuthErrorResponseBuilder location(String location) {
            this.location = location;
            return this;
        }
    }

}
