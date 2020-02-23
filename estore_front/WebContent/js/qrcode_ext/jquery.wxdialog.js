/**
 * Basic Usages:
 * $.WXDialog("生成二维码的url");
 */
(function($) {
	// 默认参数
	var options = {
		url: '', //生成二维码的支付链接
		width: 260, // 二维码宽度
		height: 260, // 二维码高度
		blankWidth: 20,// 扫码区四周空白的宽度
		base: "images", // 图片资源的位置，默认是images
		showDesc: true, // 是否显示二维码下发的图片说明
		showTitle: true, // 是否显示二维码的标题
		showClose: true, // 是否显示关闭按钮
		// 关闭之前触发，return false可以阻止关闭
		onBeforeClose: function() {
			$.confirm("当前订单尚未完成付款，是否关闭？");
		},
		onClose: null // 关闭时触发，无法阻止关闭
	};
	
	var zIndex = 9999;
	
	$.dialogBg = function() {
		// 添加遮罩层
		$("<div dialog />").css({
			position: "absolute", 
			left: 0, 
			top: 0, 
			width: $(document).outerWidth(),
			height: $(document).outerHeight(), 
			background: "#000", 
			opacity: 0.3,
			zIndex: zIndex++
		}).appendTo("body");
	};
	
	$.alert = function(msg, title) {
		window.blocking = true; // 窗口阻塞中，点击任意按钮解除阻塞
		$.dialogBg();
		var alertid = "alert_" + new Date().getTime();
		$("<div dialog='alert' />")
		.attr("id", alertid)
		.css({
			position: "fixed",
			width: 400,
			left: "50%",
			top: "50%",
			marginLeft: -200,
			backgroundColor: "rgba(0,0,0,0.5)",
			zIndex: zIndex++
		}).appendTo("body");
		
		$("<div />").css({
			backgroundColor: "#fff",
			margin:8
		})
		// 标题信息
		.append($("<div />", {
			text: title || "提示信息",
			css: {
				padding:10,
				fontSize: 18,
				fontWeight: "bold"
			}
		}))
		// 提示信息
		.append($("<div/>", {
			text: msg,
			css: {
				padding: 10,
				fontSize: 16
			}
		}))
		// 确定按钮、关闭按钮
		.append($("<div />")
				.css({
					padding:10,
					textAlign: "right"
				})
				.append($("<div />", {
					text: "确定",
					css: {
						display: "inline-block",
						padding: "5px 10px",
						color: "#fff",
						cursor: "pointer",
						backgroundColor: "#F26231",
						borderRadius: 3
					},
					click: function() {
						$.closeDialog();
						window.blocking = undefined;
					}
				})))
		.appendTo("#" + alertid)
		;
		
		$("#" + alertid).css("marginTop", function() {
			return parseInt($(this).outerHeight()) / -2;
		});
	};
	
	$.confirm = function(msg, callback) {
		window.blocking = true; // 窗口阻塞中，点击任意按钮解除阻塞
		$.dialogBg();
		
		var alertid = "alert_" + new Date().getTime();
		$("<div dialog='alert' />")
		.attr("id", alertid)
		.css({
			position: "fixed",
			width: 400,
			left: "50%",
			top: "50%",
			marginLeft: -200,
			backgroundColor: "rgba(0,0,0,0.5)",
			zIndex: zIndex++
		}).appendTo("body");
		
		$("<div />").css({
			backgroundColor: "#fff",
			margin:8
		})
		// 标题信息
		.append($("<div />", {
			text: "操作确认",
			css: {
				padding:10,
				fontSize: 18,
				fontWeight: "bold"
			}
		}))
		// 提示信息
		.append($("<div/>", {
			text: msg,
			css: {
				padding: 10,
				fontSize: 16
			}
		}))
		// 确定按钮、关闭按钮
		.append($("<div />")
				.css({
					padding:10,
					textAlign: "right"
				})
				.append($("<div />", {
					text: "确定",
					css: {
						display: "inline-block",
						padding: "5px 10px",
						color: "#fff",
						cursor: "pointer",
						backgroundColor: "#FF4D00",
						borderRadius: 3
					},
					click: function() {
						$.closeDialog();
						if ( window.preventbeforeclose === true ) {
							$.closeDialog();
						}
						window.blocking = undefined;
						window.preventbeforeclose = undefined;
						if ( typeof callback === "function" ) {
							callback();
						}
					}
				}))
				.append($("<div />", {
					text: "取消",
					css: {
						display: "inline-block",
						padding: "5px 10px",
						marginLeft: 20,
						color: "#fff",
						cursor: "pointer",
						backgroundColor: "#B3B3B3",
						borderRadius: 3
					},
					click: function() {
						window.blocking = undefined;
						$.closeDialog();
					}
				})))
		.appendTo("#" + alertid)
		;
		
		$("#" + alertid).css("marginTop", function() {
			return parseInt($(this).outerHeight()) / -2;
		});
	};
	
	$.closeDialog = function() {
		var $dialogs = $("div[dialog]");
		$dialogs.eq(-1).remove();
		$dialogs.eq(-2).remove();
	};
	
	$.WXDialog = function(_options) {
		if ( typeof _options === "string" ) {
			_options = {
				url: _options	
			};
		}
		
		// 替换默选参数
		$.each(_options, function(key) {
			options[key] = _options[key];
		});
		
		// 指定图片资源的位置时，如果最后指定了/则去掉
		var reg = /\/$/;
		if ( reg.test(options.base) ) {
			options.base = options.base.replace(reg, "");
		}
		
		// 二维码中间的logo
		options.logo = options.base + "/wxlogo.png";
		// 二维码下发的文字说明
		options.desc = options.base + "/wxdesc.png";
		// 关闭按钮
		options.close = options.base + "/close.png";
		
		// 添加遮罩层
		$.dialogBg();
		
		// 添加扫码区
		var defaultSize = 260; // 二维码尺寸
		var width = options.width || defaultSize;
		var height = options.height || defaultSize;
		// 说明图片的尺寸：260x60
		// 说明图片的宽度和高度等比缩放，宽度和二维码的宽度保持一致，由此计算说明图片的高度，以增加父元素的高度
		var descHeight = width / 260 * 60;
		var descMargin = 10; // 二维码与说明图片之间的距离
		var blankWidth = options.blankWidth; // 四周空白的大小
		// 扫码区宽度 = 二维码宽度 + 2倍空白
		var scanWidth = width + blankWidth * 2;
		// 扫描去高度 = 二维码高度 + 2倍空白
		var scanHeight = height + blankWidth * 2;
		// 如果有图片说明，高度还需要加上说明图片的高度和二维码与图片之间的间距
		if ( options.showDesc ) {
			scanHeight += descHeight + descMargin;
		}
		var qrcodeid = "qrcodeid_" + new Date().getTime();
		$("<div dialog />").css({
			position: "fixed",
			width: scanWidth,
			height: scanHeight,
			left: "50%",
			top: "50%",
			borderRadius: 10,
			marginLeft: scanWidth / -2,
			marginTop: scanHeight / -2,
			backgroundColor: "rgb(255,255,255)",
			zIndex: zIndex++
		})
		// 放二维码的容器
		.append($("<div />", {
			id: qrcodeid,
			css: {
				margin: blankWidth
			}
		})).append((function() {
			if ( options.showClose ) {
				return $("<img />", {
					src: options.close,
					title: "关闭",
					click: function() {
						if ( typeof options.onBeforeClose === "function" ) {
							var returnValue = options.onBeforeClose();
							if ( window.blocking === true ) {
								window.preventbeforeclose = true;
								return;
							}
							if ( returnValue === false) {
								return;
							}
							$.closeDialog();
						}
						
						if ( typeof options.onClose === "function" ) {
							options.onClose();
						}
						
					}
				}).css({
					cursor: "pointer",
					position: "absolute",
					right: 3,
					top: 3
				});
			}
		})())
		.appendTo("body");
		
		// 生成二维码
		var qrcode = new QRCode(qrcodeid, {
			text: options.url,
			width: width,
			height: height
		});
		
		if ( !options.showTitle ) {
			qrcode._el.title = "";
		}
		
		// 生成二维码logo
		if ( options.logo && (typeof qrcode.makeIcon) === "function") {
			var logoSize = (options.width + options.height) / 13;
			qrcode.makeIcon(options.logo, logoSize);
		}
		// 二维码下方的文字说明
		if ( options.showDesc ) {
			var $desc = $("<div />", {
				append: $("<img />", {
					src: options.desc,
					width: width
				})
			}).css({
				position: "absolute",
				top: height,
				borderRadius: 4,
				overflow: "hidden",
				marginTop: 10
			}).appendTo("#" + qrcodeid);
		}
	};
})(jQuery);