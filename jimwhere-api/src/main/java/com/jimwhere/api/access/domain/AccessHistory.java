package com.jimwhere.api.access.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import com.jimwhere.api.user.domain.User;
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
import java.time.LocalDateTime;
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
  private  IsOwner isOwner;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VisitPurpose visitPurpose;

  private String visitCode;

  private LocalDateTime accessedAt;

  @Column(nullable = false)
  private AccessResult accessResult;


  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="user_code" ,nullable = false)
  private User user;

  public AccessHistory() {

  }
  public static AccessHistory createAccessHistoryBuilder(IsOwner isOwner,VisitPurpose visitPurpose,String visitCode,User user) {
    return  AccessHistory.builder()
        .isOwner(isOwner)
        .visitPurpose(visitPurpose)
        .visitCode(visitCode)
        .accessedAt(null)
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

  /*public static AccessHistory createAccessHistoryBuilder(String visitorName,String visitPurpose,String storageItemName,Long shippingQuantity,LocalDateTime accessedAt,String storageArea,String visitCode,User user) {
    return  AccessHistory.builder()
        .visitorName(visitorName)
        .visitPurpose(visitPurpose)
        .storageStatus(StorageStatus.N)
        .storageItemName(storageItemName)
        .shippingQuantity(shippingQuantity)
        .accessResult(AccessResult.N)
        .accessedAt(accessedAt)
        .storageArea(storageArea)
        .visitCode(visitCode)
        .user(user)
        .box(null)
        .boxType(null)
        .build();
  }
  public static AccessHistory createAccessHistoryBuilder(
      String visitorName,
      String visitPurpose,
      LocalDateTime accessedAt,
      String visitCode,
      User user
  ) {
    return createAccessHistoryBuilder(visitorName, visitPurpose, null,null, accessedAt,null,visitCode, user);
  }*/
}
