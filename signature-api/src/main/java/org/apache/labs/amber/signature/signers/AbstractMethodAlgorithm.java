/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.labs.amber.signature.signers;

import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.labs.amber.signature.descriptors.Service;
import org.apache.labs.amber.signature.message.OAuthParameter;
import org.apache.labs.amber.signature.message.RequestMessage;
import org.apache.labs.amber.signature.parameters.Parameter;

/**
 * Abstract implementation of OAuth signature method algorithm.
 *
 * @param <S> the {@link SigningKey} type.
 * @param <V> the {@link VerifyingKey} type.
 */
public abstract class AbstractMethodAlgorithm<S extends SigningKey, V extends VerifyingKey> implements SignatureMethodAlgorithm<S, V> {

    /**
     * HTTP protocol name.
     */
    private static final String HTTP_PROTOCOL = "http";

    /**
     * HTTPS protocol name.
     */
    private static final String HTTPS_PROTOCOL = "https";

    /**
     * URL path separator.
     */
    private static final String PATH_SEPARATOR = "/";

    /**
     * URL scheme separator.
     */
    private static final String SCHEME_SEPARATOR = "://";

    /**
     * The default HTTP port ({@code 80}) constant.
     */
    private static final int DEFAULT_HTTP_PORT = 80;

    /**
     * The default HTTPS port ({@code 443}) constant.
     */
    private static final int DEFAULT_HTTPS_PORT = 443;

    /**
     * The empty string constant.
     */
    private static final String EMPTY = "";

    /**
     * The default {@code UTF-8} character encoding.
     */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * The {@code RFC3986} unreserved chars.
     */
    private static final BitSet UNRESERVED_CHARS = new BitSet(256);

    /**
     * Static unreserved chars bit set initialization.
     */
    static {
        for (byte b = 'A'; b <= 'Z'; b++) {
            UNRESERVED_CHARS.set(b);
        }
        for (byte b = 'a'; b <= 'z'; b++) {
            UNRESERVED_CHARS.set(b);
        }
        for (byte b = '0'; b <= '9'; b++) {
            UNRESERVED_CHARS.set(b);
        }

        // special URL encoding chars
        UNRESERVED_CHARS.set('-');
        UNRESERVED_CHARS.set('.');
        UNRESERVED_CHARS.set('_');
        UNRESERVED_CHARS.set('~');
    }

    /**
     * This class log.
     */
    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Return this class log.
     *
     * @return this class log.
     */
    protected Log getLog() {
        return this.log;
    }

