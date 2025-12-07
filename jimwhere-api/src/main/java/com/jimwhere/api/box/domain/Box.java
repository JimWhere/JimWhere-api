package com.jimwhere.api.box.domain;


import com.jimwhere.api.global.model.BaseTimeEntity;
import com.jimwhere.api.room.domain.Room;
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
@Table(name = "box")
@Getter
@Setter
@NoArgsConstructor
public class Box extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_code")
    private Long boxCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_type_code", nullable = false)
    private BoxType boxType;

    @Column(name = "box_width_location")
    private Long boxWidthLocation;

    @Column(name = "box_length_location")
    private Long boxLengthLocation;

    @Column(name = "box_possible_status", length = 2)
    private String boxPossibleStatus;

    @Column(name = "box_booking_possible_status", length = 2)
    private String boxBookingPossibleStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_code", nullable = false)
    private Room room;

    @Column(name = "room_type_code")
    private Long roomTypeCode;

}
