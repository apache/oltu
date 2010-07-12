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

import java.net.URI;
import java.util.Collection;

/**
 * A convenient object representing the properties of an OAuth request to a
 * {@link org.apache.amber.OAuthProvider}.
 *
 * @version $Id$
 */
public interface OAuthRequest {

    URI getRequestURL();

    void setRequestURL(URI requestURL);

    HTTPMethod getHTTPMethod();

    void setHTTPMethod(HTTPMethod httpMethod);

    OAuthParameterLocation getParameterLocation();

    void setParameterLocation(OAuthParameterLocation parameterLocation);

    /**
     * @param parameter
     */
    void addOAuthMessageParameter(OAuthMessageParameter parameter);

    /**
     * Returns the OAuth parameters will be included in the OAuth message.
     *
     * @return the OAuth parameters will be included in the OAuth message.
     */
    Collection<OAuthMessageParameter> getOAuthMessageParameters();

    /**
     * @param parameter
     */
    void addOAuthRequestParameter(OAuthRequestParameter parameter);

    /**
     * @param signatureMethod
     * @return
     */
    String getOAuthMessageParameter(OAuthParameter parameter);

    /**
     * Returns the aux parameters client will included in the request.
     *
     * @return the aux parameters client will included in the request.
     */
    Collection<OAuthRequestParameter> getOAuthRequestParameters();

    /**
     * @param signatureMethod
     * @return
     */
    String getOAuthRequestParameter(String name);

}
