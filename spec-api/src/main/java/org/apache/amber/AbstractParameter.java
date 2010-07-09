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

import java.util.Map.Entry;

/**
 * 
 *
 * @version $Id$
 * @param <N>
 */
abstract class AbstractParameter<N>
        implements Entry<N, String>, Comparable<AbstractParameter<N>> {

    /**
     * A non-zero, odd number used as the initial value.
     */
    private static final int INITIAL_ODD_NUMBER = 1;

    /**
     * A non-zero, odd number used as the multiplier.
     */
    private static final int MULTIPLIER_ODD_NUMBER = 31;

    /**
     * The parameter key.
     */
    private final N key;

    /**
     * The parameter value.
     */
    private final String value;

    /**
     * Creates a new parameter by his key and value.
     *
     * @param key the parameter key.
     * @param value the parameter value.
     */
    public AbstractParameter(N key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Paramater 'key' must not be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Paramater 'value' must not be null");
        }
        this.key = key;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    public final int compareTo(AbstractParameter<N> parameter) {
        int nameComparison = String.valueOf(this.key).compareTo(String.valueOf(parameter.getKey()));
        if (nameComparison == 0) {
            return this.value.compareTo(parameter.getValue());
        }
        return nameComparison;
    }

    /**
     * {@inheritDoc}
     */
    public final N getKey() {
        return this.key;
    }

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    public final String setValue(String value) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = INITIAL_ODD_NUMBER;
        result = MULTIPLIER_ODD_NUMBER * result + ((this.key == null) ? 0 : this.key.hashCode());
        result = MULTIPLIER_ODD_NUMBER * result + ((this.value == null) ? 0 : this.value.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new StringBuilder("Parameter { key=")
                .append(this.key)
                .append(", value=")
                .append(this.value)
                .append(" }")
                .toString();
    }

}
