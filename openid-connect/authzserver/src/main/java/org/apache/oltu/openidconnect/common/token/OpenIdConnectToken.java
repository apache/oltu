package org.apache.oltu.openidconnect.common.token;

import org.apache.oltu.oauth2.common.token.OAuthToken;


public interface OpenIdConnectToken  {

    String getiss();
    String getSub();
    String getAud();
    long getExp();
    long getIat();
    long getAuth_time();
    String getNonce();
    String getAcr();
    String getAmr();
    String getAzp();
}
