package com.louie.httpserver.request;

import com.louie.httpserver.response.HttpResponse;

public abstract class AbstractRequestHandler extends AbstractHttpRequestHandler{
	protected void handle(HttpRequest request,HttpResponse response){
		 handle(request);
	}

	abstract HttpResponse handle(HttpRequest request);
}
