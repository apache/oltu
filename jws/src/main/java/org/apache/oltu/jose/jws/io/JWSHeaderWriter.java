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

import org.apache.oltu.commons.json.CustomizableEntityWriter;
import org.apache.oltu.jose.jws.Header;

public final class JWSHeaderWriter extends CustomizableEntityWriter<Header> implements JWSConstants {

    @Override
    protected void handleProperties(Header header) {
        set(ALGORITHM, header.getAlgorithm());
        set(JWK_SET_URL, header.getJwkSetUrl());
        set(JSON_WEB_KEY, header.getJsonWebKey());
        set(X509_URL, header.getX509url());
        set(X509_CERTIFICATE_THUMBPRINT, header.getX509CertificateThumbprint());
        set(X509_CERTIFICATE_CHAIN, header.getX509CertificateChain());
        set(KEY_ID, header.getKeyId());
        set(TYPE, header.getType());
        set(CONTENT_TYPE, header.getContentType());
        set(CRITICAL, header.getCritical());
    }

}
