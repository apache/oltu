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

import java.util.Properties;

/**
 * @author pidster
 * @version $Revision$ $Date$
 * 
 */
public interface OAuthFactory {

    /**
     * @return version
     */
    Version getVersion();

    /**
     * @param properties
     */
    void setProperties(Properties properties);

    /**
     * @return properties
     */
    Properties getProperties();

    /**
     * @return providers
     */
    OAuthProviders getProviders();

    /**
     * @param provider
     * @throws OAuthRuntimeException
     */
    void register(OAuthProvider provider) throws OAuthRuntimeException;

    /**
     * @param realm
     * @return service
     * @throws OAuthException
     */
    OAuthClient useService(String realm) throws OAuthException;

    /**
     * @param realm
     * @return service
     * @throws OAuthException
     */
    OAuthServer createServer(String realm) throws OAuthException;

}
