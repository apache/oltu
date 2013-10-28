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

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oltu.jose.jws.JWS;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * A {@link JWS} reader.
 *
 * TODO understand if JWT can be reused to avoid code duplication!
 */
public final class JWSReader extends AbstractJWSIO {

    /**
     * The Base64 JSON string default separator.
     */
    private final Pattern base64JWTPattern = Pattern.compile("([a-zA-Z0-9/+=]+)\\.([a-zA-Z0-9/+=]+)\\.(.+)");

    public JWS read(String base64jsonString) {
        if (base64jsonString == null || base64jsonString.isEmpty()) {
            throw new IllegalArgumentException("Impossible to obtain a JWT from a null or empty string");
        }

        // TODO improve multi-line tokens
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new StringReader(base64jsonString));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line.trim());
            }
        } catch (IOException e) {
            // it cannot happen
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // swallow it
            }
        }

        Matcher matcher = base64JWTPattern.matcher(buffer.toString());
        if (!matcher.matches()) {
            throw new IllegalArgumentException(base64jsonString
                                               + "is not a valid JSON Web Signature, it does not match with the pattern: "
                                               + base64JWTPattern.pattern());
        }

        JWS.Builder jwsBuilder = new JWS.Builder();

        // HEADER

        String header = matcher.group(1);
        String decodedHeader = base64Decode(header);
        parseHeader(jwsBuilder, decodedHeader);

        // PAYLOAD
        String payload = matcher.group(2);
        String decodedPayload = base64Decode(payload);

        // SIGNATURE
        String signature = matcher.group(3);
        String decodedSignature = base64Decode(signature);

        return jwsBuilder.setPayload(decodedPayload)
                         .setSignature(decodedSignature)
                         .build();
    }

    /**
     * This method has been extracted from {@link JSONObject#JSONObject(JSONTokener)}
     *
     * @param jswBuilder the JWS builder reference
     * @param decodedHeader the BASE64 decoded JSON string
     */
    private static void parseHeader(JWS.Builder jwsBuilder, String decodedHeader) {
        final JSONTokener x = new JSONTokener(decodedHeader);
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, a JSON object text must begin with '{'",
                                                      decodedHeader));
        }
        for (;;) {
            c = x.nextClean();
            switch (c) {
            case 0:
                throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, a JSON object text must end with '}'",
                                                          decodedHeader));
            case '}':
                return;
            default:
                x.back();
                key = x.nextValue().toString();
            }

            /*
             * The key is followed by ':'. We will also tolerate '=' or '=>'.
             */
            c = x.nextClean();
            if (c == '=') {
                if (x.next() != '>') {
                    x.back();
                }
            } else if (c != ':') {
                throw new IllegalArgumentException(format("String '%s' is not a valid JSON object representation, expected a ':' after the key '%s'",
                                                          decodedHeader, key));
            }
            Object value = x.nextValue();

            if (value != null) {
                if (ALGORITHM.equals(key)) {
                    jwsBuilder.setAlgorithm(String.valueOf(value));
                } else if (JWK_SET_URL.equals(key)) {
                    jwsBuilder.setJwkSetUrl(String.valueOf(value));
                } else if (JSON_WEB_KEY.equals(key)) {
                    jwsBuilder.setJsonWebKey(String.valueOf(value));
                }  else if (X509_URL.equals(key)) {
                    jwsBuilder.setX509url(String.valueOf(value));
                } else if (X509_CERTIFICATE_THUMBPRINT.equals(key)) {
                    jwsBuilder.setX509CertificateThumbprint(String.valueOf(value));
                } else if (X509_CERTIFICATE_CHAIN.equals(key)) {
                    jwsBuilder.setX509CertificateChain(String.valueOf(value));
                } else if (KEY_ID.equals(key)) {
                    jwsBuilder.setKeyId(String.valueOf(value));
                } else if (TYPE.equals(key)) {
                    jwsBuilder.setType(String.valueOf(value));
                } else if (CONTENT_TYPE.equals(key)) {
                    jwsBuilder.setContentType(String.valueOf(value));
                } else if (CRITICAL.equals(key)) {
                    JSONArray array = (JSONArray) value;
                    String[] critical = new String[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        critical[i] = array.getString(i);
                    }
                    jwsBuilder.setCritical(critical);
                } else {
                    jwsBuilder.setCustomField(key, String.valueOf(value));
                }
            }

            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */
            switch (x.nextClean()) {
            case ';':
            case ',':
                if (x.nextClean() == '}') {
                    return;
                }
                x.back();
                break;
            case '}':
                return;
            default:
                throw new IllegalArgumentException("Expected a ',' or '}'");
            }
        }
    }

}
