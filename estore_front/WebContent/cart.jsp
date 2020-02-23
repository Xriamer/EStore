<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的购物车</title>
<%@include file="inc/common_head.jsp"%>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block table">
		<div class="AreaR">
			<div class="block box">
				<div class="blank"></div>
				<div id="ur_here">
					当前位置: <a href="index.jsp">首页</a><code>&gt;</code>我的购物车
				</div>
			</div>
			<div class="blank"></div>
<div class="box">
<div class="box_1">
<div class="userCenterBox boxCenterList clearfix"
	style="_height:1%;">
	<h5><span>我的购物车</span></h5>
<table width="100%" align="center" border="0" cellpadding="5"
	cellspacing="1" bgcolor="#dddddd">
	<tr>
		<th bgcolor="#ffffff">商品名称</th>
		<th bgcolor="#ffffff">市场价</th>
		<th bgcolor="#ffffff">本店价</th>
		<th bgcolor="#ffffff">购买数量</th>
		<th bgcolor="#ffffff">小计</th>
		<th bgcolor="#ffffff" width="160px">操作</th>
	</tr>
	<c:set var="xiaoji" value="0" />
	<c:set var="json" value="0" />
	<c:forEach items="${cList }" var="c">
	<c:set var="xiaoji" value="${xiaoji + c.goods.estoreprice * c.buynum }" />
	<c:set var="json" value="${json + (c.goods.marketprice - c.goods.estoreprice) * c.buynum }" />
	<tr>
		<td bgcolor="#ffffff" align="center" style="width:300px;">
			<!-- 商品图片 -->
			<a href="javascript:;" target="_blank">
				<img style="width:80px; height:80px;"
				src="${c.goods.imgurl }"
				border="0" title="${c.goods.name }" />
			</a><br />
			<!-- 商品名称 -->
			<a href="javascript:;" target="_blank" class="f6">${c.goods.name }</a>
		</td>
		<td align="center" bgcolor="#ffffff">${c.goods.marketprice }元</td>
		<td align="center" bgcolor="#ffffff">${c.goods.estoreprice }元</td>
		<td align="center" bgcolor="#ffffff">
			<!-- 
				gid: 商品id
				marketprice：市场价格
				estoreprice：商城价格
				num：表示商品的库存数量
			 -->
			<input value="${c.buynum }" class="cartInput" 
				gid="${c.gid }"
				marketprice="${c.goods.marketprice }"
				estoreprice="${c.goods.estoreprice }" 
				num="${c.goods.num }" />
		</td>
		<td align="center" bgcolor="#ffffff" id="xiaoji${c.gid }">${c.goods.estoreprice * c.buynum }元</td>
		<td align="center" bgcolor="#ffffff">
			<a href="javascript:;" class="f6" del="${c.gid }">删除</a>
		</td>
	</tr>
	</c:forEach>
	<script type="text/javascript">
		jQuery(function($) {
			
			$("a[del]").click(function() {
				// confirm弹出确认框 
				if ( confirm("是否删除？") ) {
					// 获取当前的行
					var $tr = $(this).closest("tr");
					$.ajax({
						url: "cart?method=del&gid=" + $(this).attr("del"),
						success: function(data) {
							if ( data == "nologin" ) {
								location = "login.jsp";
								return ;
							}
							// 删除当前行
							$tr.remove();
							// 更新总金额和节省金额
							changeSumAndJson();
						}
					});
				}
			});
			
			// 获取梳理的输入框， input表示标签名 .cartInput表示类名
			$("input.cartInput")
			// 只能输入数字 
			.on("input propertychange", function() {
				this.value = this.value.replace(/\D/g, "");
			})
			// 当输入框失去焦点，并且值发生改变的时候触发change事件
			.change(function() {
				var buynum = this.value;
				// 购买数量不能为空或小于0
				if ( buynum == "" || buynum <= 0 ) {
					this.value = buynum = 1;
				}
				
				// 购买数量不能大于库存数量
				if ( buynum > $(this).attr("num") ) {
					this.value = buynum = $(this).attr("num");
				}
				
				var gid = $(this).attr("gid");
				var marketprice = $(this).attr("marketprice");
				var estoreprice = $(this).attr("estoreprice");
				
				$.ajax({
					url: "cart?method=changeNum&buynum="+this.value+"&gid=" + gid,
					success: function(data) {
						if ( data == "nologin" ) {
							location = "login.jsp";
							return ;
						}
						
						// 1、更新小计金额：商城价 * 购买数量
						$("#xiaoji"+gid).text( estoreprice * buynum + "元" );
						
						// 更新总金额和节省金额
						changeSumAndJson();
					}
				});
				//location = "cart?method=changeNum&buynum="+this.value+"&gid=" + $(this).attr("gid");
			});
			
			function changeSumAndJson() {
				// 2、更新总金额
				var sum = 0;
				// 获取id属性以“xiaoji”开头的td元素
				$("td[id^=xiaoji]").each(function() {
					// parseFloat将字符串转换成数字 “11.5元” ===> 11.5
					sum += parseFloat($(this).text());
				});
				// toFixed表示保留2位小数
				$("#sum").text(sum.toFixed(2));
				
				// 3、更新节省金额
				var json = 0;
				var total = 0; // 右上角购物车中的商品数量
				$("input.cartInput").each(function() {
					var buynum = this.value;
					total += buynum - 0;
					var marketprice = $(this).attr("marketprice");
					var estoreprice = $(this).attr("estoreprice");
					json += (marketprice - estoreprice) * buynum;
				});
				$("#json").text(json.toFixed(2));
				
				$("#goodsNum").text(total);
			}
		});
	</script>
	<tr>
		<td colspan="6" style="text-align:right;padding-right:10px;font-size:25px;">
			购物金额总计&nbsp;<font color="red" id="sum">
				<fmt:formatNumber value="${xiaoji }" pattern="0.00" /> 
			</font>元，
			共为您节省了&nbsp;<font color="red" id="json">
				<fmt:formatNumber value="${json }" pattern="0.00" /> 
			</font>元
			<a href="javascript:;" id="gopay">
				<input value="去结算" type="button" class="btn" />
			</a>
			<script type="text/javascript">
				jQuery(function($) {
					$("#gopay").click(function() {
						// 跳转到servlet,查询购物车信息
						location = "${app}/orders?method=toOrders";
					});
				});
			</script>
		</td>
	</tr>
</table>
</div>
</div>
</div>
		</div>
		<div class="blank"></div>
		<div class="blank5"></div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>
