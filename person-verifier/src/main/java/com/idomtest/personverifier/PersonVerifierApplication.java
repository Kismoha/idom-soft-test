package com.idomtest.personverifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PersonVerifierApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PersonVerifierApplication.class, args);
		ServiceHolder.getInstance().loadContext(context);

	}

}
