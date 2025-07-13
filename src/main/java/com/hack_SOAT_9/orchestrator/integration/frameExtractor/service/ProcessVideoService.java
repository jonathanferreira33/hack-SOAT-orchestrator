package com.hack_SOAT_9.orchestrator.integration.frameExtractor.service;

import com.hack_SOAT_9.orchestrator.config.RabbitMQConfig;
import com.hack_SOAT_9.orchestrator.enums.VideoProcessingStatus;
import com.hack_SOAT_9.orchestrator.integration.frameExtractor.domain.VideoProcessorEvent;
import com.hack_SOAT_9.orchestrator.utils.VideoProcessorEventUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProcessVideoService {

    private final RabbitTemplate rabbitTemplate;

    public ProcessVideoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendVideoToQueue(VideoProcessorEvent payload) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        VideoProcessorEvent newPayload = VideoProcessorEventUtils.updateEvent(payload, VideoProcessingStatus.PROCESSING);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.VIDEO_UPLOAD_EXCHANGE,
                RabbitMQConfig.VIDEO_UPLOAD_QUEUE,
                newPayload
        );
    }

//    @Cacheable("video-processor-event")
//    public List<VideoProcessorEvent> getAllvideoEventbyUser(String id){
//
////        try {
////            HttpClient client = HttpClient.newHttpClient();
////            HttpRequest request = HttpRequest.newBuilder()
////                    .uri(URI.create(""))
////        } catch () {
////
////        }
//    }

}