    /**
     * {@inheritDoc}
     */
    public final String calculate(S signingKey, String secretCredential, Service service, RequestMessage message, Parameter...parameterList) throws SignatureException {
        if (signingKey == null) {
            throw new IllegalArgumentException("parameter 'signingKey' must not be null");
        }
        if (secretCredential == null) {
            secretCredential = EMPTY;
        }
        if (service == null) {
            throw new IllegalArgumentException("parameter 'service' must not be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("parameter 'message' must not be null");
        }

        String baseString = this.createBaseString(service, message, parameterList);
        return this.encode(signingKey, secretCredential, baseString);
    }

    /**
     * Calculates the signature applying the method algorithm.
     *
     * @param signingKey the key has to be used to sign the request.
     * @param secretCredential the temporary/token credential.
     * @param baseString the OAuth base string.
     * @return the calculated signature.
     * @throws SignatureException if any error occurs.
     */
    protected abstract String encode(S signingKey, String secretCredential, String baseString) throws SignatureException;

    /**
     * {@inheritDoc}
     */
    public final boolean verify(String signature, V verifyingKey, String secretCredential, Service service, RequestMessage message, Parameter...parameterList) throws SignatureException {
        if (signature == null) {
            throw new IllegalArgumentException("parameter 'signature' must not be null");
        }
        if (verifyingKey == null) {
            throw new IllegalArgumentException("parameter 'verifyingKey' must not be null");
        }
        if (secretCredential == null) {
            secretCredential = EMPTY;
        }
        if (service == null) {
            throw new IllegalArgumentException("parameter 'service' must not be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("parameter 'message' must not be null");
        }

        String baseString = this.createBaseString(service, message, parameterList);
        return this.verify(signature, verifyingKey, secretCredential, baseString);
    }

    /**
     * Verifies the signature applying the method algorithm.
     *
     * @param signature the OAuth signature has to be verified.
     * @param verifyingKey the key has to be used to verify the request.
     * @param secretCredential the temporary/token credential.
     * @param baseString the OAuth base string.
     * @return true if the signature is correct, false otherwise.
     * @throws SignatureException if any error occurs.
     */
    protected abstract boolean verify(String signature, V verifyingKey, String secretCredential, String baseString) throws SignatureException;

    /**
     * Calculates the OAuth base string.
     *
     * @param service the service for which the signature has to be
     *        signed/verified.
     * @param message the (has to be signed) OAuth message.
     * @param parameters the (optional) parameter list the cliend sends to
     *        the OAuth server.
     * @return the calculated OAuth base string.
     * @throws SignatureException if any error occurs.
     */
    private String createBaseString(Service service, RequestMessage message, Parameter... parameters) throws SignatureException {
        // the HTTP method
        String method = service.getHttpMethod().name();

        // the normalized request URL
        URL url = service.getServiceUri();
        String scheme = url.getProtocol().toLowerCase();
        String authority = url.getAuthority().toLowerCase();

        int port = url.getPort();
        if ((HTTP_PROTOCOL.equals(scheme) && DEFAULT_HTTP_PORT == port)
                || (HTTPS_PROTOCOL.equals(scheme) && DEFAULT_HTTPS_PORT == port)) {
            int index = authority.lastIndexOf(':');
            if (index >= 0) {
                authority = authority.substring(0, index);
            }
        }

        String path = url.getPath();
        if (path == null || path.length() <= 0) {
            path = PATH_SEPARATOR; // conforms to RFC 2616 section 3.2.2
        }

        String requestUrl =  new StringBuilder(scheme)
                                .append(SCHEME_SEPARATOR)
                                .append(authority)
                                .append(path)
                                .toString();

        // parameter normalization
        List<Parameter> parametersList = new ArrayList<Parameter>();

        // add the message parameters
        Class<?> klass = message.getClass();
        // traverses the class hierarchy
        // TODO optimize it
        while (Object.class != klass) {
            for (Field field : klass.getDeclaredFields()) {
                if (field.isAnnotationPresent(OAuthParameter.class)) {
                    OAuthParameter oAuthParameter = field.getAnnotation(OAuthParameter.class);
                    if (oAuthParameter.includeInSignature()) {
                        try {
                            Object fieldValue = BeanUtils.getProperty(message, field.getName());

                            if (fieldValue == null && !oAuthParameter.optional()) {
                                throw new SignatureException(new StringBuilder("OAuth parameter '")
                                    .append(oAuthParameter.name())
                                    .append("' specified in '")
                                    .append(field)
                                    .append("' is not optional")
                                    .toString());
                            }

                            encodeAndAddParameter(oAuthParameter.name(), String.valueOf(fieldValue), parametersList);
                        } catch (Exception e) {
                            throw new SignatureException(new StringBuilder("An error occurred while getting '")
                                        .append(field)
                                        .append("' value, see nested exception")
                                        .toString(), e);
                        }
                    }
                }
            }

            klass = klass.getSuperclass();
        }

        // add the user parameters
        for (Parameter parameter : parameters) {
            encodeAndAddParameter(parameter.getName(), parameter.getValue(), parametersList);
        }

        // now serialize the normalized parameters
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < parametersList.size(); i++) {
            if (i > 0) {
                buffer.append('&');
            }

            Parameter parameter = parametersList.get(i);
            buffer.append(parameter.getName());
            buffer.append('=');
            buffer.append(parameter.getValue());
        }

        String normalizedParameters = buffer.toString();

        return new StringBuilder(method)
                .append('&')
                .append(percentEncode(requestUrl))
                .append('&')
                .append(percentEncode(normalizedParameters))
                .toString();
    }

    /**
     * Applies the percent encoding algorithm to the input text.
     *
     * @param text the text has to be encoded.
     * @return the encoded string.
     */
    protected static String percentEncode(String text) {
        return new String(URLCodec.encodeUrl(UNRESERVED_CHARS, toUTF8Bytes(text)), UTF_8);
    }

    /**
     * Converts the input text in a sequence of UTF-8 bytes.
     *
     * @param text the text has to be converted.
     * @return the UTF-8 bytes sequence.
     */
    protected static byte[] toUTF8Bytes(String text) {
        return text.getBytes(UTF_8);
    }

    /**
     * Add the input parameter in the list, encoding the parameter name/value
     * first, then putting it in the list in the right position
     *
     * @param parameter the input parameter.
     * @param parametersList the list where add the parameter.
     */
    private static void encodeAndAddParameter(String name, String value, List<Parameter> parametersList) {
        Parameter parameter = new Parameter(percentEncode(name), percentEncode(value));
        int paramIndex = Collections.binarySearch(parametersList, parameter);
        if (paramIndex < 0) {
            paramIndex = -paramIndex - 1;
        }
        parametersList.add(paramIndex, parameter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        if (this.getClass().isAnnotationPresent(SignatureMethod.class)) {
            return this.getClass().getAnnotation(SignatureMethod.class).value();
        }
        return super.toString();
    }

}
