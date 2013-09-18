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
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oltu.jose.jws.JWS;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
        JSONObject headerObject;

        try {
            headerObject = new JSONObject(decodedHeader);
        } catch (JSONException e) {
            throw new IllegalArgumentException(format("BASE64 string '%s' (decoded to '%s') is not a valid JSON object representation",
                                                      header, decodedHeader));
        }

        for (@SuppressWarnings("unchecked") // it is known that JSON keys are strings
        Iterator<String> keys = headerObject.keys(); keys.hasNext();) {
            String key = keys.next();

            if (ALGORITHM.equals(key)) {
                jwsBuilder.setAlgorithm(getString(headerObject, ALGORITHM));
            } else if (JWK_SET_URL.equals(key)) {
                jwsBuilder.setJwkSetUrl(getString(headerObject, ALGORITHM));
            } else if (JSON_WEB_KEY.equals(key)) {
                jwsBuilder.setJsonWebKey(getString(headerObject, JSON_WEB_KEY));
            }  else if (X509_URL.equals(key)) {
                jwsBuilder.setX509url(getString(headerObject, X509_URL));
            } else if (X509_CERTIFICATE_THUMBPRINT.equals(key)) {
                jwsBuilder.setX509CertificateThumbprint(getString(headerObject, X509_CERTIFICATE_THUMBPRINT));
            } else if (X509_CERTIFICATE_CHAIN.equals(key)) {
                jwsBuilder.setX509CertificateChain(getString(headerObject, X509_CERTIFICATE_CHAIN));
            } else if (KEY_ID.equals(key)) {
                jwsBuilder.setKeyId(getString(headerObject, KEY_ID));
            } else if (TYPE.equals(key)) {
                jwsBuilder.setType(getString(headerObject, TYPE));
            } else if (CONTENT_TYPE.equals(key)) {
                jwsBuilder.setContentType(getString(headerObject, CONTENT_TYPE));
            } else if (CRITICAL.equals(key)) {
                jwsBuilder.setCritical(getStringArray(headerObject, CRITICAL));
            } else {
                jwsBuilder.setCustomField(key, getString(headerObject, key));
            }
        }

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

    private static String getString(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    private static String[] getStringArray(JSONObject object, String key) {
        try {
            JSONArray array = object.getJSONArray(key);
            String[] result = new String[array.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = array.getString(i);
            }
            return result;
        } catch (JSONException e) {
            return new String[0];
        }
    }

}
