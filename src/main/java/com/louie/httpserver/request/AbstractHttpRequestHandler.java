package com.louie.httpserver.request;

import com.louie.httpserver.response.HttpResponse;

public abstract  class AbstractHttpRequestHandler {
	abstract void handle(HttpRequest request,HttpResponse response);
}
