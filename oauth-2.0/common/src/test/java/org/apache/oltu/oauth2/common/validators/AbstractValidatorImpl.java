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

package org.apache.oltu.oauth2.common.validators;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.validators.AbstractValidator;

/**
 *
 *
 *
 */
public class AbstractValidatorImpl extends AbstractValidator {

    public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
        super.validateMethod(request);
    }

    public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
        super.validateContentType(request);
    }

    public void validateRequiredParameters(HttpServletRequest request) throws OAuthProblemException {
        super.validateRequiredParameters(request);
    }

    public void validateOptionalParameters(HttpServletRequest request) throws OAuthProblemException {
        super.validateOptionalParameters(request);
    }

    public void validateNotAllowedParameters(HttpServletRequest request) throws OAuthProblemException {
        super.validateNotAllowedParameters(request);
    }

    public void performAllValidations(HttpServletRequest request) throws OAuthProblemException {
        super.performAllValidations(request);
    }
}
