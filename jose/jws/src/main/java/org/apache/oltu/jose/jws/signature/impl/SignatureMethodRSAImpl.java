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

import java.security.Signature;
import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.apache.oltu.jose.jws.JWSConstants;
import org.apache.oltu.jose.jws.signature.SignatureMethod;

/**
 * Class that asymmetrically sign and verify the issued token.
 */
public class SignatureMethodRSAImpl implements SignatureMethod<PrivateKey, PublicKey>{

    private String algorithm;

    public SignatureMethodRSAImpl(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Calculate the signature of given header.payload as for
     * <a href="http://tools.ietf.org/html/draft-ietf-jose-json-web-signature-21#appendix-A.2.1">appendix-A.2.1</a>
     *
     * {@inheritDoc}
     */
    @Override
    public String calculate(String header, String payload, PrivateKey signingKey) {
        byte[] token = toToken(header, payload);
        try {
            Signature signature = Signature.getInstance(getAlgorithmInternal());

            signature.initSign(signingKey.getPrivateKey());
            signature.update(token);
            token = signature.sign();

            return TokenDecoder.base64Encode(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verify the signature of given header.payload as for
     * <a href="http://tools.ietf.org/html/draft-ietf-jose-json-web-signature-21#appendix-A.2.2">appendix-A.2.2</a>
     *
     * {@inheritDoc}
     */
    @Override
    public boolean verify(String signature, String header, String payload, PublicKey verifyingKey) {
        byte[] token = toToken(header, payload);
        try {
            Signature sign = Signature.getInstance(getAlgorithmInternal());
            sign.initVerify(verifyingKey.getPublicKey());
            sign.update(token);

            return sign.verify(decode(signature));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    // ---------- Private methods ---------------------------------------------

    private static byte[] toToken(String header, String payload) {
        return new StringBuilder()
               .append(header)
               .append(".")
               .append(payload)
               .toString()
               .getBytes();
    }

    private String getAlgorithmInternal() {
        String alg = null;
        if (JWSConstants.RS256.equals(algorithm)) {
            alg = "SHA256withRSA";
        } else if (JWSConstants.RS384.equals(algorithm)) {
            alg = "SHA384withRSA";
        } else if (JWSConstants.RS512.equals(algorithm)) {
            alg = "SHA512withRSA";
        }
        return alg;
    }

    private static byte[] decode(String arg) throws Exception {
        String s = arg;
        s = s.replace('-', '+'); // 62nd char of encoding
        s = s.replace('_', '/'); // 63rd char of encoding

        switch (s.length() % 4) // Pad with trailing '='s
        {
          case 0: // No pad chars in this case
              break;

          case 2: // Two pad chars
              s += "==";
              break;

          case 3: // One pad char
              s += "=";
              break;

          default:
              throw new Exception("Illegal base64url string!");
        }

        return TokenDecoder.base64DecodeToByte(s);
    }

}
