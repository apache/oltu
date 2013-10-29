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

import java.util.Map.Entry;

import org.json.JSONStringer;

public abstract class CustomizableEntityWriter<CE extends CustomizableEntity> {

    private final JSONStringer jsonWriter = new JSONStringer();

    public final String write(CE customizableEntity) {
        jsonWriter.object();

        handleProperties(customizableEntity);

        for (Entry<String, Object> customFields : customizableEntity.getCustomFields()) {
            set(customFields.getKey(), customFields.getValue());
        }

        jsonWriter.endObject();
        return jsonWriter.toString();
    }

    protected abstract void handleProperties(CE customizableEntity);

    protected final <T> void set(String key, T value) {
        if (value != null) {
            jsonWriter.key(key).value(value);
        }
    }

    protected final <T> void set(String key, T[] value) {
        if (value == null) {
            return;
        }

        jsonWriter.key(key).array();

        for (T item : value) {
            if (item != null) {
                jsonWriter.value(item);
            }
        }

        jsonWriter.endArray();
    }

}
