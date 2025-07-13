package com.hack_SOAT_9.orchestrator.utils;

import com.hack_SOAT_9.orchestrator.enums.VideoProcessingStatus;
import com.hack_SOAT_9.orchestrator.integration.frameExtractor.domain.VideoProcessorEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VideoProcessorEventUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static VideoProcessorEvent updateEvent(VideoProcessorEvent original, VideoProcessingStatus status) {

        String dateNowFormatted = LocalDateTime.now().format(FORMATTER);
        return new VideoProcessorEvent(
                original.videoPath(),
                original.outputDir(),
                original.user(),
                dateNowFormatted,
                status
        );
    }
}