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
package org.apache.oltu.oauth2.jwt;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

/**
 * This class contains utility methods required for the JWT building and
 * processing.
 * 
 */
public class JWTUtil {     
	
	
	
	/**
	 * Get the Header as defined in the 6.1 section of the JWT
	 * specification (http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1)
	 * 
	 * @param base64jsonString
	 * @return the decoded JWT header
	 */
	public static  String getHeader(String base64jsonString){
		return decodeJSON(base64jsonString.split("\\.")[0]);		
	}
	
	/**
	 * Get the Claims Set as defined in the 6.1 section of the JWT
	 * specification (http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-06#section-6.1)
	 * 
	 * @param base64jsonString
	 * @return the decoded JWT claim set
	 */
	public static  String getClaimsSet(String base64jsonString){
		return decodeJSON(base64jsonString.split("\\.")[1]);		
	}
	
	private static String decodeJSON(String base64jsonString){
		return new String(new Base64(true).decode(base64jsonString),Charset.forName("UTF-8"));
	}
}
