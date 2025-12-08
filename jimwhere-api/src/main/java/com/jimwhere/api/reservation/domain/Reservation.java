package com.jimwhere.api.reservation.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import com.jimwhere.api.room.domain.Room;
import com.jimwhere.api.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "reservation",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reservation_order_id",
                        columnNames = "order_id"
                )
        },
        indexes = {
                @Index(name = "idx_reservation_user", columnList = "user_code"),
                @Index(name = "idx_reservation_room", columnList = "room_code"),
                @Index(name = "idx_reservation_order_id", columnList = "order_id")
        }
)
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_code")
    private Long reservationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_code", nullable = false)
    private Room room;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "reservation_amount", nullable = false)
    private Long reservationAmount;

    @Column(name = "order_id") // CRUD 테스트 때문에 임시로 nullable = false 제거
    private String orderId;


    //예약 생성 메서드
    public static Reservation create(
            User user,
            Room room,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long amount
    ) {
        Reservation reservation = new Reservation();
        reservation.user = user;
        reservation.room = room;
        reservation.startAt = startAt;
        reservation.endAt = endAt;
        reservation.reservationAmount = amount;
        return reservation;
    }

    public void updateOrderId(String orderId) {
        this.orderId = orderId;
    }
}
