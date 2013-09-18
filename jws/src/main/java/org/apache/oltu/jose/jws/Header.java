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
package org.apache.oltu.jose.jws;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Represents the Header as defined in the section 4 of the JWS specification.
 *
 * @see http://tools.ietf.org/html/draft-ietf-jose-json-web-signature-15#section-4
 */
public final class Header {

    /**
     * The {@code alg} JWS Header parameter.
     */
    private final String algorithm;

    /**
     * The {@code jku} JWS Header parameter.
     */
    private final String jwkSetUrl;

    /**
     * The {@code jwk} JWS Header parameter.
     */
    private final String jsonWebKey;

    /**
     * The {@code x5u} JWS Header parameter.
     */
    private final String x509url;

    /**
     * The {@code x5t} JWS Header parameter.
     */
    private final String x509CertificateThumbprint;

    /**
     * The {@code x5c} JWS Header parameter.
     */
    private final String x509CertificateChain;

    /**
     * The {@code kid} JWS Header parameter.
     */
    private final String keyId;

    /**
     * The {@code typ} JWS Header parameter.
     */
    private final String type;

    /**
     * The {@code cty} JWS Header parameter.
     */
    private final String contentType;

    /**
     * The {@code crit} JWS Header parameter.
     */
    private final String[] critical;

    /**
     * The registry that keeps the custom fields.
     */
    private final Map<String, Object> customFields;

    Header(String algorithm,
           String jwkSetUrl,
           String jsonWebKey,
           String x509url,
           String x509CertificateThumbprint,
           String x509CertificateChain,
           String keyId,
           String type,
           String contentType,
           String[] critical,
           Map<String, Object> customFields) {
        this.algorithm = algorithm;
        this.jwkSetUrl = jwkSetUrl;
        this.jsonWebKey = jsonWebKey;
        this.x509url = x509url;
        this.x509CertificateThumbprint = x509CertificateThumbprint;
        this.x509CertificateChain = x509CertificateChain;
        this.keyId = keyId;
        this.type = type;
        this.contentType = contentType;
        this.critical = critical;
        this.customFields = customFields;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getJwkSetUrl() {
        return jwkSetUrl;
    }

    public String getJsonWebKey() {
        return jsonWebKey;
    }

    public String getX509url() {
        return x509url;
    }

    public String getX509CertificateThumbprint() {
        return x509CertificateThumbprint;
    }

    public String getX509CertificateChain() {
        return x509CertificateChain;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getType() {
        return type;
    }

    public String getContentType() {
        return contentType;
    }

    public String[] getCritical() {
        return critical;
    }

    /**
     * Return the specified custom field value,
     * {@code null} if the custom field is not present.
     *
     * @param name the custom field name, it cannot be null.
     * @return the specified custom field value,
     *         {@code null} if the custom field is not present.
     */
    public <T> T getCustomField(String name, Class<T> type) {
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
    public Set<Entry<String, Object>> getCustomFields() {
        return customFields.entrySet();
    }

}
