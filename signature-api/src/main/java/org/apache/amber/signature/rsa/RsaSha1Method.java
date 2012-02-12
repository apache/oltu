/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.amber.signature.rsa;

import java.security.Signature;

import org.apache.amber.signature.AbstractMethod;
import org.apache.amber.signature.SignatureException;
import org.apache.amber.signature.SigningKey;
import org.apache.amber.signature.VerifyingKey;

/**
 * RSA-SHA1 Method implementation.
 *
 * @version $Id$
 */
public final class RsaSha1Method extends AbstractMethod {

    /**
     * RSA+SHA1 algorithm name.
     */
    private static final String RSA_SHA1_ALGORITHM = "SHA1withRSA";

    /**
     * This method name.
     */
    private final static String RSA_SHA1 = "RSA-SHA1";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlgorithm() {
        return RSA_SHA1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String calculate(SigningKey signingKey,
            String tokenSecret,
            String baseString) throws SignatureException {
        try {
            Signature signer = Signature.getInstance(RSA_SHA1_ALGORITHM);
            signer.initSign(((RsaSha1SigningKey) signingKey).getPrivateKey());
            signer.update(toUTF8Bytes(baseString));
            byte[] signature = signer.sign();

            return encodeBase64(signature);
        } catch (Exception e) {
            // TODO add a meaningful message
            throw new SignatureException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean verify(String signature,
            VerifyingKey verifyingKey,
            String tokenSecret,
            String baseString) throws SignatureException {
        try {
            Signature verifier = Signature.getInstance(RSA_SHA1_ALGORITHM);
            verifier.initVerify(((RsaSha1VerifyingKey) verifyingKey).getPublicKey());
            verifier.update(toUTF8Bytes(baseString));

            return verifier.verify(decodeBase64(signature));
        } catch (Exception e) {
            // TODO add a meaningful message
            throw new SignatureException(e);
        }
    }

}
