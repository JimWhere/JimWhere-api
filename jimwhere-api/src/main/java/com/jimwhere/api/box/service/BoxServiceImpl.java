package com.jimwhere.api.box.service;

import com.jimwhere.api.box.dto.BoxDto;
import com.jimwhere.api.box.domain.Box;
import com.jimwhere.api.box.repository.BoxRepository;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.room.repository.RoomRepository;
import com.jimwhere.api.global.exception.ErrorCode;
import com.jimwhere.api.global.exception.CustomException;
import com.jimwhere.api.user.domain.User;
import com.jimwhere.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final RoomRepository roomRepository;
  private final UserRepository userRepository;

  @Override
    @Transactional(readOnly = true)
    public List<BoxDto.Response> listBoxesByRoom(Long roomCode) {
        // 방 존재 체크 (명확한 에러코드 사용)
        Room room = roomRepository.findById(roomCode)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        List<Box> boxes = boxRepository.findByRoomRoomCode(room.getRoomCode());
        return boxes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

  @Override
  @Transactional(readOnly = true)
  public List<BoxDto.Response> listBoxesByRoom(String userName) {
      User user=userRepository.findByUserId(userName).orElseThrow(
          ()->new CustomException(ErrorCode.INVALID_USER_ID)
      );
    // 방 존재 체크 (명확한 에러코드 사용)
    Room room = roomRepository.findByUserCode(user.getUserCode())
        .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

    List<Box> boxes = boxRepository.findByRoomRoomCode(room.getRoomCode());
    return boxes.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<BoxDto.Response> listBoxesByRoomAll(Pageable pageable,Long roomCode) {
    // 방 존재 체크 (명확한 에러코드 사용)
    if (roomCode == null) {
      Page<Box> boxes=boxRepository.findAll(pageable);
      return boxes.map(BoxDto.Response::from);
    }else{
      Page<Box>boxes=boxRepository.findByRoom_RoomCode(roomCode, pageable);
      return boxes.map(BoxDto.Response::from);
    }
  }

  @Override
    @Transactional(readOnly = true)
    public long countAvailableBoxes(Long roomCode, String boxPossibleStatus) {
        // room 존재 여부 체크
        if (!roomRepository.existsById(roomCode)) {
            throw new CustomException(ErrorCode.ROOM_NOT_FOUND);
        }

        // boxPossibleStatus 예: "Y" 또는 "N"
        return boxRepository.countByRoomRoomCodeAndBoxPossibleStatus(roomCode, boxPossibleStatus);
    }

  @Override
  @Transactional(readOnly = true)
  public long countAvailableBoxes(String userName, String boxPossibleStatus) {

    User user=userRepository.findByUserId(userName).orElseThrow(
        ()->new CustomException(ErrorCode.INVALID_USER_ID)
    );
    // 방 존재 체크 (명확한 에러코드 사용)
    Room room = roomRepository.findByUserCode(user.getUserCode())
        .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

    // boxPossibleStatus 예: "Y" 또는 "N"
    return boxRepository.countByRoomRoomCodeAndBoxPossibleStatus(room.getRoomCode(), boxPossibleStatus);
  }

  @Override
    @Transactional(readOnly = true)
    public long getTotalBoxCurrentCount(Long roomCode) {
        // 룸 존재 체크
        if (!roomRepository.existsById(roomCode)) {
            throw new CustomException(ErrorCode.ROOM_NOT_FOUND);
        }

        Long sum = boxRepository.sumBoxCurrentCountByRoomCode(roomCode);
        return sum != null ? sum : 0L;
    }

    private BoxDto.Response toResponse(Box b) {
        return BoxDto.Response.builder()
                .boxCode(b.getBoxCode())
                .boxName(b.getBoxName())
                .boxContent(b.getBoxContent())
                .boxCurrentCount(b.getBoxCurrentCount())
                .boxWidth(b.getBoxWidth())
                .boxLength(b.getBoxLength())
                .boxHeight(b.getBoxHeight())
                .roomCode(b.getRoom() != null ? b.getRoom().getRoomCode() : null)
                .build();
    }

}