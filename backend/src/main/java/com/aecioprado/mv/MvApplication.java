/**
 * Início: 20/10/2021
 * Autor: José Aécio do Prado Júnior
 * E-mail: aecioprado@gmail.com
 * Info: projeto desenvolvido para teste técnico do CUBO MV
 */


package com.aecioprado.mv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MvApplication extends SpringBootServletInitializer {
	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MvApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MvApplication.class, args);
	}

}
