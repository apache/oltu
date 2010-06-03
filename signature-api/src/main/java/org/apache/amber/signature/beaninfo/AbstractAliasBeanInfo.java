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
package org.apache.amber.signature.beaninfo;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;

/**
 * Abstract BeanInfo implementation that exposes class properties with aliases.
 *
 * @version $Id$
 */
public abstract class AbstractAliasBeanInfo extends SimpleBeanInfo {

    /**
     * The default property name that contains the alias.
     */
    private static final String VALUE = "value";

    /**
     * Maintains the original descriptors and the alias descriptor.
     */
    private final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();

    /**
     * Scans the class hierarchy and associates the property field to alias.
     *
     * @param klass the class is currently analyzed.
     * @param annotationType the annotation type that contains the alias name.
     */
    public AbstractAliasBeanInfo(Class<?> klass,
            Class<? extends Annotation> annotationType) {
        this(klass, annotationType, VALUE);
    }

    /**
     * Scans the class hierarchy and associates the property field to alias.
     *
     * @param klass the class is currently analyzed.
     * @param annotationType the annotation type that contains the alias name.
     * @param aliasPropertyName the annotation property name that contains the
     *        property alias.
     */
    public AbstractAliasBeanInfo(Class<?> klass,
            Class<? extends Annotation> annotationType,
            String aliasPropertyName) {
        this.visit(klass, annotationType, aliasPropertyName);
    }

    /**
     * Scans the class hierarchy and associates the property field to alias.
     *
     * @param klass the class is currently analyzed.
     * @param annotationType the annotation type that contains the alias name.
     * @param aliasPropertyName the annotation property name that contains the
     *        property alias.
     */
    private void visit(Class<?> klass,
            Class<? extends Annotation> annotationType,
            String aliasPropertyName) {
        if (Object.class == klass) {
            return;
        }

        for (Field field : klass.getDeclaredFields()) {
            try {
                PropertyDescriptor originalDescriptor = new PropertyDescriptor(field.getName(), klass);
                this.descriptors.add(originalDescriptor);

                if (field.isAnnotationPresent(annotationType)) {
                    Annotation annotation = field.getAnnotation(annotationType);
                    Object alias = MethodUtils.invokeExactMethod(annotation, aliasPropertyName, null);

                    if (alias == null) {
                        throw new IllegalArgumentException("'"
                                + aliasPropertyName
                                + "' on "
                                + annotation
                                + " must not be null");
                    }

                    if (String.class != alias.getClass()) {
                        throw new IllegalArgumentException("'"
                                + aliasPropertyName
                                + "' on "
                                + annotation
                                + " is not a string");
                    }

                    PropertyDescriptor aliasDescriptor = new PropertyDescriptor(String.valueOf(alias),
                                originalDescriptor.getReadMethod(),
                                originalDescriptor.getWriteMethod());
                    this.descriptors.add(aliasDescriptor);
                }
            } catch (Exception e) {
                // TODO can this exception be ignored?
            }
        }

        this.visit(klass.getSuperclass(), annotationType, aliasPropertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return this.descriptors.toArray(new PropertyDescriptor[this.descriptors.size()]);
    }

}
