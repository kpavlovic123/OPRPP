<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h2>Registration form</h2>
    <%
        String fn = "",ln = "",email = "";
        if(request.getParameter("firstname")!=null){
            fn = (String)request.getParameter("firstname");
        }
        if(request.getParameter("lastname")!=null){
            ln = (String)request.getParameter("lastname");
        }
        if(request.getParameter("email")!=null){
            email = (String)request.getParameter("email");
        }
    %>
  <form action="/blog/servleti/registration" method="POST">
    <label for="firstname">First Name:</label>
    <input type="text" id="firstname" name="firstname" value ="<%=fn%>"required><br>

    <label for="lastname">Last Name:</label>
    <input type="text" id="lastname" name="lastname" value ="<%=ln%>" required><br>

    <label for="email">Email:</label>
    <input type="text" id="email" name="email" value ="<%=email%>" required><br>

    <label for="nick">Nickname:</label>
    <input type="text" id="nick" name="nick" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>
    <%
        if(request.getAttribute("registrationError")!=null){
            out.print(request.getAttribute("registrationError")+"<br>");
        }
    %>
    <input type="submit" value="Register">
  </form>
</body>
</html>