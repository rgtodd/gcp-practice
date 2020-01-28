package com.richtodd.gcp.kubclient.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.richtodd.gcp.kubclient.KubClientConfiguration;
import com.richtodd.gcp.kubclient.models.HelloWorldMvcModel;
import com.richtodd.gcp.kubserver.models.HelloWorldRestModel;

@Controller
public class HelloConfigController {

	@Autowired
	private KubClientConfiguration configuraiton;

	private final RestTemplate m_restTemplate;

	public HelloConfigController(RestTemplateBuilder restTemplateBuilder) {
		m_restTemplate = restTemplateBuilder.build();
	}

	@GetMapping("/config")
	public String helloConfig(Model model) {

		// Specify URL of REST server.
		//
		String url = "http://" + configuraiton.getName() + ":8081/helloworld";

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

		if (mvcModel.getRestModel() == null) {
			mvcModel.setRestModel(new HelloWorldRestModel());
		}

		// Specify the Thymeleaf template binding values.
		//
		model.addAttribute("title", "Hello Config");
		model.addAttribute("description", "Calls a REST service using a configuration-based URL.");
		model.addAttribute("mvc", mvcModel);

		return "helloworld";
	}

}
