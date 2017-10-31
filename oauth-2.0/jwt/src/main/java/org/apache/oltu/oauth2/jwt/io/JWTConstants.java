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


interface JWTConstants {

    // header defined in the JWT specification

    /**
     * The {@code typ} JWT Header key.
     */
    public static final String TYPE = "typ";

    /**
     * The {@code alg} JWT Header key.
     */
    public static final String ALGORITHM = "alg";

    /**
     * The {@code cty} JWT Header key.
     */
    public static final String CONTENT_TYPE = "cty";

    // reserved claims defined in the JWT specification

    /**
     * The {@code iss} JWT Claims Set key.
     */
    public static final String ISSUER = "iss";

    /**
     * The {@code sub} JWT Claims Set key.
     */
    public static final String SUBJECT = "sub";

    /**
     * The {@code aud} JWT Claims Set key.
     */
    public static final String AUDIENCE = "aud";

    /**
     * The {@code exp} JWT Claims Set key.
     */
    public static final String EXPIRATION_TIME = "exp";

    /**
     * The {@code nbf} JWT Claims Set key.
     */
    public static final String NOT_BEFORE = "nbf";

    /**
     * The {@code iat} JWT Claims Set key.
     */
    public static final String ISSUED_AT = "iat";

    /**
     * The {@code jti} JWT Claims Set key.
     */
    public static final String JWT_ID = "jti";

}
