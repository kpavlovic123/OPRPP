<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import = "java.util.List" import = "hr.fer.zemris.java.tecaj_13.model.BlogUser" %>

<%
if(session.getAttribute("current.user.nick")==null){%>
    <jsp:include page="Login.jsp"/>
<%}else{%>
    <jsp:include page="Header.jsp"/>
<%}%>

<!DOCTYPE html>
<html>
<head>
    <title>UserList</title>
</head>
<body>
    
    
    
    <ol>
        <%
        List<BlogUser> userList = (List<BlogUser>) request.getAttribute("userList");
        for(int i = 0;i<userList.size();i++){
            String userNick = userList.get(i).getNick();
            out.print("<li><a href=\"/blog/servleti/author/"+userNick+"\">"+userNick+"</a></li>");
        }
        %>
      </ol>    
      
</body>
</html>
