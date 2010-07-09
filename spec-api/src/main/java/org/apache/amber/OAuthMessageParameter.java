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

/**
 * Identifier for the OAuth Authorization message parameter.
 *
 * @version $Id$
 */
public final class OAuthMessageParameter extends AbstractParameter<OAuthParameter> {

    public OAuthMessageParameter(OAuthParameter key, String value) {
        super(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = INITIAL_ODD_NUMBER;
        result = MULTIPLIER_ODD_NUMBER * result + ((this.getKey() == null) ? 0 : this.getKey().hashCode());
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

        AbstractParameter<?> other = (AbstractParameter<?>) obj;

        if (this.getKey() == null) {
            if (other.getKey() != null) {
                return false;
            }
        } else if (!String.valueOf(this.getKey()).equals(String.valueOf(other.getKey()))) {
            return false;
        }

        return true;
    }

}
