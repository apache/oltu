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
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.common.validators.OAuthValidator;
import org.apache.amber.oauth2.ext.dynamicreg.common.OAuthRegistration;
import org.apache.amber.oauth2.as.request.OAuthRequest;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.ext.dynamicreg.server.validator.PullValidator;
import org.apache.amber.oauth2.ext.dynamicreg.server.validator.PushValidator;


/**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public class OAuthServerRegistrationRequest extends OAuthRequest {

    private String type;

    private boolean isDiscovered;

    public OAuthServerRegistrationRequest(HttpServletRequest request)
        throws OAuthSystemException, OAuthProblemException {
        this(request, false);
    }

    public OAuthServerRegistrationRequest(HttpServletRequest request, boolean discover)
        throws OAuthSystemException, OAuthProblemException {
        super(request);
        if (discover) {
            discover();
        }
    }

    @Override
    protected OAuthValidator initValidator() throws OAuthProblemException, OAuthSystemException {
        validators.put(OAuthRegistration.Type.PULL, PullValidator.class);
        validators.put(OAuthRegistration.Type.PUSH, PushValidator.class);
        type = getParam(OAuthRegistration.Request.TYPE);
        if (OAuthUtils.isEmpty(type)) {
            throw OAuthUtils.handleOAuthProblemException("Missing type parameter value");
        }
        Class clazz = validators.get(type);
        if (clazz == null) {
            throw OAuthUtils.handleOAuthProblemException("Invalid type parameter value");
        }
        return (OAuthValidator)OAuthUtils.instantiateClass(clazz);
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

    public String getName() {
        return getParam(OAuthRegistration.Request.NAME);
    }

    public String getUrl() {
        return getParam(OAuthRegistration.Request.URL);
    }

    public String getDescription() {
        return getParam(OAuthRegistration.Request.DESCRIPTION);
    }

    public String getIcon() {
        return getParam(OAuthRegistration.Request.ICON);
    }

    public String getRedirectURI() {
        return getParam(OAuthRegistration.Request.REDIRECT_URI);
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

}
