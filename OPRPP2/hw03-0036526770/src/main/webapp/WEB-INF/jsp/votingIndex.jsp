<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
    import = "java.util.ArrayList" import = "java.util.HashMap"
%>
<%@ include file="style.jsp"%>


<body>
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
<ol>
<%  
    ArrayList<String> listItems = new ArrayList<>();
    HashMap<Integer,String> map = (HashMap<Integer,String>)request.getAttribute("map");
    map.entrySet().stream().forEach((e)->{
        try{
            int bandId = e.getKey();
            String bandName = e.getValue();
            listItems.add("<li><a href=\"voting-vote?id="+bandId+"\">"+bandName+"</a></li>");
        }
        catch(Exception exc)
        {}
    });
    for(int i = 0;i<listItems.size();i++){
        out.print(listItems.get(i));
    }
%>
</ol>
</body>