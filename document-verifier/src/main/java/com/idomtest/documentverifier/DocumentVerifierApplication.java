package com.idomtest.documentverifier;

import com.idomtest.resources.DocumentVerifierService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@SpringBootApplication
public class DocumentVerifierApplication {

	@Bean(name = "/validate")
	HttpInvokerServiceExporter validationService(){
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(new DocumentVerifierServiceImpl());
		exporter.setServiceInterface(DocumentVerifierService.class);
		return exporter;
	}

	public static void main(String[] args) {
		SpringApplication.run(DocumentVerifierApplication.class, args);
	}

}
