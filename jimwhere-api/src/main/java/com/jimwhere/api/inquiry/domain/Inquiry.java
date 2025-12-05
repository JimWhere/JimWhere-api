package com.jimwhere.api.inquiry.domain;

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
import lombok.Getter;

@Entity
@Getter
public class Inquiry {
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



}
