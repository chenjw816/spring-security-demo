<%--
  Created by IntelliJ IDEA.
  User: yamorn
  Date: 2014/11/23
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
  <script src="/resources/jquery/jquery-1.11.1.js"></script>
  <script src="/resources/jquery/jquery.form.js"></script>
  <script src="/resources/websocket/sockjs-0.3.4.js"></script>
  <script src="/resources/websocket/stomp.js"></script>
</head>
<body>
  <h1>Please upload a file</h1>
  <div>
  <form id="myForm" method="post" action="/uploadAction" enctype="multipart/form-data">
    <input type="text" name="name"/>
    <input type="file" name="file"/>
    <input type="submit" value="SUBMIT" class="submit" />
  </form>

  </div>
  <div id='output1'></div>
  <button onclick="upload();">upload</button><br>
  <button onclick="connect()">connection</button>
  <button onclick="disconnect()">disconnection</button>
<script>
  $(function(){
    var options = {
      target:        '#output1',
      success:       function(data){
        console.log(data);
      }
    };
    $('#myForm').ajaxForm(options);
  });


  function connect() {
    var socket = new SockJS('/stompEnd');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/queue/${sessionScope.get("USER")}', function(greeting){
        console.log(JSON.parse(greeting.body).content);
      });
    });
  }

  function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
  }



</script>
</body>
</html>
