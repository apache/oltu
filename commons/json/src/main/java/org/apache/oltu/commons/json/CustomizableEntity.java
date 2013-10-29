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

import java.util.Formatter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * TODO
 */
public abstract class CustomizableEntity {

    /**
     * The registry that keeps the custom fields.
     */
    private final Map<String, Object> customFields;

    /**
     * TODO
     *
     * @param customFields
     */
    public CustomizableEntity(Map<String, Object> customFields) {
        if (customFields == null) {
            throw new IllegalArgumentException("Null custom field registry not allowed.");
        }
        this.customFields = customFields;
    }

    /**
     * Return the specified custom field value,
     * {@code null} if the custom field is not present.
     *
     * @param name the custom field name, it cannot be null.
     * @return the specified custom field value,
     *         {@code null} if the custom field is not present.
     */
    public final <T> T getCustomField(String name, Class<T> type) {
        if (name == null) {
            throw new IllegalArgumentException("Null custom field name not present in the registry.");
        }

        Object value = customFields.get(name);

        if (value != null) {
            return type.cast(value);
        }

        return null;
    }

    /**
     * Returns the custom fields stored in the entity.
     *
     * @return the custom fields stored in the entity.
     */
    public final Set<Entry<String, Object>> getCustomFields() {
        return customFields.entrySet();
    }

    @Override
    public String toString() {
        if (customFields.isEmpty()) {
            return "";
        }

        Formatter formatter = new Formatter();

        formatter.format( ", " );

        int counter = 0;
        for (Entry<String, Object> customField : customFields.entrySet()) {
            Object value = customField.getValue();

            if (value != null) {
                if (counter++ > 0) {
                    formatter.format(", ");
                }

                String pattern;
                if (customField.getValue() instanceof Number) {
                    pattern = "\"%s\": %s";
                } else {
                    pattern = "\"%s\": \"%s\"";
                }

                formatter.format(pattern, customField.getKey(), value);
            }
        }

        try {
            return formatter.toString();
        } finally {
            formatter.close();
        }
    }

}
