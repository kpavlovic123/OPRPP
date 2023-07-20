<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <h2>Login Form</h2>
    <form method="post" action="/blog/servleti/main">
    <%
        String nick = "";
        if(request.getParameter("nick")!=null){
            nick = (String) request.getParameter("nick");
        }
    %>
        <label for="nick">Nick:</label>
        <input type="text" id="nick" name="nick" value ="<%=nick%>" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <%
        if(request.getAttribute("loginError")!=null){
            out.print(request.getAttribute("loginError")+"<br>");
        }
        %>
        <input type="submit" value="Login">
    </form>
    
    <form action="/blog/servleti/registration">
        <button type="submit">Registration</button>
      </form>