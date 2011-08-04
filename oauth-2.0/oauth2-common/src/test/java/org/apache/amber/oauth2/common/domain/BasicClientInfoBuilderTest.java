package org.apache.amber.oauth2.common.domain;

import org.apache.amber.oauth2.common.domain.client.BasicClientInfo;
import org.apache.amber.oauth2.common.domain.client.BasicClientInfoBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Lukasz Moren
 */
public class BasicClientInfoBuilderTest {

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String NAME = "name";
    public static final String ICON_URI = "icon_uri";
    public static final Long EXPIRES_IN = 1L;
    public static final Long ISSUED_AT = 2L;

    @Test
    public void testNewClientInfo() throws Exception {

        BasicClientInfo basicClientInfo = BasicClientInfoBuilder.clientInfo()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRedirectUri(REDIRECT_URI)
            .setName(NAME)
            .setIconUri(ICON_URI)
            .setExpiresIn(EXPIRES_IN)
            .setIssuedAt(ISSUED_AT)
            .build();

        Assert.assertNotNull(basicClientInfo);

        Assert.assertEquals(CLIENT_ID, basicClientInfo.getClientId());
        Assert.assertEquals(CLIENT_SECRET, basicClientInfo.getClientSecret());
        Assert.assertEquals(REDIRECT_URI, basicClientInfo.getRedirectUri());
        Assert.assertEquals(NAME, basicClientInfo.getName());
        Assert.assertEquals(ICON_URI, basicClientInfo.getIconUri());
        Assert.assertEquals(EXPIRES_IN, basicClientInfo.getExpiresIn());
        Assert.assertEquals(ISSUED_AT, basicClientInfo.getIssuedAt());
    }
}
