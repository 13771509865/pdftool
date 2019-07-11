
var currentPage;
var origin = { x: 0, y: 0 }
var end = { x: 0, y: 0 }
var drawType = 0;
var drawable = false;
var dragable = false;
var dragType = 0;
var canvas;
var canvas2;
var context;
var context2;
var fileOriginalWidth;
var fileOriginalHeight;
var step = 0;
var line_width = 2;
var font_size = 24;
var font_color = "#fd3f71";
var httpFlag = false;
var bfscrolltop = 0;//get keyboard height
var canvasDataArray = [];
//bind textArea position while editing
var interval
//save contemporary draw status
var recoverDrawStatus = [];
//lines coordinate
var lines = []
//save-draw-status
var defaultDrawStatus = {
    lineArr: [],
    straightLineArr: [],
    arrowArr: [],
    circleArr: [],
    rectArr: [],
    textArr: [],
}

var drawStatus = defaultDrawStatus;
//arrow-parameter
var polygonVertex = [];
var arrowAngel;
CONST = {
    edgeLen: 25,
    angle: 15
}
//text-area
var textAreaHtml = !isPhone ?
    '<div class="op-frame active">'
    + '<div class="op-inter" data-id="corner8"></div>'
    + '<div class="op-obj op-rect op-text">'
    + '<textarea onclick="focusText(this)"></textarea>'
    + '</div>'
    + '<div class="op-ctl-p lt-p" data-id="corner0"></div>'
    + '<div class="op-ctl-p mt-p" data-id="corner1"></div>'
    + '<div class="op-ctl-p rt-p" data-id="corner2"></div>'
    + '<div class="op-ctl-p rm-p" data-id="corner3"></div>'
    + '<div class="op-ctl-p rb-p" data-id="corner4"></div>'
    + '<div class="op-ctl-p mb-p" data-id="corner5"></div>'
    + '<div class="op-ctl-p lb-p" data-id="corner6"></div>'
    + '<div class="op-ctl-p lm-p" data-id="corner7"></div>'
    + '</div>' :
    '<div class="op-frame active">'
    + '<div class="op-inter isPhone" data-id="corner8"></div>'
    + '<div class="op-obj op-rect op-text">'
    + '<textarea onclick="focusText(this)"></textarea>'
    + '</div>'
    + '<div class="op-ctl-p rb-p isPhone" data-id="corner4"></div>'
    + '</div>';
var loadingHtml =
    '<div id="loading">'
    + '<div id="loading-center">'
    + '<div id="loading-center-absolute">'
    + '<div class="object" id="object_one"></div>'
    + '<div class="object" id="object_two"></div>'
    + '<div class="object" id="object_three"></div>'
    + '<div class="object" id="object_four"></div>'
    + '<div class="object" id="object_five"></div>'
    + '<div class="object" id="object_six"></div>'
    + '<div class="object" id="object_seven"></div>'
    + '<div class="object" id="object_eight"></div>'
    + '</div>'
    + '</div>'
    + '</div>';
var hideToolbarHtml =
    '<div class="hideToolbar">'
    + '<button id="btnCurve" class="secondaryToolbarButton active" title="曲线" drawType="0" onclick="changeDrawType(0)">'
    + '<a id="btnCurve" class="drawToolType" drawType="0" href="javascript:;">'
    + '<img src="' + basePath + '/curve.png" width="20" height="20" title="曲线">'
    + '</a>'
    + '<span>曲线</span>'
    + '</button>'
    + '<button id="btnCurve" class="secondaryToolbarButton" title="直线" drawType="1" onclick="changeDrawType(1)">'
    + '<a id="btnStraightLiner" class="drawToolType" drawType="1" href="javascript:;">'
    + '<img src="' + basePath + '/straightLine.png" width="20" height="20" title="直线">'
    + '</a>'
    + '<span>直线</span>'
    + '</button>'
    + '<button id="btnRound" class="secondaryToolbarButton" title="圆形" drawType="2" onclick="changeDrawType(2)">'
    + '<a class="drawToolType" drawType="0" href="javascript:;">'
    + '<img src="' + basePath + '/round.png" width="20" height="20" title="圆形">'
    + '</a>'
    + '<span>圆形</span>'
    + '</button>'
    + '<button id="btnRectangle" class="secondaryToolbarButton" title="矩形" drawType="3" onclick="changeDrawType(3)">'
    + '<a class="drawToolType" drawType="0" href="javascript:;">'
    + '<img src="' + basePath + '/rectangle.png" width="20" height="20" title="矩形">'
    + '</a>'
    + '<span>矩形</span>'
    + '</button>'
    + '<button id="btnArrow" class="secondaryToolbarButton" title="箭头" drawType="4" onclick="changeDrawType(4)">'
    + '<a class="drawToolType" drawType="0" href="javascript:;">'
    + '<img src="' + basePath + '/arrow.png" width="20" height="20" title="箭头">'
    + '</a>'
    + '<span>箭头</span>'
    + '</button>'
    + '<button id="btnDrawText" class="secondaryToolbarButton" title="文字"  drawType="5" onclick="changeDrawType(5)">'
    + '<a class="drawToolType" drawType="0" href="javascript:;">'
    + '<img src="' + basePath + '/drawText.png" width="20" height="20" title="文字">'
    + '</a>'
    + '<span>文字</span>'
    + '</button>'
    + '</div>';
var colorSelectHtml =
    '<div class="m-popup color-select" style="z-index: 50000; display: block;height: auto; top: 50px; left: 0px;display:none">'
    + '<div class="content"><i class="popup-arrow top black" style="left:45%"></i><div class="content-inner">'
    + '<ul class="m-menu color-list">'
    + '<li class="selected" style="background-color:#fd3f71"></li>'
    + '<li style="background-color:#ff0000"></li>'
    + '<li style="background-color:#00ff00"></li>'
    + '<li style="background-color:#0000ff"></li>'
    + '<li style="background-color:#ffffff"></li>'
    + '<li style="background-color:#000000"></li>'
    + '</ul>'
    + '</div>'
    + '</div>'
    + '</div>';
var fontsizeSlectHtml =
    '<div class="m-popup fontsize-select" style="z-index: 50000; display: block; width: 70px; height: auto; top: 30px; left: 0px;display:none">'
    + '<div class="content"><i class="popup-arrow"></i><div class="content-inner">'
    + '<ul class="m-menu select-list small-font">'
    + '<li><span class="icon"></span>18</li>'
    + '<li><span class="icon"></span>20</li>'
    + '<li><span class="icon"></span>24</li>'
    + '<li><span class="icon"></span>28</li>'
    + '<li><span class="icon"></span>32</li>'
    + '<li><span class="icon"></span>36</li>'
    + '<li><span class="icon"></span>42</li>'
    + '<li><span class="icon"></span>46</li>'
    + '<li><span class="icon"></span>52</li>'
    + '<li><span class="icon"></span>56</li>'
    + '</ul>'
    + '</div>'
    + '</div>'
    + '</div>';
