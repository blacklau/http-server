package com.louie.httpserver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.louie.httpserver.anno.RequestMapping;
import com.louie.httpserver.controller.ExampleControl;
import com.louie.httpserver.request.HttpRequestAdapter;
import com.louie.httpserver.request.HttpRequestHandler;
import com.louie.httpserver.response.HttpResponse;
import com.louie.httpserver.response.HttpResponseHandler;
import com.louie.httpserver.scanner.PkgScanner;

public class NioHttpServer implements HttpServer{
	public NioHttpServer(){
		initController();
		ConcurrentHashMap<String,Object> maps = Uri.getAll();
		for(String key:maps.keySet()){
			Object[] mm = (Object[])maps.get(key);
			Method m = (Method) mm[0];
			System.out.println(key + " : "+m.getName());
		}
	}
	public void run(int port)  {
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress("localhost",port));
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("server started at port "+port);
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
	
	private void initController(){
		List<Class> controlles = getControlles();
		initHandleMapping(controlles);
	}
	
	private void initHandleMapping(List<Class> controlles){
		for(Class cls:controlles){
			RequestMapping mapping = (RequestMapping) cls.getAnnotation(RequestMapping.class);
			String cRootPath = mapping.path();
			System.out.println("cRootPath："+cRootPath);
			Method[] ms = cls.getMethods();
			for(Method m:ms){
				RequestMapping mmapping = (RequestMapping) m.getAnnotation(RequestMapping.class);
				if(mmapping != null){
					String path = mmapping.path();
					Object[] me = new Object[2];
					me[0] = m;
					me[1] = cls;
					Uri.add(cRootPath+path,  me);
				}
			}
		}
	}
	
	private List<Class> getControlles(){
		PkgScanner pkgScanner = new PkgScanner("com.louie.httpserver");
		try{
			List<String> clss = pkgScanner.scan();
			for(String name:clss){
				System.out.println(name);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		List<Class> cls = new ArrayList<Class>();
		cls.add(ExampleControl.class);
		return cls;
	}
}
