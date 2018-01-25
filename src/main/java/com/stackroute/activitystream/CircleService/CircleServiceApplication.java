package com.stackroute.activitystream.CircleService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins={"http://172.23.238.162:9011/activityStream/api/user/","http://localhost:9013/api/circle/"})
@SpringBootApplication(scanBasePackages={"com.stackroute.activitystream"})
@EntityScan(basePackages={"com.stackroute.activitystream.model"})
public class CircleServiceApplication {

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
	    return new HibernateJpaSessionFactoryBean();
	}
	public static void main(String[] args) {
		SpringApplication.run(CircleServiceApplication.class, args);
	}
}
