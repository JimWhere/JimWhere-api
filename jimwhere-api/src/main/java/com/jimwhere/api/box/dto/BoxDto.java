package com.jimwhere.api.box.dto;

import com.jimwhere.api.box.domain.Box;
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
        private Long roomCode;// 소속 방 코드


      public static Response from(Box box) {
        return Response.builder()
            .boxCode(box.getBoxCode())
            .boxName(box.getBoxName())
            .boxContent(box.getBoxContent())
            .boxCurrentCount(box.getBoxCurrentCount())
            .boxWidth(box.getBoxWidth())
            .boxLength(box.getBoxLength())
            .boxHeight(box.getBoxHeight())
            .roomCode(box.getRoom().getRoomCode())  // Room 매핑
            .build();
      }
    }
}
