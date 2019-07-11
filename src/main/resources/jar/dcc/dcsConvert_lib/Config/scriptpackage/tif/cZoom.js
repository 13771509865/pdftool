 var timer = self.setInterval("bindToolbar()", 500)
 var scrollPosition = $(".container-fluid-content").scrollTop();
 var scolling = false;


 $("#toggleToolbar").click(function (e) {
     $(".secondaryToolbar").toggleClass("show")
 })

 function bindToolbar() {
     var currentPosition = $(".container-fluid-content").scrollTop()
     if (currentPosition != scrollPosition) {
         scrollPosition = currentPosition;
         $("#pageNum").addClass("visible");
         scolling = true;
     } else {
         scolling = false;
         setTimeout(
             "ifHide()", 1000)
     }
 }

 function ifHide() {
     if ($(".container-fluid-content").scrollTop() == scrollPosition && scolling == false) {
         $("#pageNum").removeClass("visible");
     }
 }
 $(function () {
     var appendStr = '<div id="zoom">' +
         '<div class="w-scale-btn w-scale-shrink " id="shrinkBtn" onclick="changeTab(0)" title="缩小"></div>' +
         '<div class="w-scale-text" id="scale_text">100%</div>' +
         '<div class="w-scale-btn w-scale-magnify " id="magnifyBtn" onclick="changeTab(1)" title="放大"></div>' +
         '</div>';
     if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
         $('body').append(appendStr);
     }
     if(!showFooter.isShowFooter){
        $('#zoom').css({'bottom':'42px','right':'10px'})
     }
 });

 function changeTab(type) {
     if (!isZoom) {
         cw = $(".word-content")[0].getBoundingClientRect().width;
         cr = datas[nowpage].width / datas[nowpage].height
         $("#mainbody").css("overflow", "auto");
         isZoom = true;
     };
     var mw = $(".word-page")[0].getBoundingClientRect().width;
     var mc = mw / cw;
     if (type == 1 && currrentScale <= mc) {
         currrentScale = currrentScale + 0.2;
     } else if (type == 0 && currrentScale >= 0.4) {
         currrentScale = currrentScale - 0.2;
     }
     var c = cw * currrentScale > mw ? mw : cw * currrentScale;
     $(".word-content").css({
         width: c + "px",
         height: c / cr + "px"
     })
     $("#scale_text").html(Math.round(currrentScale * 100) + "%")

 }
