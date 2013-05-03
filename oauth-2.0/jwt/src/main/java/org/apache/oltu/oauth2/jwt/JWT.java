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

/**
 * This class contains constants used in the JWT implementation.
 * 
 */
public class JWT {

    /**
     * This inner class contains reserved claims defined in the JWT
     * specification
     */
    public static final class ReservedClaim {
        public static final String ISSUER = "iss";
        public static final String SUBJECT = "sub";
        public static final String AUDIENCE = "aud";
        public static final String EXPIRATION_TIME = "exp";
        public static final String NOT_BEFORE = "nbf";
        public static final String ISSUED_AT = "iat";
        public static final String JWT_ID = "jti";
        public static final String TYPE = "typ";
    }

    /**
     * This inner class contains JWT header parameters
     * 
     */
    public static final class HeaderParam {
        public static final String TYPE = "typ";
        public static final String ALGORITHM = "alg";
        public static final String CONTENT_TYPE = "cty";
    }

    /**
     * This inner class contains defined values for JWT header parameters
     */
    public static final class HeaderParamValue {
        public static final String TYPE_JWT = "JWT";
        public static final String ALG_HS256 = "HS256";
        public static final String ALG_NONE = "none";
    }

}
