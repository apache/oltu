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
public interface OAuthToken extends Serializable {

    /**
     * @return the the {@code oauth_token} parameter.
     */
    String getToken();

    /**
     * @param token the {@code oauth_token} parameter.
     */
    void setToken(String token);

    /**
     * @return the {@code oauth_token_secret} parameter.
     */
    String getTokenSecret();

    /**
     * @param tokenSecret the {@code oauth_token_secret} parameter.
     */
    void setTokenSecret(String tokenSecret);

    /**
     * @return the {@code oauth_callback_confirmed} parameter.
     */
    boolean isCallbackConfirmed();

    /**
     * @param callbackConfirmed the {@code oauth_callback_confirmed} parameter.
     */
    void setCallbackConfirmed(boolean callbackConfirmed);

    /**
     * Associates the specified value with the specified name in this additional
     * parameters map.
     *
     * @param name name with which the specified value is to be associated.
     * @param value value to be associated with the specified name.
     */
    void addAdditionalParameters(String name, String value);

    /**
     * @return a {@link Set} view of the mappings contained in this additional
     *         parameters map
     */
    Set<Entry<String, String>> additionalParametersEntrySet();

}
