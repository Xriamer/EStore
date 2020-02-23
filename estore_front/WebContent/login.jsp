<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
	jQuery(function($) {
		$("#regBtn").click(function() {
			// 校验前端的规则
			var ok = userLogin();
			
			if ( ok ) {
				// 发送到后台的数据
				var data = {
					method: "login",
					telephone: $("#telephone").val(),
					password: $("#password").val()
				};
				
				// 是否发送记住用户名的标识
				if ( $("#remember").is(":checked") ) {
					data.remember = 1;
				}
				
				$.ajax({
					url: "user", // 请求的servlet路径
					type: "post", // 请求方式
					data: data, // 请求时发送的数据 
					// 处理结果
					success: function(code) {
						if ( code == "ok" ) {
							
							//location = "index.jsp";
							if ( "${param.redirect}" ) {
								//alert("${param.redirect}")
								// URL在java中已经进行了编码，因此需要对URL进行解码
								location = decodeURIComponent("${param.redirect}");
							} else {
								location = "index.jsp";
							}
						} else {
							alert("用户名或密码错误！");
						}
					}
				});
			}
		})//.click();
	});
	
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox clearfix">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li class="active">用户登录</li>
						<li onclick="location.href='register.jsp';">
							<a href="javascript:;">用户注册</a>
						</li>
					</ul>
				</div>
				<form name="formLogin" id="loginForm" action="javascript:alert(1);" method="post">
					<table width="100%" border="0" align="left" cellpadding="3"
						cellspacing="5">
						<tr>
							<td width="70px" class="justify">用户名</td>
							<td>
								<input placeholder="请输入手机号" value="${cookie.telephone.value }" name="telephone" id="telephone" 
									class="inputBg" onkeyup="is_registered(this.value, true);"  />
								<span style="color:#FF0000" id="username_notice"></span>
							</td>
						</tr>
						<tr>
							<td class="justify">密码</td>
							<td>
								<input name="password" id="password" value="123123" placeholder="请输入密码" type="password" 
									class="inputBg" onkeyup="check_password(this.value, true);" />
								<span style="color:#FF0000" id="password_notice"></span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="checkbox" value="1" 
								${empty cookie.telephone.value ? "" : "checked" } name="remember" id="remember" />
								<label for="remember">记住用户名</label>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<a href="javascript:;">
									<input id="regBtn" value="立  即  登  录" 
										type="button" class="us_Submit_reg"/>
								</a>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<a href="javascript:;" onclick="location='register.jsp'">
									<input id="loginBtn" value="没有账号？立即注册" 
										type="button" class="us_Submit_log" />
								</a>
							</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
		<%@include file="inc/footer.jsp"%>
</body>
</html>