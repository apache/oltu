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
package org.apache.oltu.oauth2.httpclient4;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HttpClient4Test {

    private static final Header[] SIMPLE_HEADERS =
            new Header[]{new BasicHeader("Location", "test-location"), new BasicHeader("Cache-Control", "no-cache")};
    private static final Header[] MULTI_VALUE_HEADERS_WITH_STRANGE_CASING =
            new Header[]{new BasicHeader("CACHE-CONTROL", "no-cache"), new BasicHeader("Cache-Control", "no-store")};

    private HttpClient4 httpClient4 = new HttpClient4();

    @Test
    public void shouldGetHeaders() {
        final Map<String, List<String>> headers = httpClient4.getHeaders(SIMPLE_HEADERS);
        assertEquals(2, headers.size());
    }

    @Test
    public void shouldGetMultiValueHeaders() {
        final Map<String, List<String>> headers = httpClient4.getHeaders(MULTI_VALUE_HEADERS_WITH_STRANGE_CASING);
        assertEquals(1, headers.size());
        final List<String> cacheControlHeader = headers.get("cache-control");
        assertEquals(2, cacheControlHeader.size());

        assertEquals(true, cacheControlHeader.contains("no-cache"));
        assertEquals(true, cacheControlHeader.contains("no-store"));
    }

    @Test
    public void shouldGetEmptyHeaders() {
        final Map<String, List<String>> headers = httpClient4.getHeaders(new Header[]{});
        assertEquals(0, headers.size());
    }

}