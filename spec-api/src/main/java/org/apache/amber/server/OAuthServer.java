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

/**
 * An OAuth Server provides the functionality required to deliver OAuth Provider
 * functionality. It can be exposed by wrapping it in an HTTP layer, e.g. that
 * provided by the Servlet Spec or perhaps directly exposed by a custom HTTP
 * server.
 *
 * @version $Id$
 */
public abstract class OAuthServer implements OAuthnServer, OAuthzServer {

    private OAuthServerConfiguration configuration;

    /**
     * @param configuration
     */
    protected OAuthServer(OAuthServerConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * @return configuration
     */
    protected OAuthServerConfiguration getConfiguration() {
        return configuration;
    }

}
