package com.robsonapsilva.service;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.robsonapsilva.component.SQSClientComponent;
import com.robsonapsilva.properties.SQSClientProperties;
import com.robsonapsilva.records.MessageRecord;
import com.robsonapsilva.records.PlanetRecord;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class SQSClientService {

    private static final FluentLogger LOG = FluentLogger.forEnclosingClass();

    private final SQSClientProperties sqsClientProperties;
    private final SQSClientComponent sqsClientComponent;

    public List<MessageRecord> receive() {
        ReceiveMessageResponse receiveMessageResponse = sqsClientComponent.receive(
                sqsClientProperties.sqsMaxNumberMessages(),
                sqsClientProperties.sqsQueueName());
        List<Message> messages = receiveMessageResponse.messages();
        LOG.atInfo().log(String.format("[SQS] Receive:%s messages", messages.size()));
        return toMessageRecord(messages);
    }

    public MessageRecord send(PlanetRecord planetRecord) {
        SendMessageResponse response = sqsClientComponent.send(
                new Gson().toJson(planetRecord),
                sqsClientProperties.sqsQueueName());
        LOG.atInfo().log(String.format("[SQS] Message send with success! MessageId:%s!", response.messageId()));
        return new MessageRecord(response.messageId(), planetRecord);
    }

    private List<MessageRecord> toMessageRecord(List<Message> messages) {
        return messages.stream()
                .map(m -> new MessageRecord(
                        m.messageId(),
                        new Gson().fromJson(m.body(), PlanetRecord.class)))
                .toList();
    }
}
