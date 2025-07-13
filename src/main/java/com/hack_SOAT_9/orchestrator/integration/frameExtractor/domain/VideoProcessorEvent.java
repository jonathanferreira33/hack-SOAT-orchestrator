package com.hack_SOAT_9.orchestrator.integration.frameExtractor.domain;

import com.hack_SOAT_9.orchestrator.enums.VideoProcessingStatus;

import java.io.Serializable;

public record VideoProcessorEvent(
        String videoPath,
        String outputDir,
        String user,
        String queuedAt,
        VideoProcessingStatus status
) implements Serializable {
}
