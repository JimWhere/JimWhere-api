package com.jimwhere.api.inquiry.controller;

import com.jimwhere.api.inquiry.sevice.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryController {
  InquiryService inquiryService;

  /*@PostMapping("/")
  public ResponseEntity<String> createInquiry(
      @RequestBody
  ){
    return ResponseEntity.ok().body(inquiryService)
  }*/
}
