package org.apache.oltu.oauth2.as.validator;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.validators.AbstractValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * Validator that checks for the required fields for an OAuth Token request with the Authorization Code grant type.
 * This validator does NOT enforce client authentication.
 *
 */
public class UnauthenticatedAuthorizationCodeValidator extends AbstractValidator<HttpServletRequest> {

    public UnauthenticatedAuthorizationCodeValidator() {
        requiredParams.add(OAuth.OAUTH_GRANT_TYPE);
        requiredParams.add(OAuth.OAUTH_CLIENT_ID);
        requiredParams.add(OAuth.OAUTH_CODE);

        enforceClientAuthentication = false;
    }
}
