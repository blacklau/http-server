package com.louie.httpserver.response;

public class HttpResponseHandler {
	
	public String response(HttpResponse response){
		if(response != null){
			String respBody = "{\"id\":\"334\"}";
			String resp = "HTTP/1.1 200 OK \r\n"+
			"Date: Fri, 22 May 2009 06:07:21 GMT\r\n"+
			"Content-Type: text/html; charset=UTF-8\r\n" +
			"Content-Length: "+respBody.length()+"\r\n\r\n"+ 
			respBody;
			return resp;
		}else{
			String respBody = "{\"id\":\"334\"}";
			String resp = "HTTP/1.1 404 notfind \r\n"+
			"Date: Fri, 22 May 2009 06:07:21 GMT\r\n"+
			"Content-Type: text/html; charset=UTF-8\r\n" +
			"Content-Length: 0"+"\r\n\r\n";
			return resp;
		}
	}
	
}
