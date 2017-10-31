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

package org.apache.oltu.oauth2.rsfilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 *
 *
 */
public class OAuthUtils {

    public static <T> T initiateServletContext(FilterConfig config, String key, Class<T> expectedClass)
        throws ServletException {

        T provider = (T) config.getServletContext().getAttribute(key);

        if (provider != null) {
            return provider;
        }

        provider = (T) loadObject(config, key, expectedClass);

        // set the provider and validator
        config.getServletContext().setAttribute(key, provider);

        return provider;
    }

    public static <T> T loadObject(FilterConfig config, String classParamName, Class<T> expectedClass)
        throws ServletException {

        T ob = null;

        String providerClassName = config.getInitParameter(classParamName);
        if (isEmpty(providerClassName)) {
            throw new ServletException(classParamName + " context param required");
        }
        try {
            Class<T> clazz = (Class<T>) Class.forName(providerClassName);
            if (!expectedClass.isAssignableFrom(clazz)) {
                throw new ServletException(classParamName + " class: " + providerClassName
                    + " must be an instance of: " + expectedClass.getName());
            }
            ob = createObjectFromClassName(clazz);
        } catch (ClassNotFoundException e) {
            throw new ServletException(classParamName + " class " + providerClassName + " not found");
        } catch (Exception e) {
            throw new ServletException("Cannot instantiate: " + providerClassName);
        }
        return ob;
    }

    public static <T> T createObjectFromClassName(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
