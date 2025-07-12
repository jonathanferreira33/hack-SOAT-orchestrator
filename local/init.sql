CREATE DATABASE IF NOT EXISTS fiapdb;

CREATE USER IF NOT EXISTS 'fiapuser'@'%' IDENTIFIED BY '123';

GRANT ALL PRIVILEGES ON fiapdb.* TO 'fiapuser'@'%';

FLUSH PRIVILEGES;

USE fiapdb;

CREATE TABLE IF NOT EXISTS video_processing_event_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_path TEXT,
    video_name TEXT,
    output_dir TEXT,
    user_id TEXT,
    queued_at TIMESTAMP,
    completed_at TIMESTAMP,
    status VARCHAR(50)
);

INSERT INTO video_processing_event_entity (
    video_path, video_name, output_dir, user_id, queued_at, completed_at, status
) VALUES
('path/to/video1.mp4', 'video1.mp4', '/frames/output1', 'user123', NOW(), NULL, 'PROCESSING'),
('path/to/video2.mp4', 'video2.mp4', '/frames/output2', 'user123', NOW() - INTERVAL 5 MINUTE, NOW(), 'SUCCESS'),
('path/to/video3.mp4', 'video3.mp4', '/frames/output3', 'user456', NOW() - INTERVAL 10 MINUTE, NOW(), 'ERROR');
