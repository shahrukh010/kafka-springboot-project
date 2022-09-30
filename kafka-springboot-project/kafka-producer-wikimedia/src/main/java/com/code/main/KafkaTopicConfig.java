package com.code.main;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	private String topicName;

	@Bean
	public NewTopic newTopi() {

		return TopicBuilder.name("wikimedia_recentchange").build();
	}

}
