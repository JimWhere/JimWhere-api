package com.jimwhere.api.notice.domain;

import com.jimwhere.api.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long noticeCode;

  @Column(nullable = false)
  private String noticeTitle;

  @Column(nullable = false)
  private String noticeContent;

  @Column(nullable = false)
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_code" ,nullable = false)
  private User user;


  public Notice() {
  }
  @Builder
  private Notice(Long  noticeCode, String noticeTitle, String noticeContent, User  user) {
      this.noticeCode = noticeCode;
      this.noticeTitle = noticeTitle;
      this.noticeContent = noticeContent;
      this.isDeleted = false;
      this.user=user;
  }

}
