$(function() {

	var comId = $("#comId");
	var id = $("#id");
	var password = $("#password");
	var allFields = $([]).add(comId).add(id).add(password);
	var tipsAdmin = $(".validateTipsAdmin");
	var contextPath = $("#contextPath").val();

	$("#addAdmin").click(function() {
		var selectedNode = $(".row_selected");
		if (selectedNode.length == 0) {
			$("#dialog-message").html("请点击选择行！");
			$("#dialog-message").dialog("open");
			return;
		}
		var nodeValue = [];
		$(".row_selected td").each(function(index) {
			nodeValue[index] = $(this).text();
		});
		$("#comId").val(nodeValue[1]);
		$("#dialog-form-admin").dialog("open");
	});

	$("#dialog-form-admin").dialog({
		autoOpen : false,
		height : 400,
		width : 400,
		modal : true,
		buttons : {
			"创建" : function() {
				var bValid = true;

				allFields.removeClass("ui-state-error");

				bValid = bValid && checkLength(tipsAdmin, id, "公司管理员代码", 2, 16);
				
				// bValid = bValid && checkLength(tips, postId, "岗位代码", 1, 11);
				// bValid = bValid && checkLength(tips, orgId, "组织代码", 1, 11);
				if (bValid) {

					var user = {
						"companyId" : comId.val(),
						"id" : id.val(),
						"password" : password.val()
					};
					var users = JSON.stringify(user);
					$.ajax({
						type : "post", // 请求方式
						url : contextPath + "/admin/user/add", // url地址
						data : users, // 数据
						contentType : "application/json",
						dataType : "json",
						success : function(data) {
							$(data).each(function() {
								if (this.id == 0) {
									$("#dialog-message").html("创建失败！");
									$("#dialog-message").dialog("open");
								} else {
									$("#dialog-message").html("创建成功！请至公司管理员列表页查看。");
									$("#dialog-message").dialog("open");
								}

							});
						},
						error : function() {
							$("#dialog-message").html("创建失败！");
							$("#dialog-message").dialog("open");
						}
					});

					$(this).dialog("close");
				}
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			allFields.val("").removeClass("ui-state-error");
		}
	});
});