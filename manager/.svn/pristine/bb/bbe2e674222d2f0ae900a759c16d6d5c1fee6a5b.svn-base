$(function() {

	var companyIdUpdate = $("#companyIdUpdate");
	var nameUpdate = $("#nameUpdate");
	var contactorUpdate = $("#contactorUpdate");
	var mobilePhoneUpdate = $("#mobilePhoneUpdate");
	var telephoneUpdate = $("#telephoneUpdate");
	var faxUpdate = $("#faxUpdate");
	var emailUpdate = $("#emailUpdate");
	var addrUpdate = $("#addrUpdate");
	var zipcodeUpdate = $("#zipcodeUpdate");
	var allFieldsUpdate = $([]).add(companyIdUpdate).add(nameUpdate).add(
			contactorUpdate).add(mobilePhoneUpdate).add(telephoneUpdate).add(
			faxUpdate).add(emailUpdate).add(addrUpdate).add(zipcodeUpdate);
	var tipsUpdate = $(".validateTipsUpdate");

	var contextPath = $("#contextPath").val();
	$("#dialog-form-update").dialog(
			{
				autoOpen : false,
				height : 600,
				width : 600,
				modal : true,
				buttons : {
					"更新" : function() {
						var bValid = true;
						allFieldsUpdate.removeClass("ui-state-error");
						bValid = bValid
								&& checkLength(tipsUpdate, nameUpdate, "公司名称",
										2, 16);

						if (bValid) {
							var selectedNode = $(".row_selected td");

							var company = {
								"id" : selectedNode.first().text(),
								"companyId" : companyIdUpdate.val(),
								"name" : nameUpdate.val(),
								"contactor" : contactorUpdate.val(),
								"mobilePhone" : mobilePhoneUpdate.val(),
								"telephone" : telephoneUpdate.val(),
								"fax" : faxUpdate.val(),
								"email" : emailUpdate.val(),
								"addr" : addrUpdate.val(),
								"zipcode" : zipcodeUpdate.val()
							};
							var companys = JSON.stringify(company);
							$.ajax({
								type : "post", // 请求方式
								url : contextPath + "/admin/company/update", // url地址
								data : companys, // 数据
								contentType : "application/json",
								dataType : "json",
								success : function(data) {
									if (data == true) {

										window.location.reload();
									} else {
										$("#dialog-message").html(
												this.id + "更新失败！");
										$("#dialog-message").dialog("open");
									}
								},
								error : function() {
									$("#dialog-message").html("更新失败！");
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
					allFieldsUpdate.val("").removeClass("ui-state-error");
				}
			});

	$("#edit").click(function() {
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

		$("#companyIdUpdate").val(nodeValue[1]);
		$("#nameUpdate").val(nodeValue[2]);
		$("#contactorUpdate").val(nodeValue[3]);
		$("#mobilePhoneUpdate").val(nodeValue[4]);
		$("#telephoneUpdate").val(nodeValue[5]);
		$("#faxUpdate").val(nodeValue[6]);
		$("#emailUpdate").val(nodeValue[7]);
		$("#addrUpdate").val(nodeValue[8]);
		$("#zipcodeUpdate").val(nodeValue[9]);
		$("#dialog-form-update").dialog("open");
	});
});