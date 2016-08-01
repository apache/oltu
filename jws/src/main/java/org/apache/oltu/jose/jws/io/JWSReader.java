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

import org.apache.oltu.commons.encodedtoken.TokenReader;
import org.apache.oltu.jose.jws.JWS;

/**
 * A {@link JWS} reader.
 *
 * TODO understand if JWT can be reused to avoid code duplication!
 */
public final class JWSReader extends TokenReader<JWS> {

    @Override
    protected JWS build(String rawString, String decodedHeader, String decodedBody, String encodedSignature) {
        final JWS.Builder jwsBuilder = new JWS.Builder(rawString);

        new JWSHeaderParser(jwsBuilder).read(decodedHeader);

        return jwsBuilder
               .setPayload(decodedBody)
               .setSignature(encodedSignature)
               .build();
    }

}
