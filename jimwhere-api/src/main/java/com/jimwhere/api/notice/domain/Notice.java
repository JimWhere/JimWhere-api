package com.jimwhere.api.notice.domain;

import com.jimwhere.api.global.model.BaseTimeEntity;
import com.jimwhere.api.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Builder
@Entity
@Getter
@AllArgsConstructor
public class Notice extends BaseTimeEntity {
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
  public static Notice createNotice( String noticeTitle, String noticeContent, User  user) {
    return Notice.builder()
        .noticeTitle(noticeTitle)
        .noticeContent(noticeContent)
        .isDeleted(false)
        .user(user)
        .build();

  }
  public void deleteNotice() {
    this.isDeleted =true;
  }
  public void updateTitle(String title) {
    this.noticeTitle = title;
  }
  public void updateContent(String content){
    this.noticeContent = content;
  }

}
