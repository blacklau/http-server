package com.louie.httpserver.controller;

import com.louie.httpserver.anno.Controller;
import com.louie.httpserver.anno.RequestMapping;
import com.louie.httpserver.model.Person;

@Controller
@RequestMapping(path="/person")
public class ExampleControl {
	@RequestMapping(path="/find",method="GET")
	public Person find(Integer id,String name){
		return new Person(id+" louiee");
	}
	
	@RequestMapping(path="/detail",method="GET")
	public Person detail(Integer id,String name){
		return new Person(id+" louie detail");
	}

	@RequestMapping(path="/add",method="PUT")
	public Person add(Integer id,String name){
		return new Person(id+" added");
	}
}
