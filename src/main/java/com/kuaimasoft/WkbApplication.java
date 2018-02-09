package com.kuaimasoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.http.HttpStatus;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class WkbApplication implements ErrorPageRegistrar{

	public static void main(String[] args) {
		SpringApplication.run(WkbApplication.class, args);
	}
	
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error_page/401.html");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error_page/404.html");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error_page/500.html");
        registry.addErrorPages(error401Page, error404Page, error500Page);
        
    }
}
