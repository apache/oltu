package org.apache.oltu.oauth2.common.validators;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.common.validators.OAuthValidator;


public class OAuthValidatorMixer implements OAuthValidator<HttpServletRequest> {


    private final Collection<OAuthValidator<HttpServletRequest>> valids;

    public OAuthValidatorMixer(Collection<Class<? extends OAuthValidator<HttpServletRequest>>> valids) throws OAuthSystemException {
        this.valids=transform(valids);
    }

    private Collection<OAuthValidator<HttpServletRequest>> transform(Collection<Class<? extends OAuthValidator<HttpServletRequest>>> valids) throws OAuthSystemException {
        Collection<OAuthValidator<HttpServletRequest>> newList=new ArrayList<OAuthValidator<HttpServletRequest>>();
        for(Class<? extends OAuthValidator<HttpServletRequest>> classez:valids){
            newList.add(OAuthUtils.instantiateClass(classez));
        }
        return newList;
    }

    public void validateMethod(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.validateMethod(request);
        }
    }

    public void validateContentType(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.validateContentType(request);
        }
    }

    public void validateRequiredParameters(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.validateRequiredParameters(request);
        }
    }

    public void validateOptionalParameters(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.validateOptionalParameters(request);
        }
    }

    public void validateNotAllowedParameters(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.validateNotAllowedParameters(request);
        }
    }

    public void validateClientAuthenticationCredentials(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.validateClientAuthenticationCredentials(request);
        }
    }

    public void performAllValidations(HttpServletRequest request) throws OAuthProblemException {
        for(OAuthValidator<HttpServletRequest> validator:valids){
            validator.performAllValidations(request);
        }
    }
}
