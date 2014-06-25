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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test based on the example contained in
 * http://tools.ietf.org/html/draft-ietf-jose-json-web-signature-25#appendix-A.2 and
 * http://tools.ietf.org/html/draft-ietf-jose-cookbook-01#section-3.1
 *
 */
public class SignatureMethodRSAImplTest {

    private String rsa256;

    private SignatureMethodRSAImpl sRsaImpl;

    private String payload;

    private RSAPrivateKey rsaPrivKey;

    private RSAPublicKey rsaPublicKey;

    @Before
    public void setUp() throws Exception {
        sRsaImpl = new SignatureMethodRSAImpl("RS256");
    }

    @After
    public void tearDown() {
        payload = null;
        rsa256 = null;
        rsaPrivKey = null;
        rsaPublicKey = null;
        sRsaImpl= null;
    }

    //validates the example in  http://tools.ietf.org/html/draft-ietf-jose-json-web-signature-25#appendix-A.2
    @Test
    public void testCalculate() throws Exception{
        final byte[] n = { (byte) 161, (byte) 248, (byte) 22, (byte) 10, (byte) 226, (byte) 227, (byte) 201, (byte) 180,
                (byte) 101, (byte) 206, (byte) 141, (byte) 45, (byte) 101, (byte) 98, (byte) 99, (byte) 54, (byte) 43,
                (byte) 146, (byte) 125, (byte) 190, (byte) 41, (byte) 225, (byte) 240, (byte) 36, (byte) 119, (byte) 252,
                (byte) 22, (byte) 37, (byte) 204, (byte) 144, (byte) 161, (byte) 54, (byte) 227, (byte) 139, (byte) 217,
                (byte) 52, (byte) 151, (byte) 197, (byte) 182, (byte) 234, (byte) 99, (byte) 221, (byte) 119, (byte) 17,
                (byte) 230, (byte) 124, (byte) 116, (byte) 41, (byte) 249, (byte) 86, (byte) 176, (byte) 251, (byte) 138,
                (byte) 143, (byte) 8, (byte) 154, (byte) 220, (byte) 75, (byte) 105, (byte) 137, (byte) 60, (byte) 193,
                (byte) 51, (byte) 63, (byte) 83, (byte) 237, (byte) 208, (byte) 25, (byte) 184, (byte) 119, (byte) 132,
                (byte) 37, (byte) 47, (byte) 236, (byte) 145, (byte) 79, (byte) 228, (byte) 133, (byte) 119, (byte) 105,
                (byte) 89, (byte) 75, (byte) 234, (byte) 66, (byte) 128, (byte) 211, (byte) 44, (byte) 15, (byte) 85,
                (byte) 191, (byte) 98, (byte) 148, (byte) 79, (byte) 19, (byte) 3, (byte) 150, (byte) 188, (byte) 110,
                (byte) 155, (byte) 223, (byte) 110, (byte) 189, (byte) 210, (byte) 189, (byte) 163, (byte) 103, (byte) 142,
                (byte) 236, (byte) 160, (byte) 198, (byte) 104, (byte) 247, (byte) 1, (byte) 179, (byte) 141, (byte) 191,
                (byte) 251, (byte) 56, (byte) 200, (byte) 52, (byte) 44, (byte) 226, (byte) 254, (byte) 109, (byte) 39,
                (byte) 250, (byte) 222, (byte) 74, (byte) 90, (byte) 72, (byte) 116, (byte) 151, (byte) 157, (byte) 212,
                (byte) 185, (byte) 207, (byte) 154, (byte) 222, (byte) 196, (byte) 199, (byte) 91, (byte) 5, (byte) 133,
                (byte) 44, (byte) 44, (byte) 15, (byte) 94, (byte) 248, (byte) 165, (byte) 193, (byte) 117, (byte) 3,
                (byte) 146, (byte) 249, (byte) 68, (byte) 232, (byte) 237, (byte) 100, (byte) 193, (byte) 16, (byte) 198,
                (byte) 182, (byte) 71, (byte) 96, (byte) 154, (byte) 164, (byte) 120, (byte) 58, (byte) 235, (byte) 156,
                (byte) 108, (byte) 154, (byte) 215, (byte) 85, (byte) 49, (byte) 48, (byte) 80, (byte) 99, (byte) 139,
                (byte) 131, (byte) 102, (byte) 92, (byte) 111, (byte) 111, (byte) 122, (byte) 130, (byte) 163, (byte) 150,
                (byte) 112, (byte) 42, (byte) 31, (byte) 100, (byte) 27, (byte) 130, (byte) 211, (byte) 235, (byte) 242,
                (byte) 57, (byte) 34, (byte) 25, (byte) 73, (byte) 31, (byte) 182, (byte) 134, (byte) 135, (byte) 44,
                (byte) 87, (byte) 22, (byte) 245, (byte) 10, (byte) 248, (byte) 53, (byte) 141, (byte) 154, (byte) 139,
                (byte) 157, (byte) 23, (byte) 195, (byte) 64, (byte) 114, (byte) 143, (byte) 127, (byte) 135, (byte) 216,
                (byte) 154, (byte) 24, (byte) 216, (byte) 252, (byte) 171, (byte) 103, (byte) 173, (byte) 132, (byte) 89,
                (byte) 12, (byte) 46, (byte) 207, (byte) 117, (byte) 147, (byte) 57, (byte) 54, (byte) 60, (byte) 7,
                (byte) 3, (byte) 77, (byte) 111, (byte) 96, (byte) 111, (byte) 158, (byte) 33, (byte) 224, (byte) 84,
                (byte) 86, (byte) 202, (byte) 229, (byte) 233, (byte) 161 };
        final byte[] e = { 1, 0, 1 };
        final byte[] d = { 18, (byte) 174, (byte) 113, (byte) 164, (byte) 105, (byte) 205, (byte) 10, (byte) 43,
                (byte) 195, (byte) 126, (byte) 82, (byte) 108, (byte) 69, (byte) 0, (byte) 87, (byte) 31, (byte) 29,
                (byte) 97, (byte) 117, (byte) 29, (byte) 100, (byte) 233, (byte) 73, (byte) 112, (byte) 123, (byte) 98,
                (byte) 89, (byte) 15, (byte) 157, (byte) 11, (byte) 165, (byte) 124, (byte) 150, (byte) 60, (byte) 64,
                (byte) 30, (byte) 63, (byte) 207, (byte) 47, (byte) 44, (byte) 211, (byte) 189, (byte) 236, (byte) 136,
                (byte) 229, (byte) 3, (byte) 191, (byte) 198, (byte) 67, (byte) 155, (byte) 11, (byte) 40, (byte) 200,
                (byte) 47, (byte) 125, (byte) 55, (byte) 151, (byte) 103, (byte) 31, (byte) 82, (byte) 19, (byte) 238,
                (byte) 216, (byte) 193, (byte) 90, (byte) 37, (byte) 216, (byte) 213, (byte) 206, (byte) 160, (byte) 2,
                (byte) 94, (byte) 227, (byte) 171, (byte) 46, (byte) 139, (byte) 127, (byte) 121, (byte) 33, (byte) 111,
                (byte) 198, (byte) 59, (byte) 234, (byte) 86, (byte) 39, (byte) 83, (byte) 180, (byte) 6, (byte) 68,
                (byte) 198, (byte) 161, (byte) 81, (byte) 39, (byte) 217, (byte) 178, (byte) 149, (byte) 69, (byte) 64,
                (byte) 160, (byte) 187, (byte) 225, (byte) 163, (byte) 5, (byte) 86, (byte) 152, (byte) 45, (byte) 78,
                (byte) 159, (byte) 222, (byte) 95, (byte) 100, (byte) 37, (byte) 241, (byte) 77, (byte) 75, (byte) 113,
                (byte) 52, (byte) 65, (byte) 181, (byte) 93, (byte) 199, (byte) 59, (byte) 155, (byte) 74, (byte) 237,
                (byte) 204, (byte) 146, (byte) 172, (byte) 227, (byte) 146, (byte) 126, (byte) 55, (byte) 245, (byte) 125,
                (byte) 12, (byte) 253, (byte) 94, (byte) 117, (byte) 129, (byte) 250, (byte) 81, (byte) 44, (byte) 143,
                (byte) 73, (byte) 97, (byte) 169, (byte) 235, (byte) 11, (byte) 128, (byte) 248, (byte) 168, (byte) 7,
                (byte) 70, (byte) 114, (byte) 138, (byte) 85, (byte) 255, (byte) 70, (byte) 71, (byte) 31, (byte) 52,
                (byte) 37, (byte) 6, (byte) 59, (byte) 157, (byte) 83, (byte) 100, (byte) 47, (byte) 94, (byte) 222,
                (byte) 30, (byte) 132, (byte) 214, (byte) 19, (byte) 8, (byte) 26, (byte) 250, (byte) 92, (byte) 34,
                (byte) 208, (byte) 81, (byte) 40, (byte) 91, (byte) 214, (byte) 59, (byte) 148, (byte) 59, (byte) 86,
                (byte) 93, (byte) 137, (byte) 138, (byte) 5, (byte) 104, (byte) 84, (byte) 19, (byte) 229, (byte) 60,
                (byte) 60, (byte) 108, (byte) 101, (byte) 37, (byte) 255, (byte) 31, (byte) 227, (byte) 78, (byte) 61,
                (byte) 220, (byte) 112, (byte) 240, (byte) 213, (byte) 100, (byte) 80, (byte) 253, (byte) 164, (byte) 139,
                (byte) 161, (byte) 46, (byte) 16, (byte) 78, (byte) 157, (byte) 235, (byte) 159, (byte) 184, (byte) 24,
                (byte) 129, (byte) 225, (byte) 196, (byte) 189, (byte) 242, (byte) 93, (byte) 146, (byte) 71, (byte) 244,
                (byte) 80, (byte) 200, (byte) 101, (byte) 146, (byte) 121, (byte) 104, (byte) 231, (byte) 115, (byte) 52,
                (byte) 244, (byte) 65, (byte) 79, (byte) 117, (byte) 167, (byte) 80, (byte) 225, (byte) 57, (byte) 84,
                (byte) 110, (byte) 58, (byte) 138, (byte) 115, (byte) 157 };

        BigInteger N = new BigInteger(1, n);
        BigInteger E = new BigInteger(1, e);
        BigInteger D = new BigInteger(1, d);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(N, E);
        RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(N, D);
        rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        rsaPrivKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

        rsa256 = "{\"alg\":\"RS256\"}";
        payload = "{\"iss\":\"joe\",\r\n \"exp\":1300819380,\r\n \"http://example.com/is_root\":true}";

        assertEquals("cC4hiUPoj9Eetdgtv3hF80EGrhuB__dzERat0XF9g2VtQgr9PJbu3XOiZj5RZmh7"+
                "AAuHIm4Bh-0Qc_lF5YKt_O8W2Fp5jujGbds9uJdbF9CUAr7t1dnZcAcQjbKBYNX4"+
                "BAynRFdiuB--f_nZLgrnbyTyWzO75vRK5h6xBArLIARNPvkSjtQBMHlb1L07Qe7K"+
                "0GarZRmB_eSN9383LcOLn6_dO--xi12jzDwusC-eOkHWEsqtFZESc6BfI7noOPqv"+
                "hJ1phCnvWh6IeYI2w9QOYEUipUTI8np6LbgGY9Fs98rqVt5AXLIhWkWywlVmtVrB"+
                "p0igcN_IoypGlUPQGe77Rw",
                sRsaImpl.calculate(TokenDecoder.base64Encode(rsa256),
                        TokenDecoder.base64Encode(payload), new PrivateKey(rsaPrivKey)));
    }

