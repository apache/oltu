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

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * The minimum configurable values required to specify the different behaviour
 * of a Provider (an entity defined by the OAuth specification).
 * </p>
 * 
 * @version $Revision$ $Date$
 * 
 */
public interface OAuthProvider extends Serializable {

    /**
     * @return consumer
     */
    List<OAuthConsumer> getConsumers();

    /**
     * @param consumers
     */
    void setConsumers(List<OAuthConsumer> consumers);

    /**
     * @return the algorithm
     */
    String getAlgorithm();

    /**
     * @param algorithm
     *            the algorithm to set
     */
    void setAlgorithm(String algorithm);

    /**
     * @return the location
     */
    OAuthParameterLocation getLocation();

    /**
     * @param location
     *            the location to set
     */
    void setLocation(OAuthParameterLocation location);

    /**
     * @return the realm
     */
    String getRealm();

    /**
     * @param realm
     *            the realm to set
     */
    void setRealm(String realm);

    /**
     * @return the requestTokenPath
     */
    String getRequestTokenPath();

    /**
     * @param requestTokenPath
     *            the requestTokenPath to set
     */
    void setRequestTokenPath(String requestTokenPath);

    /**
     * @return the accessTokenPath
     */
    String getAccessTokenPath();

    /**
     * @param accessTokenPath
     *            the accessTokenPath to set
     */
    void setAccessTokenPath(String accessTokenPath);

    /**
     * @return the authorizePath
     */
    String getAuthorizePath();

    /**
     * @param authorizePath
     *            the authorizePath to set
     */
    void setAuthorizePath(String authorizePath);

    /**
     * @return the authenticatePath
     */
    String getAuthenticatePath();

    /**
     * @param authenticatePath
     *            the authenticatePath to set
     */
    void setAuthenticatePath(String authenticatePath);

    /**
     * @return the responseParser
     */
    OAuthResponseParser getResponseParser();

    /**
     * @param responseParser
     *            the responseParser to set
     */
    void setResponseParser(OAuthResponseParser responseParser);

}