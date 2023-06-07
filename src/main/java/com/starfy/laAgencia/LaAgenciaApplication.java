package com.starfy.laAgencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LaAgenciaApplication {

	private static final Logger logger = LoggerFactory.getLogger(LaAgenciaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LaAgenciaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(){
		return (args) ->{
			logger.info("La apliacion inicio con exito. \u00A9 Starfy - 2023");
		};
	}
}
