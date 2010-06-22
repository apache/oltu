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
package org.apache.amber;

/**
 * @version $Revision$ $Date$
 * 
 */
public enum Version {

    /**
     * 1.0
     * 
     * @since 1.0
     */
    v1_0(1, 0),

    /**
     * 1.0a
     * 
     * @since 1.0a
     */
    v1_0a(1, 0, 'a')

    ; // End of enum type definitions

    private static final char EMPTY_VARIANT = ' ';

    private static final char UNDERSCORE_SEPARATOR = '_';

    private static final char DOT_SEPARATOR = '.';

    private final int major;

    private final int minor;

    private final char variant;

    /**
     * 
     */
    Version(int major, int minor) {
        this(major, minor, EMPTY_VARIANT);
    }

    /**
     * 
     */
    Version(int major, int minor, char variant) {
        this.major = major;
        this.minor = minor;
        this.variant = variant;
    }

    /**
     * @return handle
     */
    public String toHandle() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName())
            .append(UNDERSCORE_SEPARATOR)
            .append(this.major)
            .append(UNDERSCORE_SEPARATOR)
            .append(this.minor);
        if (this.variant != ' ') {
            s.append(UNDERSCORE_SEPARATOR);
            s.append(this.variant);
        }

        return s.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(this.major)
            .append(DOT_SEPARATOR)
            .append(this.minor);
        if (EMPTY_VARIANT != this.variant) {
            s.append(this.variant);
        }

        return s.toString();
    }

}
