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

package org.apache.oltu.oauth2.client.validator;

import org.junit.Assert;
import org.junit.Test;
import org.apache.oltu.oauth2.client.validator.OAuthClientValidator;
import org.apache.oltu.oauth2.client.validator.TokenValidator;
import org.apache.oltu.oauth2.common.OAuth;


/**
 *
 *
 *
 */
public class TokenValidatorTest {

    @Test
    public void testValidator() {

        OAuthClientValidator validator = new TokenValidator();
        Assert.assertNotNull(validator.requiredParams);
        Assert.assertEquals(1, validator.requiredParams.size());
        Assert.assertArrayEquals(new String[] {}, validator.requiredParams.get(OAuth.OAUTH_ACCESS_TOKEN));
        Assert.assertNotNull(validator.notAllowedParams);
        Assert.assertEquals(1, validator.notAllowedParams.size());
        Assert.assertTrue(validator.notAllowedParams.contains(OAuth.OAUTH_CODE));
    }

}
