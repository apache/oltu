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
package org.apache.amber.server;

import org.apache.amber.OAuthException;
import org.apache.amber.OAuthRequest;
import org.apache.amber.OAuthToken;

/**
 * @version $Id $HeadURL $Revision $Date $Author
 *
 */
public interface OAuthnServer {

    /**
     * Generates a new request token.
     *
     * @param request
     * @return
     * @throws OAuthException
     */
    OAuthToken requestToken(OAuthRequest request) throws OAuthException;

    /**
     * Authorize a request token.
     *
     * @param oauthToken
     * @return
     * @throws OAuthException
     */
    OAuthToken authorizeToken(String oauthToken) throws OAuthException;

    /**
     * Generates a new access token.
     *
     * @param request
     * @return
     * @throws OAuthException
     */
    OAuthToken accessToken(OAuthRequest request) throws OAuthException;

}
