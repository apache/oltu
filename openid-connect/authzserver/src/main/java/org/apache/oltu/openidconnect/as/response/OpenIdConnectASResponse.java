package org.apache.oltu.openidconnect.as.response;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.openidconnect.common.OpenIdConnect;


public class OpenIdConnectASResponse extends OAuthResponse {
    protected OpenIdConnectASResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }


    public static OpenIdConnectAuthorizationResponseBuilder authorizationResponse(HttpServletRequest request,int code) {
        return new OpenIdConnectAuthorizationResponseBuilder(request,code);
    }

    public static OpenIdConnectTokenResponseBuilder tokenResponse(int code) {
        return new OpenIdConnectTokenResponseBuilder(code);
    }

    public static class OpenIdConnectAuthorizationResponseBuilder extends OAuthResponseBuilder {

        public OpenIdConnectAuthorizationResponseBuilder(HttpServletRequest request,int responseCode) {
            super(responseCode);
            //AMBER-45
            String state=request.getParameter(OAuth.OAUTH_STATE);
            if (state!=null){
                this.setState(state);
            }
        }

        OpenIdConnectAuthorizationResponseBuilder setState(String state) {
            this.parameters.put(OAuth.OAUTH_STATE, state);
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setCode(String code) {
            this.parameters.put(OAuth.OAUTH_CODE, code);
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setAccessToken(String token) {
            this.parameters.put(OAuth.OAUTH_ACCESS_TOKEN, token);
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setTokenType(String tokenType) {
            this.parameters.put(OAuth.OAUTH_TOKEN_TYPE, tokenType);
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setExpiresIn(String expiresIn) {
            this.parameters.put(OAuth.OAUTH_EXPIRES_IN, expiresIn == null ? null : Long.valueOf(expiresIn));
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setExpiresIn(Long expiresIn) {
            this.parameters.put(OAuth.OAUTH_EXPIRES_IN, expiresIn);
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setIdToken(String idtoken) {
            this.parameters.put(OpenIdConnect.OPENIDCONNECT_ID_TOKEN, idtoken);
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder location(String location) {
            this.location = location;
            return this;
        }

        public OpenIdConnectAuthorizationResponseBuilder setParam(String key, String value) {
            this.parameters.put(key, value);
            return this;
        }
    }


    public static class OpenIdConnectTokenResponseBuilder extends OAuthResponseBuilder {

        public OpenIdConnectTokenResponseBuilder(int responseCode) {
            super(responseCode);
        }

        public OpenIdConnectTokenResponseBuilder setAccessToken(String token) {
            this.parameters.put(OAuth.OAUTH_ACCESS_TOKEN, token);
            return this;
        }

        public OpenIdConnectTokenResponseBuilder setExpiresIn(String expiresIn) {
            this.parameters.put(OAuth.OAUTH_EXPIRES_IN, expiresIn == null ? null : Long.valueOf(expiresIn));
            return this;
        }

        public OpenIdConnectTokenResponseBuilder setRefreshToken(String refreshToken) {
            this.parameters.put(OAuth.OAUTH_REFRESH_TOKEN, refreshToken);
            return this;
        }

        public OpenIdConnectTokenResponseBuilder setTokenType(String tokenType) {
            this.parameters.put(OAuth.OAUTH_TOKEN_TYPE, tokenType);
            return this;
        }
        public OpenIdConnectTokenResponseBuilder setIdToken(String idtoken) {
            this.parameters.put(OpenIdConnect.OPENIDCONNECT_ID_TOKEN, idtoken);
            return this;
        }

        public OpenIdConnectTokenResponseBuilder setParam(String key, String value) {
            this.parameters.put(key, value);
            return this;
        }

        public OpenIdConnectTokenResponseBuilder location(String location) {
            this.location = location;
            return this;
        }
    }
}
