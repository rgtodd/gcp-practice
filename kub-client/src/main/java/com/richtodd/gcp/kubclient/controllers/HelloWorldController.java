package com.richtodd.gcp.kubclient.controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloWorldController {
	
	private final RestTemplate m_restTemplate;
	
	public HelloWorldController(RestTemplateBuilder restTemplateBuilder)
	{
		m_restTemplate = restTemplateBuilder.build();
	}
	
	@GetMapping("/helloworld")
	public String helloWorld(Model model) {
		
		String message = m_restTemplate.getForObject("http://localhost:8081/helloworld", String.class);
		
		model.addAttribute("message", message);
		
		return "helloworld";
	}

}
