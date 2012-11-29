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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;


/**
 * Implementation of the OAuth HttpClient using URL Connection
 *
 *
 *
 *
 */
public class URLConnectionClient implements HttpClient {

    public URLConnectionClient() {
    }

    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request, Map<String, String> headers,
                                                     String requestMethod, Class<T> responseClass)
        throws OAuthSystemException, OAuthProblemException {

        String responseBody = null;
        URLConnection c = null;
        int responseCode = 0;
        try {
            URL url = new URL(request.getLocationUri());

            c = url.openConnection();
            responseCode = -1;
            if (c instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)c;

                if (headers != null && !headers.isEmpty()) {
                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        httpURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }
                
                if (request.getHeaders() != null) {
                    for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                    	httpURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }

                if (!OAuthUtils.isEmpty(requestMethod)) {
                    httpURLConnection.setRequestMethod(requestMethod);
                    if (requestMethod.equals(OAuth.HttpMethod.POST)) {
                        httpURLConnection.setDoOutput(true);
                        OutputStream ost = httpURLConnection.getOutputStream();
                        PrintWriter pw = new PrintWriter(ost);
                        pw.print(request.getBody());
                        pw.flush();
                        pw.close();
                    }
                } else {
                    httpURLConnection.setRequestMethod(OAuth.HttpMethod.GET);
                }

                httpURLConnection.connect();

                InputStream inputStream;
                responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 400) {
                    inputStream = httpURLConnection.getErrorStream();
                } else {
                    inputStream = httpURLConnection.getInputStream();
                }

                responseBody = OAuthUtils.saveStreamAsString(inputStream);
            }
        } catch (IOException e) {
            throw new OAuthSystemException(e);
        }

        return OAuthClientResponseFactory
            .createCustomResponse(responseBody, c.getContentType(), responseCode, responseClass);
    }

    @Override
    public void shutdown() {
        // Nothing to do here
    }

}
