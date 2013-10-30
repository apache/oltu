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

package org.apache.oltu.oauth2.common.utils;

import static java.lang.String.format;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONStringer;
import org.json.JSONTokener;

/**
 *
 *
 *
 */
public final class JSONUtils {

    public static String buildJSON(Map<String, Object> params) {
        final JSONStringer stringer = new JSONStringer();
        stringer.object();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (param.getKey() != null && !"".equals(param.getKey()) && param.getValue() != null && !""
                .equals(param.getValue())) {
                stringer.key(param.getKey()).value(param.getValue());
            }
        }

        return stringer.endObject().toString();
    }

    public static Map<String, Object> parseJSON(String jsonBody) {
        final Map<String, Object> params = new HashMap<String, Object>();

        final JSONTokener x = new JSONTokener(jsonBody);
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, a JSON object text must begin with '{'",
                                                      jsonBody));
        }
        for (;;) {
            c = x.nextClean();
            switch (c) {
            case 0:
                throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, a JSON object text must end with '}'",
                                                          jsonBody));
            case '}':
                return params;
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
                throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, expected a ':' after the key '%s'",
                                                          jsonBody, key));
            }
            Object value = x.nextValue();

            // guard from null values
            if (value != null) {
                if (value instanceof JSONArray) { // only plain simple arrays in this version
                    JSONArray array = (JSONArray) value;
                    Object[] values = new Object[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        values[i] = array.get(i);
                    }
                    value = values;
                }

                params.put(key, value);
            }

            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */
            switch (x.nextClean()) {
            case ';':
            case ',':
                if (x.nextClean() == '}') {
                    return params;
                }
                x.back();
                break;
            case '}':
                return params;
            default:
                throw new IllegalArgumentException("Expected a ',' or '}'");
            }
        }
    }

}
