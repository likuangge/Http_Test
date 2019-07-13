<%--
  Created by IntelliJ IDEA.
  User: EDZ
  Date: 2019/7/12
  Time: 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>HttpClient</title>
    <style type="text/css">
      a {
        text-decoration: none;
        color: black;
        font-size: 18px;
      }

      h3 {
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: deepskyblue;
        border-radius: 4px;
      }
    </style>
  </head>
  <body>
    <div class="row clearfix">
      <div class="col-md-12 column">
        <div class="page-header">
          <h1>
            HTTP文件管理
          </h1>
        </div>
      </div>
    </div>
    <br><br>
    <form action="queryDir" method="post">
      Directory Name：<input type="text" name="dirname" /> <input type="submit" value="查询" />
    </form>
    <form action="fileupload" method="post">
      Upload File：<input type="text" name="filename" /> <input type="submit" value="上传" />
    </form>
  </body>
</html>
