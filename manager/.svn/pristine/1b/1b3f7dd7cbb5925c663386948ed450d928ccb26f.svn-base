$(function() {
	var oTable;
	$("#DataTables_Table_0_length").addClass("col-md-3");

	$("#DataTables_Table_0_length")
			.before(
					"<div class='col-md-5'><button class='btn btn-primary'>增加</button><button class='btn btn-primary col-md-offset-2'>删除</button></div>");
	$("#DataTables_Table_0_filter").addClass("col-md-4");
	$("#DataTables_Table_0_info").addClass("col-md-6");
	//$("#DataTables_Table_0_paginate").addClass("col-md-6");
	//$("#DataTables_Table_0_paginate").css("text-align", "right");
	$("#postTable tbody tr").click(function(e) {
		if ($(this).hasClass('row_selected')) {
			$(this).removeClass('row_selected');
		} else {
			oTable.$('tr.row_selected').removeClass('row_selected');
			$(this).addClass('row_selected');
		}
	});
	function fnGetSelected(oTableLocal) {
		return oTableLocal.$('tr.row_selected');
	}

	oTable = $('#postTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"iDisplayLength" : 10,
		"bPaginate" : true,
		"oLanguage" : {
			"sLengthMenu" : "每页显示 _MENU_ 条记录",
			"sZeroRecords" : "抱歉， 没有找到",
			"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
			"sInfoEmpty" : "没有数据",
			"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "前一页",
				"sNext" : "后一页",
				"sLast" : "尾页"
			}
		}
	});
	
	$("#dialog-message").dialog({
		autoOpen : false,
		height : 300,
		width : 400,
		modal : true,
		close : function() {
			if($("#status").val() == 1) {
				window.location.reload();
			}
		}
	});
});

function updateTips(tp, t) {
	tp.text(t).addClass("ui-state-highlight");
	setTimeout(function() {
		tp.removeClass("ui-state-highlight", 100);
	}, 500);
}

function checkLength(tip, o, n, min, max) {
	if (o.val().length > max || o.val().length < min) {
		o.addClass("ui-state-error");
		updateTips(tip, "Length of " + n + " must be between " + min
				+ " and " + max + ".");
		return false;
	} else {
		return true;
	}
}