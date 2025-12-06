package com.jimwhere.api.room.domain;

import com.jimwhere.api.global.model.BaseEntity;
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
@Table(name = "room_type")
@Getter
@Setter
@NoArgsConstructor
public class RoomType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_code")
    private Long roomTypeCode;

    @Column(name = "room_type_name", length = 2, nullable = false)
    private String roomTypeName;

    @Column(name = "room_type_count", nullable = false)
    private Long roomTypeCount;

    @Column(name = "room_type_width", nullable = false)
    private Long roomTypeWidth;

    @Column(name = "room_type_length", nullable = false)
    private Long roomTypeLength;

    @Column(name = "room_type_height", nullable = false)
    private Long roomTypeHeight;

}
