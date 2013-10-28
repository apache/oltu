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

import java.util.Map.Entry;

import org.apache.oltu.jose.jws.Header;
import org.apache.oltu.jose.jws.JWS;
import org.json.JSONStringer;

public final class JWSWriter extends AbstractJWSIO {

    public String write(JWS jws) {
        if (jws == null) {
            throw new IllegalArgumentException("Impossible to build a Token from a null JWS representation.");
        }

        String header = write(jws.getHeader());
        String encodedHeader = base64Encode(header);

        String payload = jws.getPayload();
        String encodedPayload = base64Encode(payload);

        String signature = jws.getSignature();
        String encodedSignature = base64Encode(signature);

        return new StringBuilder()
                   .append(encodedHeader)
                   .append('.')
                   .append(encodedPayload)
                   .append('.')
                   .append(encodedSignature)
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

        setString(jsonWriter, ALGORITHM, header.getAlgorithm());
        setString(jsonWriter, JWK_SET_URL, header.getJwkSetUrl());
        setString(jsonWriter, JSON_WEB_KEY, header.getJsonWebKey());
        setString(jsonWriter, X509_URL, header.getX509url());
        setString(jsonWriter, X509_CERTIFICATE_THUMBPRINT, header.getX509CertificateThumbprint());
        setString(jsonWriter, X509_CERTIFICATE_CHAIN, header.getX509CertificateChain());
        setString(jsonWriter, KEY_ID, header.getKeyId());
        setString(jsonWriter, TYPE, header.getType());
        setString(jsonWriter, CONTENT_TYPE, header.getContentType());
        setStringArray(jsonWriter, CRITICAL, header.getCritical());

        for (Entry<String, Object> customField : header.getCustomFields()) {
            setObject(jsonWriter, customField.getKey(), customField.getValue());
        }

        return jsonWriter.endObject().toString();
    }

    private static void setString(JSONStringer jsonWriter, String key, String value) {
        if (value != null) {
            jsonWriter.key(key).value(value);
        }
    }

    private static void setStringArray(JSONStringer jsonWriter, String key, String[] value) {
        if (value != null) {
            jsonWriter.key(key).array();

            for (Object item : value) {
                if (item != null) {
                    jsonWriter.value(item);
                }
            }

            jsonWriter.endArray();
        }
    }

    private static void setObject(JSONStringer jsonWriter, String key, Object value) {
        if (value != null) {
            jsonWriter.key(key).value(value);
        }
    }

}
