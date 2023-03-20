package com.acetech.assignment.service;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.acetech.assignment.pojos.BatchStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
	public final String webhookUrl = "https://webhook.site/964cca2f-7527-4e66-99d1-2ae425544d8e";

	/*
	 * Method to notify the batch creation status.
	 */
	@Override
	public void notifyBatchCreated(BatchStatus batchStatus ){
		RestTemplate restTemplate = new RestTemplate();
		//Map<Batch, String> webHookObject = Collections.singletonMap(batch, batchCreationStatus);
		try {
			restTemplate.postForObject(webhookUrl, batchStatus, Void.class);
			System.out.println("Webhook request successful for batch " + batchStatus.getBatch());
		//catch (HttpClientErrorException | HttpServerErrorException e) {
		} catch (RestClientException e) {
			System.out.println("Webhook request failed for batch " + batchStatus.getBatch() + ": " + e.getMessage());
		}
	}


}