var textAreaTemplateHtml =
    '<div id="textAreaTemplate" style="display:none">'
    + '<div class="op-frame fixed" style="left: 0px; top: 0px; width: 200px; height: 100px;" data-save-step="0">'
    + '<div class="op-inter" data-id="corner8"></div>'
    + '<div class="op-obj op-rect op-text"><textarea onclick="focusText(this)" readonly="readonly" style="color: rgb(253, 63, 113); font-size: 24px;"></textarea></div>'
    + '</div>'
    + '</div>';
var drawToolHtml =
    '<div class="drawTool" style="display:none">'
    + '<a id="hideToolbarButton" class="hideToolbarButton" href="javascript:;">'
    + '<img src="' + basePath + '/hideToolbar.png" drawType="0" width="20" height="20" title="模式">'
    + '</a>'
    + '<a id="btnCurve" class="drawToolType active" drawType="0" href="javascript:;" onclick="changeDrawType(0)">'
    + '<img src="' + basePath + '/curve.png" drawType="0" width="20" height="20" title="曲线">'
    + '</a>'
    + '<a id="btnStraightLiner" class="drawToolType" drawType="1" href="javascript:;" onclick="changeDrawType(1)">'
    + '<img src="' + basePath + '/straightLine.png" drawType="1" width="20" height="20" title="直线">'
    + '</a>'
    + '<a id="btnRound" class="drawToolType" drawType="2" href="javascript:;" onclick="changeDrawType(2)">'
    + '<img src="' + basePath + '/round.png" drawType="2" width="20" height="20" title="圆形">'
    + '</a>'
    + '<a id="btnRectangle" class="drawToolType" drawType="3" href="javascript:;" onclick="changeDrawType(3)">'
    + '<img src="' + basePath + '/rectangle.png" drawType="3" width="20" height="20" title="矩形">'
    + '</a>'
    + '<a id="btnArrow" class="drawToolType" drawType="4" href="javascript:;" onclick="changeDrawType(4)">'
    + '<img src="' + basePath + '/arrow.png" drawType="4" width="20" height="20" title="箭头">'
    + '</a>'
    + '<a id="btnDrawText" class="drawToolType" drawType="5" href="javascript:;" onclick="changeDrawType(5)">'
    + '<img src="' + basePath + '/drawText.png" drawType="5" width="20" height="20" title="文字">'
    + '</a>'
    // + '<a id="moveCanvas" class="moveCanvas" href="javascript:;"  onclick="cancelListener()">'
    // + '<img src="' + basePath + '/moveCanvas.png"  width="20" height="20" title="移动">'
    // + '</a>'
    + '<li id="color-marker" class="marker-tool" title="颜色">'
    + '<div class="marker-icon-btn color-marker" style="background-color:#fd3f71;"></div>'
    + '</li>'
    + '<li id="width-marker" class="marker-op width-op" title="线宽">'
    + '<span class="thin selected" line-width="2"><div></div></span>'
    + '<span class="middle" line-width="4"><div></div></span>'
    + '<span class="wide" line-width="6"><div></div></span>'
    + '</li>'
    + '<li id="width-marker" class="marker-op font-op" title="字号" style="display: inline-block;display:none">'
    + '<span class="prompt">字号</span>'
    + '<span class="font-list"><div class="combo-box-root"><span class="combo-caption">24</span><span class="combo-arrow"></span></div></span>'
    + '</li>'
    + '<a id="btnWithdraw" class="btnWithdraw" href="javascript:;" onclick="widthdrawCanvas()">'
    + '<img src="' + basePath + '/withdraw.png" class="disable" width="20" height="16" title="撤销">'
    + '</a>'
    + '<a id="btnRecover" class="btnRecover" href="javascript:;" onclick="recoverCanvas()">'
    + '<img src="' + basePath + '/recover.png" class="disable" width="20" height="16" title="撤销">'
    + '</a>'
    + '</div>';
var zoomCanvasHtml = '<div id="zoomCanvas" style="position: fixed;z-index:99999;right: 20px; bottom: 30px; width: 24px; height: 70px;display:none">' +
    '<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="zoomCanvas(1)"><img src="' + basePath + '/zoomIn.png" style="opacity: 0.5;"/></div>' +
    '<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="zoomCanvas(0)"><img src="' + basePath + '/zoomOut.png" style="opacity: 0.5;"/></div>' +
    '</div>';
var drawTextArea;
var textAreaData = {
    "left": 0,
    "top": 0,
    "width": 200,
    "height": 100
}

//toast config
toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "rtl": false,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": 300,
    "hideDuration": 1000,
    "timeOut": 5000,
    "extendedTimeOut": 1000,
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}
$(".fontsize-select ul li span").removeClass("slt-li");

