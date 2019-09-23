# 2019KaKaoAssignment
2019KaKaoAssignment

1. 개발 환경
 (1) 개발 언어 : Java 1.8
 (2) 프레임워크 : Spring Boot 2.1.8 (Gradle)
 (3) 사용 라이브러리 : hibernate-jpa, hibernate-type, javassist, json-simple, gson
 (4) 데이터베이스 : Mysql 8.0.17
 (4) 개발툴 : STS4


2. 문제해결 전략
 (1) DB 설계 
   ① 주어진 데이터를 저장할 테이블 구조 설계
  기간(년도)별 데이터가 주어지므로, ‘디바이스 별 이용률’ 데이터를 저장하는 테이블의 KEY는 ‘기간’으로 설정한다. 접속 디바이스 기기는 미리 정해져 있는 항목이 아니라, csv 파일로부터 읽어 알 수 있는 값들이다. 이처럼 유연한 특성이 필요하기에 ‘디바이스 별 이용률’ 데이터는 json 타입으로 설정할 필요가 있다. 
  csv 파일로부터 읽은 디바이스 기기에 1:1 매칭되는 고유한 디바이스 ID를 생성한다. 이 디바이스 ID를 키로 접속 디바이스 테이블을 생성한다.
   ② 적합한 DBMS 선정
     관계형 DB(RDBMS)에 json 타입을 다룰 수 있는 Mysql 8.0.x 선정
   ③ 테이블 생성 DDL문

CREATE TABLE `userating` (
  `year` varchar(4) NOT NULL,
  `rate` varchar(10) NOT NULL,
  `device_rate` json NOT NULL,
  PRIMARY KEY (`year`)
)

CREATE TABLE `usedevice` (
  `device_id` varchar(12) NOT NULL,
  `device_name` varchar(20) NOT NULL,
  PRIMARY KEY (`device_id`)
)

 (2) 프로젝트 구조
  ① com.banking.controller : 서버 <-> 클라이언트 간 통신 및 데이터타입 정의
   -. BankingController.java : 서버 <-> 클라이언트 간 통신을 제어하는 컨트롤러(URL Mapping)
   -. BankingInfo.java : 서버 <-> 클라이언트 간 송수신 하는 데이터타입(JSON) 정의 
  ② com.banking.entity :  JPA가 entity(테이블)로 관리하는 클래스 정의
   -. UseDevice.java : usedevice 테이블 항목 정의
   -. UseRating.java : userating 테이블 항목 정의
  ③ com.banking.repository : CrudRepository를 상속받아 구현한 인터페이스로, DB Query Repository
   -. UseDeviceRepository.java : UseDevice 클래스를 기반으로 usedevice 테이블과 연관된 쿼리 정의
   -. UseRatingRepository.java : UseRating 클래스를 기반으로 userating 테이블과 연관된 쿼리 정읭
  ④ com.banking.service : Controller에서 호출하며, 실제 서비스 역할을 하는 패키지
   -. IBankingService.java : BankingService.java에서 수행하는 메서드들을 정의하는 인터페이스
   -. BankingService.java : 서비스를 수행하는 메서드를 묶어놓은 클래스
  ⑤ application.properties : 서버 및 DB Connection 설정
  ⑥ build.gradle : gradle dependency 설정

 (3) API 명세서 및 설계
  ① (POST) /add : 데이터 파일을 로컬 파일 시스템에서 로딩하여 적재하는 API
   -. ~/resource에 저장된 csv 데이터를 읽어 DB에 저장한다. 첫 줄은 3번째 인덱스부터 기기라고 읽는다. 디바이스 ID는 “DIS” + 7자리 랜덤한 숫자(중복 허용 X) 조합으로 이루어진다. (데이터 로드는 최초 한번이라 가정, DB의 기존데이터와 디바이스 ID 중복 체크는 하지 않음) 두 번째 줄부터는 연도별 디바이스 이용률 데이터를 읽어 저장한다. 디바이스 이용률 데이터는 json 타입으로 설정하였으므로, 조인이 필요없도록 ‘device_id’, ‘device_name’, ‘rate’ 항목을 저장한다.

  ② (GET) /device : 인터넷뱅킹 서비스 접속기기 목록을 출력하는 API
   -. usedevice 테이블을 조회하여 리턴

  ③ (GET) /device/max : 각 연도별로 인터넷뱅킹을 가장 많이 이용하는 접속기기를 출력하는 API
   -. userating 테이블의 데이터를 조회하여, device_rate의 rate가 가장 큰 데이터를 리턴

  ④ (GET) /device/{year}/max : 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기이름을 출력하는 API
   -. userating 테이블에서 입력받은 년도의 데이터를 조회하여, device_rate의 rate가 가장 큰 데이터를 리턴

  ⑤ (GET) /year/{id}/max : 디바이스 ID를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해를 출력하는 API
   -. userating 테이블의 데이터를 조회하여, 연도 별 입력받은 디바이스의 접속 비율을 조회, 가장 큰 해의 데이터를 리턴 

3. 단위테스트 
 (1) 파일 위치 : src/tset/java/InternetBankingApplicationTests.java
 (2) @SpringBootTest, MockMvc를 통한 데이터 정상 적재/데이터 검증
