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

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

abstract class AbstractJWSIO {

    /**
     * The {@code UTF-8} charset reference.
     */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    // header defined in the JWT specification

    /**
     * The {@code alg} JWT Header key.
     */
    public static final String ALGORITHM = "alg";

    /**
     * The {@code jku} JWT Header key.
     */
    public static final String JWK_SET_URL = "jku";

    /**
     * The {@code jwk} JWT Header key.
     */
    public static final String JSON_WEB_KEY = "jwk";

    /**
     * The {@code x5u} JWT Header key.
     */
    public static final String X509_URL = "x5u";

    /**
     * The {@code x5t} JWT Header key.
     */
    public static final String X509_CERTIFICATE_THUMBPRINT = "x5t";

    /**
     * The {@code x5c} JWT Header key.
     */
    public static final String X509_CERTIFICATE_CHAIN = "x5c";

    /**
     * The {@code kid} JWT Header key.
     */
    public static final String KEY_ID = "kid";

    /**
     * The {@code typ} JWT Header key.
     */
    protected static final String TYPE = "typ";

    /**
     * The {@code cty} JWT Header key.
     */
    public static final String CONTENT_TYPE = "cty";

    /**
     * The {@code crit} JWT Header key.
     */
    public static final String CRITICAL = "crit";

    /**
     * The BASE64 encoder/decoder.
     */
    private final Base64 base64 = new Base64(true);

    protected final String base64Decode(String base64encoded) {
        return new String(base64.decode(base64encoded), UTF_8);
    }

    protected final String base64Encode(String input) {
        return new String(base64.encode(input.getBytes(UTF_8)), UTF_8);
    }

}
