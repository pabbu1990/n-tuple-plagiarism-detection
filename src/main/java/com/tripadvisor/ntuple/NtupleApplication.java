package com.tripadvisor.ntuple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NtupleApplication{

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(NtupleApplication.class, args);
		PlagiarismService service = applicationContext.getBean(PlagiarismService.class);
		if(args.length < 3 || args.length>4) {
			System.out.println(service.printUsageMessage());
			return;
		}
		service.readInput(args);
	}
}
