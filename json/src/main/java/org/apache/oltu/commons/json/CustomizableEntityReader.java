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
package org.apache.oltu.commons.json;

import static java.lang.String.format;

import org.json.JSONArray;
import org.json.JSONTokener;

/**
 * TODO
 */
public abstract class CustomizableEntityReader<E, B extends CustomizableBuilder<E>> {

    private final B builder;

    public CustomizableEntityReader(B builder) {
        this.builder = builder;
    }

    protected final B getBuilder() {
        return builder;
    }

    /**
     * Method extracted from {@code org.json.JSONObject#JSONObject(JSONTokener)}
     *
     * @param jsonString
     */
    public void read(String jsonString) {
        final JSONTokener x = new JSONTokener(jsonString);
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, a JSON object text must begin with '{'",
                                                      jsonString));
        }
        for (;;) {
            c = x.nextClean();
            switch (c) {
            case 0:
                throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, a JSON object text must end with '}'",
                                                          jsonString));
            case '}':
                return;
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
                                                          jsonString, key));
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

                // if the concrete implementation is not able to handle the property, set the custom field
                if (!handleProperty(key, value)) {
                    builder.setCustomField(key, value);
                }
            }

            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */
            switch (x.nextClean()) {
            case ';':
            case ',':
                if (x.nextClean() == '}') {
                    return;
                }
                x.back();
                break;
            case '}':
                return;
            default:
                throw new IllegalArgumentException("Expected a ',' or '}'");
            }
        }
    }

    protected abstract <T> boolean handleProperty(String key, T value);

}
