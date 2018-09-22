# Socket

> TCP/IP Socket과 Socket io 사용법

> 작성자 : 송진우

> Present Time : 2018-09-20 (THU)

## 1. Socket 이란

두 프로그램이 네트워크를 통해 서로 통신을 수행 할 수 있도록 양쪽에 생성되는 링크의 단자입니다.

- 데이터를 캡슐화하여 전달 가능
- UNIX에서의 입출력 메소드의 표준인 개방/읽기/쓰기/닫기 메커니즘

![Socket 이미지](http://2.bp.blogspot.com/-ztRG8ei0eq4/TfBlm38bldI/AAAAAAAAAKw/sFf8d03WzYs/s1600/scheme.jpg)

## 2. TCP/IP Socket
TCP 는 두 프로그램 간의 통신이 처음 시작될 때부터 끝날 때까지 계속 연결을 유지하는 연결지향(Connection oriented) 방식입니다.

- 스트림 소켓 방식
- 양쪽 어플리케이션 모두 데이터 주고 받기 가능
- 흐름제어등을 보장해 주며 송신된 순서에 따른 중복되지 않은 데이터를 수신 가능
- IP와 포트 번호로 소켓을 연결하면 통신 시작



### 2.1 통신 절차

![통신 절차](http://www.a2big.com/_images/product/web/main/jay/2012-07-2616:21:00.JPG)

* TCP Client
 1) 소켓을 생성합니다.
 2) 서버로 connect() 합니다.
 3) 접속이 성공됐다면 read 및 write 함수를 통해 서버와 통신을 주고 받습니다.
 4) 사용을 마치면 close로 소켓을 닫습니다.
 
* TCP Server
 1) 듣기 소켓을 생선합니다.
 2) bind합니다. (내선 부여)
 3) listen합니다. (내선 연결)
 4) accept() 클라이언트가 connect할 경우 소켓을 생성 하고 연결한다.
 5) read와 write 함수를 이용해 메시지를 주고 받는다.
 6) 사용된 연결 소켓을 닫는다.
 7) 사용을 마쳤을 경우 듣기 소켓을 닫는다.

### 2.2 클라이언트 통신과정
