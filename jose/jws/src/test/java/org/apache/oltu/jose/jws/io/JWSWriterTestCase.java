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

import static org.junit.Assert.assertEquals;

import org.apache.oltu.jose.jws.JWS;
import org.junit.Ignore;
import org.junit.Test;

public final class JWSWriterTestCase {

    private final JWSWriter jwsWriter = new JWSWriter();

    @Test
    @Ignore // FIXME JSON field order makes the function not invertible!!!
    public void serialize() {
        JWS jws = new JWS.Builder()
                         .setAlgorithm("HS256")
                         .setType("JWT")
                         .setPayload("{\"iss\":\"joe\",\r\n \"exp\":1300819380,\r\n \"http://example.com/is_root\":true}")
                         .setSignature("tߴ���%O�`}ح���%Miֿ�[XXS��y")
                         .build();
        String actual = jwsWriter.write(jws).replaceAll("\r?\n", "");
        assertEquals("eyJ0eXAiOiJKV1QiLA0KICJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk", actual);
    }

}
