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

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.oltu.commons.json.CustomizableBuilder;

/**
 * This class contains constants used in the JWT implementation.
 *
 */
public class JWT {

    /**
     * The JWT Header as defined in the 6.1 section of the JWT specification.
     */
    private final Header header;

    /**
     * The JWT Claims Set as defined in the 6.1 section of the JWT specification.
     */
    private final ClaimsSet claimsSet;

    /**
     * The JWT Signature.
     */
    private final String signature;

    /**
     * The JWT raw string;
     */
    private final String rawString;

    JWT(String rawString, Header header, ClaimsSet claimsSet, String signature) {
        this.rawString = rawString;
        this.header = header;
        this.claimsSet = claimsSet;
        this.signature = signature;
    }

    /**
     * Get the JWT Header as defined in the 6.1 section of the JWT specification.
     *
     * @return the JWT Header.
     * @see http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Get the JWT Claims Set as defined in the 6.1 section of the JWT specification.
     *
     * @return the JWT Claims Set
     * @see http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1
     */
    public ClaimsSet getClaimsSet() {
        return claimsSet;
    }

    /**
     * Get the JWT Signature.
     *
     * @return the JWT Signature.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Get the JWT raw string.
     *
     * @return the JWT raw string
     */
    public String getRawString() {
        return rawString;
    }

    @Override
    public String toString() {
        return format("-- JWT --%nRaw String: %s%nHeader: %s%nClaims Set: %s%nSignature: %s%n---------",
                      rawString != null ? rawString : "unknown", header, claimsSet, signature);
    }

    /**
     * A simple {@link JWT} builder.
     */
    public static final class Builder extends CustomizableBuilder<JWT> {

        /**
         * The JWT raw string
         */
        private final String rawString;

        /**
         * The {@code typ} JWT Header parameter.
         */
        private String headerType;

        /**
         * The {@code alg} JWT Header parameter.
         */
        private String headerAlgorithm;

        /**
         * The {@code cty} JWT Header parameter.
         */
        private String headerContentType;

        /**
         * The JWT Header custom fields.
         */
        private final Map<String, Object> headerCustomFields = new LinkedHashMap<String, Object>();

        /**
         * The {@code iss} JWT Claims Set parameter.
         */
        private String claimsSetIssuer;

        /**
         * The {@code sub} JWT Claims Set parameter.
         */
        private String claimsSetSubject;

        /**
         * The {@code aud} JWT Claims Set parameter.
         */
        private List<String> claimsSetAudiences;

        /**
         * The {@code exp} JWT Claims Set parameter.
         */
        private long claimsSetExpirationTime;

        /**
         * The {@code nbf} JWT Claims Set parameter.
         */
        private String claimsSetNotBefore;

        /**
         * The {@code iat} JWT Claims Set parameter.
         */
        private long claimsSetIssuedAt;

        /**
         * The {@code jti} JWT Claims Set parameter.
         */
        private String claimsSetJwdId;

        /**
         * The {@code typ} JWT Claims Set parameter.
         */
        private String claimsSetType;

        /**
         * The JWT Header custom fields.
         */
        private final Map<String, Object> claimsSetCustomFields = new LinkedHashMap<String, Object>();

        /**
         * The JWT Signature.
         */
        private String signature;

        public Builder() {
            this(null);
        }

        public Builder(String rawString) {
            this.rawString = rawString;
        }

        /**
         * Sets the JWT Header {@code typ}.
         *
         * @param headerType the JWT Header {@code typ}.
         * @return this builder instance.
         */
        public Builder setHeaderType(String headerType) {
            this.headerType = headerType;
            return this;
        }

        /**
         * Sets the JWT Header {@code alg}.
         *
         * @param headerAlgorithm the JWT Header {@code alg}.
         * @return this builder instance.
         */
        public Builder setHeaderAlgorithm(String headerAlgorithm) {
            this.headerAlgorithm = headerAlgorithm;
            return this;
        }

        /**
         * Sets the JWT Header {@code cty}.
         *
         * @param headerContentType the JWT Header {@code cty}.
         * @return this builder instance.
         */
        public Builder setHeaderContentType(String headerContentType) {
            this.headerContentType = headerContentType;
            return this;
        }

