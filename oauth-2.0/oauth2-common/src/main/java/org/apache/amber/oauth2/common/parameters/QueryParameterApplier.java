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

import java.util.HashMap;
import java.util.Map;

import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.message.OAuthMessage;
import org.apache.amber.oauth2.common.utils.OAuthUtils;

/**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public class QueryParameterApplier implements OAuthParametersApplier {

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, String> params) {

        String messageUrl = message.getLocationUri();
        if (messageUrl != null) {
            boolean containsQuestionMark = messageUrl.contains("?");
            StringBuffer url = new StringBuffer(messageUrl);

            //apply uri fragment component if exist access_toke param
            Map<String, String> fragmentParams = new HashMap<String, String>();
            if (params.containsKey(OAuth.OAUTH_ACCESS_TOKEN)) {
                fragmentParams.put(OAuth.OAUTH_ACCESS_TOKEN, params.remove(OAuth.OAUTH_ACCESS_TOKEN));

                if (params.containsKey(OAuth.OAUTH_EXPIRES_IN)) {
                    fragmentParams.put(OAuth.OAUTH_EXPIRES_IN, params.remove(OAuth.OAUTH_EXPIRES_IN));
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
                url.append("#").append(fragmentQuery);
            }

            message.setLocationUri(url.toString());
        }
        return message;
    }
}
