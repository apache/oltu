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

package org.apache.oltu.oauth2.rs.extractor;

import org.apache.oltu.oauth2.common.OAuth;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HeaderTokenExtractorTest {

    @Test
    public void testGetAccessToken() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn("Bearer sometoken");
        replay(request);
        BearerHeaderTokenExtractor hte = new BearerHeaderTokenExtractor();
        assertEquals("sometoken", hte.getAccessToken(request));
        verify(request);
    }

    @Test
    public void testGetAccessTokenNull() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getHeader(OAuth.HeaderType.AUTHORIZATION)).andStubReturn(null);
        replay(request);
        BearerHeaderTokenExtractor hte = new BearerHeaderTokenExtractor();
        assertNull(hte.getAccessToken(request));
        verify(request);
    }
}
