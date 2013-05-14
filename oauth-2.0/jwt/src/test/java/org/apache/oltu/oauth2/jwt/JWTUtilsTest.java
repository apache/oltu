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

import org.junit.Assert;
import org.junit.Test;

public class JWTUtilsTest extends Assert {

	private final String JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImJlMWRhMGIzNTY3YmQyNjVhMjUwOThmYmNjMmIwOWYyMTM0NWIzYTIifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiaWQiOiIxMDY0MjI0NTMwODI0Nzk5OTg0MjkiLCJzdWIiOiIxMDY0MjI0NTMwODI0Nzk5OTg0MjkiLCJ2ZXJpZmllZF9lbWFpbCI6InRydWUiLCJlbWFpbF92ZXJpZmllZCI6InRydWUiLCJhdWQiOiI3ODg3MzIzNzIwNzguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJjaWQiOiI3ODg3MzIzNzIwNzguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhenAiOiI3ODg3MzIzNzIwNzguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJlbWFpbCI6ImFudG9uaW8uc2Fuc29AZ21haWwuY29tIiwidG9rZW5faGFzaCI6IkwySTc3Z2lCTGswUlNzMHpRMVN2Q0EiLCJhdF9oYXNoIjoiTDJJNzdnaUJMazBSU3MwelExU3ZDQSIsImlhdCI6MTM2NjcyNjMxNywiZXhwIjoxMzY2NzMwMjE3fQ.XWYi5Zj1YWAMGIml_ftoAwmvW1Y7oeybLCpzQrJVuWJpS8L8Vd2TL-RTIOEVG03VA7e0_-_frNuw7MxUgVEgh8G-Nnbk_baJ6k_3w5c1SKFamFiHHDoKLFhrt1Y8JKSuGwE02V-px4Cn0dRAQAc1IN5CU6wqCrYK0p-fv_fvy28";

	@Test
	public void testJWT() throws Exception {
		assertEquals(JWT, JWTUtils.parseJWT(JWT).getRawString());
	}

	@Test
	public void testHeader() throws Exception {
		Header header = JWTUtils.parseJWT(JWT).getHeader();
		assertEquals("{\"alg\":\"RS256\"}", JWTUtils.toJsonString(header));
		assertEquals("RS256", header.getAlgorithm());
	}

	@Test
	public void testClaimsSet() throws Exception {
		ClaimsSet claimsSet = JWTUtils.parseJWT(JWT).getClaimsSet();
		assertEquals(
				"{\"aud\":\"788732372078.apps.googleusercontent.com\",\"iss\":\"accounts.google.com\",\"sub\":\"106422453082479998429\",\"exp\":1366730217,\"iat\":1366726317}",
				JWTUtils.toJsonString(claimsSet));
		assertEquals("788732372078.apps.googleusercontent.com",
				claimsSet.getAudience());
		assertEquals("accounts.google.com", claimsSet.getIssuer());
		assertEquals("106422453082479998429", claimsSet.getSubject());
		assertEquals(1366730217, claimsSet.getExpirationTime());
		assertEquals(1366726317, claimsSet.getIssuedAt());
	}

}
