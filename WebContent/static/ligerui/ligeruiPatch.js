function btnSearch(formId,grid){
	grid.options.parms=[]; //每次做查询的时候需清空下，否则数组里面元素一直累加
	var x=$(formId).serializeArray();
	$.each(x, function(i, field){
		grid.options.parms.push({name:field.name,value:field.value});
	});
	grid.loadData("");
}