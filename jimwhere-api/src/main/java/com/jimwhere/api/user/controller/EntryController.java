package com.jimwhere.api.user.controller;

import com.jimwhere.api.global.model.ApiResponse;
import com.jimwhere.api.user.dto.reqeust.QrVerifyRequest;
import com.jimwhere.api.user.dto.response.QrVerifyResponse;
import com.jimwhere.api.user.service.EntryAuthService;
import com.jimwhere.api.user.service.EntryQrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@Tag(name="출입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entry")
public class EntryController {

    private final EntryAuthService entryAuthService;
    private final EntryQrService entryQrService;

    // QR 인증
    @Operation(
        summary = "QR인증", description = "유저의 QR인증을 검증하는 api"
    )
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<QrVerifyResponse>> verifyQr(@RequestBody QrVerifyRequest req) {
        /*QrVerifyResponse response = entryAuthService.verify(req);*/
        QrVerifyResponse response = entryAuthService.applyBox(req);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // QR 미리보기 (브라우저에서 바로 보여주기)
    @Operation(
        summary = "QR미리보기", description = "유저가 QR을 브라우저에서 바로 보여주는 api "
    )
    @GetMapping(value = "/preview/{accessHistoryId}", produces = "text/html")
    public String previewQr(@PathVariable Long accessHistoryId) throws Exception {

        // EntryQrService 이용해 QR 생성
        var result = entryQrService.generatePreviewQr(accessHistoryId);

        String qrToken = result.qrToken();
        String base64 = Base64.getEncoder().encodeToString(result.qrImage());

        return """
        <!DOCTYPE html>
        <html>
        <body style="font-family:sans-serif; text-align:center;">
            <h2>QR 코드 미리보기</h2>
            <img src="data:image/png;base64,%s"
                 style="width:300px; height:300px; border:1px solid #ccc;" />
            <p>%s</p>
        </body>
        </html>
        """.formatted(base64, qrToken);
    }
}
