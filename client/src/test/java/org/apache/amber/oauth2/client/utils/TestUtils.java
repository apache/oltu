/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
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

package org.apache.amber.oauth2.client.utils;

import static org.easymock.EasyMock.expect;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.common.error.OAuthError;


/**
 *
 *
 *
 */
public final class TestUtils {
    private TestUtils() {
    }

    public static final String VALID_JSON_RESPONSE
        = "{\"expires_in\":3600,\"access_token\":\"b52d434791fd52316232b6cf2d3\",\"scope\":\"read\","
        + "\"refresh_token\":\"test_refresh_token\"}";

    public static final Long EXPIRES_IN = 3600l;
    public static final String ACCESS_TOKEN = "b52d434791fd52316232b6cf2d3";
    public static final String SCOPE = "read";
    public static final String REFRESH_TOKEN = "test_refresh_token";

    public static final String ERROR_JSON_BODY
        = "{\"error_uri\":\"null\",\"error\":\"invalid_request\",\"state\":\"null\",\"error_description\":"
        + "\"Invalid grant_type parameter value\"}";

    public static final String INVALID_JSON
        = "\"expires_in\":3600,\"access_token\":\"b52d434791fd52316232b6cf2d3\"}";

//    public static final String

    public static void expectNoErrorParameters(HttpServletRequest request) {
        expect(request.getParameter(OAuthError.OAUTH_ERROR))
            .andStubReturn(null);
        expect(request.getParameter(OAuthError.OAUTH_ERROR_DESCRIPTION)).andStubReturn(null);
        expect(request.getParameter(OAuthError.OAUTH_ERROR_URI)).andStubReturn(null);
    }
}
