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

package org.apache.oltu.oauth2.client.response;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 *
 *
 */
public class OAuthResourceResponseTest {

    private static final byte[] BINARY = new byte[]{ 0, 1, 2, 3, 4, 5 };

    public static final String STRING = "roundtrip";

    private static final byte[] STRING_BYTES = STRING.getBytes(Charsets.UTF_8);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void allowBinaryResponseBody() throws OAuthProblemException, OAuthSystemException, IOException {
        final OAuthResourceResponse resp = createBinaryResponse(BINARY);
        final byte[] bytes = IOUtils.toByteArray(resp.getBodyAsInputStream());
        assertArrayEquals(BINARY, bytes);
    }

    @Test
    public void allowStringAsBinaryResponseBody() throws OAuthProblemException, OAuthSystemException, IOException {
        final OAuthResourceResponse resp = createBinaryResponse(STRING_BYTES);
        final byte[] bytes = IOUtils.toByteArray(resp.getBodyAsInputStream());
        assertArrayEquals(STRING_BYTES, bytes);
    }

    @Test
    public void allowStringResponseBody() throws OAuthProblemException, OAuthSystemException, IOException {
        final OAuthResourceResponse resp = createBinaryResponse(STRING_BYTES);
        assertEquals("getBody() should return correct string", STRING, resp.getBody());
    }

    @Test
    public void errorRetrievingBodyAfterStream() throws OAuthProblemException, OAuthSystemException, IOException {
        final OAuthResourceResponse resp = createBinaryResponse(STRING_BYTES);
        resp.getBodyAsInputStream();
        expectedException.expect(IllegalStateException.class);
        resp.getBody();
    }

    @Test
    public void errorRetrievingStreamAfterBody() throws OAuthProblemException, OAuthSystemException, IOException {
        final OAuthResourceResponse resp = createBinaryResponse(STRING_BYTES);
        resp.getBody();
        expectedException.expect(IllegalStateException.class);
        resp.getBodyAsInputStream();
    }

    private OAuthResourceResponse createBinaryResponse(byte[] bytes) throws OAuthSystemException, OAuthProblemException {
        final ByteArrayInputStream binaryStream = new ByteArrayInputStream(bytes);
        return OAuthClientResponseFactory.createCustomResponse(binaryStream, null, 200, new HashMap<String, List<String>>(), OAuthResourceResponse.class);
    }

}
