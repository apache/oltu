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

import org.apache.oltu.oauth2.client.validator.OAuthClientValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *
 */
public abstract class OAuthClientResponse {

    protected String body;
    protected String contentType;
    protected int responseCode;
    protected Map<String, List<String>> headers;

    protected OAuthClientValidator validator;
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public String getParam(String param) {
        Object value = parameters.get(param);
        return value == null ? null : String.valueOf(value);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    /**
     * Allows setting the response body to a String value.
     *
     * @param body A String representing the response body.
     * @throws OAuthProblemException
     * @throws UnsupportedOperationException for subclasses that only
     *         support InputStream as body
     */
    protected void setBody(String body) throws OAuthProblemException {
        throw new UnsupportedOperationException();
    }

    /**
     * Allows setting the response body to an InputStream value.
     *
     * @param body An InputStream representing the response body.
     * @throws OAuthProblemException
     * @throws UnsupportedOperationException for subclasses that only
     *         support String as body
     */
    protected void setBody(InputStream body) throws OAuthProblemException {
        throw new UnsupportedOperationException();
    }

    protected abstract void setContentType(String contentType);

    protected abstract void setResponseCode(int responseCode);

    protected void init(String body, String contentType, int responseCode, Map<String, List<String>> headers)
            throws OAuthProblemException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
        this.setHeaders(headers);
        this.validate();
    }

    protected void init(String body, String contentType, int responseCode)
            throws OAuthProblemException {
        init(body, contentType, responseCode, new HashMap<String, List<String>>());
    }

    /**
     * Default implementation that converts the body InputStream to a String and delegates
     * to {@link #init(String, String, int)}.
     * <br/>
     * This implementation ensures backwards compatibility, as many subclasses expect String
     * type bodies. At the same time it can be overridden to also deal with binary InputStreams.
     *
     * @param body an InputStream representing the response body
     * @param contentType the content type of the response.
     * @param responseCode the HTTP response code of the response.
     * @param headers The HTTP response headers
     * @throws OAuthProblemException
     */
    protected void init(InputStream body, String contentType, int responseCode, Map<String, List<String>> headers)
            throws OAuthProblemException {
        try {
            init(OAuthUtils.saveStreamAsString(body), contentType, responseCode);
        } catch (final IOException e) {
            throw OAuthProblemException.error(e.getMessage());
        }
    }

    protected void validate() throws OAuthProblemException {
        validator.validate(this);
    }
    
}
