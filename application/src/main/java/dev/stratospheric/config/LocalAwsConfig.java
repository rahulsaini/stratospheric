package dev.stratospheric.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration to start the application local using LocalStack to provide AWS services.
 */
@Configuration
@Profile("dev")
public class LocalAwsConfig {

  private final AWSStaticCredentialsProvider DUMMY_CREDENTIALS
    = new AWSStaticCredentialsProvider(new BasicAWSCredentials("foo", "bar"));

  private final AwsClientBuilder.EndpointConfiguration LOCAL_ENDPOINT
    = new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "eu-central-1");

  @Bean
  public AmazonSQSAsync amazonSQS() {
    return AmazonSQSAsyncClientBuilder.standard()
      .withCredentials(DUMMY_CREDENTIALS)
      .withEndpointConfiguration(LOCAL_ENDPOINT)
      .build();
  }

  @Bean
  public AmazonSNS amazonSNS() {
    return AmazonSNSClientBuilder.standard()
      .withCredentials(DUMMY_CREDENTIALS)
      .withEndpointConfiguration(LOCAL_ENDPOINT)
      .build();
  }

  @Bean
  public AmazonSimpleEmailService amazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
      .withCredentials(DUMMY_CREDENTIALS)
      .withEndpointConfiguration(LOCAL_ENDPOINT)
      .build();
  }
}
