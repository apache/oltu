package org.apache.oltu.oauth2.as.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.validator.PasswordValidator;
import org.apache.oltu.oauth2.as.validator.UnauthenticatedAuthorizationCodeValidator;
import org.apache.oltu.oauth2.as.validator.UnauthenticatedPasswordValidator;
import org.apache.oltu.oauth2.as.validator.UnauthenticatedRefreshTokenValidator;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.common.validators.OAuthValidator;

/**
 * The Unauthenticated OAuth Authorization Server class that validates whether a given HttpServletRequest is a valid
 * OAuth Token request.
 * <p/>
 * This class accepts requests that are NOT authenticated, that is requests that do not contain a client_secret.
 * <p/>
 * IMPORTANT: The ClientCredentials Grant Type is NOT supported by this class since client authentication is required
 * for this grant type. In order to support the client credentials grant type please use the {@link OAuthTokenRequest}
 * class.
 */
public class OAuthUnauthenticatedTokenRequest extends AbstractOAuthTokenRequest {

    public OAuthUnauthenticatedTokenRequest(HttpServletRequest request) throws OAuthSystemException,
            OAuthProblemException {
        super(request);
    }

    @Override
    protected OAuthValidator<HttpServletRequest> initValidator() throws OAuthProblemException, OAuthSystemException {
        validators.put(GrantType.PASSWORD.toString(), UnauthenticatedPasswordValidator.class);
        validators.put(GrantType.AUTHORIZATION_CODE.toString(), UnauthenticatedAuthorizationCodeValidator.class);
        validators.put(GrantType.REFRESH_TOKEN.toString(), UnauthenticatedRefreshTokenValidator.class);
        return super.initValidator();
    }
}
