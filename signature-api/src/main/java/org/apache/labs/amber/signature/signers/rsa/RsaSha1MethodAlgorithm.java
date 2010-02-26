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

import java.security.Signature;

import org.apache.commons.codec.binary.Base64;
import org.apache.labs.amber.signature.signers.AbstractMethodAlgorithm;
import org.apache.labs.amber.signature.signers.SignatureException;
import org.apache.labs.amber.signature.signers.SignatureMethod;

/**
 * RSA-SHA1 Method implementation.
 *
 * @version $Id$
 */
@SignatureMethod("RSA-SHA1")
public final class RsaSha1MethodAlgorithm extends AbstractMethodAlgorithm<DerRsaSha1SigningKey, DerRsaSha1VeryfingKey> {

    /**
     * The RSA+SHA1 algorithm name.
     */
    private static final String RSA_SHA1_ALGORITHM = "SHA1withRSA";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String encode(DerRsaSha1SigningKey signingKey,
            String secretCredential,
            String baseString) throws SignatureException {
        try {
            Signature signer = Signature.getInstance(RSA_SHA1_ALGORITHM);
            signer.initSign(signingKey.getRsaPrivateKey());
            signer.update(toUTF8Bytes(baseString));

            byte[] signature = signer.sign();

            return new String(Base64.encodeBase64(signature, false));
        } catch (Exception e) {
            throw new SignatureException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean verify(String signature,
            DerRsaSha1VeryfingKey verifyingKey,
            String secretCredential,
            String baseString) throws SignatureException {
        return false;
    }

}
