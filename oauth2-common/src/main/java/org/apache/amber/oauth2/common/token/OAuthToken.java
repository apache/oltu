package org.apache.amber.oauth2.common.token;

/**
 * @author Lukasz Moren
 */
public interface OAuthToken {

    public String getAccessToken();

    public Long getExpiresIn();

    public String getRefreshToken();

    public String getScope();
}
