/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
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
package org.apache.oltu.oauth2.common.token;

/**
 *
 */
public class BasicOAuthToken implements OAuthToken {
    protected String accessToken;
    protected String tokenType;
    protected Long expiresIn;
    protected String refreshToken;
    protected String scope;

    public BasicOAuthToken() {
    }

    public BasicOAuthToken(String accessToken, String tokenType, Long expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public BasicOAuthToken(String accessToken, String tokenType) {
        this(accessToken, tokenType, null, null, null);
    }

    public BasicOAuthToken(String accessToken, String tokenType, Long expiresIn) {
        this(accessToken, tokenType, expiresIn, null, null);
    }

    public BasicOAuthToken(String accessToken, String tokenType, Long expiresIn, String scope) {
        this(accessToken, tokenType, expiresIn, null, scope);
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }
}
