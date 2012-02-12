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
package org.apache.amber.signature.plaintext;

import org.apache.amber.signature.AbstractMethod;
import org.apache.amber.signature.SignatureException;
import org.apache.amber.signature.SigningKey;
import org.apache.amber.signature.VerifyingKey;

/**
 * PLAINTEXT Method implementation.
 *
 * @version $Id$
 */
public final class PlaintextMethod extends AbstractMethod<SigningKey, VerifyingKey> {

    private static final String PLAINTEXT = "PLAINTEXT";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String calculate(SigningKey signingKey,
            String tokenSecret,
            String baseString) throws SignatureException {
        return new StringBuilder(signingKey.getValue())
                .append('&')
                .append(tokenSecret)
                .toString();
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
        return PLAINTEXT;
    }

}
