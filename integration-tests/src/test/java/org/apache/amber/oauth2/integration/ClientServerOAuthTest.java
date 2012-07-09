/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
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

package org.apache.amber.oauth2.integration;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 *
 *
 */
public abstract class ClientServerOAuthTest extends JUnit4SpringContextTests {
    protected static Server s2;
    protected static Server s;

    @BeforeClass
    public static void initService() throws Exception {

        JAXRSServerFactoryBean sf = (JAXRSServerFactoryBean)ctx.getBean("oauthServer");
        s = sf.create();

        JAXRSServerFactoryBean sf2 = (JAXRSServerFactoryBean)ctx.getBean("oauthClient");
        s2 = sf2.create();


    }

    @AfterClass
    public static void stopService() throws Exception {
        if (s != null) {
            s.stop();
        }
        if (s2 != null) {
            s2.stop();
        }
    }


}
