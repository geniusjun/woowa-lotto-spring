# 🎰 로또 운세 실험실 (Lotto Fortune Lab)
매일 로또를 사보고, 오늘의 운세도 확인하는 안드로이드 앱입니다.

## 🎯 프로젝트 배경  
우테코 오픈미션을 위해 제가 정한 작은 주제인, “작은 도전들이 모여 큰 도전을 만든다”는 주제로 진행된 프로젝트입니다.

원래는 로또 미션(3주차)을 Kotlin으로 리팩토링하며,   (https://github.com/geniusjun/kotlin-practice, https://geniusjun4663.tistory.com/54)

“이걸 실제 앱으로 만들고 싶다!”는 생각에서 출발했습니다. 

아래 내용은 이번 프로젝트에서 처음 시도해본 도전 포인트입니다🔥
- Kotlin → Android 앱 개발
- 소셜 로그인 구현해보기(Google Login)  
- 코틀린 기반의 Spring 해보기
- redis 도입해보기 , (https://geniusjun4663.tistory.com/55)
- PostgreSQL 도입해보기 (https://geniusjun4663.tistory.com/57)
- Google Cloud Platform을 이용하여 배포해보기 
- 과거에 실패했던 방식의 무중단 ci/cd 다시 해보기 (https://geniusjun4663.tistory.com/56)

---

## 📸 앱 미리보기

### 메인 화면 · 로또 구매 팝업 · 오늘의 운세 팝업 · 랭킹 팝업
<p align="center">
  <img src="https://github.com/user-attachments/assets/69134a2c-5fef-4c9d-8e7e-d25a4be4ccae" width="30%" />
  <img src="https://github.com/user-attachments/assets/e2f94eb1-8a90-4f69-ab5a-88bca841077b" width="30%" />
  <img src="https://github.com/user-attachments/assets/21ba6eb5-8ac0-4d54-b864-b9983be78f03" width="30%" />
  <img src="https://github.com/user-attachments/assets/63353d9a-46c2-4a71-a403-e6f5b5ead55b" width="30%" />
</p>

---


## 🧪 주요 기능 소개

### 1. 🔐 Google 로그인
- **Google OAuth Client ID 직접 연동**
- 로그인 성공 시 Access Token 발급  
- 서버에 Member 정보 저장 및 초기 금액(₩100,000) 지급  
- Jetpack Compose UI 기반 로그인 구현

---

### 2. 🎟 랜덤 로또 구매  
- 번호 6개 + 보너스 번호 1개 자동 생성  
- 서버에서 구매 번호와 이번 주 당첨번호를 비교하여 **프론트에서는 결과만 반환하게끔 설계**
- 일치한 번호는 주황색으로 강조
- 보너스 번호는 별도 라인으로 구분 표시  
- 결과에 따라 서버로 "당첨 정산 API” 호출 → 잔액 자동 업데이트  
- 잔액 부족 시 팝업 표시

---

### 3. 🔮 오늘의 운세  
- 하루 1번만 확인 가능  
- Redis + LocalDate 기반으로 **서버가 1일 1회 제한 관리**  
- 하루동안 동일한 운세 제공(회원별로 다름)
- 이미 확인한 날이면 “오늘은 이미 운세를 보셨습니다” 팝업 표시

---

### 4. 💰 잔액 관리  
- 초기 금액: ₩100,000  
- 로또 구매 시 –₩1,000  
- 당첨 시 등수별 금액 자동 지급  
- 서버에서 Member가 가진 총 금액을 일관되게 관리  
- 클라이언트는 UI 업데이트만 담당

---

### 5. 🏆 랭킹 관리 
- 1~3등까지 조회하는 TopN 쿼리 사용 api 구현
- 자신의 등수를 조회하는 api 구현
- PostgreSQL의 윈도우 함수를 이용하여 성능이 좋은 쿼리 흐름 구성
---

## ⚙️ 기술 스택

### Frontend (Android)
- Kotlin
- Jetpack Compose
- Google Sign-In

### Backend (Kotlin + Spring)
- Spring Boot 3.5.3
- Spring Security + JWT
- Redis
- PostgreSQL
- JPA

### DevOps / Infra
<img width="921" height="546" alt="image" src="https://github.com/user-attachments/assets/ca6dd77e-2ac1-465e-96e3-a45d04acf3c1" />
- GCP Compute Engine
- Docker & Docker Compose
- Nginx Reverse Proxy
- Blue-Green Deployment
- GitHub Actions CI/CD

---

## 📝 앞으로의 해보면 좋은 요소들

- 유저별 로또 구매 기록 저장  
- 이력 기반 "운세 통계" 기능 추가  
- 이번주 로또 당첨번호 자동 수집 크롤러  
- Kotlin Multiplatform 확장  
- FCM 알림 기능 추가  

---


