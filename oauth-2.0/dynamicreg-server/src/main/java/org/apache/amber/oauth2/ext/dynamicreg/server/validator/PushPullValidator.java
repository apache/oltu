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

package org.apache.amber.oauth2.ext.dynamicreg.server.validator;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.common.validators.AbstractValidator;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;
import org.apache.amber.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 *
 */
public class PushPullValidator extends AbstractValidator<JSONHttpServletRequestWrapper> {

    private Logger log = LoggerFactory.getLogger(PushPullValidator.class);

    public PushPullValidator() {

    }

    public void validateContentType(JSONHttpServletRequestWrapper request) throws OAuthProblemException {

        String contentType = request.getContentType();
        final String expectedContentType = OAuth.ContentType.JSON;
        if (!OAuthUtils.hasContentType(contentType, expectedContentType)) {
            throw OAuthUtils.handleBadContentTypeException(expectedContentType);
        }

//        if content type is json initialize validator for pull or push method based on json content
        initializeValidationParameter(request);

    }

    private void initializeValidationParameter(JSONHttpServletRequestWrapper request)
        throws OAuthProblemException {
        final String requestType = request.getParameter(OAuthRegistration.Request.TYPE);

        if (OAuthUtils.isEmpty(requestType)) {
            throw OAuthUtils.handleOAuthProblemException("Missing [type] parameter value");
        }

        if (OAuthRegistration.Type.PULL.equals(requestType)) {
            requiredParams.add(OAuthRegistration.Request.TYPE);
            requiredParams.add(OAuthRegistration.Request.CLIENT_URL);
        } else if (OAuthRegistration.Type.PUSH.equals(requestType)) {
            requiredParams.add(OAuthRegistration.Request.TYPE);
            requiredParams.add(OAuthRegistration.Request.CLIENT_NAME);
            requiredParams.add(OAuthRegistration.Request.CLIENT_URL);
            requiredParams.add(OAuthRegistration.Request.CLIENT_DESCRIPTION);
            requiredParams.add(OAuthRegistration.Request.REDIRECT_URL);
        } else {
            throw OAuthUtils.handleOAuthProblemException("Invalid [type] parameter value");
        }

        if (log.isDebugEnabled()) {
            log.debug("OAuth dynamic client registration type is: {}", new String[] {requestType});
        }
    }
}
