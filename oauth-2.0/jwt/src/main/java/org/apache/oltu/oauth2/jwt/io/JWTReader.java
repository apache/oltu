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
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oltu.oauth2.jwt.JWT;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
    @SuppressWarnings("unchecked") // it is known that JSON keys are strings
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
                buffer.append(line);
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

        String header = matcher.group(1);
        JSONObject headerObject = decodeJSON(header);

        for (Iterator<String> keys = headerObject.keys(); keys.hasNext();) {
            String key = keys.next();

            if (ALGORITHM.equals(key)) {
                jwtBuilder.setHeaderAlgorithm(getString(headerObject, ALGORITHM));
            } else if (TYPE.equals(key)) {
                jwtBuilder.setHeaderType(getString(headerObject, TYPE));
            } else if (CONTENT_TYPE.equals(key)) {
                jwtBuilder.setHeaderContentType(getString(headerObject, CONTENT_TYPE));
            } else {
                jwtBuilder.setHeaderCustomField(key, getString(headerObject, key));
            }
        }

        String claimsSet = matcher.group(2);
        JSONObject claimsSetObject = decodeJSON(claimsSet);

        for (Iterator<String> keys = claimsSetObject.keys(); keys.hasNext();) {
            String key = keys.next();

            if (AUDIENCE.equals(key)) {
                jwtBuilder.setClaimsSetAudience(getString(claimsSetObject, AUDIENCE));
            } else if (EXPIRATION_TIME.equals(key)) {
                jwtBuilder.setClaimsSetExpirationTime(getLong(claimsSetObject, EXPIRATION_TIME));
            } else if (ISSUED_AT.equals(key)) {
                jwtBuilder.setClaimsSetIssuedAt(getLong(claimsSetObject, ISSUED_AT));
            } else if (ISSUER.equals(key)) {
                jwtBuilder.setClaimsSetIssuer(getString(claimsSetObject, ISSUER));
            } else if (JWT_ID.equals(key)) {
                jwtBuilder.setClaimsSetJwdId(getString(claimsSetObject, JWT_ID));
            } else if (NOT_BEFORE.equals(key)) {
                jwtBuilder.setClaimsSetNotBefore(getString(claimsSetObject, NOT_BEFORE));
            } else if (SUBJECT.equals(key)) {
                jwtBuilder.setClaimsSetSubject(getString(claimsSetObject, SUBJECT));
            } else if (TYPE.equals(key)) {
                jwtBuilder.setClaimsSetType(getString(claimsSetObject, TYPE));
            } else {
                jwtBuilder.setClaimsSetCustomField(key, getString(claimsSetObject, key));
            }
        }

        String signature = matcher.group(3);

        return jwtBuilder.setSignature(signature).build();
    }

    private JSONObject decodeJSON(String base64jsonString) {
        String decodedJsonString = new String(base64.decode(base64jsonString), UTF_8);

        try {
            return new JSONObject(decodedJsonString);
        } catch (JSONException e) {
            throw new IllegalArgumentException(format("BASE64 string '%s' (decoded to '%s') is not a valid JSON object representation",
                                                      base64jsonString, decodedJsonString));
        }
    }

    private static String getString(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    private static long getLong(JSONObject object, String key) {
        try {
            return object.getLong(key);
        } catch (JSONException e) {
            return 0;
        }
    }

}
