# TCP-IP-Socket
Android Socket 통신을 공부합니다.
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
### 소켓 경로 설정(Application)
~~~java
private Socket socket;
@Override
protected void onCreate(Bundle savedInstanceState) {
  // 설명의 편의를 위해 onCreate()메서드에 추가하였으나,
  // 꼭 onCreate() 메서드에 위치할 필요는 없을 것 같습니다.
  try {
    socket = IO.socket("SERVER URL");
  } catch(URISyntaxException e) {
    e.printStackTrace();
  }
}
~~~
### 전체적인 사용 코드
~~~java
socket = IO.socket("http://localhost");
socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

  @Override
  public void call(Object... args) {
    socket.emit("foo", "hi");
    socket.disconnect();
  }

}).on("event", new Emitter.Listener() {

  @Override
  public void call(Object... args) {}

}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

  @Override
  public void call(Object... args) {}

});
socket.connect();
~~~
### 서버에 메시지 전달(Json 기준)
~~~java
// Sending an object
JSONObject obj = new JSONObject();
obj.put("hello", "server");
obj.put("binary", new byte[42]);
socket.emit("foo", obj);
~~~

### 서버에서 메시지 받기(Json 기준)
~~~java
// Receiving an object
socket.on("foo", new Emitter.Listener() {
  @Override
  public void call(Object... args) {
    JSONObject obj = (JSONObject)args[0];
  }
});
~~~
## kotlin ver
### 소캣 경로 설정(kotlin)
~~~kotlin
lateinit val mSocket : Socket
override protected fun onCreate(savedInstanceState : Bundle) {
  // 설명의 편의를 위해 onCreate()메서드에 추가하였으나,
  // 꼭 onCreate() 메서드에 위치할 필요는 없을 것 같습니다.
  try {
    mSocket = IO.socket("SERVER URL")
  } catch(e : URISyntaxException ) {
    e.printStackTrace()
  }
}
~~~
### 소켓 이벤트(kotlin)
~~~kotlin
override protected fun onCreate(savedInstanceState : Bundle) {
  // IO.socet()메서드와 mSocket.connect() 메서드사이에 일반적으로 작성하는 것 같습니다.
  mSocket.on(Socket.EVENT_CONNECT, onConnect);
  mSocket.on("chat-message", onMessageReceived);
}
~~~
### 전체적인 사용 코드(kotlin)
~~~kotlin
socket = IO.socket("http://localhost")
socket.on(Socket.EVENT_CONNECT, Emitter.Listener() {

    override public fun call(Object... args) {
    socket.emit("foo", "hi")
    socket.disconnect()
  }

}).on("event", Emitter.Listener() {

  override public fun call(Object... args) {}

}).on(Socket.EVENT_DISCONNECT, Emitter.Listener() {

  override public fun call(Object... args) {}

})
socket.connect()
~~~
### 서버에 메시지 전달(Json 기준)
~~~kotlin
// Sending an object
val JSONObject obj = JSONObject()
obj.put("hello", "server")
obj.put("binary", byte[42])
socket.emit("foo", obj)
~~~

### 서버에서 메시지 받기(Json 기준)
~~~kotlin
// Receiving an object
socket.on("foo", Emitter.Listener() {
  override public fun call(Object... args) {
    val JSONObject obj = (JSONObject)args[0]
  }
});
~~~