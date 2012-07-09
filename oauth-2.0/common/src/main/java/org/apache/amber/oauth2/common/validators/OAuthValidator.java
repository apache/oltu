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

import org.apache.amber.oauth2.common.exception.OAuthProblemException;

/**
 *
 *
 *
 */
public interface OAuthValidator<T extends HttpServletRequest> {

    public void validateMethod(T request) throws OAuthProblemException;

    public void validateContentType(T request) throws OAuthProblemException;

    public void validateRequiredParameters(T request) throws OAuthProblemException;

    public void validateOptionalParameters(T request) throws OAuthProblemException;

    public void validateNotAllowedParameters(T request) throws OAuthProblemException;

    public void performAllValidations(T request) throws OAuthProblemException;

}
