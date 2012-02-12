/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.amber.signature;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.BitSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.amber.OAuthMessageParameter;
import org.apache.amber.OAuthRequest;
import org.apache.amber.OAuthRequestParameter;
import org.apache.amber.OAuthToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract implementation of OAuth signature method algorithm.
 *
 * @version $Id$
 */
public abstract class AbstractMethod implements SignatureMethod {

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
    public final String calculate(SigningKey signingKey,
            OAuthToken token,
            OAuthRequest request) throws SignatureException {
        if (signingKey == null) {
            throw new SignatureException("parameter 'signingKey' must not be null");
        }
        if (request == null) {
            throw new SignatureException("parameter 'request' must not be null");
        }
        this.checkKey(signingKey);

        String baseString = this.createBaseString(request);
        String tokenSecret = extractTokenSecret(token);
        return this.calculate(signingKey, tokenSecret, baseString);
    }

    /**
     * Calculates the signature applying the method algorithm.
     *
     * @param signingKey the key has to be used to sign the request.
     * @param tokenSecret the temporary/token credential.
     * @param baseString the OAuth base string.
     * @return the calculated signature.
     * @throws SignatureException if any error occurs.
     */
    protected abstract String calculate(SigningKey signingKey, String tokenSecret, String baseString) throws SignatureException;

    /**
     * {@inheritDoc}
     */
    public final boolean verify(String signature,
            VerifyingKey verifyingKey,
            OAuthToken token,
            OAuthRequest request) throws SignatureException {
        if (signature == null) {
            throw new SignatureException("parameter 'signature' must not be null");
        }
        if (verifyingKey == null) {
            throw new SignatureException("parameter 'verifyingKey' must not be null");
        }
        if (request == null) {
            throw new SignatureException("parameter 'request' must not be null");
        }
        this.checkKey(verifyingKey);

        String baseString = this.createBaseString(request);
        String tokenSecret = extractTokenSecret(token);
        return this.verify(signature, verifyingKey, tokenSecret, baseString);
    }

    /**
     * Verifies the signature applying the method algorithm.
     *
     * @param signature the OAuth signature has to be verified.
     * @param verifyingKey the key has to be used to verify the request.
     * @param tokenSecret the temporary/token credential.
     * @param baseString the OAuth base string.
     * @return true if the signature is verified, false otherwise.
     * @throws SignatureException if any error occurs.
     */
    protected abstract boolean verify(String signature, VerifyingKey verifyingKey, String tokenSecret, String baseString) throws SignatureException;

    /**
     *
     *
     * @param key
     * @throws SignatureException
     */
    private void checkKey(Key key) throws SignatureException {
        for (String method : key.getAlgorithmMethods()) {
            if (this.getAlgorithm().equals(method)) {
                return;
            }
        }
        throw new SignatureException("Required '"
                + this.getAlgorithm()
                + "', key of type '"
                + key.getClass().getName()
                + "' only supports "
                + Arrays.toString(key.getAlgorithmMethods())
                + " methods");
    }

    /**
     * Calculates the OAuth base string.
     *
     * @param request
     * @return the calculated OAuth base string.
     * @throws SignatureException if any error occurs.
     */
    private String createBaseString(OAuthRequest request) throws SignatureException {
        // the HTTP method
        String method = request.getHTTPMethod().name();

        // the normalized request URL
        URI url = request.getRequestURL();
        String scheme = url.getScheme().toLowerCase();
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

        // parameters normalization
        SortedSet<Entry<String, String>> normalizedParameters = new TreeSet<Entry<String,String>>();

        for (OAuthMessageParameter parameter : request.getOAuthMessageParameters()) {
            if (parameter.getKey().isIncludeInSignature()) {
                encodeAndAddParameter(parameter.getKey().getLabel(), parameter.getValue(), normalizedParameters);
            }
        }

        for (OAuthRequestParameter parameter : request.getOAuthRequestParameters()) {
            if (request.getOAuthMessageParameters().contains(parameter)) {
                throw new SignatureException("Request parameter "
                        + parameter
                        + " can't override an OAuth message one");
            }
            encodeAndAddParameter(parameter.getKey(), parameter.getValue(), normalizedParameters);
        }

        // now serialize the normalized parameters
        StringBuilder normalizedParametersBuffer = new StringBuilder();
        int counter = 0;
        for (Entry<String, String> parameter : normalizedParameters) {
            if (counter > 0) {
                normalizedParametersBuffer.append('&');
            }

            normalizedParametersBuffer.append(parameter.getKey());
            normalizedParametersBuffer.append('=');
            normalizedParametersBuffer.append(parameter.getValue());
            counter++;
        }

        return new StringBuilder(method)
                .append('&')
                .append(percentEncode(requestUrl))
                .append('&')
                .append(percentEncode(normalizedParametersBuffer.toString()))
                .toString();
    }

    private static final String extractTokenSecret(OAuthToken token) {
        if (token == null) {
            return EMPTY;
        }
        return token.getTokenSecret();
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
     * Encodes a bytes sequence applying the Base64 algorithm without chuncking
     * the output string.
     *
     * @param sequence the bytes sequence has to be encoded.
     * @return the Base64 encoded string.
     */
    protected static String encodeBase64(byte[] sequence) {
        return new String(Base64.encodeBase64(sequence, false));
    }

    /**
     * Decodes an input text to a bytes sequence applying the Base64 algorithm.
     *
     * @param text the text has to be encoded.
     * @return the decoded bytes sequence;
     */
    protected static byte[] decodeBase64(String text) {
        return Base64.decodeBase64(text);
    }

    /**
     * Add the input parameter in the list, encoding the parameter name/value
     * first, then putting it in the list in the right position
     *
     * @param parameter the input parameter.
     * @param parametersList the list where add the parameter.
     */
    private static void encodeAndAddParameter(String name, String value, SortedSet<Entry<String, String>> normalizedParameters) {
        normalizedParameters.add(new OAuthRequestParameter(percentEncode(name), percentEncode(value)));
    }

}
