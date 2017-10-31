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
package org.apache.oltu.commons.encodedtoken;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public abstract class TokenDecoder {

    /**
     * The {@code UTF-8} charset reference.
     */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * Empty Line separator for rfc 2045 section 6.8
     * {@see org.apache.commons.codec.binary.Base64}
     */
    private static final byte[] LINE_SEPARATOR = {};

    public static final String base64Decode(String base64encoded) {
        return new String(new Base64(-1, LINE_SEPARATOR, true).decode(base64encoded), UTF_8);
    }
    
    public static final byte[] base64DecodeToByte(String base64encoded) {
        return new Base64(-1, LINE_SEPARATOR, true).decode(base64encoded);
    }

    public static final String base64Encode(String input) {
        return new String(new Base64(-1, LINE_SEPARATOR, true).encode(input.getBytes(UTF_8)), UTF_8);
    }
    
    public static final String base64Encode(byte [] input) {
        return new String(new Base64(-1, LINE_SEPARATOR, true).encode(input));
    }
}
