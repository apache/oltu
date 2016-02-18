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
package org.apache.oltu.jose.jws.signature.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.apache.oltu.jose.jws.JWSConstants;
import org.apache.oltu.jose.jws.signature.SignatureMethod;

public class SignatureMethodsHMAC256Impl implements SignatureMethod<SymmetricKeyImpl, SymmetricKeyImpl> {

    @Override
    public String calculate(String header, String payload, SymmetricKeyImpl signingKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(header).append(".").append(payload);
        String stringToSign = sb.toString();
        byte[] bytes = stringToSign.getBytes();

        try {
            Mac mac = Mac.getInstance("HMACSHA256");
            mac.init(new SecretKeySpec(signingKey.getKey(), mac.getAlgorithm()));
            mac.update(bytes);
            bytes = mac.doFinal();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return TokenDecoder.base64Encode(bytes);
    }

    @Override
    public boolean verify(String signature, String header, String payload, SymmetricKeyImpl verifyingKey) {
        String signed = calculate(header, payload, verifyingKey);
        return signed.equals(signature);
    }

    @Override
    public String getAlgorithm() {
        return JWSConstants.HS256;
    }

}
