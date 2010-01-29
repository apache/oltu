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
package org.apache.labs.amber.signature.message;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * BeanInfo that exposes class properties with aliases.
 *
 * @version $Id$
 */
abstract class AbstractMessageBeanInfo extends SimpleBeanInfo {

    /**
     * Maintains the original descriptors and the alias descriptor.
     */
    private final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();

    /**
     * Scans the class hierarchy and associates the OAuth param name.
     *
     * @param klass the class is currently analyzed.
     */
    public AbstractMessageBeanInfo(Class<?> klass) {
        this.visit(klass);
    }

    /**
     * Scans the class hierarchy and associates the OAuth param name.
     *
     * @param klass the class is currently analyzed.
     */
    private void visit(Class<?> klass) {
        if (Object.class == klass) {
            return;
        }

        for (Field field : klass.getDeclaredFields()) {
            try {
                PropertyDescriptor originalDescriptor = new PropertyDescriptor(field.getName(), klass);
                this.descriptors.add(originalDescriptor);

                if (field.isAnnotationPresent(OAuthParameter.class)) {
                    OAuthParameter oAuthParameter = field.getAnnotation(OAuthParameter.class);
                    PropertyDescriptor aliasDescriptor = new PropertyDescriptor(oAuthParameter.name(),
                                originalDescriptor.getReadMethod(),
                                originalDescriptor.getWriteMethod());
                    this.descriptors.add(aliasDescriptor);
                }
            } catch (IntrospectionException e) {
                // TODO can this exception be ignored?
            }
        }

        this.visit(klass.getSuperclass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return this.descriptors.toArray(new PropertyDescriptor[this.descriptors.size()]);
    }

}
