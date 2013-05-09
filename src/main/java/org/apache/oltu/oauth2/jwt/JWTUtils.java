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

import static java.lang.String.format;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * This class contains utility methods required for the JWT building and
 * processing.
 */
public class JWTUtils {

    /**
     * The {@code UTF-8} charset reference.
     */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * The Base64 JSON string default separator.
     */
    private static final Pattern BASE64_JWT_PATTERN = Pattern.compile("([a-zA-Z0-9/+=]+)\\.([a-zA-Z0-9/+=]+)\\.(.+)");

    // header defined in the JWT specification

    /**
     * The {@code typ} JWT Header key.
     */
    private static final String TYPE = "typ";

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
    private static final String ISSUER = "iss";

    /**
     * The {@code sub} JWT Claims Set key.
     */
    private static final String SUBJECT = "sub";

    /**
     * The {@code aud} JWT Claims Set key.
     */
    private static final String AUDIENCE = "aud";

    /**
     * The {@code exp} JWT Claims Set key.
     */
    private static final String EXPIRATION_TIME = "exp";

    /**
     * The {@code nbf} JWT Claims Set key.
     */
    private static final String NOT_BEFORE = "nbf";

    /**
     * The {@code iat} JWT Claims Set key.
     */
    private static final String ISSUED_AT = "iat";

    /**
     * The {@code jti} JWT Claims Set key.
     */
    private static final String JWT_ID = "jti";

    /**
     * Hidden constructor, this class must not be instantiated.
     */
    private JWTUtils() {
        // do nothing
    }

    // parse

    /**
     * Parses a Base64 encoded JSON Web Token.
     *
     * @param base64jsonString a Base64 encoded JSON Web Token.
     * @return a JWT instance.
     */
    public static JWT parseJWT(String base64jsonString) {
        if (base64jsonString == null || base64jsonString.isEmpty()) {
            throw new IllegalArgumentException("Impossible to obtain a JWT from a null or empty string");
        }

        Matcher matcher = BASE64_JWT_PATTERN.matcher(base64jsonString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(base64jsonString
                                               + "is not avalid JSON Web Token, it does not match with the pattern: "
                                               + BASE64_JWT_PATTERN.pattern());
        }

        JSONObject headerObject = decodeJSON(matcher.group(1));
        JSONObject claimsSetObject = decodeJSON(matcher.group(2));
        String signature = matcher.group(3);

        return new JWT.Builder()
                      .setHeaderAlgorithm(getString(headerObject, ALGORITHM))
                      .setHeaderContentType(getString(headerObject, CONTENT_TYPE))
                      .setHeaderType(getString(headerObject, CONTENT_TYPE))
                      .setClaimsSetAudience(getString(claimsSetObject, AUDIENCE))
                      .setClaimsSetExpirationTime(getLong(claimsSetObject, EXPIRATION_TIME))
                      .setClaimsSetIssuedAt(getLong(claimsSetObject, ISSUED_AT))
                      .setClaimsSetIssuer(getString(claimsSetObject, ISSUER))
                      .setClaimsSetJwdId(getString(claimsSetObject, JWT_ID))
                      .setClaimsSetNotBefore(getString(claimsSetObject, NOT_BEFORE))
                      .setClaimsSetSubject(getString(claimsSetObject, SUBJECT))
                      .setClaimsSetType(getString(claimsSetObject, TYPE))
                      .setSignature(signature)
                      .build();
    }

    private static JSONObject decodeJSON(String base64jsonString) {
        String decodedJsonString = new String(new Base64(true).decode(base64jsonString), UTF_8);

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

    // serialization

    /**
     * Serializes the input JWT Header to its correct JSON representation.
     *
     * @param header the JWT Header has to be serialized.
     * @return the JSON string that represents the JWT Header.
     */
    public static String toJson(Header header) {
        if (header == null) {
            throw new IllegalArgumentException("Null JWT Header cannot be serialized to JSON representation.");
        }

        JSONObject object = new JSONObject();
        setString(object, ALGORITHM, header.getAlgorithm());
        setString(object, CONTENT_TYPE, header.getContentType());
        setString(object, TYPE, header.getType());
        return toJson(object);
    }

    /**
     * Serializes the input JWT Claims Set to its correct JSON representation.
     *
     * @param claimsSet the JWT Claims Set has to be serialized.
     * @return the JSON string that represents the JWT Claims Set.
     */
    public static String toJson(ClaimsSet claimsSet) {
        if (claimsSet == null) {
            throw new IllegalArgumentException("Null JWT Claims Set cannot be serialized to JSON representation.");
        }

        JSONObject object = new JSONObject();
        setString(object, AUDIENCE, claimsSet.getAudience());
        setString(object, ISSUER, claimsSet.getIssuer());
        setString(object, JWT_ID, claimsSet.getJwdId());
        setString(object, NOT_BEFORE, claimsSet.getNotBefore());
        setString(object, SUBJECT, claimsSet.getSubject());
        setString(object, TYPE, claimsSet.getType());
        setLong(object, EXPIRATION_TIME, claimsSet.getExpirationTime());
        setLong(object, ISSUED_AT, claimsSet.getIssuedAt());
        return toJson(object);
    }

    private static String toJson(JSONObject object) {
        StringWriter writer = new StringWriter();
        try {
            object.write(writer);
        } catch (JSONException e) {
            // swallow it, it should be safe enough to write to a StringWriter
        }
        return writer.toString();
    }

    private static void setString(JSONObject object, String key, String value) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                // swallow it, null values are already guarded
            }
        }
    }

    private static void setLong(JSONObject object, String key, long value) {
        if (value != 0) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                // swallow it, null values are already guarded
            }
        }
    }

}
