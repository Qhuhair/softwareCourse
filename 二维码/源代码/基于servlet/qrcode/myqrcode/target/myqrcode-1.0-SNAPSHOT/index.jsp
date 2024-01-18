<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>使用Java语言生成二维码</title>
  </head>
  <body>
  <h1>使用谷歌的zxing开源库生成普通的黑白二维码：</h1>
  <hr />
  请输入文本内容：
  <textarea id="url" cols="60" rows="20"></textarea>
  <button onclick="generateQRcode()">生成二维码</button><br>
  <hr />
  <img id="qrcodeImg" />

  <script>
    function generateQRcode(){
      // 获取url
      let url = document.getElementById("url").value
      // 设置img标签的src属性
      let qrcodeImg = document.getElementById("qrcodeImg")
      qrcodeImg.src = "/myqrcode/generate?url=" + url
    }
  </script>
  </body>
</html>
