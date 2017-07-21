/**
 * 弹出层工具
 * 需要先引入：mm-base.js
 * version：3.3
 * LastDate: 2017-07-10
 */
!function(window, jQuery) {
	"use strict";
	if(window.mmui)return ;
	
	var $ = jQuery,
	win = $(window);
	
// 缓存常用字符
	var doms = [ 'mmui-layer', '.mmui-layer-title', '.mmui-layer-main', '.mmui-layer-dialog', 'mmui-layer-iframe', 'mmui-layer-content',
			'mmui-layer-btn', 'mmui-layer-close' ];
	doms.anim = [ 'mmui-anim', 'mmui-anim-01', 'mmui-anim-02', 'mmui-anim-03', 'mmui-anim-04', 'mmui-anim-05', 'mmui-anim-06' ];
	
	var ready = {
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

	/********************定义弹出工具类，一个页面只允许有一个此对象*******************/
	
	// 默认内置方法。
	var mmui = {
		ie6 : !!window.ActiveXObject && !window.XMLHttpRequest,
		index : 0,
		path : mmUtl.getPath,
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

	/***********定义弹出层对象，每次弹出层会新生成此对象************/
	
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
	
	/*******************************提供一些mmui静态方法***********************************/
	
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
	
	window.mmui = mmui;
}(window,jQuery)