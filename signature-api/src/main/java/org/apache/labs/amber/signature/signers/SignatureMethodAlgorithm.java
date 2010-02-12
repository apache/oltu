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
package org.apache.labs.amber.signature.signers;

import org.apache.labs.amber.signature.descriptors.Service;
import org.apache.labs.amber.signature.message.RequestMessage;
import org.apache.labs.amber.signature.parameters.Parameter;

/**
 * Common definition of OAuth signature method algorithm.
 *
 * @version $Id$
 */
public interface SignatureMethodAlgorithm<S extends SigningKey, V extends VerifyingKey> {

    /**
     * Calculates the OAuth request message signature.
     *
     * @param signingKey the key has to be used to sign the request.
     * @param secretCredential the temporary/token credential.
     * @param service the service for which the signature is calculated.
     * @param message the OAuth message has to be signed.
     * @param parameterList the (optional) parameter list the cliend sends to
     *        the OAuth server.
     * @return the calculated signature.
     * @throws SignatureException if any error occurs.
     */
    String calculate(S signingKey,
            String secretCredential,
            Service service,
            RequestMessage message,
            Parameter... parameterList) throws SignatureException;

    /**
     * Verifies the OAuth request message signature.
     *
     * @param signature the OAuth signature has to be verified.
     * @param verifyingKey the key has to be used to verify the request.
     * @param secretCredential the temporary/token credential.
     * @param service the service for which the signature has to be verified.
     * @param message the signed OAuth message.
     * @param parameterList the (optional) parameter list the cliend sends to
     *        the OAuth server.
     * @return true if the signature is correct, false otherwise.
     * @throws SignatureException if any error occurs.
     */
    boolean verify(String signature,
            V verifyingKey,
            String secretCredential,
            Service service,
            RequestMessage message,
            Parameter... parameterList) throws SignatureException;

}
