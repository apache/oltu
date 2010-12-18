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

package org.apache.amber.oauth2.integration.server;

import java.net.URISyntaxException;

import org.apache.amber.oauth2.integration.Common;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;


/**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public abstract class AbstractJettyServerTest {
    protected AbstractJettyServerTest() {
    }

    private static org.eclipse.jetty.server.Server server;

    @BeforeClass
    public static void run() throws Exception {

        server = new org.eclipse.jetty.server.Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(Integer.parseInt("9000"));
        server.setConnectors(new Connector[] {connector});

        WebAppContext webappcontext = new WebAppContext();
        String contextPath = null;
        try {
            contextPath = ExampleServlet.class.getResource(Common.TEST_WEBAPP_PATH).toURI().getPath();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        webappcontext.setContextPath("/");

        webappcontext.setWar(contextPath);

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] {webappcontext, new DefaultHandler()});

        server.setHandler(handlers);
        server.start();
    }

    @AfterClass
    public static void stop() throws Exception {

        if (server != null) {
            server.stop();
            server.destroy();
            server = null;
        }
    }
}
