package com.jimwhere.api.room.domain;


import com.jimwhere.api.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_code")
    private Long roomCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_code", nullable = false)
    private RoomType roomType;

    @Column(name = "room_booking_status", length = 2)
    private String roomBookingStatus = "N";

    @Column(name = "room_booking_possible_status", length = 2)
    private String roomBookingPossibleStatus = "N";

    @Column(name = "room_booking_start_at")
    private java.time.LocalDateTime roomBookingStartAt;

    @Column(name = "room_booking_end_at")
    private java.time.LocalDateTime roomBookingEndAt;

    @Column(name = "user_code")
    private Long userCode;

}
