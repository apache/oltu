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

import org.apache.oltu.commons.json.CustomizableEntityReader;
import org.apache.oltu.oauth2.jwt.JWT;

final class JWTHeaderParser extends CustomizableEntityReader<JWT, JWT.Builder> implements JWTConstants {

    public JWTHeaderParser(JWT.Builder builder) {
        super( builder );
    }

    @Override
    protected <T> boolean handleProperty( String key, T value ) {
        if (ALGORITHM.equals(key)) {
            getBuilder().setHeaderAlgorithm(String.valueOf(value));
        } else if (TYPE.equals(key)) {
            getBuilder().setHeaderType(String.valueOf(value));
        } else if (CONTENT_TYPE.equals(key)) {
            getBuilder().setHeaderContentType(String.valueOf(value));
        } else {
            getBuilder().setHeaderCustomField(key, value);
        }

        return true;
    }

}
