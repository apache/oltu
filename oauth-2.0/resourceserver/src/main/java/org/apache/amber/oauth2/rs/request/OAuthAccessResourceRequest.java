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

package org.apache.amber.oauth2.rs.request;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.common.message.types.TokenType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.common.validators.OAuthValidator;
import org.apache.amber.oauth2.common.OAuth; 
import org.apache.amber.oauth2.rs.BearerResourceServer;
import org.apache.amber.oauth2.rs.ResourceServer;
import org.apache.amber.oauth2.rs.extractor.TokenExtractor;

/**
 *
 *
 *
 */
public class OAuthAccessResourceRequest {

    private HttpServletRequest request;
    private ParameterStyle[] parameterStyles=new ParameterStyle[] {OAuth.DEFAULT_PARAMETER_STYLE};
    private TokenType[] tokenTypes=new TokenType []{OAuth.DEFAULT_TOKEN_TYPE};
    private ParameterStyle usedParameterStyle;
    private ResourceServer usedResourceServer;

    protected static Map<TokenType, Class> tokens = new HashMap<TokenType, Class>();

    private TokenExtractor extractor;
    
    {
        tokens.put(TokenType.BEARER, BearerResourceServer.class);
        //TODO add MACResourceServer - see AMBER-41
    }
  
    public OAuthAccessResourceRequest(HttpServletRequest request)
        throws OAuthSystemException, OAuthProblemException {
        this(request,new TokenType []{OAuth.DEFAULT_TOKEN_TYPE}, new ParameterStyle[] {OAuth.DEFAULT_PARAMETER_STYLE});
    }

    public OAuthAccessResourceRequest(HttpServletRequest request, ParameterStyle... parameterStyles)
    throws OAuthSystemException, OAuthProblemException {
    	this(request,new TokenType []{OAuth.DEFAULT_TOKEN_TYPE}, parameterStyles);
    }
    
    public OAuthAccessResourceRequest(HttpServletRequest request, TokenType... tokenTypes)
    throws OAuthSystemException, OAuthProblemException {
    	this(request,tokenTypes,  new ParameterStyle[] {OAuth.DEFAULT_PARAMETER_STYLE});
    }
    
    public OAuthAccessResourceRequest(HttpServletRequest request, TokenType[] tokenTypes ,ParameterStyle[] parameterStyles)
        throws OAuthSystemException, OAuthProblemException {
        this.request = request;
        this.tokenTypes = tokenTypes;
        this.parameterStyles = parameterStyles;
        this.validate();
    }

    public String getAccessToken() throws OAuthSystemException {
        return extractor.getAccessToken(request);
    }

    private void validate() throws OAuthSystemException, OAuthProblemException {

        int foundValidStyles = 0;
        boolean lackAuthInfo = false;
        OAuthProblemException ex = null;
        String lackAuthReason = "OAuth parameters were not found";
        for (TokenType tokenType : tokenTypes) {
        	ResourceServer resourceServer=instantiateResourceServer(tokenType);
        	for (ParameterStyle style : parameterStyles) {
        		try {
        			 
        			OAuthValidator validator = resourceServer.instantiateValidator(style);
        			validator.validateContentType(request);
        			validator.validateMethod(request);
        			validator.validateRequiredParameters(request);

        			usedParameterStyle = style;
        			usedResourceServer = resourceServer;
        			foundValidStyles++;
        		} catch (OAuthProblemException e) {
        			//request lacks any authentication information?
        			if (OAuthUtils.isEmpty(e.getError())) {
        				lackAuthInfo = true;
        				lackAuthReason = e.getDescription();
        			} else {        				 
        				ex = OAuthProblemException.error(e.getError(), e.getDescription());
        			}
        		}
        	}
        }

        if (foundValidStyles > 1) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST,
                "Found more than one mechanism for authenticating client");
        }

        if (ex != null) {
            throw ex;
        }

        if (foundValidStyles == 0 && lackAuthInfo) {
            throw OAuthProblemException.error(null, lackAuthReason);
        }

        if (foundValidStyles == 0) {
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_REQUEST,
                "OAuth parameters were not found");
        }

        extractor= usedResourceServer.instantiateExtractor(usedParameterStyle);
    }

    public static ResourceServer instantiateResourceServer(TokenType tokenType) throws OAuthSystemException {
        Class clazz = tokens.get(tokenType);
        if (clazz == null) {
            throw new OAuthSystemException("Cannot instantiate a resource server.");
        }
        return (ResourceServer)OAuthUtils.instantiateClass(clazz);
    }
    
}
