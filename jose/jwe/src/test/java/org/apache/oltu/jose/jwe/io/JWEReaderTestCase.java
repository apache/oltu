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
package org.apache.oltu.jose.jwe.io;

import static org.junit.Assert.assertEquals;
import org.apache.oltu.jose.jwe.JWE;
import org.apache.oltu.jose.jwe.JWEConstants;
import org.junit.Test;

public class JWEReaderTestCase {
    
    private JWEReader reader = new JWEReader();

    @Test
    public void parse() {
        String specJWE = "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0."+
                "6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ."+
                "AxY8DCtDaGlsbGljb3RoZQ."+
                "KDlTtXchhZTGufMYmOYGS4HffxPSUrfmqCHXaI9wOGY."+
                "U0m_YmjN04DJvceFICbCVQ";
        JWE jwe = reader.read(specJWE);

        assertEquals(JWEConstants.A128KW, jwe.getHeader().getAlgorithm());
        assertEquals(JWEConstants.A128CBC_HS256, jwe.getHeader().getEncryptionAlgorithm());
        assertEquals("6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ", jwe.getEncryptedKey());
        assertEquals("AxY8DCtDaGlsbGljb3RoZQ.KDlTtXchhZTGufMYmOYGS4HffxPSUrfmqCHXaI9wOGY.U0m_YmjN04DJvceFICbCVQ", jwe.getContentEncryption());
    }

}
