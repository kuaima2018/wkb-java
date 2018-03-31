$(function() {

	var companyId = $("#companyId");
	var name = $("#name");
	var contactor = $("#contactor");
	var mobilePhone = $("#mobilePhone");
	var telephone = $("#telephone");
	var fax = $("#fax");
	var email = $("#email");
	var addr = $("#addr");
	var zipcode = $("#zipcode");
	var allFields = $([]).add(companyId).add(name).add(contactor).add(
			mobilePhone).add(telephone).add(fax).add(email).add(addr).add(
			zipcode);
	var tips = $(".validateTips");

	var contextPath = $("#contextPath").val();

	function addCompany(company) {
		var num = $("#postTable tbody tr").size();
		var trClass = "even";
		if (num % 2 == 0) {
			trClass = "odd";
		}
		$("#postTable tbody").append(
				"<tr class='" + trClass + "'><td>" + company.id + "</td><td>"
						+ company.companyId + "</td><td>" + company.name
						+ "</td><td>" + company.contactor + "</td><td>"
						+ company.mobilePhone + "</td><td>" + company.telephone
						+ "</td><td>" + company.fax + "</td><td>"
						+ company.email + "</td><td>" + company.addr
						+ "</td><td>" + company.zipcode + "</td></tr>");
	}

	$("#add").click(function() {
		$("#dialog-form").dialog("open");
	});

	$("#dialog-form").dialog({
		autoOpen : false,
		height : 600,
		width : 600,
		modal : true,
		buttons : {
			"创建" : function() {
				var bValid = true;

				allFields.removeClass("ui-state-error");

				bValid = bValid && checkLength(tips, name, "公司名称", 2, 16);
				// bValid = bValid && checkLength(tips, postId, "岗位代码", 1, 11);
				// bValid = bValid && checkLength(tips, orgId, "组织代码", 1, 11);
				if (bValid) {

					var company = {
						"companyId" : companyId.val(),
						"name" : name.val(),
						"contactor" : contactor.val(),
						"mobilePhone" : mobilePhone.val(),
						"telephone" : telephone.val(),
						"fax" : fax.val(),
						"email" : email.val(),
						"addr" : addr.val(),
						"zipcode" : zipcode.val()
					};
					var companys = JSON.stringify([ company ]);
					$.ajax({
						type : "post", // 请求方式
						url : contextPath + "/admin/company/add", // url地址
						data : companys, // 数据
						contentType : "application/json",
						dataType : "json",
						success : function(data) {
							$(data).each(function() {
								if (this.id == 0) {
									this.id = "插入失败！";
									addCompany(this);
								} else {
									window.location.reload();
								}

							});
						},
						error : function() {

							var company = {
								id : "插入失败",
								companyId : companyId.val(),
								name : name.val(),
								contactor : contactor.val(),
								mobilePhone : mobilePhone.val(),
								telephone : telephone.val(),
								fax : fax.val(),
								email : email.val(),
								addr : addr.val(),
								zipcode : zipcode.val()
							}
							addCompany(company);
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