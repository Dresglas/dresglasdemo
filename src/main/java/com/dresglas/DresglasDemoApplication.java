package com.dresglas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan("com.dresglas")
@SpringBootApplication(scanBasePackages = {"com.dresglas"})
@EnableTransactionManagement
public class DresglasDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run( DresglasDemoApplication.class, args );
    }

}
