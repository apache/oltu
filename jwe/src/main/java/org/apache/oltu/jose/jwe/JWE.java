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

import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.apache.oltu.commons.json.CustomizableBuilder;
import org.apache.oltu.jose.jwe.encryption.ContentEncryptMethod;
import org.apache.oltu.jose.jwe.encryption.DecryptingKey;
import org.apache.oltu.jose.jwe.encryption.EncryptingKey;
import org.apache.oltu.jose.jwe.encryption.KeyEncryptMethod;
import org.apache.oltu.jose.jwe.io.JWEHeaderWriter;

public class JWE {

    /**
     * The JWE Header.
     */
    private final Header header;

    /**
     * The JWE encryptedKey.
     */
    private final String encryptedKey;

    
    //TODO remove??
    /**
     * The JWE Payload.
     */
    private final String payload;

    /**
     * The JWE Content Encryption.
     */
    private final String contentEncryption;

    JWE(Header header, String encryptedKey, String payload ,String contentEncryption) {
        this.header = header;
        this.encryptedKey = encryptedKey;
        this.payload = payload;
        this.contentEncryption = contentEncryption;
    }

    public Header getHeader() {
        return header;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public String getPayload() {
        return payload;
    }

    public String getContentEncryption() {
        return contentEncryption;
    }

    public <EK extends EncryptingKey, DK extends DecryptingKey> boolean acceptAlgorithm(KeyEncryptMethod<EK, DK> keyEncryptMethod, ContentEncryptMethod<EK, DK> contentEncryptMethod) {
        if (keyEncryptMethod == null) {
            throw new IllegalArgumentException("An encrypt method is required in order to decrypt the content encryption key.");
        }
        if (contentEncryptMethod == null) {
            throw new IllegalArgumentException("An encrypt method is required in order to decrypt the payload.");
        }
        if (header == null || header.getAlgorithm() == null || header.getEncryptionAlgorithm() == null) {
            throw new IllegalStateException("JWE token must have a valid JSON header with specified algorithm.");
        }

        return header.getAlgorithm().equalsIgnoreCase(keyEncryptMethod.getAlgorithm()) && header.getEncryptionAlgorithm().equalsIgnoreCase(contentEncryptMethod.getAlgorithm());
    }

    public <EK extends EncryptingKey, DK extends DecryptingKey> String decrypt(KeyEncryptMethod<EK, DK> keyEncryptMethod,
            DK decryptingKey, ContentEncryptMethod<EK, DK> contentEncryptMethod) {        
        if (!acceptAlgorithm(keyEncryptMethod, contentEncryptMethod)) {
            throw new IllegalArgumentException("Impossible to decrypt current JWE");
        }
        if (decryptingKey == null) {
            throw new IllegalArgumentException("A decrypting key is required in order to decrypt the JWE");
        }

        if (encryptedKey == null) {
            throw new IllegalStateException("JWE token must have an encrypted key.");
        }

        if (contentEncryption == null) {
            throw new IllegalStateException("JWE token must have a content encryption");
        }

        return contentEncryptMethod.decrypt(new JWEHeaderWriter().write(header), contentEncryption, keyEncryptMethod.decrypt(encryptedKey, decryptingKey));
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
         * The JWE encryptedKey.
         */
        private String encryptedKey;

        /**
         * The JWE Payload.
         */
        private String payload;

        /**
         * The JWE Content Encryption.
         */
        private String contentEncryption;

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

        public Builder setEncryptedKey(String encryptedKey) {
            this.encryptedKey = encryptedKey;
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

        public Builder setContentEncryption(String contentEncryption) {
            this.contentEncryption = contentEncryption;
            return this;
        }

        public <EK extends EncryptingKey, DK extends DecryptingKey> Builder encrypt(KeyEncryptMethod<EK, DK> keyEncryptMethod,
                EK encryptingKey, ContentEncryptMethod<EK, DK> contentEncryptMethod) {
            if (keyEncryptMethod == null) {
                throw new IllegalArgumentException("A key encryption method is required in order to encrypt the content encryption key.");
            }
            if (encryptingKey == null) {
                throw new IllegalArgumentException("An encryption key is required in order to encrypt the content encryption key.");
            }
            if (payload == null) {
                throw new IllegalStateException("Payload needs to be set in order to encrypt it.");
            }
            if (contentEncryptMethod == null) {
                throw new IllegalArgumentException("A key encryption method is required in order to encrypt the payload.");
            }

            setAlgorithm(keyEncryptMethod.getAlgorithm());
            setEncryptionAlgorithm(contentEncryptMethod.getAlgorithm());

            String header = new JWEHeaderWriter().write(new Header(algorithm,
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
                    getCustomFields()));

            ContentEncryptionKey cek = keyEncryptMethod.encrypt(encryptingKey);

            setEncryptedKey(cek.getEncryptedKey());

            return setContentEncryption(contentEncryptMethod.encrypt(TokenDecoder.base64Encode(header), payload, cek.getContentEncryptionKey()));
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
                    encryptedKey,
                    payload,
                    contentEncryption);
        }

    }
}
