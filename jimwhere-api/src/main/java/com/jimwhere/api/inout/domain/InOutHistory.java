package com.jimwhere.api.inout.domain;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.box.domain.Box;
import com.jimwhere.api.global.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
@AllArgsConstructor

public class InOutHistory extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long inOutHistoryCode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private InOutType inOutType;

  @Column(nullable = false)
  private String inOutName;

  @Column(nullable = false)
  private Long inOutQuantity;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="box_code",nullable = true)
  private Box box;

  @ManyToOne(fetch =FetchType.LAZY)
  @JoinColumn(name="access_history_code",nullable = false)
  private AccessHistory accessHistory;


  public InOutHistory() {

  }
  public static InOutHistory createInOutHistory(
      InOutType inOutType,
      String inOutName,
      Long inOutQuantity,
      Box box
  ){
    return InOutHistory.builder()
        .inOutType(inOutType)
        .inOutName(inOutName)
        .inOutQuantity(inOutQuantity)
        .box(box)
        .build();
  }

  public void updateInOutType(InOutType inOutType) {
    this.inOutType = inOutType;
  }
  public void updateInOutName(String inOutName){
    this.inOutName = inOutName;
  }
  public void updateInOutQuantity(Long inOutQuantity){
    this.inOutQuantity = inOutQuantity;
  }
  public void updateInOutHistory(InOutType inOutType,String inOutName,Long inOutQuantity){
    this.inOutType = inOutType;
    this.inOutName = inOutName;
    this.inOutQuantity = inOutQuantity;
  }
  public void setAccessHistory(AccessHistory accessHistory) {
    this.accessHistory = accessHistory;
  }
}
