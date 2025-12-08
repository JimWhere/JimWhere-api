package com.jimwhere.api.box.domain;


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
@Table(name = "box")
@Getter
@Setter
@NoArgsConstructor
public class Box extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_code")
    private Long boxCode;

    // New: box_name (e.g. a1, b1, c1)
    @Column(name = "box_name", length = 4, nullable = false)
    private String boxName;

    @Column(name = "box_width", nullable = false)
    private Long boxWidth;

    @Column(name = "box_length", nullable = false)
    private Long boxLength;

    @Column(name = "box_height", nullable = false)
    private Long boxHeight;

    @Column(name = "box_possible_status", length = 2)
    private String boxPossibleStatus = "Y";

    @Column(name = "box_content", length = 50)
    private String boxContent;

    @Column(name = "box_current_count")
    private Long boxCurrentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_code", nullable = false)
    private com.jimwhere.api.room.domain.Room room;

}
