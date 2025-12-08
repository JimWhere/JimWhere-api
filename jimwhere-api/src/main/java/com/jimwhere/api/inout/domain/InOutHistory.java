package com.jimwhere.api.inout.domain;

import com.jimwhere.api.access.domain.AccessHistory;
import com.jimwhere.api.box.domain.Box;
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

@Entity
@Builder
@AllArgsConstructor
public class InOutHistory {
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
  @JoinColumn(name="box_code",nullable = false)
  private Box box;

  @ManyToOne(fetch =FetchType.LAZY)
  @JoinColumn(name="access_history_code",nullable = false)
  private AccessHistory accessHistory;


  public InOutHistory() {

  }
  public InOutHistory createInOutHistory(
      InOutType inOutType,
      String inOutName,
      Long inOutQuantity,
      Box box,
      AccessHistory accessHistory
  ){
    return InOutHistory.builder()
        .inOutType(inOutType)
        .inOutName(inOutName)
        .inOutQuantity(inOutQuantity)
        .box(box)
        .accessHistory(accessHistory)
        .build();
  }
}
