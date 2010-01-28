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
package org.apache.labs.amber.signature.parameters;

/**
 * Simple parameter (name, value) representation, used when clients/server
 * send/receive aux parameters.
 *
 * This class is not thread-safety.
 *
 * @version $Id$
 */
public final class Parameter implements Comparable<Parameter> {

    /**
     * A non-zero, odd number used as the initial value.
     */
    private final static int INITIAL_ODD_NUMBER = 1;

    /**
     * A non-zero, odd number used as the multiplier.
     */
    private static final int MULTIPLIER_ODD_NUMBER = 31;

    /**
     * The parameter name.
     */
    private final String name;

    /**
     * The parameter value.
     */
    private final String value;

    /**
     * Creates a new parameter by his name and value.
     *
     * @param name the parameter name.
     * @param value the parameter value.
     */
    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the parameter name.
     *
     * @return the parameter name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the parameter value.
     *
     * @return the parameter value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Parameter parameter) {
        return this.name.compareTo(parameter.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = INITIAL_ODD_NUMBER;
        result = MULTIPLIER_ODD_NUMBER * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = MULTIPLIER_ODD_NUMBER * result + ((this.value == null) ? 0 : this.value.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Parameter other = (Parameter) obj;

        if (this.name == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!this.name.equals(other.getName())) {
            return false;
        }

        if (this.value == null) {
            if (other.getValue() != null) {
                return false;
            }
        } else if (!this.value.equals(other.getValue())) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder("Parameter { name=")
                .append(this.name)
                .append(", value=")
                .append(this.value)
                .append(" }")
                .toString();
    }

}
