package com.distribute.persistence.diffusion;

import com.pushtechnology.diffusion.client.callbacks.ErrorReason;
import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;
import com.pushtechnology.diffusion.datatype.json.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
abstract class AbstractDiffusionClient {

    protected void doSubscribe(Topics topics, String topicPath) {

        topics.addStream(topicPath, JSON.class, new Topics.ValueStream.Default<JSON>() {
            @Override
            public void onValue(String path, TopicSpecification topicSpecification, JSON oldValue, JSON newValue) {
                String str = newValue.toJsonString();
                log.info("do somthing with str:{}", str);
            }

            @Override
            public void onSubscription(String path, TopicSpecification topicSpecification) {
                super.onSubscription(path, topicSpecification);
            }

            @Override
            public void onUnsubscription(String path, TopicSpecification topicSpecification, Topics.UnsubscribeReason unsubscribeReason) {
                super.onUnsubscription(path, topicSpecification, unsubscribeReason);
            }

            @Override
            public void onClose() {
                super.onClose();
            }

            @Override
            public void onError(ErrorReason errorReason) {
                super.onError(errorReason);
            }
        });

    }
}
