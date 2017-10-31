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
package org.apache.oltu.oauth2.jwt.io;

import org.apache.oltu.oauth2.jwt.JWT;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public final class JWTWriterTestCase implements IOTestCaseConstants {

    @Test
    public void write() {
        JWT jwt = new JWT.Builder()
                          // header
                          .setHeaderAlgorithm("RS256")
                          .setHeaderCustomField("kid", "be1da0b3567bd265a25098fbcc2b09f21345b3a2")
                          // claimset
                          .setClaimsSetAudience("788732372078.apps.googleusercontent.com")
                          .setClaimsSetIssuer("accounts.google.com")
                          .setClaimsSetSubject("106422453082479998429")
                          .setClaimsSetExpirationTime(1366730217L)
                          .setClaimsSetIssuedAt(1366726317L)
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
        String encodedJWT = new JWTWriter().write(jwt);
        assertEquals(JWT, encodedJWT);
    }

    @Test
    public void writeSingleAudienceAsList() {
        JWT jwt = new JWT.Builder()
                          // header
                          .setHeaderAlgorithm("RS256")
                          .setHeaderCustomField("kid", "be1da0b3567bd265a25098fbcc2b09f21345b3a2")
                          // claimset
                          .setClaimsSetAudiences(Arrays.asList("788732372078.apps.googleusercontent.com"))
                          .setClaimsSetIssuer("accounts.google.com")
                          .setClaimsSetSubject("106422453082479998429")
                          .setClaimsSetExpirationTime(1366730217L)
                          .setClaimsSetIssuedAt(1366726317L)
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
        String encodedJWT = new JWTWriter().write(jwt);
        assertEquals(JWT, encodedJWT);
    }

    @Test
    public void writeWithMultipleAudiences() {
        JWT jwt = new JWT.Builder()
                          // header
                          .setHeaderAlgorithm("RS256")
                          .setHeaderCustomField("kid", "be1da0b3567bd265a25098fbcc2b09f21345b3a2")
                          // claimset
                          .setClaimsSetAudiences(Arrays.asList("788732372078.apps.googleusercontent.com", "foo"))
                          .setClaimsSetIssuer("accounts.google.com")
                          .setClaimsSetSubject("106422453082479998429")
                          .setClaimsSetExpirationTime(1366730217L)
                          .setClaimsSetIssuedAt(1366726317L)
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
        String encodedJWT = new JWTWriter().write(jwt);
        assertEquals(JWT_MULTIPLE_AUDIENCES, encodedJWT);
    }

}
