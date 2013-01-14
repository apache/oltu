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
package org.apache.amber.oauth2.client.response;

import org.apache.amber.oauth2.common.exception.OAuthProblemException;

public class OAuthResourceResponse  extends OAuthClientResponse {
	
    public String getBody() {
        return body;
    }	
 
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getContentType(){
		return contentType;
	}
    
	@Override
	protected void setBody(String body) throws OAuthProblemException {
		 this.body = body;
	}

	@Override
	protected void setContentType(String contentType) {
		this.contentType = contentType;	
	}

	@Override
	protected void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	@Override
	protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
	}

}