        /**
         * Set the JWT Header custom field.
         *
         * @param key the custom field name.
         * @param value value the custom field value.
         * @return this builder instance.
         */
        public Builder setHeaderCustomField(String key, Object value) {
            if (key == null) {
                throw new IllegalArgumentException("Null key not allowed");
            }
            if (value != null) {
                headerCustomFields.put(key, value);
            }
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code iss}.
         *
         * @param claimsSetIssuer the JWT Claims Set {@code iss}.
         * @return this builder instance.
         */
        public Builder setClaimsSetIssuer(String claimsSetIssuer) {
            this.claimsSetIssuer = claimsSetIssuer;
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code sub}.
         *
         * @param claimsSetSubject the JWT Claims Set {@code sub}.
         * @return this builder instance.
         */
        public Builder setClaimsSetSubject(String claimsSetSubject) {
            this.claimsSetSubject = claimsSetSubject;
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code aud} for a single audience.
         *
         * @param claimsSetAudience the JWT Claims Set {@code aud}.
         * @return this builder instance.
         */
        public Builder setClaimsSetAudience(String claimsSetAudience) {
            this.claimsSetAudiences = claimsSetAudience == null ? null
                : Arrays.asList(claimsSetAudience);
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code aud}.
         *
         * @param claimsSetAudiences the JWT Claims Set {@code aud}.
         * @return this builder instance.
         */
        public Builder setClaimsSetAudiences(List<String> claimsSetAudiences) {
            this.claimsSetAudiences = claimsSetAudiences == null ? null
                : new ArrayList<String>(claimsSetAudiences);
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code exp}.
         *
         * @param claimsSetExpirationTime the JWT Claims Set {@code exp}.
         * @return this builder instance.
         */
        public Builder setClaimsSetExpirationTime(long claimsSetExpirationTime) {
            this.claimsSetExpirationTime = claimsSetExpirationTime;
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code nbf}.
         *
         * @param claimsSetNotBefore the JWT Claims Set {@code nbf}.
         * @return this builder instance.
         */
        public Builder setClaimsSetNotBefore(String claimsSetNotBefore) {
            this.claimsSetNotBefore = claimsSetNotBefore;
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code issuedAt}.
         *
         * @param claimsSetIssuedAt the JWT Claims Set {@code issuedAt}.
         * @return this builder instance.
         */
        public Builder setClaimsSetIssuedAt(long claimsSetIssuedAt) {
            this.claimsSetIssuedAt = claimsSetIssuedAt;
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code jti}.
         *
         * @param claimsSetJwdId the JWT Claims Set {@code jti}.
         * @return this builder instance.
         */
        public Builder setClaimsSetJwdId(String claimsSetJwdId) {
            this.claimsSetJwdId = claimsSetJwdId;
            return this;
        }

        /**
         * Sets the JWT Claims Set {@code typ}.
         *
         * @param claimsSetType the JWT Claims Set {@code typ}.
         * @return this builder instance.
         */
        public Builder setClaimsSetType(String claimsSetType) {
            this.claimsSetType = claimsSetType;
            return this;
        }

        /**
         * Set the JWT Claims Set custom field.
         *
         * @param key the custom field name.
         * @param value value the custom field value.
         * @return this builder instance.
         */
        public Builder setClaimsSetCustomField(String key, Object value) {
            if (key == null) {
                throw new IllegalArgumentException("Null key not allowed");
            }
            if (value != null) {
                claimsSetCustomFields.put(key, value);
            }
            return this;
        }

        /**
         * Sets the JWT signature.
         *
         * @param signature
         * @return this builder instance.
         */
        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        /**
         * Creates a new {@link JWT} instance.
         *
         * @return a new {@link JWT} instance.
         */
        public JWT build() {
            return new JWT(rawString,
                           new Header(headerType, headerAlgorithm, headerContentType, headerCustomFields),
                           new ClaimsSet(claimsSetIssuer,
                                         claimsSetSubject,
                                         claimsSetAudiences,
                                         claimsSetExpirationTime,
                                         claimsSetNotBefore,
                                         claimsSetIssuedAt,
                                         claimsSetJwdId,
                                         claimsSetType,
                                         claimsSetCustomFields),
                           signature);
        }

    }

}
