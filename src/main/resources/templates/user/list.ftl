<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>列表</title>
    <script type="text/javascript">
        function del(id) {
            document.getElementById("del").action = "/user/del/"+id;
            document.getElementById("del").submit();
            return false;
        }
    </script>
</head>
<body>

<#-- delete 请求 -->
<form action="" method="POST" id="del">
    <input type="hidden" name="_method" value="DELETE"/>
</form>

<#-- 文件上传 -->
<form action="/user/upload" method="POST" enctype="multipart/form-data">
    <input type="file" name="files"/>
    <input type="submit" value="上传"/>
</form>

</br>

    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>id</th>
            <th>头像</th>
            <th>文件名字</th>
            <th>文件路径</th>
            <th>编辑</th>
        </tr>
        <#list lst as l>
            <tr>
                <th>${l.id}</th>
                <th><img src='http://192.168.1.230/${l.groupName}/${l.filePath}' style="width: 200px"/></th>
                <th>${l.fileName}</th>
                <th>${l.groupName}/${l.filePath}</th>
                <th>
                    <a href="/user/del/${l.id}">删除</a>
                    <a href="/user/yuan/${l.id}">查看元数据</a>
                    <a href="/user/yuanupdate/${l.id}">修改元数据</a>
                    <a href="/user/download/${l.id}">下载图片</a>
                </th>
            </tr>
        </#list>
    </table>

<br/>
<br/>
<a href="/user/users">添加</a>

</body>
</html>
