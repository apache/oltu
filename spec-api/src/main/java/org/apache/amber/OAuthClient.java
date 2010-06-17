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
package org.apache.amber;

import java.io.Serializable;

/**
 * <p>
 * An OAuth Service is an abstract representation of the standard remote API
 * offered by an {@link org.apache.amber.OAuthProvider}, providing a simple mechanism
 * for performing {@link org.apache.amber.OAuth} authentication and authorisation
 * tasks.
 * </p>
 * 
 * <h3>Using an OAuthClient</h3>
 * 
 * <p>
 * By far the easiest way to use an OAuthClient is by configuring an
 * {@link org.apache.amber.OAuthProvider} in an XML file (called
 * &quot;oauth-providers.xml&quot;) and placing it in the META-INF directory on
 * the applications classpath.
 * </p>
 * 
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *  &lt;providers&gt;
 *      &lt;provider
 *          realm=&quot;http://example.com&quot;
 *          algorithm=&quot;HmacSHA1&quot;
 *          requestTokenPath=&quot;/oauth/request_token&quot;
 *          authorizePath=&quot;/oauth/authorize&quot;
 *          accessTokenPath=&quot;/oauth/access_token&quot;&gt;
 *          &lt;consumer
 *              name=&quot;mysite.com&quot;
 *              callback=&quot;http://www.mysite.com/oauth/response&quot;
 *              key=&quot;mykey&quot;
 *              secret=&quot;mysecret&quot;/&gt;
 *      &lt;/provider&gt;
 *  &lt;/providers&gt;
 * </pre>
 * 
 * <p>
 * The OAuthClient is called by using it's realm name as a reference. The
 * example below assumes the API is used in a Servlet environment, and simply
 * redirects the user to the {@link org.apache.amber.OAuthProvider} to authorize the
 * token.
 * </p>
 * 
 * <p>
 * The {@link org.apache.amber.OAuthProvider} uses the default
 * {@link org.apache.amber.OAuthConsumer} configured in the XML file.
 * </p>
 * 
 * <pre>
 * HttpSession session = request.getSession();
 * 
 * OAuthClient service = OAuth.useService(&quot;http://example.com&quot;);
 * OAuthToken requestToken = service.getRequestToken(); // This method also takes an OAuthToken implementation class 
 * 
 * session.setAttribute(&quot;requestToken&quot;, requestToken);
 * 
 * String authorizePath = service.getAuthorizeURL(requestToken);
 * response.sendRedirect(authorizePath);
 * </pre>
 * 
 * <p>
 * If you supply your own implementation of the {@link org.apache.amber.OAuthToken}
 * interface when you call the requestToken method, you could store the
 * OAuthToken in a database.
 * </p>
 * 
 * <p>
 * The token is stored in the user session, as we can re-use it when the
 * response comes back. We initialise the service and retrieve the token from
 * the session, before resubmitting the token and the verifier to the
 * {@link org.apache.amber.OAuthProvider}, who converts the authorised Request Token
 * into an Access Token.
 * </p>
 * 
 * <p>
 * After checking that the token is authorised, we remove the old request token
 * from the session and add the access token, so it can be used to sign future
 * requests for services at the {@link org.apache.amber.OAuthProvider}.
 * </p>
 * 
 * <pre>
 * HttpSession session = req.getSession();
 * 
 * OAuthClient service = OAuth.useService(&quot;http://oauth.apache.site/&quot;);
 * OAuthToken requestToken = OAuthToken.class.cast(session
 *         .getAttribute(&quot;requestToken&quot;));
 * 
 * String oauth_token = req.getParameter(&quot;oauth_token&quot;); // you could check this matches the one in the session
 * String oauth_verifier = req.getParameter(&quot;oauth_verifier&quot;); // supplied with the response
 * OAuthToken accessToken = service.getAccessToken(requestToken, oauth_verifier);
 * 
 * if (accessToken.isAuthorized()) {
 *     session.removeAttribute(&quot;requestToken&quot;);
 *     session.setAttribute(&quot;accessToken&quot;, accessToken);
 * }
 * </pre>
 * 
 * <p>
 * The OAuthClient could be initialised in an HttpServlet.init() method and
 * stored in an instance field. Implementations must be thread-safe.
 * </p>
 * 
 * @version $Revision$ $Date$
 * 
 */
public interface OAuthClient extends Serializable {

    /**
     * Get a request token using the default consumer and token
     * 
     * @return token
     * @throws OAuthException
     */
    OAuthToken getRequestToken() throws OAuthException;

    /**
     * Get a request token using the specified consumer
     * 
     * @param consumer
     * @return token
     * @throws OAuthException
     */
    OAuthToken getRequestToken(OAuthConsumer consumer) throws OAuthException;

    /**
     * Get a request token, instantiated from the provided class, using the
     * default consumer
     * 
     * @param tokenClass
     * @return token
     * @throws OAuthException
     */
    OAuthToken getRequestToken(Class<? extends OAuthToken> tokenClass) throws OAuthException;

    /**
     * Get a request token, instantiated from the provided class, using the
     * specified consumer
     * 
     * @param tokenClass
     * @param consumer
     * @return token
     * @throws OAuthException
     */
    OAuthToken getRequestToken(Class<? extends OAuthToken> tokenClass, OAuthConsumer consumer) throws OAuthException;

    /**
     * Get the authentication URL for the configured provider, using the
     * specified token
     * 
     * @param token
     *            The token to be authenticated
     * @return path The path to which the user should be directed for
     *         authentication
     */
    String getAuthenticateURL(OAuthToken token);

    /**
     * Get the authentication URL for the configured provider, using the
     * specified token and callback
     * 
     * @param token
     * @param callback
     *            The URL to which the user should be redirected after
     *            authentication
     * @return path The path to which the user should be directed for
     *         authentication
     */
    String getAuthenticateURL(OAuthToken token, String callback);

    /**
     * Get the authorisation URL for the configured provider, using the
     * specified token
     * 
     * @param token
     *            The token to be authorised
     * @return path The path to which the user should be directed for
     *         authentication
     */
    String getAuthorizeURL(OAuthToken token);

    /**
     * Get the authorisation URL for the configured provider, using the
     * specified token
     * 
     * @param token
     * @param callback
     *            The URL to which the user should be redirected after
     *            authorisation
     * @return path The path to which the user should be directed for
     *         authorisation
     */
    String getAuthorizeURL(OAuthToken token, String callback);

    /**
     * Convert the authorised token to an access token, using the supplied
     * verification code
     * 
     * @param token
     * @param verifier
     * @return authorised token
     * @throws OAuthException
     */
    OAuthToken getAccessToken(OAuthToken token, String verifier) throws OAuthException;

    /**
     * Convert the authorised token to an access token, using the supplied
     * consumer and verification code
     * 
     * @param consumer
     * @param token
     * @param verifier
     * @return authorised token
     * @throws OAuthException
     */
    OAuthToken getAccessToken(OAuthConsumer consumer, OAuthToken token, String verifier) throws OAuthException;

    /**
     * Get the current connector
     * 
     * @return connector
     */
    HttpConnector getConnector();

    /**
     * Set the connector to be used
     * 
     * @param httpConnector
     */
    void setConnector(HttpConnector httpConnector);

}