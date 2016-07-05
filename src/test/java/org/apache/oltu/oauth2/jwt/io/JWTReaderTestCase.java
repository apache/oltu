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

import org.apache.oltu.oauth2.jwt.ClaimsSet;
import org.apache.oltu.oauth2.jwt.Header;
import org.apache.oltu.oauth2.jwt.JWT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public final class JWTReaderTestCase implements IOTestCaseConstants {

    private JWT jwt;

    private final JWTReader jwtReader = new JWTReader();

    @Before
    public void setUp() throws Exception {
        jwt = jwtReader.read(JWT);
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
    public void testJWTWithMultipleAudiences() throws Exception {
        jwt = jwtReader.read(JWT_MULTIPLE_AUDIENCES);
        assertEquals(JWT_MULTIPLE_AUDIENCES, jwt.getRawString());
    }

    @Test
    public void testHeader() throws Exception {
        Header header = jwt.getHeader();
        assertEquals("RS256", header.getAlgorithm());
    }

    @Test
    public void testClaimsSet() throws Exception {
        ClaimsSet claimsSet = jwt.getClaimsSet();
        assertEquals(Arrays.asList("788732372078.apps.googleusercontent.com"), claimsSet.getAudiences());
        assertEquals("788732372078.apps.googleusercontent.com", claimsSet.getAudience());
        assertEquals("accounts.google.com", claimsSet.getIssuer());
        assertEquals("106422453082479998429", claimsSet.getSubject());
        assertEquals(1366730217L, claimsSet.getExpirationTime());
        assertEquals(1366726317L, claimsSet.getIssuedAt());
    }

    @Test
    public void testClaimsSetWithMultipleAudiences() throws Exception {
        jwt = jwtReader.read(JWT_MULTIPLE_AUDIENCES);
        ClaimsSet claimsSet = jwt.getClaimsSet();
        assertEquals(Arrays.asList("788732372078.apps.googleusercontent.com", "foo"), claimsSet.getAudiences());
        assertEquals("788732372078.apps.googleusercontent.com", claimsSet.getAudience());
        assertEquals("accounts.google.com", claimsSet.getIssuer());
        assertEquals("106422453082479998429", claimsSet.getSubject());
        assertEquals(1366730217L, claimsSet.getExpirationTime());
        assertEquals(1366726317L, claimsSet.getIssuedAt());
    }

}
