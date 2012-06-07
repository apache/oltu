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

package org.apache.amber.oauth2.client.response;

import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.client.response.OAuthClientResponseFactory;
import org.junit.Assert;
import org.junit.Test;
import org.apache.amber.oauth2.common.OAuth;


/**
 *
 *
 *
 */
public class OAuthClientResponseFactoryTest {

    @Test
    public void testCreateGitHubTokenResponse() throws Exception {
        OAuthClientResponse gitHubTokenResponse = OAuthClientResponseFactory
            .createGitHubTokenResponse("access_token=123", OAuth.ContentType.URL_ENCODED, 200);
        Assert.assertNotNull(gitHubTokenResponse);
    }

    @Test
    public void testCreateJSONTokenResponse() throws Exception {
        OAuthClientResponse jsonTokenResponse = OAuthClientResponseFactory
            .createJSONTokenResponse("{'access_token':'123'}", OAuth.ContentType.JSON, 200);
        Assert.assertNotNull(jsonTokenResponse);
    }

    @Test
    public void testCreateCustomResponse() throws Exception {

    }
}
