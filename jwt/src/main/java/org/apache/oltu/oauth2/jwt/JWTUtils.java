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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;
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
    @SuppressWarnings("unchecked") // it is known that JSON keys are strings
    public static JWT parseJWT(String base64jsonString) {
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

    public static String toBase64JsonString(JWT jwt) {
        if (jwt == null) {
            throw new IllegalArgumentException("Impossible to build a Token from a null JWT representation.");
        }

        String header = toJsonString(jwt.getHeader());
        String encodedHeader = encodeJson(header);

        String claimsSet = toJsonString(jwt.getClaimsSet());
        String encodedClaimsSet = encodeJson(claimsSet);

        String signature = jwt.getSignature();
        if (signature == null) {
            signature = "";
        }

        return new StringBuilder()
               .append(encodedHeader)
               .append('.')
               .append('\r')
               .append('\n')
               .append(encodedClaimsSet)
               .append('.')
               .append('\r')
               .append('\n')
               .append(signature)
               .toString();
    }

    /**
     * Serializes the input JWT Header to its correct JSON representation.
     *
     * @param header the JWT Header has to be serialized.
     * @return the JSON string that represents the JWT Header.
     */
    private static String toJsonString(Header header) {
        if (header == null) {
            throw new IllegalArgumentException("Null JWT Header cannot be serialized to JSON representation.");
        }

        JSONObject object = new JSONObject();
        setString(object, ALGORITHM, header.getAlgorithm());
        setString(object, CONTENT_TYPE, header.getContentType());
        setString(object, TYPE, header.getType());
        return toJsonString(header, object);
    }

    /**
     * Serializes the input JWT Claims Set to its correct JSON representation.
     *
     * @param claimsSet the JWT Claims Set has to be serialized.
     * @return the JSON string that represents the JWT Claims Set.
     */
    private static String toJsonString(ClaimsSet claimsSet) {
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
        return toJsonString(claimsSet, object);
    }

    private static String toJsonString(JWTEntity entity, JSONObject object) {
        for (Entry<String, Object> customField : entity.getCustomFields()) {
            setObject(object, customField.getKey(), customField.getValue());
        }

        StringWriter writer = new StringWriter();
        try {
            object.write(writer);
        } catch (JSONException e) {
            // swallow it, it should be safe enough to write to a StringWriter
        }
        return writer.toString();
    }

    private static String encodeJson(String jsonString) {
        return new String(new Base64(true).encode(jsonString.getBytes(UTF_8)), UTF_8);
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

    private static void setObject(JSONObject object, String key, Object value) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                // swallow it, null values are already guarded
            }
        }
    }

}
