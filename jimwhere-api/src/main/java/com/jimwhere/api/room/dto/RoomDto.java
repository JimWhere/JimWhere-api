package com.jimwhere.api.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RoomDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private String roomName;
        private Long roomWidth;
        private Long roomLength;
        private Long roomHeight;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        private String roomName;
        private Long roomWidth;
        private Long roomLength;
        private Long roomHeight;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long roomCode;
        private String roomName;
        private Long roomWidth;
        private Long roomLength;
        private Long roomHeight;
        private Long userCode;
        private String userName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
