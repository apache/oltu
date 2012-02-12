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
package org.apache.amber.signature.hmac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.amber.signature.AbstractMethod;
import org.apache.amber.signature.SignatureException;
import org.apache.amber.signature.SigningKey;
import org.apache.amber.signature.VerifyingKey;

/**
 * HMAC-SHA1 Method implementation.
 *
 * @version $Id$
 */
public final class HmacSha1Method extends AbstractMethod<SigningKey, VerifyingKey> {

    private static final String HMAC_SHA1 = "HMAC-SHA1";

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String calculate(SigningKey signingKey,
            String tokenSecret,
            String baseString) throws SignatureException {
        String key = new StringBuilder(percentEncode(signingKey.getValue()))
                .append('&')
                .append(percentEncode(tokenSecret))
                .toString();

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("HMAC-SHA1 Algorithm not supported", e);
        }

        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new SignatureException(new StringBuilder("Signing key '")
                    .append(key)
                    .append("' caused HMAC-SHA1 error")
                    .toString(), e);
        }

        byte[] rawHmac = mac.doFinal(baseString.getBytes());

        return encodeBase64(rawHmac);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean verify(String signature,
            VerifyingKey verifyingKey,
            String tokenSecret,
            String baseString) throws SignatureException {
        String expectedSignature = this.calculate((SigningKey) verifyingKey, tokenSecret, baseString);

        if (this.getLog().isDebugEnabled()) {
            this.getLog().debug(new StringBuilder("Received signature {")
                    .append(signature)
                    .append("} expected signature {")
                    .append(expectedSignature)
                    .append('}')
                    .toString());
        }

        return expectedSignature.equals(signature);
    }

    /**
     * {@inheritDoc}
     */
    public String getAlgorithm() {
        return HMAC_SHA1;
    }

}
