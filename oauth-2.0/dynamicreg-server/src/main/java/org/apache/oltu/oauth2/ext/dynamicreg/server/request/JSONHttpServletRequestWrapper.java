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
package org.apache.oltu.oauth2.ext.dynamicreg.server.request;

import static java.lang.String.format;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JSONHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Logger log = LoggerFactory.getLogger(JSONHttpServletRequestWrapper.class);

    private boolean bodyRead = false;

    final Map<String, String[]> parameters = new HashMap<String, String[]>();

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
        if (!bodyRead) {
            String body = readJsonBody();

            final JSONTokener x = new JSONTokener(body);
            char c;
            String key;

            if (x.nextClean() != '{') {
                throw new OAuthRuntimeException(format("String '%s' is not a valid JSON object representation, a JSON object text must begin with '{'",
                                                       body));
            }
            for (;;) {
                c = x.nextClean();
                switch (c) {
                case 0:
                    throw new OAuthRuntimeException(format("String '%s' is not a valid JSON object representation, a JSON object text must end with '}'",
                                                           body));
                case '}':
                    return Collections.unmodifiableMap(parameters);
                default:
                    x.back();
                    key = x.nextValue().toString();
                }

                /*
                 * The key is followed by ':'. We will also tolerate '=' or '=>'.
                 */
                c = x.nextClean();
                if (c == '=') {
                    if (x.next() != '>') {
                        x.back();
                    }
                } else if (c != ':') {
                    throw new OAuthRuntimeException(format("String '%s' is not a valid JSON object representation, expected a ':' after the key '%s'",
                                                           body, key));
                }
                Object value = x.nextValue();

                // guard from null values
                if (value != null) {
                    if (value instanceof JSONArray) { // only plain simple arrays in this version
                        JSONArray array = (JSONArray) value;
                        String[] values = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            values[i] = String.valueOf(array.get(i));
                        }
                        parameters.put(key, values);
                    } else {
                        parameters.put(key, new String[]{ String.valueOf(value) });
                    }
                }

                /*
                 * Pairs are separated by ','. We will also tolerate ';'.
                 */
                switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == '}') {
                        return Collections.unmodifiableMap(parameters);
                    }
                    x.back();
                    break;
                case '}':
                    return Collections.unmodifiableMap(parameters);
                default:
                    throw new OAuthRuntimeException(format("String '%s' is not a valid JSON object representation, Expected a ',' or '}",
                                                           body));
                }
            }
        }

        return Collections.unmodifiableMap(parameters);
    }

    public Enumeration<String> getParameterNames() {
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
    private String readJsonBody() {
        try {
            final ServletRequest request = getRequest();
            String contentType = request.getContentType();
            final String expectedContentType = OAuth.ContentType.JSON;
            if (!OAuthUtils.hasContentType(contentType, expectedContentType)) {
                return "";
            }

            final ServletInputStream inputStream = request.getInputStream();
            if (inputStream == null) {
                return "";
            }

            bodyRead = true;
            return OAuthUtils.saveStreamAsString(inputStream);
        } catch (Exception e) {
            log.error("Dynamic client registration error: ", e);
            throw new OAuthRuntimeException("OAuth server error");
        }
    }
}