initialsignatureElement();
function initialsignatureElement() {
    $("body").prepend(loadingHtml + hideToolbarHtml + colorSelectHtml
        + fontsizeSlectHtml + textAreaTemplateHtml + zoomCanvasHtml);
    $(".changePage").after(drawToolHtml);
    $("#download").attr("href", fileUrl);
    $(".changePage").before(
        '<a id="btnSignature" href="javascript:;" onclick="openSignature()">'
        + '<img src="' + basePath + '/signature.png" width="20" height="20" title="开启签批">'
        + '</a>'
        + '<a id="btnSave" class="btnSave" href="javascript:;" onclick="saveFile()">'
        + '<img src="' + basePath + '/save.png" class="disable" width="20" height="20" title="保存">'
        + '</a>'
        + '<a id="btnReadModel" class="btnReadModel" href="javascript:;" onclick="closeSignature()">'
        + '<img src="' + basePath + '/readModel.png" width="20" height="20" title="阅读模式">'
        + '</a>');
    if (isPhone) {
        font_size = 18;
        $(".combo-caption").html("18");
        $(".fontsize-select ul li:first span").addClass("slt-li")
        textAreaData.width = 120;
        textAreaData.height = 60;
    } else {
        $(".fontsize-select ul li:eq(2) span").addClass("slt-li")
    }


}
function zoomCanvas(zoomType) {
    var scale = parseFloat($("#scale").val());
    if (zoomType == 1 && scale <= 2) {
        scale = scale + 0.2;
    } else if (zoomType == 0 && scale > 1) {
        scale = scale - 0.2;
    }
    $("#scale").val(scale);
    renderPage(currentPage - 1);
    $("canvas").css({
        "width": $(".word-page:visible")[0].getBoundingClientRect().width + "px",
        "height": $(".word-page:visible")[0].getBoundingClientRect().height + "px"
    }).attr({
        "width": $(".word-page:visible")[0].getBoundingClientRect().width,
        "height": $(".word-page:visible")[0].getBoundingClientRect().height
    });
    reDrawCanvas(document.getElementById("draw_canvas"), true);
}
function draw(context, fromx, fromy, tox, toy) {
    var this_origin_x = fromx ? fromx : origin.x;
    var this_origin_y = fromy ? fromy : origin.y;
    var this_end_x = tox ? tox : end.x;
    var this_end_y = toy ? toy : end.y
    if (drawType == 0 || drawType == 1) {
        context.beginPath();
        context.moveTo(this_origin_x, this_origin_y);
        context.lineTo(this_end_x, this_end_y);
        context.stroke();
    } else if (drawType == 2) {
        var k = ((this_end_x - this_origin_x) / 0.75) / 2,
            w = (this_end_x - this_origin_y) / 2,
            h = (this_end_y - this_origin_y) / 2,
            x = (this_end_x + this_origin_x) / 2,
            y = (this_end_y + this_origin_y) / 2;
        context.beginPath();
        context.moveTo(x, y - h);
        context.bezierCurveTo(x + k, y - h, x + k, y + h, x, y + h);
        context.bezierCurveTo(x - k, y + h, x - k, y - h, x, y - h);
        context.closePath();
        context.stroke();
    } else if (drawType == 3) {
        context.beginPath();
        context.rect(this_origin_x, this_origin_y, this_end_x - this_origin_x, this_end_y - this_origin_y);
        context.stroke();
    } else if (drawType == 4) {

        context.beginPath();
        context.moveTo(this.polygonVertex[0], this.polygonVertex[1]);
        context.lineTo(this.polygonVertex[2], this.polygonVertex[3]);
        context.lineTo(this.polygonVertex[4], this.polygonVertex[5]);
        context.lineTo(this.polygonVertex[6], this.polygonVertex[7]);
        context.lineTo(this.polygonVertex[8], this.polygonVertex[9]);
        context.lineTo(this.polygonVertex[10], this.polygonVertex[11]);
        context.closePath();
        context.fill();
    }
}
function getRadian(t, e) {
    arrowAngel = Math.atan2(e.y - t.y, e.x - t.x) / Math.PI * 180
}
function sideCoord() {
    var t = {};
    t.x = (polygonVertex[4] + polygonVertex[8]) / 2,
        t.y = (polygonVertex[5] + polygonVertex[9]) / 2,
        polygonVertex[2] = (polygonVertex[4] + t.x) / 2,
        polygonVertex[3] = (polygonVertex[5] + t.y) / 2,
        polygonVertex[10] = (polygonVertex[8] + t.x) / 2,
        polygonVertex[11] = (polygonVertex[9] + t.y) / 2
}
function arrowCoord(t, e, i) {
    polygonVertex[0] = t.x,
        polygonVertex[1] = t.y,
        polygonVertex[6] = e.x,
        polygonVertex[7] = e.y,
        getRadian(t, e),
        polygonVertex[8] = e.x - CONST.edgeLen * Math.cos(Math.PI / 180 * (arrowAngel + i)),
        polygonVertex[9] = e.y - CONST.edgeLen * Math.sin(Math.PI / 180 * (arrowAngel + i)),
        polygonVertex[4] = e.x - CONST.edgeLen * Math.cos(Math.PI / 180 * (arrowAngel - i)),
        polygonVertex[5] = e.y - CONST.edgeLen * Math.sin(Math.PI / 180 * (arrowAngel - i))
}

