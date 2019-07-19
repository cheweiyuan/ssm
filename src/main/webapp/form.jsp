<%--
  Created by IntelliJ IDEA.
  User: cwy
  Date: 2019/7/18
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试RESTful</title>
</head>
<body>
    <form action="/test/update" method="post">
        <%--在表单项中 添加一个隐藏域 并设置name="_method" value="put"--%>
        <input type="hidden" name="_method" value="put" />



        <input name="name" value="设施是大" />
        <input name="age" value="18" />

        <button>提交</button>
    </form>
</body>
</html>
