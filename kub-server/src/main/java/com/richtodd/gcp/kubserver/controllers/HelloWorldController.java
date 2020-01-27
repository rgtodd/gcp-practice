package com.richtodd.gcp.kubserver.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richtodd.gcp.kubserver.models.HelloWorldRestModel;

@RestController
public class HelloWorldController {

	@GetMapping("/helloworld")
	public HelloWorldRestModel helloWorld() {

		HelloWorldRestModel model = new HelloWorldRestModel();

		model.setMessage("Hello world!");
		model.setDateTime(LocalDateTime.now().toString());

		try {
			InetAddress localHost = InetAddress.getLocalHost();
			model.setHostName(localHost.getHostName());
			model.setHostAddress(localHost.getHostAddress());
		} catch (UnknownHostException e) {
			model.setException(e.getMessage());
		}

		return model;
	}

}
