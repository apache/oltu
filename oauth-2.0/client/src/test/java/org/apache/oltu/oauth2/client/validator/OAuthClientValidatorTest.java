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
package org.apache.oltu.oauth2.client.validator;

import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OAuthClientValidatorTest {

    private static final String OAUTH_ERROR_JSON = "{\"error\":\"invalid_client\"}";

    @Test
    public void shouldReturnExceptionWithSpecificResponseCode() throws OAuthProblemException {
        try {
            OAuthClientResponseFactory.createJSONTokenResponse(OAUTH_ERROR_JSON, OAuth.ContentType.JSON, 401);
            fail();
        } catch (OAuthProblemException e) {
            assertEquals(401, e.getResponseStatus());
        }
    }
}