    //validates the example in  http://tools.ietf.org/html/draft-ietf-jose-json-web-signature-25#appendix-A.2
    @Test
    public void testVerify() throws Exception{
        final byte[] n = { (byte) 161, (byte) 248, (byte) 22, (byte) 10, (byte) 226, (byte) 227, (byte) 201, (byte) 180,
                (byte) 101, (byte) 206, (byte) 141, (byte) 45, (byte) 101, (byte) 98, (byte) 99, (byte) 54, (byte) 43,
                (byte) 146, (byte) 125, (byte) 190, (byte) 41, (byte) 225, (byte) 240, (byte) 36, (byte) 119, (byte) 252,
                (byte) 22, (byte) 37, (byte) 204, (byte) 144, (byte) 161, (byte) 54, (byte) 227, (byte) 139, (byte) 217,
                (byte) 52, (byte) 151, (byte) 197, (byte) 182, (byte) 234, (byte) 99, (byte) 221, (byte) 119, (byte) 17,
                (byte) 230, (byte) 124, (byte) 116, (byte) 41, (byte) 249, (byte) 86, (byte) 176, (byte) 251, (byte) 138,
                (byte) 143, (byte) 8, (byte) 154, (byte) 220, (byte) 75, (byte) 105, (byte) 137, (byte) 60, (byte) 193,
                (byte) 51, (byte) 63, (byte) 83, (byte) 237, (byte) 208, (byte) 25, (byte) 184, (byte) 119, (byte) 132,
                (byte) 37, (byte) 47, (byte) 236, (byte) 145, (byte) 79, (byte) 228, (byte) 133, (byte) 119, (byte) 105,
                (byte) 89, (byte) 75, (byte) 234, (byte) 66, (byte) 128, (byte) 211, (byte) 44, (byte) 15, (byte) 85,
                (byte) 191, (byte) 98, (byte) 148, (byte) 79, (byte) 19, (byte) 3, (byte) 150, (byte) 188, (byte) 110,
                (byte) 155, (byte) 223, (byte) 110, (byte) 189, (byte) 210, (byte) 189, (byte) 163, (byte) 103, (byte) 142,
                (byte) 236, (byte) 160, (byte) 198, (byte) 104, (byte) 247, (byte) 1, (byte) 179, (byte) 141, (byte) 191,
                (byte) 251, (byte) 56, (byte) 200, (byte) 52, (byte) 44, (byte) 226, (byte) 254, (byte) 109, (byte) 39,
                (byte) 250, (byte) 222, (byte) 74, (byte) 90, (byte) 72, (byte) 116, (byte) 151, (byte) 157, (byte) 212,
                (byte) 185, (byte) 207, (byte) 154, (byte) 222, (byte) 196, (byte) 199, (byte) 91, (byte) 5, (byte) 133,
                (byte) 44, (byte) 44, (byte) 15, (byte) 94, (byte) 248, (byte) 165, (byte) 193, (byte) 117, (byte) 3,
                (byte) 146, (byte) 249, (byte) 68, (byte) 232, (byte) 237, (byte) 100, (byte) 193, (byte) 16, (byte) 198,
                (byte) 182, (byte) 71, (byte) 96, (byte) 154, (byte) 164, (byte) 120, (byte) 58, (byte) 235, (byte) 156,
                (byte) 108, (byte) 154, (byte) 215, (byte) 85, (byte) 49, (byte) 48, (byte) 80, (byte) 99, (byte) 139,
                (byte) 131, (byte) 102, (byte) 92, (byte) 111, (byte) 111, (byte) 122, (byte) 130, (byte) 163, (byte) 150,
                (byte) 112, (byte) 42, (byte) 31, (byte) 100, (byte) 27, (byte) 130, (byte) 211, (byte) 235, (byte) 242,
                (byte) 57, (byte) 34, (byte) 25, (byte) 73, (byte) 31, (byte) 182, (byte) 134, (byte) 135, (byte) 44,
                (byte) 87, (byte) 22, (byte) 245, (byte) 10, (byte) 248, (byte) 53, (byte) 141, (byte) 154, (byte) 139,
                (byte) 157, (byte) 23, (byte) 195, (byte) 64, (byte) 114, (byte) 143, (byte) 127, (byte) 135, (byte) 216,
                (byte) 154, (byte) 24, (byte) 216, (byte) 252, (byte) 171, (byte) 103, (byte) 173, (byte) 132, (byte) 89,
                (byte) 12, (byte) 46, (byte) 207, (byte) 117, (byte) 147, (byte) 57, (byte) 54, (byte) 60, (byte) 7,
                (byte) 3, (byte) 77, (byte) 111, (byte) 96, (byte) 111, (byte) 158, (byte) 33, (byte) 224, (byte) 84,
                (byte) 86, (byte) 202, (byte) 229, (byte) 233, (byte) 161 };
        final byte[] e = { 1, 0, 1 };
        final byte[] d = { 18, (byte) 174, (byte) 113, (byte) 164, (byte) 105, (byte) 205, (byte) 10, (byte) 43,
                (byte) 195, (byte) 126, (byte) 82, (byte) 108, (byte) 69, (byte) 0, (byte) 87, (byte) 31, (byte) 29,
                (byte) 97, (byte) 117, (byte) 29, (byte) 100, (byte) 233, (byte) 73, (byte) 112, (byte) 123, (byte) 98,
                (byte) 89, (byte) 15, (byte) 157, (byte) 11, (byte) 165, (byte) 124, (byte) 150, (byte) 60, (byte) 64,
                (byte) 30, (byte) 63, (byte) 207, (byte) 47, (byte) 44, (byte) 211, (byte) 189, (byte) 236, (byte) 136,
                (byte) 229, (byte) 3, (byte) 191, (byte) 198, (byte) 67, (byte) 155, (byte) 11, (byte) 40, (byte) 200,
                (byte) 47, (byte) 125, (byte) 55, (byte) 151, (byte) 103, (byte) 31, (byte) 82, (byte) 19, (byte) 238,
                (byte) 216, (byte) 193, (byte) 90, (byte) 37, (byte) 216, (byte) 213, (byte) 206, (byte) 160, (byte) 2,
                (byte) 94, (byte) 227, (byte) 171, (byte) 46, (byte) 139, (byte) 127, (byte) 121, (byte) 33, (byte) 111,
                (byte) 198, (byte) 59, (byte) 234, (byte) 86, (byte) 39, (byte) 83, (byte) 180, (byte) 6, (byte) 68,
                (byte) 198, (byte) 161, (byte) 81, (byte) 39, (byte) 217, (byte) 178, (byte) 149, (byte) 69, (byte) 64,
                (byte) 160, (byte) 187, (byte) 225, (byte) 163, (byte) 5, (byte) 86, (byte) 152, (byte) 45, (byte) 78,
                (byte) 159, (byte) 222, (byte) 95, (byte) 100, (byte) 37, (byte) 241, (byte) 77, (byte) 75, (byte) 113,
                (byte) 52, (byte) 65, (byte) 181, (byte) 93, (byte) 199, (byte) 59, (byte) 155, (byte) 74, (byte) 237,
                (byte) 204, (byte) 146, (byte) 172, (byte) 227, (byte) 146, (byte) 126, (byte) 55, (byte) 245, (byte) 125,
                (byte) 12, (byte) 253, (byte) 94, (byte) 117, (byte) 129, (byte) 250, (byte) 81, (byte) 44, (byte) 143,
                (byte) 73, (byte) 97, (byte) 169, (byte) 235, (byte) 11, (byte) 128, (byte) 248, (byte) 168, (byte) 7,
                (byte) 70, (byte) 114, (byte) 138, (byte) 85, (byte) 255, (byte) 70, (byte) 71, (byte) 31, (byte) 52,
                (byte) 37, (byte) 6, (byte) 59, (byte) 157, (byte) 83, (byte) 100, (byte) 47, (byte) 94, (byte) 222,
                (byte) 30, (byte) 132, (byte) 214, (byte) 19, (byte) 8, (byte) 26, (byte) 250, (byte) 92, (byte) 34,
                (byte) 208, (byte) 81, (byte) 40, (byte) 91, (byte) 214, (byte) 59, (byte) 148, (byte) 59, (byte) 86,
                (byte) 93, (byte) 137, (byte) 138, (byte) 5, (byte) 104, (byte) 84, (byte) 19, (byte) 229, (byte) 60,
                (byte) 60, (byte) 108, (byte) 101, (byte) 37, (byte) 255, (byte) 31, (byte) 227, (byte) 78, (byte) 61,
                (byte) 220, (byte) 112, (byte) 240, (byte) 213, (byte) 100, (byte) 80, (byte) 253, (byte) 164, (byte) 139,
                (byte) 161, (byte) 46, (byte) 16, (byte) 78, (byte) 157, (byte) 235, (byte) 159, (byte) 184, (byte) 24,
                (byte) 129, (byte) 225, (byte) 196, (byte) 189, (byte) 242, (byte) 93, (byte) 146, (byte) 71, (byte) 244,
                (byte) 80, (byte) 200, (byte) 101, (byte) 146, (byte) 121, (byte) 104, (byte) 231, (byte) 115, (byte) 52,
                (byte) 244, (byte) 65, (byte) 79, (byte) 117, (byte) 167, (byte) 80, (byte) 225, (byte) 57, (byte) 84,
                (byte) 110, (byte) 58, (byte) 138, (byte) 115, (byte) 157 };

        BigInteger N = new BigInteger(1, n);
        BigInteger E = new BigInteger(1, e);
        BigInteger D = new BigInteger(1, d);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(N, E);
        RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(N, D);
        rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        rsaPrivKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

        String accessToken = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.cC4hiUPoj9Eetdgtv3hF80EGrhuB__dzERat0XF9g2VtQgr9PJbu3XOiZj5RZmh7AAuHIm4Bh-0Qc_lF5YKt_O8W2Fp5jujGbds9uJdbF9CUAr7t1dnZcAcQjbKBYNX4BAynRFdiuB--f_nZLgrnbyTyWzO75vRK5h6xBArLIARNPvkSjtQBMHlb1L07Qe7K0GarZRmB_eSN9383LcOLn6_dO--xi12jzDwusC-eOkHWEsqtFZESc6BfI7noOPqvhJ1phCnvWh6IeYI2w9QOYEUipUTI8np6LbgGY9Fs98rqVt5AXLIhWkWywlVmtVrBp0igcN_IoypGlUPQGe77Rw";
        String jwt[] = accessToken.split("\\.");
        assertTrue(sRsaImpl.verify(jwt[2], jwt[0], jwt[1], new PublicKey(rsaPublicKey)));
    }

