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
package org.apache.oltu.jose.jws.signature;

import static org.junit.Assert.*;

import org.apache.oltu.jose.jws.JWS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class SignatureMethodTestCase {

    private String signature;

    private TestSymetricKey key;

    private TestSignatureMethod method;

    @Before
    public void setUp() {
        signature = "supercalifragilistichespiralidoso123456789";
        key = new TestSymetricKey(signature);
        method = new TestSignatureMethod();
    }

    @After
    public void tearDown() {
        signature = null;
        key = null;
        method = null;
    }

    @Test
    public void simpleSignatureVerification() {
        assertEquals(signature, method.calculate(key));
        assertTrue(method.verify(signature, key));
    }

    @Test
    public void signJWS() {
        JWS jws = new JWS.Builder()
                         .setType("JWT")
                         .setPayload("{\"iss\":\"joe\",\r\n \"exp\":1300819380,\r\n \"http://example.com/is_root\":true}")
                         .sign( method, key )
                         .build();

        assertEquals("TEST", jws.getHeader().getAlgorithm());
        assertEquals(signature, jws.getSignature());
    }

}
