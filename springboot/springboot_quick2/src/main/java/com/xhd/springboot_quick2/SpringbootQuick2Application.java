package com.xhd.springboot_quick2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({"com.xhd.controller"})
public class SpringbootQuick2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootQuick2Application.class, args);
	}


}
