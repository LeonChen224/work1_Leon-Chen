package com.connext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication //等同于 @Configuration @EnableAutoConfiguration @ComponentScan
@EnableTransactionManagement
@MapperScan("com.connext.dao")
public class SpringbootTestApplication {

	@RequestMapping("/")
	public String index(){
		System.out.println("ready start enter index..");
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTestApplication.class, args);
	}
}
