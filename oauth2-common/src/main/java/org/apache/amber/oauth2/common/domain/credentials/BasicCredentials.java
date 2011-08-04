/**
 * Copyright 2011 Newcastle University. All rights reserved.
 * Maciej Machulak, Lukasz Moren, Aad van Moorsel
 *
 * http://research.ncl.ac.uk/smart/
 */

package org.apache.amber.oauth2.common.domain.credentials;

import java.lang.Long;import java.lang.Object;import java.lang.Override;import java.lang.String; /**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public class BasicCredentials implements Credentials {


    private String clientId;
    private String clientSecret;
    private Long issuedAt;
    private Long expiresIn;

    BasicCredentials() {

    }

    public BasicCredentials(String clientId, String clientSecret, Long issuedAt, Long expiresIn) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.issuedAt = issuedAt;
        this.expiresIn = expiresIn;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public Long getIssuedAt() {
        return issuedAt;
    }

    @Override
    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicCredentials that = (BasicCredentials)o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) {
            return false;
        }
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) {
            return false;
        }
        if (expiresIn != null ? !expiresIn.equals(that.expiresIn) : that.expiresIn != null) {
            return false;
        }
        if (issuedAt != null ? !issuedAt.equals(that.issuedAt) : that.issuedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        result = 31 * result + (issuedAt != null ? issuedAt.hashCode() : 0);
        result = 31 * result + (expiresIn != null ? expiresIn.hashCode() : 0);
        return result;
    }
}
