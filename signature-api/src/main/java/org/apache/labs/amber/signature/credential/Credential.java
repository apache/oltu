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
package org.apache.labs.amber.signature.credential;

/**
 * The OAuth credential representation.
 *
 * @version $Id$
 */
public class Credential {

    /**
     * The {@code oauth_token} parameter.
     */
    @CredentialParameter("oauth_token")
    private String identifier;

    /**
     * The {@code oauth_token_secret} parameter.
     */
    @CredentialParameter("sharedSecret")
    private String sharedSecret;

    /**
     * Return the credential identifier.
     *
     * @return the credential identifier.
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Set the credential identifier.
     *
     * @param identifier the credential identifier.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Return the credential shared secret.
     *
     * @return the credential shared secret.
     */
    public String getSharedSecret() {
        return this.sharedSecret;
    }

    /**
     * Set the credential shared secret.
     *
     * @param sharedSecret the credential shared secret.
     */
    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuffer("TemporaryCredential { identifier=")
                .append(this.identifier)
                .append(", sharedSecret=")
                .append(this.sharedSecret)
                .append(" }")
                .toString();
    }

}
