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
package org.apache.oltu.jose.jwe.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oltu.commons.encodedtoken.TokenDecoder;
import org.apache.oltu.jose.jwe.JWE;

public class JWEReader extends TokenDecoder {
    /**
     * The Base64 JSON string default separator.
     */
    private final Pattern base64urlTokenPattern = Pattern.compile("([a-zA-Z0-9-_=]+)\\.([a-zA-Z0-9-_=]+)\\.([a-zA-Z0-9-_=]+)\\.([a-zA-Z0-9-_=]+)\\.([a-zA-Z0-9-_=]+)");
    
    /**
     * Read the base64url token string
     * @param base64String
     * @return
     */
    public JWE read(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            throw new IllegalArgumentException("Impossible to obtain a Token from a null or empty string");
        }

        // TODO improve multi-line tokens
        StringBuilder buffer = new StringBuilder();
        String line = null;
        try (BufferedReader reader = new BufferedReader(new StringReader(base64String))) {
            while ((line = reader.readLine()) != null) {
                buffer.append(line.trim());
            }
        } catch (IOException e) {
            // swallow it
        }

        Matcher matcher = base64urlTokenPattern.matcher(buffer.toString());
        if (!matcher.matches()) {
            throw new IllegalArgumentException(base64String
                                               + "is not a valid Token, it does not match with the pattern: "
                                               + base64urlTokenPattern.pattern());
        }

        // HEADER
        String header = matcher.group(1);
        String decodedHeader = base64Decode(header);

        // ENCRYPTED KEY
        String encryptedKey = matcher.group(2);

        StringBuilder contentEncryption = new StringBuilder();
        // IV
        contentEncryption.append(matcher.group(3)).append(".");
        
        // CIPHER TEXT
        contentEncryption.append(matcher.group(4)).append(".");
        
        // AUTHENTICATION TAG
        contentEncryption.append(matcher.group(5));

        return build(decodedHeader, encryptedKey, contentEncryption.toString());
    }
    
    protected JWE build(String decodedHeader, String encryptedKey, String contentEncryption) {
        final JWE.Builder jweBuilder = new JWE.Builder();

        new JWEHeaderParser(jweBuilder).read(decodedHeader);

        return jweBuilder
               .setEncryptedKey(encryptedKey)
               .setContentEncryption(contentEncryption)
               .build();
    }


}
