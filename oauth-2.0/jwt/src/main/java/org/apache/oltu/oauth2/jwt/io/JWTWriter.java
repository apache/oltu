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

import java.util.Map.Entry;

import org.apache.oltu.oauth2.jwt.ClaimsSet;
import org.apache.oltu.oauth2.jwt.Header;
import org.apache.oltu.oauth2.jwt.JWT;
import org.apache.oltu.oauth2.jwt.JWTEntity;
import org.json.JSONStringer;

/**
 * A {@link JWT} writer.
 */
public final class JWTWriter extends AbstractJWTIO {

    public String write(JWT jwt) {
        if (jwt == null) {
            throw new IllegalArgumentException("Impossible to build a Token from a null JWT representation.");
        }

        String header = write(jwt.getHeader());
        String encodedHeader = encodeJson(header);

        String claimsSet = write(jwt.getClaimsSet());
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
    public String write(Header header) {
        if (header == null) {
            throw new IllegalArgumentException("Null JWT Header cannot be serialized to JSON representation.");
        }

        JSONStringer jsonWriter = new JSONStringer();
        jsonWriter.object();

        setObject(jsonWriter, ALGORITHM, header.getAlgorithm());
        setObject(jsonWriter, CONTENT_TYPE, header.getContentType());
        setObject(jsonWriter, TYPE, header.getType());

        return toJsonString(header, jsonWriter);
    }

    /**
     * Serializes the input JWT Claims Set to its correct JSON representation.
     *
     * @param claimsSet the JWT Claims Set has to be serialized.
     * @return the JSON string that represents the JWT Claims Set.
     */
    public String write(ClaimsSet claimsSet) {
        if (claimsSet == null) {
            throw new IllegalArgumentException("Null JWT Claims Set cannot be serialized to JSON representation.");
        }

        JSONStringer jsonWriter = new JSONStringer();
        jsonWriter.object();

        setObject(jsonWriter, AUDIENCE, claimsSet.getAudience());
        setObject(jsonWriter, ISSUER, claimsSet.getIssuer());
        setObject(jsonWriter, JWT_ID, claimsSet.getJwdId());
        setObject(jsonWriter, NOT_BEFORE, claimsSet.getNotBefore());
        setObject(jsonWriter, SUBJECT, claimsSet.getSubject());
        setObject(jsonWriter, TYPE, claimsSet.getType());
        setLong(jsonWriter, EXPIRATION_TIME, claimsSet.getExpirationTime());
        setLong(jsonWriter, ISSUED_AT, claimsSet.getIssuedAt());

        return toJsonString(claimsSet, jsonWriter);
    }

    private static String toJsonString(JWTEntity entity, JSONStringer jsonWriter) {
        for (Entry<String, Object> customField : entity.getCustomFields()) {
            setObject(jsonWriter, customField.getKey(), customField.getValue());
        }

        return jsonWriter.endObject().toString();
    }

    private String encodeJson(String jsonString) {
        return new String(base64.encode(jsonString.getBytes(UTF_8)), UTF_8);
    }

    private static void setLong(JSONStringer jsonWriter, String key, long value) {
        if (value != 0) {
            jsonWriter.key(key).value(value);
        }
    }

    private static void setObject(JSONStringer jsonWriter, String key, Object value) {
        if (value != null) {
            jsonWriter.key(key).value(value);
        }
    }

}
