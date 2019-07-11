var sUserAgent = navigator.userAgent.toLowerCase();
var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
var bIsMidp = sUserAgent.match(/midp/i) == "midp";
var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
var bIsAndroid = sUserAgent.match(/android/i) == "android";
var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
 $("#toggleToolbar").click(function(e) {
     $(".secondaryToolbar").toggleClass("show")
 })

 $(function() {
     var appendStrShowFooter = '<div id="zoom">' +
         '<div class="w-scale-btn_small w-scale-shrink " id="shrinkBtn" onclick="changeTab(0)" title="缩小"></div>' +
         '<div class="w-scale-text_small" id="scale_text">100%</div>' +
         '<div class="w-scale-btn_small w-scale-magnify " id="magnifyBtn" onclick="changeTab(1)" title="放大"></div>' +
         '</div>';
     var appendStrHideFooter = '<div id="zoom">' +
         '<div class="w-scale-btn w-scale-small " id="shrinkBtn" onclick="changeTab(0)" title="缩小"></div>' +
         '<div class="w-scale-text" id="scale_text">100%</div>' +
         '<div class="w-scale-btn w-scale-big " id="magnifyBtn" onclick="changeTab(1)" title="放大"></div>' +
         '</div>';
     var rotateStr = '<a class="rightButton" id="rotateLeft" title="旋转" style="float:right;padding: 10px;" href="javascript:;" onclick="rotate(0)">' +
         '<img src="' + basePath + '/rotate_left.png" width="20" height="20">' +
         '</a>' +
         '<a class="rightButton" title="旋转" id="rotateRight" style="float:right;padding: 10px;" href="javascript:;" onclick="rotate(1)"> ' +
         '<img src="' + basePath + '/rotate_right.png" width="20" height="20" >' +
         '</a>';
     //$(".navbar-inner .container-fluid").append(rotateStr)
     if (authority.showFooter){
        $('body').append(appendStrShowFooter);
     }else{
        $('body').append(appendStrHideFooter);
     }
     if ((bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
        $('#zoom').remove()
     }
 });

 function changeTab(type) {
     var size = parseFloat($("#scale").val());
     var previousPage = $(".activePage").val();

     if (type == 1 && size <= 4) {
         size = size + 0.2;
     } else if (type == 0 && size >= 0.4) {
         size = size - 0.2;
     }
     $("#scale").val(size)

     $("#scale_text").html(Math.round(size * 100) + "%")
     isZoom = true;
     renderPage();
     changePage(previousPage)


 }

 function rotate(direction) {
     var angle = parseFloat($("#angle").val());
     var previousPage = $(".activePage").val();
     if (direction == 1) {
         angle = angle + 90;
     } else if (direction == 0) {
         angle = angle - 90;
     };
     $("#angle").val(angle);
     if (angle / 90 % 2 != 0) {
         isRotate = true;
     } else {
         isRotate = false;
     }

     renderPage();
     changePage(previousPage)
 }