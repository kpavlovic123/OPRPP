<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String fn,ln; 
if(session.getAttribute("current.user.nick")!=null){
    fn=(String) session.getAttribute("current.user.fn"); 
    ln=(String) session.getAttribute("current.user.ln");
    out.print("<h2> First Name: "+fn+" Last Name: "+ln+"</h2>"+
    "<form action=\"/blog/servleti/logout\"> <button type=\"submit\">Logout</button> </form>");
}
else{
    out.print("<h2>not logged in</h2>");
}
%>
<p>------------------------------</p>

