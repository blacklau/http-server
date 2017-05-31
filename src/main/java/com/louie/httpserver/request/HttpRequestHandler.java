package com.louie.httpserver.request;

import com.louie.httpserver.Uri;
import com.louie.httpserver.response.HttpResponse;

public class HttpRequestHandler extends AbstractRequestHandler{

	public HttpResponse handle(HttpRequest request){
		if(Uri.contains(request.getUri())){
			return new HttpResponse();
		}else{
			return null;
		}
	}
}
