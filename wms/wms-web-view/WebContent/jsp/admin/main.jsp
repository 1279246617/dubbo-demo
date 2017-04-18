<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="meta.jsp"%> 

<title>管理后台</title>
<meta name="description" content="">
<meta name="author" content="">

<link href="${ctx}/static/metisMenu/metisMenu.min.css" rel="stylesheet">
<script src="${ctx}/static/metisMenu/metisMenu.min.js"></script>

<link href="${ctx}/static/admin/css/main.css?v=<%=Math.random() %>" rel="stylesheet">
</head>

<body>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" id="navigation" style="margin-bottom: 0;border:0;min-height: 25px;">
			<%@ include  file="header.jsp"%>
           	<%@ include  file="left.jsp"%>
        </nav>

        <div id="page-wrapper" style="overflow:hidden;margin-left:200px;">
       	 	<%@ include  file="nav.jsp"%>
        	<div id="page_body">
        		<iframe iframeuid="adminMain" style="width:100%;height:100%;border: 0;" src="${ctx}/jsp/admin/index.jsp" ></iframe>
        	</div>
        </div>
    </div>

</body>
</html>

