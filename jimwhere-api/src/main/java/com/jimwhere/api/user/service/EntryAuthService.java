package com.jimwhere.api.user.service;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.access.repository.AccessHistoryRepository;
import com.jimwhere.api.box.domain.Box;
import com.jimwhere.api.box.repository.BoxRepository;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.inout.domain.InOutType;
import com.jimwhere.api.inout.repository.InOutHistoryRepository;
import com.jimwhere.api.user.dto.reqeust.EntryAuthData;
import com.jimwhere.api.user.dto.reqeust.QrVerifyRequest;
import com.jimwhere.api.user.dto.response.QrVerifyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryAuthService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final AccessHistoryRepository accessHistoryRepository;
    private final InOutHistoryRepository inOutHistoryRepository;
    private final BoxRepository boxRepository;

    private final static Duration AUTHORIZED_TTL = Duration.ofMinutes(30);

    @Transactional
    public QrVerifyResponse applyBox(QrVerifyRequest request) {
        QrVerifyResponse qrVerifyResponse=verify(request);
        String key = "entry:auth:" + request.getToken();
        EntryAuthData data = (EntryAuthData) redisTemplate.opsForValue().get(key);
        Long accessHistoryId =data.getAccessHistoryId();
        List<InOutHistory> inOutHistoryList=inOutHistoryRepository.findByAccessHistory_AccessHistoryCode(accessHistoryId);
        for(InOutHistory inOutHistory :  inOutHistoryList){
          //음수  처리 /예외 처리
          String inOutName=inOutHistory.getInOutName();
          InOutType inOutType=inOutHistory.getInOutType();
          Long inOutQuantity=inOutHistory.getInOutQuantity();
          Box box=boxRepository.findById(inOutHistory.getBox().getBoxCode()).orElseThrow();
          String boxContent=box.getBoxContent();

          if(inOutName.equals(boxContent)&&
              inOutType== InOutType.IN
          ){
            box.addCurrentCount(inOutQuantity);
          }else if( inOutName.equals(boxContent)&&
              inOutType== InOutType.OUT){
            box.subtractCurrentCount( inOutQuantity);
          }
          else if(!inOutName.equals( boxContent)&&
              inOutType== InOutType.IN
          ){
            box.updateBoxContentCount(
                inOutName,
                inOutQuantity
            );
          }
          else {
            throw new CustomException(ErrorCode.INVALID_INPUT_FORMAT,"잘못된 물품 출고 입니다");
          }
        }
        return qrVerifyResponse;
    }

    public QrVerifyResponse verify(QrVerifyRequest req) {

        String key = "entry:auth:" + req.getToken();

        EntryAuthData data = (EntryAuthData) redisTemplate.opsForValue().get(key);

        if (data == null) {
            throw new CustomException(ErrorCode.INVALID_OR_EXPIRED_QR, "QR이 만료되었거나 존재하지 않습니다.");
        }

        // 최초 인증일 경우
        if ("PENDING".equals(data.getStatus())) {

            data.setStatus("AUTHORIZED");

            // ⬇ DB AccessHistory 업데이트
            AccessHistory history = accessHistoryRepository
                    .findById(data.getAccessHistoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST,"해당정보가 없습니다"));

            history.updateAccessSuccess(LocalDateTime.now());

            accessHistoryRepository.save(history);

            // Redis TTL = 30분 재설정
            redisTemplate.opsForValue().set(key, data, AUTHORIZED_TTL);
        }

        // 재입장일 경우: AUTHORIZED 상태 그대로 응답
        return new QrVerifyResponse(
                data.getAccessHistoryId(),
                data.getUserId(),
                data.getRoomId(),
                data.getStatus()
        );
    }
}
