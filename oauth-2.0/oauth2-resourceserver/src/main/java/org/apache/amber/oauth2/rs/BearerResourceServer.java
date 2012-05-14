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
package org.apache.amber.oauth2.rs;

import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.rs.extractor.BearerBodyTokenExtractor;
import org.apache.amber.oauth2.rs.extractor.BearerHeaderTokenExtractor;
import org.apache.amber.oauth2.rs.extractor.BearerQueryTokenExtractor;
import org.apache.amber.oauth2.rs.validator.BearerBodyOAuthValidator;
import org.apache.amber.oauth2.rs.validator.BearerHeaderOAuthValidator;
import org.apache.amber.oauth2.rs.validator.BearerQueryOAuthValidator;

public class BearerResourceServer extends ResourceServer {

    public BearerResourceServer() {
        extractors.put(ParameterStyle.HEADER, BearerHeaderTokenExtractor.class);
        extractors.put(ParameterStyle.BODY, BearerBodyTokenExtractor.class);
        extractors.put(ParameterStyle.QUERY, BearerQueryTokenExtractor.class);

        validators.put(ParameterStyle.HEADER, BearerHeaderOAuthValidator.class);
        validators.put(ParameterStyle.BODY, BearerBodyOAuthValidator.class);
        validators.put(ParameterStyle.QUERY, BearerQueryOAuthValidator.class);
    }

}
