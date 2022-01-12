package com.serbernal.basespring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cualquiera")
public class CualquieraRestController {

	@GetMapping
	public String hola() {
		return "Hola mundo";
	}
	
	@PostMapping
	public String holaPost() {
		return "Hola mundo post";
	}
}
