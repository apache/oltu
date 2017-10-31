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

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public abstract class CustomizableEntityWriter<CE extends CustomizableEntity> {

    private final StringWriter stringWriter = new StringWriter();

    private final JsonGenerator generator = Json.createGenerator(stringWriter);

    public final String write(CE customizableEntity) {
        generator.writeStartObject();

        handleProperties(customizableEntity);

        for (Entry<String, Object> customFields : customizableEntity.getCustomFields()) {
            set(customFields.getKey(), customFields.getValue());
        }

        generator.writeEnd().close();

        return stringWriter.toString();
    }

    protected abstract void handleProperties(CE customizableEntity);

    protected final <T> void set(String key, T value) {
        if (key != null && value != null) {
            if (value instanceof Boolean) {
                generator.write(key, (Boolean) value);
            } else if (value instanceof Double) {
                generator.write(key, (Double) value);
            } else if (value instanceof Integer) {
                generator.write(key, (Integer) value);
            } else if (value instanceof BigDecimal) {
                generator.write(key, (BigDecimal) value);
            } else if (value instanceof BigInteger) {
                generator.write(key, (BigInteger) value);
            } else if (value instanceof Long) {
                generator.write(key, (Long) value);
            } else if (value instanceof String) {
                String string = (String) value;
                if (!string.isEmpty()) {
                    generator.write(key, string);
                }
            }
        }
    }

    protected final <T> void set(String key, T[] value) {
        if (value == null) {
            return;
        }

        generator.writeStartArray(key);

        for (T item : value) {
            if (item != null) {
                if (item instanceof Boolean) {
                    generator.write((Boolean) item);
                } else if (item instanceof Double) {
                    generator.write((Double) item);
                } else if (item instanceof Integer) {
                    generator.write((Integer) item);
                } else if (item instanceof BigDecimal) {
                    generator.write((BigDecimal) item);
                } else if (item instanceof BigInteger) {
                    generator.write((BigInteger) item);
                } else if (item instanceof Long) {
                    generator.write((Long) item);
                } else if (item instanceof String) {
                    generator.write((String) item);
                }
            }
        }

        generator.writeEnd();
    }

}
