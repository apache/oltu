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

import org.apache.amber.oauth2.client.utils.TestUtils;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 *
 */
public class OAuthJSONAccessTokenResponseTest extends Assert {

    Logger logger = LoggerFactory.getLogger(OAuthJSONAccessTokenResponse.class);

    @Test
    public void testGetAccessToken() throws Exception {
        logger.info("Running test: testGetAccessToken " + this.getClass().getName());
        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.ACCESS_TOKEN, r.getAccessToken());

        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());
        }
    }

    @Test
    public void testGetExpiresIn() throws Exception {
        OAuthJSONAccessTokenResponse r = null;

        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.EXPIRES_IN, r.getExpiresIn());

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());
        }
    }

    @Test
    public void testGetScope() throws Exception {
        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.SCOPE, r.getScope());

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());

        }
    }

    @Test
    public void testGetRefreshToken() throws Exception {
        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        Assert.assertEquals(TestUtils.REFRESH_TOKEN, r.getRefreshToken());

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertNotNull(e.getError());
        }
    }

    @Test
    public void testSetBody() throws Exception {

        OAuthJSONAccessTokenResponse r = null;
        try {
            r = new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.VALID_JSON_RESPONSE,
                OAuth.ContentType.JSON, 200);
        } catch (OAuthProblemException e) {
            fail("Exception not expected");
        }

        String accessToken = r.getAccessToken();
        Long expiresIn = r.getExpiresIn();

        Assert.assertEquals(TestUtils.EXPIRES_IN, expiresIn);
        Assert.assertEquals(TestUtils.ACCESS_TOKEN, accessToken);

        try {
            new OAuthJSONAccessTokenResponse();
            r.init(TestUtils.ERROR_JSON_BODY,
                OAuth.ContentType.JSON, 200);
            fail("Exception expected");
        } catch (OAuthProblemException e) {
            Assert.assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, e.getError());
        }


    }
}
