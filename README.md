##개발툴 : Spring Tool Suite 4
##JDK : 1.8
##DB : hsqldb
##구성 : SpringBoot + JPA + lombok
##프로젝트 : 
      1. SpringBoot로 pg 수신데이터와 DB 데이터를 대사하는 REST API를 구현하였으며 application.yml에 Tomcat 포트(8013)를 설정 하였습니다.
      2. DB는 in memory db를 사용하였으며 JPA로 데이터를 핸들링 하도록 구성하였습니다.
      3. 서버 기동시 /resources/import.sql을 import하면서 3개의 테이블과 데이터를 생성하며 가상계좌/신용카드 대사 데이터는 PG사에 수신하여 테이블에 저장하였다고 가정합니다.
##업무별 설명
      1. 신용카드 대사
        - pg사로 부터 받은 승인일/취소일 기준데이터를 가져와 주문내역과 TID로 대사합니다.
        - 금액 불일치와 승인/취소 상태 불일치를 대사합니다.
      2. 가상계좌 대사
        - pg사로 부터 받은 입금일/취소일 기준데이터를 가져와 주문내역과 TID로 대사합니다.
        - 가상계좌는 입금취소가 발생할 수 있기 때문에 취소건에 대하여 로깅합니다.(입금성공으로 물건을 배송하였으나 취소 되어 재입금을 받아야 할 수 있습니다.)
      3. 일대사/월대사
        - 일대사 : 실행일 전일자에 대해서 대사합니다.
        - 월대사 : 실행일의 전월의 01일부터 31일까지 데이터를 가져와 대사합니다.
      4. 운영업무
        - 일대사가 이상이 없다면 월대사는 총건수와 총금액만 비교할 수 있습니다.
        - pg사 수신데이터 테이블에 대사성공여부 컬럼을 추가하고 해당 컬럼의 불일치 여부를 select하여 alert(문자, 알림톡, 슬랙봇) 할 수 있습니다.
        - pg사의 데이터가 DB의 데이터 보다 많은 경우과 적은 경우 분리해서 각 케이스를 분석하고 관리합니다.
## 테스트 API
      1. 신용카드 일대사 API : http://localhost:8213/v1/day-compare/card
        - request body : {"processDt":"20220121","pgCd":"TOSS"}, {"processDt":"20220121","pgCd":"INICIS"}
      2. 신용카드 월대사 API : http://localhost:8213/v1/month-compare/card
        - request body : {"processDt":"20220221","pgCd":"TOSS"}, {"processDt":"20220221","pgCd":"INICIS"}
      3. 가상계좌 일대사 API : http://localhost:8213/v1/day-compare/vacct
        - request body : {"processDt":"20220121","pgCd":"TOSS"}, {"processDt":"20220121","pgCd":"INICIS"}
      4. 가상계좌 일대사 API : http://localhost:8213/v1/month-compare/vacct
        - request body : {"processDt":"20220221","pgCd":"TOSS"}, {"processDt":"20220221","pgCd":"INICIS"}
    
###감사합니다.

"# compareAPI" 
"# compareAPI" 
