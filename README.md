
#  JimWhere – 창고/보관함 예약 & 결제 서비스

JimWhere는 사용자가 원하는 창고/보관함을 기간제로 예약하고
결제 → 출입 관리 → 이용 내역 확인까지 한 번에 처리할 수 있는
실사용형 B2C 공간 대여 플랫폼입니다.
<br>

<div align="center">
<img width="600" height="500" alt="jimwhere_" src="https://github.com/user-attachments/assets/47da31df-2438-41a1-af7a-ead4676c30bb" />
</div>

---
## 🤣  목차 (Table of Contents)

1. [👩‍👧‍👦 멤버 소개](#-1-멤버-소개)  
2. [🖼️ 프로젝트 개요](#️-2-프로젝트-개요)  
3. [🚀 주요 기능 요약](#-3-주요-기능-요약)  
4. [🗂️ 프로젝트 산출물](#️-4-프로젝트-산출물)
5. [😒 회고록](#️-5-회고록)
6. [⚠️ Trouble Shooting](#️-5-trouble-shooting)  

## 👩‍👧‍👦 1. 멤버 소개

<div align="center">

| 신광운 | 김성태 | 김상재 | 김성현 | 박인수 |
|--------|--------|--------|--------|--------|
|<img width="150" height="150" src = "https://github.com/user-attachments/assets/7eb87cad-7554-4341-bd20-20f78e7ef2da">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/699e02b7-546d-4181-a31b-71ae0502e458">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/b9e64560-6418-426c-b4f3-0c4d5fa829f1">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/2a199074-db5a-4ee7-a638-f322fd9843ca">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/a7c3529e-983a-4233-8f26-bebd8e4784d3">|

</div>

## 🖼️ 2. **프로젝트 개요**

JimWhere는 사용자가 원하는 **창고/보관함을 기간제로 예약**하고,
**Toss Payments 결제를 통해 안전하게 결제 및 관리**할 수 있는 플랫폼입니다.

또한 이용자는
✔ 문의 등록
✔ 공지사항 조회
✔ 예약·결제 내역 관리
까지 통합적으로 경험할 수 있습니다.
<br>


## 🚀 3. **주요 기능 요약**

### 👤 회원 (User)

* 회원가입 (사업자 정보, 아이디, 비밀번호, 휴대전화)
* 사업자 번호 / 연락처 인증
* 로그인 / 로그아웃

🧩 예약 (Reservation)

* 룸 / 섹션 / 박스 단위 대여 공간 예약
* 예약 기간 설정
* 예약 코드(reservationCode) 자동 생성
* 나의 예약 목록 조회
---

### 💳 **결제(Payment – Toss Payments API 연동)**

* 결제 준비
* 결제 승인
* 결제 취소
* 결제 성공 시 **PaymentHistory 자동 저장**
* 내 결제 내역 조회
* 관리자 전체 결제 내역 조회

---

### 📄 **문의( Inquiry ) / 공지( Notice )**

#### 문의 (Inquiry)
* 문의 작성
* 문의 목록 조회
* 문의 상세 조회
* 관리자 답변 등록

#### 공지사항 (Notice)
* 공지 등록 / 수정 / 삭제
* 공지 목록 조회
* 공지 상세 조회
---

### 🔔 **알림(Alarm)**

* 알림 (Alarm)
* 알림 템플릿 기반 자동 발송
* 이벤트 발생 시 DB 알림 저장
* 회원 알림 목록 조회 / 삭제
* 관리자 알림 템플릿 CRUD

<br>

## 🛠️ 5. **Tech Stack**

### Backend

* Java 21
* Spring Boot 3.x
* Spring Security + JWT
* JPA(Hibernate)
* MariaDB
* Redis
* Gradle

### Infra / Tools

* Docker, Docker Compose
* Swagger (OpenAPI 3)
* IntelliJ IDEA

<br>

## 📂 4. **프로젝트 산출물**

- ### WBS
  <details> <summary>WBS</summary>
  https://www.notion.so/2bcaf3cc722280dba57fc13c20ec5faa?v=2bcaf3cc72228139a66a000c74f56483&source=copy_link
    </details> 

- ### 요구사항 명세서
  <details> <summary>요구사항 명세서</summary>
    요구사항 명세서 [바로가기](https://docs.google.com/spreadsheets/d/1ZJ-VvwojNntdTJuB8xYLUpwb5sDpQdYNS2v0t6UabYk/edit?usp=sharing)<br>

    
    </details> 
    
- ### ERD
  <details> <summary>ERD</summary>
    ERD [바로가기](https://www.erdcloud.com/d/DzLxPEyKdLQzbJgxG)<br>
  
    </details> 

- ### 화면 설계서
  <details> <summary>화면 설계서</summary>
  화면 설계서 [바로가기] https://www.figma.com/design/n3rEnWyHXGEpve9oCY5dq7/JimWhere?node-id=0-1&p=f&t=kChlrvnGykNybOwg-0
  
    </details> 

- ### 기능 수행 Test 결과
  <details> <summary>회원</summary>
  
    </details> 

    <details> <summary>예약, 결제</summary>
  
    </details> 

    <details> <summary>게시판</summary>

    </details> 

    <details> <summary>룸</summary>
  
    </details> 

    <details> <summary>출입</summary>
  
    </details> 

    <details> <summary>알림</summary>

    </details> 

- ### 아키텍처 구조도
  <details> <summary>아키텍처 구조도</summary>
  
    </details> 

- ### CI/CD 테스트 결과
  <details> <summary>CI/CD 테스트 결과</summary>
  
    </details> 

- ### API 명세서
  <details> <summary>API 명세서</summary>
  
    </details> 
<br>

## 😒 5. 회고


## ⚠️ 6. Trouble Shooting
  <details> <summary>트러블 슈튕</summary>
  
  </details> 
