var process_request = "正在处理您的请求...";
var username_empty = "用户名不能为空。";
var username_shorter = "用户名长度不能少于 3 个字符。";
var username_invalid = "用户名只能是由字母数字以及下划线组成。";
var nickname_empty = "昵称不能为空。";
var nickname_length = "昵称长度不能少于2位，且不能超过8位。";
var password_empty = "登录密码不能为空。";
var password_shorter = "登录密码不能少于 6 个字符。";
var confirm_password_invalid = "两次输入密码不一致";
var email_empty = "Email 为空";
var email_invalid = "Email 不是合法的地址";
var agreement = "您没有接受协议";
var msn_invalid = "msn地址不是一个有效的邮件地址";
var qq_invalid = "QQ号码不是一个有效的号码";
var home_phone_invalid = "家庭电话不是一个有效号码";
var office_phone_invalid = "办公电话不是一个有效号码";
var mobile_phone_invalid = "手机号码不是一个有效号码";
var msg_un_blank = "用户名不能为空";
var msg_un_length = "用户名最长不得超过7个汉字";
var msg_un_format = "用户名含有非法字符";
var msg_un_registered = "用户名已经存在,请重新输入";
var msg_can_rg = "可以注册";
var msg_email_blank = "邮件地址不能为空";
var msg_email_registered = "邮箱已存在,请重新输入";
var msg_email_format = "邮件地址不合法";
var msg_blank = "不能为空";
var no_select_question = "您没有完成密码提示问题的操作";
var passwd_balnk = "密码中不能包含空格";
var username_exist = "用户名 %s 已经存在";

/*******************************************************************************
 * 会员登录
 */
function userLogin() {
	var frm = document.forms['formLogin'];
	var username = frm.elements['telephone'].value;
	var password = frm.elements['password'].value;
	var a = is_registered(username, true);
	var b = check_password(password, true);
	return a && b;
}

function chkstr(str) {
	for ( var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) < 127 && !str.substr(i, 1).match(/^\w+$/ig)) {
			return false;
		}
	}
	return true;
}

function check_password(password, islogin) {
	var result = false;
	document.getElementById('password_notice').innerHTML = "";
	if (password == "") {
		document.getElementById('password_notice').innerHTML = password_empty;
	} else if (!islogin && password.length < 6) {
		document.getElementById('password_notice').innerHTML = password_shorter;
	} else {
		result = true;
	}
	jQuery("#password_notice").attr("class", result ? "ok" : "error");
	if (islogin) {
		jQuery("#password_notice").removeClass("ok");
	}
	return result;
}

function check_conform_password(conform_password) {
	var result = false;
	document.getElementById('conform_password_notice').innerHTML = "";
	password = document.getElementById('password1').value;
	if (conform_password.length < 6) {
		document.getElementById('conform_password_notice').innerHTML = password_shorter;
	} else if (conform_password != password) {
		document.getElementById('conform_password_notice').innerHTML = confirm_password_invalid;
	} else {
		result = true;
	}
	jQuery("#conform_password_notice").attr("class", result ? "ok" : "error");
	return result;
}

function is_registered(username, islogin) {
	var result = false;
	document.getElementById('username_notice').innerHTML = "";
	var unlen = username.replace(/[^\x00-\xff]/g, "**").length;
	
	if (username == '') {
		document.getElementById('username_notice').innerHTML = msg_un_blank;
	} else if (!islogin) {
		if (!chkstr(username)) {
			document.getElementById('username_notice').innerHTML = msg_un_format;
		} else if (unlen < 3) {
			document.getElementById('username_notice').innerHTML = username_shorter;
		} else if (unlen > 14) {
			document.getElementById('username_notice').innerHTML = msg_un_length;
		} else {
			result = true;
		}
	} else {
		result = true;
	}
	jQuery("#username_notice").attr("class", result ? "ok" : "error");
	if (islogin) {
		jQuery("#username_notice").removeClass("ok");
	}
	return result;
}

function check_nickname(nickname) {
	var result = false;
	document.getElementById('nickname_notice').innerHTML = "";
	nickname = nickname.replace(/(^\s*)|(\s*$)/g, "");
	var len = nickname.length;
	if (nickname == '') {
		document.getElementById('nickname_notice').innerHTML = nickname_empty;
	} else if (len < 2 || len > 8) {
		document.getElementById('nickname_notice').innerHTML = nickname_length;
	} else {
		result = true;
	}
	
	jQuery("#nickname_notice").attr("class", result ? "ok" : "error");
	return result;
}

/*******************************************************************************
 * 处理注册用户
 */
