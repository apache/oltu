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
package org.apache.amber.server;

import org.apache.amber.client.OAuthConsumer;

/**
 * OAuthConsumer consumer storage interface.
 *
 * @version $Id$
 */
public interface ConsumerStorage {

    /**
     * Stores consumer data.
     *
     * @param consumer the consumer has to be stored.
     * @throws StorageException if a backend error occurs.
     */
    void add(OAuthConsumer consumer) throws StorageException;

    /**
     * Modifies a consumer data.
     *
     * @param consumer the consumer has to be stored.
     * @throws StorageException if a backend error occurs.
     */
    void update(OAuthConsumer consumer) throws StorageException;

    /**
     * Searches for an existing consumer.
     *
     * @param consumerKey the consumer key.
     * @return the consumer data if any, null otherwise.
     * @throws StorageException if a backend error occurs.
     */
    OAuthConsumer read(String consumerKey) throws StorageException;

    /**
     * Removes a token from the store.
     *
     * @param consumerKey the consumer key.
     * @throws StorageException if a backend error occurs.
     */
    void delete(String consumerKey) throws StorageException;

}
