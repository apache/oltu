package org.apache.amber.oauth2.common.token;

/**
 * @author Lukasz Moren
 */
public interface OAuthToken {

    public String getAccessToken();

    public String getExpiresIn();

    public String getRefreshToken();

    public String getScope();
}
