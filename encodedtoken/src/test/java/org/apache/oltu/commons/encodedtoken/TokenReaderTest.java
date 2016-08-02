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
package org.apache.oltu.commons.encodedtoken;

import org.junit.Assert;
import org.junit.Test;

public class TokenReaderTest {
    
    private TokenReader tokenReader;
    
    @Test
    public void test_read() {
        tokenReader = new TokenReader<String>() {
            protected String build(String rawString, String decodedHeader,
                    String decodedBody, String encodedSignature) {
 
                return "";
            }
        };
        
        String accessToken = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.cC4hiUPoj9Eetdgtv3hF80EGrhuB__dzERat0XF9g2VtQgr9PJbu3XOiZj5RZmh7AAuHIm4Bh-0Qc_lF5YKt_O8W2Fp5jujGbds9uJdbF9CUAr7t1dnZcAcQjbKBYNX4BAynRFdiuB--f_nZLgrnbyTyWzO75vRK5h6xBArLIARNPvkSjtQBMHlb1L07Qe7K0GarZRmB_eSN9383LcOLn6_dO--xi12jzDwusC-eOkHWEsqtFZESc6BfI7noOPqvhJ1phCnvWh6IeYI2w9QOYEUipUTI8np6LbgGY9Fs98rqVt5AXLIhWkWywlVmtVrBp0igcN_IoypGlUPQGe77Rw";
        Assert.assertNotNull(tokenReader.read(accessToken));
    }
    
    @Test
    public void test_read2() {
        tokenReader = new TokenReader<String>() {
            protected String build(String rawString, String decodedHeader,
                    String decodedBody, String encodedSignature) {
                return null;
            }
        };
        String accessToken = "BadToken";
        try {
            tokenReader.read(accessToken);
            Assert.fail("failed test");
        }catch (IllegalArgumentException e) {
            //expected
        }
        
    }
}
