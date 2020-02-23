<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="Top">
	<div class="tops"></div>
	<div class=" block header_bg" style="margin-bottom:0px;">
		<div class="top_nav_header">
			<div class="top_nav">
				<div class="block">
					<div class="f_l left_login">
						<script type="text/javascript" src="js/transport.js"></script>
						<script type="text/javascript" src="js/utils.js"></script>
						<font id="ECS_MEMBERZONE">
							欢迎光临本店，
							<!-- request.getSession().getAttribute("loginUser"); -->
							<c:if test="${ loginUser == null }">
								<a href="login.jsp">登录</a>
								<a href="register.jsp">注册</a>
							</c:if>
							<c:if test="${ loginUser != null }">
								${loginUser.nickname }
								<a href="logOut.jsp">退出</a>
							</c:if>
						</font>
					</div>
					<ul class="top_bav_l">
						<li class="top_sc">&nbsp;&nbsp;<a href="javascript:void(0);"
							onclick="AddFavorite('我的网站',location.href)">收藏本站</a></li>
						<li>&nbsp;&nbsp;&nbsp;关注我们</li>
					</ul>
					<div class="header_r">
						<a href="${app }/orders?method=query">我的订单</a>
						<a href="goods?method=query">商品列表</a>
					</div>
				</div>
			</div>
		</div>
		<div class="clear_f"></div>
		<div class="header_top logo_wrap clearfix">
			<a class="logo_new" href="javascript:;">
				<img src="themes/ecmoban_jumei/images/logo.gif" />
			</a>
			<div class="ser_n">
				<form id="searchForm" class="searchBox clearfix" name="searchForm"
					onsubmit="return false;" method="get" action="">
					<span class="ipt1"><input name="keywords"
						placeholder="请输入您想购买的商品" type="text" id="keyword" value="${param.name }"
						class="searchKey" /></span> <span class="ipt2"><input
						type="submit" name="imageField" id="serchGoods" class="fm_hd_btm_shbx_bttn"
						value="搜  索"></span>
				</form>
				<div class="clear_f"></div>
				<ul class="searchType none_f"></ul>
			</div>
			<ul class="cart_info">
				<li id="ECS_CARTINFO">
					<div class="top_cart">
						<img src="themes/ecmoban_jumei/images/cart.gif" /> <span
							class="carts_num none_f">
							<a href="cart?method=query" title="查看购物车" id="goodsNum">0</a></span>
						<a href="cart?method=query" class="shopborder">去购物车结算</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
<div style="clear:both"></div>
<div class="menu_box clearfix">
	<div id="category_header" class="block" style="width:840px">
		<a href="index.jsp" class="cur">首页</a>
		<!-- <a href="javascript:;">化妆品</a>
		<a href="javascript:;">鞋包配饰</a>
		<a href="javascript:;">居家母婴</a>
		<a href="javascript:;">服饰内衣</a>
		<a href="javascript:;">团购商品</a> -->
	</div>
</div>
<script>
	jQuery(function($) {
		// 登录的情况下，去查询购物车中所有商品的数量
		if ( ${ not empty loginUser } ) {
			$.ajax({
				url: "cart?method=goodsNum&uid=${loginUser.id}",
				success: function(data) {
					$("#goodsNum").text(data);
				}
			});
		}
		
		// 根据商品名称模糊查询
		$("#serchGoods").click(function() {
			location = "goods?method=query&name=" + $("#keyword").val();
		});
		
		// 发送ajax请求，读取类别信息
		$.ajax({
			url: "category?method=query",
			// 后台返回json格式的数据,将其转换成可以直接使用的对象（数组或对象）
			// js数组：  [value1, value2, ...]
			// js对象： {key:value, key:value}
			dataType: "json",
			success: function(data) {
				// [{id:11, name: 北京},{id: 12, name: 天津}]
				$(data).each(function() {
					//$("#category_header").append("<a href='javascript:;'>"+this.name+"</a>");
					
					// 创建标签的同时指定属性、事件等
					// <a href="javascript:;" cid="1">手机数码</a>
					$("<a>", {
						href: "javascript:;", // attr("href", "javascript:;")
						text: this.name, // text(this.name)
						cid: this.id, // attr("cid", this.id)
						click: function() { // click(fn);
							//alert(1);
							location = "goods?method=query&cid=" + $(this).attr("cid");
						}
					})
					// 追加到父元素中
					.appendTo("#category_header");
				});
				
				
				$("#category_header a[cid=${param.cid}]")
				.addClass("cur")
				.siblings().removeClass("cur");
				
				// 显示商品详情页面的商品类别
				$("#goods_category").text( $("a[cid=${gList[0].cid}]").text() );
			}
		});
	});
</script>

<link href="themes/ecmoban_qq/images/qq.css" rel="stylesheet" type="text/css" />
<div class="QQbox" id="divQQbox" style="width:170px;">
	<div class="Qlist" id="divOnline" onmouseout="hideMsgBox(event);"
		style="display: none; " onmouseover="OnlineOver();">
		<div class="t"></div>
		<div class="infobox">
			我们营业的时间<br>9:00-18:00
		</div>
		<div class="con">
			<ul>
				<li><a href="javascript:;" target="_blank"><img
						src="images/button_old_41.gif" height="16" border="0" alt="QQ" />&nbsp;&nbsp;666666666</a></li>
				<li><a href="javascript:;" target="_blank"><img
						src="images/button_old_41.gif" height="16" border="0" alt="QQ" />&nbsp;&nbsp;888888888</a></li>
				<li><a href="javascript:;" target="_blank"><img
						src="images/button_old_41.gif" height="16" border="0" alt="QQ" />&nbsp;&nbsp;999999999</a></li>
				<li>服务热线: 88888888</li>
			</ul>
		</div>
		<div class="b"></div>
	</div>
	<div id="divMenu" onmouseover="OnlineOver();" style="display: block; ">
		<img src="themes/ecmoban_qq/images/qq_1.gif" class="press" alt="在线咨询">
	</div>
	<script type="text/javascript">
		//初始化主菜单
		function sw_nav(obj, tag) {
			var DisSub = document.getElementById("DisSub_" + obj);
			var HandleLI = document.getElementById("HandleLI_" + obj);
			if (tag == 1) {
				DisSub.style.display = "block";
			} else {
				DisSub.style.display = "none";
			}
		}
	</script>
</div>