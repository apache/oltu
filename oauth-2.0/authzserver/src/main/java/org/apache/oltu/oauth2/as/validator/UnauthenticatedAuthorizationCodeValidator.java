/**
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

package org.apache.oltu.oauth2.as.validator;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.validators.AbstractValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * Validator that checks for the required fields for an OAuth Token request with the Authorization Code grant type.
 * This validator does NOT enforce client authentication.
 *
 */
public class UnauthenticatedAuthorizationCodeValidator extends AbstractValidator<HttpServletRequest> {

    public UnauthenticatedAuthorizationCodeValidator() {
        requiredParams.add(OAuth.OAUTH_GRANT_TYPE);
        requiredParams.add(OAuth.OAUTH_CLIENT_ID);
        requiredParams.add(OAuth.OAUTH_CODE);

        enforceClientAuthentication = false;
    }
}
