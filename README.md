![header](https://capsule-render.vercel.app/api?type=waving&color=0070C0&height=250&section=header&text=미주정복&fontSize=90&fontColor=ffffff)

종합설계1   
: 주식 배당금을 시각적으로 정리하여 보여주고, 주식을 관리할 수 있게 해주는 애플리케이션  

![미주정복 시연](https://github.com/Yoonjin-Lee/us-dividend/assets/71547678/49d10ea3-60db-4dbb-9627-3cdfac504266)

개발 기간
---
2023.03. ~ 2023.06.

개발 환경
---
![android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)  

  Jetpack Compose | Kotlin | Github  
  Retrofit2 | Okhttp3 라이브러리 사용
  
  MVVM 모델 + Hilt(DI) 사용 구조로 리팩토링 진행
  - LiveData를 이용한 observer 패턴 구현
  - View의 LifeCycle에 따른 데이터 수신
  - 리팩토링 과정 블로그 작성

Room 데이터베이스 사용
- 내부 정보를 저장하여, 서버 없이 기능 사용 가능

프로젝트 소개
---
#### 얼마나 받았지? 이걸로 재투자해야 하는데..  
#### 그 때 환율이 얼마였더라..?
#### 고민 그만! 이젠 미주정복해보자!!
😀 매수한 주식 입력하고!  
😃 매수 당시 환율 입력!  
😄 매달 받은 배당 확인하고, 차트로 확안하자!  
😁 배당 기록을 엑셀 파일로 저장하기    

#### 기능
- 달러 환율 및 보유 달러 정보 제공 기능
- 보유 주식 저장 기능
- 월별 배당금 정리 기능
- 월별 배당금 BarChart 및 엑셀 파일 제공 기능
- Kakao 로그인 및 Naver 로그인 기능

#### 프로토타입
디자인 - 이윤진   
[Figma](https://www.figma.com/file/ROVhRrEokhOPhvCpfMwPkY/UI-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%84%A4%EA%B3%84?type=design&node-id=2%3A97&mode=dev)  

#### 시연 영상
[![시연영상](http://img.youtube.com/vi/dWHUoTT2pQ0/0.jpg)](https://youtu.be/dWHUoTT2pQ0)
