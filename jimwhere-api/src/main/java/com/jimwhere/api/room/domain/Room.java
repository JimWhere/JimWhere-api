package com.jimwhere.api.room.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "room_name", length = 50, nullable = false, unique = true)
    private String roomName;

    @Column(name = "room_width", nullable = false)
    private Long roomWidth;

    @Column(name = "room_length", nullable = false)
    private Long roomLength;

    @Column(name = "room_height", nullable = false)
    private Long roomHeight;

    @Column(name = "user_code")
    private Long userCode;

}
