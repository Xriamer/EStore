/**
 * 在引入该js文件时，可以传递model参数，表示刷新模式：
 * 	strict或1:严格模式(默认值)，所有点击后退或前进按钮的方式进入页面都会刷新
 * 	loose或0:宽松模式，只针对点击某元素的方式离开页面才会刷新
 * 示例：<script src="nohistory.js?model=0"></script>
 * 特别提醒：该插件只对get(查询)请求方式有效！
 */
(function(id, refresh) {
	var hisgory = performance.navigation.type === 2,
		scripts = document.scripts,
		src = scripts[scripts.length - 1].src,
		// 宽松模式检测
		loose = /model\s*=\s*(?:loose|0)/.test(src);
	if (loose) {
		document.write('<input type="hidden" id="' + id + '" autocomplete="on" />');
		var element = document.getElementById(id);
		function addEvent(e, type, fn) {
			type = type.toLowerCase().replace(/^on/, "");
			if (e.addEventListener) {
				e.addEventListener(type, fn);
			} else if (e.attachEvent) {
				e.attachEvent("on" + type, fn);
			} else {
				e["on" + type] = fn; // IE5
			}
		}

		function removeEvent(e, type, fn) {
			type = type.toLowerCase().replace(/^on/, "");
			if (e.removeEventListener) {
				e.removeEventListener(type, fn);
			} else if (e.detachEvent) {
				e.detachEvent("on" + type, fn);
			} else {
				e["on" + type] = null; // IE5
			}
		}

		function mark() {
			element.value = "1";
		}

		var binded = false;
		addEvent(window, "mousedown", function() {
			if (!binded) {
				binded = true;
				addEvent(window, "beforeunload", mark);
			}
		});

		addEvent(window, "mouseout", function(e) {
			e = e || window.event;
			var tagName = e.target.tagName;
			if (binded && (tagName == "BODY" || tagName == "HTML")) {
				binded = false;
				removeEvent(window, "beforeunload", mark);
			}
		});

		// 排除“F5”和“Ctrl+R”刷新
		addEvent(document, "keydown", function(e) {
			e = e || window.event;
			if (e.keyCode == 116 || e.ctrlKey && e.keyCode == 82) {
				removeEvent(window, "beforeunload", mark);
			}
		});

		// Compatible IE
		addEvent(document, "readystatechange", function() {
			if (document.readyState === "complete" && element.value && hisgory) {
				refresh();
			}
		});
	} else if (hisgory) {
		refresh();
	}
})("ajaxupload_v2.0, made by chenqi in 2017.12.07", new Function("location+=''"));