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
/**
 * <h2>An OAuth Java Specification</h2>
 * 
 * <p>Provides the interfaces for an implementation of the OAuth specification.</p>
 * 
 * <p>The implementation MUST permit entirely programmatical configuration, configuration
 * by the presence of an XML file called &quot;oauth-providers.xml&quot; located in a META-INF 
 * directory on the classpath and by detecting classes configured using the ServiceLoader 
 * mechanism.</p>
 * 
 * @version $Revision$ $Date$
 * @see org.apache.amber.OAuth
 * 
 */
package org.apache.amber;

