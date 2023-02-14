package com.robsonapsilva.resource;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.util.Map;

public class SQSResource implements QuarkusTestResourceLifecycleManager {

    private static final LocalStackContainer CONTAINER = new LocalStackContainer(DockerImageName
            .parse("localstack/localstack")
            .withTag("0.12.17"))
            .withServices(LocalStackContainer.Service.SQS);

    public final static String QUEUE_NAME = "QUARKUS_AWS_SQS_QUEUE";

    @Override
    public Map<String, String> start() {
        DockerClientFactory.instance().client();
        CONTAINER.start();
        URI endpointOverride = builderEndpointOverride(LocalStackContainer.Service.SQS);
        StaticCredentialsProvider staticCredentials = builderStaticCredentialsProvider();
        builderSqsClient(endpointOverride, staticCredentials);
        return Map.of("quarkus.sqs.endpoint-override", endpointOverride.toString());
    }

    @Override
    public void stop() {
        if (CONTAINER != null) {
            CONTAINER.close();
        }
    }

    private URI builderEndpointOverride(LocalStackContainer.Service service) {
        return CONTAINER.getEndpointOverride(LocalStackContainer.EnabledService
                .named(service.getName()));
    }

    private SqsClient builderSqsClient(URI endpointOverride, StaticCredentialsProvider staticCredentials) {
        return SqsClient.builder()
                .endpointOverride(endpointOverride)
                .credentialsProvider(staticCredentials)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .region(Region.US_EAST_1)
                .build();
    }

    private StaticCredentialsProvider builderStaticCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                "accesskey",
                "secretKey"
        ));
    }


}