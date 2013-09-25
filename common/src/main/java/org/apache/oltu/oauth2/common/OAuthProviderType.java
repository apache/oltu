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
package org.apache.oltu.oauth2.common;

/**
 * An enumeration of pre-identified and well known OAuth 2 providers, along 
 * with their authorization and token endpoints.
 */
public enum OAuthProviderType {

	FACEBOOK(
			"facebook", 
			"https://graph.facebook.com/oauth/authorize", 
			"https://graph.facebook.com/oauth/access_token"),
	
	FOURSQUARE(
			"foursquare", 
			"https://foursquare.com/oauth2/authenticate", 
			"https://foursquare.com/oauth2/access_token"),
	
	GITHUB(
			"GitHub", 
			"https://github.com/login/oauth/authorize", 
			"https://github.com/login/oauth/access_token"),
	
	GOOGLE(
			"Google", 
			"https://accounts.google.com/o/oauth2/auth", 
			"https://accounts.google.com/o/oauth2/token"),
	
	INSTAGRAM(
			"Instagram", 
			"https://api.instagram.com/oauth/authorize", 
			"https://api.instagram.com/oauth/access_token"),
	
	LINKEDIN(
			"LinkedIn", 
			"https://www.linkedin.com/uas/oauth2/authorization", 
			"https://www.linkedin.com/uas/oauth2/accessToken"),
	
	MICROSOFT(
			"Microsoft", 
			"https://login.live.com/oauth20_authorize.srf", 
			"https://login.live.com/oauth20_token.srf"),
	
	PAYPAL(
			"PayPal", 
			"https://identity.x.com/xidentity/resources/authorize", 
			"https://identity.x.com/xidentity/oauthtokenservice"),
	
	REDDIT(
			"reddit", 
			"https://ssl.reddit.com/api/v1/authorize", 
			"https://ssl.reddit.com/api/v1/access_token"),
	
	SALESFORCE(
			"salesforce", 
			"https://login.salesforce.com/services/oauth2/authorize", 
			"https://login.salesforce.com/services/oauth2/token"),
	
	YAMMER(
			"Yammer", 
			"https://www.yammer.com/dialog/oauth", 
			"https://www.yammer.com/oauth2/access_token.json");
	
	private String providerName;
	
	private String authzEndpoint; 
	
	private String tokenEndpoint;
	
	/**
	 * Get the provider name
	 * 
	 * @return Returns the provider name
	 */
	public String getProviderName() {
		return providerName;
	}
	
	/**
	 * Get the authorization endpoint
	 * 
	 * @return Returns the authorization endpoint
	 */
	public String getAuthzEndpoint() {
		return authzEndpoint;
	}
	
	/**
	 * Get the access token endpoint
	 * 
	 * @return Returns the access token endpoint
	 */
	public String getTokenEndpoint() {
		return tokenEndpoint;
	}
	
	/**
	 * Full private constructor
	 * 
	 * @param providerName The provider name
	 * @param authzEndpoint The authorization endpoint
	 * @param tokenEndpoint The token endpoint
	 */
	private OAuthProviderType(
			final String providerName, 
			final String authzEndpoint, 
			final String tokenEndpoint) {
		
		this.providerName = providerName;
		this.authzEndpoint = authzEndpoint;
		this.tokenEndpoint = tokenEndpoint;
	}
	
}