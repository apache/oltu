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
package org.apache.amber.server;

import org.apache.amber.OAuthToken;

/**
 * 
 *
 * @version $Id$
 */
public interface TokenStorage {

    /**
     * Stores a new token.
     *
     * @param consumerKey Consumer key associated to the token to be stored
     * @param token The token to be stored
     * @throws StorageException if a backend error occurs.
     */
    void add(String consumerKey, OAuthToken token) throws StorageException;

    /**
     * Modifies an existing token.
     *
     * @param token The token to be modified
     * @throws StorageException if a backend error occurs.
     */
    void update(OAuthToken token) throws StorageException;

    /**
     * Searches for an existing token.
     *
     * @param consumerKey The consumer key associated with the Token
     * @param token The token string to be searched
     * @return the relative token associated to the consumerKey
     * @throws StorageException if a backend error occurs.
     */
    OAuthToken read(String consumerKey, String token) throws StorageException;

    /**
     * Searches for an existing token.
     *
     * @param token The token string to be searched
     * @return the relative token
     * @throws StorageException if a backend error occurs.
     */
    OAuthToken read(String token) throws StorageException;

    /**
     * Removes a token from the store.
     *
     * @param token The token to be removed
     * @throws StorageException if a backend error occurs.
     */
    void delete(String token) throws StorageException;

}
