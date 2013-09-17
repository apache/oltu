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
package org.apache.oltu.oauth2.jwt;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JWTUtilsTest extends Assert {

    private final String JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImJlMWRhMGIzNTY3YmQyNjVhMjUwOThmYmNjMmIwOWYyMTM0\r\n"
                             + "NWIzYTIifQ\r\n"
                             + ".\r\n"
                             + "eyJhdWQiOiI3ODg3MzIzNzIwNzguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJpc3MiOiJh\r\n"
                             + "Y2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTA2NDIyNDUzMDgyNDc5OTk4NDI5IiwiZXhwIjox\r\n"
                             + "MzY2NzMwMjE3LCJpYXQiOjEzNjY3MjYzMTcsImlkIjoiMTA2NDIyNDUzMDgyNDc5OTk4NDI5Iiwi\r\n"
                             + "dmVyaWZpZWRfZW1haWwiOiJ0cnVlIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiY2lkIjoiNzg4\r\n"
                             + "NzMyMzcyMDc4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXpwIjoiNzg4NzMyMzcyMDc4\r\n"
                             + "LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJhbnRvbmlvLnNhbnNvQGdtYWls\r\n"
                             + "LmNvbSIsInRva2VuX2hhc2giOiJMMkk3N2dpQkxrMFJTczB6UTFTdkNBIiwiYXRfaGFzaCI6Ikwy\r\n"
                             + "STc3Z2lCTGswUlNzMHpRMVN2Q0EifQ\r\n"
                             + ".\r\n"
                             + "XWYi5Zj1YWAMGIml_ftoAwmvW1Y7oeybLCpzQrJVuWJpS8L8Vd2TL-RTIOEVG03VA7e0_-_frNuw7MxUgVEgh8G-Nnbk_baJ6k_3w5c1SKFamFiHHDoKLFhrt1Y8JKSuGwE02V-px4Cn0dRAQAc1IN5CU6wqCrYK0p-fv_fvy28";

    private JWT jwt;

    @Before
    public void setUp() throws Exception {
        jwt = JWTUtils.parseJWT(JWT);
    }

    @After
    public void tearDown() throws Exception {
        jwt = null;
    }

    @Test
    public void testJWT() throws Exception {
        assertEquals(JWT, jwt.getRawString());
    }

    @Test
    public void testHeader() throws Exception {
        Header header = jwt.getHeader();
        assertEquals("RS256", header.getAlgorithm());
    }

    @Test
    public void testClaimsSet() throws Exception {
        ClaimsSet claimsSet = jwt.getClaimsSet();
        assertEquals("788732372078.apps.googleusercontent.com", claimsSet.getAudience());
        assertEquals("accounts.google.com", claimsSet.getIssuer());
        assertEquals("106422453082479998429", claimsSet.getSubject());
        assertEquals(1366730217, claimsSet.getExpirationTime());
        assertEquals(1366726317, claimsSet.getIssuedAt());
    }

    @Test
    public void serialization() throws Exception {
        JWT jwt = new JWT.Builder()
                         // header
                         .setHeaderAlgorithm("RS256")
                         .setHeaderCustomField("kid", "be1da0b3567bd265a25098fbcc2b09f21345b3a2")
                         // claimset
                         .setClaimsSetAudience("788732372078.apps.googleusercontent.com")
                         .setClaimsSetIssuer("accounts.google.com")
                         .setClaimsSetSubject("106422453082479998429")
                         .setClaimsSetExpirationTime(1366730217)
                         .setClaimsSetIssuedAt(1366726317)
                         .setClaimsSetCustomField("id", "106422453082479998429")
                         .setClaimsSetCustomField("verified_email", "true")
                         .setClaimsSetCustomField("email_verified", "true")
                         .setClaimsSetCustomField("cid", "788732372078.apps.googleusercontent.com")
                         .setClaimsSetCustomField("azp", "788732372078.apps.googleusercontent.com")
                         .setClaimsSetCustomField("email", "antonio.sanso@gmail.com")
                         .setClaimsSetCustomField("token_hash", "L2I77giBLk0RSs0zQ1SvCA")
                         .setClaimsSetCustomField("at_hash", "L2I77giBLk0RSs0zQ1SvCA")
                         // signature
                         .setSignature("XWYi5Zj1YWAMGIml_ftoAwmvW1Y7oeybLCpzQrJVuWJpS8L8Vd2TL-RTIOEVG03VA7e0_-_frNuw7MxUgVEgh8G-Nnbk_baJ6k_3w5c1SKFamFiHHDoKLFhrt1Y8JKSuGwE02V-px4Cn0dRAQAc1IN5CU6wqCrYK0p-fv_fvy28")
                         .build();
        String encodedJWT = JWTUtils.toBase64JsonString(jwt);
        assertEquals(JWT, encodedJWT);
    }

}
