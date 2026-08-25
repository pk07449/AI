package com.pankaj.ai.helloworld;

import com.pankaj.ai.config.CommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = CommonConfig.class)
@SpringBootApplication(scanBasePackages = {"com.pankaj.ai"})
public class HelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}

}
