package com.hack_SOAT_9.orchestrator.controller;

import com.hack_SOAT_9.orchestrator.integration.frameExtractor.domain.VideoProcessorEvent;
import com.hack_SOAT_9.orchestrator.integration.frameExtractor.service.ProcessVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/v1/orchestrator")
public class ProcessVideoController {

    private final ProcessVideoService publisherService;

    @Autowired
    public ProcessVideoController(ProcessVideoService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processVideo(@RequestBody VideoProcessorEvent payload) {
        System.out.println("ProcessVideo chamado");
        publisherService.sendVideoToQueue(payload);
        return ResponseEntity.ok("Evento enviado para processamento");
    }

    @PostMapping("/enqueue/batch")
    public ResponseEntity<String> enqueueVideos(@RequestBody List<VideoProcessorEvent> requests) {

        for (VideoProcessorEvent request : requests) {
            publisherService.sendVideoToQueue(request);
        }

        return ResponseEntity.ok(requests.size() + " eventos enviados para processamento!");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
