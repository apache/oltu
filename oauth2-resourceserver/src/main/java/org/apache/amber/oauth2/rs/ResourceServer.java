package org.apache.amber.oauth2.rs;

import java.util.HashMap;
import java.util.Map;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.common.validators.OAuthValidator;
import org.apache.amber.oauth2.rs.extractor.TokenExtractor;

public abstract class ResourceServer {

    protected Map<ParameterStyle, Class> extractors = new HashMap<ParameterStyle, Class>();
    protected Map<ParameterStyle, Class> validators = new HashMap<ParameterStyle, Class>();
    
    public OAuthValidator instantiateValidator(ParameterStyle ps) throws OAuthSystemException {
        Class clazz = validators.get(ps);
        if (clazz == null) {
            throw new OAuthSystemException("Cannot instantiate a message validator.");
        }
        return (OAuthValidator)OAuthUtils.instantiateClass(clazz);
    }

    public TokenExtractor instantiateExtractor(ParameterStyle ps) throws OAuthSystemException {
        Class clazz = extractors.get(ps);
        if (clazz == null) {
            throw new OAuthSystemException("Cannot instantiate a token extractor.");
        }
        return (TokenExtractor)OAuthUtils.instantiateClass(clazz);
    }
 }
