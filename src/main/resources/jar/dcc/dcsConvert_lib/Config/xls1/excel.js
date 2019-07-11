var param = window.location.search;
var hideSheet = param.substring(param.lastIndexOf("hideSheet=") + 1);
var pathname = window.location.pathname
var json_begin = pathname.substring(pathname.lastIndexOf("sheet"), pathname.lastIndexOf(".html"));
var json_originalIndex = 1;
var scrollElement = $("#t_r_content");
var scrollTable = $(".tab-content table");
var canReq = false;
var onload_tr = 100;
var scrollX = [];
var scrollY = [];
var thisTd = $("#t_r_content .tab-content table").not(".nested").find("tbody>tr>td")
for (var i = 0; i < hideSheet; i++) {
	$("#excel-tab-title li:eq(" + i + ")").css("display", "none");
}
$("body").append('<input type="hidden" id="scale" value="1"/>')
$(".excel-tab-title>li:first").addClass("first")
$(".excel-tab-title li").not(".active").find("a").hover(function () {
	$(".excel-tab-title li a").removeClass("hover");
	$(this).addClass("hover");
})
thisTd.click(function(){
	thisTd.removeClass("msdr")
	$(this).addClass("msdr");
	$(".layer .postil").hide();
	if($(this).attr("postilId")){
		var tpi = $(this).attr("postilId");
		//批注位置原先一直不对,现在动态获取鼠标点击的td位置,然后进行计算
		$("#postil"+tpi).css({"top":$(this).offset().top + 10,"left":$(this).offset().left+$(this).width() + 20})
		$(".layer").find("#postil"+tpi).show();
	}
})
window.onload = function () {
	winChange();
}
window.onresize = function () {
	winChange()
};
initScrollballData();
function initScrollballData(){
	$("#tbCol thead th").each(function(index,e){
		scrollX[index] = $(this).width() + 2;
	})
	$("#tbRaw tbody tr").each(function(index,e){
		scrollY[index] = $(this).height();
	})
	
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
	try{
		if(eval(json_begin + "_1_json"))
			canReq = true;
	}catch(e){
		canReq = false;
	}
}
function toscroll() {
	var a = document.getElementById("t_r_content").scrollTop;
	var b = document.getElementById("t_r_content").scrollLeft;
	document.getElementById("vertical_axis").scrollTop = a;
	document.getElementById("t_r_t").scrollLeft = b;
	$(".layer .postil").hide();
	showMore();
}

function move(direction) {
	if (direction == 0) {
		for (var i = $(".excel-tab-title li").length - 1; i > -1; i--) {
			if ($(".excel-tab-title li:eq(" + i + ")").css("display") == "none") {
				$(".excel-tab-title li:eq(" + i + ")").css("display", "block");
				break;
			}

		}
	}
	else {
		if (document.getElementById("excel-tab-title").scrollHeight > 42) {
			for (var i = 0; i < $(".excel-tab-title li").length; i++) {
				if ($(".excel-tab-title li:eq(" + i + ")").css("display") != "none") {
					$(".excel-tab-title li:eq(" + i + ")").css("display", "none");
					break;
				}

			}
		}
	}
}

function stepMove(direction) {
	if (direction == 0) {
		$(".excel-tab-title li").css("display", "block");
	}
	else {
		for (var i = 0; i < $(".excel-tab-title li").length; i++) {
			if ($(".excel-tab-title li:eq(" + i + ")").css("display") != "none"
				&& document.getElementById("excel-tab-title").scrollHeight > 42) {
				$(".excel-tab-title li:eq(" + i + ")").css("display", "none");

			}
		}

	}
}
function linkTo(id) {
	var hideNum = $("#excel-tab-title li:hidden").length;
	var hrefParam = location.search.indexOf("watermark_txt") == -1 ? 
		"?hideSheet=" + hideNum : location.search.indexOf("hideSheet") == -1 ? 
		location.search + "&hideSheet=" + hideNum : location.search;
	var href = "sheet" + id + ".html" + hrefParam;
	location.href = href;
}
function showMore() {

	if (scrollElement.scrollTop() + scrollElement.height() > scrollTable.height() * parseFloat($("#scale").val()) * 0.95) {
		if (canReq) {
			var currentData = eval(json_begin + "_" + json_originalIndex + "_json");
			json_originalIndex++;
			scrollTable.append("" + currentData.data + "");
			if(currentData.result == 0){

			}else{
				canReq = false;
			}
			// $.ajax({
			// 	url: json_begin + "_" + json_originalIndex + ".json",
			// 	data: {},
			// 	dataType: "json",
			// 	async: true,
			// 	type: "post",
			// 	beforeSend: function () {
			// 		$("#loading").css({ "left": "" + (document.documentElement.clientWidth / 2 - 50) + "px", "display": "block" })
			// 	},
			// 	success: function (data) {
					
			// 		canReq = true;

			// 		scrollTable.append("" + data.data + "");
			// 		if (data.result == 0) {
			// 		} else {
						
			// 		}
			// 	},
			// 	error: function (data) {
			// 		alert("网络错误")
			// 	}
			// });
		}
	}
}

