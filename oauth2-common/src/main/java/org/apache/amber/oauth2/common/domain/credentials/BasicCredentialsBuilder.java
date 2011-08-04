package org.apache.amber.oauth2.common.domain.credentials;

/**
 * @author Maciej Machulak
 */
public class BasicCredentialsBuilder {

    protected BasicCredentials credentials;

    private BasicCredentialsBuilder() {
        credentials = new BasicCredentials();
    }

    public static BasicCredentialsBuilder credentials() {
        return new BasicCredentialsBuilder();
    }

    public BasicCredentials build() {
        return credentials;
    }

    public BasicCredentialsBuilder setClientId(String value) {
        credentials.setClientId(value);
        return this;
    }

    public BasicCredentialsBuilder setClientSecret(String value) {
        credentials.setClientSecret(value);
        return this;
    }

    public BasicCredentialsBuilder setExpiresIn(Long value) {
        credentials.setExpiresIn(value);
        return this;
    }

    public BasicCredentialsBuilder setIssuedAt(Long value) {
        credentials.setIssuedAt(value);
        return this;
    }
}
