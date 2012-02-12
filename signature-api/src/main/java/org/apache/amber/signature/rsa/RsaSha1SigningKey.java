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
import java.security.PrivateKey;

import org.apache.amber.signature.SignatureException;
import org.apache.amber.signature.SigningKey;

/**
 *
 *
 * @version $Id$
 */
public final class RsaSha1SigningKey
        extends AbstractRsaSha1Key
        implements SigningKey {

    public RsaSha1SigningKey(File certificateFileLocation, String password)
            throws SignatureException {
        super(certificateFileLocation, password);
    }

    public RsaSha1SigningKey(String certificateClasspathLocation, String password)
            throws SignatureException {
        super(certificateClasspathLocation, password);
    }

    public RsaSha1SigningKey(URL certificateURL, String password) throws SignatureException {
        super(certificateURL, password);
    }

    public PrivateKey getPrivateKey() {
        return getRsaKey().getPrivateKey();
    }

}
