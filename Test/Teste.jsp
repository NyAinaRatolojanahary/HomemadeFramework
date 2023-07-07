<%
    String nom = (String)request.getAttribute("img");
    out.println(nom);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Matching Date</title>
</head>
<body>
    <h1>Hello From Test.jsp</h1>
    <form action="getFile" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="">
        <input type="submit" value="">
    </form>
</body>
</html>