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
 * <p>
 * It's possible that a {@link org.apache.amber.server.OAuthProvider} will return OAuth
 * information in a custom response format. The response parser interface allows
 * a {@link org.apache.amber.server.OAuthProvider} to specific a concrete implementation.
 * </p>
 * 
 * <p>
 * An OAuth API implementation MUST provide a response parser that assumes the
 * returned parameters are name/value pairs, separated by ampersand characters.
 * </p>
 * 
 * @version $Revision$ $Date$
 * 
 */
public interface OAuthResponseParser {

    /**
     * Update the provided token, to include the details from the response
     * 
     * @param token
     * @param response
     * @return token
     */
    OAuthToken parseResponse(OAuthToken token, OAuthResponse response);

}