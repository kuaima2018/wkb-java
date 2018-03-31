$(function() {
	$(":button[bean]").click(function() {
		var date = $.trim($("#date").val());
		if (date == "") {
			// alert("请输入任务日期");
			$("#date").parent().removeClass("has-success");
			$("#date").parent().addClass("has-error");
			$("#date").attr("placeholder", "请输入任务日期");
			return false;
		} else {
			$("#date").parent().removeClass("has-error");
			$("#date").parent().addClass("has-success");
		}
		var bean = $(this).attr("bean");
		var activityNumber = $(":radio[checked]").val();
		var context = $("#contextPathURL").val();
		var url = context + bean + "&date=" + date;
		if ("extractMarketMidFlow" === bean) {
			if (activityNumber === undefined) {
				alert("请选择营销活动！")
				return false;
			} else {
				url += "&activityNumber=" + activityNumber
			}
		}
		if ("extractMarketResFlow" === bean) {
			if (activityNumber === undefined) {
				alert("请选择营销活动！")
				return false;
			} else {
				url += "&activityNumber=" + activityNumber
			}
		}
		if (!confirm("确定拉起任务吗?")) {
			return false;
		}
		$(this).hide();
		$(this).siblings().show();

		var $this = $(this);
		$this.siblings().find(".progress-bar").css("width", "60%")
		$.post(url, {}, function(data) {
			$this.siblings().find(".progress-bar").css("width", "100%")
			$this.siblings().hide();
			$this.show();
			alert(data);
		});
	});
	$(".rule-detail").hide();
	$(".look-rule").each(function() {
		$(this).toggle(function() {
			$(this).siblings(".rule-detail").show();
		}, function() {
			$(this).siblings(".rule-detail").hide();
		});
	})
});