var sheets = new Array();
var currentSheet = 1;
var timer;
var timerStatus = true;// 定时器状态
var isLoad = false;
linkTo(currentSheet);
$(".excel-tab-title>li:first").addClass("first")
$(".excel-tab-title li").not(".active").find("a").hover(function() {
	$(".excel-tab-title li a").removeClass("hover");
	$(this).addClass("hover");
})
$(window).resize(function(e) {
	var w = document.documentElement.clientWidth || window.innerWidth;
	var h = document.documentElement.clientHeight || window.innerHeight;
	document.getElementById("ctnDiv").style.width = w + "px";
	document.getElementById("ctnDiv").style.height = (h - 41) + "px";
	if (isLoad) {
		winChange();
	}

}).resize();

function linkTo(id) {
	$("#ctnDiv").html("");
	currentSheet = id;
	isLoad = false;
	$(".excel-tab-title li").removeClass("active").eq("" + id - 1 + "")
			.addClass("active");
	if (sheets[id]) {
		$("#loading").show();
		var data = sheets[id];
		$("#ctnDiv").html("" + sheets[id] + "");
		winChange();
		isLoad = true;
		$("#loading").fadeOut(500);
		clearInterval(timer);
		timerStatus = true;
		initData();
	} else {
		$("#loading").show();
		$.ajax({
					url : "sheet" + id + ".html",
					data : {},
					type : "head",
					async : false,
					success : function(data) {
						$("#ctnDiv").load("sheet" + id + ".html",
								function(data) {
									sheets[id] = data;
									winChange();
									isLoad = true;
									$("#loading").fadeOut(500);
									clearInterval(timer);
									timerStatus = true;
								})
					},
					error : function(data) {
						$("#loading").fadeOut(500);
						$("#ctnDiv").html("<span style='font-size:40px;line-height:40px;'>正在转换...</span>");
						console.clear();
						if (timerStatus) {
							timer = setInterval("linkTo('" + currentSheet
									+ "')", 5000);
							timerStatus = false;
						}
					}

				});
		setTimeout(function() {
			initData();
		}, 100)
	}

}

function winChange() {
	var w = document.documentElement.clientWidth || window.innerWidth;
	var h = document.documentElement.clientHeight || window.innerHeight;
	var left = document.getElementById("divLeft").clientWidth;
	document.getElementById("ctnDiv").style.width = w + "px";
	document.getElementById("divRight").style.width = (w - left - 2) + "px";
	document.getElementById("t_r_content").style.height = (h - 62) + "px";
	document.getElementById("vertical_axis").style.height = (h - 80) + "px";
	document.getElementById("t_r_t").style.width = (w - left - 4) + "px";
	document.getElementById("t_r_content").style.width = (w - left - 2) + "px";
}
function toscroll() {
	var a = document.getElementById("t_r_content").scrollTop;
	var b = document.getElementById("t_r_content").scrollLeft;
	document.getElementById("vertical_axis").scrollTop = a;
	document.getElementById("t_r_t").scrollLeft = b;
}
function move(direction) {
	if (direction == 0) {
		for (var i = $(".excel-tab-title li").length - 1; i > -1; i--) {
			if ($(".excel-tab-title li:eq(" + i + ")").css("display") == "none") {
				$(".excel-tab-title li:eq(" + i + ")").css("display", "block");
				break;
			}

		}
	} else {
		if (document.getElementById("excel-tab-title").scrollHeight > 42) {
			for (var i = 0; i < $(".excel-tab-title li").length; i++) {
				if ($(".excel-tab-title li:eq(" + i + ")").css("display") != "none") {
					$(".excel-tab-title li:eq(" + i + ")").css("display",
							"none");
					break;
				}

			}
		}
	}
}

function stepMove(direction) {
	if (direction == 0) {
		$(".excel-tab-title li").css("display", "block");
	} else {
		for (var i = 0; i < $(".excel-tab-title li").length; i++) {
			if ($(".excel-tab-title li:eq(" + i + ")").css("display") != "none"
					&& document.getElementById("excel-tab-title").scrollHeight > 42) {
				$(".excel-tab-title li:eq(" + i + ")").css("display", "none");

			}
		}

	}
}