function initialCanvasParameter(e) {
    canvas_size = { x: $(e.target).width(), y: $(e.target).height() };
    canvas_offset = { x: $(e.target).offset().left, y: $(e.target).offset().top };
    context.strokeStyle = font_color;
    context.lineWidth = line_width;
    context2.strokeStyle = font_color;
    context2.lineWidth = line_width;
}
function initialDrawToolbar() {
    drawType = 0;
    $(".drawToolType[drawType='" + drawType + "'],.secondaryToolbarButton[drawType='" + drawType + "']")
        .addClass("active").siblings().removeClass("active");
}
function openSignature() {
    currentPage = $(".activePage").val();
    if (!$(".word-page:eq(" + (currentPage - 1) + ")").attr("data-loaded")) {
        toastr.warning('当前页还没加载完!')
        return
    }
    fileOriginalWidth = datas[currentPage - 1].width;
    fileOriginalHeight = datas[currentPage - 1].height;
    isSignatureModel = true;
    if (t) t = window.clearInterval(t);
    $("#scale").val(1);
    $("#angle").val(0);
    isRotate = false;
    renderPage();
    $("body").removeClass("openSidebar")
    $(".changePage,.lnk-file-title,#zoom,#btnSignature,#download,#btnPrint,.endPage,#toggleSidebar,#rotateLeft,#rotateRight").hide();
    $(".word-page").not(":eq(" + (currentPage - 1) + ")").hide();
    $("#btnReadModel,.drawTool,#btnSave").show();
    changePage(currentPage);
    $(".word-page:visible").append("<canvas id='draw_canvas'>您的浏览器不支持画布</canvas><canvas id='cache_canvas'>您的浏览器不支持画布</canvas>");
    canvas = document.getElementById("draw_canvas");
    canvas2 = document.getElementById("cache_canvas");
    context = canvas.getContext('2d');
    context2 = canvas2.getContext('2d');
    initialCanvas();
    initialDrawToolbar();
    startBindCanvas();
}
function closeSignature() {
    isSignatureModel = false;
    clearDrawData();
    $("#scale").val(1);
    $(".op-frame").remove();
    if (t) t = self.setInterval("intervalPage()", 5000);
    $("#btnReadModel,.drawTool,#btnSave,.m-popup,#zoomCanvas").hide();
    $(".changePage,.lnk-file-title,#zoom,#btnSignature,#download,#btnPrint,.endPage,#toggleSidebar,#rotateLeft,#rotateRight").show();
    $(".word-page").not(":eq(" + (currentPage - 1) + ")").show();
    changePage(currentPage);
    $("canvas").remove();
    renderPage()
}
function cancelListener() {
    $(".drawToolType,.secondaryToolbarButton").removeClass("active");
    $(".moveCanvas").addClass("active")
    $("canvas").unbind();
    $(document).unbind();
}
function bindFontsizeBar() {
    if ($(".m-popup.fontsize-select").height() - 10 > $(".container-fluid-content").height()) {
        $(".m-popup.fontsize-select").css({
            "height": $(".container-fluid-content").height() + "px",
            "left": ($(".font-list").offset().left - 30) + "px",
            "overflow": "auto"
        })
    } else {
        $(".m-popup.fontsize-select").css({
            "height": "auto",
            "left": ($(".font-list").offset().left - 30) + "px",
            "overflow": "hidden"
        })
    }
}
function clearDrawData() {
    canvasDataArray = [];
    step = 0;
    drawStatus.lineArr = [];
    drawStatus.straightLineArr = [];
    drawStatus.circleArr = [];
    drawStatus.rectArr = [];
    drawStatus.arrowArr = [];
    drawStatus.textArr = [];
    recoverDrawStatus = [];
}
function initialCanvas() {
    clearDrawData();
    $("#btnCurve").addClass("active").siblings().removeClass("active");
    $(".docArea .op-frame").remove();
    $(".btnWithdraw img,.btnRecover img,.btnSave img").addClass("disable")
    $("canvas").css({
        "width": $(".word-page:visible").width() + "px",
        "height": $(".word-page:visible").height() + "px",
    }).attr({
        "width": $(".word-page:visible").width() + "px",
        "height": $(".word-page:visible").height() + "px"
    })

};
function saveFile() {
	if(step == 0) return;
    drawable = false;
    dragable = false;
    clearInterval(interval);
    if ($(".op-frame.active").length > 0) saveTextArea();
    var saveCanvas = document.createElement("canvas");
    saveCanvas.width = fileOriginalWidth;
    saveCanvas.height = fileOriginalHeight;
    reDrawCanvas(saveCanvas);
    $("#loading").show();
    saveSuccessCallBack(saveCanvas);
}
function reDrawCanvas(saveCanvas, iszoomCanvas) {
    if (step == 0) {
        $(".op-frame.active").remove();
        return
    }

    var saveCanvasContext = saveCanvas.getContext("2d");
    //get current step drawStatus

    var thisDrawStatus = drawStatus;
    //redraw_lines
    for (var i = 0; i < thisDrawStatus.lineArr.length; i++) {
        var thisLine = thisDrawStatus.lineArr[i];
        saveCanvasContext.lineWidth = thisDrawStatus.lineArr[i].line_width;
        saveCanvasContext.strokeStyle = thisDrawStatus.lineArr[i].line_color;
        drawType = 0;
        for (var j = 0; j < thisLine.line.length - 1; j++) {
            draw(saveCanvasContext,
                thisLine.line[j].x * saveCanvas.width,
                thisLine.line[j].y * saveCanvas.height,
                thisLine.line[j + 1].x * saveCanvas.width,
                thisLine.line[j + 1].y * saveCanvas.height)
        }
    }
    //redraw_strightLine
    for (var i = 0; i < thisDrawStatus.straightLineArr.length; i++) {
        var thisStrightLine = thisDrawStatus.straightLineArr[i];
        saveCanvasContext.lineWidth = thisDrawStatus.straightLineArr[i].line_width;
        saveCanvasContext.strokeStyle = thisDrawStatus.straightLineArr[i].line_color;
        drawType = 1;
        draw(saveCanvasContext,
            thisStrightLine.origin_x * saveCanvas.width,
            thisStrightLine.origin_y * saveCanvas.height,
            thisStrightLine.end_x * saveCanvas.width,
            thisStrightLine.end_y * saveCanvas.height)

    }
    //redraw_circle
    for (var i = 0; i < thisDrawStatus.circleArr.length; i++) {
        var thisCircleLine = thisDrawStatus.circleArr[i];
        saveCanvasContext.lineWidth = thisDrawStatus.circleArr[i].line_width;
        saveCanvasContext.strokeStyle = thisDrawStatus.circleArr[i].line_color;
        drawType = 2;
        draw(saveCanvasContext,
            thisCircleLine.origin_x * saveCanvas.width,
            thisCircleLine.origin_y * saveCanvas.height,
            thisCircleLine.end_x * saveCanvas.width,
            thisCircleLine.end_y * saveCanvas.height)

    }
    //redraw_rect
    for (var i = 0; i < thisDrawStatus.rectArr.length; i++) {
        var thisRectLine = thisDrawStatus.rectArr[i];
        saveCanvasContext.lineWidth = thisDrawStatus.rectArr[i].line_width;
        saveCanvasContext.strokeStyle = thisDrawStatus.rectArr[i].line_color;
        drawType = 3;
        draw(saveCanvasContext,
            thisRectLine.origin_x * saveCanvas.width,
            thisRectLine.origin_y * saveCanvas.height,
            thisRectLine.end_x * saveCanvas.width,
            thisRectLine.end_y * saveCanvas.height)

    }
    //redraw_arrow
    for (var i = 0; i < thisDrawStatus.arrowArr.length; i++) {
        var thisArrow = thisDrawStatus.arrowArr[i];
        saveCanvasContext.fillStyle = thisDrawStatus.arrowArr[i].line_color;
        drawType = 4;
        arrowCoord(
            { x: thisArrow.origin.x * saveCanvas.width, y: thisArrow.origin.y * saveCanvas.height },
            { x: thisArrow.end.x * saveCanvas.width, y: thisArrow.end.y * saveCanvas.height },
            CONST.edgeLen);
        sideCoord();
        saveCanvasContext.fillStyle = thisArrow.arrow_color;
        draw(saveCanvasContext);
    }

    if (!iszoomCanvas) {
        //draw textArea to canvas
        for (var i = 0; i < thisDrawStatus.textArr.length; i++) {
            var thisLineHeight = thisDrawStatus.textArr[i].font_size.substring(0, thisDrawStatus.textArr[i].font_size.length - 2);
            saveCanvasContext.beginPath()
            saveCanvasContext.font = thisDrawStatus.textArr[i].font_size + " ו";
            saveCanvasContext.fillStyle = thisDrawStatus.textArr[i].color;
            canvasTextAutoLine(
                thisDrawStatus.textArr[i].textVal, saveCanvasContext,
                thisDrawStatus.textArr[i].width * saveCanvas.width, thisDrawStatus.textArr[i].left * saveCanvas.width + 5,
                thisDrawStatus.textArr[i].top * saveCanvas.height + parseInt(thisLineHeight) + 5, parseInt(thisLineHeight)
            );

            saveCanvasContext.save();
        }
    }
    else {
        if ($(".op-frame.active").length > 0) saveTextArea();
        $(".docArea .op-frame.fixed").each(function (index, e) {
            $(this).css({
                "top": thisDrawStatus.textArr[index].top * saveCanvas.height + "px",
                "left": thisDrawStatus.textArr[index].left * saveCanvas.width + "px"
            })
        })
    }



}
function saveSuccessCallBack(saveCanvas) {

    //window.open(saveCanvas.toDataURL("image/png"), "_blank")
    $.ajax({
        url: signatureUrl,
        data: {
            "page": currentPage,
            "imgData": saveCanvas.toDataURL('image/png'),
            "fileUrl": fileUrl,
            "folderUrl": folderUrl,
            "filePath": filePath,
            "Type":Type
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#loading').hide();
            if (data.result == 0) {
                fileUrl = data.data;
                $("#download").attr("href", data.data);
                var pageImage = new Image();
                var thisSrc = basePath + "/" + currentPage + "."+imgType+"?ver=" + Math.random()
                pageImage.src = thisSrc
                $(pageImage).bind("load", function () {
                	
                	if("svg" == imgType){
                		$(".word-content:visible").find("embed").remove();
                		$(".word-content:visible").append('<embed src="' + thisSrc + '" width="100%" height="100%" type="image/svg+xml"></embed>')     
                	}else if("png" == imgType){
                		$(".word-content:visible").find("img").remove();
                		$(".word-content:visible").append('<img src="' + thisSrc + '" width="100%" height="100%" />')     
                	}
                    closeSignature();
                })


            } else {
                toastr.warning(data.message + '!')
            }
        },
        error: function (data) {
            $('#loading').hide();
            toastr.error('网络错误!')
            closeSignature();
        }
    });

}
function canvasTextAutoLine(str, ctx, textWidth, initX, initY, lineHeight) {
    var lineWidth = 0;
    var lastSubStrIndex = 0;
    for (var i = 0; i < str.length; i++) {     
        if(str[i] == "\n"){
            ctx.fillText(str.substring(lastSubStrIndex, i), initX, initY);
            initY += lineHeight;
            lineWidth = 0;
            lastSubStrIndex = i + 1;
            lineWidth = 0;     
        }
        else{
            lineWidth += ctx.measureText(str[i]).width;
            if (lineWidth > textWidth) {
                ctx.fillText(str.substring(lastSubStrIndex, i), initX, initY);
                initY += lineHeight;
                lineWidth = 0;
                lastSubStrIndex = i;
            }
        }
        if (i == str.length - 1) {
            ctx.fillText(str.substring(lastSubStrIndex, i + 1), initX, initY);
        }
    }
}
/*save canvas to image*/
function saveCanvas() {
    var saveCanvas = document.createElement("canvas");
    saveCanvas.width = datas[currentPage - 1].width;
    saveCanvas.height = datas[currentPage - 1].height;
    var img = new Image();
    img.src = canvasDataArray[step - 1].data;
    $(img).bind("load", function () {
        saveCanvas.getContext("2d").drawImage(this, 0, 0, datas[currentPage - 1].width, datas[currentPage - 1].height);
        //console.log(saveCanvas.toDataURL("image/png"))
    })
}
function listenDrag() {
    $(".op-inter,.op-ctl-p").bind("mousedown touchstart", function (event) {
        event.stopPropagation()
        dragable = true;
        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
        origin.x = i.clientX - canvas_offset.x;
        origin.y = i.clientY - canvas_offset.y;
        textAreaData.left = $(".op-frame.active").position().left;
        textAreaData.top = $(".op-frame.active").position().top;
        textAreaData.width = $(".op-frame.active").width();
        textAreaData.height = $(".op-frame.active").height();
        $(".saveText").remove();
        if ($(event.target).attr("data-id") == "corner8") {
            dragType = 0
        }
        else if ($(event.target).attr("data-id") == "corner0") {
            dragType = 1
        } else if ($(event.target).attr("data-id") == "corner1") {
            dragType = 2
        } else if ($(event.target).attr("data-id") == "corner2") {
            dragType = 3
        } else if ($(event.target).attr("data-id") == "corner3") {
            dragType = 4
        } else if ($(event.target).attr("data-id") == "corner4") {
            dragType = 5
        } else if ($(event.target).attr("data-id") == "corner5") {
            dragType = 6
        } else if ($(event.target).attr("data-id") == "corner6") {
            dragType = 7
        } else if ($(event.target).attr("data-id") == "corner7") {
            dragType = 8
        };

    });
    //bind textRea move
    $(document).bind('mousemove touchmove', function (event) {
        event.preventDefault();
        var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;
        if (dragable == true) {
            clearInterval(interval)
            end.x = i.clientX - canvas_offset.x;
            end.y = i.clientY - canvas_offset.y;
            if (dragType == 0) {
                $(".op-frame.active").css({
                    "left": textAreaData.left + (end.x - origin.x) + "px",
                    "top": textAreaData.top + (end.y - origin.y) + "px"
                })
            } else if (dragType == 1) {
                $(".op-frame.active").css({
                    "left": textAreaData.left + (end.x - origin.x) + "px",
                    "top": textAreaData.top + (end.y - origin.y) + "px",
                    "width": textAreaData.width - (end.x - origin.x) + "px",
                    "height": textAreaData.height - (end.y - origin.y) + "px"
                })
            } else if (dragType == 2) {
                $(".op-frame.active").css({
                    "top": textAreaData.top + (end.y - origin.y) + "px",
                    "height": textAreaData.height - (end.y - origin.y) + "px"
                })
            } else if (dragType == 3) {
                $(".op-frame.active").css({
                    "top": textAreaData.top + (end.y - origin.y) + "px",
                    "width": textAreaData.width + (end.x - origin.x) + "px",
                    "height": textAreaData.height - (end.y - origin.y) + "px"
                })
            } else if (dragType == 4) {
                $(".op-frame.active").css({
                    "width": textAreaData.width + (end.x - origin.x) + "px",
                })
            } else if (dragType == 5) {
                $(".op-frame.active").css({
                    "width": textAreaData.width + (end.x - origin.x) + "px",
                    "height": textAreaData.height + (end.y - origin.y) + "px"
                })
            } else if (dragType == 6) {
                $(".op-frame.active").css({
                    "height": textAreaData.height + (end.y - origin.y) + "px"
                })
            } else if (dragType == 7) {
                $(".op-frame.active").css({
                    "left": textAreaData.left + (end.x - origin.x) + "px",
                    "width": textAreaData.width - (end.x - origin.x) + "px",
                    "height": textAreaData.height + (end.y - origin.y) + "px"
                })
            } else if (dragType == 8) {
                $(".op-frame.active").css({
                    "left": textAreaData.left + (end.x - origin.x) + "px",
                    "width": textAreaData.width - (end.x - origin.x) + "px",
                })
            }
            if ($(".op-frame.active").width == 0 || $(".op-frame.active").height == 0) {
                $(".op-frame.active").css({
                    "width": "0px",
                    "height": "0px"
                })
            }

        }
    })
    $(document).bind('mouseup touchend', function (event) {
        dragable = false;
        if (drawType == 5 && dragable == false && $(".op-frame.active").position()) {
            //constrict size of textArea
            if ($(".op-frame.active").position().left + $(".op-frame.active").width() > canvas.width - 20) {
                $(".op-frame.active").css({
                    "left": (canvas.width - $(".op-frame.active").width() - 20) + "px"
                })
            }
            if ($(".op-frame.active").position().top + $(".op-frame.active").height() > canvas.height - 20) {
                $(".op-frame.active").css({
                    "top": (canvas.height - $(".op-frame.active").height() - 20) + "px"
                })
            };
            if ($(".op-frame.active").position().left < 20) {
                $(".op-frame.active").css({
                    "left": "20px"
                })
            }
            if ($(".op-frame.active").position().top < 50) {
                $(".op-frame.active").css({
                    "top": "50px"
                })
            }
            if ($(".op-frame.active").width() > canvas.width - 40) {
                $(".op-frame.active").css({
                    "left": "20px",
                    "width": (canvas.width - 40) + "px"
                })
            }
            if ($(".op-frame.active").height() > canvas.height - 70) {
                $(".op-frame.active").css({
                    "top": "50px",
                    "height": (canvas.height - 40) + "px"
                })
            }
            textAreaData.left = $(".op-frame.active").position().left;
            textAreaData.top = $(".op-frame.active").position().top;
            textAreaData.width = $(".op-frame.active").width();
            textAreaData.height = $(".op-frame.active").height();
            if ($(".saveText").length != 1) {
                var op_frame_width = $('.op-frame.active').width();
                $(".op-frame.active").append("<div class='saveText' style='left:" + (op_frame_width - 50) / 2 + "px'>"
                    + '<i class="arrow" style=""></i>'
                    + "<input type='button' value='删除' onclick='deleteTextArea(this)'>"
                    + "</div>");
            }

        }
    });

}


