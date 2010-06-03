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
package org.apache.amber.signature.signers;

/**
 * Encapsulate a general OAuth signature error or warning.
 *
 * @version $Id$
 */
public final class SignatureException extends Exception {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new signature exception with the specified detail message.
     *
     * @param message the specified detail message.
     */
    public SignatureException(String message) {
        super(message);
    }

    /**
     * Constructs a new signature exception with the specified cause.
     *
     * @param cause the specified cause.
     */
    public SignatureException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new signature exception with the specified detail message
     * and cause.
     *
     * @param message the specified detail message.
     * @param cause the specified cause.
     */
    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

}
