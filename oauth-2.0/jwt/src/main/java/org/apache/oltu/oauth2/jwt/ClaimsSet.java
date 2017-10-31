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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.oltu.commons.json.CustomizableEntity;

/**
 * Represents the Claims Set as defined in the 6.1 section of the JWT specification.
 *
 * @see http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1
 */
public final class ClaimsSet extends CustomizableEntity {

    /**
     * The {@code iss} JWT Claims Set parameter.
     */
    private final String issuer;

    /**
     * The {@code sub} JWT Claims Set parameter.
     */
    private final String subject;

    /**
     * The {@code aud} JWT Claims Set parameter.
     */
    private final List<String> audiences;

    /**
     * The {@code exp} JWT Claims Set parameter.
     */
    private final long expirationTime;

    /**
     * The {@code nbf} JWT Claims Set parameter.
     */
    private final String notBefore;

    /**
     * The {@code iat} JWT Claims Set parameter.
     */
    private final long issuedAt;

    /**
     * The {@code jti} JWT Claims Set parameter.
     */
    private final String jwdId;

    /**
     * The {@code typ} JWT Claims Set parameter.
     */
    private final String type;

    ClaimsSet(String issuer,
              String subject,
              List<String> audiences,
              long expirationTime,
              String notBefore,
              long issuedAt,
              String jwdId,
              String type,
              Map<String, Object> customFields) {
        super(customFields);
        this.issuer = issuer;
        this.subject = subject;
        this.audiences = audiences == null ? null : new ArrayList<String>(audiences);
        this.expirationTime = expirationTime;
        this.notBefore = notBefore;
        this.issuedAt = issuedAt;
        this.jwdId = jwdId;
        this.type = type;
    }

    /**
     * Returns the {@code iss} JWT Claims Set parameter.
     *
     * @return the {@code iss} JWT Claims Set parameter.
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Returns the {@code sub} JWT Claims Set parameter.
     *
     * @return the {@code sub} JWT Claims Set parameter.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns the first audience of the {@code aud} JWT Claims Set
     * parameter.
     *
     * <p>There may be more than one audience listed.</p>
     *
     * @return the {@code aud} JWT Claims Set parameter.
     * @see #getAudiences
     */
    public String getAudience() {
        return getAudiences().isEmpty() ? null : audiences.get(0);
    }

    /**
     * Returns the {@code aud} JWT Claims Set parameter.
     *
     * @return the {@code aud} JWT Claims Set parameter.
     */
    public List<String> getAudiences() {
        return audiences == null ? new ArrayList<String>() : Collections.unmodifiableList(audiences);
    }

    /**
     * Returns the {@code exp} JWT Claims Set parameter.
     *
     * @return the {@code exp} JWT Claims Set parameter.
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Returns the {@code nbf} JWT Claims Set parameter.
     *
     * @return the {@code nbf} JWT Claims Set parameter.
     */
    public String getNotBefore() {
        return notBefore;
    }

    /**
     * Returns the {@code iat} JWT Claims Set parameter.
     *
     * @return the {@code iat} JWT Claims Set parameter.
     */
    public long getIssuedAt() {
        return issuedAt;
    }

    /**
     * Returns the {@code jti} JWT Claims Set parameter.
     *
     * @return the {@code jti} JWT Claims Set parameter.
     */
    public String getJwdId() {
        return jwdId;
    }

    /**
     * Returns the {@code typ} JWT Claims Set parameter.
     *
     * @return the {@code typ} JWT Claims Set parameter.
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return format("{\"iss\": \"%s\", \"sub\": \"%s\", \"aud\": %s, \"exp\": %s, \"nbf\": \"%s\", \"iat\": %s, \"jti\": \"%s\", \"typ\": \"%s\" }",
                      issuer, subject, formatAudiences(), expirationTime, notBefore, issuedAt, jwdId, type, super.toString());
    }

    private String formatAudiences() {
        if (audiences == null || audiences.size() < 1) {
            // "null" for no audience at all, "single-audience" otherwise
            return "\"" + getAudience() + "\"";
        }
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (String aud : audiences) {
            if (!first) {
                sb.append(", ");
            }
            sb.append("\"").append(aud).append("\"");
            first = false;
        }
        return sb.append("]").toString();
    }

}
