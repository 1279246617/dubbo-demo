﻿/*!
 * lhgcore Calendar Plugin v3.0.0
 * Date : 2012-03-13 10:35:11
 * Copyright (c) 2009 - 2012 By Li Hui Gang
 */

;(function( $, window, undefined ){

var document = window.document, _box,
    addzero = /\b(\w)\b/g,
    _ie = !!window.ActiveXObject,
	_ie6 = _ie && !window.XMLHttpRequest,
	_$window = $(window),
	expando = 'JCA' + (new Date).getTime(),
	
_path = (function( script, i ){
    var l = script.length, path;
	
	for( ; i < l; i++ )
	{
		path = !!document.querySelector ?
		    script[i].src : script[i].getAttribute('src',4);
		
		if( path.substr(path.lastIndexOf('/')).indexOf('lhgcalendar') !== -1 )
		    break;
	}
	
	return path.substr( 0, path.lastIndexOf('/') + 1 );
})(document.getElementsByTagName('script'),0),

iframeTpl = _ie6 ? '<iframe id="lhgcal_frm" hideFocus="true" ' + 
	'frameborder="0" src="about:blank" style="position:absolute;' +
	'z-index:-1;width:100%;top:0px;left:0px;filter:' +
	'progid:DXImageTransform.Microsoft.Alpha(opacity=0)"><\/iframe>' : '',

calendarTpl =
'<table class="lcui_border" border="0" cellspacing="0" cellpadding="0">' +
'<tbody>' +
'<tr>' +
	'<td class="lcui_lt"></td>' +
	'<td class="lcui_t"></td>' +
	'<td class="lcui_rt"></td>' +
'</tr>' +
'<tr>' +
	'<td class="lcui_l"></td>' +
	'<td>' +
	    '<div class="lcui_head">' +
			'<table width="100%" cellspacing="0" cellpadding="0" border="0">' +
			'<tr>' +
			    '<td width="14"><a class="cui_pm" href="javascript:void(0);"></a></td>' +
				'<td width="40"><input class="cui_im" maxlength="4" value=""/>月</td>' +
				'<td><a class="cui_nm" href="javascript:void(0);"></a></td>' +
				'<td width="14"><a class="cui_py" href="javascript:void(0);"></a></td>' +
				'<td width="60"><input class="cui_iy" maxlength="4" value=""/>年</td>' +
				'<td width="9"><a class="cui_ny" href="javascript:void(0);"></a></td>' +
			'</tr>' +
			'</table>' +
			'<div class="cui_ymlist" style="display:none;">' +
			    '<table width="100%" cellspacing="1" cellpadding="0" border="0">' +
				    '<thead class="cui_ybar"><tr>' +
					    '<td><a class="cui_pybar" href="javascript:void(0);">«</a></td>' +
						'<td><a class="cui_cybar" href="javascript:void(0);">\xd7</a></td>' +
						'<td><a class="cui_nybar" href="javascript:void(0);">»</a></td>' +
					'</tr></thead>' +
					'<tbody class="cui_lbox">' +
					
					'</tbody>' +
				'</table>' +
			'</div>' +
		'</div>' +
		'<div class="lcui_body">' +
		    '<table cellspacing="1" cellpadding="0" border="0">' +
			    '<thead><tr>' +
				    '<td>\u65E5</td><td>\u4E00</td><td>\u4E8C</td><td>\u4E09</td><td>\u56DB</td><td>\u4E94</td><td>\u516D</td>' +
				'</tr></thead>' +
				'<tbody class="cui_db">' +
				'</tbody>' +
			'</table>' +
		'</div>' +
		'<div class="cui_foot">' +
		    '<table width="100%" cellspacing="0" cellpadding="0" border="0">' +
			'<tr>' +
			    '<td align="center" class="lcui_today"><a class="cui_tbtn" href="javascript:void(0);">\u4ECA\u5929</a></td>' +
				'<td align="center" class="lcui_time"><input class="cui_hour" maxlength="2"/>:<input class="cui_minute" maxlength="2"/>:<input class="cui_second" maxlength="2"/></td>' +
				'<td align="center" class="lcui_empty"><a class="cui_dbtn" href="javascript:void(0);">\u6E05\u7A7A</a></td>' +
			'</tr>' +
			'</table>' +
		'</div>' +
	'</td>' +
	'<td class="lcui_r"></td>' +
'</tr>' +
'<tr>' +
	'<td class="lcui_lb"></td>' +
	'<td class="lcui_b"></td>' +
	'<td class="lcui_rb"></td>' +
'</tr>' +
'</tbody>' +
'</table>' + iframeTpl;

/*! 开启IE6 CSS背景图片缓存 */
try{
	document.execCommand( 'BackgroundImageCache', false, true );
}catch(e){};

(function(head){
    var link = document.createElement('link');
	
	link.href = _path + 'skins/lhgcalendar.css';
    link.rel = 'stylesheet';
	
	head.appendChild( link );
})(document.getElementsByTagName('head')[0]);

function isDigit(ev)
{
    var iCode = ( ev.keyCode || ev.charCode );

	return (
			( iCode >= 48 && iCode <= 57 )		// Numbers
			|| (iCode >= 37 && iCode <= 40)		// Arrows
			|| iCode == 8						// Backspace
			|| iCode == 46						// Delete
	);
};

function dateFormat( format )
{
	var that = this,
	
	o = {
	    'M+': that.getMonth() + 1,
		'd+': that.getDate(),
		'h+': that.getHours()%12 == 0 ? 12 : that.getHours()%12,
		'H+': that.getHours(),
		'm+': that.getMinutes(),
		's+': that.getSeconds(),
		'q+': Math.floor((that.getMonth() + 3) / 3),
		'w': '0123456'.indexOf(that.getDay()),
		'S': that.getMilliseconds()
	};
	
	if( /(y+)/.test(format) )
	    format = format.replace(RegExp.$1, (that.getFullYear() + '').substr(4 - RegExp.$1.length));
	
	for( var k in o )
	{
	    if( new RegExp('(' + k + ')').test(format) )
		    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
	}
	
	return format;
};

function getDate( string, format )
{
    var regexp, tmpnow = new Date();
	
	/** year : /yyyy/ */
	y4 = '([0-9]{4})',
	/** year : /yy/ */
	y2 = '([0-9]{2})',
	/** index year */
	yi = -1,
	
	/** month : /MM/ */
	M2 = '(0[1-9]|1[0-2])',
	/** month : /M/ */
	M1 = '([1-9]|1[0-2])',
	/** index month */
	Mi = -1,
	
	/** day : /dd/ */
	d2 = '(0[1-9]|[1-2][0-9]|30|31)',
	/** day : /d/ */
	d1 = '([1-9]|[1-2][0-9]|30|31)',
	/** index day */
	di = -1,
	
	/** hour : /HH/ */
	H2 = '([0-1][0-9]|20|21|22|23)',
	/** hour : /H/ */
	H1 = '([0-9]|1[0-9]|20|21|22|23)',
	/** index hour */
	Hi = -1,
	
	/** minute : /mm/ */
	m2 = '([0-5][0-9])',
	/** minute : /m/ */
	m1 = '([0-9]|[1-5][0-9])',
	/** index minute */
	mi = -1,
	
	/** second : /ss/ */
	s2 = '([0-5][0-9])',
	/** second : /s/ */
	s1 = '([0-9]|[1-5][0-9])',
	/** index month */
	si = -1;
	
	if( validDate(string,format) )
	{
		var val = regexp.exec( string ), reDate,
			index = getIndex( format ),
			year = index[0] >= 0 ? val[ index[0] + 1 ] : tmpnow.getFullYear(),
			month = index[1] >= 0 ? ( val[index[1]+1] - 1 ) : tmpnow.getMonth(),
			day = index[2] >= 0 ? val[ index[2] + 1 ] : tmpnow.getDate(),
			hour = index[3] >= 0 ? val[ index[3] + 1 ] : tmpnow.getHours(),
			minute = index[4] >= 0 ? val[ index[4] + 1 ] : tmpnow.getMinutes(),
			second = index[5] >= 0 ? val[ index[5] + 1 ] : tmpnow.getSeconds(),

		reDate = new Date( year, month, day, hour, minute, second );
		
		if( reDate.getDate() == day )
		    return reDate;
		else
		    return tmpnow;
	}
	else
	    return tmpnow;
	
	function validDate( string, format )
	{
		
		sting = $.trim( string );
		if( string === '' ) return;
		
		format = format.replace(/yyyy/,y4).replace(/yy/,y2).replace(/MM/,M2)
		         .replace(/M/,M1).replace(/dd/,d2).replace(/d/,d1).replace(/HH/,H2)
				 .replace(/H/,H1).replace(/mm/,m2).replace(/m/,m1).replace(/ss/,s2)
				 .replace(/s/,s1);
		
		format = new RegExp( '^' + format + '$' );
		regexp = format;
		
		return format.test( string );
	};
	
	function getIndex( format )
	{
	    var ia = [], i = 0, ia2;
		
		yi = format.indexOf('yyyy');
		if( yi < 0 ) yi = format.indexOf('yy');
		if( yi >= 0 )
		{
		    ia[i] = yi;
			i++;
		}
		
		Mi = format.indexOf('MM');
		if( Mi < 0 ) Mi = format.indexOf('M');
		if( Mi >= 0 )
		{
		    ia[i] = Mi;
			i++;
		}
		
		di = format.indexOf('dd');
		if( di < 0 ) di = format.indexOf('d');
		if( di >= 0 )
		{
		    ia[i] = di;
			i++;
		}
		
		Hi = format.indexOf('HH');
		if( Hi < 0 ) Hi = format.indexOf('H');
		if( Hi >= 0 )
		{
		    ia[i] = Hi;
			i++;
		}
		
		mi = format.indexOf('mm');
		if( mi < 0 ) mi = format.indexOf('m');
		if( mi >= 0 )
		{
		    ia[i] = mi;
			i++;
		}
		
		si = format.indexOf('ss');
		if( si < 0 ) si = format.indexOf('s');
		if( si >= 0 )
		{
		    ia[i] = si;
			i++;
		}
		
		ia2 = [ yi, Mi, di, Hi, mi, si ];
		
		for( i = 0; i < ia.length - 1; i++ )
		{
		    for( j = 0; j < ia.length - 1 - i; j++ )
			{
			    if( ia[j] > ia[j+1] )
				{
				    var temp = ia[j];
					ia[j] = ia[j+1];
					ia[j+1] = temp;
				}
			}
		}
		
		for( i = 0; i < ia.length; i++ )
		{
		    for( j = 0; j < ia2.length; j++ )
			{
			    if( ia[i] == ia2[j] )
				    ia2[j] = i;
			}
		}
		
		return ia2;
	};
};

function convertDate( date, format, day )
{
    var tmpnow = new Date();
	
	if( /%/.test(date) )
	{
		day = day || 0;
		date = date.replace( /%y/, tmpnow.getFullYear() ).replace( /%M/, tmpnow.getMonth() + 1 ).replace( /%d/, tmpnow.getDate() + day )
		    .replace( /%H/, tmpnow.getHours() ).replace( /%m/, tmpnow.getMinutes() ).replace( /%s/, tmpnow.getSeconds() ).replace( addzero, '0$1' );
	}
	else if( /^#[\w-]+$/.test(date) )
	{
	    date = $.trim( $(date)[0].value );

		if( date.length > 0 && format )
		    date = dateFormat.call( getDate(date,format), 'yyyy-MM-dd' );
	}
	
	return date;
};

/*!--------------------------------------------------------------*/

var lhgcalendar = function( config )
    {
	    config = config || {};
		
		var setting = lhgcalendar.setting;
		
		for( var i in setting )
		{
		    if( config[i] === undefined ) config[i] = setting[i];
		}
		
		return _box ? _box._init(config) : new lhgcalendar.fn._init(config);
	};

lhgcalendar.fn = lhgcalendar.prototype =
{
    constructor: lhgcalendar,
	
	_init: function( config )
	{
	    var that = this, DOM,
		    evt = that._getEvent(),
			inpVal, date;
		
		that.config = config;
		
		that.DOM = DOM = that.DOM || that._getDOM();
		that.evObj = evt.srcElement || evt.target;
		that.inpE = config.id ? $(config.id)[0] : that.evObj;
		
		if( !config.btnBar )
		    DOM.foot[0].style.display = 'none';
		else
		    DOM.foot[0].style.display = '';
		
		if( config.minDate )
		    config.minDate = convertDate( config.minDate, config.targetFormat, config.noToday ? 1 : 0 );  
		
		if( config.maxDate )
		    config.maxDate = convertDate( config.maxDate, config.targetFormat, config.noToday ? -1 : 0 );
		
		inpVal = $.trim( that.inpE.value );
		
		if( inpVal.length > 0 )
		    date = getDate( inpVal, config.format );
		else
		    date = new Date();
		
		DOM.hour[0].value = (date.getHours() + '').replace(addzero,'0$1');
		DOM.minute[0].value = (date.getMinutes() + '').replace(addzero,'0$1');
		DOM.second[0].value = (date.getSeconds() + '').replace(addzero,'0$1');
		
		$('input',DOM.foot[0]).attr({ disabled:config.format.indexOf('H')>=0?false:true });
		
		that._draw(date).show()._offset(that.evObj);
		
		_ie6 && $('#lhgcal_frm').css({height:DOM.wrap[0].offsetHeight+'px'});
		
		if( !_box )
		{
			DOM.wrap[0].style.width = DOM.wrap[0].offsetWidth + 'px';
			that._addEvent(); _box = that;
		}
		
		return that;
	},
	
	_draw: function( date, day )
	{
	    var that = this,
		    DOM = that.DOM,
			firstDay,
			befMonth,
			curMonth,
			arrDate = [],
			inpYear,
			inpMonth,
			opt = that.config,
			frag, row, cell, n = 0, curDateStr;
		
		that.year = inpYear = date.getFullYear();
		that.month = inpMonth = date.getMonth() + 1;
		that.day = day || date.getDate();
		
		DOM.iy[0].value = inpYear;
		DOM.im[0].value = inpMonth;
		
		firstDay = new Date( inpYear, inpMonth - 1, 1 ).getDay();
		befMonth = new Date( inpYear, inpMonth - 1, 0 ).getDate();
		curMonth = new Date( inpYear, inpMonth, 0 ).getDate();
		
		for( var i = 0; i < firstDay; i++ )
		{
		    arrDate.push( befMonth );
			befMonth--;
		}
		
		arrDate.reverse();
		
		for( var i = 1; i <= curMonth; i++ )
		    arrDate.push(i);
		
		for( var i = 1; i <= 42 - curMonth - firstDay; i++ )
		    arrDate.push(i);
		
		frag = document.createDocumentFragment();
		
		for( var i = 0; i < 6; i++ )
		{
		    row = document.createElement('tr');
			for( var j = 0; j < 7; j++ )
			{
			    cell = document.createElement('td');
				curDateStr = (inpYear + '-' + inpMonth + '-' + arrDate[n]).replace(addzero,'0$1');
				
				if( n < firstDay || n >= curMonth + firstDay ||
				    opt.minDate && opt.minDate > curDateStr ||
					opt.maxDate && opt.maxDate < curDateStr ||
					opt.disWeek && opt.disWeek.indexOf(j) >= 0 )
				{
				    that._setCell( cell, arrDate[n] );
				}
				else if( opt.disDate )
				{
				    for( var m = 0, l = opt.disDate.length; m < l; m++ )
					{
					    if( /%/.test(opt.disDate[m]) )
						    opt.disDate[m] = convertDate( opt.disDate[m] );
							
						var regex = new RegExp(opt.disDate[m]),
						    tmpre = opt.enDate ? !regex.test(curDateStr) : regex.test(curDateStr);
						
						if( tmpre ) break;
					}
						
					if( tmpre )
						that._setCell( cell, arrDate[n] );
					else
						that._setCell( cell, arrDate[n], true );
				}
				else
					that._setCell( cell, arrDate[n], true );
				
				row.appendChild( cell ); n++;
			}
			frag.appendChild( row );
		}
		
		while( DOM.db[0].firstChild )
		    DOM.db[0].removeChild( DOM.db[0].firstChild );
			
		DOM.db[0].appendChild(frag);
		
		return that;
	},
	
	_setCell: function( cell, num, enabled )
	{
		if( enabled )
		{
			cell.innerHTML = '<a href="javascript:void(0);">' + num + '</a>';
			cell.firstChild[expando+'D'] = num + '';
			
			if( num === this.day )
			    $(cell).addClass('cui_today');
		}
		else
			cell.innerHTML = num + '';
	},
	
	_drawList: function( val, arr )
	{
		var DOM = this.DOM, row, cell,
		    frag = document.createDocumentFragment();
			
		for( var i = 0; i < 4; i++ )
		{
		    row = document.createElement('tr');
			for( var j = 0; j < 3; j++ )
			{
				cell = document.createElement('td');
				cell.innerHTML = '<a href="javascript:void(0);">' + (arr?arr[val]:val) + '</a>';
				row.appendChild(cell);
				
				if( arr )
				    cell.firstChild[expando+'M'] = val;
				else
				    cell.firstChild[expando+'Y'] = val;
					
				val++;
			}
			frag.appendChild(row);
		}
		
		while( DOM.lbox[0].firstChild )
		    DOM.lbox[0].removeChild( DOM.lbox[0].firstChild );
		
		DOM.lbox[0].appendChild(frag);
	    
		return this;
	},
	
	_showList: function()
	{
	    this.DOM.ymlist[0].style.display = 'block';
	},
	
	_hideList: function()
	{
	    this.DOM.ymlist[0].style.display = 'none';
	},
	
	_offset: function()
	{
	    var that = this, DOM = that.DOM, ltop,
		    inpP = $(that.evObj).offset(),
		    inpY = inpP.top + that.evObj.offsetHeight,
			ww = _$window.width(),
			wh = _$window.height(),
			dl = _$window.scrollLeft(),
			dt = _$window.scrollTop(),
			cw = DOM.wrap[0].offsetWidth,
			ch = DOM.wrap[0].offsetHeight;
		
		if( inpY + ch > wh + dt )
			inpY = inpP.top - ch - 2;
			
		if( inpP.left + cw > ww + dl )
		    inpP.left -= cw;
		
		DOM.wrap.css({ left:inpP.left + 'px', top:inpY + 'px' });
		
		ltop = DOM.im.offset().top + DOM.im[0].offsetHeight;
		DOM.ymlist[0].style.top = ltop - inpY + 'px';
		
		return that;
	},
	
	_getDOM: function()
	{
	    var wrap = document.createElement('div');
		
		wrap.style.cssText = 'position:absolute;display:none;z-index:' + this.config.zIndex + ';';
		wrap.innerHTML = calendarTpl;
		
        var name, i = 0,
			DOM = { wrap: $(wrap) },
			els = wrap.getElementsByTagName('*'),
			len = els.length;
		
		for( ; i < len; i ++ )
		{
			name = els[i].className.split('cui_')[1];
			if(name) DOM[name] = $(els[i]);
		};
		
		document.body.appendChild(wrap);
		
		return DOM;
	},
	
	_getEvent: function()
	{
	    if( _ie ) return window.event;
		
		var func = this._getEvent.caller;
	
	    while( func != null )
	    {
		    var arg = func.arguments[0];
		    if( arg && (arg + '').indexOf('Event') >= 0 ) return arg;
		    func = func.caller;
	    }
		
		return null;
	},
	
	_setDate: function( day )
	{
	    day = parseInt( day, 10 );
		
		var that = this, opt = that.config, DOM = that.DOM,
		    tmpDate = new Date( that.year, that.month-1, day );
		
		if( opt.format.indexOf('H') >= 0 )
		{
		    var hourVal = parseInt( DOM.hour[0].value, 10 ),
			    minuteVal = parseInt( DOM.minute[0].value, 10 ),
			    secondVal = parseInt( DOM.second[0].value, 10 );
			
			tmpDate = new Date(that.year,that.month-1,day,hourVal,minuteVal,secondVal);
		}
		
		that.day = day;
		
		opt.onSetDate && opt.onSetDate.call( that );
		that.inpE.value = dateFormat.call( tmpDate, opt.format );
		
		if( opt.real )
		{
		    var realFormat = opt.format.indexOf('H') >= 0 ? 'yyyy-MM-dd HH:mm:ss' : 'yyyy-MM-dd';
			$(opt.real)[0].value = dateFormat.call(tmpDate,realFormat);
		}
		
		that.hide();
	},
	
	_addEvent: function()
	{
	    var that = this,
		    DOM = that.DOM;
		
		DOM.wrap.bind('click',function(evt){
		    var target = evt.target;
			
			if( target[expando+'D'] )
			    that._setDate( target[expando+'D'] );
			else if( target === DOM.pm[0] )
				that._draw( new Date(that.year, that.month - 2), that.day );
			else if( target === DOM.nm[0] )
			    that._draw( new Date(that.year, that.month), that.day );
			else if( target === DOM.py[0] )
			    that._draw( new Date(that.year - 1, that.month - 1), that.day );
			else if( target === DOM.ny[0] )
			    that._draw( new Date(that.year + 1, that.month - 1), that.day );
			else if( target === DOM.tbtn[0] )
			{
			    var today = new Date();
				that.year = today.getFullYear();
				that.month = today.getMonth() + 1;
				that.day = today.getDate();
				that._setDate( that.day );
			}
			else if( target === DOM.dbtn[0] )
			{
			    var config = that.config;
				
				if( config.onSetDate )
				{
				    that.year = '';
					that.month = '';
					that.day = '';
					config.onSetDate.call( that );
				}
				
				that.inpE.value = '';
				that.hide();
				
				if( config.real )
				    $(config.real)[0].value = '';
			}
			else if( target === DOM.im[0] )
			{
				var marr = ['01','02','03','04','05','06','07','08','09','10','11','12'],
				    x = DOM.im.offset().left - parseInt(DOM.wrap[0].style.left,10);
			    
				DOM.im[0].select();
				DOM.ybar[0].style.display = 'none';
				DOM.ymlist[0].style.left = x + 'px';
				that._drawList(0, marr)._showList();
				
				return false;
			}
			else if( target === DOM.iy[0] )
			{
				var x = DOM.iy.offset().left - parseInt(DOM.wrap[0].style.left,10);
			    
				DOM.iy[0].select();
				DOM.ybar[0].style.display = '';
				DOM.ymlist[0].style.left = x + 'px';
				that._drawList(that.year - 4)._showList();
				
				return false;
			}
			else
			{
				var today = new Date(),
				    m = DOM.im[0].value || today.getMonth() + 1,
					y = DOM.iy[0].value || today.getFullYear();
				that._draw( new Date(y,m-1), that.day );
			}
			
			that._hideList();
			
			return false;
		});
		
		DOM.ymlist.bind('click',function(evt){
		    var target = evt.target;
;			
			if( target[expando+'M'] >= 0 )
			{
			    DOM.im[0].value = target[expando+'M'] + 1;
				that._draw( new Date(that.year, target[expando+'M']), that.day )._hideList();
			}
			else if( target[expando+'Y'] )
			{
			    DOM.iy[0].value = target[expando+'Y'];
				that._draw( new Date(target[expando+'Y'], that.month-1), that.day )._hideList();
			}
			else if( target === DOM.pybar[0] )
			{
				var p = $('a',DOM.lbox[0])[0][expando+'Y'];
				that._drawList( p - 12 );
			}
			else if( target === DOM.nybar[0] )
			{
			    var p = $('a',DOM.lbox[0])[0][expando+'Y'];
				that._drawList( p + 12 );
			}
			else if( target === DOM.cybar[0] )
			    that._hideList();
			
			return false;
		});
		
		DOM.im.bind('keypress',isDigit);
		DOM.iy.bind('keypress',isDigit);
		DOM.hour.bind('keypress',isDigit);
		DOM.minute.bind('keypress',isDigit);
		DOM.second.bind('keypress',isDigit);
		
		$(document).bind('click',function(evt){
			if( evt.target !== that.evObj )
				that.hide()._hideList();
		});
	},
	
	show: function()
	{
	    this.DOM.wrap[0].style.display = 'block';
		return this;
	},
	
	hide: function()
	{
	    this.DOM.wrap[0].style.display = 'none';
		return this;
	},
	
	getDate: function( type )
	{
	    var that = this, DOM = that.DOM,
		    h = parseInt( DOM.hour[0].value, 10 ),
		    m = parseInt( DOM.minute[0].value, 10 ),
			s = parseInt( DOM.second[0].value, 10 );
		
		if( that.year === '' && that.month === '' && that.day === '' )
		    return '';
			
		switch( type )
		{
		    case 'y': return that.year;
			case 'M': return that.month;
			case 'd': return that.day;
			case 'H': return h;
			case 'm': return m;
			case 's': return s;
			case 'date': return ( that.year + '-' + that.month + '-' + that.day );
			case 'dateTime': return ( that.year + '-' + that.month + '-' + that.day + ' ' + h + ':' + m + ':' + s );
		};
	}
};

lhgcalendar.fn._init.prototype = lhgcalendar.fn;

lhgcalendar.formatDate = function( date, format )
{
	return dateFormat.call( date, format );
};

lhgcalendar.setting =
{
    id: null,
	format: 'yyyy-MM-dd',
	minDate: null,
	maxDate: null,
	btnBar: true,
	targetFormat: null,
	disWeek: null,
	onSetDate: null,
	real: null,
	disDate: null,
	enDate: false,
	zIndex: 1978,
	noToday: false,
	linkageObj: null
};

$.fn.calendar = function( config, event )
{
	event = event || 'click';
	
	this.bind(event, function(){
		lhgcalendar( config );
		return false;
	});
	
	return this;
};

window.lhgcalendar = $.calendar = lhgcalendar;

})( this.jQuery||this.lhgcore, this );