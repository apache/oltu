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

import java.io.StringReader;
import java.util.Map.Entry;

import javax.json.*;

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
     *
     * @param jsonString
     */
    public void read(String jsonString) {
        if (jsonString == null) {
            throw new IllegalArgumentException("Null string does not represent a valid JSON object");
        }

        StringReader reader = new StringReader(jsonString);
        JsonReader jsonReader = Json.createReader(reader);
        JsonStructure structure = jsonReader.read();

        if (structure == null || structure instanceof JsonArray) {
            throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation",
                                                      jsonString));
        }

        JsonObject object = (JsonObject) structure;
        for (Entry<String, JsonValue> entry : object.entrySet()) {
            String key = entry.getKey();
            JsonValue jsonValue = entry.getValue();

            // guard from null values
            if (jsonValue != null) {
                Object value = toJavaObject(jsonValue);

                // if the concrete implementation is not able to handle the property, set the custom field
                if (!handleProperty(key, value)) {
                    builder.setCustomField(key, value);
                }
            }
        }

        jsonReader.close();
    }

    private static Object toJavaObject(JsonValue jsonValue) {
        Object value = null;

        switch (jsonValue.getValueType()) {
            case ARRAY:
                JsonArray array = (JsonArray) jsonValue;
                Object[] values = new Object[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    JsonValue current = array.get(i);
                    values[i] = toJavaObject(current);
                }
                value = values;
                break;

            case FALSE:
                value = false;
                break;

            case NULL:
                value = null;
                break;

            case NUMBER:
                JsonNumber jsonNumber = (JsonNumber) jsonValue;
                value = jsonNumber.numberValue();
                break;

            case OBJECT:
                // not supported in this version
                break;

            case STRING:
                JsonString jsonString = (JsonString) jsonValue;
                value = jsonString.getString();
                break;

            case TRUE:
                value = true;
                break;

            default:
                break;
        }

        return value;
    }

    protected abstract <T> boolean handleProperty(String key, T value);

}
