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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class JWTClaimsSetParser extends CustomizableEntityReader<JWT, JWT.Builder> implements JWTConstants {

    public JWTClaimsSetParser(JWT.Builder builder) {
        super(builder);
    }

    @Override
    protected <T> boolean handleProperty(String key, T value) {
        if (AUDIENCE.equals(key)) {
            handleAudience(value);
        } else if (EXPIRATION_TIME.equals(key)) {
            getBuilder().setClaimsSetExpirationTime(((Number) value).longValue());
        } else if (ISSUED_AT.equals(key)) {
            getBuilder().setClaimsSetIssuedAt(((Number) value).longValue());
        } else if (ISSUER.equals(key)) {
            getBuilder().setClaimsSetIssuer(String.valueOf(value));
        } else if (JWT_ID.equals(key)) {
            getBuilder().setClaimsSetJwdId(String.valueOf(value));
        } else if (NOT_BEFORE.equals(key)) {
            getBuilder().setClaimsSetNotBefore(String.valueOf(value));
        } else if (SUBJECT.equals(key)) {
            getBuilder().setClaimsSetSubject(String.valueOf(value));
        } else if (TYPE.equals(key)) {
            getBuilder().setClaimsSetType(String.valueOf(value));
        } else {
            getBuilder().setClaimsSetCustomField(key, value);
        }

        return true;
    }

    private <T> void handleAudience(T value) {
        if (value instanceof Collection) {
            getBuilder().setClaimsSetAudiences(collectionToStringList((Collection<?>) value));
        } else if (value instanceof Object[]) {
            getBuilder().setClaimsSetAudiences(arrayToStringList((Object[]) value));
        } else {
            getBuilder().setClaimsSetAudience(String.valueOf(value));
        }
    }

    private List<String> collectionToStringList(Collection<?> values) {
        List<String> l = new ArrayList<String>();
        for (Object v : values) {
            l.add(String.valueOf(v));
        }
        return l;
    }

    private List<String> arrayToStringList(Object[] values) {
        List<String> l = new ArrayList<String>();
        for (Object v : values) {
            l.add(String.valueOf(v));
        }
        return l;
    }

}
