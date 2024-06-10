package com.products.products.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DynamoDBConfig {
    private final Environment env;

    public DynamoDBConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        String region = env.getProperty("region");
        if (isLocal()) {
            return AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://dynamodb-local:8000", region))
                    .build();
        } else {
            return AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                    .withRegion(region)
                    .build();
        }
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB());
    }

    private boolean isLocal() {
        return "local".equalsIgnoreCase(env.getProperty("stage"));
    }
}
