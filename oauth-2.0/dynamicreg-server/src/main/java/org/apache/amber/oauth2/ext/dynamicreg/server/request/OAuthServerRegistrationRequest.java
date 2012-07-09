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

package org.apache.amber.oauth2.ext.dynamicreg.server.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.validators.OAuthValidator;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;
import org.apache.amber.oauth2.as.request.OAuthRequest;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.ext.dynamicreg.server.validator.PushPullValidator;


/**
 *
 *
 *
 */
public class OAuthServerRegistrationRequest extends OAuthRequest {

    private String type;

    private boolean isDiscovered;

    public OAuthServerRegistrationRequest(JSONHttpServletRequestWrapper request)
        throws OAuthSystemException, OAuthProblemException {
        this(request, false);
    }

    public OAuthServerRegistrationRequest(JSONHttpServletRequestWrapper request, boolean discover)
        throws OAuthSystemException, OAuthProblemException {
        super(request);
        if (discover) {
            discover();
        }
    }

    @Override
    protected OAuthValidator initValidator() throws OAuthProblemException, OAuthSystemException {
        return new PushPullValidator();
    }

    public void discover() throws OAuthSystemException {
        if (OAuthRegistration.Type.PULL.equals(type)) {
            // discover            
        }
        isDiscovered = true;
    }

    public String getType() {
        return getParam(OAuthRegistration.Request.TYPE);
    }

    public String getClientName() {
        return getParam(OAuthRegistration.Request.CLIENT_NAME);
    }

    public String getClientUrl() {
        return getParam(OAuthRegistration.Request.CLIENT_URL);
    }

    public String getClientDescription() {
        return getParam(OAuthRegistration.Request.CLIENT_DESCRIPTION);
    }

    public String getClientIcon() {
        return getParam(OAuthRegistration.Request.CLIENT_ICON);
    }

    public String getRedirectURI() {
        return getParam(OAuthRegistration.Request.REDIRECT_URL);
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

}
