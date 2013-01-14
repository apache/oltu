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

import java.util.HashMap;
import java.util.Map;

import org.apache.amber.oauth2.client.validator.OAuthClientValidator;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;

/**
 *
 *
 *
 */
public abstract class OAuthClientResponse {

    protected String body;
    protected String contentType;
    protected int responseCode;

    protected OAuthClientValidator validator;
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public String getParam(String param) {
        Object value = parameters.get(param);
        return value == null ? null : String.valueOf(value);
    }

    protected abstract void setBody(String body) throws OAuthProblemException;

    protected abstract void setContentType(String contentType);

    protected abstract void setResponseCode(int responseCode);

    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
        this.validate();

    }

    protected void validate() throws OAuthProblemException {
        validator.validate(this);
    }
    
}
