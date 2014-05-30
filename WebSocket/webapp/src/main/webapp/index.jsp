<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
 <style type="text/css">
 #chatlog{
   width:50%;
   height:200px;
   background-color:#DDDDDD
 }
 #msg{
  width:50%;
  
 }
 </style>
  <title> Tomcat WebSocket Chat </title>
   <meta charset=UTF-8>
   <script>
   //http://localhost:8080/WebApp/socket
var ws =null;
var host="ws://"+ window.location.host +"webapp/websocket/data";
if ('WebSocket' in window) {
	ws=new WebSocket("ws://"+ window.location.host +"/webapp/websocket/data");
}else if('MozWebSocket' in window) {
	ws=new MozWebSocket("ws://"+ window.location.host +"webapp/websocket/data");
}else {
	alert("Your Brower is not support webSocket!");
}
 
ws.onopen = function(){};
ws.onmessage = function(message){
document.getElementById("chatlog").textContent += message.data + "\n";
};
function postToServer(){
ws.send(document.getElementById("msg").value);
document.getElementById("msg").value = "";
}
function closeConnect(){
ws.close();
}
</script>
 </head>

 <body> 
 <textarea id="chatlog" readonly></textarea><br/>
<input id="msg" type="text" />
<button type="submit" id="sendButton" onClick="postToServer()">Send!</button>
<button type="submit" id="sendButton" onClick="closeConnect()">End</button>
 </body>
</html>
