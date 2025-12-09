package com.jimwhere.api.access.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import com.jimwhere.api.inout.domain.InOutHistory;
import com.jimwhere.api.user.domain.User;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class AccessHistory extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long accessHistoryCode;

  @Column(nullable = false)
  private IsOwner isOwner;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VisitPurpose visitPurpose;

  private String visitCode;

  private LocalDateTime accessedAt;

  @Column(nullable = false)
  private Long roomCode;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AccessResult accessResult;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_code", nullable = false)
  private User user;

  @OneToMany(
      mappedBy = "accessHistory",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @Builder.Default
  private List<InOutHistory> inOutHistoryList = new ArrayList<>();

  public AccessHistory() {

  }

  public static AccessHistory createAccessHistoryBuilder(IsOwner isOwner, VisitPurpose visitPurpose,
      String visitCode, Long roomCode,User user) {
    return AccessHistory.builder()
        .isOwner(isOwner)
        .visitPurpose(visitPurpose)
        .visitCode(visitCode)
        .accessedAt(null)
        .roomCode(roomCode)
        .accessResult(AccessResult.N)
        .user(user)
        .build();
  }


  public void updateAccessSuccess(LocalDateTime time) {
    this.accessedAt = time;
    this.accessResult = AccessResult.Y;
  }

  public void updateVisitCode(String qrToken) {
    this.visitCode = qrToken;
  }

  public void updateBasic(IsOwner isOwner, VisitPurpose visitPurpose) {
    this.isOwner = isOwner;
    this.visitPurpose = visitPurpose;
  }

  public void addInOutHistory(InOutHistory inOutHistory) {
    this.inOutHistoryList.add(inOutHistory);
    inOutHistory.setAccessHistory(this);
  }
}
