<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<title>欢迎您！十分抱歉，您要查看的网页当前已过期，或已被更名或删除！</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="refresh" content="10;URL=${pageContext.request.contextPath}">
<STYLE type=text/css>
INPUT {FONT-SIZE: 12px}
TD {FONT-SIZE: 12px}
.p2 {FONT-SIZE: 12px}
.p6 {FONT-SIZE: 12px; COLOR: #1b6ad8}
A {COLOR: #1b6ad8; TEXT-DECORATION: none}
A:hover {COLOR: red}
</STYLE>

<META content="Microsoft FrontPage 5.0" name=GENERATOR>
<script type="text/javascript">
	setInterval("miao.innerHTML--", 1000);
</script>
</HEAD>
<BODY oncontextmenu="return false" onselectstart="return false">
	<P align=center></P>
	<P align=center></P>
	<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
		<TBODY>
			<TR>
				<TD vAlign=top height=270>
					<DIV align=center>
						<BR>
						<IMG height=211 src="images/error/error.gif" width=329><BR>
						<BR>
						<TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
							<TBODY>
								<TR>
									<TD height=8></TD>
								<TR>
									<TD align="center">
										<P style="text-align:center;">
											<FONT color=red size=5><BR> 您所请求的页面不存在！</FONT>
											<FONT class=p2>&nbsp;&nbsp;&nbsp; <br />
											<FONT>
												<span id="miao" style="color: orange;font-size: 30px;">10</span> 秒后跳到
												<a href="${pageContext.request.contextPath }/index.jsp">首页</a>
											</FONT>
										</FONT>
										</P>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</DIV>
				</TD>
			</TR>
			<TR>
				<TD height=5></TD>
			<TR>
				<TD align=middle>
					<CENTER>
						<TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
							<TBODY>
								<TR>
									<TD width=6><IMG height=26
										src="images/error/left.gif" width=7></TD>
									<TD background="images/error/bg.gif">
										<DIV align=center>
											<font class=p6> <a
												href="${pageContext.request.contextPath }/index.jsp">返回首页</a>
												| <a href="${pageContext.request.contextPath }/login.jsp">返回登录页</a>
											</font>
										</DIV>
									</TD>
									<TD width=7><IMG src="images/error/right.gif"></TD>
								</TR>
							</TBODY>
						</TABLE>
					</CENTER>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
	<P align=center></P>
	<P align=center></P>
</BODY>
</HTML>