	function fomatTime(l) {
		var format="yyyy-MM-dd hh:mm:ss";
		var dd = new Date(l);
		var o = {
			"M+" : dd.getMonth() + 1,
			"d+" : dd.getDate(),
			"h+" : dd.getHours(),
			"m+" : dd.getMinutes(),
			"s+" : dd.getSeconds(),
			"q+" : Math.floor((dd.getMonth() + 3) / 3),
			"S" : dd.getMilliseconds()
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (dd.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}