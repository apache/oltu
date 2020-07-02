/**
 *		 Author : Yang Hong
 *
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

package org.apache.oltu.oauth2.provider.demo;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Login extends HttpServlet { 
	protected void doGet(HttpServletRequest request, 
		HttpServletResponse response) throws ServletException, IOException 
	{
		// reading the user input
		String username = request.getParameter("username");    
		String password = request.getParameter("password");  
		PrintWriter out = response.getWriter();
		out.println (
			"<!DOCTYPE html PUBLIC\""+"-//W3C//DTD HTML 4.01 Transitional//EN\""+ 
			"\"http://www.w3.org/TR/html4/loose.dtd\">\n" +
			"<html> \n" +
			"<head> \n" +
			"<meta http-equiv=\""+"Content-Type\""+" content=\""+"text/html;" 
			+  "charset=ISO-8859-1\""+"> \n" +
			"<title> My first jsp  </title> \n" +
			"</head> \n" +
			"<body> \n" +
			"<font size=\"12px\" " + "\">" + "username= " + username + "<br>\n" +
			"password= " + password + "\n" +
			"</font> \n" +
			"</body> \n" +
			"</html>" 
		);  
	}  
}