package com.hack_SOAT_9.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hack_SOAT_9.orchestrator.bootstrap.OrchestratorApplication;
import com.hack_SOAT_9.orchestrator.enums.VideoProcessingStatus;
import com.hack_SOAT_9.orchestrator.integration.frameExtractor.domain.VideoProcessorEvent;
import com.hack_SOAT_9.orchestrator.integration.frameExtractor.service.ProcessVideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProcessVideoController.class)
@ContextConfiguration(classes = {
        OrchestratorApplication.class,
        ProcessVideoController.class,
        ProcessVideoControllerTest.MockConfig.class
})
class ProcessVideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessVideoService publisherService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ProcessVideoService processVideoService() {
            return Mockito.mock(ProcessVideoService.class);
        }
    }

    @BeforeEach
    void cleanMocks() {
        reset(publisherService);
    }

    @Test
    void shouldProcessSingleVideo() throws Exception {
        VideoProcessorEvent payload = new VideoProcessorEvent(
                "video.mp4",
                "output/path",
                "user123",
                "2025-07-11T12:00:00",
                VideoProcessingStatus.PROCESSING
        );

        mockMvc.perform(post("/api/v1/orchestrator/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento enviado para processamento"));

        verify(publisherService).sendVideoToQueue(payload);
    }

    @Test
    void shouldProcessBatchVideos() throws Exception {
        List<VideoProcessorEvent> payloads = List.of(
                new VideoProcessorEvent("video1.mp4", "output/1", "user123", "2025-07-11T12:00:00", VideoProcessingStatus.PROCESSING),
                new VideoProcessorEvent("video2.mp4", "output/2", "user123", "2025-07-11T12:01:00", VideoProcessingStatus.PROCESSING)
        );

        mockMvc.perform(post("/api/v1/orchestrator/enqueue/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payloads)))
                .andExpect(status().isOk())
                .andExpect(content().string("2 eventos enviados para processamento!"));

        verify(publisherService, times(2)).sendVideoToQueue(any(VideoProcessorEvent.class));
    }
}
