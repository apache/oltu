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

import java.util.Collection;

/**
 * Association of nonce(s) and timestamp.
 *
 * @version $Id$
 */
public interface Access extends Comparable<Long> {

    /**
     * Returns the timestamp access.
     *
     * @return the timestamp access
     */
    long getTimestamp();

    /**
     * Add a nonce to an access.
     *
     * @param nonce the nonce has o be added.
     * @return true, if the nonce has not used yet, false otherwise.
     */
    boolean addNonce(String nonce);

    /**
     * Checks if the access already contains a nonce.
     *
     * @param nonce the nonce has to be checked.
     * @return true if the nonce is contained, false otherwise.
     */
    boolean containsNonce(String nonce);

    /**
     * Returns the whole list of nonces associated to the timestamp.
     *
     * @return the whole list of nonces associated to the timestamp.
     */
    Collection<String> getNonces();

}
