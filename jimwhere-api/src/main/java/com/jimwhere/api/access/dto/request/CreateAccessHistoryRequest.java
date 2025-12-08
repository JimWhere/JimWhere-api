package com.jimwhere.api.access.dto.request;

import com.jimwhere.api.access.domain.IsOwner;
import com.jimwhere.api.access.domain.VisitPurpose;


public record CreateAccessHistoryRequest(
        IsOwner isOwner,
        VisitPurpose visitPurpose,
        Long roomId
) {}
