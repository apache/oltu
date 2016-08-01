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

import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.apache.oltu.commons.json.CustomizableBuilder;
import org.apache.oltu.jose.jws.io.JWSHeaderWriter;
import org.apache.oltu.jose.jws.signature.SignatureMethod;
import org.apache.oltu.jose.jws.signature.SigningKey;
import org.apache.oltu.jose.jws.signature.VerifyingKey;

public class JWS {
    
    /**
     * The raw JWS String
     */
    private String rawString;

    /**
     * The JWS Header.
     */
    private final Header header;

    /**
     * The JWS Payload.
     */
    private final String payload;

    /**
     * The JWS Signature.
     */
    private final String signature;

    JWS(Header header, String payload, String signature) {
        this(null, header, payload, signature);
    }
    
    JWS(String rawString, Header header, String payload, String signature) {
        this.rawString = rawString;
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    public Header getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }

    public String getSignature() {
        return signature;
    }

    public <SK extends SigningKey, VK extends VerifyingKey> boolean acceptAlgorithm(SignatureMethod<SK, VK> method) {
        if (method == null) {
            throw new IllegalArgumentException("A signature method is required in order to verify the signature.");
        }
        if (header == null || header.getAlgorithm() == null) {
            throw new IllegalStateException("JWS token must have a valid JSON header with specified algorithm.");
        }

        return header.getAlgorithm().equalsIgnoreCase(method.getAlgorithm());
    }

    public <SK extends SigningKey, VK extends VerifyingKey> boolean validate(SignatureMethod<SK, VK> method,
                                                                             VK verifyingKey) {
        if (!acceptAlgorithm(method)) {
            throw new IllegalArgumentException("Impossible to verify current JWS signature with algorithm '"
                                               + method.getAlgorithm()
                                               + "', JWS header specifies message has been signed with '"
                                               + header.getAlgorithm()
                                               + "' algorithm.");
        }

        if (verifyingKey == null) {
            throw new IllegalArgumentException("A verifying key is required in order to verify the signature.");
        }

        if (payload == null) {
            throw new IllegalStateException("JWS token must have a payload.");
        }
        if (signature == null) {
            throw new IllegalStateException("JWS token must have a signature to be verified.");
        }
        
        if (rawString == null) {
            return method.verify(signature, TokenDecoder.base64Encode(new JWSHeaderWriter().write(header)), TokenDecoder.base64Encode(payload), verifyingKey);
        } else {
            String jwt[] = rawString.split("\\.");
            return method.verify(jwt[2], jwt[0], jwt[1], verifyingKey);
        }
    }

    public static final class Builder extends CustomizableBuilder<JWS> {
        
        public Builder(){}
        
        public Builder(String rawString) {
            this.rawString = rawString;
        }
        
        /**
         * The raw JWS String
         */
        private String rawString;

        /**
         * The {@code alg} JWS Header parameter.
         */
        private String algorithm;

        /**
         * The {@code jku} JWS Header parameter.
         */
        private String jwkSetUrl;

        /**
         * The {@code jwk} JWS Header parameter.
         */
        private String jsonWebKey;

        /**
         * The {@code x5u} JWS Header parameter.
         */
        private String x509url;

        /**
         * The {@code x5t} JWS Header parameter.
         */
        private String x509CertificateThumbprint;

        /**
         * The {@code x5c} JWS Header parameter.
         */
        private String x509CertificateChain;

        /**
         * The {@code kid} JWS Header parameter.
         */
        private String keyId;

        /**
         * The {@code typ} JWS Header parameter.
         */
        private String type;

        /**
         * The {@code cty} JWS Header parameter.
         */
        private String contentType;

        /**
         * The {@code crit} JWS Header parameter.
         */
        private String[] critical;

        /**
         * The JWS Payload.
         */
        private String payload;

        /**
         * The JWS Signature.
         */
        private String signature;

        public Builder setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
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

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public <SK extends SigningKey, VK extends VerifyingKey> Builder sign(SignatureMethod<SK, VK> method,
                                                                             SK signingKey) {
            if (method == null) {
                throw new IllegalArgumentException("A signature method is required in order to calculate the signature.");
            }
            if (signingKey == null) {
                throw new IllegalArgumentException("A signing key is required in order to calculate the signature.");
            }
            if (payload == null) {
                throw new IllegalStateException("Payload needs to be set in order to sign the current JWT");
            }
            setAlgorithm(method.getAlgorithm());
            
            String header = new JWSHeaderWriter().write(new Header(algorithm,
                                      jwkSetUrl,
                                      jsonWebKey,
                                      x509url,
                                      x509CertificateThumbprint,
                                      x509CertificateChain,
                                      keyId, type,
                                      contentType,
                                      critical,
                                      getCustomFields())); 
            
            return setSignature(method.calculate(TokenDecoder.base64Encode(header), TokenDecoder.base64Encode(payload), signingKey));
        }

        public JWS build() {
            return new JWS(rawString, new Header(algorithm,
                                      jwkSetUrl,
                                      jsonWebKey,
                                      x509url,
                                      x509CertificateThumbprint,
                                      x509CertificateChain,
                                      keyId, type,
                                      contentType,
                                      critical,
                                      getCustomFields()),
                           payload,
                           signature);
        }

    }

}
