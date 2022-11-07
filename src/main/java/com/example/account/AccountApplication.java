package com.example.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//spring boot cache kütüphanesini enable etmesi icin
public class AccountApplication{


	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

/*
	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String description,
								 @Value("${application-version}") String version){
		return new OpenAPI()
				.info(new Info()
						.title("Bank API")
						.version(version)
						.description(description)
						.license(new License().name("Bank API Licence")));


 */
	}





