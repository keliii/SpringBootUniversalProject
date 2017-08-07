package com.keliii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class UniversalApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversalApplication.class, args);
	}

}
//使用war包时重写configure
//public class UniversalApplication extends SpringBootServletInitializer {
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(UniversalApplication.class);
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(UniversalApplication.class, args);
//	}
//}

