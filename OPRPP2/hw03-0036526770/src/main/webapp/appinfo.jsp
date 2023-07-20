<%@ page import = "java.text.SimpleDateFormat" import = "java.sql.Date" import = "java.util.TimeZone"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../WEB-INF/jsp/style.jsp"%>

<% 

    String time;
    long now = System.currentTimeMillis();
    long start = (long) application.getAttribute("timeInit");
    long dif = now - start;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    time = formatter.format(new Date(dif));

%>
<h3>This app has been running for: <%= time%></h3>

