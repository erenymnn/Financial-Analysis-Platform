package com.Yama.Financial.Analysis.Platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinancialAnalysisPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialAnalysisPlatformApplication.class, args);
	}

}
