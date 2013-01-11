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

package org.apache.amber.oauth2.common.parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.message.OAuthMessage;
import org.apache.amber.oauth2.common.utils.OAuthUtils;

/**
 *
 *
 *
 */
public class QueryParameterApplier implements OAuthParametersApplier {

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, Object> params) {

        String messageUrl = message.getLocationUri();
        if (messageUrl != null) {
            boolean containsQuestionMark = messageUrl.contains("?");
            StringBuffer url = new StringBuffer(messageUrl);

            //apply uri fragment component if exist access_toke param
            Map<String, Object> fragmentParams = new LinkedHashMap<String, Object>();
            if (params.containsKey(OAuth.OAUTH_ACCESS_TOKEN)) {
                fragmentParams.put(OAuth.OAUTH_ACCESS_TOKEN, params.remove(OAuth.OAUTH_ACCESS_TOKEN));

                // State should be in the fragment too
                if (params.containsKey(OAuth.OAUTH_STATE)) {
                    fragmentParams.put(OAuth.OAUTH_STATE, params.remove(OAuth.OAUTH_STATE));
                }
                
                if (params.containsKey(OAuth.OAUTH_EXPIRES_IN)) {
                    fragmentParams.put(OAuth.OAUTH_EXPIRES_IN, params.remove(OAuth.OAUTH_EXPIRES_IN));
                }
                
                if (params.containsKey(OAuth.OAUTH_TOKEN_TYPE)) {
                    fragmentParams.put(OAuth.OAUTH_TOKEN_TYPE, params.remove(OAuth.OAUTH_TOKEN_TYPE));
                }
                
                if (params.containsKey(OAuth.OAUTH_SCOPE)) {
                    fragmentParams.put(OAuth.OAUTH_SCOPE, params.remove(OAuth.OAUTH_SCOPE));
                }
                
                if (params.containsKey(OAuthError.OAUTH_ERROR)) {
                    fragmentParams.put(OAuthError.OAUTH_ERROR, params.remove(OAuthError.OAUTH_ERROR));
                }
                
                if (params.containsKey(OAuthError.OAUTH_ERROR_DESCRIPTION)) {
                    fragmentParams.put(OAuthError.OAUTH_ERROR_DESCRIPTION, params.remove(OAuthError.OAUTH_ERROR_DESCRIPTION));
                }
                
                if (params.containsKey(OAuthError.OAUTH_ERROR_URI)) {
                    fragmentParams.put(OAuthError.OAUTH_ERROR_URI, params.remove(OAuthError.OAUTH_ERROR_URI));
                }
                
            }

            StringBuffer query = new StringBuffer(OAuthUtils.format(params.entrySet(), "UTF-8"));
            String fragmentQuery = "";
            if (fragmentParams.containsKey(OAuth.OAUTH_ACCESS_TOKEN)) {
                fragmentQuery = OAuthUtils.format(fragmentParams.entrySet(), "UTF-8");
            }

            if (!OAuthUtils.isEmpty(query.toString())) {
                if (containsQuestionMark) {
                    url.append("&").append(query);
                } else {
                    url.append("?").append(query);
                }
            }

            if (!OAuthUtils.isEmpty(fragmentQuery)) {
            	if (fragmentParams.size()>1){
            		url.append("#").append(fragmentQuery);
            	}else{
            		if (containsQuestionMark) {
                        url.append("&").append(fragmentQuery);
                    } else {
                        url.append("?").append(fragmentQuery);
                    }
            	}
            }

            message.setLocationUri(url.toString());
        }
        return message;
    }
}
