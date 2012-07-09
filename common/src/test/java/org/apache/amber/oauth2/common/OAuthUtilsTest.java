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

package org.apache.amber.oauth2.common;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;

import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.utils.JSONUtils;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 *
 */
public class OAuthUtilsTest extends Assert {

    @Test
    public void testBuildJSON() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(OAuthError.OAUTH_ERROR, OAuthError.TokenResponse.INVALID_REQUEST);

        String json = JSONUtils.buildJSON(params);

        JSONObject obj = new JSONObject(json);

        AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);

        assertEquals(XMLStreamReader.START_ELEMENT, reader.next());
        assertEquals(OAuthError.OAUTH_ERROR, reader.getName().getLocalPart());

        assertEquals(OAuthError.TokenResponse.INVALID_REQUEST, reader.getText());
        assertEquals(XMLStreamReader.CHARACTERS, reader.next());
        assertEquals(XMLStreamReader.END_ELEMENT, reader.next());
        assertEquals(XMLStreamReader.END_DOCUMENT, reader.next());

    }

    @Test
    public void testEncodeOAuthHeader() throws Exception {

        Map<String, Object> entries = new HashMap<String, Object>();
        entries.put("realm", "Some Example Realm");
        entries.put("error", "invalid_token");

        String header = OAuthUtils.encodeOAuthHeader(entries);
        assertEquals("Bearer error=\"invalid_token\",realm=\"Some Example Realm\"", header);

    }
}
