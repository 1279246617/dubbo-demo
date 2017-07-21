	/**
	 * 表格工具类-扩展的各种方法
	 * 需要先引入：mm-base.js，mm-table.js
	 * version：3.3
	 * LastDate: 2017-07-10
	 * 
	 * 这里把表格各种方法用原型的方式绑定，单独出来方便扩展。
	 * 
	 * 初始化table容器 初始化容器中的各个对象元素到对象，后期使用
	 * 包含：fixed-table-toolbar（头部按钮）和fixed-table-container（表格体）
	 * fixed-table-container 设置了这个展示页的全部高度，包含表头，表体，表尾，翻页
	 * 包含：表头（fixed-table-header），表体（fixed-table-body），表尾（fixed-table-footer），翻页（fixed-table-pagination）
	 * 
	 */

	//初始化
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
	
	//初始化---表格骨架
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

	//初始化---表格内容
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

	//初始化---表格头
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

	//初始化---表格尾部
	mmuiTable.prototype.initFooter = function() {
		if (!this.options.showFooter || this.options.cardView) {
			this.$tableFooter.hide();
		} else {
			this.$tableFooter.show();
		}
	};

	//初始化---填充数据
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

	//初始化---排序（暂时不用）
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

	//初始化---工具条（暂时不用）
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

	//初始化---查询
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
	
	//初始化---表格条目骨架
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

	//初始化---根据条件去服务器加载数据
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

				that.load(res);// 加载服务器数据到表格展示

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

	//初始化---工具条的查询（没有用）
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

	//初始化---底部翻页
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

	//表格对象事件---排序（暂时不用）
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
	
	//表格对象事件
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

	//表格对象事件---翻页事件
	mmuiTable.prototype.onPageListChange = function(event) {
		var $this = $(event.currentTarget);

		$this.parent().addClass('active').siblings().removeClass('active');
		this.options.pageSize = $this.text().toUpperCase() === this.options.formatAllRows().toUpperCase() ? this.options.formatAllRows() : +$this
				.text();
		this.$toolbar.find('.page-size').text(this.options.pageSize);

		this.updatePagination(event);
	};

	//表格对象事件---翻页---第一页
	mmuiTable.prototype.onPageFirst = function(event) {
		this.options.pageNumber = 1;
		this.updatePagination(event);
	};

	//表格对象事件---翻页---下一页
	mmuiTable.prototype.onPagePre = function(event) {
		if ((this.options.pageNumber - 1) == 0) {
			this.options.pageNumber = this.options.totalPages;
		} else {
			this.options.pageNumber--;
		}
		this.updatePagination(event);
	};

	//表格对象事件---翻页---上一页
	mmuiTable.prototype.onPageNext = function(event) {
		if ((this.options.pageNumber + 1) > this.options.totalPages) {
			this.options.pageNumber = 1;
		} else {
			this.options.pageNumber++;
		}
		this.updatePagination(event);
	};

	//表格对象事件---翻页---最后一页
	mmuiTable.prototype.onPageLast = function(event) {
		this.options.pageNumber = this.totalPages;
		this.updatePagination(event);
	};

	//表格对象事件---翻页---根据页号
	mmuiTable.prototype.onPageNumber = function(event) {
		if (this.options.pageNumber === +$(event.currentTarget).text()) {
			return;
		}
		this.options.pageNumber = +$(event.currentTarget).text();
		this.updatePagination(event);
	};

	//表格对象事件---刷新，查询
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
	 * 具体用传过来的name+'.bs.table'转为事件全名，
	 * 在mmuiTable.EVENTS[name]查找对应的事件方法执行
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

	//重置头部
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

	//重置底部
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

	//尾部添加数据
	mmuiTable.prototype.append = function(data) {
		this.initData(data, 'append');
		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

	//头部添加数据
	mmuiTable.prototype.prepend = function(data) {
		this.initData(data, 'prepend');
		this.initSearch();
		this.initPagination();
		this.initSort();
		this.initBody(true);
	};

	//根据条件，删除条目数据
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

	//删除所有数据
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
	
	//子表
	mmuiTable.prototype.childTable = function(detail,list,columns) {
        var cur_table = detail.html('<table></table>').find('table');
        $(cur_table).mmuiTable({
        	data:list,
        	pagination:false,
            uniqueId: "id",
            pageSize: 100,
            columns: columns
        });
	};
	