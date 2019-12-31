<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/1/8
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript"
            src="<%=request.getContextPath()%>/common/js/Barrett.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/common/js/BigInt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/common/js/RSA.js"></script>
    <script type="text/javascript">

        function login() {
            if (checkForm()) {
                setMaxDigits(200);
                key = new RSAKeyPair(
                    "10001",
                    "",
                    "9648083f34e30d978091c165b94b3559f3a5631d154e951a7daa440ba3852b6344c0bf4c0c8871929c8ed7a0874a17336cbf75fbd99b84bbf69c3b76420089555b83612321f40742926462d7d1e11031d324a19e05921f4b09fc491946c7b5bda5a1537d75f9748a720c845331e51672c5270f17c5c424ac9c40ca820e3cd157");
                document.getElementById("loginKey").value = encryptedString(key,
                    '{"account":"' + document.getElementById("account").value
                    + '","password":"'
                    + document.getElementById("password").value+ '"}');
                var time=new Date();
                $('#timeStamp').val(time.getTime());
                var nonce =uuid();
                $('#nonce').val(nonce);

                document.forms[0].submit();
            }
        }
        <script>
</head>
<body>
    this is a jsp
</body>





</html>
