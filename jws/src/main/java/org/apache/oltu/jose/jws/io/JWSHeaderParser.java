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

import org.apache.oltu.commons.json.CustomizableEntityReader;
import org.apache.oltu.jose.jws.JWS;

final class JWSHeaderParser extends CustomizableEntityReader<JWS, JWS.Builder> implements JWSConstants {

    public JWSHeaderParser(JWS.Builder builder) {
        super(builder);
    }

    @Override
    protected <T> boolean handleProperty(String key, T value) {
        boolean handled = true;

        if (ALGORITHM.equals(key)) {
            getBuilder().setAlgorithm(String.valueOf(value));
        } else if (JWK_SET_URL.equals(key)) {
            getBuilder().setJwkSetUrl(String.valueOf(value));
        } else if (JSON_WEB_KEY.equals(key)) {
            getBuilder().setJsonWebKey(String.valueOf(value));
        }  else if (X509_URL.equals(key)) {
            getBuilder().setX509url(String.valueOf(value));
        } else if (X509_CERTIFICATE_THUMBPRINT.equals(key)) {
            getBuilder().setX509CertificateThumbprint(String.valueOf(value));
        } else if (X509_CERTIFICATE_CHAIN.equals(key)) {
            getBuilder().setX509CertificateChain(String.valueOf(value));
        } else if (KEY_ID.equals(key)) {
            getBuilder().setKeyId(String.valueOf(value));
        } else if (TYPE.equals(key)) {
            getBuilder().setType(String.valueOf(value));
        } else if (CONTENT_TYPE.equals(key)) {
            getBuilder().setContentType(String.valueOf(value));
        } else if (CRITICAL.equals(key)) {
            Object[] criticalValues = (Object[]) value;
            String[] critical = new String[criticalValues.length];
            for (int i = 0; i < critical.length; i++) {
                critical[i] = String.valueOf(criticalValues[i]);
            }
            getBuilder().setCritical(critical);
        } else {
            handled = false;
        }

        return handled;
    }


}
