package com.louie.httpserver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	HttpServer server = new NioHttpServer();
    	server.run(8001);
        System.out.println( "Hello World!" );
    }
}
