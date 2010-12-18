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

package org.apache.amber.oauth2.as.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.as.validator.CodeTokenValidator;
import org.apache.amber.oauth2.common.OAuth;
import org.junit.Assert;
import org.junit.Test;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;


/**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public class CodeTokenValidatorTest {
    @Test
    public void testValidateMethod() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.GET);

        replay(request);
        CodeTokenValidator validator = new CodeTokenValidator();
        validator.validateMethod(request);

        verify(request);

        reset(request);

        request = createStrictMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.POST);

        replay(request);
        validator = new CodeTokenValidator();
        validator.validateMethod(request);

        verify(request);

        reset(request);

        request = createStrictMock(HttpServletRequest.class);
        expect(request.getMethod()).andStubReturn(OAuth.HttpMethod.DELETE);

        replay(request);
        validator = new CodeTokenValidator();

        try {
            validator.validateMethod(request);
            Assert.fail("Expected validation exception");
        } catch (OAuthProblemException e) {
            //ok, expected
        }

        verify(request);
    }
}
