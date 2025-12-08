package com.jimwhere.api.alarm.repository;

import com.jimwhere.api.alarm.domain.Alarm;
import com.jimwhere.api.alarm.domain.AlarmType;
import com.jimwhere.api.alarm.domain.IsRead;
import com.jimwhere.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    /* 특정 유저 전체 알람 (최신순) */
    List<Alarm> findByUserOrderByCreatedAtDesc(User user);

    /* 특정 유저의 읽지 않은 알람 (정렬 없이) */
    List<Alarm> findByUserAndIsRead(User user, IsRead isRead);

    /* 특정 유저의 읽지 않은 알람 (최신순)*/
    List<Alarm> findByUserAndIsReadOrderByCreatedAtDesc(User user, IsRead isRead);

    /* 특정 유저의 특정 타입 알람 */
    List<Alarm> findByUserAndType(User user, AlarmType type);
    List<Alarm> findByUserAndTypeOrderByCreatedAtDesc(User user, AlarmType type);

    /* 특정 유저의 읽지 않은 알람 개수 (뱃지) */
    Long countByUserAndIsRead(User user, IsRead isRead);
}
