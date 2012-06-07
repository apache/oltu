/**
 *       Copyright 2011 Newcastle University
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
package org.apache.amber.oauth2.ext.dynamicreg.server.request;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthRuntimeException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JSONHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private Logger log = LoggerFactory.getLogger(JSONHttpServletRequestWrapper.class);
    private JSONObject body;
    private boolean bodyRead = false;

    public JSONHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getParameter(String name) {
        final String[] values = getParameterMap().get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    public Map<String, String[]> getParameterMap() {
        try {
            readJsonBody();
            Map<String, String[]> parameters = new HashMap<String, String[]>();

            if (body != null) {
                final JSONArray attributeNames = body.names();
                for (int i = 0; i < attributeNames.length(); i++) {
                    final String attributeName = attributeNames.getString(i);
                    final String attributeValue = body.getString(attributeName);

                    parameters.put(attributeName, new String[] {attributeValue});
                }
            }

            return Collections.unmodifiableMap(parameters);
        } catch (JSONException e) {
            log.error("Dynamic client registration error: ", e);
            throw new OAuthRuntimeException("OAuth server error");
        }
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    public String[] getParameterValues(String name) {
        return getParameterMap().get(name);
    }

    /**
     * Lazily read JSON from request
     *
     * @throws OAuthProblemException
     */
    private void readJsonBody() {
        if (!bodyRead) {
            bodyRead = true;
            try {
                final ServletRequest request = getRequest();
                String contentType = request.getContentType();
                final String expectedContentType = OAuth.ContentType.JSON;
                if (!OAuthUtils.hasContentType(contentType, expectedContentType)) {
                    return;
                }

                final ServletInputStream inputStream = request.getInputStream();
                if (inputStream == null) {
                    return;
                }
                final String jsonString = OAuthUtils.saveStreamAsString(inputStream);
                body = new JSONObject(jsonString);
            } catch (JSONException e) {
                log.error("Cannot decode request body as a JSON: ", e);
            } catch (Exception e) {
                log.error("Dynamic client registration error: ", e);
                throw new OAuthRuntimeException("OAuth server error");
            }
        }
    }
}
