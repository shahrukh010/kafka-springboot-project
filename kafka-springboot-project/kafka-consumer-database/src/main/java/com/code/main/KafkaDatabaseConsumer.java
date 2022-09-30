package com.code.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.code.main.entity.WikimediaData;
import com.code.main.repository.WikimediaDataRepository;

@Service
public class KafkaDatabaseConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

	private WikimediaDataRepository dataRepository;

	public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
		this.dataRepository = dataRepository;
	}

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consum(String eventMessage) {

		LOGGER.info(String.format("event message received-> %s ", eventMessage));

		WikimediaData wikimediaData = new WikimediaData();
		wikimediaData.setWikimediaData(eventMessage);
		dataRepository.save(wikimediaData);
	}

}
