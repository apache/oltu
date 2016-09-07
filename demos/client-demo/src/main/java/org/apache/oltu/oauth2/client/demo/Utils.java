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

package org.apache.oltu.oauth2.client.demo;

import org.apache.oltu.oauth2.client.demo.exception.ApplicationException;
import org.apache.oltu.oauth2.client.demo.model.OAuthParams;
import org.apache.oltu.oauth2.client.demo.model.OAuthRegParams;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public final class Utils {

    public Utils() {
    }

    private String redirectUri;

    @Autowired
    private void setServletContext(ServletContext ctx) {
        String contextPath = ctx.getContextPath();
        redirectUri = "http://localhost:8080" + contextPath + "/redirect";
    }

    public static final String DISCOVERY_URI = "http://localhost:8080";

    public static final String REG_TYPE_PULL = "pull";
    public static final String REG_TYPE_PUSH = "push";

    public static final String REQUEST_TYPE_QUERY = "queryParameter";
    public static final String REQUEST_TYPE_HEADER = "headerField";
    public static final String REQUEST_TYPE_BODY = "bodyParameter";

    public static final String GENERIC = "generic";

    public static final String FACEBOOK = OAuthProviderType.FACEBOOK.getProviderName();
    public static final String FACEBOOK_AUTHZ = OAuthProviderType.FACEBOOK.getAuthzEndpoint();
    public static final String FACEBOOK_TOKEN = OAuthProviderType.FACEBOOK.getTokenEndpoint();
    public static final String FACEBOOK_SCOPE = "public_profile";
    public static final String FACEBOOK_RESOURCE_URL = "https://graph.facebook.com/me";

    public static final String GOOGLE = OAuthProviderType.GOOGLE.getProviderName();
    public static final String GOOGLE_AUTHZ = OAuthProviderType.GOOGLE.getAuthzEndpoint();
    public static final String GOOGLE_TOKEN = OAuthProviderType.GOOGLE.getTokenEndpoint();
    public static final String GOOGLE_SCOPE = "openid profile";
    public static final String GOOGLE_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    public static final String LINKEDIN = OAuthProviderType.LINKEDIN.getProviderName();
    public static final String LINKEDIN_AUTHZ = OAuthProviderType.LINKEDIN.getAuthzEndpoint();
    public static final String LINKEDIN_TOKEN = OAuthProviderType.LINKEDIN.getTokenEndpoint();
    public static final String LINKEDIN_SCOPE = "r_basicprofile";
    public static final String LINKEDIN_RESOURCE_URL = "https://www.linkedin.com/v1/people/~";

    public static final String GITHUB = OAuthProviderType.GITHUB.getProviderName();
    public static final String GITHUB_AUTHZ = OAuthProviderType.GITHUB.getAuthzEndpoint();
    public static final String GITHUB_TOKEN = OAuthProviderType.GITHUB.getTokenEndpoint();
    public static final String GITHUB_SCOPE = "";
    public static final String GITHUB_RESOURCE_URL = "https://api.github.com/user";
    
    public static final String MICROSOFT = OAuthProviderType.MICROSOFT.getProviderName();
    public static final String MICROSOFT_AUTHZ = OAuthProviderType.MICROSOFT.getAuthzEndpoint();
    public static final String MICROSOFT_TOKEN = OAuthProviderType.MICROSOFT.getTokenEndpoint();
    public static final String MICROSOFT_SCOPE = "";
    public static final String MICROSOFT_RESOURCE_URL = "";
    
    public static final String INSTAGRAM = OAuthProviderType.INSTAGRAM.getProviderName();
    public static final String INSTAGRAM_AUTHZ = OAuthProviderType.INSTAGRAM.getAuthzEndpoint();
    public static final String INSTAGRAM_TOKEN = OAuthProviderType.INSTAGRAM.getTokenEndpoint();
    public static final String INSTAGRAM_SCOPE = "";
    public static final String INSTAGRAM_RESOURCE_URL = "";

    public static final String SMART_GALLERY = "smart_gallery";
    public static final String SMART_GALLERY_AUTHZ = "http://localhost:8090/oauth/authorize";
    public static final String SMART_GALLERY_TOKEN = "http://localhost:8090/oauth/token";
    public static final String SMART_GALLERY_REGISTER = "http://localhost:8090/oauthreg/register";

    public String getRedirectUri() {
        return redirectUri;
    }

    public void validateRegistrationParams(OAuthRegParams oauthParams) throws ApplicationException {

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

    public void validateAuthorizationParams(OAuthParams oauthParams) throws ApplicationException {
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

        if (!getRedirectUri().equals(redirectUri)) {
            sb.append("Redirect URI");
        }

        String incorrectParams = sb.toString();
        if ("".equals(incorrectParams)) {
            return;
        }
        throw new ApplicationException("Incorrect parameters: " + incorrectParams);

    }

    public void validateTokenParams(OAuthParams oauthParams) throws ApplicationException {

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

        if (!getRedirectUri().equals(redirectUri)) {
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
