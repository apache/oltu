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
package org.apache.amber.signature;

import java.util.Set;
import java.util.Map.Entry;

import org.apache.amber.OAuthToken;

final class FakeToken implements OAuthToken {

    /**
     * 
     */
    private static final long serialVersionUID = -4501539974041422272L;

    private String tokenSecret;

    public void addAdditionalParameters(String name, String value) {
        // unneded for test purposes
    }

    public Set<Entry<String, String>> additionalParametersEntrySet() {
        // unneded for test purposes
        return null;
    }

    public String getToken() {
        // unneded for test purposes
        return null;
    }

    public String getTokenSecret() {
        // unneded for test purposes
        return this.tokenSecret;
    }

    public boolean isCallbackConfirmed() {
        // unneded for test purposes
        return false;
    }

    public void setCallbackConfirmed(boolean callbackConfirmed) {
        // unneded for test purposes
    }

    public void setToken(String token) {
        // unneded for test purposes
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

}
