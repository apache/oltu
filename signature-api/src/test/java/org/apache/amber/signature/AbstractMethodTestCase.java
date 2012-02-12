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

import static junit.framework.Assert.assertTrue;

import java.net.URI;

import org.apache.amber.HTTPMethod;
import org.apache.amber.OAuthMessageParameter;
import org.apache.amber.OAuthParameter;
import org.apache.amber.OAuthRequest;
import org.apache.amber.OAuthRequestParameter;
import org.apache.amber.OAuthToken;
import org.apache.amber.Version;

/**
 * Abstract implementation of OAuth signature method algorithm test case.
 *
 * @version $Id$
 */
public abstract class AbstractMethodTestCase {

    protected <SK extends SigningKey, VK extends VerifyingKey> void verifySignature(VK verifyingKey,
            SignatureMethod<SK, VK> signatureMethod,
            String expectedSignature) throws Exception {
        OAuthRequest request = new FakeOAuthRequest();
        request.setRequestURL(URI.create("http://photos.example.net/photos"));
        request.setHTTPMethod(HTTPMethod.GET);
        addMessageParameter(OAuthParameter.CONSUMER_KEY, "dpf43f3p2l4k3l03", request);
        addMessageParameter(OAuthParameter.NONCE, "kllo9940pd9333jh", request);
        addMessageParameter(OAuthParameter.SIGNATURE_METHOD, signatureMethod.getAlgorithm(), request);
        addMessageParameter(OAuthParameter.TIMESTAMP, "1191242096", request);
        addMessageParameter(OAuthParameter.TOKEN, "nnch734d00sl2jdk", request);
        addMessageParameter(OAuthParameter.VERSION, Version.v1_0.toString(), request);
        addMessageParameter("size", "original", request);
        addMessageParameter("file", "vacation.jpg", request);

        OAuthToken token = new FakeToken();
        token.setTokenSecret("pfkkdhi9sl3r4s00");

        assertTrue(signatureMethod.verify(expectedSignature, verifyingKey, token, request));
    }

    private static void addMessageParameter(OAuthParameter parameter, String value, OAuthRequest request) {
        request.addOAuthMessageParameter(new OAuthMessageParameter(parameter, value));
    }

    private static void addMessageParameter(String name, String value, OAuthRequest request) {
        request.addOAuthRequestParameter(new OAuthRequestParameter(name, value));
    }

}
