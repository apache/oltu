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

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oltu.oauth2.jwt.JWT;
import org.json.JSONTokener;

/**
 * A {@link JWT} reader.
 */
public final class JWTReader extends AbstractJWTIO {

    /**
     * The Base64 JSON string default separator.
     */
    private static final Pattern BASE64_JWT_PATTERN = Pattern.compile("([a-zA-Z0-9/+=]+)\\.([a-zA-Z0-9/+=]+)\\.(.+)");

    /**
     * Parses a Base64 encoded JSON Web Token.
     *
     * @param base64jsonString a Base64 encoded JSON Web Token.
     * @return a JWT instance.
     */
    public JWT read(String base64jsonString) {
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

        Matcher matcher = BASE64_JWT_PATTERN.matcher(buffer.toString());
        if (!matcher.matches()) {
            throw new IllegalArgumentException(base64jsonString
                                               + "is not a valid JSON Web Token, it does not match with the pattern: "
                                               + BASE64_JWT_PATTERN.pattern());
        }

        JWT.Builder jwtBuilder = new JWT.Builder(base64jsonString);

        // Header
        String header = matcher.group(1);
        String decodedHeader = decodeJSON(header);
        new JWTHeaderParser(jwtBuilder).parse(decodedHeader);

        // ClaimsSet
        String claimsSet = matcher.group(2);
        String decodedClaimsSet = decodeJSON(claimsSet);
        new JWTClaimsSetParser(jwtBuilder).parse(decodedClaimsSet);

        // Signature
        String signature = matcher.group(3);

        return jwtBuilder.setSignature(signature).build();
    }

    private String decodeJSON(String base64jsonString) {
        return new String(base64.decode(base64jsonString), UTF_8);
    }

    private static abstract class JSONParser {

        private final JWT.Builder jwtBuilder;

        public JSONParser(JWT.Builder jwtBuilder) {
            this.jwtBuilder = jwtBuilder;
        }

        protected final JWT.Builder getJwtBuilder() {
            return jwtBuilder;
        }

        public final void parse(String decodedHeader) {
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
                    on(key, value);
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

        protected abstract void on(String key, Object value);

    }

    private static final class JWTHeaderParser extends JSONParser {

        public JWTHeaderParser(JWT.Builder jwtBuilder) {
            super(jwtBuilder);
        }

        @Override
        protected void on(String key, Object value) {
            if (ALGORITHM.equals(key)) {
                getJwtBuilder().setHeaderAlgorithm(String.valueOf(value));
            } else if (TYPE.equals(key)) {
                getJwtBuilder().setHeaderType(String.valueOf(value));
            } else if (CONTENT_TYPE.equals(key)) {
                getJwtBuilder().setHeaderContentType(String.valueOf(value));
            } else {
                getJwtBuilder().setHeaderCustomField(key, String.valueOf(value));
            }
        }

    }

    private static final class JWTClaimsSetParser extends JSONParser {

        public JWTClaimsSetParser(JWT.Builder jwtBuilder) {
            super(jwtBuilder);
        }

        @Override
        protected void on(String key, Object value) {
            if (AUDIENCE.equals(key)) {
                getJwtBuilder().setClaimsSetAudience(String.valueOf(value));
            } else if (EXPIRATION_TIME.equals(key)) {
                getJwtBuilder().setClaimsSetExpirationTime(((Integer) value).longValue());
            } else if (ISSUED_AT.equals(key)) {
                getJwtBuilder().setClaimsSetIssuedAt(((Integer) value).longValue());
            } else if (ISSUER.equals(key)) {
                getJwtBuilder().setClaimsSetIssuer(String.valueOf(value));
            } else if (JWT_ID.equals(key)) {
                getJwtBuilder().setClaimsSetJwdId(String.valueOf(value));
            } else if (NOT_BEFORE.equals(key)) {
                getJwtBuilder().setClaimsSetNotBefore(String.valueOf(value));
            } else if (SUBJECT.equals(key)) {
                getJwtBuilder().setClaimsSetSubject(String.valueOf(value));
            } else if (TYPE.equals(key)) {
                getJwtBuilder().setClaimsSetType(String.valueOf(value));
            } else {
                getJwtBuilder().setClaimsSetCustomField(key, String.valueOf(value));
            }
        }

    }

}
