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
package org.apache.labs.amber.signature.descriptors;

import java.net.URL;

/**
 * Basic representation of an OAuth service, defined by an URL and the relative
 * HTTP Method the URL has to be invoked.
 *
 * @version $Id$
 */
public class Service {

    /**
     * The {@link HTTPMethod} this service must be invoked with.
     */
    private final HTTPMethod httpMethod;

    /**
     * The service URL.
     */
    private final URL serviceUrl;

    /**
     * Builds a new service by the HTTP method and URI.
     *
     * @param httpMethod the {@link HTTPMethod} this service must be invoked with.
     * @param serviceUrl the service URI.
     */
    public Service(HTTPMethod httpMethod, URL serviceUrl) {
        this.httpMethod = httpMethod;
        this.serviceUrl = serviceUrl;
    }

    /**
     * Returns the {@link HTTPMethod} this service must be invoked with.
     *
     * @return the {@link HTTPMethod} this service must be invoked with.
     */
    public HTTPMethod getHttpMethod() {
        return this.httpMethod;
    }

    /**
     * Returns the service URI.
     *
     * @return the service URI.
     */
    public URL getServiceUri() {
        return this.serviceUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder("Service { httpMethod=")
                .append(httpMethod)
                .append(", serviceUri=")
                .append(serviceUrl)
                .append(" }")
                .toString();
    }

}
