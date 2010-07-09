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
package org.apache.amber.signature.hmac;

import org.apache.amber.signature.AbstractSimpleMethodTestCase;
import org.junit.Test;

/**
 * HMAC-SHA1 Method test case implementation.
 *
 * @version $Id$
 */
public class HmacSha1MethodTestCase extends AbstractSimpleMethodTestCase {

    @Test
    public void verifyHmacSha1MethodAlgorithm() throws Exception {
        this.verifySignature(new HmacSha1Method(), "tR3+Ty81lMeYAr/Fid0kMTYa/WM=");
    }

}
