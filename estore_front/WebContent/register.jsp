<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<%@include file="inc/common_head.jsp"%>

<script type="text/javascript">
	// 页面加载完成之后执行的代码
	jQuery(function($) {
		
		var cunzai = true;
		
		$("#telephone").bind("keyup blur", function() {
			var isTel = check_telephone(this.value);
			if ( isTel ) {
				// 发送ajax请求，验证是否已经注册
				$.ajax({
					url: "user?method=checkExists&tel=" + this.value,
					success: function(exists) {
						cunzai = exists == "true";
						if ( exists == "true" ) {
							$("#telephone_notice")
							.attr("class", "error")
							.text("该手机号已注册！");
						} else {
							$("#telephone_notice")
							.attr("class", "ok")
							.text("");
						}
					}
				});
			}
		});
		
		var smsCodeOk = false;
		
		$("#code_phone").bind("keyup blur", function() {
			// 前端校验规则
			var ok = check_tel_code(this.value);
			if ( ok ) {
				// 发送ajax请求，验证是否已经注册
				$.ajax({
					url: "user?method=checkSMSCode&smsCode=" + this.value,
					success: function(ok) {
						smsCodeOk = ok == "true";
						if ( ok == "false" ) {
							$("#code_phone_notice")
							.attr("class", "error")
							.text("短信验证码错误！");
						} else {
							$("#code_phone_notice")
							.attr("class", "ok")
							.text("");
						}
					}
				});
			}
		});
		
		var imgCodeOk = false;
		
		$("#captcha").bind("keyup blur", function() {
			// 前端校验规则
			var ok = check_captcha(this.value);
			if ( ok ) {
				// 发送ajax请求，验证是否已经注册
				$.ajax({
					url: "user?method=checkIMGCode&imgCode=" + this.value,
					success: function(ok) {
						imgCodeOk = ok == "true";
						if ( ok == "false" ) {
							$("#captcha_notice")
							.attr("class", "error")
							.text("验证码错误！")
							;
						} else {
							$("#captcha_notice")
							.attr("class", "ok")
							.text("");
						}
					}
				});
			}
		});
		
		
		$("#sendSMS").click(function() {
			
			// 已经注册，不能发送
			if ( cunzai ) {
				return;
			}
			
			// 60秒倒计时
			var time = 3;
			var that = this;
			
			// 获取是否不允许发送的表示，在发送之后会将unSendAble改为true
			var unSendAble = $(that).data("unSendAble");
			// 第一次点击为undefined
			if ( unSendAble ) {
				return;
			}
			
			var tel = $("#telephone").val();
			if ( !/^1[3456789]\d{9}$/.test(tel) ) {
				return;
			}
			
			$(that)
			.text("再次发送("+ time +")...")
			.css({
				"background-color": "gray",
				border: "1px solid #ccc"
			})
			.data("unSendAble", true); // 不能发送
			
			var intervalID = setInterval(function() {
				$(that).text("再次发送("+ --time +")...");
				if ( time == 0 ) {
					clearInterval(intervalID); // 停止定时器
					
					$(that)
					.text("获取短信验证码")
					.data("unSendAble", false)
					.css({
						"background-color": "#0083FF",
						border: "1px solid #2E82FF"
					});
				}
			}, 1000);
			
			$.ajax({
				url: "user?method=sendSMS&tel=" + tel,
				dataType: "json",
				success: function(result) {
					// {"Message":"OK","RequestId":"CDEF903F-9394-46A4-8B45-5A17439A6E0B","BizId":"302210358584111842^0","Code":"OK"}
					/* if ( result.Message == "OK" ) {
						// 60秒倒计时
						// 按钮不能点击
						alert("发送成功！");
					} else {
						alert("发送失败，原因是：" + result.Message);
					} */
				}
			});
		});
		
		$("#formUser").submit(function() {
			return (  !cunzai && imgCodeOk && smsCodeOk && register() );
		});
	});
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li onclick="location.href='login.jsp';">
							<a href="javascript:;">用户登录</a>
						</li>
						<li class="active">用户注册</li>
					</ul>
				</div>
				<form action="user?method=register" method="post" name="formUser" id="formUser">
					<table width="100%" border="0" align="left" cellpadding="5"
						cellspacing="3">
						<tr>
							<td width="70px" class="justify">昵称</td>
							<td>
								<input name="nickname" type="text" id="nickname" placeholder="请输入昵称" 
									onkeyup="check_nickname(this.value);"
									onblur="check_nickname(this.value);" 
									class="inputBg" />
								<span id="nickname_notice"></span>
							</td>
						</tr>
						<tr>
							<td class="justify">密码</td>
							<td>
								<input name="password" type="password" id="password1" placeholder="请输入密码" 
									onkeyup="check_password(this.value);" 
									onblur="check_password(this.value);" 
									class="inputBg" />
								<span id="password_notice"></span>
							</td>
						</tr>
						<tr>
							<td class="justify">确认密码</td>
							<td>
								<input name="confirm_password" type="password" id="conform_password"
									onkeyup="check_conform_password(this.value);"
									onblur="check_conform_password(this.value);"
									class="inputBg" placeholder="请输入确认密码"  />
								<span id="conform_password_notice"></span>
							</td>
						</tr>
						<tr>
							<td class="justify">手机号码</td>
							<td>
								<input type="text" name="telephone" id="telephone" 
									class="inputBg" placeholder="请输入手机号码" />
								<span id="telephone_notice"></span>
							</td>
						</tr>
						<tr>
							<td class="justify">手机验证码</td>
							<td>
								<input type="text" name="code_phone" id="code_phone" 
									class="inputBg" placeholder="请输入手机验证码" /><a 
									href="javascript:;" class="get_code" id="sendSMS">获取短信验证码</a>
								<span id="code_phone_notice" style="margin-left:-132px;"></span>
							</td>
						</tr>
						<tr>
							<td class="justify">图片验证码</td>
							<td>
								<input id="captcha" name="captcha" class="inputBg" 
									placeholder="请输入图片验证码" maxlength="4"
								/><img id="vcode" src="validatecode.jsp" 
									onclick="src='validatecode.jsp?'+Math.random()" />
								<span id="captcha_notice"></span>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<a href="javascript:;">
									<input id="regBtn" value="立  即  注  册" 
										type="submit" class="us_Submit_reg"/>
								</a>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<a href="javascript:;" onclick="location='login.jsp'">
									<input id="loginBtn" value="已有账号？立即登录" 
										type="button" class="us_Submit_log" />
								</a>
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>