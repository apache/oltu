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

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import org.apache.labs.amber.signature.signers.SignatureException;
import org.apache.labs.amber.signature.signers.SignatureMethod;
import org.apache.labs.amber.signature.signers.VerifyingKey;

/**
 * Abstract implementation of a RSA public key.
 *
 * @version $Id$
 */
@SignatureMethod("RSA-SHA1")
abstract class AbstractRsaSha1VerifyingKey extends AbstractRsaSha1Key implements VerifyingKey {

    /**
     * The X509 string constant.
     */
    private static final String X509 = "X509";

    /**
     * The RSA public key.
     */
    private RSAPublicKey rsaPublicKey;

    /**
     * Instantiate a new RSA public key reading the certificate from the URL.
     *
     * @param certificateLocation the certificate from the URL.
     * @throws SignatureException if any error occurs while reading the
     *         certificate.
     */
    public AbstractRsaSha1VerifyingKey(URL certificateLocation) throws SignatureException {
        super(certificateLocation);
    }

    /**
     * Return the RSA public key.
     *
     * @return the RSA public key.
     */
    public RSAPublicKey getRsaPublicKey() {
        return this.rsaPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init(byte[] bufferedValue) throws SignatureException {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);

            ByteArrayInputStream input = new ByteArrayInputStream(bufferedValue);
            try {
                X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(input);
                this.rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();
            } catch (CertificateException e) {
                throw new SignatureException("An error occurred while reading the public ceritificate", e);
            }
        } catch (CertificateException e) {
            // TODO can this exception be ignored?
        }
    }

}
