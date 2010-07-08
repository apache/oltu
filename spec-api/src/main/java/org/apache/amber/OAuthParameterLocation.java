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
 * Defines the possible locations for the OAuth parameters during each request.
 *
 * N.B. Each Provider is likely to have a preferred location.
 *
 * @version $Id$
 */
public enum OAuthParameterLocation {

    /**
     * OAuth parameters should be included in the appropriate header.
     */
    Header,

    /**
     * OAuth parameters are included in the query string.
     *
     * N.B. size/length limits may impact on the request.
     */
    Query,

    /**
     * OAuth parameters are included in the body of the request.
     */
    Body,

}
