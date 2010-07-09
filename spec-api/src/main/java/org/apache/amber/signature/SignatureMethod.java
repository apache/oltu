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
package org.apache.amber.signature;

import org.apache.amber.OAuthRequest;

/**
 * Common definition of OAuth signature method algorithm.
 *
 * @version $Id$
 */
public interface SignatureMethod {

    /**
     * Returns the signing algorithm method.
     *
     * @return the signing algorithm method.
     */
    String getAlgorithm();
    
    /**
     * @param value
     * @return key
     */
    SigningKey createSigningKey(String... value);

    /**
     * @param value
     * @return key
     */
    VerifyingKey createVerifyingKey(String... value);

    /**
     * Calculates the OAuth request message signature.
     *
     * @param signingKey the key has to be used to sign the request.
     * @param request the OAuth request message has to be signed.
     * @return the calculated signature.
     * @throws SignatureException if any error occurs.
     */
    String calculate(SigningKey signingKey, OAuthRequest request) throws SignatureException;

    /**
     * Verifies the OAuth request message signature.
     *
     * @param signature the OAuth signature has to be verified.
     * @param verifyingKey the key has to be used to verify the request.
     * @param request the signed OAuth request message.
     * @return true if the signature is correct, false otherwise.
     * @throws SignatureException if any error occurs.
     */
    boolean verify(String signature,
            VerifyingKey verifyingKey,
            OAuthRequest request) throws SignatureException;

}
