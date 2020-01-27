package com.richtodd.gcp.kubclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
public class KubClientConfiguration {

	private String name = "*UNSET*";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
