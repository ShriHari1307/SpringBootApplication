package com.example.springProject;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringProjectApplication {

	static Logger log = Logger.getLogger(SpringProjectApplication.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("C:\\Users\\shrih\\Downloads\\springProject\\springProject\\src\\Properties\\Log4j.properties");
		log.info("Starting Spring Project");
		ApplicationContext ctx = SpringApplication.run(SpringProjectApplication.class, args);
	}

}
