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
package org.apache.amber;

import org.apache.amber.server.OAuthProvider;
import org.apache.amber.server.OAuthProviders;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ServiceLoader;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

/**
 * <p>
 * The OAuth object provides access to the {@link org.apache.amber.OAuthFactory}.
 * </p>
 * 
 * @version $Revision$ $Date$
 * 
 */
public final class OAuth {

    /**
     * 
     */
    public static final String JAXB_PACKAGE = "org.apache.amber.jaxb.package";

    /**
     * default callback value
     */
    public static final String OUT_OF_BAND = "oob";

    /**
     * oauth-providers.xsd schema
     */
    private static final String PROVIDER_XSD = "/META-INF/oauth-providers.xsd";

    /**
     * oauth-providers.xml filename
     */
    private static final String PROVIDER_XML = "META-INF/oauth-providers.xml";

    /**
     * oauth-properties.xml
     */
    private static final String PROPERTIES_XML = "META-INF/oauth-properties.xml";

    /**
     * Perform initialisation of a factory
     * 
     * @return An instantiated factory
     * @throws OAuthRuntimeException
     */
    public static final OAuthFactory createFactory() throws OAuthRuntimeException {
        return createFactory(Version.v1_0a);
    }

    /**
     * Perform initialisation of a factory
     * 
     * @param properties
     * 
     * @return An instantiated factory
     * @throws OAuthRuntimeException
     */
    public static final OAuthFactory createFactory(Properties properties) throws OAuthRuntimeException {
        return createFactory(Version.v1_0a, properties);
    }

    /**
     * Perform initialisation of a factory
     * 
     * @param version
     * 
     * @return An instantiated factory
     * @throws OAuthRuntimeException
     */
    public static final OAuthFactory createFactory(Version version) throws OAuthRuntimeException {
        Properties properties = new Properties();

        // The getContextClassLoader() method is called inside the other
        // load method anyway, so we might as well expose it here
        // TODO avoid classloader memory leaks?
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            Enumeration<URL> resources = loader.getResources(PROPERTIES_XML);
            while (resources.hasMoreElements()) {
                // TODO specify classloader search order manually?
                // Load the first resource
                URL resource = resources.nextElement();
                properties.loadFromXML(resource.openStream());
            }
        } catch (IOException e) {
            throw new OAuthRuntimeException(e);
        }

        return createFactory(version, properties);
    }

    /**
     * Perform initialisation of a factory
     * 
     * @param version
     * @param properties
     * 
     * @return An instantiated factory
     * @throws OAuthRuntimeException
     */
    public static final OAuthFactory createFactory(Version version, Properties properties) throws OAuthRuntimeException {

        // TODO Can we avoid classloader leaks and related trauma, by:
        // ClassLoader loader = OAuth.class.getClassLoader();

        ServiceLoader<OAuthFactory> factories = ServiceLoader.load(OAuthFactory.class);

        for (OAuthFactory factory : factories) {

            if (!factory.getVersion().equals(version)) {
                continue;
            }

            factory.setProperties(properties);

            // ------------------------------------------------------------------------
            // Load any installed OAuthProvider classes using the ServiceLoader
            // mechanism
            try {
                ServiceLoader<OAuthProvider> providers = ServiceLoader.load(OAuthProvider.class);

                // Is this surplus, or might it help if we're in a modifiable
                // environment?
                providers.reload();

                for (OAuthProvider provider : providers) {
                    factory.register(provider);
                }

                // clean up forcibly
                providers = null;
            }
            catch (Exception e) {
                throw new OAuthRuntimeException(e);
            }

            // ------------------------------------------------------------------------
            // Check for JAXB support in this implementation, and register any
            // OAuthProvider's found
            if (factory.getProperties().containsKey(JAXB_PACKAGE)) {

                // ------------------------------------------------------------------------
                // Load any installed OAuthProviders using the JAXB XML
                // mechanism
                try {

                    // load schema from API resources
                    URL schemaURL = OAuth.class.getResource(PROVIDER_XSD);
                    SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
                    Schema schema = schemaFactory.newSchema(schemaURL);

                    // Use factory implementation class to determine package
                    // String packageName =
                    // factory.getClass().getPackage().getName();

                    String packageName = factory.getProperties().getProperty(JAXB_PACKAGE);

                    // Create unMarshaller for multiple usages
                    JAXBContext context = JAXBContext.newInstance(packageName);
                    Unmarshaller unMarshaller = context.createUnmarshaller();
                    unMarshaller.setSchema(schema);

                    // find multiple instances of the XML configuration file
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    Enumeration<URL> providerXMLs = loader.getResources(PROVIDER_XML);

                    while (providerXMLs.hasMoreElements()) {
                        URL resourceURL = providerXMLs.nextElement();
                        InputStream inputStream = resourceURL.openStream();
                        Source source = new StreamSource(inputStream);

                        try {
                            // Parse the XML file, then loop and register the
                            // providers
                            JAXBElement<? extends OAuthProviders> element = unMarshaller.unmarshal(source, factory.getProviders().getClass());

                            for (OAuthProvider provider : element.getValue().getProvider()) {
                                factory.register(provider);
                            }
                        } catch (JAXBException e) {
                            // TODO warn of error here, in log?
                            e.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    // close quietly
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new OAuthRuntimeException(e);
                } catch (SAXException e) {
                    throw new OAuthRuntimeException(e);
                } catch (JAXBException e) {
                    throw new OAuthRuntimeException(e);
                }
            }

            // ------------------------------------------------------------------------
            // return the completed OAuthFactory

            return factory;
        }

        // clean up services forcibly, who knows what GC will do otherwise.
        factories = null;

        throw new OAuthRuntimeException(OAuthFactory.class.getName() + " implementation not found.");
    }

}
