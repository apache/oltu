package org.apache.amber.oauth2.common.token;

/**
 * @author Lukasz Moren
 */
public class BasicOAuthToken implements OAuthToken {
    protected String accessToken;
    protected Long expiresIn;
    protected String refreshToken;
    protected String scope;

    public BasicOAuthToken() {
    }

    public BasicOAuthToken(String accessToken, Long expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public BasicOAuthToken(String accessToken) {
        this(accessToken, null, null, null);
    }

    public BasicOAuthToken(String accessToken, Long expiresIn) {
        this(accessToken, expiresIn, null, null);
    }

    public BasicOAuthToken(String accessToken, Long expiresIn, String scope) {
        this(accessToken, expiresIn, null, scope);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }
}
