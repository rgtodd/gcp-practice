package com.richtodd.gcp.kubclient.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.richtodd.gcp.kubclient.models.HelloWorldMvcModel;
import com.richtodd.gcp.kubserver.models.HelloWorldRestModel;

@Controller
public class HelloServerController {

	private final RestTemplate m_restTemplate;

	public HelloServerController(RestTemplateBuilder restTemplateBuilder) {
		m_restTemplate = restTemplateBuilder.build();
	}

	@GetMapping("/server")
	public String helloLocal(Model model) {

		// Specify URL of REST server.
		//
		String url = "http://kub-server:8081/helloworld";

		// Build mvcModel.
		//
		HelloWorldMvcModel mvcModel = new HelloWorldMvcModel();
		{
			mvcModel.setRestUrl(url);
			mvcModel.setMessage("Hello world!");
			mvcModel.setDateTime(LocalDateTime.now().toString());

			try {
				InetAddress localHost = InetAddress.getLocalHost();
				mvcModel.setHostName(localHost.getHostName());
				mvcModel.setHostAddress(localHost.getHostAddress());
			} catch (UnknownHostException e) {
				mvcModel.setException(e.getMessage());
			}

			try {
				HelloWorldRestModel restModel = m_restTemplate.getForObject(url, HelloWorldRestModel.class);
				mvcModel.setRestModel(restModel);
			} catch (RestClientException e) {
				mvcModel.setException(e.getMessage());
			}
		}

		// Specify the Thymeleaf template binding values.
		//
		model.addAttribute("title", "Hello Server");
		model.addAttribute("description", "Calls a REST service on kub-server using a hard-coded URL.");
		model.addAttribute("mvc", mvcModel);

		return "helloworld";
	}

}
