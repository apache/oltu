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
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

import org.apache.amber.signature.SignatureException;
import org.apache.amber.signature.VerifyingKey;

/**
 *
 *
 * @version $Id$
 */
public final class RsaSha1VerifyingKey
        extends AbstractRsaSha1Key
        implements VerifyingKey {

    public RsaSha1VerifyingKey(File certificateFileLocation, String password)
            throws SignatureException {
        super(certificateFileLocation, password);
    }

    public RsaSha1VerifyingKey(String certificateClasspathLocation, String password)
            throws SignatureException {
        super(certificateClasspathLocation, password);
    }

    public RsaSha1VerifyingKey(URL certificateURL, String password) throws SignatureException {
        super(certificateURL, password);
    }

    public PublicKey getPublicKey() throws GeneralSecurityException {
        return getRsaKey().getPublicKey();
    }

}
