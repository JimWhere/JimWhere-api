package com.jimwhere.api.box.domain;

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
@Table(name = "box_type")
@Getter
@Setter
@NoArgsConstructor
public class BoxType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_type_code")
    private Long boxTypeCode;

    @Column(name = "box_type_name")
    private String boxTypeName;

    @Column(name = "box_type_width")
    private Long boxTypeWidth;

    @Column(name = "box_type_length")
    private Long boxTypeLength;

    @Column(name = "box_type_height")
    private Long boxTypeHeight;

}
