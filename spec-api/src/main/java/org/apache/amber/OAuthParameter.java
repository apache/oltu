/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.amber;

/**
 * The OAuth specification defines individual parameters required by a provider
 * at different stages of the authentication and authorisation process.
 * 
 * @version $Id$
 */
public enum OAuthParameter {

    /**
     * The callback parameter name.
     * 
     * Value: oauth_callback
     */
    CALLBACK("oauth_callback"),

    /**
     * The Consumer key parameter name.
     * 
     * Value: oauth_consumer_key
     */
    CONSUMER_KEY("oauth_consumer_key"),

    /**
     * The nonce parameter name.
     * 
     * Value: oauth_nonce
     */
    NONCE("oauth_nonce"),

    /**
     * The realm parameter name
     * 
     * Value: realm
     */
    REALM("realm"),

    /**
     * The signature parameter name
     * 
     * Value: oauth_signature
     */
    SIGNATURE("oauth_signature"),

    /**
     * The signature method parameter name
     * 
     * Value: oauth_signature_method
     */
    SIGNATURE_METHOD("oauth_signature_method"),

    /**
     * The timestamp parameter name
     * 
     * Value: oauth_timestamp
     */
    TIMESTAMP("oauth_timestamp"),

    /**
     * The token parameter name
     * 
     * Value: oauth_token
     * 
     * @see org.apache.amber.OAuthToken
     */
    TOKEN("oauth_token"),

    /**
     * The token secret parameter name
     * 
     * Value: oauth_token_secret
     * 
     * @see org.apache.amber.OAuthToken
     */
    TOKEN_SECRET("oauth_token_secret"),

    /**
     * The verifier parameter name
     * 
     * Value: oauth_verifier
     */
    VERIFIER("oauth_verifier"),

    /**
     * The version parameter name
     * 
     * Value: oauth_version
     * 
     * @see org.apache.amber.Version
     */
    VERSION("oauth_version");

    private final String label;

    private OAuthParameter(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return this.label;
    }

}