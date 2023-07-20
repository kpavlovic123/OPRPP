<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="style.jsp"%>
<body>
    <a href="http://localhost:8080/webapp2/colors">Background color chooser</a>
    <form action="trigonometric" method="GET">
        Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
        Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
        <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
    </form>
    <a href="http://localhost:8080/webapp2/stories/funny.jsp"> Funny story </a>
    <br>
    <a href="http://localhost:8080/webapp2/report.jsp"> Report </a>
    <br>
    <a href = "http://localhost:8080/webapp2/powers?a=1&b=100&n=3">Powers</a>
    <br>
    <a href = "http://localhost:8080/webapp2/appinfo.jsp"> App info</a>
</body>
