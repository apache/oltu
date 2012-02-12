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
package org.apache.amber.signature.rsa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.amber.signature.Key;
import org.apache.amber.signature.SignatureException;
import org.apache.commons.ssl.PKCS8Key;

/**
 * Abstract implementation of RSA-SHA1 key.
 *
 * @version $Id$
 */
abstract class AbstractRsaSha1Key implements Key {

    private static final String[] METHODS = { "RSA-SHA1" };

    private final PKCS8Key rsaKey;

    /**
     * Loads a key from a certificate located in the classpath.
     *
     * @param certificateClasspathLocation
     * @param the certificate password
     * @throws SignatureException
     */
    public AbstractRsaSha1Key(String certificateClasspathLocation, String password) throws SignatureException {
        if (certificateClasspathLocation == null) {
            throw new SignatureException("parameter 'certificateClasspathLocation' must not be null");
        }
        if ('/' == certificateClasspathLocation.charAt(0)) {
            certificateClasspathLocation = certificateClasspathLocation.substring(1);
        }

        URL certificateURL = this.getClass().getClassLoader().getResource(certificateClasspathLocation);
        if (certificateURL == null) {
            certificateURL = Thread.currentThread().getContextClassLoader().getResource(certificateClasspathLocation);
        }

        if (certificateURL == null) {
            throw new SignatureException("Classpath resource '"
                    + certificateClasspathLocation
                    + "' not found, please make sure it exists in the classpath");
        }

        this.rsaKey = this.readCertificate(certificateURL, password);
    }

    /**
     * Loads a key from a certificate located in a file.
     *
     * @param certificateFileLocation
     * @param the certificate password
     * @throws SignatureException
     */
    public AbstractRsaSha1Key(File certificateFileLocation, String password) throws SignatureException {
        if (certificateFileLocation == null) {
            throw new SignatureException("parameter 'certificateFileLocation' must not be null");
        }
        if (certificateFileLocation.exists() || certificateFileLocation.isDirectory()) {
            throw new SignatureException("Impossible to load the certificate, '"
                    + certificateFileLocation
                    + "' doesn't exist or is a directory");
        }

        try {
            this.rsaKey = this.readCertificate(certificateFileLocation.toURI().toURL(), password);
        } catch (MalformedURLException e) {
            throw new SignatureException("Impossible to read the certificate from '"
                    + certificateFileLocation
                    + "' file", e);
        }
    }

    /**
     * Loads a key from a certificate located in an URL.
     *
     * @param certificateURL
     * @param the certificate password
     * @throws SignatureException
     */
    public AbstractRsaSha1Key(URL certificateURL, String password) throws SignatureException {
        if (certificateURL == null) {
            throw new SignatureException("parameter 'certificateURL' must not be null");
        }

        this.rsaKey = this.readCertificate(certificateURL, password);
    }

    private PKCS8Key readCertificate(URL certificateURL, String password) throws SignatureException {
        URLConnection urlConnection = null;
        InputStream input = null;

        if (password == null) {
            password = "";
        }

        final char[] pwd = password.toCharArray();

        try {
            urlConnection = certificateURL.openConnection();
            input = urlConnection.getInputStream();

            return new PKCS8Key(input, pwd);
        } catch (Exception e) {
            throw new SignatureException("Impossible to read the certificate from '"
                    + certificateURL
                    + "' URL", e);
        } finally {
            if (urlConnection != null && urlConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlConnection).disconnect();
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // close quietly
                }
            }
        }
    }

    protected PKCS8Key getRsaKey() {
        return rsaKey;
    }

    @Override
    public final String getValue() {
        return String.valueOf(this.rsaKey);
    }

    /**
     * {@inheritDoc}
     */
    public final String[] getAlgorithmMethods() {
        return METHODS;
    }

}
