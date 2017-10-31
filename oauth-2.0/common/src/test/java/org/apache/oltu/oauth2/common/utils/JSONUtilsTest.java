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

package org.apache.oltu.oauth2.common.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.junit.Test;

/**
 *
 *
 *
 */
public class JSONUtilsTest {

    @Test
    public void testBuildJSON() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(OAuthError.OAUTH_ERROR, OAuthError.TokenResponse.INVALID_REQUEST);

        String json = JSONUtils.buildJSON(params);

        assertEquals("{\"error\":\"invalid_request\"}", json);
    }

    @Test
    public void testParseJson() throws Exception {
        Map<String, Object> jsonParams = new HashMap<String, Object>();
        jsonParams.put("author", "John B. Smith");
        jsonParams.put("year", "2000");

        String s = JSONUtils.buildJSON(jsonParams);
        Map<String, Object> map = JSONUtils.parseJSON(s);
        assertEquals("John B. Smith", map.get("author"));
        assertEquals("2000", map.get("year"));
    }

}
