# Apache Oltu OAuth 2.0 Client and Provider

--------------------

The OAuth 2.0 client and identity provider are built based on open-source Apache Oltu OAuth 2.0 source repository which only consists of OAuth 2.0 client demo application. 

The OAuth 2.0 identity provider demo application has been developed to illustrate the OAuth 2.0 authorization flow and provide instructions on how to develop a standalone OAuth 2.0 server using Apache Oltu OAuth 2.0 library.

Installation
------------
+ Download the source repository from GitHub.
```
git clone https://github.com/winstonhong/oltu
``` 
+ Build OAuth 2.0 library from Apache Oltu source code.
```
cd oltu
mvn clean
mvn package
```
+ Run OAuth 2.0 client demo application
```
cd oltu/demos/client-demo/
mvn jetty:run
```
+ Run OAuth 2.0 identity provider demo application
```
cd oltu/demos/provider-demo/
mvn jetty:run
```

OAuth 2.0 Authorization Demo
------------
+ Access the link "http://localhost:8080" to launch the OAuth 2.0 authorization demo
+ Click **Generic OAuth2 Application**
+ Input **End-User Authorization URL** : http://localhost:9001/auth/oauth2/authz , <br>
input **Token Endpoint** : http://localhost:9001/auth/oauth2/token , <br>
input **Client ID** : clinet_id , <br>
input **Client Secret** : clinet_secret , <br>
and then click **Get Authorization**
+ Input **Username/Password** : username/password , <br>
and then click **Login**
+ Click **Grant permission**
+ Click **Get Token**
+ Input **Resource URL** : http://localhost:9001/auth/oauth2/resource_server/resource_query ,<br>
select **queryParameter** from the drop-down list of **Authenticated Request Type**, <br>
and then click **Get Resource** to retrieve User Info.


References
-------
Apache Oltu https://oltu.apache.org/

OAuthh 2.0 libraries https://oauth.net/code/

Support
-------
OAuth 2.0 identity provider developed by [winstonhong](https://github.com/winstonhong) @ [inbaytech](https://github.com/inbaytech)
