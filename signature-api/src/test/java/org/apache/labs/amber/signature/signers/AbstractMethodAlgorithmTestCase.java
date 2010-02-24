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

import java.net.URL;

import org.apache.labs.amber.signature.descriptors.HTTPMethod;
import org.apache.labs.amber.signature.descriptors.Service;
import org.apache.labs.amber.signature.message.TokenRequestMessage;
import org.apache.labs.amber.signature.parameters.Parameter;

/**
 * Abstract implementation of OAuth signature method algorithm test case.
 *
 * @param <S> the {@link SigningKey} type.
 * @param <V> the {@link VerifyingKey} type.
 */
public abstract class AbstractMethodAlgorithmTestCase<S extends SigningKey, V extends VerifyingKey> {

    protected void verifySignature(S signingKey,
            V verifyingKey,
            SignatureMethodAlgorithm<S, V> methodAlgorithm,
            String expectedSignature) throws Exception {
        // token secret
        String secretCredential = "pfkkdhi9sl3r4s00";

        // oauth message
        TokenRequestMessage message = new TokenRequestMessage();
        message.setClientIdentifier("dpf43f3p2l4k3l03");
        message.setNonce("kllo9940pd9333jh");
        message.setSignatureMethod(methodAlgorithm.getClass().getAnnotation(SignatureMethod.class).value());
        message.setTimestamp(1191242096);
        message.setToken("nnch734d00sl2jdk");
        message.setVersion(TokenRequestMessage.DEFAULT_VERSION);

        // the service has to be invoked
        Service service = new Service(HTTPMethod.GET, new URL("http://photos.example.net/photos"));

        assert methodAlgorithm.verify(expectedSignature,
                verifyingKey,
                secretCredential,
                service,
                message,
                new Parameter("size", "original"),
                new Parameter("file", "vacation.jpg"));
    }

}
