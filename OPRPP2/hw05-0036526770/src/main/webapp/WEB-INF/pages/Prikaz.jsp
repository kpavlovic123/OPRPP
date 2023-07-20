<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "hr.fer.zemris.java.tecaj_13.model.BlogUser" import = "hr.fer.zemris.java.tecaj_13.model.BlogEntry"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="Header.jsp"/>
<html>
  <body>

  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
    </c:otherwise>
  </c:choose>

  <%
      BlogUser user = (BlogUser) request.getAttribute("BlogUser");
      if(session.getAttribute("current.user.nick")!=null){
        BlogEntry be = (BlogEntry)request.getAttribute("blogEntry");
        Long beId = (Long)be.getId();
        %>

        <form action="/blog/servleti/comment" method="post">
          <label for="text">Comment:</label>
          <textarea id="text" name="text" rows="4" cols="50" required></textarea>
          <input type="hidden" name="beId" value="<%=beId%>">
          <input type="submit" value="Submit">
      </form>

        <%
        if(session.getAttribute("current.user.nick")
        .equals(user.getNick())){
          
          String nick = (String) session.getAttribute("current.user.nick");
          out.print("<form action=\"/blog/servleti/author/"+nick+"/edit\">"+  
          "<input type=\"hidden\" name=\"beId\" value=\""+
          beId+"\"><button type=\"submit\">Edit Entry</button> </form>");  
          }
      }
    %>

  </body>
</html>
