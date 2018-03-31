$(function() {
	$("#del").click(function() {
		var selectedPostId = $(".row_selected td").first().text();
		var selectedNode = $(".row_selected");
		if (selectedNode.length == 0) {
			$("#dialog-message").html("请点击选择行！");
			$("#dialog-message").dialog("open");
			return;
		}
		var contextPath = $("#contextPath").val();

		$.ajax({
			type : "get", // 请求方式  
			url : contextPath + "/admin/company/delete/" + selectedPostId, //url地址  
			contentType : "application/json",
			dataType : "json",
			success : function(data) {
				if (data == true) {
					window.location.reload();
				} else {
					$("#dialog-message").html("删除失败！");
					$("#dialog-message").dialog("open");
				}
			},
			error : function() {
				$("#dialog-message").html("删除失败！");
				$("#dialog-message").dialog("open");
			}
		});
	});
});