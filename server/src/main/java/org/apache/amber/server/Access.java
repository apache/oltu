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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Association of nonce(s) and timestamp.
 *
 * @version $Id$
 */
public final class Access implements Comparable<Long> {

    /**
     * The nonces associated to the timestamp.
     */
    private final Set<String> nonces = new HashSet<String>();

    /**
     * The timestamp reference.
     */
    private final long timestamp;

    /**
     * Creates a new access using the received timestamp and nonce.
     *
     * @param timestamp the received timestamp
     * @param nonce the received nonce
     */
    public Access(final long timestamp, final String nonce) {
        this.timestamp = timestamp;
        this.addNonce(nonce);
    }

    /**
     * Returns the timestamp access.
     *
     * @return the timestamp access
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Add a nonce to an access.
     *
     * @param nonce the nonce has o be added.
     * @return true, if the nonce has not used yet, false otherwise.
     */
    public boolean addNonce(String nonce) {
        return this.nonces.add(nonce);
    }

    /**
     * Checks if the access already contains a nonce.
     *
     * @param nonce the nonce has to be checked.
     * @return true if the nonce is contained, false otherwise.
     */
    public boolean containsNonce(String nonce) {
        return this.nonces.contains(nonce);
    }

    /**
     * Returns the whole list of nonces associated to the timestamp.
     *
     * @return the whole list of nonces associated to the timestamp.
     */
    protected Set<String> getNonces() {
        return Collections.unmodifiableSet(this.nonces);
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Long timestamp) {
        if (this.timestamp > timestamp) {
            return 1;
        } else if (this.timestamp < timestamp) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
      boolean equals = false;
      if (obj instanceof Access) {
        Access other = (Access) obj;
        if (other.timestamp == this.timestamp && other.nonces.equals(this.nonces)) {
          equals = true;
        }
      }
      return equals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((nonces == null) ? 0 : nonces.hashCode());
      result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
      return result;
    }

}
