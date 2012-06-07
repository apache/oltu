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

package org.apache.amber.oauth2.httpclient4;

import java.net.URI;
import java.util.Map;

import org.apache.amber.oauth2.client.HttpClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * Exemplar HttpClient4
 *
 *
 *
 *
 */
public class HttpClient4 implements HttpClient {

    private org.apache.http.client.HttpClient client;

    public HttpClient4() {
        client = new DefaultHttpClient();
    }

    public HttpClient4(org.apache.http.client.HttpClient client) {
        this.client = client;
    }
    
    public void shutdown() {
        if (client != null) {
            ClientConnectionManager connectionManager = client.getConnectionManager();
            if (connectionManager != null) {
                connectionManager.shutdown();
            }
        }
    }

    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request,
                                                     Map<String, String> headers,
                                                     String requestMethod,
                                                     Class<T> responseClass)
        throws OAuthSystemException, OAuthProblemException {

        try {
            URI location = new URI(request.getLocationUri());
            HttpRequestBase req = null;
            String responseBody = "";

            if (!OAuthUtils.isEmpty(requestMethod) && OAuth.HttpMethod.POST.equals(requestMethod)) {
                req = new HttpPost(location);
                HttpEntity entity = new StringEntity(request.getBody());
                ((HttpPost)req).setEntity(entity);
            } else {
                req = new HttpGet(location);
            }
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    req.setHeader(header.getKey(), header.getValue());
                }
            }
            HttpResponse response = client.execute(req);
            Header contentTypeHeader = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                contentTypeHeader = entity.getContentType();
            }
            String contentType = null;
            if (contentTypeHeader != null) {
                contentType = contentTypeHeader.toString();
            }

            return OAuthClientResponseFactory
                .createCustomResponse(responseBody, contentType, response.getStatusLine().getStatusCode(),
                    responseClass);
        } catch (Exception e) {
            throw new OAuthSystemException(e);
        }

    }
}
