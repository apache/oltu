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

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map.Entry;

import org.apache.oltu.jose.jws.Header;
import org.apache.oltu.jose.jws.JWS;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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

        JSONObject object = new JSONObject();
        setString(object, ALGORITHM, header.getAlgorithm());
        setString(object, JWK_SET_URL, header.getJwkSetUrl());
        setString(object, JSON_WEB_KEY, header.getJsonWebKey());
        setString(object, X509_URL, header.getX509url());
        setString(object, X509_CERTIFICATE_THUMBPRINT, header.getX509CertificateThumbprint());
        setString(object, X509_CERTIFICATE_CHAIN, header.getX509CertificateChain());
        setString(object, KEY_ID, header.getKeyId());
        setString(object, TYPE, header.getType());
        setString(object, CONTENT_TYPE, header.getContentType());
        setStringArray(object, CRITICAL, header.getCritical());

        for (Entry<String, Object> customField : header.getCustomFields()) {
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

    private static void setString(JSONObject object, String key, String value) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                // swallow it, null values are already guarded
            }
        }
    }

    private static void setStringArray(JSONObject object, String key, String[] value) {
        if (value != null) {
            JSONArray array = new JSONArray(Arrays.asList(value));
            try {
                object.put(key, array);
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
