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

import java.io.InputStream;
import java.net.URL;

import org.apache.labs.amber.signature.signers.SignatureException;

/**
 * Implementation of a PEM RSA public key.
 *
 * @version $Id$
 */
public final class PemRsaSha1VeryfingKey extends DerRsaSha1VeryfingKey {

    /**
     * Instantiate a new PEM RSA public key reading the certificate from the URL.
     *
     * @param certificateLocation the certificate from the URL.
     * @throws SignatureException if any error occurs while reading the
     *         certificate.
     */
    public PemRsaSha1VeryfingKey(URL certificateLocation) throws SignatureException {
        super(certificateLocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected byte[] readCertificate(InputStream input) throws SignatureException {
        PemCertificateParser parser = new PemCertificateParser(input);
        try {
            return parser.parsePublicCertificate();
        } catch (ParseException e) {
            throw new SignatureException("Syntax error while parsing public certificate", e);
        }
    }

}
