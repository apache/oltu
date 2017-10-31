/*
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

import org.apache.oltu.oauth2.client.validator.ResourceValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class OAuthResourceResponse  extends OAuthClientResponse {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthResourceResponse.class);

    private InputStream inputStream;

    private boolean bodyRetrieved = false;

    public OAuthResourceResponse() {
        this.validator = new ResourceValidator();
    }

    public String getBody() {
        if (bodyRetrieved && body == null) {
            throw new IllegalStateException("Cannot call getBody() after getBodyAsInputStream()");
        }

        if (body == null) {
            try {
                body = OAuthUtils.saveStreamAsString(getBodyAsInputStream());
                inputStream = null;
            } catch (IOException e) {
                LOG.error("Failed to convert InputStream to String", e);
            }
        }
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    protected void setBody(InputStream body) throws OAuthProblemException {
        this.inputStream = body;
    }

    @Override
    protected void setBody(String body) throws OAuthProblemException {
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public InputStream getBodyAsInputStream() {
        if (bodyRetrieved && inputStream == null) {
            throw new IllegalStateException("Cannot call getBodyAsInputStream() after getBody()");
        }
        bodyRetrieved = true;
        return inputStream;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    protected void init(InputStream body, String contentType, int responseCode, Map<String, List<String>> headers) throws OAuthProblemException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
        this.setHeaders(headers);
    }
}
