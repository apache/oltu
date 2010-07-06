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
package org.apache.amber.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @version $Id$
 */
public class AccessTest {

  @Test
  public void matchingEqualsHashTest() {
    Access access1 = new Access(123456, "313121");
    Access access2 = new Access(123456, "313121");
    assertTrue(!access1.equals(null));
    assertTrue(access1.equals(access2) && access1.hashCode() == access2.hashCode());
  }

  @Test
  public void notMatchingEqualsHashTest() {
    Access access1 = new Access(123456, "313121");
    Access access2 = new Access(123446, "313121");
    Access access3 = new Access(123456, "313122");
    Access access4 = new Access(123456, "313121");
    access4.addNonce("313122");
    assertTrue(!access1.equals(access2) && access1.hashCode() != access2.hashCode());
    assertTrue(!access1.equals(access3) && access1.hashCode() != access3.hashCode());
    assertTrue(!access1.equals(access4) && access1.hashCode() != access4.hashCode());
  }

}
