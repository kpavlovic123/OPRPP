<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% 
    String color;
    if(session == null)
        color = "white";
    else{
        color = (String) session.getAttribute("pickedBgColor");
        if(color==null)
            color = "white";
    }
%>

<head>
<style>
body{background-color:<%= color%>}
</style>
</head>