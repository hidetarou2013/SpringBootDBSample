package com.example.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.cassandra")
public class CassandraApplication {
    public static void main(String[] args) {
        SpringApplication.run(CassandraApplication.class, args);
    }
}
