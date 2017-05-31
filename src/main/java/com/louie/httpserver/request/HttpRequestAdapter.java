package com.louie.httpserver.request;

import java.util.HashMap;
import java.util.Map;

public  class HttpRequestAdapter {
	private String requestStr;
	public HttpRequestAdapter(String requestStr){
		this.requestStr = requestStr;
	}
	
	public HttpRequest getRequest(){
		if(requestStr == null || requestStr.equals("")) return null;
		String[] lines = requestStr.split("\r\n\r\n");
		String[] rl = lines[0].split("\r\n");
		String requestLine = rl[0];
		HttpRequest request = new HttpRequest();
		String[] rls = requestLine.split(" ");
		request.setMethod(rls[0]);
		String[] pv = rls[2].split("/");
		request.setProtocol(pv[0]);
		request.setVersion(pv[1]);
		
		Map<String,String> headMap = new HashMap<String,String>();
		for(int i = 1;i < rl.length;i++){
			String[] hs = rl[i].split(": ");
			headMap.put(hs[0], hs[1]);
		}
		request.setHeader(headMap);
		
		//"GET /person/info?id=89 HTTP/1.1";
		if(request.getMethod().equals("GET")){
			String[] up = rls[1].split("\\?");
			request.setUri(up[0]);
			if(up.length > 1){
				String[] paramss = up[1].split("&");
				Map<String, String> params = new HashMap<String,String>();
				for(String param:paramss){
					String[] p = param.split("=");
					params.put(p[0], p[1]);
				}
				request.setParams(params);
			}else{
				request.setParams(new HashMap<String,String>());
			}
		}else if(request.getMethod().equals("POST")){
			request.setUri(rls[1]);
			String[] paramss = lines[1].split("&");
			Map<String, String> params = new HashMap<String,String>();
			for(String param:paramss){
				String[] p = param.split("=");
				params.put(p[0], p[1]);
			}
			request.setParams(params);
		}
		
		return request;
	}

	
}
