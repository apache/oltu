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

package org.apache.amber.oauth2.common.error;

/**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public abstract class OAuthError {

    //error response params
    public static final String OAUTH_ERROR = "error";
    public static final String OAUTH_ERROR_DESCRIPTION = "error_description";
    public static final String OAUTH_ERROR_URI = "error_uri";

    public static final class CodeResponse {
        public static final String INVALID_REQUEST = "invalid_request";
        public static final String INVALID_CLIENT = "invalid_client";
        public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
        public static final String REDIRECT_URI_MISMATCH = "redirect_uri_mismatch";
        public static final String ACCESS_DENIED = "access_denied";
        public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
        public static final String INVALID_SCOPE = "invalid_scope";
    }

    public static final class TokenResponse {
        public static final String INVALID_REQUEST = "invalid_request";
        public static final String INVALID_CLIENT = "invalid_client";
        public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
        public static final String INVALID_GRANT = "invalid_grant";
        public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
        public static final String INVALID_SCOPE = "invalid_scope";
    }

    public static final class ResourceResponse {
        public static final String INVALID_REQUEST = "invalid_request";
        public static final String EXPIRED_TOKEN = "expired_token";
        public static final String INSUFFICIENT_SCOPE = "insufficient_scope";
        public static final String INVALID_TOKEN = "invalid_token";
    }

}
