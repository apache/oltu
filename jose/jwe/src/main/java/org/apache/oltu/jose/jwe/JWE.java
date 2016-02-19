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
package org.apache.oltu.jose.jwe;

import org.apache.oltu.commons.json.CustomizableBuilder;

public class JWE {
    
    /**
     * The JWE Header.
     */
    private final Header header;
    
    /**
     * The JWE Payload.
     */
    private final String payload;
    
    JWE(Header header, String payload) {
        this.header = header;
        this.payload = payload;
    }
    
    public Header getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }
    
    public static final class Builder extends CustomizableBuilder<JWE> {
        
        /**
         * The {@code alg} JWE Header parameter.
         */
        private String algorithm;
        
        /**
         * The {@code enc} JWE Header parameter.
         */
        private String encryptionAlgorithm;
        
        /**
         * The {@code zip} JWE Header key.
         */
        private String compressionAlgorithm;
        
        /**
         * The {@code jku} JWE Header parameter.
         */
        private String jwkSetUrl;

        /**
         * The {@code jwk} JWE Header parameter.
         */
        private String jsonWebKey;
        
        /**
         * The {@code x5u} JWE Header parameter.
         */
        private String x509url;

        /**
         * The {@code x5t} JWE Header parameter.
         */
        private String x509CertificateThumbprint;

        /**
         * The {@code x5c} JWE Header parameter.
         */
        private String x509CertificateChain;
        
        /**
         * The {@code kid} JWE Header parameter.
         */
        private String keyId;

        /**
         * The {@code typ} JWE Header parameter.
         */
        private String type;

        /**
         * The {@code cty} JWE Header parameter.
         */
        private String contentType;

        /**
         * The {@code crit} JWE Header parameter.
         */
        private String[] critical;
        
        /**
         * The JWE Payload.
         */
        private String payload;
        
        public Builder setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
            return this;
        }
        
        public Builder setEncryptionAlgorithm(String encryptionAlgorithm) {
            this.encryptionAlgorithm = encryptionAlgorithm;
            return this;
        }
        
        public Builder setCompressionAlgorithm(String compressionAlgorithm) {
            this.compressionAlgorithm = compressionAlgorithm;
            return this;
        }

        public Builder setJwkSetUrl(String jwkSetUrl) {
            this.jwkSetUrl = jwkSetUrl;
            return this;
        }

        public Builder setJsonWebKey(String jsonWebKey) {
            this.jsonWebKey = jsonWebKey;
            return this;
        }

        public Builder setX509url(String x509url) {
            this.x509url = x509url;
            return this;
        }

        public Builder setX509CertificateThumbprint(String x509CertificateThumbprint) {
            this.x509CertificateThumbprint = x509CertificateThumbprint;
            return this;
        }

        public Builder setX509CertificateChain(String x509CertificateChain) {
            this.x509CertificateChain = x509CertificateChain;
            return this;
        }

        public Builder setKeyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setCritical(String[] critical) {
            this.critical = critical;
            return this;
        }

        public Builder setPayload(String payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public JWE build() {
            return new JWE(new Header(algorithm,
                    encryptionAlgorithm,
                    compressionAlgorithm,
                    jwkSetUrl,
                    jsonWebKey,
                    x509url,
                    x509CertificateThumbprint,
                    x509CertificateChain,
                    keyId, type,
                    contentType,
                    critical,
                    getCustomFields()),
         payload);
        }
        
    }
}
