/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
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

package org.apache.amber.oauth2.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.amber.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;

/**
 * OAuth Client - exposes a high-level API for Client Applications
 *
 *
 *
 *
 */
public class OAuthClient {

    protected HttpClient httpClient;

    public OAuthClient(HttpClient oauthClient) {
        this.httpClient = oauthClient;
    }

    public <T extends OAuthAccessTokenResponse> T accessToken(
        OAuthClientRequest request,
        Class<T> responseClass)
        throws OAuthSystemException, OAuthProblemException {

        return accessToken(request, OAuth.HttpMethod.POST, responseClass);
    }

    public <T extends OAuthAccessTokenResponse> T accessToken(
        OAuthClientRequest request, String requestMethod, Class<T> responseClass)
        throws OAuthSystemException, OAuthProblemException {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);

        return httpClient.execute(request, headers, requestMethod, responseClass);
    }

    public OAuthJSONAccessTokenResponse accessToken(
        OAuthClientRequest request)
        throws OAuthSystemException, OAuthProblemException {
        return accessToken(request, OAuthJSONAccessTokenResponse.class);
    }

    public OAuthJSONAccessTokenResponse accessToken(
        OAuthClientRequest request, String requestMethod)
        throws OAuthSystemException, OAuthProblemException {
        return accessToken(request, requestMethod, OAuthJSONAccessTokenResponse.class);
    }

    public void shutdown() {
        httpClient.shutdown();
    }
}
