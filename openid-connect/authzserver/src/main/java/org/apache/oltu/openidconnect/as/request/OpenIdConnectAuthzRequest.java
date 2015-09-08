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

package org.apache.oltu.openidconnect.as.request;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.common.validators.OAuthValidator;
import org.apache.oltu.oauth2.common.validators.OAuthValidatorMixer;
import org.apache.oltu.oauth2.as.validator.CodeValidator;
import org.apache.oltu.oauth2.as.validator.TokenValidator;
import org.apache.oltu.openidconnect.common.OpenIdConnect;

/**
 *
 *
 *
 */
public class OpenIdConnectAuthzRequest extends OAuthRequest {

    public OpenIdConnectAuthzRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }

    @Override
    protected OAuthValidator<HttpServletRequest> initValidator() throws OAuthProblemException, OAuthSystemException {
        //end user authorization validators
        validators.put(ResponseType.CODE.toString(), CodeValidator.class);
        validators.put(ResponseType.TOKEN.toString(), TokenValidator.class);
        validators.put(ResponseType.ID_TOKEN.toString(), TokenValidator.class);
        final String requestTypeValue = getParam(OAuth.OAUTH_RESPONSE_TYPE);
        final String[]splitedRequestTypeValue=requestTypeValue.split(" ");

        if (OAuthUtils.isEmpty(requestTypeValue)||splitedRequestTypeValue.length==0) {
            throw OAuthUtils.handleOAuthProblemException("Missing response_type parameter value");
        }
        Collection<Class<? extends OAuthValidator<HttpServletRequest>>> valids=new ArrayList<Class<? extends OAuthValidator<HttpServletRequest>>>();
        for(int i=0;i<splitedRequestTypeValue.length;i++) {
            final Class<? extends OAuthValidator<HttpServletRequest>> clazz = validators.get(splitedRequestTypeValue[i]);
            if (clazz == null) {
                throw OAuthUtils.handleOAuthProblemException("Invalid response_type parameter value");
            }
            valids.add(clazz);
        }
        return merge(valids);

    }

    /**
     * Mix validator in one
     * @param valids
     * @return
     */
    private OAuthValidator<HttpServletRequest> merge(Collection<Class<? extends OAuthValidator<HttpServletRequest>>> valids) throws OAuthSystemException {
        return new OAuthValidatorMixer(valids);
    }

    public String getState() {
        return getParam(OAuth.OAUTH_STATE);
    }

    public String[] getResponseType() {
        return getParam(OAuth.OAUTH_RESPONSE_TYPE).split(" ");
    }

    public String getNonce() {
        return getParam(OpenIdConnect.OPENIDCONNECT_NONE);
    }
    public String getDisplay() {
        return getParam(OpenIdConnect.OPENIDCONNECT_DISPLAY);
    }
    public String getPrompt() {
        return getParam(OpenIdConnect.OPENIDCONNECT_PROMPT);
    }
    public String getMacAge() {
        return getParam(OpenIdConnect.OPENIDCONNECT_MAX_AGE);
    }
    public String getUiLocales() {return getParam(OpenIdConnect.OPENIDCONNECT_UI_LOCALES);}
    public String getIdTokenHint() {return getParam(OpenIdConnect.OPENIDCONNECT_ID_TOKEN_HINT);}
    public String getLoginHint() {return getParam(OpenIdConnect.OPENIDCONNECT_LOGIN_HINT);}
    public String getAcrValues() {return getParam(OpenIdConnect.OPENIDCONNECT_ACR_VALUES);}



}
