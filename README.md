
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
5. [👨‍👩‍👧‍👦 팀원 회고](#️-5-회고록)
6. [⚠️ Trouble Shooting](#️-5-trouble-shooting)  

## 👩‍👧‍👦 1. 멤버 소개

<div align="center">

| 신광운 | 김성태 | 김상재 | 김성현 | 박인수 |
|--------|--------|--------|--------|--------|
|<img width="150" height="150" src = "https://github.com/user-attachments/assets/7eb87cad-7554-4341-bd20-20f78e7ef2da">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/699e02b7-546d-4181-a31b-71ae0502e458">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/b9e64560-6418-426c-b4f3-0c4d5fa829f1">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/2a199074-db5a-4ee7-a638-f322fd9843ca">|<img width="150" height="150" src = "https://github.com/user-attachments/assets/a7c3529e-983a-4233-8f26-bebd8e4784d3">|

</div>

## 🖼️ 2. **프로젝트 개요**

JimWhere는 사용자가 필요에 맞는 창고 및 보관함 공간을 선택하고, 원하는 기간 동안 예약하여 이용할 수 있는 공간 대여 플랫폼입니다. 단순한 공간 선택을 넘어, 실제 이용 흐름을 고려한 예약 관리와 사용자·관리자 기능을 함께 제공하는 것을 목표로 설계되었습니다.

사용자는 공간 유형과 위치, 이용 기간을 기준으로 보관 공간을 선택하고 예약을 생성할 수 있으며, 예약 정보는 기간 기반으로 관리되어 중복 예약을 방지합니다. 또한 결제가 완료된 예약만 실제 이용 내역으로 확정되도록 하여, 서비스 운영 관점에서의 데이터 일관성과 신뢰성을 확보했습니다.

관리자 입장에서는 전체 예약 현황과 이용 기간을 한눈에 파악하고, 결제 완료된 예약을 기준으로 공간 운영 및 관리가 가능하도록 구성되어 있습니다. 이를 통해 실제 서비스 환경에서 필요한 운영 효율성과 관리 편의성을 고려한 구조를 구현했습니다.

JimWhere는 예약, 결제, 사용자 관리, 관리자 기능을 명확히 분리하여 설계되었으며, 향후 기능 확장과 유지보수를 고려한 구조를 기반으로 한 공간 대여 서비스입니다.

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
  <img width="999" height="797" alt="image" src="https://github.com/user-attachments/assets/18072f38-4521-4f2b-9e34-0c63aacf811f" />
    </details> 


- ### API 명세서
  <details> <summary>API 명세서</summary>
  <img width="916" height="522" alt="image" src="https://github.com/user-attachments/assets/bab19eb7-84e8-41ba-b4da-4bb487d7b0fb" />

  <img width="923" height="480" alt="image" src="https://github.com/user-attachments/assets/710dde45-9583-45b2-8cb0-4670e2f3f153" />
  <img width="916" height="555" alt="image" src="https://github.com/user-attachments/assets/90349ced-f4dd-4bba-afc8-dc550ca37339" />
  <img width="913" height="531" alt="image" src="https://github.com/user-attachments/assets/13108329-ed9e-4314-9cb2-95dba953cedd" />
  <img width="910" height="551" alt="image" src="https://github.com/user-attachments/assets/079ae45e-bdea-4a30-8475-f6f8fcccc346" />
  <img width="909" height="556" alt="image" src="https://github.com/user-attachments/assets/f508777d-aee4-4094-8f88-726a44bb7a5a" />
  <img width="915" height="516" alt="image" src="https://github.com/user-attachments/assets/92b2aa74-ee10-42cb-b5d6-80a761f57908" />


    </details> 
<br>

## 👨‍👩‍👧‍👦 5. 팀원 회고

| 이름 | 회고 |
|------|------|
| 신광운 |  |
| 김성태 | Toss Payments 연동은 처음에는 단순히 외부 API를 호출해 결제를 처리하는 작업이라고 생각했습니다. 그러나 실제 구현 과정에서 이는 단순한 API 연동이 아니라, 외부 결제 시스템과 내부 도메인을 어떻게 신뢰성 있게 연결할 것인가에 대한 설계 문제라는 점을 깨닫게 되었습니다.결제 영역은 실패가 발생해서도 안 되고, 결제가 성공했음에도 불구하고 데이터베이스에 기록되지 않는 상황이나 중복 결제가 발생하는 문제 역시 허용될 수 없는 영역이었습니다. 이러한 요구사항을 충족하기 위해 트랜잭션 관리, 결제 상태 기준의 데이터 저장 구조, 중복 요청 방지 등 백엔드 전반의 설계를 다시 고민하게 되었습니다. 이 과정을 통해 단순히 기능을 구현하는 것을 넘어, 안정성과 일관성을 보장하는 백엔드 설계의 중요성을 깊이 체감할 수 있었던 경험이었습니다. |
| 김상재 | 새롭게 팀을 만들어 작업할 때 마다 각자의 성향과 실력이 달라 소통의 중요성을 항상 느낍니다 초반에는 많이 흔들렸으나 얘기를 계속 나누고 작업을나누고 진행상황을 서로 소통하면서 점차 나아지는게 보여서 다행이었습니다 짧은 프로젝트기간 내에 api , vue , ci/cd 까지 전부 하려니 조금 벅찬 느낌도 많이 들었고 어찌저찌 ci / cd 까지 마쳐놓긴 했으나 많이 미흡하고 지식이 부족하다는걸 뼈저리게 느꼈습니다. 협업을 하며 기간을 잘 정해 주어진 목표를 주어진 시간안에 해결하기 위해 적절하게 분배하고 확실하게 작업을 나누어서 하는게 중요하다는걸 많이 느꼈으며, 지식과 경험이 많이 부족해 결과도 미흡하고 만족스럽지 않지만 최종 프로젝트 진행시에는 조금 더 체계적이고 확실하게 구분하여 작업진행에 차질이 없도록 하게 해야한다는 걸 느낀 좋은 경험이었던 것 같습니다 |
| 김성현 | 입출고 및 재고를 저장관리하는 방과 박스의 DB 설계부터 예약 기능구현까지를 맡으면서 엔티티 설계의 중요성에 대해 다시한번 깊게 체감하였습니다. 같은 도메인의 엔티티여도, 기록의 성격을 띄는지 타입의 성격을 띄는지 에 따라 엔티티를 구분할수있는것들은 최대한 구분을 해야할 뿐 아니라 엔티티 간의 참조 관계 혹은 제약조건이 제대로 보장되어있지 않으면 데이터 무결성이 훼손될 가능성이 크다는 것도 다시한번 느꼈습니다. 맡은 도메인이 여러 팀원의 기능구현에 영향을 끼치는 상황이었어서 깃허브를 통한 업데이트 이력과 관련 내용을 제때 전달하는게 매우 중요했었기 때문에 그 어떤 프로젝트를 수행할 때보다 많은 소통이 필요했던것 같습니다. 또한, 이력관리 깃허브를 운용하는 부분에 대해서 전체 개발을 기능단위로 쪼개서 임시로 브랜치를 생성하고 머지 하는 과정을 수차례 거치면서, 팀원과의 협업개발에 대한 심도있는 이해를 해볼 수 있는 기회가 되었습니다. 전반적으로 실제 서비스 개발이 시작되니까 화면설계와 백엔드 api 로직 설계 가 수시로 업데이트 되어야하는 상황이 불가피했고, 각 세부 업데이트가 될때마다 팀원과 소통을 해보려는 노력을 조금 더 기울여보는 기회가 되어 좋습니다. 마지막으로, 저보다 더 많은 고생을 해준 모든 팀원분들께 진심으로 감사하다는 말씀 전하고 싶고, 최종프로젝트에서는 스스로 조금더 발전된 모습으로 팀에 기여하고 싶습니다. |
| 박인수 |  |


## ⚠️ 6. Trouble Shooting
  <details> <summary>트러블 슈튕</summary>
  
  </details> 
