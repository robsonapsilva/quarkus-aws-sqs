package com.robsonapsilva.component;

import com.robsonapsilva.config.SqsClientConfig;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
@RequiredArgsConstructor
public class SQSClientComponent {


    private final SqsClientConfig sqsClientConfig;

    public ReceiveMessageResponse receive(Integer maxNumberMessages, String queueName) {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .maxNumberOfMessages(maxNumberMessages)
                .queueUrl(sqsClientConfig.getQueueUrl(queueName))
                .build();
        return sqsClientConfig.getSqsClient().receiveMessage(receiveMessageRequest);
    }

    public SendMessageResponse send(String message, String queue) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .messageBody(message)
                .queueUrl(sqsClientConfig.getQueueUrl(queue))
                .build();
        return sqsClientConfig.getSqsClient().sendMessage(sendMessageRequest);
    }
}
