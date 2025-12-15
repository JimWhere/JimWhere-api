
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

#Database
<br>
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

#Backend
<br>
![Java 21](https://img.shields.io/badge/Java%2021-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot Badge](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=for-the-badge)
![Spring Security Badge](https://img.shields.io/badge/Spring%20Security-6DB33F?logo=springsecurity&logoColor=fff&style=for-the-badge)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![JUnit5 Badge](https://img.shields.io/badge/JUnit5-25A162?logo=junit5&logoColor=fff&style=for-the-badge)


#Frontend
<br>
![Vue.js Badge](https://img.shields.io/badge/Vue.js-4FC08D?logo=vuedotjs&logoColor=fff&style=for-the-badge)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![Pinia Badge](https://img.shields.io/badge/Pinia-FFD859?logo=pinia&logoColor=000&style=for-the-badge)
![Axios Badge](https://img.shields.io/badge/Axios-5A29E4?logo=axios&logoColor=fff&style=for-the-badge)

#API Platform
<br>

![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Swagger UI](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black )


#Tools&External References
<br>
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
<img src="https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white">
<a href="https://www.erdcloud.com/" target="_blank"> <img src="https://img.shields.io/badge/ERD%20Cloud-4285F4?style=for-the-badge&logo=googlecloud&logoColor=white"/> </a>

<br/>
<br>

## 📂 4. **프로젝트 산출물**

- ### WBS
  <details> <summary>WBS</summary>
  https://www.notion.so/2bcaf3cc722280dba57fc13c20ec5faa?v=2bcaf3cc72228139a66a000c74f56483&source=copy_link
    </details> 

- ### 요구사항 명세서
  <details> <summary>요구사항 명세서</summary>
    요구사항 명세서 [바로가기](https://docs.google.com/spreadsheets/d/1ZJ-VvwojNntdTJuB8xYLUpwb5sDpQdYNS2v0t6UabYk/edit?usp=sharing)
    
    <img width="1399" height="579" alt="image" src="https://github.com/user-attachments/assets/a0ced6dc-7f6a-4bbd-aaff-2275bb18dc14" />
    <img width="1399" height="453" alt="image" src="https://github.com/user-attachments/assets/3a6c9d9e-4fa7-4156-878e-1ecbb21677be" />
<br>


  </details> 
    
- ### ERD
  <details> <summary>ERD</summary>
    ERD [바로가기](https://www.erdcloud.com/d/DzLxPEyKdLQzbJgxG)
    <img width="1410" height="800" alt="image" src="https://github.com/user-attachments/assets/625b940c-ff1b-4cec-a857-f0371165d101" />
<br>
  
  </details> 

- ### 화면 설계서
  <details> <summary>화면 설계서</summary>
  화면 설계서 [바로가기] https://www.figma.com/design/n3rEnWyHXGEpve9oCY5dq7/JimWhere?node-id=0-1&p=f&t=kChlrvnGykNybOwg-0
  
    </details> 

- ### 기능 수행 Test 결과
  <details> <summary>회원</summary>
  <img width="1367" height="290" alt="image" src="https://github.com/user-attachments/assets/a178c685-8089-4c8a-8baa-3811a3bbbdcb" />
    </details> 

    <details> <summary>예약, 결제</summary>
    <img width="1370" height="148" alt="image" src="https://github.com/user-attachments/assets/f971838a-6079-4f36-8dad-7761b5bca134" />
    </details> 

    <details> <summary>게시판</summary>
    <img width="1370" height="197" alt="image" src="https://github.com/user-attachments/assets/eaf6c442-b8db-4caf-a54e-8c74e2401693" />
    </details> 

    <details> <summary>룸</summary>
    <img width="1367" height="375" alt="image" src="https://github.com/user-attachments/assets/dfbaa3b9-f6e4-4a9c-9d71-af7fe2bdcdc9" />
    </details> 

    <details> <summary>출입</summary>
  
    </details> 

    <details> <summary>알림</summary>
    <img width="1365" height="434" alt="image" src="https://github.com/user-attachments/assets/5d77770d-4cc3-434a-9930-2532924ec670" />
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

## 👨‍👩‍👧‍👦 5. 팀원 회고

| 이름 | 회고 |
|------|------|
| 신광운 |  |
| 김성태 | Toss Payments 연동은 처음에는 단순히 외부 API를 호출해 결제를 처리하는 작업이라고 생각했습니다. 그러나 실제 구현 과정에서 이는 단순한 API 연동이 아니라, 외부 결제 시스템과 내부 도메인을 어떻게 신뢰성 있게 연결할 것인가에 대한 설계 문제라는 점을 깨닫게 되었습니다.결제 영역은 실패가 발생해서도 안 되고, 결제가 성공했음에도 불구하고 데이터베이스에 기록되지 않는 상황이나 중복 결제가 발생하는 문제 역시 허용될 수 없는 영역이었습니다. 이러한 요구사항을 충족하기 위해 트랜잭션 관리, 결제 상태 기준의 데이터 저장 구조, 중복 요청 방지 등 백엔드 전반의 설계를 다시 고민하게 되었습니다. 이 과정을 통해 단순히 기능을 구현하는 것을 넘어, 안정성과 일관성을 보장하는 백엔드 설계의 중요성을 깊이 체감할 수 있었던 경험이었습니다. |
| 김상재 |  |
| 김성현 |  |
| 박인수 |  |


## ⚠️ 6. Trouble Shooting
  <details> <summary>트러블 슈튕</summary>
  
  </details> 
