package com.robsonapsilva.properties;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "quarkus.sqs.aws")
public interface SQSClientProperties {

    String region();

    Integer sqsMaxNumberMessages();

    String sqsQueueName();

}
