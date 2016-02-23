package org.apache.oltu.oauth2.client.validator;

import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OAuthClientValidatorTest {

    private static final String OAUTH_ERROR_JSON = "{\"error\":\"invalid_client\"}";

    @Test
    public void shouldReturnExceptionWithSpecificResponseCode() throws OAuthProblemException {
        try {
            OAuthClientResponseFactory.createJSONTokenResponse(OAUTH_ERROR_JSON, OAuth.ContentType.JSON, 401);
            fail();
        } catch (OAuthProblemException e) {
            assertEquals(401, e.getResponseStatus());
        }
    }
}