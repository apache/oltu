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
package org.apache.amber.oauth2.common.domain;

import org.apache.amber.oauth2.common.domain.client.BasicClientInfo;
import org.apache.amber.oauth2.common.domain.client.BasicClientInfoBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BasicClientInfoBuilderTest {

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String NAME = "name";
    public static final String ICON_URI = "icon_uri";
    public static final Long EXPIRES_IN = 1L;
    public static final Long ISSUED_AT = 2L;

    @Test
    public void testNewClientInfo() throws Exception {

        BasicClientInfo basicClientInfo = BasicClientInfoBuilder.clientInfo()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRedirectUri(REDIRECT_URI)
            .setName(NAME)
            .setIconUri(ICON_URI)
            .setExpiresIn(EXPIRES_IN)
            .setIssuedAt(ISSUED_AT)
            .build();

        Assert.assertNotNull(basicClientInfo);

        Assert.assertEquals(CLIENT_ID, basicClientInfo.getClientId());
        Assert.assertEquals(CLIENT_SECRET, basicClientInfo.getClientSecret());
        Assert.assertEquals(REDIRECT_URI, basicClientInfo.getRedirectUri());
        Assert.assertEquals(NAME, basicClientInfo.getName());
        Assert.assertEquals(ICON_URI, basicClientInfo.getIconUri());
        Assert.assertEquals(EXPIRES_IN, basicClientInfo.getExpiresIn());
        Assert.assertEquals(ISSUED_AT, basicClientInfo.getIssuedAt());
    }
}
