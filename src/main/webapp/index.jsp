<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
	String user = (String)session.getAttribute("user");
	String sessionId = session.getId();
	long time = session.getCreationTime();
%>
	<div> user: <%=user %></div>
	<div> sessionId: <%=sessionId %></div>
	<div> createTime: <%=time %> </div>
</body>
</html>