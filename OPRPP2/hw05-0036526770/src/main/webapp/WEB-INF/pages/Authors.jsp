<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "java.util.List" import = "hr.fer.zemris.java.tecaj_13.model.BlogEntry"
import = "hr.fer.zemris.java.tecaj_13.model.BlogUser"
%>
<%@ include file="Header.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <ol>
        <%
        List<BlogEntry> entryList = (List<BlogEntry>) request.getAttribute("BlogUser.entryList");
        if(entryList == null || entryList.size() == 0){
            out.print("<p>User has no blog entries.</p>");
        }
        BlogUser user = (BlogUser) request.getAttribute("BlogUser");
        for(int i = 0;i<entryList.size();i++){
            BlogEntry be = entryList.get(i);
            out.print("<li><a href=\"/blog/servleti/author/"+user.getNick()+"/"+be.getId()+"\">"+be.getTitle()+"</a></li>");
        }
        %>
      </ol>
    
    <%
      if(session.getAttribute("current.user.nick")!=null && session.getAttribute("current.user.nick")
      .equals(user.getNick())){
        String nick = (String) session.getAttribute("current.user.nick");
        out.print("<form action=\"/blog/servleti/author/"+nick+"/new\"> <button type=\"submit\">New entry</button> </form>");  
      }
    %>
</body>
</html>