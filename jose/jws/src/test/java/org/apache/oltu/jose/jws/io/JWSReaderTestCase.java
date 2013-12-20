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
package org.apache.oltu.jose.jws.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.apache.oltu.jose.jws.JWS;
import org.junit.Test;

public final class JWSReaderTestCase {

    private JWSReader reader = new JWSReader();

    @Test
    public void parse() {
        JWS jws = reader.read("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImNyaXQiOlsiZXhwIl19.eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.dBjftO-_ve-_ve-_vSVP77-9YH3Yre-_ve-_vRbvv70lTWnWv--_vVtYBVhT77-977-9eQ");

        assertEquals("JWT", jws.getHeader().getType());
        assertEquals("HS256", jws.getHeader().getAlgorithm());
        assertArrayEquals(new String[]{ "exp" }, jws.getHeader().getCritical());
        assertEquals("{\"iss\":\"joe\",\r\n \"exp\":1300819380,\r\n \"http://example.com/is_root\":true}", jws.getPayload());
        assertEquals("dBjftO-_ve-_ve-_vSVP77-9YH3Yre-_ve-_vRbvv70lTWnWv--_vVtYBVhT77-977-9eQ", jws.getSignature());
    }

}