function register() {
	jQuery("span[id$=_notice]").html("");
	
	//var username = document.getElementById("username").value;
	//username = username.replace(/(^\s*)|(\s*$)/g, "");
	var nickname = document.getElementById("nickname").value;
	nickname = nickname.replace(/(^\s*)|(\s*$)/g, "");
	var pwd = document.getElementById("password1").value;
	var repwd = document.getElementById("conform_password").value;
	var captcha = document.getElementById("captcha").value;
	
	
	//var a = is_registered(username);
	var b = check_nickname(nickname);
	var c = check_password(pwd);
	var d = check_conform_password(repwd);
	var e = check_captcha(captcha);
	
	//var email = document.getElementById("email").value;
	var telephone = document.getElementById("telephone").value;
	var code_phone = document.getElementById("code_phone").value;
	
	//var f = check_email(email);
	var g = check_telephone(telephone);
	var h = check_tel_code(code_phone);
	
	return (b && c && d && e && g && h);
}

function check_captcha() {
	var result = false;
	document.getElementById('captcha_notice').innerHTML = "";
	var captcha = document.getElementById("captcha").value;
	captcha = captcha.replace(/(^\s*)|(\s*$)/g, "");
	if (captcha.length == 0) {
		document.getElementById('captcha_notice').innerHTML = "验证码不能为空";
	} else if (captcha.length != 4) {
		document.getElementById('captcha_notice').innerHTML = "验证码长度为4位字符";
	} else {
		result = true;
	}
	jQuery("#captcha_notice").attr("class", result ? "ok" : "error");
	return result;
}

// 检测密码强度
function checkIntensity(pwd) {
	var Mcolor = "#FFF", Lcolor = "#FFF", Hcolor = "#FFF";
	var m = 0;

	var Modes = 0;
	for (i = 0; i < pwd.length; i++) {
		var charType = 0;
		var t = pwd.charCodeAt(i);
		if (t >= 48 && t <= 57) {
			charType = 1;
		} else if (t >= 65 && t <= 90) {
			charType = 2;
		} else if (t >= 97 && t <= 122)
			charType = 4;
		else
			charType = 4;
		Modes |= charType;
	}

	for (i = 0; i < 4; i++) {
		if (Modes & 1)
			m++;
		Modes >>>= 1;
	}

	if (pwd.length <= 4) {
		m = 1;
	}

	switch (m) {
	case 1:
		Lcolor = "5px solid red";
		Mcolor = Hcolor = "5px solid #DADADA";
		break;
	case 2:
		Mcolor = "5px solid #f90";
		Lcolor = Hcolor = "5px solid #DADADA";
		break;
	case 3:
		Hcolor = "5px solid #3c0";
		Lcolor = Mcolor = "5px solid #DADADA";
		break;
	case 4:
		Hcolor = "5px solid #3c0";
		Lcolor = Mcolor = "5px solid #DADADA";
		break;
	default:
		Hcolor = Mcolor = Lcolor = "";
		break;
	}
	if (document.getElementById("pwd_lower")) {
		document.getElementById("pwd_lower").style.borderBottom = Lcolor;
		document.getElementById("pwd_middle").style.borderBottom = Mcolor;
		document.getElementById("pwd_high").style.borderBottom = Hcolor;
	}
	if (pwd.length == 0) {
		document.getElementById("pwd_lower").style.borderBottom = "5px solid #ccc";
		document.getElementById("pwd_middle").style.borderBottom = "5px solid #ccc";
		document.getElementById("pwd_high").style.borderBottom = "5px solid #ccc";
	}
}

var reg_email = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
var reg_telephone = /^1[34578]\d{9}$/;
var reg_code = /^\d{6}$/;

function check_tel_code(code) {
	var result = false;
	code_phone_notice.innerHTML = "";
	if (Utils.trim(code) == "") {
		code_phone_notice.innerHTML = "请输入手机验证码";
	} else if ( !reg_code.test(code) ) {
		code_phone_notice.innerHTML = "手机验证码格式不正确";
	} else {
		result = true;
	}
	jQuery("#code_phone_notice").attr("class", result ? "ok" : "error");
	return result;
}

function check_email(email) {
	var result = false;
	email_notice.innerHTML = "";
	if (Utils.trim(email) == "") {
		email_notice.innerHTML = msg_email_blank;
	} else if ( !Utils.isEmail(email) ) {
		email_notice.innerHTML = msg_email_format;
	} else {
		result = true;
	}
	jQuery("#email_notice").attr("class", result ? "ok" : "error");
	return result;
}

function check_telephone(telephone) {
	var result = false;
	telephone_notice.innerHTML = "";
	if (Utils.trim(telephone) == "") {
		telephone_notice.innerHTML = "手机号码不能为空";
	} else if ( !Utils.isTel(telephone) ) {
		telephone_notice.innerHTML = "手机号码不合法";;
	} else {
		result = true;
	}
	jQuery("#telephone_notice").attr("class", result ? "ok" : "error");
	return result;
}