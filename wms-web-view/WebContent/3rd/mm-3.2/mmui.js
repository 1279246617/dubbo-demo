/**
 * jQuery为开发插件提拱了两个方法，分别是： jQuery.extend(object); 为扩展jQuery类本身.为类添加新的方法。
 * jQuery.fn.extend(object);给jQuery对象添加方法。
 * ui，及工具类 Copyright (c) 2016 llb LastDate: 2016-04-07
 */

!function(window, jQuery) {
	"use strict";
	if(window.mmUtl)return ;
	
	var $ = jQuery,
	win = $(window);
	var mmUtl = {
		v:"3.1",
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
			mmUtl.html = $("html");
			var ctx = mmUtl.html.attr("ctx");
			if (ctx) {
				mmUtl.hostUrl = ctx;
			}
			mmUtl.setWH();
		},
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
		getRandom : function(n) {
			if (!n)
				n = 0;
			return Math.floor(Math.random() * (n + 1));
		},
		issuccess : function(result) {
			if (result[mmUtl.status] == mmUtl.success) {
				return true;
			}
			return false;
		},
		isfail : function(result) {
			if (result[mmUtl.status] == mmUtl.fail) {
				return true;
			}
			return false;
		},
		getIframeDoc : function(iframe) {
			return (iframe.contentWindow || iframe.contentDocument).document;
		}
	};

	mmUtl.json = {
		toString : function(obj) {
			return JSON.stringify(obj);
		},
		toObject : function(text, reviver) { // string-->Object
			return JSON.parse(text, reviver)
		}
	}

	mmUtl.log = {
		info : function(msg) {
			console.info(msg);
		},
		error : function(msg) {
			console.error(msg);
		}
	}

	mmUtl.iframe = {
		getWin : function(iframe) {
			return iframe[0].contentWindow;
		},
		getDocument : function(iframe) {
			return iframe[0].contentDocument || iframe[0].contentWindow.document;
		},
		getBody : function(iframe) {
			return mmUtl.iframe.getDocument(iframe).body;
		},
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
		// 验证日期--完全匹配
		isDate : function(str) {
			var reg = /^(\d{4})\-(\d{2})\-(\d{2})$/;
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
		},
		eleType : function(ele) {
			return ele[0].tagName.toLowerCase();
		}
	}

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
		toString : function(obj) {
			if (obj == null) {
				return "";
			}
			return obj;
		},
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
		// 支持逗号，回车，空格
		text2Array : function(text) {
			text = text2Str(text);
			return text.split(',');
		},
		Math : function(original) {
			Math.round(original * 100) / 100;
		},
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
		formatDate_ : function(date, pattern) {
			if (date == undefined) {
				date = new Date();
			} else {
				date = new Date(date);
			}
			if (pattern == undefined) {
				pattern = "yyyy-MM-dd";
			}
			return mmUtl.time._format(date, pattern);
		},
		formatDateByLong : function(d) {
			if (!d) {
				return "";
			}
			return mmUtl.time.formatDate(new Date(d));
		},
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

	mmUtl.media = {
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

	mmUtl.ajax = {
		postJson : function(url, backFn, pars) {
			mmUtl.ajax._ajax("json", "application/x-www-form-urlencoded", true, "post", false, url, backFn, pars);
		},
		postJsonSyn : function(url, backFn, pars) {
			mmUtl.ajax._ajax("json", "application/x-www-form-urlencoded", false, "post", false, url, backFn, pars);
		},
		getJson : function(url, backFn, pars) {
			url = mmUtl.ajax.addRandom(url);
			mmUtl.ajax._ajax("json", "application/x-www-form-urlencoded", true, "get", false, url, backFn, pars);
		},
		postJsonAnt : function(url, backFn, pars) {
			mmUtl.ajax._ajax("json", "application/json", true, "post", true, url, backFn, pars);
		},
		postJsonAntSyn : function(url, backFn, pars) {
			mmUtl.ajax._ajax("json", "application/json", false, "post", true, url, backFn, pars);
		},
		postHtml : function(url, backFn, pars) {
			mmUtl.ajax._ajax("html", "application/x-www-form-urlencoded", true, "post", false, url, backFn, pars);
		},
		getHtml : function(url, backFn, pars) {
			url = mmUtl.ajax.addRandom(url);
			mmUtl.ajax._ajax("html", "application/x-www-form-urlencoded", true, "get", false, url, backFn, pars);
		},
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
		addRandom : function(url) {
			if (url.indexOf("?") > 0) {
				url + "&v=" + mmUtl.getRandom(7)
			} else {
				url + "?v=" + mmUtl.getRandom(7)
			}
			return url;
		},
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
		getArgs : function(form) {
			var args = {};
			$.each(form.serializeArray(), function(i, f) {
				args[f.name] = f.value;
			})
			return args;
		},
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
		exeHtml : function(html) {
			var res = mmUtl.utl.getScriptHtml(html);
			if (res.jss) {
				$("body").append(res.jss);
			}
			return res.html;
		},
		getUtl : function(url) {
			var http = url.substring(0, 7);
			if ("http://" != http) {
				url = mmUtl.hostUrl + url
			}
			return url;
		},
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

	// 缓存常用字符
	var doms = [ 'mmui-layer', '.mmui-layer-title', '.mmui-layer-main', '.mmui-layer-dialog', 'mmui-layer-iframe', 'mmui-layer-content',
			'mmui-layer-btn', 'mmui-layer-close' ];
	doms.anim = [ 'mmui-anim', 'mmui-anim-01', 'mmui-anim-02', 'mmui-anim-03', 'mmui-anim-04', 'mmui-anim-05', 'mmui-anim-06' ];
	
	var ready = {
		getPath : function() {
			var js = document.scripts, script = js[js.length - 1], jsPath = script.src;
			if (script.getAttribute('merge'))
				return;
			return jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
		}(),

		// 屏蔽Enter触发弹层
		enter : function(e) {
			if (e.keyCode === 13)
				e.preventDefault();
		},
		// for ie6 恢复select
		reselect : function() {
			$.each($('select'), function(index, value) {
				var sthis = $(this);
				if (!sthis.parents('.' + doms[0])[0]) {
					(sthis.attr('layer') == 1 && $('.' + doms[0]).length < 1) && sthis.removeAttr('layer').show();
				}
				sthis = null;
			});
		},
		record : function(mmuio) {
			var area = [ mmuio.outerWidth(), mmuio.outerHeight(), mmuio.position().top, mmuio.position().left + parseFloat(mmuio.css('margin-left')) ];
			mmuio.find('.mmui-layer-max').addClass('mmui-layer-maxmin');
			mmuio.attr({
				area : area
			});
		},
		rescollbar : function(index) {
			if (mmUtl.html.attr('layer-full') == index) {
				if (mmUtl.html[0].style.removeProperty) {
					mmUtl.html[0].style.removeProperty('overflow');
				} else {
					mmUtl.html[0].style.removeAttribute('overflow');
				}
				mmUtl.html.removeAttr('layer-full');
			}
		},
		config : {},
		end : {},
		btn : [ '&#x786E;&#x5B9A;', '&#x53D6;&#x6D88;' ],

		// 五种原始层模式0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
		type : [ 'dialog', 'page', 'iframe', 'loading', 'tips' ]
	};

	// 默认内置方法。
	var mmui = {
		ie6 : !!window.ActiveXObject && !window.XMLHttpRequest,
		index : 0,
		path : ready.getPath,
		config : function(options, fn) {
			var item = 0;
			options = options || {};
			mmui.cache = ready.config = $.extend(ready.config, options);
			mmui.path = ready.config.path || mmui.path;
			typeof options.extend === 'string' && (options.extend = [ options.extend ]);
			mmui.use('skin/mmui.css', (options.extend && options.extend.length > 0) ? (function loop() {
				var ext = options.extend;
				mmui.use(ext[ext[item] ? item : item - 1], item < ext.length ? function() {
					++item;
					return loop;
				}() : fn);
			}()) : fn);
			return this;
		},
		//基本入口
		open : function(deliver) {
			var o = new Class(deliver);
			return o.index;
		},
		// 载入配件
		use : function(module, fn, readyMethod) {
			var i = 0, head = $('head')[0];
			var module = module.replace(/\s/g, '');
			var iscss = /\.css$/.test(module);
			var node = document.createElement(iscss ? 'link' : 'script');
			var id = 'mmui_layer_' + module.replace(/\.|\//g, '');
			if (!mmui.path)
				return;
			if (iscss) {
				node.rel = 'stylesheet';
			}
			node[iscss ? 'href' : 'src'] = /^http:\/\//.test(module) ? module : mmui.path + module;
			node.id = id;
			if (!$('#' + id)[0]) {
				head.appendChild(node);
			}
			// 轮询加载就绪
			;
			(function poll() {
				;
				(iscss ? parseInt($('#' + id).css('width')) === 1989 : mmui[readyMethod || id]) ? function() {
					fn && fn();
					try {
						iscss || head.removeChild(node);
					} catch (e) {
					}
					;
				}() : setTimeout(poll, 100);
			}());
			return this;
		},

		ready : function(path, fn) {
			var type = typeof path === 'function';
			if (type)
				fn = path;
			mmui.config($.extend(ready.config, function() {
				return type ? {} : {
					path : path
				};
			}()), fn);
			return this;
		},

		// 各种快捷引用
		_alert : function(content, options, yes) {
			var type = typeof options === 'function';
			if (type)
				yes = options;
			return mmui.open($.extend({
				content : content,
				yes : yes
			}, type ? {} : options));
		},

		_confirm : function(content, options, yes, cancel) {
			var type = typeof options === 'function';
			if (type) {
				cancel = yes;
				yes = options;
			}
			return mmui.open($.extend({
				content : content,
				btn : ready.btn,
				yes : yes,
				cancel : cancel
			}, type ? {} : options));
		},

		msg : function(content, options, end) { // 最常用提示层
			var type = typeof options === 'function', rskin = ready.config.skin;
			var skin = (rskin ? rskin + ' ' + rskin + '-msg' : '') || 'mmui-layer-msg';
			var shift = doms.anim.length - 1;
			if (type)
				end = options;
			return mmui.open($.extend({
				content : content,
				time : 3000,
				shade : false,
				skin : skin,
				title : false,
				closeBtn : false,
				btn : false,
				end : end
			}, (type && !ready.config.skin) ? {
				skin : skin + ' mmui-layer-hui',
				shift : shift
			} : function() {
				options = options || {};
				if (options.icon === -1 || options.icon === undefined && !ready.config.skin) {
					options.skin = skin + ' ' + (options.skin || 'mmui-layer-hui');
				}
				return options;
			}()));
		},

		load : function(icon, options) {
			return mmui.open($.extend({
				type : 3,
				icon : icon || 0,
				shade : 0.01
			}, options));
		},

		tips : function(content, follow, options) {
			return mmui.open($.extend({
				type : 4,
				content : [ content, follow ],
				closeBtn : false,
				time : 3000,
				maxWidth : 210
			}, options));
		},
		oper:function(msg,icon){
			$(".mmui-oper").remove();
			var mmuiOper=$("<div class=\"mmui-oper mmui-color"+icon+"\"><i class=\"mmui-layer-ico mmui-layer-ico"+icon+" \"></i>"+msg+"</div>");
			$("body").prepend(mmuiOper);
			var w=mmuiOper.width();
			mmuiOper.css("marginLeft",(-w/2));
			setTimeout(function() {
				mmuiOper.remove();
			},6000);
		},
		alert:function(msg,icon,time,shift){
			time=time?time:6000;
			shift=shift?shift:6;
			mmui._alert(msg, {
				shade : false,
				title: false,
				btn : false,
				icon : icon,
				time : time,
				shift : shift
			});
		},
		confirm:function(msg,yes,cancel){
			mmui._confirm(msg, {
				icon : 3,
				title : '提示'
			},yes,cancel);
		}
	};

	/**
	 * 每次都会新生成此对象
	 */
	var Class = function(setings) {
		var that = this;
		that.index = ++mmui.index;
		that.config = $.extend({}, that.config, ready.config, setings);
		//如果有前置函数就先执行
		if (typeof that.config.beforFn === 'function')
			that.config.beforFn(that.index);
		//如果有后置函数则执行
	    if(typeof that.config.afterFn === 'function'){
	    	that.config.success=function(mmuio,index){
	    		that.config.afterFn(index,mmuio);
	    	}
	    }
		that.creat();
	};

	/**
	 * 定义mmuitable对象 $el 是 <table id="list_body" ></table>
	 */
	var mmuiTable = function(el, options) {
		this.options = options;
		this.$el = $(el);
		this.$el_ = this.$el.clone();
		this.timeoutId_ = 0;
		this.timeoutFooter_ = 0;

		this.init();
	};

	/**
	 * 定义mmuitable对象的默认值
	 */
	mmuiTable.DEFAULTS = {
		classes : 'table table-hover',
		locale : undefined,
		height : undefined,
		undefinedText : '-',
		sortName : undefined,
		sortOrder : 'desc',
		striped : false,
		columns : [ [] ],
		data : [],
		dataField : 'list',
		method : 'post',
		url : undefined,
		ajax : undefined,
		cache : true,
		contentType : 'application/x-www-form-urlencoded', // application/json
		dataType : 'json',
		ajaxOptions : {},
		queryParams : function(params) {
			return params;
		},
		queryParamsType : 'pageSize', // undefined
		responseHandler : function(res) {
			return res;
		},
		pagination : true, // 是否显示分页
		onlyInfoPagination : false,
		sidePagination : 'server', // client or server
		totalRows : 0, // server side need to set
		pageNumber : 1,
		pageSize : 50,
		pageList : [ 50, 100, 500, 1000 ],
		paginationHAlign : 'right', // right, left
		paginationVAlign : 'bottom', // bottom, top, both
		paginationDetailHAlign : 'left', // right, left
		paginationPreText : '&lsaquo;',
		paginationNextText : '&rsaquo;',
		search : false,
		searchOnEnterKey : false,
		strictSearch : false,
		searchAlign : 'right',
		selectItemName : 'btSelectItem',
		showHeader : true,
		showFooter : false,
		showColumns : false,
		showPaginationSwitch : false,
		showRefresh : false,
		showToggle : false,
		buttonsAlign : 'right',
		smartDisplay : true,
		escape : true,
		minimumCountColumns : 1,
		idField : undefined,
		uniqueId : undefined,
		cardView : false,								//是否显示详细视图
		detailView : false,								//是否显示父子表
		detailFormatter : function(index, row) {
			return '';
		},
		trimOnSearch : true,
		clickToSelect : false,
		singleSelect : false,
		toolbar : undefined,
		toolbarAlign : 'left',
		checkboxHeader : true,
		sortable : true,
		silentSort : true,
		maintainSelected : false,
		searchTimeOut : 500,
		searchText : '',
		iconSize : undefined,
		iconsPrefix : 'glyphicon',
		icons : {
			paginationSwitchDown : 'glyphicon-collapse-down icon-chevron-down',
			paginationSwitchUp : 'glyphicon-collapse-up icon-chevron-up',
			refresh : 'glyphicon-refresh icon-refresh',
			toggle : 'glyphicon-list-alt icon-list-alt',
			columns : 'glyphicon-th icon-th',
			detailOpen : 'glyphicon-plus icon-plus',
			detailClose : 'glyphicon-minus icon-minus'
		},

		rowStyle : function(row, index) {
			return {};
		},

		rowAttributes : function(row, index) {
			return {};
		},

		onAll : function(name, args) {
			return false;
		},
		onClickCell : function(field, value, row, $element) {
			return false;
		},
		onDblClickCell : function(field, value, row, $element) {
			return false;
		},
		onClickRow : function(item, $element) {
			return false;
		},
		onDblClickRow : function(item, $element) {
			return false;
		},
		onSort : function(name, order) {
			return false;
		},
		onCheck : function(row) {
			return false;
		},
		onUncheck : function(row) {
			return false;
		},
		onCheckAll : function(rows) {
			return false;
		},
		onUncheckAll : function(rows) {
			return false;
		},
		onCheckSome : function(rows) {
			return false;
		},
		onUncheckSome : function(rows) {
			return false;
		},
		onLoadSuccess : function(data) {
			return false;
		},
		onLoadError : function(status) {
			return false;
		},
		onColumnSwitch : function(field, checked) {
			return false;
		},
		onPageChange : function(number, size) {
			return false;
		},
		onSearch : function(text) {
			return false;
		},
		onToggle : function(cardView) {
			return false;
		},
		onPreBody : function(data) {
			return false;
		},
		onPostBody : function() {
			return false;
		},
		onPostHeader : function() {
			return false;
		},
		onExpandRow : function(index, row, $detail) {
			return false;
		},
		onCollapseRow : function(index, row) {
			return false;
		},
		onRefreshOptions : function(options) {
			return false;
		},
		onResetView : function() {
			return false;
		},		
		formatLoadingMessage : function() {
			return 'Loading, please wait...';
		},
		formatRecordsPerPage : function(pageNumber) {
			return sprintf('%s records per page', pageNumber);
		},
		formatShowingRows : function(pageFrom, pageTo, totalRows) {
			return sprintf('Showing %s to %s of %s rows', pageFrom, pageTo, totalRows);
		},
		formatDetailPagination : function(totalRows) {
			return sprintf('Showing %s rows', totalRows);
		},
		formatSearch : function() {
			return 'Search';
		},
		formatNoMatches : function() {
			return 'No matching records found';
		},
		formatPaginationSwitch : function() {
			return 'Hide/Show pagination';
		},
		formatRefresh : function() {
			return 'Refresh';
		},
		formatToggle : function() {
			return 'Toggle';
		},
		formatColumns : function() {
			return 'Columns';
		},
		formatAllRows : function() {
			return 'All';
		}
	};

	/**
	 * columns中的每个字段的默认属性
	 */
	mmuiTable.COLUMN_DEFAULTS = {
		radio : false,
		checkbox : false,
		checkboxEnabled : true,
		field : undefined,
		title : undefined,
		titleTooltip : undefined,
		'class' : undefined,
		align : undefined, // left, right, center
		halign : undefined, // left, right, center
		falign : undefined, // left, right, center
		valign : undefined, // top, middle, bottom
		width : undefined,
		sortable : false,
		order : 'desc', // asc, desc
		visible : true,
		switchable : true,
		clickToSelect : true,
		formatter : undefined,
		footerFormatter : undefined,
		events : undefined,
		sorter : undefined,
		sortName : undefined,
		cellStyle : undefined,
		searchable : true,
		searchFormatter : true,
		cardVisible : true
	};

	mmuiTable.EVENTS = {
		'all.bs.table' : 'onAll',
		'click-cell.bs.table' : 'onClickCell',
		'dbl-click-cell.bs.table' : 'onDblClickCell',
		'click-row.bs.table' : 'onClickRow',
		'dbl-click-row.bs.table' : 'onDblClickRow',
		'sort.bs.table' : 'onSort',
		'check.bs.table' : 'onCheck',
		'uncheck.bs.table' : 'onUncheck',
		'check-all.bs.table' : 'onCheckAll',
		'uncheck-all.bs.table' : 'onUncheckAll',
		'check-some.bs.table' : 'onCheckSome',
		'uncheck-some.bs.table' : 'onUncheckSome',
		'load-success.bs.table' : 'onLoadSuccess',
		'load-error.bs.table' : 'onLoadError',
		'column-switch.bs.table' : 'onColumnSwitch',
		'page-change.bs.table' : 'onPageChange',
		'search.bs.table' : 'onSearch',
		'toggle.bs.table' : 'onToggle',
		'pre-body.bs.table' : 'onPreBody',
		'post-body.bs.table' : 'onPostBody',
		'post-header.bs.table' : 'onPostHeader',
		'expand-row.bs.table' : 'onExpandRow',
		'collapse-row.bs.table' : 'onCollapseRow',
		'refresh-options.bs.table' : 'onRefreshOptions',
		'reset-view.bs.table' : 'onResetView'
	};

	var allowedMethods = [ 'getOptions', 'getSelections', 'getAllSelections', 'getData', 'load', 'append', 'prepend', 'remove', 'removeAll',
			'insertRow', 'updateRow', 'updateCell', 'updateByUniqueId', 'removeByUniqueId', 'getRowByUniqueId', 'showRow', 'hideRow',
			'getRowsHidden', 'mergeCells', 'checkAll', 'uncheckAll', 'check', 'uncheck', 'checkBy', 'uncheckBy', 'refresh', 'resetView',
			'resetWidth', 'destroy', 'showLoading', 'hideLoading', 'showColumn', 'hideColumn', 'getHiddenColumns', 'filterBy', 'scrollTo',
			'getScrollPosition', 'selectPage', 'prevPage', 'nextPage', 'togglePagination', 'toggleView', 'refreshOptions', 'resetSearch',
			'expandRow', 'collapseRow', 'expandAllRows', 'collapseAllRows', 'updateFormatText' ];

	$.fn.mmuiTable = function(option) {
		var value, args = Array.prototype.slice.call(arguments, 1);

		this.each(function() {
			var $this = $(this), data = $this.data('bootstrap.table'), options = $.extend({}, mmuiTable.DEFAULTS, $this.data(),
					typeof option === 'object' && option);

			if (typeof option === 'string') {
				if ($.inArray(option, allowedMethods) < 0) {
					throw new Error("Unknown method: " + option);
				}

				if (!data) {
					return;
				}

				value = data[option].apply(data, args);

				if (option === 'destroy') {
					$this.removeData('bootstrap.table');
				}
			}

			if (!data) {
				$this.data('bootstrap.table', (data = new mmuiTable(this, options)));
			}
		});

		return typeof value === 'undefined' ? this : value;
	};

	$.fn.mmuiTable.Constructor = mmuiTable;
	
	$.extend(mmuiTable.DEFAULTS,{
		formatLoadingMessage : function() {
			return '正在努力地加载数据中，请稍候……';
		},
		formatRecordsPerPage : function(pageNumber) {
			return '每页显示 ' + pageNumber + ' 条记录';
		},
		formatShowingRows : function(pageFrom, pageTo, totalRows) {
			return '显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
		},
		formatSearch : function() {
			return '搜索';
		},
		formatNoMatches : function() {
			return '没有找到匹配的记录';
		},
		formatPaginationSwitch : function() {
			return '隐藏/显示分页';
		},
		formatRefresh : function() {
			return '刷新';
		},
		formatToggle : function() {
			return '切换';
		},
		formatColumns : function() {
			return '列';
		}
	});

	var mmList = function(paras) {
		var listBody = $("#list_body");
		var submitBtn = $("#submit_btn");
		var toolform = $("#toolform");
		this.option={'listBody':listBody,'submitBtn':submitBtn,'toolform':toolform};
		paras = paras || {};
		$.extend(this.option,paras);
		this.init();
		this.submitBtn();
	}
	mmList.prototype.init=function(){
		var that=this;
		var getHeight=function(){
			var th = that.option.toolform.height();
			var height = $(window.parent).height();
			height = height - th - 180;
			if (height < 200) {
				height = 200;
			}
			return height;
		}	
		
		var queryParams = function(params) {
			var args = mmUtl.ajax.getArgs(that.option.toolform);
			args.pageSize = params.pageSize;
			args.pageStart = params.pageStart;
			$.extend({}, args, that.option.initParams);
			return args;
		}
		
		var tablePara = $.extend({
			clickToSelect : true,
			height : getHeight(),
			queryParams : queryParams
		}, that.option);
		
		that.option.listBody.mmuiTable(tablePara);
	};
	mmList.prototype.submitBtn = function() {
		var that=this;
		this.option.submitBtn.click(function() {
			var args = mmUtl.ajax.getArgs(that.option.toolform);
			//alert(JSON.stringify(args));
			that.option.listBody.mmuiTable('refresh', {
				query : args
			});
		});
	};
	
   function Plugin(element, options) {
       this.element = $(element);
       this._defaults = {toggle: true,doubleTapToGo: false};
       this.settings = $.extend({}, this._defaults, options);
       this._name = 'metisMenu';
       this.init();
   }

   Plugin.prototype = {
       init: function() {

           var $this = this.element,
               $toggle = this.settings.toggle,
               obj = this;

           if (this.isIE() <= 9) {
               $this.find("li.active").has("ul").children("ul").collapse("show");
               $this.find("li").not(".active").has("ul").children("ul").collapse("hide");
           } else {
               $this.find("li.active").has("ul").children("ul").addClass("collapse in");
               $this.find("li").not(".active").has("ul").children("ul").addClass("collapse");
           }

           //add the "doubleTapToGo" class to active items if needed
           if (obj.settings.doubleTapToGo) {
               $this.find("li.active").has("ul").children("a").addClass("doubleTapToGo");
           }

           $this.find("li").has("ul").children("a").on("click" + ".metisMenu", function(e) {
               e.preventDefault();

               //Do we need to enable the double tap
               if (obj.settings.doubleTapToGo) {

                   //if we hit a second time on the link and the href is valid, navigate to that url
                   if (obj.doubleTapToGo($(this)) && $(this).attr("href") !== "#" && $(this).attr("href") !== "") {
                       e.stopPropagation();
                       document.location = $(this).attr("href");
                       return;
                   }
               }

               $(this).parent("li").toggleClass("active").children("ul").collapse("toggle");

               if ($toggle) {
                   $(this).parent("li").siblings().removeClass("active").children("ul.in").collapse("hide");
               }

           });
       },

       isIE: function() { //https://gist.github.com/padolsey/527683
           var undef,
               v = 3,
               div = document.createElement("div"),
               all = div.getElementsByTagName("i");

           while (
               div.innerHTML = "<!--[if gt IE " + (++v) + "]><i></i><![endif]-->",
               all[0]
           ) {
               return v > 4 ? v : undef;
           }
       },
       doubleTapToGo: function(elem) {
           var $this = this.element;

           if (elem.hasClass("doubleTapToGo")) {
               elem.removeClass("doubleTapToGo");
               return true;
           }

           if (elem.parent().children("ul").length) {
               $this.find(".doubleTapToGo").removeClass("doubleTapToGo");
               elem.addClass("doubleTapToGo");
               return false;
           }
       },

       remove: function() {
           this.element.off(".metisMenu");
           this.element.removeData('metisMenu');
       }

   };
   
   /*********************************main******************************************/
   
		function initView(){
			var wh=$(window).height();
			$("#page-wrapper").height(wh-96);
			$("#page_body").height(wh-140);
			
			$(window).resize(function(){
				initView();
			});
		}
		
		function addNavEvent(navuid){
			var nav=$("#nav_box").find("[navuid='"+navuid+"']")
			nav.click(function(){
				if($(this).hasClass("active")){
					return false;
				}
				var pageuid=$(this).attr("navuid");
		    	$("#page_body>iframe").hide();
		    	$("#nav_box .active").removeClass("active");
				
		    	$("#page_body").find("[iframeuid='"+pageuid+"']").show();
		    	$(this).addClass("active");
		    	return false;
			});
			nav.find("i").click(function(){
				var aele=$(this).parent();
				var uid=aele.attr("navuid");
				
				if(aele.hasClass("active")){
					aele.prev().trigger("click");
				}
				
				$("#page_body").find("[iframeuid='"+uid+"']").remove();
				aele.remove();
				return false;
			});
		}
		
	    function clickLink(href,pageuid,pageName,flush){
	    	$("#page_body>iframe").hide();
	    	$("#nav_box .active").removeClass("active");
	    	var iframe=$("#page_body").find("[iframeuid='"+pageuid+"']");
	    	if(!flush&&iframe.length>0){
	    		iframe.show();
	    		iframe[0].contentWindow.location.reload();
	    		$("#nav_box").find("[navuid='"+pageuid+"']").addClass("active");
	    	}else{
	    		$("#page_body").find("[iframeuid='"+pageuid+"']").remove();
	    		$("#nav_box").find("[navuid='"+pageuid+"']").remove();
	    		
	    		$("#page_body").append("<iframe iframeuid=\""+pageuid+"\" style=\"width:100%;height:100%;border: 0;\" src=\""+href+"\" ></iframe>");
	    		$("#nav_box").append("<a href=\"javascript:;\" class=\"J_menuTab active\" navuid=\""+pageuid+"\">"+pageName+"&nbsp;<i class=\"fa fa-times-circle\"></i></a>");
	    		addNavEvent(pageuid);
	    	}
	    }
		
	    function initMain(){
	    	initView();
	    	addNavEvent("adminMain");
	    	
	        $("#left_body").find("[pageuid]").click(function(){
	        	var pageuid=$(this).attr("pageuid");
	        	var pageName=$(this).text();
	        	var href=$(this).attr("href");

	        	clickLink(href,pageuid,pageName,false);
	        	return false;
	        });
	    }

	    //parent.window.mmgrid("${ctx}/admin/forecast/auditForecastList?forecastNo="+forecastNo,"adminForecastAuditListByNo","预报明细",true);
	  
	    window.initMain=initMain;
		window.mmgrid=clickLink;
		

   $.fn.metisMenu = function(options) {
       this.each(function () {
           var el = $(this);
           if (el.data('metisMenu')) {
               el.data('metisMenu').remove();
           }
           el.data('metisMenu', new Plugin(this, options));
       });
       return this;
   };

	window.mmUtl = mmUtl;
	window.mmui = mmui;
	window.mmList = function(option){return new mmList(option)};

	$(function() {
		mmUtl.init();
		//mmui.use('skin/mmui.css');
		$('[data-toggle="table"]').mmuiTable();
	});
	
	$.fn.mmgrid = function() {
		this.click(function(){
        	var pageuid=$(this).attr("pageuid");
        	var pageName=$(this).attr("pagename");
        	if(!pageName){
        		pageName=$(this).text();
        	}
        	var href=$(this).attr("href");
        	
        	parent.mmgrid(href,pageuid,pageName,true);
        	return false;
	    });
	};
	
	/** ******************************************************************************************************************* */
	
	// 默认配置
	Class.prototype.config = {
		type : 0,
		shade : 0.3,
		fix : true,
		move : doms[1],
		title : '&#x4FE1;&#x606F;',
		offset : 'auto',
		area : 'auto',
		closeBtn : 1,
		time : 0, // 0表示不自动关闭
		zIndex : 19891014,
		maxWidth : 360,
		shift : 0,
		icon : -1,
		scrollbar : true, // 是否允许浏览器滚动条
		tips : 2
	};

	// 容器
	Class.prototype.vessel = function(conType, callback) {
		var that = this, times = that.index, config = that.config;
		var zIndex = config.zIndex + times, titype = typeof config.title === 'object';
		var ismax = config.maxmin && (config.type === 1 || config.type === 2);
		var titleHTML = (config.title ? '<div class="mmui-layer-title" style="' + (titype ? config.title[1] : '') + '">'
				+ (titype ? config.title[0] : config.title) + '</div>' : '');

		config.zIndex = zIndex;
		callback(
				[
						// 遮罩
						config.shade ? ('<div class="mmui-layer-shade" id="mmui-layer-shade'
								+ times
								+ '" times="'
								+ times
								+ '" style="'
								+ ('z-index:' + (zIndex - 1) + '; background-color:' + (config.shade[1] || '#000') + '; opacity:'
										+ (config.shade[0] || config.shade) + '; filter:alpha(opacity='
										+ (config.shade[0] * 100 || config.shade * 100) + ');') + '"></div>') : '',

						// 主体
						'<div class="'
								+ doms[0]
								+ ' '
								+ (doms.anim[config.shift] || '')
								+ (' mmui-layer-' + ready.type[config.type])
								+ (((config.type == 0 || config.type == 2) && !config.shade) ? ' mmui-layer-border' : '')
								+ ' '
								+ (config.skin || '')
								+ '" id="'
								+ doms[0]
								+ times
								+ '" type="'
								+ ready.type[config.type]
								+ '" times="'
								+ times
								+ '" showtime="'
								+ config.time
								+ '" conType="'
								+ (conType ? 'object' : 'string')
								+ '" style="z-index: '
								+ zIndex
								+ '; width:'
								+ config.area[0]
								+ ';height:'
								+ config.area[1]
								+ (config.fix ? '' : ';position:absolute;')
								+ '">'
								+ (conType && config.type != 2 ? '' : titleHTML)
								+ '<div class="mmui-layer-content'
								+ ((config.type == 0 && config.icon !== -1) ? ' mmui-layer-padding' : '')
								+ (config.type == 3 ? ' mmui-layer-loading' + config.icon : '')
								+ '">'
								+ (config.type == 0 && config.icon !== -1 ? '<i class="mmui-layer-ico mmui-layer-ico' + config.icon + '"></i>' : '')
								+ (config.type == 1 && conType ? '' : (config.content || ''))
								+ '</div>'
								+ '<span class="mmui-layer-setwin">'
								+ function() {
									var closebtn = ismax ? '<a class="mmui-layer-min" href="javascript:;"><cite></cite></a><a class="mmui-layer-ico mmui-layer-max" href="javascript:;"></a>'
											: '';
									config.closeBtn
											&& (closebtn += '<a class="mmui-layer-ico ' + doms[7] + ' ' + doms[7]
													+ (config.title ? config.closeBtn : (config.type == 4 ? '1' : '2'))
													+ '" href="javascript:;"></a>');
									return closebtn;
								}() + '</span>' + (config.btn ? function() {
									var button = '';
									typeof config.btn === 'string' && (config.btn = [ config.btn ]);
									for (var i = 0, len = config.btn.length; i < len; i++) {
										button += '<a class="' + doms[6] + '' + i + '">' + config.btn[i] + '</a>'
									}
									return '<div class="' + doms[6] + '">' + button + '</div>'
								}() : '') + '</div>' ], titleHTML);
		return that;
	};

	// 创建骨架
	Class.prototype.creat = function() {
		var that = this, config = that.config, times = that.index, nodeIndex;
		var content = config.content, conType = typeof content === 'object';

		if (typeof config.area === 'string') {
			config.area = config.area === 'auto' ? [ '', '' ] : [ config.area, '' ];
		}

		switch (config.type) {
		case 0:
			config.btn = ('btn' in config) ? config.btn : ready.btn[0];
			mmui.closeAll('dialog');
			break;
		case 2:
			var content = config.content = conType ? config.content : [ config.content || 'http://www.coe.com.hk', 'auto' ];
			config.content = '<iframe scrolling="' + (config.content[1] || 'auto') + '" allowtransparency="true" id="' + doms[4] + '' + times
					+ '" name="' + doms[4] + '' + times + '" onload="this.className=\'\';" class="mmui-layer-load" frameborder="0" src="'
					+ config.content[0] + '"></iframe>';
			break;
		case 3:
			config.title = false;
			config.closeBtn = false;
			config.icon === -1 && (config.icon === 0);
			mmui.closeAll('loading');
			break;
		case 4:
			conType || (config.content = [ config.content, 'body' ]);
			config.follow = config.content[1];
			config.content = config.content[0] + '<i class="mmui-layer-TipsG"></i>';
			config.title = false;
			config.shade = false;
			config.fix = false;
			config.tips = typeof config.tips === 'object' ? config.tips : [ config.tips, true ];
			config.tipsMore || mmui.closeAll('tips');
			break;
		}

		// 建立容器
		that.vessel(conType, function(html, titleHTML) {
			$('body').append(html[0]);
			conType ? function() {
				(config.type == 2 || config.type == 4) ? function() {
					$('body').append(html[1]);
				}() : function() {
					if (!content.parents('.' + doms[0])[0]) {
						content.show().addClass('mmui-layer-wrap').wrap(html[1]);
						$('#' + doms[0] + times).find('.' + doms[5]).before(titleHTML);
					}
				}();
			}() : $('body').append(html[1]);
			that.mmuio = $('#' + doms[0] + times);
			config.scrollbar || mmUtl.html.css('overflow', 'hidden').attr('layer-full', times);
		}).auto(times);

		config.type == 2 && mmui.ie6 && that.mmuio.find('iframe').attr('src', content[0]);
		$(document).off('keydown', ready.enter).on('keydown', ready.enter);
		that.mmuio.on('keydown', function(e) {
			$(document).off('keydown', ready.enter);
		});

		// 坐标自适应浏览器窗口尺寸
		config.type == 4 ? that.tips() : that.offset();
		if (config.fix) {
			win.on('resize', function() {
				that.offset();
				(/^\d+%$/.test(config.area[0]) || /^\d+%$/.test(config.area[1])) && that.auto(times);
				config.type == 4 && that.tips();
			});
		}

		config.time <= 0 || setTimeout(function() {
			mmui.close(that.index)
		}, config.time);
		that.move().callback();
	};

	// 自适应
	Class.prototype.auto = function(index) {
		var that = this, config = that.config, mmuio = $('#' + doms[0] + index);
		if (config.area[0] === '' && config.maxWidth > 0) {
			// 为了修复IE7下一个让人难以理解的bug
			if (/MSIE 7/.test(navigator.userAgent) && config.btn) {
				mmuio.width(mmuio.innerWidth());
			}
			mmuio.outerWidth() > config.maxWidth && mmuio.width(config.maxWidth);
		}
		var area = [ mmuio.innerWidth(), mmuio.innerHeight() ];
		var titHeight = mmuio.find(doms[1]).outerHeight() || 0;
		var btnHeight = mmuio.find('.' + doms[6]).outerHeight() || 0;
		function setHeight(elem) {
			elem = mmuio.find(elem);
			elem.height(area[1] - titHeight - btnHeight - 2 * (parseFloat(elem.css('padding')) | 0));
		}
		switch (config.type) {
		case 2:
			setHeight('iframe');
			break;
		default:
			if (config.area[1] === '') {
				if (config.fix && area[1] >= win.height()) {
					area[1] = win.height();
					setHeight('.' + doms[5]);
				}
			} else {
				setHeight('.' + doms[5]);
			}
			break;
		}
		return that;
	};

	// 计算坐标
	Class.prototype.offset = function() {
		var that = this, config = that.config, mmuio = that.mmuio;
		var area = [ mmuio.outerWidth(), mmuio.outerHeight() ];
		var type = typeof config.offset === 'object';
		that.offsetTop = (win.height() - area[1]) / 2;
		that.offsetLeft = (win.width() - area[0]) / 2;
		if (type) {
			that.offsetTop = config.offset[0];
			that.offsetLeft = config.offset[1] || that.offsetLeft;
		} else if (config.offset !== 'auto') {
			that.offsetTop = config.offset;
			if (config.offset === 'rb') { // 右下角
				that.offsetTop = win.height() - area[1];
				that.offsetLeft = win.width() - area[0];
			}
		}
		if (!config.fix) {
			that.offsetTop = /%$/.test(that.offsetTop) ? win.height() * parseFloat(that.offsetTop) / 100 : parseFloat(that.offsetTop);
			that.offsetLeft = /%$/.test(that.offsetLeft) ? win.width() * parseFloat(that.offsetLeft) / 100 : parseFloat(that.offsetLeft);
			that.offsetTop += win.scrollTop();
			that.offsetLeft += win.scrollLeft();
		}
		mmuio.css({
			top : that.offsetTop,
			left : that.offsetLeft
		});
	};

	//小提示
	Class.prototype.tips = function() {
		var that = this, config = that.config, mmuio = that.mmuio;
		var layArea = [ mmuio.outerWidth(), mmuio.outerHeight() ], follow = $(config.follow);
		if (!follow[0])
			follow = $('body');
		var goal = {
			width : follow.outerWidth(),
			height : follow.outerHeight(),
			top : follow.offset().top,
			left : follow.offset().left
		}, tipsG = mmuio.find('.mmui-layer-TipsG');

		var guide = config.tips[0];
		config.tips[1] || tipsG.remove();

		goal.autoLeft = function() {
			if (goal.left + layArea[0] - win.width() > 0) {
				goal.tipLeft = goal.left + goal.width - layArea[0];
				tipsG.css({
					right : 12,
					left : 'auto'
				});
			} else {
				goal.tipLeft = goal.left;
			}
			;
		};

		// 辨别tips的方位
		goal.where = [ function() { // 上
			goal.autoLeft();
			goal.tipTop = goal.top - layArea[1] - 10;
			tipsG.removeClass('mmui-layer-TipsB').addClass('mmui-layer-TipsT').css('border-right-color', config.tips[1]);
		}, function() { // 右
			goal.tipLeft = goal.left + goal.width + 10;
			goal.tipTop = goal.top;
			tipsG.removeClass('mmui-layer-TipsL').addClass('mmui-layer-TipsR').css('border-bottom-color', config.tips[1]);
		}, function() { // 下
			goal.autoLeft();
			goal.tipTop = goal.top + goal.height + 10;
			tipsG.removeClass('mmui-layer-TipsT').addClass('mmui-layer-TipsB').css('border-right-color', config.tips[1]);
		}, function() { // 左
			goal.tipLeft = goal.left - layArea[0] - 10;
			goal.tipTop = goal.top;
			tipsG.removeClass('mmui-layer-TipsR').addClass('mmui-layer-TipsL').css('border-bottom-color', config.tips[1]);
		} ];
		goal.where[guide - 1]();

		/* 8*2为小三角形占据的空间 */
		if (guide === 1) {
			goal.top - (win.scrollTop() + layArea[1] + 8 * 2) < 0 && goal.where[2]();
		} else if (guide === 2) {
			win.width() - (goal.left + goal.width + layArea[0] + 8 * 2) > 0 || goal.where[3]()
		} else if (guide === 3) {
			(goal.top - win.scrollTop() + goal.height + layArea[1] + 8 * 2) - win.height() > 0 && goal.where[0]();
		} else if (guide === 4) {
			layArea[0] + 8 * 2 - goal.left > 0 && goal.where[1]()
		}

		mmuio.find('.' + doms[5]).css({
			'background-color' : config.tips[1],
			'padding-right' : (config.closeBtn ? '30px' : '')
		});
		mmuio.css({
			left : goal.tipLeft,
			top : goal.tipTop
		});
	}

	// 拖拽层
	Class.prototype.move = function() {
		var that = this, config = that.config, conf = {
			setY : 0,
			moveLayer : function() {
				var mmuio = conf.mmuio, mgleft = parseInt(mmuio.css('margin-left'));
				var lefts = parseInt(conf.move.css('left'));
				mgleft === 0 || (lefts = lefts - mgleft);
				if (mmuio.css('position') !== 'fixed') {
					lefts = lefts - mmuio.parent().offset().left;
					conf.setY = 0;
				}
				mmuio.css({
					left : lefts,
					top : parseInt(conf.move.css('top')) - conf.setY
				});
			}
		};

		var movedom = that.mmuio.find(config.move);
		config.move && movedom.attr('move', 'ok');
		movedom.css({
			cursor : config.move ? 'move' : 'auto'
		});

		$(config.move).on(
				'mousedown',
				function(M) {
					M.preventDefault();
					if ($(this).attr('move') === 'ok') {
						conf.ismove = true;
						conf.mmuio = $(this).parents('.' + doms[0]);
						var xx = conf.mmuio.offset().left, yy = conf.mmuio.offset().top, ww = conf.mmuio.outerWidth() - 6, hh = conf.mmuio
								.outerHeight() - 6;
						if (!$('#mmui-layer-moves')[0]) {
							$('body').append(
									'<div id="mmui-layer-moves" class="mmui-layer-moves" style="left:' + xx + 'px; top:' + yy + 'px; width:' + ww
											+ 'px; height:' + hh + 'px; z-index:2147483584"></div>');
						}
						conf.move = $('#mmui-layer-moves');
						config.moveType && conf.move.css({
							visibility : 'hidden'
						});

						conf.moveX = M.pageX - conf.move.position().left;
						conf.moveY = M.pageY - conf.move.position().top;
						conf.mmuio.css('position') !== 'fixed' || (conf.setY = win.scrollTop());
					}
				});

		$(document).mousemove(function(M) {
			if (conf.ismove) {
				var offsetX = M.pageX - conf.moveX, offsetY = M.pageY - conf.moveY;
				M.preventDefault();

				// 控制元素不被拖出窗口外
				if (!config.moveOut) {
					conf.setY = win.scrollTop();
					var setRig = win.width() - conf.move.outerWidth(), setTop = conf.setY;
					offsetX < 0 && (offsetX = 0);
					offsetX > setRig && (offsetX = setRig);
					offsetY < setTop && (offsetY = setTop);
					offsetY > win.height() - conf.move.outerHeight() + conf.setY && (offsetY = win.height() - conf.move.outerHeight() + conf.setY);
				}

				conf.move.css({
					left : offsetX,
					top : offsetY
				});
				config.moveType && conf.moveLayer();

				offsetX = offsetY = setRig = setTop = null;
			}
		}).mouseup(function() {
			try {
				if (conf.ismove) {
					conf.moveLayer();
					conf.move.remove();
					config.moveEnd && config.moveEnd();
				}
				conf.ismove = false;
			} catch (e) {
				conf.ismove = false;
			}
		});
		return that;
	};
	
	//回掉函数
	Class.prototype.callback = function() {
		var that = this, mmuio = that.mmuio, config = that.config;
		that.openLayer();
		if (config.success) {
			if (config.type == 2) {
				mmuio.find('iframe').on('load', function() {
					config.success(mmuio, that.index);
				});
			} else {
				config.success(mmuio, that.index);
			}
		}
		mmui.ie6 && that.IE6(mmuio);

		// 按钮
		mmuio.find('.' + doms[6]).children('a').on('click', function() {
			var index = $(this).index();
			config['btn' + (index + 1)] && config['btn' + (index + 1)](that.index, mmuio);
			if (index === 0) {
				config.yes ? config.yes(that.index, mmuio) : mmui.close(that.index);
			} else if (index === 1) {
				cancel();
			} else {
				config['btn' + (index + 1)] || mmui.close(that.index);
			}
		});

		// 取消
		function cancel() {
			var close = config.cancel && config.cancel(that.index);
			close === false || mmui.close(that.index);
		}

		// 右上角关闭回调
		mmuio.find('.' + doms[7]).on('click', cancel);

		// 点遮罩关闭
		if (config.shadeClose) {
			$('#mmui-layer-shade' + that.index).on('click', function() {
				mmui.close(that.index);
			});
		}

		// 最小化
		mmuio.find('.mmui-layer-min').on('click', function() {
			mmui.min(that.index, config);
			config.min && config.min(mmuio);
		});

		// 全屏/还原
		mmuio.find('.mmui-layer-max').on('click', function() {
			if ($(this).hasClass('mmui-layer-maxmin')) {
				mmui.restore(that.index);
				config.restore && config.restore(mmuio);
			} else {
				mmui.full(that.index, config);
				config.full && config.full(mmuio);
			}
		});

		config.end && (ready.end[that.index] = config.end);
	};

	Class.prototype.IE6 = function(mmuio) {
		var that = this, _ieTop = mmuio.offset().top;

		// ie6的固定与相对定位
		function ie6Fix() {
			mmuio.css({
				top : _ieTop + (that.config.fix ? win.scrollTop() : 0)
			});
		}
		;
		ie6Fix();
		win.scroll(ie6Fix);

		// 隐藏select
		$('select').each(function(index, value) {
			var sthis = $(this);
			if (!sthis.parents('.' + doms[0])[0]) {
				sthis.css('display') === 'none' || sthis.attr({
					'layer' : '1'
				}).hide();
			}
			sthis = null;
		});
	};

	// 需依赖原型的对外方法
	Class.prototype.openLayer = function() {
		var that = this;

		// 置顶当前窗口
		mmui.zIndex = that.config.zIndex;
		mmui.setTop = function(mmuio) {
			var setZindex = function() {
				mmui.zIndex++;
				mmuio.css('z-index', mmui.zIndex + 1);
			};
			mmui.zIndex = parseInt(mmuio[0].style.zIndex);
			mmuio.on('mousedown', setZindex);
			return mmui.zIndex;
		};
	};
	
	// 获取子iframe的DOM
	mmui.getChildFrame = function(selector, index) {
		index = index || $('.' + doms[4]).attr('times');
		return $('#' + doms[0] + index).find('iframe').contents().find(selector);
	};

	// 得到当前iframe层的索引，子iframe时使用
	mmui.getFrameIndex = function(name) {
		return $('#' + name).parents('.' + doms[4]).attr('times');
	};

	// iframe层自适应宽高
	mmui.iframeAuto = function(index) {
		if (!index)
			return;
		var heg = mmui.getChildFrame('html', index).outerHeight();
		var mmuio = $('#' + doms[0] + index);
		var titHeight = mmuio.find(doms[1]).outerHeight() || 0;
		var btnHeight = mmuio.find('.' + doms[6]).outerHeight() || 0;
		mmuio.css({
			height : heg + titHeight + btnHeight
		});
		mmuio.find('iframe').css({
			height : heg
		});
	};

	// 重置iframe url
	mmui.iframeSrc = function(index, url) {
		$('#' + doms[0] + index).find('iframe').attr('src', url);
	};

	// 设定层的样式
	mmui.style = function(index, options) {
		var mmuio = $('#' + doms[0] + index), type = mmuio.attr('type');
		var titHeight = mmuio.find(doms[1]).outerHeight() || 0;
		var btnHeight = mmuio.find('.' + doms[6]).outerHeight() || 0;
		if (type === ready.type[1] || type === ready.type[2]) {
			mmuio.css(options);
			if (type === ready.type[2]) {
				mmuio.find('iframe').css({
					height : parseFloat(options.height) - titHeight - btnHeight
				});
			}
		}
	};

	// 最小化
	mmui.min = function(index, options) {
		var mmuio = $('#' + doms[0] + index);
		var titHeight = mmuio.find(doms[1]).outerHeight() || 0;
		ready.record(mmuio);
		mmui.style(index, {
			width : 180,
			height : titHeight,
			overflow : 'hidden'
		});
		mmuio.find('.mmui-layer-min').hide();
		mmuio.attr('type') === 'page' && mmuio.find(doms[4]).hide();
		ready.rescollbar(index);
	};

	// 还原
	mmui.restore = function(index) {
		var mmuio = $('#' + doms[0] + index), area = mmuio.attr('area').split(',');
		var type = mmuio.attr('type');
		mmui.style(index, {
			width : parseFloat(area[0]),
			height : parseFloat(area[1]),
			top : parseFloat(area[2]),
			left : parseFloat(area[3]),
			overflow : 'visible'
		});
		mmuio.find('.mmui-layer-max').removeClass('mmui-layer-maxmin');
		mmuio.find('.mmui-layer-min').show();
		mmuio.attr('type') === 'page' && mmuio.find(doms[4]).show();
		ready.rescollbar(index);
	};

	// 全屏
	mmui.full = function(index) {
		var mmuio = $('#' + doms[0] + index), timer;
		ready.record(mmuio);
		if (!mmUtl.html.attr('layer-full')) {
			mmUtl.html.css('overflow', 'hidden').attr('layer-full', index);
		}
		clearTimeout(timer);
		timer = setTimeout(function() {
			var isfix = mmuio.css('position') === 'fixed';
			mmui.style(index, {
				top : isfix ? 0 : win.scrollTop(),
				left : isfix ? 0 : win.scrollLeft(),
				width : win.width(),
				height : win.height()
			});
			mmuio.find('.mmui-layer-min').hide();
		}, 100);
	};

	// 改变title
	mmui.title = function(name, index) {
		var title = $('#' + doms[0] + (index || mmui.index)).find(doms[1]);
		title.html(name);
	};

	// 关闭layer总方法
	mmui.close = function(index) {
		var mmuio = $('#' + doms[0] + index), type = mmuio.attr('type');
		if (!mmuio[0])
			return;
		if (type === ready.type[1] && mmuio.attr('conType') === 'object') {
			mmuio.children(':not(.' + doms[5] + ')').remove();
			for (var i = 0; i < 2; i++) {
				mmuio.find('.mmui-layer-wrap').unwrap().hide();
			}
		} else {
			// 低版本IE 回收 iframe
			if (type === ready.type[2]) {
				try {
					var iframe = $('#' + doms[4] + index)[0];
					iframe.contentWindow.document.write('');
					iframe.contentWindow.close();
					mmuio.find('.' + doms[5])[0].removeChild(iframe);
				} catch (e) {
				}
			}
			mmuio[0].innerHTML = '';
			mmuio.remove();
		}
		$('#mmui-layer-moves, #mmui-layer-shade' + index).remove();
		mmui.ie6 && ready.reselect();
		ready.rescollbar(index);
		$(document).off('keydown', ready.enter);
		typeof ready.end[index] === 'function' && ready.end[index]();
		delete ready.end[index];
	};

	// 关闭所有层
	mmui.closeAll = function(type) {
		$.each($('.' + doms[0]), function() {
			var othis = $(this);
			var is = type ? (othis.attr('type') === type) : 1;
			is && mmui.close(othis.attr('times'));
			is = null;
		});
	};
	
	mmuiTable.prototype.init = function() {
		this.initContainer();
		this.initTable();
		this.initHeader();
		this.initData();
		this.initFooter();
		this.initToolbar();
		this.initPagination();
		this.initBody();
		this.initSearchText();
		this.initServer();
	};

	/**
	 * 初始化table容器 初始化容器中的各个对象元素到对象，后期使用
	 * 
	 * 最外层：bootstrap-table
	 * 包含：fixed-table-toolbar（头部按钮）和fixed-table-container（表格体）
	 * 
	 * fixed-table-container 设置了这个展示页的全部高度，包含表头，表体，表尾，翻页
	 * 包含：表头（fixed-table-header），表体（fixed-table-body），表尾（fixed-table-footer），翻页（fixed-table-pagination）
	 * 
	 */
	mmuiTable.prototype.initContainer = function() {
		this.$container = $([
				'<div class="bootstrap-table">',
				'<div class="fixed-table-toolbar"></div>',
				this.options.paginationVAlign === 'top' || this.options.paginationVAlign === 'both' ? '<div class="fixed-table-pagination" style="clear: both;"></div>'
						: '',
				'<div class="fixed-table-container">',
				'<div class="fixed-table-header"><table></table></div>',
				'<div class="fixed-table-body">',
				'<div class="fixed-table-loading">',
				this.options.formatLoadingMessage(),
				'</div>',
				'</div>',
				'<div class="fixed-table-footer"><table><tr></tr></table></div>',
				this.options.paginationVAlign === 'bottom' || this.options.paginationVAlign === 'both' ? '<div class="fixed-table-pagination"></div>'
						: '', '</div>', '</div>' ].join(''));

		this.$container.insertAfter(this.$el);
		this.$tableContainer = this.$container.find('.fixed-table-container');
		this.$tableHeader = this.$container.find('.fixed-table-header');
		this.$tableBody = this.$container.find('.fixed-table-body');
		this.$tableLoading = this.$container.find('.fixed-table-loading');
		this.$tableFooter = this.$container.find('.fixed-table-footer');
		this.$toolbar = this.$container.find('.fixed-table-toolbar');
		this.$pagination = this.$container.find('.fixed-table-pagination');

		this.$tableBody.append(this.$el);
		this.$container.after('<div class="clearfix"></div>');

		this.$el.addClass(this.options.classes);
		if (this.options.striped) {
			this.$el.addClass('table-striped');
		}
		if ($.inArray('table-no-bordered', this.options.classes.split(' ')) !== -1) {
			this.$tableContainer.addClass('table-no-bordered');
		}
	};

	/**
	 * 初始化表格内容
	 */
	mmuiTable.prototype.initTable = function() {
		var that = this, columns = [], data = [];

		this.$header = this.$el.find('>thead');
		if (!this.$header.length) {
			this.$header = $('<thead></thead>').appendTo(this.$el);
		}
		//合并页面和js传递的两种方式的值
		this.$header.find('tr').each(function() {
			var column = [];

			$(this).find('th').each(function() {
				column.push($.extend({}, {
					title : $(this).html(),
					'class' : $(this).attr('class'),
					titleTooltip : $(this).attr('title'),
					rowspan : $(this).attr('rowspan') ? +$(this).attr('rowspan') : undefined,
					colspan : $(this).attr('colspan') ? +$(this).attr('colspan') : undefined
				}, $(this).data()));
			});
			columns.push(column);
		});
		if (!$.isArray(this.options.columns[0])) {
			this.options.columns = [ this.options.columns ];
		}
		this.options.columns = $.extend(true, [], columns, this.options.columns);
		this.columns = [];

		//合并之后，开始处理所有字段
		mmUtl.table.setFieldIndex(this.options.columns);
		$.each(this.options.columns, function(i, columns) {
			$.each(columns, function(j, column) {
				column = $.extend({}, mmuiTable.COLUMN_DEFAULTS, column);

				if (typeof column.fieldIndex !== 'undefined') {
					that.columns[column.fieldIndex] = column;
				}
				
				that.options.columns[i][j] = column;
			});
		});

		if (this.options.data.length) {
			return;
		}

		this.$el.find('>tbody>tr').each(function() {
			var row = {};
			row._id = $(this).attr('id');
			row._class = $(this).attr('class');
			row._data = mmUtl.table.getRealDataAttr($(this).data());
			$(this).find('td').each(function(i) {
				var field = that.columns[i].field;
				row[field] = $(this).html();
				row['_' + field + '_id'] = $(this).attr('id');
				row['_' + field + '_class'] = $(this).attr('class');
				row['_' + field + '_rowspan'] = $(this).attr('rowspan');
				row['_' + field + '_title'] = $(this).attr('title');
				row['_' + field + '_data'] = mmUtl.table.getRealDataAttr($(this).data());
			});
			data.push(row);
		});
		this.options.data = data;
	};

	/**
	 * 初始化表格头
	 */
	mmuiTable.prototype.initHeader = function() {
		var that = this, visibleColumns = {}, html = [];

		this.header = {
			fields : [],
			styles : [],
			classes : [],
			formatters : [],
			events : [],
			sorters : [],
			sortNames : [],
			cellStyles : [],
			searchables : []
		};

		//循环处理columns传过来的数据，解析出内容
		$.each(this.options.columns, function(i, columns) {
			html.push('<tr class="info">');

			if (i == 0 && !that.options.cardView && that.options.detailView) {
				html.push(sprintf('<th class="detail" rowspan="%s"><div class="fht-cell"></div></th>', that.options.columns.length));
			}

			//columns中的每一个字段循环处理，解析出各个属性
			$.each(columns, function(j, column) {
				var text = '', halign = '', align = '', style = '', class_ = sprintf(' class="%s"', column['class']), order = that.options.sortOrder
						|| column.order, unitWidth = 'px', width = column.width;

				if (column.width !== undefined && (!that.options.cardView)) {
					if (typeof column.width === 'string') {
						if (column.width.indexOf('%') !== -1) {
							unitWidth = '%';
						}
					}
				}
				if (column.width && typeof column.width === 'string') {
					width = column.width.replace('%', '').replace('px', '');
				}

				halign = sprintf('text-align: %s; ', column.halign ? column.halign : column.align);
				align = sprintf('text-align: %s; ', column.align);
				style = sprintf('vertical-align: %s; ', column.valign);
				style += sprintf('width: %s; ', (column.checkbox || column.radio) && !width ? '36px' : (width ? width + unitWidth : undefined));
			
				if (typeof column.fieldIndex !== 'undefined') {
					that.header.fields[column.fieldIndex] = column.field;
					that.header.styles[column.fieldIndex] = align + style;
					that.header.classes[column.fieldIndex] = class_;
					that.header.formatters[column.fieldIndex] = column.formatter;
					that.header.events[column.fieldIndex] = column.events;
					that.header.sorters[column.fieldIndex] = column.sorter;
					that.header.sortNames[column.fieldIndex] = column.sortName;
					that.header.cellStyles[column.fieldIndex] = column.cellStyle;
					that.header.searchables[column.fieldIndex] = column.searchable;

					if (!column.visible) {
						return;
					}

					if (that.options.cardView && (!column.cardVisible)) {
						return;
					}

					visibleColumns[column.field] = column;
				}

				html.push('<th' + sprintf(' title="%s"', column.titleTooltip), column.checkbox || column.radio ? sprintf(' class="bs-checkbox %s"',
						column['class'] || '') : class_, sprintf(' style="%s"', halign + style), sprintf(' rowspan="%s"', column.rowspan), sprintf(
						' colspan="%s"', column.colspan), sprintf(' data-field="%s"', column.field), "tabindex='0'", '>');

				html.push(sprintf('<div class="th-inner %s">', that.options.sortable && column.sortable ? 'sortable both' : ''));

				text = column.title;

				if (column.checkbox) {
					if (!that.options.singleSelect && that.options.checkboxHeader) {
						text = '<input name="btSelectAll" type="checkbox" />';
					}
					that.header.stateField = column.field;
				}
				if (column.radio) {
					text = '';
					that.header.stateField = column.field;
					that.options.singleSelect = true;
				}

				html.push(text);
				html.push('</div>');
				html.push('<div class="fht-cell"></div>');
				html.push('</div>');
				html.push('</th>');
			});
			html.push('</tr>');
		});

		this.$header.html(html.join(''));
		this.$header.find('th[data-field]').each(function(i) {
			$(this).data(visibleColumns[$(this).data('field')]);
		});
		this.$container.off('click', '.th-inner').on('click', '.th-inner', function(event) {
			var target = $(this);
			if (target.closest('.bootstrap-table')[0] !== that.$container[0])
				return false;

			if (that.options.sortable && target.parent().data().sortable) {
				that.onSort(event);
			}
		});

		this.$header.children().children().off('keypress').on('keypress', function(event) {
			if (that.options.sortable && $(this).data().sortable) {
				var code = event.keyCode || event.which;
				if (code == 13) {
					that.onSort(event);
				}
			}
		});

		if (!this.options.showHeader || this.options.cardView) {
			this.$header.hide();
			this.$tableHeader.hide();
			this.$tableLoading.css('top', 0);
		} else {
			this.$header.show();
			this.$tableHeader.show();
			this.$tableLoading.css('top', this.$header.outerHeight() + 1);
			this.getCaret();
		}

		this.$selectAll = this.$header.find('[name="btSelectAll"]');
		this.$selectAll.off('click').on('click', function() {
			var checked = $(this).prop('checked');
			that[checked ? 'checkAll' : 'uncheckAll']();
			that.updateSelected();
		});
	};

	/**
	 * 初始化表格尾部
	 */
	mmuiTable.prototype.initFooter = function() {
		if (!this.options.showFooter || this.options.cardView) {
			this.$tableFooter.hide();
		} else {
			this.$tableFooter.show();
		}
	};

	/**
	 * 初始化数据
	 */
	mmuiTable.prototype.initData = function(data, type) {
		if (type === 'append') {
			this.data = this.data.concat(data);
		} else if (type === 'prepend') {
			this.data = [].concat(data).concat(this.data);
		} else {
			this.data = data || this.options.data;
		}

		if (type === 'append') {
			this.options.data = this.options.data.concat(data);
		} else if (type === 'prepend') {
			this.options.data = [].concat(data).concat(this.options.data);
		} else {
			this.options.data = this.data;
		}

		if (this.options.sidePagination === 'server') {
			return;
		}
		this.initSort();
	};

	mmuiTable.prototype.initSort = function() {
		var that = this, name = this.options.sortName, order = this.options.sortOrder === 'desc' ? -1 : 1, index = $.inArray(this.options.sortName,
				this.header.fields);

		if (index !== -1) {
			this.data.sort(function(a, b) {
				if (that.header.sortNames[index]) {
					name = that.header.sortNames[index];
				}
				var aa = mmUtl.table.getItemField(a, name, that.options.escape), bb = mmUtl.table.getItemField(b, name, that.options.escape), value = calculateObjectValue(
						that.header, that.header.sorters[index], [ aa, bb ]);

				if (value !== undefined) {
					return order * value;
				}

				if (aa === undefined || aa === null) {
					aa = '';
				}
				if (bb === undefined || bb === null) {
					bb = '';
				}

				if ($.isNumeric(aa) && $.isNumeric(bb)) {
					aa = parseFloat(aa);
					bb = parseFloat(bb);
					if (aa < bb) {
						return order * -1;
					}
					return order;
				}

				if (aa === bb) {
					return 0;
				}

				if (typeof aa !== 'string') {
					aa = aa.toString();
				}

				if (aa.localeCompare(bb) === -1) {
					return order * -1;
				}

				return order;
			});
		}
	};

	mmuiTable.prototype.onSort = function(event) {
		var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(), $this_ = this.$header.find('th').eq(
				$this.index());

		this.$header.add(this.$header_).find('span.order').remove();

		if (this.options.sortName === $this.data('field')) {
			this.options.sortOrder = this.options.sortOrder === 'asc' ? 'desc' : 'asc';
		} else {
			this.options.sortName = $this.data('field');
			this.options.sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
		}
		this.trigger('sort', this.options.sortName, this.options.sortOrder);

		$this.add($this_).data('order', this.options.sortOrder);

		this.getCaret();

		if (this.options.sidePagination === 'server') {
			this.initServer(this.options.silentSort);
			return;
		}

		this.initSort();
		this.initBody();
	};

	mmuiTable.prototype.initToolbar = function() {
		var that = this, html = [], timeoutId = 0, $keepOpen, $search, switchableCount = 0;

		if (this.$toolbar.find('.bars').children().length) {
			$('body').append($(this.options.toolbar));
		}
		this.$toolbar.html('');

		if (typeof this.options.toolbar === 'string' || typeof this.options.toolbar === 'object') {
			$(sprintf('<div class="bars pull-%s"></div>', this.options.toolbarAlign)).appendTo(this.$toolbar).append($(this.options.toolbar));
		}

		html = [ sprintf('<div class="columns columns-%s btn-group pull-%s">', this.options.buttonsAlign, this.options.buttonsAlign) ];

		if (typeof this.options.icons === 'string') {
			this.options.icons = calculateObjectValue(null, this.options.icons);
		}

		if (this.options.showPaginationSwitch) {
			html.push(sprintf('<button class="btn btn-default" type="button" name="paginationSwitch" title="%s">', this.options
					.formatPaginationSwitch()), sprintf('<i class="%s %s"></i>', this.options.iconsPrefix, this.options.icons.paginationSwitchDown),
					'</button>');
		}

		if (this.options.showRefresh) {
			html.push(sprintf('<button class="btn btn-default' + sprintf(' btn-%s', this.options.iconSize)
					+ '" type="button" name="refresh" title="%s">', this.options.formatRefresh()), sprintf('<i class="%s %s"></i>',
					this.options.iconsPrefix, this.options.icons.refresh), '</button>');
		}

		if (this.options.showToggle) {
			html.push(sprintf('<button class="btn btn-default' + sprintf(' btn-%s', this.options.iconSize)
					+ '" type="button" name="toggle" title="%s">', this.options.formatToggle()), sprintf('<i class="%s %s"></i>',
					this.options.iconsPrefix, this.options.icons.toggle), '</button>');
		}

		if (this.options.showColumns) {
			html.push(sprintf('<div class="keep-open btn-group" title="%s">', this.options.formatColumns()),
					'<button type="button" class="btn btn-default' + sprintf(' btn-%s', this.options.iconSize)
							+ ' dropdown-toggle" data-toggle="dropdown">', sprintf('<i class="%s %s"></i>', this.options.iconsPrefix,
							this.options.icons.columns), ' <span class="caret"></span>', '</button>', '<ul class="dropdown-menu" role="menu">');

			$.each(this.columns, function(i, column) {
				if (column.radio || column.checkbox) {
					return;
				}

				if (that.options.cardView && (!column.cardVisible)) {
					return;
				}

				var checked = column.visible ? ' checked="checked"' : '';

				if (column.switchable) {
					html.push(sprintf('<li>' + '<label><input type="checkbox" data-field="%s" value="%s"%s> %s</label>' + '</li>', column.field, i,
							checked, column.title));
					switchableCount++;
				}
			});
			html.push('</ul>', '</div>');
		}

		html.push('</div>');

		if (this.showToolbar || html.length > 2) {
			this.$toolbar.append(html.join(''));
		}

		if (this.options.showPaginationSwitch) {
			this.$toolbar.find('button[name="paginationSwitch"]').off('click').on('click', $.proxy(this.togglePagination, this));
		}

		if (this.options.showRefresh) {
			this.$toolbar.find('button[name="refresh"]').off('click').on('click', $.proxy(this.refresh, this));
		}

		if (this.options.showToggle) {
			this.$toolbar.find('button[name="toggle"]').off('click').on('click', function() {
				that.toggleView();
			});
		}

		if (this.options.showColumns) {
			$keepOpen = this.$toolbar.find('.keep-open');

			if (switchableCount <= this.options.minimumCountColumns) {
				$keepOpen.find('input').prop('disabled', true);
			}

			$keepOpen.find('li').off('click').on('click', function(event) {
				event.stopImmediatePropagation();
			});
			$keepOpen.find('input').off('click').on('click', function() {
				var $this = $(this);

				that.toggleColumn(mmUtl.table.getFieldIndex(that.columns, $(this).data('field')), $this.prop('checked'), false);
				that.trigger('column-switch', $(this).data('field'), $this.prop('checked'));
			});
		}

		if (this.options.search) {
			html = [];
			html.push('<div class="pull-' + this.options.searchAlign + ' search">', sprintf('<input class="form-control'
					+ sprintf(' input-%s', this.options.iconSize) + '" type="text" placeholder="%s">', this.options.formatSearch()), '</div>');

			this.$toolbar.append(html.join(''));
			$search = this.$toolbar.find('.search input');
			$search.off('keyup drop').on('keyup drop', function(event) {
				if (that.options.searchOnEnterKey) {
					if (event.keyCode !== 13) {
						return;
					}
				}

				clearTimeout(timeoutId);
				timeoutId = setTimeout(function() {
					that.onSearch(event);
				}, that.options.searchTimeOut);
			});

			if (mmUtl.table.isIEBrowser()) {
				$search.off('mouseup').on('mouseup', function(event) {
					clearTimeout(timeoutId);
					timeoutId = setTimeout(function() {
						that.onSearch(event);
					}, that.options.searchTimeOut);
				});
			}
		}
	};

	mmuiTable.prototype.onSearch = function(event) {
		var text = $.trim($(event.currentTarget).val());

		if (this.options.trimOnSearch && $(event.currentTarget).val() !== text) {
			$(event.currentTarget).val(text);
		}

		if (text === this.searchText) {
			return;
		}
		this.searchText = text;
		this.options.searchText = text;

		this.options.pageNumber = 1;
		this.initSearch();
		this.updatePagination();
		this.trigger('search', text);
	};

	mmuiTable.prototype.initSearch = function() {
		var that = this;

		if (this.options.sidePagination !== 'server') {
			var s = this.searchText && this.searchText.toLowerCase();
			var f = $.isEmptyObject(this.filterColumns) ? null : this.filterColumns;

			this.data = f ? $.grep(this.options.data, function(item, i) {
				for ( var key in f) {
					if ($.isArray(f[key])) {
						if ($.inArray(item[key], f[key]) === -1) {
							return false;
						}
					} else if (item[key] !== f[key]) {
						return false;
					}
				}
				return true;
			}) : this.options.data;

			this.data = s ? $.grep(this.data, function(item, i) {
				for ( var key in item) {
					key = $.isNumeric(key) ? parseInt(key, 10) : key;
					var value = item[key], column = that.columns[mmUtl.table.getFieldIndex(that.columns, key)], j = $.inArray(key, that.header.fields);

					if (column && column.searchFormatter) {
						value = calculateObjectValue(column, that.header.formatters[j], [ value, item, i ], value);
					}

					var index = $.inArray(key, that.header.fields);
					if (index !== -1 && that.header.searchables[index] && (typeof value === 'string' || typeof value === 'number')) {
						if (that.options.strictSearch) {
							if ((value + '').toLowerCase() === s) {
								return true;
							}
						} else {
							if ((value + '').toLowerCase().indexOf(s) !== -1) {
								return true;
							}
						}
					}
				}
				return false;
			}) : this.data;
		}
	};

	/**
	 * 初始化翻页
	 */
	mmuiTable.prototype.initPagination = function() {
		if (!this.options.pagination) {
			this.$pagination.hide();
			return;
		} else {
			this.$pagination.show();
		}

		var that = this, html = [], $allSelected = false, i, from, to, $pageList, $first, $pre, $next, $last, $number, data = this.getData();

		if (this.options.sidePagination !== 'server') {
			this.options.totalRows = data.length;
		}

		this.totalPages = 0;
		if (this.options.totalRows) {
			if (this.options.pageSize === this.options.formatAllRows()) {
				this.options.pageSize = this.options.totalRows;
				$allSelected = true;
			} else if (this.options.pageSize === this.options.totalRows) {
				var pageLst = typeof this.options.pageList === 'string' ? this.options.pageList.replace('[', '').replace(']', '').replace(/ /g, '')
						.toLowerCase().split(',') : this.options.pageList;
				if ($.inArray(this.options.formatAllRows().toLowerCase(), pageLst) > -1) {
					$allSelected = true;
				}
			}

			this.totalPages = ~~((this.options.totalRows - 1) / this.options.pageSize) + 1;

			this.options.totalPages = this.totalPages;
		}
		if (this.totalPages > 0 && this.options.pageNumber > this.totalPages) {
			this.options.pageNumber = this.totalPages;
		}

		this.pageFrom = (this.options.pageNumber - 1) * this.options.pageSize + 1;
		this.pageTo = this.options.pageNumber * this.options.pageSize;
		if (this.pageTo > this.options.totalRows) {
			this.pageTo = this.options.totalRows;
		}

		html.push('<div class="pull-' + this.options.paginationDetailHAlign + ' pagination-detail">', '<span class="pagination-info">',
				this.options.onlyInfoPagination ? this.options.formatDetailPagination(this.options.totalRows) : this.options.formatShowingRows(
						this.pageFrom, this.pageTo, this.options.totalRows), '</span>');

		if (!this.options.onlyInfoPagination) {
			html.push('<span class="page-list">');

			var pageNumber = [
					sprintf('<span class="btn-group %s">',
							this.options.paginationVAlign === 'top' || this.options.paginationVAlign === 'both' ? 'dropdown' : 'dropup'),
					'<button type="button" class="btn btn-default ' + sprintf(' btn-%s', this.options.iconSize)
							+ ' dropdown-toggle" data-toggle="dropdown">', '<span class="page-size">',
					$allSelected ? this.options.formatAllRows() : this.options.pageSize, '</span>', ' <span class="caret"></span>', '</button>',
					'<ul class="dropdown-menu" role="menu">' ], pageList = this.options.pageList;

			if (typeof this.options.pageList === 'string') {
				var list = this.options.pageList.replace('[', '').replace(']', '').replace(/ /g, '').split(',');

				pageList = [];
				$.each(list, function(i, value) {
					pageList.push(value.toUpperCase() === that.options.formatAllRows().toUpperCase() ? that.options.formatAllRows() : +value);
				});
			}

			$.each(pageList, function(i, page) {
				if (!that.options.smartDisplay || i === 0 || pageList[i - 1] <= that.options.totalRows) {
					var active;
					if ($allSelected) {
						active = page === that.options.formatAllRows() ? ' class="active"' : '';
					} else {
						active = page === that.options.pageSize ? ' class="active"' : '';
					}
					pageNumber.push(sprintf('<li%s><a href="javascript:void(0)">%s</a></li>', active, page));
				}
			});
			pageNumber.push('</ul></span>');

			html.push(this.options.formatRecordsPerPage(pageNumber.join('')));
			html.push('</span>');

			html.push('</div>', '<div class="pull-' + this.options.paginationHAlign + ' pagination">', '<ul class="pagination'
					+ sprintf(' pagination-%s', this.options.iconSize) + '">', '<li class="page-pre"><a href="javascript:void(0)">'
					+ this.options.paginationPreText + '</a></li>');

			if (this.totalPages < 5) {
				from = 1;
				to = this.totalPages;
			} else {
				from = this.options.pageNumber - 2;
				to = from + 4;
				if (from < 1) {
					from = 1;
					to = 5;
				}
				if (to > this.totalPages) {
					to = this.totalPages;
					from = to - 4;
				}
			}

			if (this.totalPages >= 6) {
				if (this.options.pageNumber >= 3) {
					html.push('<li class="page-first' + (1 === this.options.pageNumber ? ' active' : '') + '">', '<a href="javascript:void(0)">', 1,
							'</a>', '</li>');

					from++;
				}

				if (this.options.pageNumber >= 4) {
					if (this.options.pageNumber == 4 || this.totalPages == 6 || this.totalPages == 7) {
						from--;
					} else {
						html.push('<li class="page-first-separator disabled">', '<a href="javascript:void(0)">...</a>', '</li>');
					}

					to--;
				}
			}

			if (this.totalPages >= 7) {
				if (this.options.pageNumber >= (this.totalPages - 2)) {
					from--;
				}
			}

			if (this.totalPages == 6) {
				if (this.options.pageNumber >= (this.totalPages - 2)) {
					to++;
				}
			} else if (this.totalPages >= 7) {
				if (this.totalPages == 7 || this.options.pageNumber >= (this.totalPages - 3)) {
					to++;
				}
			}

			for (i = from; i <= to; i++) {
				html.push('<li class="page-number' + (i === this.options.pageNumber ? ' active' : '') + '">', '<a href="javascript:void(0)">', i,
						'</a>', '</li>');
			}

			if (this.totalPages >= 8) {
				if (this.options.pageNumber <= (this.totalPages - 4)) {
					html.push('<li class="page-last-separator disabled">', '<a href="javascript:void(0)">...</a>', '</li>');
				}
			}

			if (this.totalPages >= 6) {
				if (this.options.pageNumber <= (this.totalPages - 3)) {
					html.push('<li class="page-last' + (this.totalPages === this.options.pageNumber ? ' active' : '') + '">',
							'<a href="javascript:void(0)">', this.totalPages, '</a>', '</li>');
				}
			}

			html.push('<li class="page-next"><a href="javascript:void(0)">' + this.options.paginationNextText + '</a></li>', '</ul>', '</div>');
		}
		this.$pagination.html(html.join(''));

		if (!this.options.onlyInfoPagination) {
			$pageList = this.$pagination.find('.page-list a');
			$first = this.$pagination.find('.page-first');
			$pre = this.$pagination.find('.page-pre');
			$next = this.$pagination.find('.page-next');
			$last = this.$pagination.find('.page-last');
			$number = this.$pagination.find('.page-number');

			if (this.options.smartDisplay) {
				if (this.totalPages <= 1) {
					this.$pagination.find('div.pagination').hide();
				}
				if (pageList.length < 2 || this.options.totalRows <= pageList[0]) {
					this.$pagination.find('span.page-list').hide();
				}

				this.$pagination[this.getData().length ? 'show' : 'hide']();
			}
			if ($allSelected) {
				this.options.pageSize = this.options.formatAllRows();
			}
			$pageList.off('click').on('click', $.proxy(this.onPageListChange, this));
			$first.off('click').on('click', $.proxy(this.onPageFirst, this));
			$pre.off('click').on('click', $.proxy(this.onPagePre, this));
			$next.off('click').on('click', $.proxy(this.onPageNext, this));
			$last.off('click').on('click', $.proxy(this.onPageLast, this));
			$number.off('click').on('click', $.proxy(this.onPageNumber, this));
		}
	};

	mmuiTable.prototype.updatePagination = function(event) {
		if (event && $(event.currentTarget).hasClass('disabled')) {
			return;
		}

		if (!this.options.maintainSelected) {
			this.resetRows();
		}

		this.initPagination();
		if (this.options.sidePagination === 'server') {
			this.initServer();
		} else {
			this.initBody();
		}

		this.trigger('page-change', this.options.pageNumber, this.options.pageSize);
	};

	mmuiTable.prototype.onPageListChange = function(event) {
		var $this = $(event.currentTarget);

		$this.parent().addClass('active').siblings().removeClass('active');
		this.options.pageSize = $this.text().toUpperCase() === this.options.formatAllRows().toUpperCase() ? this.options.formatAllRows() : +$this
				.text();
		this.$toolbar.find('.page-size').text(this.options.pageSize);

		this.updatePagination(event);
	};

	mmuiTable.prototype.onPageFirst = function(event) {
		this.options.pageNumber = 1;
		this.updatePagination(event);
	};

	mmuiTable.prototype.onPagePre = function(event) {
		if ((this.options.pageNumber - 1) == 0) {
			this.options.pageNumber = this.options.totalPages;
		} else {
			this.options.pageNumber--;
		}
		this.updatePagination(event);
	};

	mmuiTable.prototype.onPageNext = function(event) {
		if ((this.options.pageNumber + 1) > this.options.totalPages) {
			this.options.pageNumber = 1;
		} else {
			this.options.pageNumber++;
		}
		this.updatePagination(event);
	};

	mmuiTable.prototype.onPageLast = function(event) {
		this.options.pageNumber = this.totalPages;
		this.updatePagination(event);
	};

	mmuiTable.prototype.onPageNumber = function(event) {
		if (this.options.pageNumber === +$(event.currentTarget).text()) {
			return;
		}
		this.options.pageNumber = +$(event.currentTarget).text();
		this.updatePagination(event);
	};

	mmuiTable.prototype.initBody = function(fixedScroll) {
		var that = this, html = [], data = this.getData();

		this.trigger('pre-body', data);

		this.$body = this.$el.find('>tbody');
		if (!this.$body.length) {
			this.$body = $('<tbody></tbody>').appendTo(this.$el);
		}

		if (!this.options.pagination || this.options.sidePagination === 'server') {
			this.pageFrom = 1;
			this.pageTo = data.length;
		}

		for (var i = this.pageFrom - 1; i < this.pageTo; i++) {
			var key, item = data[i], style = {}, csses = [], data_ = '', attributes = {}, htmlAttributes = [];

			style = calculateObjectValue(this.options, this.options.rowStyle, [ item, i ], style);

			if (style && style.css) {
				for (key in style.css) {
					csses.push(key + ': ' + style.css[key]);
				}
			}

			attributes = calculateObjectValue(this.options, this.options.rowAttributes, [ item, i ], attributes);

			if (attributes) {
				for (key in attributes) {
					htmlAttributes.push(sprintf('%s="%s"', key, mmUtl.table.escapeHTML(attributes[key])));
				}
			}

			if (item._data && !$.isEmptyObject(item._data)) {
				$.each(item._data, function(k, v) {
					// ignore data-index
					if (k === 'index') {
						return;
					}
					data_ += sprintf(' data-%s="%s"', k, v);
				});
			}

			html.push('<tr', sprintf(' %s', htmlAttributes.join(' ')), sprintf(' id="%s"', $.isArray(item) ? undefined : item._id), sprintf(
					' class="%s"', style.classes || ($.isArray(item) ? undefined : item._class)), sprintf(' data-index="%s"', i), sprintf(
					' data-uniqueid="%s"', item[this.options.uniqueId]), sprintf('%s', data_), '>');

			if (this.options.cardView) {
				html.push(sprintf('<td colspan="%s">', this.header.fields.length));
			}

			if (!this.options.cardView && this.options.detailView) {
				html.push('<td>', '<a class="detail-icon" href="javascript:">', sprintf('<i class="%s %s"></i>', this.options.iconsPrefix,
						this.options.icons.detailOpen), '</a>', '</td>');
			}

			$
					.each(
							this.header.fields,
							function(j, field) {
								var text = '', value = mmUtl.table.getItemField(item, field, that.options.escape), type = '', cellStyle = {}, id_ = '', class_ = that.header.classes[j], data_ = '', rowspan_ = '', title_ = '', column = that.columns[mmUtl.table.getFieldIndex(
										that.columns, field)];

								if (!column.visible) {
									return;
								}

								style = sprintf('style="%s"', csses.concat(that.header.styles[j]).join('; '));

								value = calculateObjectValue(column, that.header.formatters[j], [ value, item, i ], value);

								// handle td's id and class
								if (item['_' + field + '_id']) {
									id_ = sprintf(' id="%s"', item['_' + field + '_id']);
								}
								if (item['_' + field + '_class']) {
									class_ = sprintf(' class="%s"', item['_' + field + '_class']);
								}
								if (item['_' + field + '_rowspan']) {
									rowspan_ = sprintf(' rowspan="%s"', item['_' + field + '_rowspan']);
								}
								if (item['_' + field + '_title']) {
									title_ = sprintf(' title="%s"', item['_' + field + '_title']);
								}
								cellStyle = calculateObjectValue(that.header, that.header.cellStyles[j], [ value, item, i ], cellStyle);
								if (cellStyle.classes) {
									class_ = sprintf(' class="%s"', cellStyle.classes);
								}
								if (cellStyle.css) {
									var csses_ = [];
									for ( var key in cellStyle.css) {
										csses_.push(key + ': ' + cellStyle.css[key]);
									}
									style = sprintf('style="%s"', csses_.concat(that.header.styles[j]).join('; '));
								}

								if (item['_' + field + '_data'] && !$.isEmptyObject(item['_' + field + '_data'])) {
									$.each(item['_' + field + '_data'], function(k, v) {
										// ignore data-index
										if (k === 'index') {
											return;
										}
										data_ += sprintf(' data-%s="%s"', k, v);
									});
								}

								if (column.checkbox || column.radio) {
									type = column.checkbox ? 'checkbox' : type;
									type = column.radio ? 'radio' : type;

									text = [
											sprintf(that.options.cardView ? '<div class="card-view %s">' : '<td class="bs-checkbox %s">',
													column['class'] || ''),
											'<input'
													+ sprintf(' data-index="%s"', i)
													+ sprintf(' name="%s"', that.options.selectItemName)
													+ sprintf(' type="%s"', type)
													+ sprintf(' value="%s"', item[that.options.idField])
													+ sprintf(' checked="%s"', value === true || (value && value.checked) ? 'checked' : undefined)
													+ sprintf(' disabled="%s"', !column.checkboxEnabled || (value && value.disabled) ? 'disabled'
															: undefined) + ' />',
											that.header.formatters[j] && typeof value === 'string' ? value : '',
											that.options.cardView ? '</div>' : '</td>' ].join('');

									item[that.header.stateField] = value === true || (value && value.checked);
								} else {
									value = typeof value === 'undefined' || value === null ? that.options.undefinedText : value;

									text = that.options.cardView ? [
											'<div class="card-view">',
											that.options.showHeader ? sprintf('<span class="title" %s>%s</span>', style, mmUtl.table.getPropertyFromOther(
													that.columns, 'field', 'title', field)) : '', sprintf('<span class="value">%s</span>', value),
											'</div>' ].join('') : [ sprintf('<td%s %s %s %s %s %s>', id_, class_, style, data_, rowspan_, title_),
											value, '</td>' ].join('');

									if (that.options.cardView && that.options.smartDisplay && value === '') {
										text = '<div class="card-view"></div>';
									}
								}

								html.push(text);
							});

			if (this.options.cardView) {
				html.push('</td>');
			}

			html.push('</tr>');
		}

		if (!html.length) {
			html.push('<tr class="no-records-found">', sprintf('<td colspan="%s">%s</td>', this.$header.find('th').length, this.options
					.formatNoMatches()), '</tr>');
		}

		this.$body.html(html.join(''));

		if (!fixedScroll) {
			this.scrollTo(0);
		}

		this.$body
				.find('> tr[data-index] > td')
				.off('click dblclick')
				.on(
						'click dblclick',
						function(e) {
							var $td = $(this), $tr = $td.parent(), item = that.data[$tr.data('index')], index = $td[0].cellIndex, field = that.header.fields[that.options.detailView
									&& !that.options.cardView ? index - 1 : index], column = that.columns[mmUtl.table.getFieldIndex(that.columns, field)], value = mmUtl.table.getItemField(
									item, field, that.options.escape);

							if ($td.find('.detail-icon').length) {
								return;
							}

							that.trigger(e.type === 'click' ? 'click-cell' : 'dbl-click-cell', field, value, item, $td);
							that.trigger(e.type === 'click' ? 'click-row' : 'dbl-click-row', item, $tr);

							if (e.type === 'click' && that.options.clickToSelect && column.clickToSelect) {
								var $selectItem = $tr.find(sprintf('[name="%s"]', that.options.selectItemName));
								if ($selectItem.length) {
									$selectItem[0].click();
								}
							}
						});

		this.$body.find('> tr[data-index] > td > .detail-icon').off('click').on('click', function() {
			var $this = $(this), $tr = $this.parent().parent(), index = $tr.data('index'), row = data[index];

			if ($tr.next().is('tr.detail-view')) {
				$this.find('i').attr('class', sprintf('%s %s', that.options.iconsPrefix, that.options.icons.detailOpen));
				$tr.next().remove();
				that.trigger('collapse-row', index, row);
			} else {
				$this.find('i').attr('class', sprintf('%s %s', that.options.iconsPrefix, that.options.icons.detailClose));
				$tr.after(sprintf('<tr class="detail-view"><td colspan="%s"></td></tr>', $tr.find('td').length));
				var $element = $tr.next().find('td');
				var content = calculateObjectValue(that.options, that.options.detailFormatter, [ index, row, $element ], '');
				if ($element.length === 1) {
					$element.append(content);
				}
				that.trigger('expand-row', index, row, $element);
			}
			that.resetView();
		});

		this.$selectItem = this.$body.find(sprintf('[name="%s"]', this.options.selectItemName));
		this.$selectItem.off('click').on('click', function(event) {
			event.stopImmediatePropagation();

			var $this = $(this), checked = $this.prop('checked'), row = that.data[$this.data('index')];

			if (that.options.maintainSelected && $(this).is(':radio')) {
				$.each(that.options.data, function(i, row) {
					row[that.header.stateField] = false;
				});
			}

			row[that.header.stateField] = checked;

			if (that.options.singleSelect) {
				that.$selectItem.not(this).each(function() {
					that.data[$(this).data('index')][that.header.stateField] = false;
				});
				that.$selectItem.filter(':checked').not(this).prop('checked', false);
			}

			that.updateSelected();
			that.trigger(checked ? 'check' : 'uncheck', row, $this);
		});

		$.each(this.header.events, function(i, events) {
			if (!events) {
				return;
			}
			if (typeof events === 'string') {
				events = calculateObjectValue(null, events);
			}

			var field = that.header.fields[i], fieldIndex = $.inArray(field, that.getVisibleFields());

			if (that.options.detailView && !that.options.cardView) {
				fieldIndex += 1;
			}

			for ( var key in events) {
				that.$body.find('>tr:not(.no-records-found)')
						.each(
								function() {
									var $tr = $(this), $td = $tr.find(that.options.cardView ? '.card-view' : 'td').eq(fieldIndex), index = key
											.indexOf(' '), name = key.substring(0, index), el = key.substring(index + 1), func = events[key];

									$td.find(el).off(name).on(name, function(e) {
										var index = $tr.data('index'), row = that.data[index], value = row[field];

										func.apply(this, [ e, value, row, index ]);
									});
								});
			}
		});

		this.updateSelected();
		this.resetView();

		this.trigger('post-body');
	};

	/**
	 * 根据条件去服务器加载数据
	 * 
	 * @param silent
	 *            ，悄悄加载，不显示正在加载。。。
	 * @param query
	 */
	mmuiTable.prototype.initServer = function(silent, query) {
		var that = this, data = {}, params = {
			searchText : this.searchText,
			sortName : this.options.sortName,
			sortOrder : this.options.sortOrder
		}, request;

		if (this.options.pagination) {
			params.pageSize = this.options.pageSize === this.options.formatAllRows() ? this.options.totalRows : this.options.pageSize;
			params.pageNumber = this.options.pageNumber;
		}

		if (!this.options.url && !this.options.ajax) {
			return;
		}

		if (this.options.queryParamsType === 'pageSize') {
			params = {
				search : params.searchText,
				sort : params.sortName,
				order : params.sortOrder
			};
			if (this.options.pagination) {
				params.pageSize = this.options.pageSize === this.options.formatAllRows() ? this.options.totalRows : this.options.pageSize;
				params.pageStart = this.options.pageSize === this.options.formatAllRows() ? 0 : this.options.pageSize * (this.options.pageNumber - 1);
			}
		}

		if (!($.isEmptyObject(this.filterColumnsPartial))) {
			params['filter'] = JSON.stringify(this.filterColumnsPartial, null);
		}

		/**
		 * 计算获取执行对象 self 执行的上下环境 name,如果是对象，就获取返回， 如果是方法就执行args参数并返回，
		 * 如果是字符串就把字符串连接到args中返回 如果都不是就返回默认值defaultValue
		 */
		// var calculateObjectValue = function (self, name, args, defaultValue)
		// 这里的执行是执行queryParams方法，吧options，[params] 作为参数传入，返回[params]
		data = calculateObjectValue(this.options, this.options.queryParams, [ params ], data);
		data.page = this.options.pageNumber;
		data.limit = this.options.pageSize;
		$.extend(data, query || {});

		if (data === false) {
			return;
		}
		data = mmUtl.filter.ise(data);
		if (!silent) {
			this.$tableLoading.show();
		}
		request = $.extend({}, calculateObjectValue(null, this.options.ajaxOptions), {
			type : this.options.method,
			url : this.options.url,
			data : this.options.contentType === 'application/json' && this.options.method === 'post' ? JSON.stringify(data) : data,
			cache : this.options.cache,
			contentType : this.options.contentType,
			dataType : this.options.dataType,
			success : function(res) {
				
				res = calculateObjectValue(that.options, that.options.responseHandler, [ res ], res);

				if(res.code==0){
					that.load(res.data);// 加载服务器数据到表格展示
				}else{
					mmui.alert(res.msg,3);
				}
				
				that.trigger('load-success', res);
				if (!silent)
					that.$tableLoading.hide();
			},
			error : function(res) {
				that.trigger('load-error', res.status, res);
				if (!silent)
					that.$tableLoading.hide();
			}
		});

		if (this.options.ajax) {
			calculateObjectValue(this, this.options.ajax, [ request ], null);
		} else {
			$.ajax(request);
		}
	};

	mmuiTable.prototype.initSearchText = function() {
		if (this.options.search) {
			if (this.options.searchText !== '') {
				var $search = this.$toolbar.find('.search input');
				$search.val(this.options.searchText);
				this.onSearch({
					currentTarget : $search
				});
			}
		}
	};

	mmuiTable.prototype.getCaret = function() {
		var that = this;

		$.each(this.$header.find('th'),
				function(i, th) {
					$(th).find('.sortable').removeClass('desc asc').addClass(
							$(th).data('field') === that.options.sortName ? that.options.sortOrder : 'both');
				});
	};

	mmuiTable.prototype.updateSelected = function() {
		var checkAll = this.$selectItem.filter(':enabled').length
				&& this.$selectItem.filter(':enabled').length === this.$selectItem.filter(':enabled').filter(':checked').length;

		this.$selectAll.add(this.$selectAll_).prop('checked', checkAll);

		this.$selectItem.each(function() {
			$(this).closest('tr')[$(this).prop('checked') ? 'addClass' : 'removeClass']('selected');
		});
	};

	mmuiTable.prototype.updateRows = function() {
		var that = this;

		this.$selectItem.each(function() {
			that.data[$(this).data('index')][that.header.stateField] = $(this).prop('checked');
		});
	};

	mmuiTable.prototype.resetRows = function() {
		var that = this;

		$.each(this.data, function(i, row) {
			that.$selectAll.prop('checked', false);
			that.$selectItem.prop('checked', false);
			if (that.header.stateField) {
				row[that.header.stateField] = false;
			}
		});
	};

	/**
	 * 根据名称触发对应的事件
	 * 具体用传过来的name+'.bs.table'转为事件全名，在mmuiTable.EVENTS[name]查找对应的事件方法执行
	 * 
	 * @param name
	 */
	mmuiTable.prototype.trigger = function(name) {
		var args = Array.prototype.slice.call(arguments, 1);

		name += '.bs.table';
		this.options[mmuiTable.EVENTS[name]].apply(this.options, args);
		this.$el.trigger($.Event(name), args);

		this.options.onAll(name, args);
		this.$el.trigger($.Event('all.bs.table'), [ name, args ]);
	};

	mmuiTable.prototype.resetHeader = function() {
		clearTimeout(this.timeoutId_);
		this.timeoutId_ = setTimeout($.proxy(this.fitHeader, this), this.$el.is(':hidden') ? 100 : 0);
	};

	mmuiTable.prototype.fitHeader = function() {
		var that = this, fixedBody, scrollWidth, focused, focusedTemp;

		if (that.$el.is(':hidden')) {
			that.timeoutId_ = setTimeout($.proxy(that.fitHeader, that), 100);
			return;
		}
		fixedBody = this.$tableBody.get(0);

		scrollWidth = fixedBody.scrollWidth > fixedBody.clientWidth && fixedBody.scrollHeight > fixedBody.clientHeight + this.$header.outerHeight() ? mmUtl.scrollbarWidth : 0;

		this.$el.css('margin-top', -this.$header.outerHeight());

		focused = $(':focus');
		if (focused.length > 0) {
			var $th = focused.parents('th');
			if ($th.length > 0) {
				var dataField = $th.attr('data-field');
				if (dataField !== undefined) {
					var $headerTh = this.$header.find("[data-field='" + dataField + "']");
					if ($headerTh.length > 0) {
						$headerTh.find(":input").addClass("focus-temp");
					}
				}
			}
		}

		//复制一个头部单独使用
		this.$header_ = this.$header.clone(true, true);
		this.$selectAll_ = this.$header_.find('[name="btSelectAll"]');
		this.$tableHeader.css({
			'margin-right' : scrollWidth
		}).find('table').css('width', this.$el.outerWidth()).html('').attr('class', this.$el.attr('class')).append(this.$header_);

		focusedTemp = $('.focus-temp:visible:eq(0)');
		if (focusedTemp.length > 0) {
			focusedTemp.focus();
			this.$header.find('.focus-temp').removeClass('focus-temp');
		}

		this.$header.find('th[data-field]').each(function(i) {
			that.$header_.find(sprintf('th[data-field="%s"]', $(this).data('field'))).data($(this).data());
		});

		var visibleFields = this.getVisibleFields();

		this.$body.find('>tr:first-child:not(.no-records-found) > *').each(function(i) {
			var $this = $(this), index = i;

			if (that.options.detailView && !that.options.cardView) {
				if (i === 0) {
					//设置父子表的宽度
					that.$header_.find('th.detail').find('.fht-cell').width($this.innerWidth());
				}
				index = i - 1;
			}
			//设置表头宽度
			that.$header_.find(sprintf('th[data-field="%s"]', visibleFields[index])).find('.fht-cell').width($this.innerWidth());
		});
		
		this.$tableBody.off('scroll').on('scroll', function() {
			that.$tableHeader.scrollLeft($(this).scrollLeft());

			if (that.options.showFooter && !that.options.cardView) {
				that.$tableFooter.scrollLeft($(this).scrollLeft());
			}
		});
		that.trigger('post-header');
	};

	mmuiTable.prototype.resetFooter = function() {
		var that = this, data = that.getData(), html = [];

		if (!this.options.showFooter || this.options.cardView) { // do
			// nothing
			return;
		}

		if (!this.options.cardView && this.options.detailView) {
			html.push('<td><div class="th-inner">&nbsp;</div><div class="fht-cell"></div></td>');
		}

		$.each(this.columns, function(i, column) {
			var falign = '', style = '', class_ = sprintf(' class="%s"', column['class']);

			if (!column.visible) {
				return;
			}

			if (that.options.cardView && (!column.cardVisible)) {
				return;
			}

			falign = sprintf('text-align: %s; ', column.falign ? column.falign : column.align);
			style = sprintf('vertical-align: %s; ', column.valign);

			html.push('<td', class_, sprintf(' style="%s"', falign + style), '>');
			html.push('<div class="th-inner">');

			html.push(calculateObjectValue(column, column.footerFormatter, [ data ], '&nbsp;') || '&nbsp;');

			html.push('</div>');
			html.push('<div class="fht-cell"></div>');
			html.push('</div>');
			html.push('</td>');
		});

		this.$tableFooter.find('tr').html(html.join(''));
		clearTimeout(this.timeoutFooter_);
		this.timeoutFooter_ = setTimeout($.proxy(this.fitFooter, this), this.$el.is(':hidden') ? 100 : 0);
	};

	//设置表尾fnt-cell的宽度;
	mmuiTable.prototype.fitFooter = function() {
		var that = this, $footerTd, elWidth, scrollWidth;

		clearTimeout(this.timeoutFooter_);
		if (this.$el.is(':hidden')) {
			this.timeoutFooter_ = setTimeout($.proxy(this.fitFooter, this), 100);
			return;
		}

		elWidth = this.$el.css('width');
		scrollWidth = elWidth > this.$tableBody.width() ? mmUtl.scrollbarWidth : 0;

		this.$tableFooter.css({
			'margin-right' : scrollWidth
		}).find('table').css('width', elWidth).attr('class', this.$el.attr('class'));

		$footerTd = this.$tableFooter.find('td');

		this.$body.find('>tr:first-child:not(.no-records-found) > *').each(function(i) {
			var $this = $(this);
			//设置表尾宽度
			$footerTd.eq(i).find('.fht-cell').width($this.innerWidth());
		});
	};

	mmuiTable.prototype.toggleColumn = function(index, checked, needUpdate) {
		if (index === -1) {
			return;
		}
		this.columns[index].visible = checked;
		this.initHeader();
		this.initSearch();
		this.initPagination();
		this.initBody();

		if (this.options.showColumns) {
			var $items = this.$toolbar.find('.keep-open input').prop('disabled', false);

			if (needUpdate) {
				$items.filter(sprintf('[value="%s"]', index)).prop('checked', checked);
			}

			if ($items.filter(':checked').length <= this.options.minimumCountColumns) {
				$items.filter(':checked').prop('disabled', true);
			}
		}
	};

	mmuiTable.prototype.toggleRow = function(index, uniqueId, visible) {
		if (index === -1) {
			return;
		}

		this.$body.find(typeof index !== 'undefined' ? sprintf('tr[data-index="%s"]', index) : sprintf('tr[data-uniqueid="%s"]', uniqueId))[visible ? 'show'
				: 'hide']();
	};

	mmuiTable.prototype.getVisibleFields = function() {
		var that = this, visibleFields = [];

		$.each(this.header.fields, function(j, field) {
			var column = that.columns[mmUtl.table.getFieldIndex(that.columns, field)];

			if (!column.visible) {
				return;
			}
			visibleFields.push(field);
		});
		return visibleFields;
	};

	/**
	 * 重置表格视图
	 * 
	 * @param params
	 */
	mmuiTable.prototype.resetView = function(params) {
		var padding = 0;

		if (params && params.height) {
			this.options.height = params.height;
		}

		this.$selectAll.prop('checked', this.$selectItem.length > 0 && this.$selectItem.length === this.$selectItem.filter(':checked').length);

		if (this.options.height) {
			var toolbarHeight = mmUtl.table.getRealHeight(this.$toolbar), paginationHeight = mmUtl.table.getRealHeight(this.$pagination), height = this.options.height
					- toolbarHeight - paginationHeight;

			this.$tableContainer.css('height', height + 'px');
		}

		if (this.options.cardView) {
			this.$el.css('margin-top', '0');
			this.$tableContainer.css('padding-bottom', '0');
			return;
		}

		if (this.options.showHeader && this.options.height) {
			this.$tableHeader.show();
			this.resetHeader();
			padding += this.$header.outerHeight();
		} else {
			this.$tableHeader.hide();
			this.trigger('post-header');
		}

		if (this.options.showFooter) {
			this.resetFooter();
			if (this.options.height) {
				padding += this.$tableFooter.outerHeight() + 1;
			}
		}

		this.getCaret();
		this.$tableContainer.css('padding-bottom', padding + 'px');
		this.trigger('reset-view');
	};

	mmuiTable.prototype.getData = function(useCurrentPage) {
		return (this.searchText || !$.isEmptyObject(this.filterColumns) || !$.isEmptyObject(this.filterColumnsPartial)) ? (useCurrentPage ? this.data
				.slice(this.pageFrom - 1, this.pageTo) : this.data) : (useCurrentPage ? this.options.data.slice(this.pageFrom - 1, this.pageTo)
				: this.options.data);
	};

	/**
	 * 加载数据到表格显示
	 */
	mmuiTable.prototype.load = function(data) {
		var fixedScroll = false;

		if (this.options.sidePagination === 'server') {
			this.options.totalRows = data.total;
			fixedScroll = data.fixedScroll;
			data = data[this.options.dataField];
		} else if (!$.isArray(data)) {
			fixedScroll = data.fixedScroll;
			data = data.data;
		}

		this.initData(data);
		this.initSearch();
		this.initPagination();
		this.initBody(fixedScroll);
	};

	mmuiTable.prototype.append = function(data) {
		this.initData(data, 'append');
		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.prepend = function(data) {
		this.initData(data, 'prepend');
		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.remove = function(params) {
		var len = this.options.data.length, i, row;

		if (!params.hasOwnProperty('field') || !params.hasOwnProperty('values')) {
			return;
		}

		for (i = len - 1; i >= 0; i--) {
			row = this.options.data[i];

			if (!row.hasOwnProperty(params.field)) {
				continue;
			}
			if ($.inArray(row[params.field], params.values) !== -1) {
				this.options.data.splice(i, 1);
			}
		}

		if (len === this.options.data.length) {
			return;
		}

		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.removeAll = function() {
		if (this.options.data.length > 0) {
			this.options.data.splice(0, this.options.data.length);
			this.initSearch();
			this.initPagination();
			this.initBody(true);
		}
	};

	mmuiTable.prototype.getRowByUniqueId = function(id) {
		var uniqueId = this.options.uniqueId, len = this.options.data.length, dataRow = null, i, row, rowUniqueId;

		for (i = len - 1; i >= 0; i--) {
			row = this.options.data[i];

			if (row.hasOwnProperty(uniqueId)) {
				rowUniqueId = row[uniqueId];
			} else if (row._data.hasOwnProperty(uniqueId)) {
				rowUniqueId = row._data[uniqueId];
			} else {
				continue;
			}

			if (typeof rowUniqueId === 'string') {
				id = id.toString();
			} else if (typeof rowUniqueId === 'number') {
				if ((Number(rowUniqueId) === rowUniqueId) && (rowUniqueId % 1 === 0)) {
					id = parseInt(id);
				} else if ((rowUniqueId === Number(rowUniqueId)) && (rowUniqueId !== 0)) {
					id = parseFloat(id);
				}
			}

			if (rowUniqueId === id) {
				dataRow = row;
				break;
			}
		}

		return dataRow;
	};

	mmuiTable.prototype.removeByUniqueId = function(id) {
		var len = this.options.data.length, row = this.getRowByUniqueId(id);

		if (row) {
			this.options.data.splice(this.options.data.indexOf(row), 1);
		}

		if (len === this.options.data.length) {
			return;
		}

		this.initSearch();
		this.initPagination();
		this.initBody(true);
	};

	mmuiTable.prototype.updateByUniqueId = function(params) {
		var rowId;

		if (!params.hasOwnProperty('id') || !params.hasOwnProperty('row')) {
			return;
		}

		rowId = $.inArray(this.getRowByUniqueId(params.id), this.options.data);

		if (rowId === -1) {
			return;
		}

		$.extend(this.data[rowId], params.row);
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.insertRow = function(params) {
		if (!params.hasOwnProperty('index') || !params.hasOwnProperty('row')) {
			return;
		}
		this.data.splice(params.index, 0, params.row);
		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.updateRow = function(params) {
		if (!params.hasOwnProperty('index') || !params.hasOwnProperty('row')) {
			return;
		}
		$.extend(this.data[params.index], params.row);
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.showRow = function(params) {
		if (!params.hasOwnProperty('index') && !params.hasOwnProperty('uniqueId')) {
			return;
		}
		this.toggleRow(params.index, params.uniqueId, true);
	};

	mmuiTable.prototype.hideRow = function(params) {
		if (!params.hasOwnProperty('index') && !params.hasOwnProperty('uniqueId')) {
			return;
		}
		this.toggleRow(params.index, params.uniqueId, false);
	};

	mmuiTable.prototype.getRowsHidden = function(show) {
		var rows = $(this.$body[0]).children().filter(':hidden'), i = 0;
		if (show) {
			for (; i < rows.length; i++) {
				$(rows[i]).show();
			}
		}
		return rows;
	};

	mmuiTable.prototype.mergeCells = function(options) {
		var row = options.index, col = $.inArray(options.field, this.getVisibleFields()), rowspan = options.rowspan || 1, colspan = options.colspan || 1, i, j, $tr = this.$body
				.find('>tr'), $td;

		if (this.options.detailView && !this.options.cardView) {
			col += 1;
		}

		$td = $tr.eq(row).find('>td').eq(col);

		if (row < 0 || col < 0 || row >= this.data.length) {
			return;
		}

		for (i = row; i < row + rowspan; i++) {
			for (j = col; j < col + colspan; j++) {
				$tr.eq(i).find('>td').eq(j).hide();
			}
		}

		$td.attr('rowspan', rowspan).attr('colspan', colspan).show();
	};

	mmuiTable.prototype.updateCell = function(params) {
		if (!params.hasOwnProperty('index') || !params.hasOwnProperty('field') || !params.hasOwnProperty('value')) {
			return;
		}
		this.data[params.index][params.field] = params.value;

		if (params.reinit === false) {
			return;
		}
		this.initSort();
		this.initBody(true);
	};

	mmuiTable.prototype.getOptions = function() {
		return this.options;
	};

	mmuiTable.prototype.getSelections = function() {
		var that = this;

		return $.grep(this.data, function(row) {
			return row[that.header.stateField];
		});
	};

	mmuiTable.prototype.getAllSelections = function() {
		var that = this;

		return $.grep(this.options.data, function(row) {
			return row[that.header.stateField];
		});
	};

	mmuiTable.prototype.checkAll = function() {
		this.checkAll_(true);
	};

	mmuiTable.prototype.uncheckAll = function() {
		this.checkAll_(false);
	};

	mmuiTable.prototype.checkAll_ = function(checked) {
		var rows;
		if (!checked) {
			rows = this.getSelections();
		}
		this.$selectAll.add(this.$selectAll_).prop('checked', checked);
		this.$selectItem.filter(':enabled').prop('checked', checked);
		this.updateRows();
		if (checked) {
			rows = this.getSelections();
		}
		this.trigger(checked ? 'check-all' : 'uncheck-all', rows);
	};

	mmuiTable.prototype.check = function(index) {
		this.check_(true, index);
	};

	mmuiTable.prototype.uncheck = function(index) {
		this.check_(false, index);
	};

	mmuiTable.prototype.check_ = function(checked, index) {
		var $el = this.$selectItem.filter(sprintf('[data-index="%s"]', index)).prop('checked', checked);
		this.data[index][this.header.stateField] = checked;
		this.updateSelected();
		this.trigger(checked ? 'check' : 'uncheck', this.data[index], $el);
	};

	mmuiTable.prototype.checkBy = function(obj) {
		this.checkBy_(true, obj);
	};

	mmuiTable.prototype.uncheckBy = function(obj) {
		this.checkBy_(false, obj);
	};

	mmuiTable.prototype.checkBy_ = function(checked, obj) {
		if (!obj.hasOwnProperty('field') || !obj.hasOwnProperty('values')) {
			return;
		}

		var that = this, rows = [];
		$.each(this.options.data, function(index, row) {
			if (!row.hasOwnProperty(obj.field)) {
				return false;
			}
			if ($.inArray(row[obj.field], obj.values) !== -1) {
				var $el = that.$selectItem.filter(':enabled').filter(sprintf('[data-index="%s"]', index)).prop('checked', checked);
				row[that.header.stateField] = checked;
				rows.push(row);
				that.trigger(checked ? 'check' : 'uncheck', row, $el);
			}
		});
		this.updateSelected();
		this.trigger(checked ? 'check-some' : 'uncheck-some', rows);
	};

	mmuiTable.prototype.destroy = function() {
		this.$el.insertBefore(this.$container);
		$(this.options.toolbar).insertBefore(this.$el);
		this.$container.next().remove();
		this.$container.remove();
		this.$el.html(this.$el_.html()).css('margin-top', '0').attr('class', this.$el_.attr('class') || '');
	};

	mmuiTable.prototype.showLoading = function() {
		this.$tableLoading.show();
	};

	mmuiTable.prototype.hideLoading = function() {
		this.$tableLoading.hide();
	};

	mmuiTable.prototype.togglePagination = function() {
		this.options.pagination = !this.options.pagination;
		var button = this.$toolbar.find('button[name="paginationSwitch"] i');
		if (this.options.pagination) {
			button.attr("class", this.options.iconsPrefix + " " + this.options.icons.paginationSwitchDown);
		} else {
			button.attr("class", this.options.iconsPrefix + " " + this.options.icons.paginationSwitchUp);
		}
		this.updatePagination();
	};

	/**
	 * 提供参数刷新查询
	 * 
	 * @param params
	 */
	mmuiTable.prototype.refresh = function(params) {
		if (params && params.url) {
			this.options.url = params.url;
			this.options.pageNumber = 1;
		}
		this.initServer(params && params.silent, params && params.query);
	};

	mmuiTable.prototype.resetWidth = function() {
		if (this.options.showHeader && this.options.height) {
			this.fitHeader();
		}
		if (this.options.showFooter) {
			this.fitFooter();
		}
	};

	mmuiTable.prototype.showColumn = function(field) {
		this.toggleColumn(mmUtl.table.getFieldIndex(this.columns, field), true, true);
	};

	mmuiTable.prototype.hideColumn = function(field) {
		this.toggleColumn(mmUtl.table.getFieldIndex(this.columns, field), false, true);
	};

	mmuiTable.prototype.getHiddenColumns = function() {
		return $.grep(this.columns, function(column) {
			return !column.visible;
		});
	};

	mmuiTable.prototype.filterBy = function(columns) {
		this.filterColumns = $.isEmptyObject(columns) ? {} : columns;
		this.options.pageNumber = 1;
		this.initSearch();
		this.updatePagination();
	};

	mmuiTable.prototype.scrollTo = function(value) {
		if (typeof value === 'string') {
			value = value === 'bottom' ? this.$tableBody[0].scrollHeight : 0;
		}
		if (typeof value === 'number') {
			this.$tableBody.scrollTop(value);
		}
		if (typeof value === 'undefined') {
			return this.$tableBody.scrollTop();
		}
	};

	mmuiTable.prototype.getScrollPosition = function() {
		return this.scrollTo();
	};

	mmuiTable.prototype.selectPage = function(page) {
		if (page > 0 && page <= this.options.totalPages) {
			this.options.pageNumber = page;
			this.updatePagination();
		}
	};

	mmuiTable.prototype.prevPage = function() {
		if (this.options.pageNumber > 1) {
			this.options.pageNumber--;
			this.updatePagination();
		}
	};

	mmuiTable.prototype.nextPage = function() {
		if (this.options.pageNumber < this.options.totalPages) {
			this.options.pageNumber++;
			this.updatePagination();
		}
	};

	mmuiTable.prototype.toggleView = function() {
		this.options.cardView = !this.options.cardView;
		this.initHeader();
		this.initBody();
		this.trigger('toggle', this.options.cardView);
	};

	/**
	 * 提供option数据进行刷新操作，是根本性的改变
	 * 
	 * @param options
	 */
	mmuiTable.prototype.refreshOptions = function(options) {
		// 比较两个对象如果没有变化就返回，什么也不做
		if (mmUtl.table.compareObjects(this.options, options, true)) {
			return;
		}
		this.options = $.extend(this.options, options);
		this.trigger('refresh-options', this.options);
		this.destroy();
		this.init();
	};

	mmuiTable.prototype.resetSearch = function(text) {
		var $search = this.$toolbar.find('.search input');
		$search.val(text || '');
		this.onSearch({
			currentTarget : $search
		});
	};

	mmuiTable.prototype.expandRow_ = function(expand, index) {
		var $tr = this.$body.find(sprintf('> tr[data-index="%s"]', index));
		if ($tr.next().is('tr.detail-view') === (expand ? false : true)) {
			$tr.find('> td > .detail-icon').click();
		}
	};

	mmuiTable.prototype.expandRow = function(index) {
		this.expandRow_(true, index);
	};

	mmuiTable.prototype.collapseRow = function(index) {
		this.expandRow_(false, index);
	};

	mmuiTable.prototype.expandAllRows = function(isSubTable) {
		if (isSubTable) {
			var $tr = this.$body.find(sprintf('> tr[data-index="%s"]', 0)), that = this, detailIcon = null, executeInterval = false, idInterval = -1;

			if (!$tr.next().is('tr.detail-view')) {
				$tr.find('> td > .detail-icon').click();
				executeInterval = true;
			} else if (!$tr.next().next().is('tr.detail-view')) {
				$tr.next().find(".detail-icon").click();
				executeInterval = true;
			}

			if (executeInterval) {
				try {
					idInterval = setInterval(function() {
						detailIcon = that.$body.find("tr.detail-view").last().find(".detail-icon");
						if (detailIcon.length > 0) {
							detailIcon.click();
						} else {
							clearInterval(idInterval);
						}
					}, 1);
				} catch (ex) {
					clearInterval(idInterval);
				}
			}
		} else {
			var trs = this.$body.children();
			for (var i = 0; i < trs.length; i++) {
				this.expandRow_(true, $(trs[i]).data("index"));
			}
		}
	};

	mmuiTable.prototype.collapseAllRows = function(isSubTable) {
		if (isSubTable) {
			this.expandRow_(false, 0);
		} else {
			var trs = this.$body.children();
			for (var i = 0; i < trs.length; i++) {
				this.expandRow_(false, $(trs[i]).data("index"));
			}
		}
	};

	mmuiTable.prototype.updateFormatText = function(name, text) {
		if (this.options[sprintf('format%s', name)]) {
			if (typeof text === 'string') {
				this.options[sprintf('format%s', name)] = function() {
					return text;
				};
			} else if (typeof text === 'function') {
				this.options[sprintf('format%s', name)] = text;
			}
		}
		this.initToolbar();
		this.initPagination();
		this.initBody();
	};
	
}(window,jQuery)

