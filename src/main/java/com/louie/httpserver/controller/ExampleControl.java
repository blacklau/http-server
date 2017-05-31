package com.louie.httpserver.controller;

import com.louie.httpserver.anno.Controller;
import com.louie.httpserver.anno.RequestMapping;
import com.louie.httpserver.model.Person;

@Controller
public class ExampleControl {
	@RequestMapping(path="/person/find",method="GET")
	public Person find(Integer id){
		return new Person("louiee");
	}
}
