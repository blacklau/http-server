package com.louie.httpserver;

import java.util.concurrent.ConcurrentHashMap;

public class Uri {
	private static ConcurrentHashMap<String,Object> uriMap = new  ConcurrentHashMap<String,Object>();
	
	public static void add(String uri,Object object){
		uriMap.put(uri, object);
	}
	
	public static boolean contains(String uri){
		return uriMap.containsKey(uri);
	}
}
