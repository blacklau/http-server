package com.louie.httpserver.request;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import com.louie.httpserver.Uri;
import com.louie.httpserver.response.HttpResponse;

public class HttpRequestHandler extends AbstractRequestHandler{
    private ObjectMapper objectMapper = new ObjectMapper();
    
	public HttpResponse handle(HttpRequest request){
		HttpResponse response = new HttpResponse();
		response.setVersion(request.getVersion());
		response.setProtocol(request.getProtocol());
		if(Uri.contains(request.getUri())){
			Map<String,String> params = request.getParams();
			String id = params.get("id");
			Object[] ms = (Object[]) Uri.get(request.getUri());
			Method m = (Method) ms[0];
			Class cls = (Class)ms[1];
			try {
				//参数名获取
				LocalVariableTableParameterNameDiscoverer u =     
			            new LocalVariableTableParameterNameDiscoverer(); 
		        String[] mParams = u.getParameterNames(m);
		        List<String> pNames = new ArrayList<String>();
		        Map<String,String> values = new HashMap<String,String>();
		        for (int i = 0; i < mParams.length; i++) {    
		            System.out.println(mParams[i]);  
		            String name = mParams[i];
		            pNames.add(mParams[i]);
		            String v = params.get(name) == null?null:params.get(name);
		            values.put(name, v);
		        }    
		        //参数类型获取
		        Class<?>[] pTypes = m.getParameterTypes();
		        List<Object> paramsV = new ArrayList<Object>();
		        for(int i = 0;i < pNames.size();i++){
		        	Class type = pTypes[i];
		        	Object v = null;
		        	if(type.getName().equals(Integer.class.getName())){
		        		v = Integer.valueOf(values.get(pNames.get(i)));
		        	}
		        	if(type.getName().equals(Double.class.getName())){
		        		v = Double.valueOf(values.get(pNames.get(i)));
		        	}
		        	if(type.getName().equals(Float.class.getName())){
		        		v = Double.valueOf(values.get(pNames.get(i)));
		        	}
		        	if(type.getName().equals(String.class.getName())){
		        		v = (String)values.get(pNames.get(i));
		        	}
		        	paramsV.add(v);
		        }
				
				Object o = cls.newInstance();
				Object result = m.invoke(o, paramsV.toArray());
				if(objectMapper.canSerialize(result.getClass())){
					String v = objectMapper.writeValueAsString(result);
					response.setBody(v);
					response.setContentLength(v.length());
					response.setStatus(200);
				}
				return response;
			} catch (Exception e) {
				String msg = e.getMessage();
				response.setStatus(500);
				response.setBody(msg);
				response.setContentLength(msg.length());
			}
		}else{
			response.setStatus(404);
			response.setBody("");
			response.setContentLength(0);
		}
		return response;
	}
}
