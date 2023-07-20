<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../WEB-INF/jsp/style.jsp"%>

<%  
    int rand = (new Random().nextInt()); 
    rand = rand%5;
    if(rand<0)
        rand*=-1;   
    String fontColor;
    switch(rand){
        case 0: fontColor = "green";
                break;
        case 1: fontColor = "blue";
                break;
        case 2: fontColor = "brown";
                break;
        case 3: fontColor = "yellow";
                break;
        case 4 : fontColor = "red";
                break;
        default: fontColor = "gray";
                break;
    }
%>

<body>
   <p style="color:<%= fontColor%>">
        Danas mi je ispit.
   </p> 
</body>