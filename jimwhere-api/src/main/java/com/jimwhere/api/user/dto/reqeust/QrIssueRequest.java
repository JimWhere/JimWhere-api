package com.jimwhere.api.user.dto.reqeust;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrIssueRequest {
    private Long userId;
    private Long roomId;
    private Long accessHistoryId; // AccessHistory PK
}
