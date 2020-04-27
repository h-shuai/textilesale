package com.rosellete.textilesale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TextilesaleApplication {

	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password","jasypt");
		SpringApplication.run(TextilesaleApplication.class, args);
	}

}
