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
package org.apache.labs.amber.signature.message;

/**
 * OAuth base request message representation.
 *
 * This class is not thread-safety.
 *
 * @version $Id$
 */
public class RequestMessage {

    /**
     * The default OAuth protocol version.
     */
    public final static String DEFAULT_VERSION = "1.0";

    /**
     * The {@code oauth_consumer_key} parameter.
     */
    @OAuthParameter(name = "oauth_consumer_key")
    private String clientIdentifier;

    /**
     * The {@code oauth_signature_method} parameter.
     */
    @OAuthParameter(name = "oauth_signature_method")
    private String signatureMethod;

    /**
     * The {@code oauth_signature} parameter.
     */
    @OAuthParameter(
            name = "oauth_signature",
            includeInSignature = false
    )
    private String signature;

    /**
     * The {@code oauth_timestamp} parameter.
     */
    @OAuthParameter(name = "oauth_timestamp", optional = true)
    private long timestamp;

    /**
     * The {@code oauth_nonce} parameter.
     */
    @OAuthParameter(name = "oauth_nonce")
    private String nonce;

    /**
     * The {@code oauth_version} parameter.
     */
    @OAuthParameter(
            name = "oauth_version",
            optional = true,
            includeInSignature = false
    )
    private String version;

    /**
     * The {@code realm} parameter.
     */
    @OAuthParameter(
            name = "realm",
            optional = true,
            includeInSignature = false
    )
    private String realm;

    /**
     * Returns the client identifier.
     *
     * @return the client identifier.
     */
    public String getClientIdentifier() {
        return this.clientIdentifier;
    }

    /**
     * Sets the client identifier.
     *
     * @param clientIdentifier the client identifier.
     */
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    /**
     * Returns the signature method.
     *
     * @return the signature method.
     */
    public String getSignatureMethod() {
        return this.signatureMethod;
    }

    /**
     * Sets the signature method.
     *
     * @param signatureMethod the signature method.
     */
    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    /**
     * Returns the signature.
     *
     * @return the signature.
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     * Sets the signature.
     *
     * @param signature the signature.
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Returns the timestamp.
     *
     * @return the timestamp.
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp the timestamp.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the nonce.
     *
     * @return the nonce.
     */
    public String getNonce() {
        return this.nonce;
    }

    /**
     * Sets the nonce.
     *
     * @param nonce the nonce.
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    /**
     * Returns the protocol version.
     *
     * @return the protocol version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Sets the protocol version.
     *
     * @param version the protocol version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Returns the realm.
     *
     * @return the realm.
     */
    public String getRealm() {
        return this.realm;
    }

    /**
     * Sets the realm.
     *
     * @param realm the realm.
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

}
