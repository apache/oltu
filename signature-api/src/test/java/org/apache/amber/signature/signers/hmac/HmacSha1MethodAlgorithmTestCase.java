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
package org.apache.amber.signature.signers.hmac;

import org.apache.amber.signature.signers.AbstractMethodAlgorithmTestCase;
import org.apache.amber.signature.signers.hmac.HmacSha1Key;
import org.apache.amber.signature.signers.hmac.HmacSha1MethodAlgorithm;
import org.junit.Test;

/**
 * HMAC-SHA1 Method test case implementation.
 *
 * @version $Id$
 */
public final class HmacSha1MethodAlgorithmTestCase extends AbstractMethodAlgorithmTestCase<HmacSha1Key, HmacSha1Key> {

    @Test
    public void verifyHmacSha1MethodAlgorithm() throws Exception {
        // the consumer secret
        HmacSha1Key hmacKey = new HmacSha1Key("kd94hf93k423kf44");

        this.verifySignature(hmacKey, hmacKey, new HmacSha1MethodAlgorithm(), "tR3+Ty81lMeYAr/Fid0kMTYa/WM=");
    }

}
