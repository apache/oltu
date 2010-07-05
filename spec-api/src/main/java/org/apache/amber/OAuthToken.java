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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>
 * An OAuthToken is the wrapper for the pair of key values returned by the
 * {@link org.apache.amber.OAuthClient} during the authentication or authorisation
 * process.
 * </p>
 *
 * <p>
 * The implementation MUST also support validation of the returned access token
 * values to determine whether the token is authorised or unauthorised.
 * </p>
 *
 * <p>
 * A Map contains additional response parameters, sent by the provider.
 * </p>
 *
 * @version $Id$
 */
public final class OAuthToken implements Serializable {

    /**
     * The default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The additional response parameters, sent by the provider.
     */
    private final Map<String, String> additionalParameters = new HashMap<String, String>();

    /**
     * The {@code oauth_token} parameter.
     */
    private String token;

    /**
     * The {@code oauth_token_secret} parameter.
     */
    private String tokenSecret;

    /**
     * The {@code oauth_callback_confirmed} parameter.
     */
    private boolean callbackConfirmed;

    /**
     * @return the the {@code oauth_token} parameter.
     */
    public String getToken() {
        return this.token;
    }

    /**
     * @param token the {@code oauth_token} parameter.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the {@code oauth_token_secret} parameter.
     */
    public String getTokenSecret() {
        return tokenSecret;
    }

    /**
     * @param tokenSecret the {@code oauth_token_secret} parameter.
     */
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    /**
     * @return the {@code oauth_callback_confirmed} parameter.
     */
    public boolean isCallbackConfirmed() {
        return callbackConfirmed;
    }

    /**
     * @param callbackConfirmed the {@code oauth_callback_confirmed} parameter.
     */
    public void setCallbackConfirmed(boolean callbackConfirmed) {
        this.callbackConfirmed = callbackConfirmed;
    }

    /**
     * Associates the specified value with the specified name in this additional
     * parameters map.
     *
     * @param name name with which the specified value is to be associated.
     * @param value value to be associated with the specified name.
     */
    public void addAdditionalParameters(String name, String value) {
        this.additionalParameters.put(name, value);
    }

    /**
     * @return a {@link Set} view of the mappings contained in this additional
     *         parameters map
     */
    public Set<Entry<String, String>> additionalParametersEntrySet() {
        return this.additionalParameters.entrySet();
    }

}
