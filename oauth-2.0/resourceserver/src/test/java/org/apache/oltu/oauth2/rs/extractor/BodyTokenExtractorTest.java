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

public class BodyTokenExtractorTest {

    @Test
    public void testGetAccessToken() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn("sometoken");
        replay(request);
        BearerBodyTokenExtractor bte = new BearerBodyTokenExtractor();
        assertEquals("sometoken", bte.getAccessToken(request));
        verify(request);
    }

    @Test
    public void testGetAccessTokenNull() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getParameter(OAuth.OAUTH_BEARER_TOKEN)).andStubReturn(null);
        expect(request.getParameter(OAuth.OAUTH_TOKEN)).andStubReturn(null);
        replay(request);
        BearerBodyTokenExtractor bte = new BearerBodyTokenExtractor();
        assertNull(bte.getAccessToken(request));
        verify(request);
    }
}
