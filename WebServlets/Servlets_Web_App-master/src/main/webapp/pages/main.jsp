<%--
  Created by IntelliJ IDEA.
  User: Suzukie
  Date: 14.06.2024
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main </title>
</head>
Hello (forward) = ${user}
<hr/>
Hello (redirect/forward)= ${user_name}
<hr/>
<form action =" controller ">
    <input type="hidden" name="command" value="logout">
    <input type="submit" name="logOut" />

</form>
</body>
</html>
