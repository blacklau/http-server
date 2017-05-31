package com.louie.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import com.louie.httpserver.controller.ExampleControl;
import com.louie.httpserver.request.HttpRequestAdapter;
import com.louie.httpserver.request.HttpRequestHandler;
import com.louie.httpserver.response.HttpResponse;
import com.louie.httpserver.response.HttpResponseHandler;

public class NioHttpServer implements HttpServer{
	public void run(int port)  {
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress("localhost",port));
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("server started at port "+port);
			Uri.add("/find", ExampleControl.class);
			while(true){
				int ks = selector.select(500);
				if(ks > 0){
					Set<SelectionKey> keys = selector.selectedKeys();
					for(SelectionKey key:keys){
						if(key.isAcceptable()){
							SocketChannel sc = server.accept();
							sc.configureBlocking(false);
							sc.register(selector, SelectionKey.OP_READ);
						}
						if(key.isReadable()){
								SocketChannel sc = (SocketChannel) key.channel();
								ByteBuffer dst = ByteBuffer.allocate(1024);
								try{
								sc.read(dst);
								String rs = new String(dst.array(),"utf-8");
								System.out.println("rec:\r\n"+ rs);
								HttpRequestAdapter rq = new HttpRequestAdapter(rs);
								HttpRequestHandler handler = new HttpRequestHandler();
								HttpResponse res = handler.handle(rq.getRequest());
								HttpResponseHandler responseH = new HttpResponseHandler();
								sc.write(ByteBuffer.wrap(responseH.response(res).getBytes()));
							}catch(Exception ex){
								ex.printStackTrace();
								key.cancel();
								key.channel().close();
							}
						}
					}
					keys.clear();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
