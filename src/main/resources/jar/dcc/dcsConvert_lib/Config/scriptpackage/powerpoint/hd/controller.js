var isPhone = false;
var sUserAgent = navigator.userAgent.toLowerCase();
var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
var bIsMidp = sUserAgent.match(/midp/i) == "midp";
var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
var bIsAndroid = sUserAgent.match(/android/i) == "android";
var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
if ((bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
	isPhone = true;
}
(function () {
	isPhone ? (LoadRs("css", basePath + "/style_mobile.css"), LoadRs("js", basePath + "/word.js")) : (LoadRs("css", basePath + "/style.css"), LoadRs("js", basePath + "/ppt_hd.js"))
})()
$(window).resize(function () {
	var mar = ($(window).width() - 365) / 2
	$('#header p').css('margin-left', mar)
})

function LoadRs(type, filePath) {
	var p = type == "js" ? document.body : document.getElementsByTagName("head")[0];
	var o = type == "js" ? document.createElement("script") : document.createElement("link");
	type == "js" ? (o.type = "text/javascript", o.src = filePath) : (o.type = "text/css", o.rel = "stylesheet", o.href = filePath);
	p.appendChild(o);
}
if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
	$('.btnMore').css("opacity", 1)
} else {
	$('#header').remove()
}