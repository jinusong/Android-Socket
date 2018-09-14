# TCP-IP-Socket
Android Socket 통신을 공부합니다.
# Socket 통신 사용법
## 세팅
### Maven
~~~Gradle
<dependencies>
  <dependency>
    <groupId>io.socket</groupId>
    <artifactId>socket.io-client</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
~~~
### Gradle
~~~gradle
implements ('io.socket:socket.io-client:1.0.0') {
  // excluding org.json which is provided by Android
  exclude group: 'org.json', module: 'json'
}
~~~
## 사용법
### 소켓 생성(Application)
~~~java
private Socket mSocket;
@Override
protected void onCreate(Bundle savedInstanceState) {
  // 설명의 편의를 위해 onCreate()메서드에 추가하였으나,
  // 꼭 onCreate() 메서드에 위치할 필요는 없을 것 같습니다.
  try {
    mSocket = IO.socket("SERVER URL");
    mSocket.connect();
  } catch(URISyntaxException e) {
    e.printStackTrace();
  }
}
~~~
## 소켓 연결
~~~java
@Override
protected void onCreate(Bundle savedInstanceState) {
  // IO.socet()메서드와 mSocket.connect() 메서드사이에 일반적으로 작성하는 것 같습니다.
  mSocket.on(Socket.EVENT_CONNECT, onConnect);
  mSocket.on("chat-message", onMessageReceived);
}
~~~
## 메시지를 주고 받아 이벤트 처리
~~~java
@Override
protected void onCreate(Bundle savedInstanceState) {
  // IO.socet()메서드와 mSocket.connect() 메서드사이에 일반적으로 작성하는 것 같습니다.
  mSocket.on(Socket.EVENT_CONNECT, onConnect);
  mSocket.on("chat-message", onMessageReceived);
}
// Socket서버에 connect 된 후, 서버로부터 전달받은 'Socket.EVENT_CONNECT' Event 처리.
private Emitter.Listener onConnect = new Emitter.Listener() {
  @Override
  public void call(Object... args) {
    // your code...
  }
};
// 서버로부터 전달받은 'chat-message' Event 처리.
private Emitter.Listener onMessageReceived = new Emitter.Listener() {
  @Override
  public void call(Object... args) {
    // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.
    JSONObject receivedData = (JSONObject) args[0];
    // your code...
  }
};
~~~
## 서버에 메시지 전달
~~~java
// 서버로 이벤트를 전송하는 부분에 적절히 추가하시면 될 것 같아요.
JSONObject data = new JSONObject();
try {
  data.put("key1", "value1");
  data.put("key2", "value2");
  mSocket.emit('event-name', data);
} catch(JSONException e) {
  e.printStackTrace();
}
~~~
# kotlin ver
