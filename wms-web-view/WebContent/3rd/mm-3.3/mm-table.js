/**
 * 表格工具类
 * 需要先引入：mm-base.js
 * version：3.3
 * LastDate: 2017-07-10
 */
!function(window, jQuery) {
	"use strict";
	if(window.mmuiTable)return ;
	
	var $ = jQuery,
	win = $(window);
	
	/**************定义mmuitable表格对象的各种字段，参数，方法，事件**********************/
	
	var mmuiTable = function(el, options) {
		this.options = options;
		this.$el = $(el);
		this.$el_ = this.$el.clone();
		this.timeoutId_ = 0;
		this.timeoutFooter_ = 0;
		this.init();
	};

	/**
	 * 定义mmuitable对象拥有的参数，方法
	 */
	mmuiTable.DEFAULTS = {
		classes : 'table table-hover',
		locale : undefined,
		height : undefined,
		undefinedText : '-',
		sortName : undefined,
		sortOrder : 'desc',
		striped : true,
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
	 * 定义表格展示时列表columns中的每个字段的参数
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

	/**
	 * 定义mmuiTable对象的事件，这里把事件跟字符名称绑定，方便支持无js使用
	 */
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

	/**
	 * 定义表格中列表的操作用到的事件
	 */
	var allowedMethods = [ 'getOptions', 'getSelections', 'getAllSelections', 'getData', 'load', 'append', 'prepend', 'remove', 'removeAll',
			'insertRow', 'updateRow', 'updateCell', 'updateByUniqueId', 'removeByUniqueId', 'getRowByUniqueId', 'showRow', 'hideRow',
			'getRowsHidden', 'mergeCells', 'checkAll', 'uncheckAll', 'check', 'uncheck', 'checkBy', 'uncheckBy', 'refresh', 'resetView',
			'resetWidth', 'destroy', 'showLoading', 'hideLoading', 'showColumn', 'hideColumn', 'getHiddenColumns', 'filterBy', 'scrollTo',
			'getScrollPosition', 'selectPage', 'prevPage', 'nextPage', 'togglePagination', 'toggleView', 'refreshOptions', 'resetSearch',
			'expandRow', 'collapseRow', 'expandAllRows', 'collapseAllRows', 'updateFormatText','childTable'];

	
	/******************************把表格工具作为jquery的插件使用********************************/

	
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
	
	/*************************************定义页面表格工具****************************************/
	
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
	
		//撑起表格页面高度
		var getHeight=function(){
			var th = that.option.toolform.height();
			var height = $(window.parent).height();
			height = height - th - 80;
			if (height < 200) {
				height = 200;
			}
			return height;
		}	
		
		//处理表单数据---进一步查询
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
		
		//主方法---表格创建
		that.option.listBody.mmuiTable(tablePara);
	};
	
	//列表查询点击
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
	
	window.mmList = function(option){return new mmList(option)};
	window.mmuiTable = mmuiTable;
	
	$(function(){
		//初始化表格---非js调用的表格
		$('[data-toggle="table"]').mmuiTable();
	})
	
}(window,jQuery)