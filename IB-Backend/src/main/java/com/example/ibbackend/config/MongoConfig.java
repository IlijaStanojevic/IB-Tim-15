package com.example.ibbackend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
@Configuration
@EnableMongoRepositories(basePackages = "com.example.ibbackend.repository")
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "mydb");
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

}
