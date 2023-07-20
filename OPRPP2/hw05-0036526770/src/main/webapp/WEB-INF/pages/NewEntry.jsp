<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "java.util.List" import = "hr.fer.zemris.java.tecaj_13.model.BlogEntry"
import = "hr.fer.zemris.java.tecaj_13.model.BlogUser"
%>
<%@ include file="Header.jsp"%>

<%
String nick = (String) session.getAttribute("current.user.nick");
%>

<form action="/blog/servleti/author/<%=nick%>/new" method="post">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" required>
    
    <label for="text">Text:</label>
    <textarea id="text" name="text" rows="4" cols="50" required></textarea>
    
    <input type="submit" value="Submit">
</form>
