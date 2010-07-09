package org.apache.amber.signature;

import java.util.Set;
import java.util.Map.Entry;

import org.apache.amber.OAuthToken;

final class FakeToken implements OAuthToken {

    /**
     * 
     */
    private static final long serialVersionUID = -4501539974041422272L;

    private String tokenSecret;

    public void addAdditionalParameters(String name, String value) {
        // unneded for test purposes
    }

    public Set<Entry<String, String>> additionalParametersEntrySet() {
        // unneded for test purposes
        return null;
    }

    public String getToken() {
        // unneded for test purposes
        return null;
    }

    public String getTokenSecret() {
        // unneded for test purposes
        return this.tokenSecret;
    }

    public boolean isCallbackConfirmed() {
        // unneded for test purposes
        return false;
    }

    public void setCallbackConfirmed(boolean callbackConfirmed) {
        // unneded for test purposes
    }

    public void setToken(String token) {
        // unneded for test purposes
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

}