function focusText(e) {

    var havePopUP = $(".fontsize-select").is(':visible') || $(".color-select").is(':visible') || $(".hideToolbar").is(':visible');
    $(".fontsize-select,.color-select,.hideToolbar").hide();
    if (havePopUP) {
        return
    }
    if ($(".op-frame.active").length > 0 && $(e).parent().parent().attr("data-save-step") != $(".op-frame.active").attr("data-save-step")) {
        saveTextArea();
        return
    }
    textAreaData.top = $(e).parent().parent().position().top;
    clearInterval(interval)
    $(".op-frame.active").removeClass("active").addClass("fixed").css("border", "none")
        .find("textarea").css("border", "none").attr("readonly", "readonly");
    drawType = 5;
    if ($(e).attr("readonly")) $(e).removeAttr("readonly")
    $(".op-frame.active").removeClass("active").addClass("fixed")
    $(".saveText,.op-ctl-p").remove();
    $(".op-inter,.op-ctl-p").unbind();
    if ($(e).parent().parent().hasClass("fixed")) {
        $(e).parent().css("border", "1px dashed red").parent().addClass("active").removeClass("fixed").addClass("active")
            .append(!isPhone ? '<div class="op-ctl-p lt-p" data-id="corner0"></div>'
                + '<div class="op-ctl-p mt-p" data-id="corner1"></div>'
                + '<div class="op-ctl-p rt-p" data-id="corner2"></div>'
                + '<div class="op-ctl-p rm-p" data-id="corner3"></div>'
                + '<div class="op-ctl-p rb-p" data-id="corner4"></div>'
                + '<div class="op-ctl-p mb-p" data-id="corner5"></div>'
                + '<div class="op-ctl-p lb-p" data-id="corner6"></div>'
                + '<div class="op-ctl-p lm-p" data-id="corner7"></div>' :
                '<div class="op-ctl-p rb-p isPhone" data-id="corner4"></div>');
        // interval = setInterval(function () {
        //     if ($(".op-frame.active").length == 0) clearInterval(interval);
        //     if ($(".container-fluid-content").height() - ($(".op-frame.active").offset().top - 40) < 30) {
        //         $(".op-frame.active").css({
        //             "top": $(".container-fluid-content").height() + $(".container-fluid-content").scrollTop() - 40 + "px",
        //         })
        //     } else {
        //         $(".op-frame.active").css({
        //             "top": textAreaData.top + "px"
        //         })
        //     }
        // }, 100);

    };
    $("canvas").unbind();
    startBindCanvas();
    listenDrag();
    $(".drawToolType[drawType='" + drawType + "'],.secondaryToolbarButton[drawType='" + drawType + "']")
        .addClass("active").siblings().removeClass("active");
    $(".width-op").hide();
    $(".font-op").show();
    if ($(".saveText").length != 1) {
        var op_frame_width = $('.op-frame.active').width();
        $(".op-frame.active").append("<div class='saveText' style='left:" + (op_frame_width - 50) / 2 + "px'>"
            + '<i class="arrow" style=""></i>'
            + "<input type='button' value='删除' onclick='deleteTextArea(this)'>"
            + "</div>");
    }

}
function deleteTextArea(e) {
    drawStatus.textArr.splice($(e).parent().parent().index() - 3, 1);
    canvasDataArray.splice($(e).parent().parent().attr("data-save-step") - 1, 1);
    if ($(e).parent().parent().attr("data-save-step")) {
        step = step - 1;
    }

    if (step == 0) {
        $(".btnWithdraw img,.btnSave img").addClass("disable")
    }
    $(e).parent().parent().remove();

}
function saveTextArea() {
    clearInterval(interval);
    $(".op-frame.active").css("top", textAreaData.top + "px");
    if ($(".op-frame.active").find("textarea").val() == "") {
        $(".op-frame.active").remove();
        return;
    }
    if (!$(".op-frame.active").attr("data-save-step")) {
        canvasDataArray.splice(step);
        recoverDrawStatus = [];
        step = step + 1;
        $(".op-frame.active").attr("data-save-step", step);
        canvasDataArray.push({
            "type": drawType,
            "data": $("#draw_canvas")[0].toDataURL("image/png"),
            "step": step
        });
        drawStatus.textArr.push({
            "width": $(".op-frame.active").width() / canvas.width,
            "height": $(".op-frame.active").height() / canvas.height,
            "top": textAreaData.top / canvas.height,
            "left": ($(".op-frame.active").offset().left - canvas_offset.x) / canvas.width,
            "textVal": $(".op-frame.active").find("textarea").val(),
            "color": $(".op-frame.active").find("textarea").css("color"),
            "font_size": $(".op-frame.active").find("textarea").css("font-size")
        });
        drawable = false;


    } else {
        drawStatus.textArr[$(".op-frame.active").index() - 3] = {
            "width": $(".op-frame.active").width() / canvas.width,
            "height": $(".op-frame.active").height() / canvas.height,
            "top": textAreaData.top / canvas.height,
            "left": ($(".op-frame.active").offset().left - canvas_offset.x) / canvas.width,
            "textVal": $(".op-frame.active").find("textarea").val(),
            "color": $(".op-frame.active").find("textarea").css("color"),
            "font_size": $(".op-frame.active").find("textarea").css("font-size")
        }
    }
    if (canvasDataArray.length == step) {
        $(".btnRecover img").addClass("disable")
    }
    if (canvasDataArray.length >= 1) {
        $(".btnWithdraw img,.btnSave img").removeClass("disable")
    }
    $(".op-frame.active").removeClass("active").addClass("fixed")
        .css("border", "none").find("textarea")
        .attr("readonly", "readonly").parent().css("border", "none");
    $(".saveText,.op-ctl-p").remove();
    $(".op-inter,.op-ctl-p").unbind();





}
function widthdrawDrawStatus(type) {
    clearInterval(interval);
    var thisType = parseInt(type)
    switch (thisType) {
        case 0:
            recoverDrawStatus.push({
                "data": drawStatus.lineArr[drawStatus.lineArr.length - 1],
                "type": 0
            });
            drawStatus.lineArr.pop();
            break;
        case 1:
            recoverDrawStatus.push({
                "data": drawStatus.straightLineArr[drawStatus.straightLineArr.length - 1],
                "type": 1
            });
            drawStatus.straightLineArr.pop();
            break;
        case 2:
            recoverDrawStatus.push({
                "data": drawStatus.circleArr[drawStatus.circleArr.length - 1],
                "type": 2
            });
            drawStatus.circleArr.pop();
            break;
        case 3:
            recoverDrawStatus.push({
                "data": drawStatus.rectArr[drawStatus.rectArr.length - 1],
                "type": 3
            });
            drawStatus.rectArr.pop();
            break;
        case 4:
            recoverDrawStatus.push({
                "data": drawStatus.arrowArr[drawStatus.arrowArr.length - 1],
                "type": 4
            });
            drawStatus.arrowArr.pop();
            break;
        case 5:
            recoverDrawStatus.push({
                "data": drawStatus.textArr[drawStatus.textArr.length - 1],
                "type": 5
            });
            drawStatus.textArr.pop();
            break;
    };

}
function recoverDrawCanvas(type) {
    clearInterval(interval);
    var thisType = parseInt(type)
    switch (thisType) {
        case 0:
            drawStatus.lineArr.push(recoverDrawStatus[recoverDrawStatus.length - 1].data);
            break;
        case 1:
            drawStatus.straightLineArr.push(recoverDrawStatus[recoverDrawStatus.length - 1].data);
            break;
        case 2:
            drawStatus.circleArr.push(recoverDrawStatus[recoverDrawStatus.length - 1].data);
            break;
        case 3:
            drawStatus.rectArr.push(recoverDrawStatus[recoverDrawStatus.length - 1].data);
            break;
        case 4:
            drawStatus.arrowArr.push(recoverDrawStatus[recoverDrawStatus.length - 1].data);
            break;
        case 5:
            drawStatus.textArr.push(recoverDrawStatus[recoverDrawStatus.length - 1].data);
            break;
    }
    recoverDrawStatus.pop();
}
function recoverCanvas() {
    if (step == canvasDataArray.length) return;
    step = step + 1;
    recoverDrawCanvas(canvasDataArray[step - 1].type);
    if (canvasDataArray[step - 1].type != 5) {
        var img = new Image();
        img.src = canvasDataArray[step - 1].data;
        $(img).bind("load", function () {
            context.drawImage(this, 0, 0, this.width, this.height);
        });
    } else if (canvasDataArray[step - 1].type == 5) {
        $("#textAreaTemplate .op-frame").css({
            "width": drawStatus.textArr[drawStatus.textArr.length - 1].width * canvas.width + "px",
            "height": drawStatus.textArr[drawStatus.textArr.length - 1].height * canvas.height + "px",
            "top": drawStatus.textArr[drawStatus.textArr.length - 1].top * canvas.height + "px",
            "left": drawStatus.textArr[drawStatus.textArr.length - 1].left * canvas.width + "px",
        }).attr("data-save-step", step)
            .find("textarea").html(drawStatus.textArr[drawStatus.textArr.length - 1].textVal).css({
                "color": drawStatus.textArr[drawStatus.textArr.length - 1].color,
                "font-size": drawStatus.textArr[drawStatus.textArr.length - 1].font_size
            }).parent().css("border", "none");
        $(".word-page:visible").append($("#textAreaTemplate").html());
    }
    if (step < canvasDataArray.length) {
        $(".btnRecover img").removeClass("disable");
    } else {
        $(".btnRecover img").addClass("disable");
    }
    if (step != 0) $(".btnWithdraw img,.btnSave img").removeClass("disable");


}
function widthdrawCanvas() {
    if (step == 0) return;
    //get latest drawStatusType
    var currrentStep = canvasDataArray[step - 1].type;
    widthdrawDrawStatus(currrentStep);
    if (canvasDataArray[step - 1].type != 5) {
        clear_canvas();
        step = step - 1;
        if (step >= 1) {
            var img = new Image();
            img.src = canvasDataArray[step - 1].data;
            $(img).bind("load", function () {
                context.drawImage(this, 0, 0, this.width, this.height);
            });
            ;
        }
    } else if (canvasDataArray[step - 1].type == 5) {
        if (step >= 1) {
            $(".docArea .op-frame[data-save-step=" + step + "]").remove();
            step = step - 1;
        }
    }
    if (step == 0) $(".btnWithdraw img,.btnSave img").addClass("disable");
    if (step < canvasDataArray.length) $(".btnRecover img").removeClass("disable");

}

