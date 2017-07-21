/**
 * jQuery为开发插件提拱了两个方法，
 * 分别是： jQuery.extend(object); 为扩展jQuery类本身.为类添加新的方法。
 * jQuery.fn.extend(object);给jQuery对象添加方法。
 * 此处是基础工具类，
 * 
 * 基础使用，引入及其顺序：		mm-base.js
 * 弹出框使用，引入及其顺序：	mm-base.js，mm-alert.js
 * 表格使用，引入及其顺序：		mm-base.js，mm-table.js，mm-table-extend.js
 * 上传使用，引入及其顺序：		mm-base.js，mm-upload.js
 * 后台整体使用，引入及其顺序：mm-base.js，mm-alert.js，mm-table.js，mm-table-extend.js，mm-main.js
 * 压缩，全部引入：						mmui.js
 *
 * version：3.3
 * 
 * Copyright (c) 2017 llb LastDate: 2017-07-10
 */

!function(window, jQuery) {
	"use strict";
	if(window.mmUtl)return ;
	
	var $ = jQuery,
	win = $(window);
	var mmUtl = {
		v:"3.3",
		yes : "yes",
		no : "no",
		hostUrl : "",
		status : "status",
		success : "success",
		fail : "fail",
		error : "error",
		msg : "msg",
		ent : "ent",
		list : "list",
		page : "page",
		randomToken : "random_token",
		html:null,
		scrollbarWidth:0,
		init : function() {
			var ua = navigator.userAgent.toLowerCase();
			var matches = /(?:msie|firefox|webkit|opera)[\/:\s](\d+)/.exec(ua);
			mmUtl.IE = ua.indexOf('msie') > -1 && ua.indexOf('opera') == -1;
			mmUtl.IEV = matches ? matches[1] : '0';
			
			Array.prototype.distinct = function() {
				var newArr = [], obj = {};
				for (var i = 0, len = this.length; i < len; i++) {
					if (!obj[typeof (this[i]) + this[i]]) {
						newArr.push(this[i]);
						obj[typeof (this[i]) + this[i]] = 'new';
					}
				}
				return newArr;
			}
			
			var ctx = $("html").attr("ctx");
			if (ctx) {
				mmUtl.hostUrl = ctx;
				window.$ctx=ctx;
			}
			mmUtl.setWH();
		},
		//获取浏览器滚动条的宽高
		setWH:function(){
			var i = document.createElement('p');
			i.style.width = '100%';
			i.style.height = '200px';
			var o = document.createElement('div');
			o.style.position = 'absolute';
			o.style.top = '0px';
			o.style.left = '0px';
			o.style.visibility = 'hidden';
			o.style.width = '200px';
			o.style.height = '150px';
			o.style.overflow = 'hidden';
			o.appendChild(i);
			document.body.appendChild(o);
			var w1 = i.offsetWidth;
			var h1 = i.offsetHeight;
			o.style.overflow = 'scroll';
			var w2 = i.offsetWidth;
			var h2 = i.offsetHeight;
			if (w1 == w2)
				w2 = o.clientWidth;
			if (h1 == h2)
				h2 = o.clientWidth;
			document.body.removeChild(o);
			mmUtl.scrollbarWidth = w1 - w2;
			mmUtl.scrollbarHeight = h1 - h2;
		},
		//获取随机数
		getRandom : function(n) {
			if (!n)
				n = 0;
			return Math.floor(Math.random() * (n + 1));
		},
		//判断后台返回是否成功
		issuccess : function(result) {
			if (result[mmUtl.status] == mmUtl.success) {
				return true;
			}
			return false;
		},
		//判断后台返回是否失败
		isfail : function(result) {
			if (result[mmUtl.status] == mmUtl.fail) {
				return true;
			}
			return false;
		},
		//如果没有提供hostUrl参数，可以用这个获取js路径
		getPath : function() {
			var js = document.scripts, script = js[js.length - 1], jsPath = script.src;
			if (script.getAttribute('merge'))
				return;
			return jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
		}
	};

	//json工具
	mmUtl.json = {
		toString : function(obj) {
			return JSON.stringify(obj);				//Object -->string
		},
		toObject : function(text, reviver) { 
			return JSON.parse(text, reviver)	// string-->Object
		}
	}
	
	//日志工具
	mmUtl.log = {
		info : function(msg) {
			console.info(msg);
		},
		error : function(msg) {
			console.error(msg);
		}
	}

	//子页面获取
	mmUtl.iframe = {
		//获取子页面window对象	
		getWin : function(iframe) {
			return iframe[0].contentWindow;
		},
		//获取子页面document对象
		getDocument : function(iframe) {
			return iframe[0].contentDocument || iframe[0].contentWindow.document;
		},
		//获取子页面body对象
		getBody : function(iframe) {
			return mmUtl.iframe.getDocument(iframe).body;
		},
		//监听子页面加载成功事件
		onload : function(iframe, backFn) {
			if (iframe[0].attachEvent) {
				iframe[0].attachEvent("onload", function() {
					backFn.call(iframe[0]);
				});
			} else {
				iframe[0].onload = function() {
					backFn.call(iframe[0]);
				};
			}
		},
		//用子页面设置父页面的高度
		resetH : function(iframe) {
			mmUtl.iframe.onload(iframe, function() {
				var subWeb = mmUtl.iframe.getDocument(iframe);
				if (subWeb != null) {
					$(this).height(subWeb.body.scrollHeight);
				}
			});
		}
	}

	// 过滤对象中的空
	mmUtl.filter = {
		ise : function(obj) {
			if ($.type(obj) == "object") {
				return mmUtl.filter.obj(obj);
			} else if ($.type(obj) == "array") {
				return mmUtl.filter.arr(obj);
			} else if ($.type(obj) == "string") {
				return mmUtl.filter.str(obj);
			} else if ($.type(obj) == "number") {
				return obj;
			} else {
				return obj;
			}
		},
		str : function(str) {
			return $.trim(str);
		},
		obj : function(obj) {
			$.each(obj, function(i, f) {
				obj[i] = mmUtl.filter.ise(f);
			})
			return obj;
		},
		arr : function(arr) {
			$.each(arr, function(i, f) {
				arr[i] = mmUtl.filter.ise(f);
			})
			return arr;
		}
	}

	//数据验证工具
	mmUtl.valid = {
		isFalse : function(obj) {
			// if:true/false,null或undefined,0 或NaN,非空串（“”）/空串("")
			if (obj)
				return true;
			else
				return false;
		},
		// 判断是否为金额，默认整数最大8位，小数2位
		isPrice : function(price, len) {
			if (price) {
				if (!len || len < 0)
					len = 7;// 整数位最长8位
				var exp = new RegExp('^([1-9][\\d]{0,' + len + '}|0)(\\.[\\d]{1,2})?$', "gi");
				if (exp.test(price)) {
					return true;
				} else {
					return false;
				}
			}
		},
		// 判断是否含有中文
		isChinese : function(str) {
			var reg = /[\u4e00-\u9fa5\uf900-\ufa2d]/g;
			if (reg.test(str))
				return true;
			return false;
		},
		// 验证邮箱
		isEmail : function(str) {
			var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			if (reg.test(str))
				return true;
			return false;
		},
		// 验证日期时间--完全匹配
		isDateTime : function(str) {
			var reg = /^(\d{4})\-(\d{2})\-(\d{2})(\s{1})(\d{2}):(\d{2}):(\d{2})$/;
			if (str.match(reg))
				return true;
			return false;
		},
		// 验证 正整数--完全匹配
		isInteger : function(str) {
			if (str.match(/^\d+$/))
				return true;
			return false;
		},
		// 验证 负整数--完全匹配
		isInteger_ : function(str) {
			if (str.match(/^-\d+$/))
				return true;
			return false;
		},
		// 验证 整数--完全匹配
		isInt : function(str) {
			if (str.match(/^-?\d+$/))
				return true;
			return false;
		},
		// 验证 浮点数--完全匹配
		isFloat : function(str) {
			if (str.match(/^\d+\.?\d+$/))
				return true;
			return false;
		},
		// 验证浮点数--完全匹配
		isFloat_ : function(str) {
			if (str.match(/^-?\d+\.?\d*$/))
				return true;
			return false;
		},
		// 验证数字类型--完全匹配
		isNumber : function(str) {
			if (str.match(/^-?\d+\.\d+$/))
				return true;
			return false;
		},
		// 验证身份证
		isCard : function(str) {
			if (str.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/))
				return true;
			return false;
		}
	}

	//简单工具类
	mmUtl.utl = {
		// 获取页面的html和js部分
		getScriptHtml : function(html) {
			if (!html)
				html = '';
			var defaults = {
				doc : "",
				regx : /<script(.|\n)*?>(.|\n|\r\n)*?<\/script>/ig
			};
			var options = $.extend(defaults, {
				doc : html
			});
			var jss = options.doc.match(options.regx);

			if (jss) {
				var j = "";
				for (var i = 0, ii = jss.length; i < ii; i++) {
					j = j + jss[i];
				}
				jss = j;
			} else {
				jss = '';
			}
			html = html.replace(options.regx, '');

			return {
				'html' : html,
				'jss' : jss
			}
		},
		// 获得窗体高
		getWindowH : function() {
			return $(window).height();
		},
		// 获得窗体宽
		getWindowW : function() {
			return $(window).width();
		},
		// 遮罩底层重置高度
		setOverlayFix : function(fix) {
			fix.css("height", 0);
			fix.css("height", $(document).height());
		},
		//批量查询时，格式化输入
		text2Str : function(text) {
			text = $.trim(text);
			text = text.replace(/\s+/g, " ");
			text = text.replace(/\uff0c/g, ",");

			if (text.match(/,/)) {
				text = text.replace(/\n/g, "");
				text = text.replace(/ /g, "");
			} else if (text.match(/ /)) {
				text = text.replace(/,/g, "");
				text = text.replace(/\n/g, "");
				text = text.replace(/ /g, ",");
			}

			if (text.substring(text.length - 1) == ",") {
				text = text.substring(0, text.length - 1);
			}
			return text;
		},
		//支持逗号，回车，空格
		text2Array : function(text) {
			text = text2Str(text);
			return text.split(',');
		},
		//选中元素
		select : function(ele) {
			ele.focus().select();
		},
		// 对象{}转数组[]
		obj2arr : function(args) {
			var arr = [];
			$.each(args, function(name, value) {
				arr.push({
					name : name,
					value : value
				});
			});
			return arr;
		},
		//获取图片的宽高
		getImgWH : function(img, callback) {
			var nWidth, nHeight;
			if (img.naturalWidth) { // 现代浏览器
				nWidth = img.naturalWidth;
				nHeight = img.naturalHeight;
				callback(nWidth, nHeight);
			} else { // IE6/7/8
				var imgae = new Image();
				image.src = img.src;
				image.onload = function() {
					callback(image.width, image.height);
				}
			}
		},
		//按比例缩放图片宽高
		resizeImg : function(img, width, height) {
			if (!img || img.length == 0) {
				return;
			}
			function backF(sw, sh) {
				if (width) {
					if (sw > width) {
						img.css("width", width);
						var h = sh * width / sw
						img.css("height", h);
					}
				} else if (height) {
					if (sh > height) {
						img.css("height", height);
						var w = sw * height / sh
						img.css("width", w);
					}
				}
			}
			mmUtl.utl.getImgWH(img[0], backF);
		}
	}

	//对象继承
	mmUtl.extend = function(option, child) {
		if (!child) {
			return option;
		}
		for ( var key in option) {
			if (child.hasOwnProperty(key)) {
				option[key] = child[key];
			}
		}
		return option;
	}

	//时间工具
	mmUtl.time = {
		// 获取当前时间
		getNowTime : function() {
			var date = new Date();
			return date.getFullYear() + "-" + (Number(date.getMonth()) + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes()
					+ ":" + date.getSeconds();
		},
		// 获取第几天时间
		_getTime : function(num, date) {
			var d = new Date();
			if (date && $.type(date) == "date") {
				d = date;
			}
			var n = 0;
			if (num && parseInt(num)) {
				n = parseInt(num);
			}
			var year = d.getFullYear();
			var mon = d.getMonth() + 1;
			var day = d.getDate();
			if (day <= n) {
				if (mon > 1) {
					mon = mon - 1;
				} else {
					year = year - 1;
					mon = 12;
				}
			}
			d.setDate(d.getDate() - n);
			year = d.getFullYear();
			mon = d.getMonth() + 1;
			day = d.getDate();
			s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day) + " " + d.getHours() + ":" + d.getMinutes() + ":"
					+ d.getSeconds();
			return s;
		},
		// 获取当前时间的前几天
		getTimeBy : function(n, date) {
			return mmUtl.time._getTime(n, date);
		},
		// 获取当前天
		getNowDate : function() {
			var date = new Date();
			return date.getFullYear() + "-" + (Number(date.getMonth()) + 1) + "-" + date.getDate();
		},
		//格式化long型的时间
		_format : function(l, format) {
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
		},
		//格式化long型的时间
		formatDate : function(date, pattern) {
			if (date == undefined) {
				date = new Date();
			} else {
				date = new Date(date);
			}
			if (pattern == undefined) {
				pattern = "yyyy-MM-dd hh:mm:ss";
			}
			return mmUtl.time._format(date, pattern);
		},
		//格式化long型的时间
		formatDateByLong : function(d) {
			if (!d) {
				return "";
			}
			return mmUtl.time.formatDate(new Date(d));
		},
		//设置输入框的时间--插件
		setDateTime : function(str, paras) {
			paras = $.extend({
				language : 'zh-CN',
				weekStart : 1,
				autoclose : true,
				minView : 2,
				pickerPosition : 'bottom-left',
				todayHighlight : true,
				keyboardNavigation : false,
				todayBtn : 'linked',
				startView : 'month',
				format : 'yyyy-mm-dd hh:ii:ss'
			}, paras);
			$(str).datetimepicker(paras);
		}
	}

	//多媒体
	mmUtl.media = {
		//播放音乐
		playAudio : function(src) {
			var audio = $("#audio");
			if (audio.length == 0) {
				audio = $('<audio id="audio" >');
				$("body").append(audio);
			}
			audio.attr("src", mmUtl.hostUrl + src);
			audio[0].play();
		}
	}

	//ajax工具
	mmUtl.ajax = {
		//post方式请求json返回，普通传参到后台
		postJson : function(url, backFn, pars) {
			mmUtl.ajax._ajax("json", "application/x-www-form-urlencoded", true, "post", false, url, backFn, pars);
		},
		//get方式请求json返回，普通传参到后台
		getJson : function(url, backFn, pars) {
			url = mmUtl.ajax.addRandom(url);
			mmUtl.ajax._ajax("json", "application/x-www-form-urlencoded", true, "get", false, url, backFn, pars);
		},
		//post方式请求json返回，json传参到后台
		postJsonAnt : function(url, backFn, pars) {
			mmUtl.ajax._ajax("json", "application/json", true, "post", true, url, backFn, pars);
		},
		//post方式请求html页面返回，普通传参到后台
		postHtml : function(url, backFn, pars) {
			mmUtl.ajax._ajax("html", "application/x-www-form-urlencoded", true, "post", false, url, backFn, pars);
		},
		//get方式请求html页面返回，普通传参到后台
		getHtml : function(url, backFn, pars) {
			url = mmUtl.ajax.addRandom(url);
			mmUtl.ajax._ajax("html", "application/x-www-form-urlencoded", true, "get", false, url, backFn, pars);
		},
		//post方式请求html页面返回，json传参到后台
		postHtmlAnt : function(url, backFn, pars) {
			mmUtl.ajax._ajax("html", "application/json", true, "post", true, url, backFn, pars);
		},
		_ajax : function(dataType, contentType, async, reqType, ant, url, backFn, pars) {
			url = mmUtl.ajax.getUtl(url);
			var args = "";
			pars = mmUtl.filter.ise(pars);
			if ($.type(pars) == "object" || $.type(pars) == "array") {
				if (ant) {
					args = JSON.stringify(pars);
				} else {
					args = pars;
				}
			} else if ($.type(pars) == "string") {
				args = pars;
			}
			// alert(args);alert(mmUtl.hostUrl + url);
			$.ajax({
				type : reqType, // get,post
				contentType : contentType, // application/x-www-form-urlencoded
				dataType : dataType, // 返回值类型 json ,html
				async : async, // true
				data : args, // {}
				url : url,
				success : function(result) {
					if (result[mmUtl.status] == mmUtl.error) {
						if (result[mmUtl.msg]) {
							mmUtl.ui.error(result[mmUtl.msg]);
						} else {
							mmUtl.ui.error('出错了');
						}
					} else {
						backFn(result);
					}
				},
				error : function() {
					mmUtl.ui.error('出错了，请稍后再试');
				}
			});
		},
		//请求地址添加随机数
		addRandom : function(url) {
			if (url.indexOf("?") > 0) {
				url + "&v=" + mmUtl.getRandom(7)
			} else {
				url + "?v=" + mmUtl.getRandom(7)
			}
			return url;
		},
		//获取from表单数据，并处理特殊字符，以get参数形式返回
		getUrlArgs : function(form) {
			var args = "";
			$.each(form.serializeArray(), function(i, f) {
				args = args + "&" + [ f.name ] + "=" + f.value;
			})
			if (args.length > 1) {
				args = args.substring(1);
			}
			args = encodeURI(args);
			return args;
		},
		//获取from表单数据，数组返回
		getArgs : function(form) {
			var args = {};
			$.each(form.serializeArray(), function(i, f) {
				args[f.name] = f.value;
			})
			return args;
		},
		//把sales中的数据按name做key的方式构造args对象返回
		array : function(name, sales) {// 接收数组对象时，数组必须确定长度，否则用list对象
			var args = {}
			for (var i = 0, ii = sales.length; i < ii; i++) {
				args[name + '[' + i + ']'] = sales[i];
			}
			return args;
		},
		arrayObj : function(name, pro, sales) {// 接收数组复杂对象
			var args = {}
			for (var i = 0, ii = sales.length; i < ii; i++) {
				for (var j = 0, jj = pro.length; j < jj; j++) {
					args[name + '[' + i + ']' + pro[j]] = sales[i];
				}
			}
			return args;
		},
		//把html中的js添加到页面并且执行
		exeHtml : function(html) {
			var res = mmUtl.utl.getScriptHtml(html);
			if (res.jss) {
				$("body").append(res.jss);
			}
			return res.html;
		},
		//统一请求地址
		getUtl : function(url) {
			var http = url.substring(0, 7);
			if ("http://" != http) {
				url = mmUtl.hostUrl + url
			}
			return url;
		},
		//带参数请求到服务器下载文件
		downFile : function(url, data) {
			url = mmUtl.ajax.getUtl(url);
			var form = $("<form>");
			form.attr("style", "display:none");
			form.attr("target", "");
			form.attr("method", "post");
			form.attr("action", url);
			$("body").append(form);
			for ( var name in data) {
				var input1 = $("<input>");
				input1.attr("type", "hidden");
				input1.attr("name", name);
				input1.attr("value", data[name]);
				form.append(input1);
			}
			form.submit();// 表单提交
			form.remove();
		}
	}
	
	//表格工具
	mmUtl.table={
			//从集合list中查找对象的属性from等于value的值，找到了则返回此对象to的值
			getPropertyFromOther : function(list, from, to, value) {
				var result = '';
				$.each(list, function(i, item) {
					if (item[from] === value) {
						result = item[to];
						return false;
					}
					return true;
				});
				return result;
			},
			//设置字段的序列号：fieldIndex，从0开始算起，第一列就是fieldIndex:0
			setFieldIndex : function(columns) {
				var i, j, k, totalCol = 0, flag = [];

				for (i = 0; i < columns[0].length; i++) {
					totalCol += columns[0][i].colspan || 1;
				}

				for (i = 0; i < columns.length; i++) {
					flag[i] = [];
					for (j = 0; j < totalCol; j++) {
						flag[i][j] = false;
					}
				}

				for (i = 0; i < columns.length; i++) {
					for (j = 0; j < columns[i].length; j++) {
						var r = columns[i][j], rowspan = r.rowspan || 1, colspan = r.colspan || 1, index = $.inArray(false, flag[i]);

						if (colspan === 1) {
							r.fieldIndex = index;
							if (typeof r.field === 'undefined'|| r.field == '') {
								r.field = index;
							}
						}

						for (k = 0; k < rowspan; k++) {
							flag[i + k][index] = true;
						}
						for (k = 0; k < colspan; k++) {
							flag[i][index + k] = true;
						}
					}
				}
			},
			//比较两个对象 compareLength 等于true 就是对比两个对象的长度 否则完全对比两个对象的所有属性值
			compareObjects : function(objectA, objectB, compareLength) {
				var objectAProperties = Object.getOwnPropertyNames(objectA), objectBProperties = Object.getOwnPropertyNames(objectB), propName = '';
				if (compareLength) {
					if (objectAProperties.length !== objectBProperties.length) {
						return false;
					}
				}
				for (var i = 0; i < objectAProperties.length; i++) {
					propName = objectAProperties[i];

					if ($.inArray(propName, objectBProperties) > -1) {
						if (objectA[propName] !== objectB[propName]) {
							return false;
						}
					}
				}
				return true;
			},
			 //格式化字符串，包括& < > " ' `换成对应的转意符
			escapeHTML : function(text) {
				if (typeof text === 'string') {
					return text.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#039;').replace(/`/g, '&#x60;');
				}
				return text;
			},
			 //获取元素的内部高度
			getRealHeight : function($el) {
				var height = 0;
				$el.children().each(function() {
					if (height < $(this).outerHeight(true)) {
						height = $(this).outerHeight(true);
					}
				});
				return height;
			},
			//把数组中的字符添加-
			getRealDataAttr : function(dataAttr) {
				for ( var attr in dataAttr) {
					var auxAttr = attr.split(/(?=[A-Z])/).join('-').toLowerCase();
					if (auxAttr !== attr) {
						dataAttr[auxAttr] = dataAttr[attr];
						delete dataAttr[attr];
					}
				}

				return dataAttr;
			},
			 //获取item条目数据中的对应的字段field的值
			getItemField : function(item, field, escape) {
				var value = item;
				if (typeof field !== 'string' || item.hasOwnProperty(field)) {
					return escape ? mmUtl.table.escapeHTML(item[field]) : item[field];
				}

				// 如果此值是复合类型，则拆分获取
				var props = field.split('.');
				$.each(props, function(idx, obj) {
					value = value && value[props[idx]];
				});
				return escape ? mmUtl.table.escapeHTML(value) : value;
			},
			//判断是否为ie浏览器
			isIEBrowser : function() {
				return !!(navigator.userAgent.indexOf("MSIE ") > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./));
			},
			//从columns对象集合中查找field属性值等于field的，找到返回所在位置index，否则返回-1
			getFieldIndex : function(columns, field) {
				var index = -1;
				$.each(columns, function(i, column) {
					if (column.field === field) {
						index = i;
						return false;
					}
					return true;
				});
				return index;
			}
	}

	/**
	 * 替换占位符%s的内容
	 */
	var sprintf = function(str) {
		var args = arguments, flag = true, i = 1;
		str = str.replace(/%s/g, function() {
			var arg = args[i++];

			if (typeof arg === 'undefined') {
				flag = false;
				return '';
			}
			return arg;
		});
		return flag ? str : '';
	};

	/**
	 * 计算获取执行对象 self 执行的上下环境 name,如果是对象，就获取返回， 如果是方法就执行args参数并返回，
	 * 如果是字符串就把字符串连接到args中返回 如果都不是就返回默认值defaultValue
	 */
	var calculateObjectValue = function(self, name, args, defaultValue) {
		var func = name;
		if (typeof name === 'string') {
			var names = name.split('.');

			if (names.length > 1) {
				func = window;
				$.each(names, function(i, f) {
					func = func[f];
				});
			} else {
				func = window[name];
			}
		}
		if (typeof func === 'object') {
			return func;
		}
		if (typeof func === 'function') {
			return func.apply(self, args);
		}
		if (!func && typeof name === 'string' && sprintf.apply(this, [ name ].concat(args))) {
			return sprintf.apply(this, [ name ].concat(args));
		}
		return defaultValue;
	};
	
	window.calculateObjectValue=calculateObjectValue;
	window.sprintf=sprintf;
	window.mmUtl = mmUtl;
	
	$(function(){
		//初始化工具类的参数
		mmUtl.init(); 
	})
	
}(window,jQuery)