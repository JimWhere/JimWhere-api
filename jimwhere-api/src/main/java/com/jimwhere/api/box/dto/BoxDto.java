package com.jimwhere.api.box.dto;

import lombok.*;

public class BoxDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        private String boxName;
        private String boxContent;
        private Long boxCurrentCount;
        private Long boxWidth;
        private Long boxLength;
        private Long boxHeight;
    }

    @Getter
    @Builder
    public static class Response {
        private Long boxCode;
        private String boxName;
        private String boxContent;
        private Long boxCurrentCount;
        private Long boxWidth;
        private Long boxLength;
        private Long boxHeight;
        private Long roomCode; // 소속 방 코드
    }
}
