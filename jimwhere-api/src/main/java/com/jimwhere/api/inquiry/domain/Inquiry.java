package com.jimwhere.api.inquiry.domain;

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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Inquiry extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long inquiryCode;

  @Column(nullable = false)
  private String inquiryTitle;

  @Column(nullable = false)
  private String inquiryContent;

  private String inquiryAnswer;

  private LocalDateTime answeredAt;

  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_code" ,nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="admin_code")                    // inquiry.admin_code
  private User admin;

  public Inquiry(){

  }


  public static Inquiry createInquiry(String inquiryTitle,String inquiryContent, User user) {
    return Inquiry.builder()
        .inquiryTitle(inquiryTitle)
        .inquiryContent(inquiryContent)
        .inquiryAnswer(null)
        .answeredAt (null)
        .isDeleted(false)
        .user(user)
        .admin(null)
        .build();

  }
  public void answerInquiry(String answer,User admin){
    this.inquiryAnswer = answer;
    this.answeredAt = LocalDateTime.now();
    this.admin = admin;
  }
  public void deleteInquiry(){
    this.isDeleted = true;
  }

}
