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

/**
 * Nonce/timestamp storage definition.
 *
 * @version $Id$
 */
public interface AccessStorage {

    /**
     * Stores a new nonce / timestamp pair associated with a consumer key.
     *
     * @param clientCredentials the consumer to be associated with the access.
     * @param timestamp the timestamp of the request.
     * @param nonce the nonce associated with the timestamp.
     * @throws StorageException if a backend error occurs.
     */
    void add(String clientCredentials, long timestamp, String nonce) throws StorageException;

    /**
     * Get the last access (by timestamp) of a consumer.
     *
     * @param clientCredentials the consumer whose access needs to be read.
     * @return the access object.
     * @throws StorageException if a backend error occurs.
     */
    Access getLastAccess(String clientCredentials) throws StorageException;

    /**
     * Remove a consumer access.
     *
     * @param clientCredentials the consumer key.
     * @param timestamp the timestamp.
     * @throws StorageException if a backend error occurs.
     */
    void remove(String clientCredentials, long timestamp) throws StorageException;

}