    //validates the example in http://tools.ietf.org/html/draft-ietf-jose-cookbook-01#section-3.1
    @Test
    public void testCalculateCookbook() throws Exception{
        final byte[] n = TokenDecoder.base64DecodeToByte("n4EPtAOCc9AlkeQHPzHStgAbgs7bTZLwUBZdR8_KuKPEHLd4rHVTeT-O-XV2jRojdNhxJWTDvNd7nqQ0VEiZQHz_AJmSCpMaJMRBSFKrKb2wqVwGU_NsYOYL-QtiWN2lbzcEe6XC0dApr5ydQLrHqkHHig3RBordaZ6Aj-oBHqFEHYpPe7Tpe-OfVfHd1E6cS6M1FZcD1NNLYD5lFHpPI9bTwJlsde3uhGqC0ZCuEHg8lhzwOHrtIQbS0FVbb9k3-tVTU4fg_3L_vniUFAKwuCLqKnS2BYwdq_mzSnbLY7h_qixoR7jig3__kRhuaxwUkRz5iaiQkqgc5gHdrNP5zw");
        final byte[] e =TokenDecoder.base64DecodeToByte("AQAB");
        final byte[] d = TokenDecoder.base64DecodeToByte("bWUC9B-EFRIo8kpGfh0ZuyGPvMNKvYWNtB_ikiH9k20eT-O1q_I78eiZkpXxXQ0UTEs2LsNRS-8uJbvQ-A1irkwMSMkK1J3XTGgdrhCku9gRldY7sNA_AKZGh-Q661_42rINLRCe8W-nZ34ui_qOfkLnK9QWDDqpaIsA-bMwWWSDFu2MUBYwkHTMEzLYGqOe04noqeq1hExBTHBOBdkMXiuFhUq1BU6l-DqEiWxqg82sXt2h-LMnT3046AOYJoRioz75tSUQfGCshWTBnP5uDjd18kKhyv07lhfSJdrPdM5Plyl21hsFf4L_mHCuoFau7gdsPfHPxxjVOcOpBrQzwQ");

        BigInteger N = new BigInteger(1, n);
        BigInteger E = new BigInteger(1, e);
        BigInteger D = new BigInteger(1, d);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(N, E);
        RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(N, D);
        rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        rsaPrivKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);


