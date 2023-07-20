<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="style.jsp"%>
<body>
    <table>
   
    <thead>
    <tr>
    <th>Angle</th>
    <th>Sin </th>
    <th>Cos</th>
    </tr>
    </thead>

    <tbody>
    <%
        int a = (int) request.getAttribute("a");
        int b = (int) request.getAttribute("b");
        double sin[] = (double[])request.getAttribute("sin");
        double cos[] = (double[])request.getAttribute("cos");
        for(int i = a;i<b;i++){
            out.print("<tr> <td>"+i+"</td> <td>"+sin[i-a]+"</td> <td>"+cos[i-a]+"</td> </tr>");
        }
    %>
    </tbody>

    </table>
</body>