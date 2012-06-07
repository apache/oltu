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

package org.apache.amber.oauth2.integration;

/**
 *
 *
 *
 */
public final class CommonExt {
    private CommonExt() {
    }

    public static final String REGISTRATION_ENDPOINT = "http://localhost:9000/auth/oauth2ext/register";
    public static final String APP_NAME = "Sample Application";
    public static final String APP_URL = "http://www.example.com";
    public static final String APP_ICON = "http://www.example.com/app.ico";
    public static final String APP_DESCRIPTION = "Description of a Sample App";
    public static final String APP_REDIRECT_URI = "http://www.example.com/redirect";

    public static final String CLIENT_ID = "someclientid";
    public static final String CLIENT_SECRET = "someclientsecret";
    public static final String ISSUED_AT = "0123456789";
    public static final Long EXPIRES_IN = 987654321l;

}
