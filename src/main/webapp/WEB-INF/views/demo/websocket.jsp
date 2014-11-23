<%--
  Created by IntelliJ IDEA.
  User: yamorn
  Date: 2014/11/23
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>webSocket demo</title>
  <script src="/resources/jquery/jquery-1.11.1.js"></script>
  <script src="/resources/websocket/sockjs-0.3.4.js"></script>
  <script src="/resources/websocket/stomp.js"></script>
  <script type="text/javascript">
    var stompClient = null;

    function setConnected(connected) {
      document.getElementById('connect').disabled = connected;
      document.getElementById('disconnect').disabled = !connected;
      document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
      document.getElementById('response').innerHTML = '';
    }

    function connect() {
      var socket = new SockJS('/stompEnd');
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame+"==============");
        stompClient.subscribe('/queue/${sessionScope.get("USER")}', function(greeting){
          console.log(greeting+"===")
          showGreeting(JSON.parse(greeting.body).content);
        });
      });
    }

    function disconnect() {
      stompClient.disconnect();
      setConnected(false);
      console.log("Disconnected");
    }

    function sendName() {
      var name = document.getElementById('name').value;
      stompClient.send("/app/hello", {}, JSON.stringify({ 'name': name}));
    }

    function showGreeting(message) {
      var response = document.getElementById('response');
      var p = document.createElement('p');
      p.style.wordWrap = 'break-word';
      p.appendChild(document.createTextNode(message));
      response.appendChild(p);
    }
  </script>
</head>
<body>
  username:${sessionScope.get("USER")}

  <noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
  <div>
    <div>
      <button id="connect" onclick="connect();">Connect</button>
      <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    <div id="conversationDiv">
      <label>What is your name?</label><input type="text" id="name" />
      <button id="sendName" onclick="sendName();">Send</button>
      <p id="response"></p>
    </div>
  </div>

</body>
</html>
