<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "java.util.List" import = "hr.fer.zemris.java.tecaj_13.model.BlogEntry"
import = "hr.fer.zemris.java.tecaj_13.model.BlogUser"
%>
<%@ include file="Header.jsp"%>

<%
String nick = (String) session.getAttribute("current.user.nick");
BlogEntry be = (BlogEntry) request.getAttribute("blogEntry");
String text = (String) be.getText();
String title = (String) be.getTitle();
Long id = be.getId();
%>

<form action="/blog/servleti/author/<%=nick%>/edit" method="post">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" value ="<%=title%>" required>
    
    <label for="text">Text:</label>
    <textarea id="text" name="text" rows="4" cols="50" required><%=text%></textarea>
    <input type="hidden" name="beId" value="<%=id%>">
    <input type="submit" value="Submit">
</form>