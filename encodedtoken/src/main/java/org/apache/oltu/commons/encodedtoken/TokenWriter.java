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
package org.apache.oltu.commons.encodedtoken;

public abstract class TokenWriter<T> extends TokenDecoder {

    public final String write(T token) {
        if (token == null) {
            throw new IllegalArgumentException("Impossible to build a Token from a null JWS representation.");
        }

        String header = writeHeader(token);
        String encodedHeader = base64Encode(header);

        String body = writeBody(token);
        String encodedBody = base64Encode(body);

        String signature = writeSignature(token);

        return new StringBuilder()
                   .append(encodedHeader)
                   .append('.')
                   .append(encodedBody)
                   .append('.')
                   .append(signature)
                   .toString();
    }

    protected abstract String writeHeader(T token);

    protected abstract String writeBody(T token);

    protected abstract String writeSignature(T token);

}