function clear_canvas() {
    context.clearRect(0, 0, canvas_size.x, canvas_size.y);
}

function startBindCanvas() {
    $("canvas").bind('mousedown touchstart', function (event) {
        event.preventDefault();
        var havePopUP = $(".fontsize-select").is(':visible') || $(".color-select").is(':visible') || $(".hideToolbar").is(':visible');

        if (havePopUP) {
            $(".fontsize-select,.color-select,.hideToolbar").hide();
            return
        }

        initialCanvasParameter(event);

        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;

        drawable = true;
        origin.x = i.clientX - canvas_offset.x;
        origin.y = i.clientY - canvas_offset.y;
        if (drawType == 0) {
            lines.push({
                "x": origin.x / canvas.width,
                "y": origin.y / canvas.height
            })
        }
        if (drawType == 5) {
            if ($(".op-frame.active").length == 0) {
                origin.x = i.clientX - canvas_offset.x;
                origin.y = i.clientY - canvas_offset.y;
                $(event.target).parent().append(textAreaHtml);
                listenDrag();
                $(".op-frame.active").css({
                    "left": origin.x + "px",
                    "top": origin.y + "px",
                    "width": textAreaData.width + "px",
                    "height": textAreaData.height + "px"
                }).find("textarea").css({
                    "color": font_color,
                    "font-size": font_size + "px"
                });

            } else {
                if ($(".op-frame.active").find("textarea").val() != "") {
                    saveTextArea()
                } else {
                    $(".op-frame.active").remove();
                }
            }
        }

    })
    $("canvas").bind("mousemove touchmove", function (event) {
        event.preventDefault();
        if (drawable == false) return;
        var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;
        if (drawType == 0) {
            end.x = i.clientX - canvas_offset.x;
            end.y = i.clientY - canvas_offset.y;
            lines.push({
                "x": end.x / canvas.width,
                "y": end.y / canvas.height
            })
            draw(context);
            origin.x = end.x;
            origin.y = end.y;
        } else if (drawType == 1 || drawType == 2 || drawType == 3) {
            end.x = i.clientX - canvas_offset.x;
            end.y = i.clientY - canvas_offset.y;
            context2.clearRect(0, 0, canvas_size.x, canvas_size.y);
            draw(context2);
        } else if (drawType == 4) {
            end.x = i.clientX - canvas_offset.x;
            end.y = i.clientY - canvas_offset.y;
            context2.clearRect(0, 0, canvas_size.x, canvas_size.y);
            arrowCoord(origin, end, CONST.edgeLen);
            sideCoord();
            context2.fillStyle = font_color;
            draw(context2);
        }
    })
    $("canvas").bind('mouseup touchend', function (event) {
        var i = event.type == "touchend" ? event.originalEvent.changedTouches[0] : event;
        if (drawable == true) {
            recoverDrawStatus = []
        }
        if (drawType == 0 && drawable == true) {
            drawable = false;
            canvasDataArray.splice(step);
            step++;
            drawStatus.lineArr.push({
                "line": lines,
                "line_width": line_width,
                "line_color": font_color
            });
            if (lines.length > 2) {
                canvasDataArray.push({
                    "type": drawType,
                    "data": $("#draw_canvas")[0].toDataURL("image/png"),
                    "step": step
                });
            }
            lines = [];

        }
        if ((drawType == 1 || drawType == 2 || drawType == 3) && drawable == true) {
            drawable = false;
            context2.clearRect(0, 0, canvas_size.x, canvas_size.y);
            end.x = i.clientX - canvas_offset.x;
            end.y = i.clientY - canvas_offset.y;
            draw(context);
            canvasDataArray.splice(step);
            step++;
            canvasDataArray.push({
                "type": drawType,
                "data": $("#draw_canvas")[0].toDataURL("image/png"),
                "step": step
            });
            var thisDrawStatus = drawType == 1 ? drawStatus.straightLineArr : drawType == 2 ? drawStatus.circleArr : drawType == 3 ? drawStatus.rectArr : null;
            thisDrawStatus.push({
                "origin_x": origin.x / canvas.width,
                "origin_y": origin.y / canvas.height,
                "end_x": end.x / canvas.width,
                "end_y": end.y / canvas.height,
                "line_width": line_width,
                "line_color": font_color
            });

        }
        if (drawType == 4 && drawable == true) {
            drawable = false;
            context2.clearRect(0, 0, canvas_size.x, canvas_size.y);
            arrowCoord(origin, end, 25);
            sideCoord();
            context.fillStyle = font_color;
            draw(context);
            canvasDataArray.splice(step);
            step++;
            canvasDataArray.push({
                "type": drawType,
                "data": $("#draw_canvas")[0].toDataURL("image/png"),
                "step": step
            });
            drawStatus.arrowArr.push({
                "origin": { x: origin.x / canvas.width, y: origin.y / canvas.height },
                "end": { x: end.x / canvas.width, y: end.y / canvas.height },
                "arrow_color": font_color
            });
        }
        if (canvasDataArray.length == step) {
            $(".btnRecover img").addClass("disable")
        }
        if (canvasDataArray.length >= 1) {
            $(".btnWithdraw img,.btnSave img").removeClass("disable");
        }

    })

}
$(".color-marker").click(function (e) {
    $(".fontsize-select,.hideToolbar").hide();
    if ($(".m-popup.color-select").is(":hidden")) {
        $(".m-popup.color-select").css({
            "left": $(e.target).offset().left - 72 < 0 ? 0 : ($(e.target).offset().left - 72) + "px"
        }).show();
    } else {
        $(".m-popup.color-select").hide();
    }
});
$(".font-list").click(function (e) {
    $(".hideToolbar,.color-select").hide();
    if ($(".m-popup.fontsize-select").is(":hidden")) {
        $(".m-popup.fontsize-select").show();
        bindFontsizeBar();
    } else {
        $(".m-popup.fontsize-select").hide()
    }
})
$(".small-font li").click(function (e) {
    $(this).find("span").addClass("slt-li").parent()
        .siblings().find("span").removeClass("slt-li");

    $(".m-popup.fontsize-select").hide()
    $(".combo-caption").html($(this).text());
    font_size = $(this).text();
    $(".op-frame.active").find("textarea").css("font-size", $(this).text() + "px")
})
$("#width-marker span").click(function (e) {
    $(this).addClass("selected").siblings().removeClass("selected");
    line_width = $(this).attr("line-width");
})
$(".color-list li").click(function (e) {
    $(this).addClass("selected").siblings().removeClass("selected");
    $(".m-popup.color-select").hide();
    $(".color-marker").css("background-color", $(this).css("background-color"));
    font_color = $(this).css("background-color");
    $(".op-frame.active").find("textarea").css("color", $(this).css("background-color"))
})

function changeDrawType(type) {
    drawable = false;
    drawType = type;
    $(".moveCanvas").removeClass("active");
    $(".drawToolType[drawType='" + drawType + "'],.secondaryToolbarButton[drawType='" + drawType + "']")
        .addClass("active").siblings().removeClass("active");
    $(".m-popup").hide();
    $(".hideToolbar").fadeOut();
    if (drawType == 5) {
        $(".width-op").hide();
        $(".font-op").show();
    } else {
        $(".width-op").show();
        $(".font-op").hide();
        if ($(".op-frame.active").length > 0) {
            drawType = 5;
            saveTextArea()
        }
    };
    $("canvas").unbind();
    $(document).unbind();
    startBindCanvas();
}
$(".hideToolbarButton").click(function (e) {
    $(".fontsize-select,.color-select").hide();
    $(".hideToolbar").is(":hidden") ? $(".hideToolbar").show() : $(".hideToolbar").hide()
})
window.onresize = function () {

    $(".m-popup.color-select").css({
        "left": $(".color-marker").offset().left - 70 < 0 ? 0 : ($(".color-marker").offset().left - 70) + "px"
    });


    bindFontsizeBar()

}