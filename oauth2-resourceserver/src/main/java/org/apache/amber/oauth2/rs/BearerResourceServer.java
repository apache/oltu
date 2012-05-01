package org.apache.amber.oauth2.rs;

import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.rs.extractor.BearerBodyTokenExtractor;
import org.apache.amber.oauth2.rs.extractor.BearerHeaderTokenExtractor;
import org.apache.amber.oauth2.rs.extractor.BearerQueryTokenExtractor;
import org.apache.amber.oauth2.rs.validator.BearerBodyOAuthValidator;
import org.apache.amber.oauth2.rs.validator.BearerHeaderOAuthValidator;
import org.apache.amber.oauth2.rs.validator.BearerQueryOAuthValidator;

public class BearerResourceServer extends ResourceServer {

    public BearerResourceServer() {
        extractors.put(ParameterStyle.HEADER, BearerHeaderTokenExtractor.class);
        extractors.put(ParameterStyle.BODY, BearerBodyTokenExtractor.class);
        extractors.put(ParameterStyle.QUERY, BearerQueryTokenExtractor.class);

        validators.put(ParameterStyle.HEADER, BearerHeaderOAuthValidator.class);
        validators.put(ParameterStyle.BODY, BearerBodyOAuthValidator.class);
        validators.put(ParameterStyle.QUERY, BearerQueryOAuthValidator.class);
    }

}
