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
package org.apache.oltu.oauth2.jwt;

import static java.lang.String.format;

import java.util.Map;

import org.apache.oltu.commons.json.CustomizableEntity;

/**
 * Represents the Header as defined in the 6.1 section of the JWT specification.
 *
 * @see http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1
 */
public final class Header extends CustomizableEntity {

    /**
     * The {@code typ} JWT Header parameter.
     */
    private final String type;

    /**
     * The {@code alg} JWT Header parameter.
     */
    private final String algorithm;

    /**
     * The {@code cty} JWT Header parameter.
     */
    private final String contentType;

    Header(String type,
           String algorithm,
           String contentType,
           Map<String, Object> customFields) {
        super(customFields);
        this.type = type;
        this.algorithm = algorithm;
        this.contentType = contentType;
    }

    /**
     * Returns the {@code typ} JWT Header parameter.
     *
     * @return the {@code typ} JWT Header parameter.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the {@code alg} JWT Header parameter.
     *
     * @return the {@code alg} JWT Header parameter.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Returns the {@code cty} JWT Header parameter.
     *
     * @return the {@code cty} JWT Header parameter.
     */
    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return format("{\"typ\": \"%s\", \"alg\": \"%s\", \"cty\": \"%s\" %s}", type, algorithm, contentType, super.toString());
    }

}
