package com.jimwhere.api.user.dto.reqeust;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryAuthData {
    private Long accessHistoryId;
    private Long userId;
    private Long roomId;
    private String status;   // PENDING / AUTHORIZED
}
