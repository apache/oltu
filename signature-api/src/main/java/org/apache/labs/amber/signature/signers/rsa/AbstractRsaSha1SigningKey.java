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
package org.apache.labs.amber.signature.signers.rsa;

import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.labs.amber.signature.signers.SignatureException;
import org.apache.labs.amber.signature.signers.SignatureMethod;
import org.apache.labs.amber.signature.signers.SigningKey;

/**
 * Abstract implementation of a RSA private key.
 *
 * @version $Id$
 */
@SignatureMethod("RSA-SHA1")
abstract class AbstractRsaSha1SigningKey extends AbstractRsaSha1Key implements SigningKey {

    /**
     * The RSA algorithm name.
     */
    private static final String RSA = "RSA";

    /**
     * The RSA private key.
     */
    private RSAPrivateKey rsaPrivateKey;

    /**
     * Instantiate a new RSA private key reading the certificate from the URL.
     *
     * @param certificateLocation the certificate from the URL.
     * @throws SignatureException if any error occurs while reading the
     *         certificate.
     */
    public AbstractRsaSha1SigningKey(URL certificateLocation) throws SignatureException {
        super(certificateLocation);
    }

    /**
     * Return the RSA private key.
     *
     * @return the RSA private key.
     */
    public RSAPrivateKey getRsaPrivateKey() {
        return this.rsaPrivateKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init(byte[] bufferedValue) throws SignatureException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bufferedValue);
            try {
                this.rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);
            } catch (InvalidKeySpecException e) {
                throw new SignatureException("An error occurred while reading the private key ceritificate", e);
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO can this exception be ignored?
        }
    }

}
