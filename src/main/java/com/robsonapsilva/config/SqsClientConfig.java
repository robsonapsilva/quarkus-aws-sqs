package com.robsonapsilva.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
public class SqsClientConfig {

    @Getter
    private final SqsClient sqsClient;

    public String getQueueUrl(String queue) {
        GetQueueUrlRequest requestUrl = GetQueueUrlRequest.builder().queueName(queue).build();
        GetQueueUrlResponse response = this.sqsClient.getQueueUrl(requestUrl);
        return response.queueUrl();
    }

}
