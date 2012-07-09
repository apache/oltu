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

package org.apache.amber.oauth2.common.validators;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.junit.Test;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

/**
 *
 *
 *
 */
public class AbstractValidatorTest {

    @Test
    public void testValidateContentType() throws Exception {

        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED);
        replay(request);

        AbstractValidator validator = new AbstractValidatorImpl();
        validator.validateContentType(request);
        verify(request);

        reset(request);

        expect(request.getContentType()).andStubReturn(OAuth.ContentType.URL_ENCODED + ";utf-8");
        replay(request);

        validator = new AbstractValidatorImpl();
        validator.validateContentType(request);
        verify(request);
    }

    @Test(expected = OAuthProblemException.class)
    public void testInvalidContentType() throws Exception {
        HttpServletRequest request = createStrictMock(HttpServletRequest.class);

        expect(request.getContentType()).andStubReturn(OAuth.ContentType.JSON);
        replay(request);

        AbstractValidator validator = new AbstractValidatorImpl();
        validator.validateContentType(request);
        verify(request);
    }
}
