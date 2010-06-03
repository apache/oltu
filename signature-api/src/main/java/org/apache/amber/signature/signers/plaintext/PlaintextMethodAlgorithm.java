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
package org.apache.amber.signature.signers.plaintext;

import org.apache.amber.signature.signers.AbstractMethodAlgorithm;
import org.apache.amber.signature.signers.SignatureException;
import org.apache.amber.signature.signers.SignatureMethod;

/**
 * PLAINTEXT Method implementation.
 *
 * @version $Id$
 */
@SignatureMethod("PLAINTEXT")
public final class PlaintextMethodAlgorithm extends AbstractMethodAlgorithm<PlaintextKey, PlaintextKey> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String encode(PlaintextKey signingKey,
            String secretCredential,
            String baseString) throws SignatureException {
        return new StringBuilder(signingKey.getValue())
                .append('&')
                .append(secretCredential)
                .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean verify(String signature,
            PlaintextKey verifyingKey,
            String secretCredential,
            String baseString) throws SignatureException {
        String expectedSignature = this.encode(verifyingKey, secretCredential, baseString);

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

}
