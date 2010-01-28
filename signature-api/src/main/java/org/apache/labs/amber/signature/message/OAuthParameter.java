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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker for request message fields as OAuth parameter.
 *
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OAuthParameter {

    /**
     * The OAuth parameter name.
     *
     * @return the OAuth parameter name.
     */
    String name();

    /**
     * Flag to mark an OAuth parameter optional or not.
     *
     * @return false by default, user specified otherwise.
     */
    boolean optional() default false;

    /**
     * Flag to mark an OAuth parameter has to be included in the signature.
     *
     * @return true by default, user specified otherwise.
     */
    boolean includeInSignature() default true;

}
