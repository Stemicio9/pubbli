package com.pubbli.pubbli;

import com.pubbli.pubbli.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class PubbliApplication {



    public static void main(String[] args) {
        SpringApplication.run(PubbliApplication.class, args);
    }






}
