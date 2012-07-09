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

package org.apache.amber.oauth2.client.demo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.amber.oauth2.client.demo.exception.ApplicationException;
import org.apache.amber.oauth2.client.demo.model.OAuthParams;
import org.apache.amber.oauth2.client.demo.model.OAuthRegParams;

/**
 *
 *
 *
 */
public final class Utils {
    private Utils() {
    }

    public static final String REDIRECT_URI = "http://localhost:8080/redirect";
    public static final String DISCOVERY_URI = "http://localhost:8080";

    public static final String REG_TYPE_PULL = "pull";
    public static final String REG_TYPE_PUSH = "push";

    public static final String FACEBOOK = "facebook";
    public static final String FACEBOOK_AUTHZ = "https://graph.facebook.com/oauth/authorize";
    public static final String FACEBOOK_TOKEN = "https://graph.facebook.com/oauth/access_token";

    public static final String GOWALLA = "gowalla";
    public static final String GOWALLA_AUTHZ = "https://gowalla.com/api/oauth/authorize";
    public static final String GOWALLA_TOKEN = "https://gowalla.com/api/oauth/access_token";

    public static final String GITHUB = "github";
    public static final String GITHUB_AUTHZ = "https://github.com/login/oauth/authorize";
    public static final String GITHUB_TOKEN = "https://github.com/login/oauth/access_token";

    public static final String SMART_GALLERY = "smart_gallery";
    public static final String SMART_GALLERY_AUTHZ = "http://localhost:8090/oauth/authorize";
    public static final String SMART_GALLERY_TOKEN = "http://localhost:8090/oauth/token";
    public static final String SMART_GALLERY_REGISTER = "http://localhost:8090/oauthreg/register";

    public static void validateRegistrationParams(OAuthRegParams oauthParams) throws ApplicationException {

        String regType = oauthParams.getRegistrationType();

        String name = oauthParams.getName();
        String url = oauthParams.getUrl();
        String description = oauthParams.getDescription();
        StringBuffer sb = new StringBuffer();

        if (isEmpty(url)) {
            sb.append("Application URL ");
        }

        if (REG_TYPE_PUSH.equals(regType)) {
            if (isEmpty(name)) {
                sb.append("Application Name ");
            }

            if (isEmpty(description)) {
                sb.append("Application URL ");
            }
        } else if (!REG_TYPE_PULL.equals(regType)) {
            throw new ApplicationException("Incorrect registration type: " + regType);
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public static void validateAuthorizationParams(OAuthParams oauthParams) throws ApplicationException {


        String authzEndpoint = oauthParams.getAuthzEndpoint();
        String tokenEndpoint = oauthParams.getTokenEndpoint();
        String clientId = oauthParams.getClientId();
        String clientSecret = oauthParams.getClientSecret();
        String redirectUri = oauthParams.getRedirectUri();

        StringBuffer sb = new StringBuffer();

        if (isEmpty(authzEndpoint)) {
            sb.append("Authorization Endpoint ");
        }

        if (isEmpty(tokenEndpoint)) {
            sb.append("Token Endpoint ");
        }

        if (isEmpty(clientId)) {
            sb.append("Client ID ");
        }

        if (isEmpty(clientSecret)) {
            sb.append("Client Secret ");
        }

        if (!REDIRECT_URI.equals(redirectUri)) {
            sb.append("Redirect URI");
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public static void validateTokenParams(OAuthParams oauthParams) throws ApplicationException {

        String authzEndpoint = oauthParams.getAuthzEndpoint();
        String tokenEndpoint = oauthParams.getTokenEndpoint();
        String clientId = oauthParams.getClientId();
        String clientSecret = oauthParams.getClientSecret();
        String redirectUri = oauthParams.getRedirectUri();
        String authzCode = oauthParams.getAuthzCode();

        StringBuffer sb = new StringBuffer();

        if (isEmpty(authzCode)) {
            sb.append("Authorization Code ");
        }

        if (isEmpty(authzEndpoint)) {
            sb.append("Authorization Endpoint ");
        }

        if (isEmpty(tokenEndpoint)) {
            sb.append("Token Endpoint ");
        }

        if (isEmpty(clientId)) {
            sb.append("Client ID ");
        }

        if (isEmpty(clientSecret)) {
            sb.append("Client Secret ");
        }

        if (!REDIRECT_URI.equals(redirectUri)) {
            sb.append("Redirect URI");
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }


    public static String findCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public static String isIssued(String value) {
        if (isEmpty(value)) {
            return "(Not issued)";
        }
        return value;
    }
}
