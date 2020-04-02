package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"controller"})
public class SpringbootQuick3Application {


	public static void main(String[] args) {
		SpringApplication.run(SpringbootQuick3Application.class, args);
	}

}