        rsa256 = "{\"alg\":\"RS256\",\"kid\":\"bilbo.baggins@hobbiton.example\"}";

        assertEquals("MRjdkly7_-oTPTS3AXP41iQIGKa80A0ZmTuV5MEaHoxnW2e5CZ5NlKtainoFmK"+
                "ZopdHM1O2U4mwzJdQx996ivp83xuglII7PNDi84wnB-BDkoBwA78185hX-Es4J"+
                "IwmDLJK3lfWRa-XtL0RnltuYv746iYTh_qHRD68BNt1uSNCrUCTJDt5aAE6x8w"+
                "W1Kt9eRo4QPocSadnHXFxnt8Is9UzpERV0ePPQdLuW3IS_de3xyIrDaLGdjluP"+
                "xUAhb6L2aXic1U12podGU0KLUQSE_oI-ZnmKJ3F4uOZDnd6QZWJushZ41Axf_f"+
                "cIe8u9ipH84ogoree7vjbU5y18kDquDg",
                sRsaImpl.calculate(TokenDecoder.base64Encode(rsa256),
                "SXTigJlzIGEgZGFuZ2Vyb3VzIGJ1c2luZXNzLCBGcm9kbywgZ29pbmcgb3V0IH"+
                "lvdXIgZG9vci4gWW91IHN0ZXAgb250byB0aGUgcm9hZCwgYW5kIGlmIHlvdSBk"+
                "b24ndCBrZWVwIHlvdXIgZmVldCwgdGhlcmXigJlzIG5vIGtub3dpbmcgd2hlcm"+
                "UgeW91IG1pZ2h0IGJlIHN3ZXB0IG9mZiB0by4", new PrivateKey(rsaPrivKey)));
    }

    //validates the example in http://tools.ietf.org/html/draft-ietf-jose-cookbook-01#section-3.1
    @Test
    public void testVerifyCookbook() throws Exception{
        final byte[] n = TokenDecoder.base64DecodeToByte("n4EPtAOCc9AlkeQHPzHStgAbgs7bTZLwUBZdR8_KuKPEHLd4rHVTeT-O-XV2jRojdNhxJWTDvNd7nqQ0VEiZQHz_AJmSCpMaJMRBSFKrKb2wqVwGU_NsYOYL-QtiWN2lbzcEe6XC0dApr5ydQLrHqkHHig3RBordaZ6Aj-oBHqFEHYpPe7Tpe-OfVfHd1E6cS6M1FZcD1NNLYD5lFHpPI9bTwJlsde3uhGqC0ZCuEHg8lhzwOHrtIQbS0FVbb9k3-tVTU4fg_3L_vniUFAKwuCLqKnS2BYwdq_mzSnbLY7h_qixoR7jig3__kRhuaxwUkRz5iaiQkqgc5gHdrNP5zw");
        final byte[] e =TokenDecoder.base64DecodeToByte("AQAB");
        final byte[] d = TokenDecoder.base64DecodeToByte("bWUC9B-EFRIo8kpGfh0ZuyGPvMNKvYWNtB_ikiH9k20eT-O1q_I78eiZkpXxXQ0UTEs2LsNRS-8uJbvQ-A1irkwMSMkK1J3XTGgdrhCku9gRldY7sNA_AKZGh-Q661_42rINLRCe8W-nZ34ui_qOfkLnK9QWDDqpaIsA-bMwWWSDFu2MUBYwkHTMEzLYGqOe04noqeq1hExBTHBOBdkMXiuFhUq1BU6l-DqEiWxqg82sXt2h-LMnT3046AOYJoRioz75tSUQfGCshWTBnP5uDjd18kKhyv07lhfSJdrPdM5Plyl21hsFf4L_mHCuoFau7gdsPfHPxxjVOcOpBrQzwQ");

        BigInteger N = new BigInteger(1, n);
        BigInteger E = new BigInteger(1, e);
        BigInteger D = new BigInteger(1, d);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(N, E);
        RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(N, D);
        rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        rsaPrivKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

        String accessToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImJpbGJvLmJhZ2dpbnNAaG9iYml0b24uZXhhbXBsZSJ9." +
                "SXTigJlzIGEgZGFuZ2Vyb3VzIGJ1c2luZXNzLCBGcm9kbywgZ29pbmcgb3V0IH" +
                "lvdXIgZG9vci4gWW91IHN0ZXAgb250byB0aGUgcm9hZCwgYW5kIGlmIHlvdSBk" +
                "b24ndCBrZWVwIHlvdXIgZmVldCwgdGhlcmXigJlzIG5vIGtub3dpbmcgd2hlcm" +
                "UgeW91IG1pZ2h0IGJlIHN3ZXB0IG9mZiB0by4." +
                "MRjdkly7_-oTPTS3AXP41iQIGKa80A0ZmTuV5MEaHoxnW2e5CZ5NlKtainoFmK" +
                "ZopdHM1O2U4mwzJdQx996ivp83xuglII7PNDi84wnB-BDkoBwA78185hX-Es4J" +
                "IwmDLJK3lfWRa-XtL0RnltuYv746iYTh_qHRD68BNt1uSNCrUCTJDt5aAE6x8w" +
                "W1Kt9eRo4QPocSadnHXFxnt8Is9UzpERV0ePPQdLuW3IS_de3xyIrDaLGdjluP" +
                "xUAhb6L2aXic1U12podGU0KLUQSE_oI-ZnmKJ3F4uOZDnd6QZWJushZ41Axf_f" +
                "cIe8u9ipH84ogoree7vjbU5y18kDquDg";
        String jwt[] = accessToken.split("\\.");
        assertTrue(sRsaImpl.verify(jwt[2], jwt[0], jwt[1], new PublicKey(rsaPublicKey)));
    }

}
