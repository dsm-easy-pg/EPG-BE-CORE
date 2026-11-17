package com.sungjujjang.epg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EpgApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpgApplication.class, args);
	}

}
