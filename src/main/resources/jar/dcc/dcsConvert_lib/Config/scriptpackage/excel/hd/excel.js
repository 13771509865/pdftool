//
var sUserAgent = navigator.userAgent.toLowerCase();
var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
var bIsMidp = sUserAgent.match(/midp/i) == "midp";
var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
var bIsAndroid = sUserAgent.match(/android/i) == "android";
var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
//end
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
var thisTd = $("#t_r_content .tab-content table").not(".nested").find("tbody>tr>td");
var headerHeight = $("#header").is(':visible') ? $("#header").height() : 0;
var footerHeight = $("#footer").is(':visible') ? $("#footer").height() : 0;
$.each(authority, function (index, ele) {
	var html = '';
	if (ele["isShow"]) {
		if (index==3) {
			html += '<li onClick="showAttribute()"><img src="'+ele.image+'"><span>' + ele.title + '</span></li>'
		}else if(index==2){
            html += '<li id="fullScreen" onClick="toggleFullScreen()"><img src="'+ele.image+'"><span>' + ele.title + '</span></li>'
		}else if (index == 1) {
			html += '<li><a style="text-decoration:none; color:#373737; font-size: 12px; font-weight: 400;" href="'+ele.url+'"  ><img src="' + ele.image + '"/><span>' + ele.title + '</span></a></li>'
		}else{
			html += '<li><img src="'+ele.image+'"><span>' + ele.title + '</span></li>'
		}
		$('.more-content_btn').append(html)
	}
})
$.each(docAttribute,function(index,ele){
	var html='';
	html+='<p><span>'+ele.title+':</span><span class="attr_detail">'+ele.data+'</span></p>'
	$('.attr_content').append(html)
})
for (var i = 0; i < hideSheet; i++) {
	$("#excel-tab-title li:eq(" + i + ")").css("display", "none");
}
$("body").append('<input type="hidden" id="scale" value="1"/>')
$(".excel-tab-title>li:first").addClass("first")
$(".excel-tab-title li").not(".active").find("a").hover(function () {
	$(".excel-tab-title li a").removeClass("hover");
	$(this).addClass("hover");
})
thisTd.click(function () {
	thisTd.removeClass("msdr")
	$(this).addClass("msdr");
	$(".layer .postil").hide();
	if ($(this).attr("postilId")) {
		var tpi = $(this).attr("postilId");

		$(".layer").find("#postil" + tpi).show();
	}
})
window.onload = function () {
	$(".navbar.navbar-fixed-top").css({
		"bottom": footerHeight + 'px'
	})
	winChange();
	var width= $(window).width()
	var height=$(window).height()
	$('.docMark').width(width)
	$('.attribute').css('margin-top',(height-229)/2)
	$('.docMark').height(height)
}
window.onresize = function () {
	winChange()
	var width= $(window).width()
	var height=$(window).height()
	$('.docMark').width(width)
	$('.attribute').css('margin-top',(height-229)/2)
	$('.docMark').height(height)
};
initScrollballData();
var flg = true;

function showMoreMenu() {
	if (flg) {
		$('.more-content').show()
		flg = false;
	} else {
		$('.more-content').hide()
		flg = true;
	}
}
function initScrollballData() {
	$("#tbCol thead th").each(function (index, e) {
		scrollX[index] = $(this).width() + 2;
	})
	$("#tbRaw tbody tr").each(function (index, e) {
		scrollY[index] = $(this).height();
	})

}

function showAttribute() {
	$('.more-content').hide()
	$('.docMark').show()
}

function offAttribute(){
	$('.docMark').hide()
}

function winChange() {
	var w = document.documentElement.clientWidth || window.innerWidth;
	var h = document.documentElement.clientHeight || window.innerHeight;
	var left = document.getElementById("divLeft").clientWidth;
	document.getElementById("ctnDiv").style.width = w + "px";
	document.getElementById("divRight").style.width = (w - left - 2) + "px";
	document.getElementById("t_r_content").style.height = (h - 58 - headerHeight - footerHeight) + "px";
	document.getElementById("vertical_axis").style.height = (h - 80 - headerHeight - footerHeight) + "px";
	document.getElementById("t_r_t").style.width = (w - left - 4) + "px";
	document.getElementById("t_r_content").style.width = (w - left - 2) + "px";
	try {
		if (eval(json_begin + "_1_json"))
			canReq = true;
	} catch (e) {
		canReq = false;
	}
	if ((bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)){
		$('.navbar .nav').css('background-color','#FFFFFF')
		$('.navbar .nav').css('bottom','5px')
		$('.excel-tab-title').css('padding-left','26px').find('.active').css({'border-right':0,'border-bottom':'2px solid #2A9F65'})
	}else{
		$('#btnMore').show()
		$("#zoom").show()
		$('#excel-tab-title .moveBtn').show()
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
	} else {
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
	} else {
		for (var i = 0; i < $(".excel-tab-title li").length; i++) {
			if ($(".excel-tab-title li:eq(" + i + ")").css("display") != "none" &&
				document.getElementById("excel-tab-title").scrollHeight > 42) {
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
			if (currentData.result == 0) {

			} else {
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

// var away = $(window).width() - 200
// if (away <=200) {
// 	$('#header .title').width(away)
// }
$(window).resize(function () {
	var mar = ($(window).width() - 290) / 2
	$('#header .title').css({
		'margin-left': mar,
		'opacity': 1
	})
})
$(function () {
	var mar = ($(window).width() - 290) / 2
	$('#header .title').css({
		'margin-left': mar,
		'opacity': 1
	})
})

function FullScreen(el) {
    var isFullscreen = document.fullScreen || document.mozFullScreen || document.webkitIsFullScreen;
    if (!isFullscreen) { //进入全屏,多重短路表达式
        (el.requestFullscreen && el.requestFullscreen()) ||
        (el.mozRequestFullScreen && el.mozRequestFullScreen()) ||
		(el.webkitRequestFullscreen && el.webkitRequestFullscreen()) || (el.msRequestFullscreen && el.msRequestFullscreen());
    } else { //退出全屏,三目运算符
        document.exitFullscreen ? document.exitFullscreen() :
        document.mozCancelFullScreen ? document.mozCancelFullScreen() :
		document.webkitExitFullscreen ? document.webkitExitFullscreen() : '';
    }
}

function toggleFullScreen(e) {
    var el = document.getElementsByTagName('html')[0];
	FullScreen(el);
	$('.more-content').hide()
	flg=true;
}