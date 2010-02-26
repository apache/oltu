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
package org.apache.labs.amber.signature.signers.rsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.labs.amber.signature.descriptors.HTTPMethod;
import org.apache.labs.amber.signature.signers.Key;
import org.apache.labs.amber.signature.signers.SignatureException;

/**
 * Abstract implementation of a RSA key.
 *
 * @version $Id$
 */
abstract class AbstractRsaSha1Key implements Key {

    /**
     * The RSA certificate string representation.
     */
    private final String value;

    /**
     * The certificate URL location.
     */
    private final URL certificateLocation;

    /**
     * Instantiate a new RSA key reading the certificate from the URL.
     *
     * @param certificateLocation the certificate from the URL.
     * @throws SignatureException if any error occurs while reading the
     *         certificate.
     */
    public AbstractRsaSha1Key(URL certificateLocation) throws SignatureException {
        if (certificateLocation == null) {
            throw new IllegalArgumentException("Impossible to read a certificate from a null URL");
        }

        URLConnection urlConnection = null;
        InputStream input = null;

        try {
            urlConnection = certificateLocation.openConnection();
            if (urlConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlConnection).setRequestMethod(HTTPMethod.GET.name());
            }

            input = urlConnection.getInputStream();

            byte[] bufferedValue = this.readCertificate(input);
            this.value = new String(bufferedValue);

            this.init(bufferedValue);

            this.certificateLocation = certificateLocation;
        } catch (Exception e) {
            throw new SignatureException("An error occurred while reading RSA cerificate '"
                    + certificateLocation
                    + "', see nested exceptions", e);
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

    /**
     * {@inheritDoc}
     */
    public final String getValue() {
        return this.value;
    }

    /**
     * Initializes the key after reading the RSA certificate.
     *
     * @param bufferedValue the buffered RSA certificate.
     * @throws SignatureException if any error occurs while reading the
     *         certificate.
     */
    protected abstract void init(byte[] bufferedValue) throws SignatureException;

    /**
     * Read the certificate and store the value into a returned buffer.
     *
     * Subclasses that override this method don't have to take care about
     * closing the input stream.
     *
     * @param input the certificate input stream.
     * @return the certificate value stored in he buffer.
     * @throws SignatureException if any error occurs while reading the
     *         certificate.
     */
    protected byte[] readCertificate(InputStream input) throws SignatureException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int i;
        try {
            while ((i = input.read()) != -1) {
                baos.write(i);
            }

            baos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new SignatureException("Fatal error while reading public certificate", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.certificateLocation.toString();
    }

}
