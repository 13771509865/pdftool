var viewport = document.querySelector("meta[name=viewport]");
var coordinate = { x: 0, y: 0 }
var drawArea = { x: 0, y: 0 }
var origin = { x: 0, y: 0 }
var end = { x: 0, y: 0 }
var originalPage = { width: 0, height: 0 }
var limitArea = { x_min: 0, x_max: 2000, y_min: 0, y_max: 2000 };
var coordinateXArray = [];
var coordinateYArray = []
var drawable = false;
var dragable = false;
var dragResource = false;
var clipBorder = 10;
var currentPage;
var moveStep = 0;
var drawColor = "black";
var drawLineWidth = "6";
var font_size = 18;
var text_line_height = 30;
//是否是绘图模式
var isDrawModel = false;
//绘图模式类型
var drawModelType = 1;
//记录单条线段的数组集合
var singleLines = [];
//记录当前textarea的位置，用于键盘弹出后记录之前位置
var currentTextTop;
//定时器-键盘弹出后监听高度变化
var focusTextInterval
var displayImgData = {
    "left": 0,
    "top": 0,
    "width": 0,
    "height": 0
};
var diplayTextData = {
    "left": 0,
    "top": 0,
    "width": 0,
    "height": 0
}
var currentImgScale;
var displayImgTemplate =
    '<DIV class="signatureImg active" onclick="focusImg(this)" isCreat="true">' +
    '<div class="signatureBorder"></div>' +
    '<div style="width:100%;height:100%;position:relative;">' +
    '<img drag-id="0" src="" width="100%" height="100%"/>' +
    '<div drag-id="1" class="se-resize"></div>' +
    '<div class="deleteImageData">' +
    '<i class="arrow" ></i>' +
    '<input value="删除" onclick="deleteImageData(this)" type="button">' +
    '</div>' +
    '</div>'

    + '</DIV>';
var displayTextTemplate =
    '<div class="signatureText focus" isCreat="true" style="width:120px">'
    + '<div class="textDrag">'
    + '<div class="se-resize" drag-id="1"></div>'
    + '</div>'
    + '<div class="text-rect">'
    + '<textarea drag-id="0" style="font-size:18px;line-height:30px;"></textarea>'
    + '</div>'
    + '<div class="saveText_phone" style="left:-25px;display:none">'
    + '<i class="arrow"></i>'
    + '<input class="editText" value="编辑" onclick="editThisText(this)" type="button">'
    + '<input class="deleteText" value="删除" onclick="deleteThisText(this)" type="button" style="margin-left:1px">'
    + '</div>'
    + '</div>'
var dragType = 0;

var canvas;
var $targetObj
var $stageObj
var lastTimestamp
var currentArrayIndex = 0;
var lastLineWidth = -1;
var textFontColor = "#fd3f71"
toastr.options = {
    "closeButton": false,
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
var drawStatus = {
    imgArr: [],
    textArr: [],
    lineArr: []
};
var preTransformX = 0;
var preTransformY = 0;
var preScale = 1;
var currentScale = 1;
var lastPinchArray = [];
var enablePinch = false;
var zoomCanvasHtml = '<div id="zoomCanvas" style="position: fixed;z-index:99999;right: 20px; bottom: 30px; width: 24px; height: 70px;display:none">' +
    '<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="zoomCanvas(1)"><img src="' + basePath + '/zoomIn.png" style="opacity: 0.5;"/></div>' +
    '<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="zoomCanvas(0)"><img src="' + basePath + '/zoomOut.png" style="opacity: 0.5;"/></div>' +
    '</div>';
var pop_ups = '<div class="m-popup color-select" style="z-index: 50000; height: auto; top: 50px; left: 40px; display: block;display:none">'
    + '<div class="content"><i class="popup-arrow top black" style="left:45%"></i>'
    + '<div class="content-inner">'
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
    + '</div>'
    + '<div class="m-popup fontsize-select" style="z-index: 50000; width: 70px; height: auto; top: 30px; left: 20px; overflow: hidden;display:none;">'
    + '<div class="content"><i class="popup-arrow"></i>'
    + '<div class="content-inner">'
    + '<ul class="m-menu select-list small-font">'
    + '<li><span class="icon slt-li"></span>18</li>'
    + '<li><span class="icon"></span>20</li>'
    + '<li><span class="icon"></span>24</li>'
    + '<li><span class="icon"></span>28</li>'
    + '<li><span class="icon"></span>32</li>'
    + '<li><span class="icon"></span>36</li>'
    + '</ul>'
    + '</div>'
    + '</div>'
    + '</div>';
var sig_toolbar = '<DIV id="signatureTool_phone">'
    + '<ul class="drawTool_phone">'
    + '<li>'
    + '<a href="javascript:;" onclick="closeSignature_phone()">'
    + '<img src="' + basePath + '/readModel_phone.png" width="24" height="24">'
    + '</a>'
    + '<div>阅读</div>'
    + '</li>'
    + '<li>'
    + '<a href="javascript:;" onclick="startSignature_phone(2)">'
    + '<img src="' + basePath + '/draw_phone.png" width="24" height="24">'
    + '</a>'
    + '<div>画笔</div>'
    + '</li>'
    + '<li>'
    + '<a href="javascript:;" onclick="startSignature_phone(0)">'
    + '<img src="' + basePath + '/signature_phone.png" width="24" height="24">'
    + '</a>'
    + '<div>签字</div>'
    + '</li>'
    + '<li>'
    + '<a href="javascript:;" onclick="startSignature_phone(1)">'
    + '<img src="' + basePath + '/drawText_phone.png" width="24" height="24">'
    + '</a>'
    + '<div>文本</div>'
    + '</li>'
    + '<li class="saveImgButton">'
    + '<a href="javascript:;" onclick="saveImg()">'
    + '<img src="' + basePath + '/save_phone.png" width="24" height="24">'
    + '</a>'
    + '<div>保存</div>'
    + '</li>'
    + '</ul>'
    + '</DIV>';
var draw_panel = '<div id="signature_hint">'
    + '<span>点击你要插入签名的位置</span>'
    + '<a class="cancelSignature_phone" href="javascript:;" onclick="cancelSignature_phone()">'
    + '<img src="' + basePath + '/cancelSignature_phone.png" width="24" height="24">'
    + '</a>'
    + '</div>'
    + '<div class="canvasArea_phone">'
    + '<div class="innerCanvas" style="position:relative;width:100%;height:100%;">'
    + '<div class="canvas_hint">请在空白处签字</div>'
    + '<div class="canvas_top">'
    + '<a href="javascript:;" onclick="submitDrawImage()" class="right">'
    + '<img src="' + basePath + '/confirmCanvas_phone.png" width="44" height="32">'
    + '</a>'
    + '<a href="javascript:;" onclick="editDrawImage()" class="wrong">'
    + '<img src="' + basePath + '/cancelCanvas_phone.png" width="32" height="32">'
    + '</a>'
    + '</div>'
    + '<div class="lineWidth_select">'
    + '<i class="arrow_phone"></i>'
    + '<span id="size_span" style="">3</span>'
    + '<div id="size_bar" style="">'
    + '<span id="size_thumb" onClick="" style="left:64px">'
    + '<span style=""></span>'
    + '</span>'
    + '</div>'
    + '</div>'
    + '<div class="canvas_bottom">'
    + '<a href="javascript:;" class="open_lineWidth" onclick="open_lineWidth()">'
    + '<img src="' + basePath + '/drawMenu_phone.png" width="24" height="24">'
    + '</a>'
    + '<a href="javascript:;" class="color_select active" data-color="black" onclick="choseDrawColor(this)" style="background:black;"></a>'
    + '<a href="javascript:;" class="color_select" data-color="blue" onclick="choseDrawColor(this)" style="background:blue"></a>'
    + '<a href="javascript:;" class="color_select" data-color="red" onclick="choseDrawColor(this)" style="background:red"></a>'
    + '</div>'
    + '<canvas width="100%" height="100%" id="drawCanvas_phone" style="width:100%;height:100%;position:absolute;z-index:10"></canvas>'
    + '</div>'
    + '</div>';
var sig_sidebar = '<li id="width-marker" class="marker-op font-op" title="字号" style="display:none">'
    + '<span class="prompt">字号</span>'
    + '<span class="font-list">'
    + '<div class="combo-box-root">'
    + '<span class="combo-caption">18</span>'
    + '<span class="combo-arrow"></span>'
    + '</div>'
    + '</span>'
    + '</li>'
    + '<li id="line-width" class="marker-op width-op" style="display:none" title="线宽">'
    + '<span class="thin selected" line-width="2">'
    + '<div></div>'
    + '</span>'
    + '<span class="middle" line-width="4">'
    + '<div></div>'
    + '</span>'
    + '<span class="wide" line-width="6">'
    + '<div></div>'
    + '</span>'
    + '</li>'
    + '<li id="color-marker" class="marker-tool" title="颜色" style="display:none">'
    + '<div class="marker-icon-btn color-marker" style="background-color:#fd3f71;"></div>'
    + '</li>'
    + '<a id="btnSignature" href="javascript:;" onclick="openSignature_phone()">'
    + '<img src="' + basePath + '/signature.png" width="20" height="20" title="开启签批">'
    + '</a>';
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
$(function () {
    $.fn.autoHeight = function () {
        function autoHeight(elem) {
            elem.style.height = 'auto';
            elem.scrollTop = 0; //防抖动
            elem.style.height = elem.scrollHeight + 'px';
        }
        this.each(function () {
            autoHeight(this);
            $(this).on('keyup', function () {
                autoHeight(this);
            });
        });
    }
    $('textarea[autoHeight]').autoHeight();
})
String.prototype.getByteLen = function () {
    var len = 0;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
            len += 2;
        } else if (this.charCodeAt(i) >= 65 && this.charCodeAt(i) <= 90) {
            len += 1.5

        } else {
            len++;
        }
    }
    return len;
}
$("body").prepend(zoomCanvasHtml + pop_ups + sig_toolbar + draw_panel + loadingHtml);
$(".changePage").before(sig_sidebar);
function openSignature_phone() {
    currentPage = $(".activePage").val();
    if (!$(".word-page:eq(" + (currentPage - 1) + ")").attr("data-loaded")) {
        toastr.warning('当前页还没加载完!')
        return
    }
    currentPage = $(".activePage").val();
    isSignatureModel = true;
    if (t) t = window.clearInterval(t);
    $("#scale").val(1);
    $("#angle").val(0);
    isRotate = false;
    $("#signatureTool_phone").show();
    $(".word-page").not(":eq(" + (currentPage - 1) + ")").hide();
    $("body").removeClass("openSidebar")
    $(".changePage,#zoom,#btnSignature,#download,#btnPrint,.lnk-file-title,#footer,.endPage,#toggleSidebar,#rotateLeft,#rotateRight").hide();
    //$("#zoomCanvas").show()
    viewport.setAttribute('content', 'width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0');
    renderPage();
    changePage(currentPage);
    $(".word-page:visible").append("<canvas id='cache_canvas'>您的浏览器不支持画布</canvas>");
    $(".word-page:visible .word-content").append("<canvas id='line_canvas' stlye='z-index:5'>您的浏览器不支持画布</canvas>");
    $lineCanvas = $("#line_canvas");
    $targetObj = $("#cache_canvas");
    $stageObj = fileType == "word" ? $(".word-page:visible embed") : $(".word-page:visible img");
    $(".word-page:visible,.word-page:visible .word-content").addClass("hideShadow")
    initialCanvas();
    originalPage.width = datas[currentPage - 1].width;
    originalPage.height = datas[currentPage - 1].height;


    $("#cache_canvas").bind('mousedown touchstart', function (event) {
        dragResource = true;
        event.preventDefault();
        if (event.type == "touchstart") {
            var r = event.originalEvent.touches;
            var s = r.length;
            var o = r[0];
            if (s == 2 && !enablePinch) {
                var a = r[1];
                var c = new A(o.pageX, o.pageY);
                var h = new A(a.pageX, a.pageY);
                lastPinchArray[0] = c;
                lastPinchArray[1] = h;
                enablePinch = true;
                cancelSignature_phone();
            }
        }

        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
        origin.x = i.clientX;
        origin.y = i.clientY;
        if ($(".signatureImg.active").length > 0) {
            saveImgData()
        }
        if ($(".signatureText.active,.signatureText.focus").length > 0) {
            saveTextData()
        }
    });
    $("#cache_canvas").bind('mousemove touchmove', function (event) {
        if (!dragResource) return;
        event.preventDefault();
        if (event.type == "touchmove") {
            var r = event.originalEvent.touches;
            var s = r.length;
            if (s == 2 && enablePinch) {
                bindPinch(r)
            }
        }

        var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;
        end.x = i.clientX;
        end.y = i.clientY;
        if (isDrawModel) {
            var fromx = origin.x - $stageObj[0].getBoundingClientRect().left,
                fromy = origin.y - $stageObj[0].getBoundingClientRect().top,
                tox = end.x - $stageObj[0].getBoundingClientRect().left,
                toy = end.y - $stageObj[0].getBoundingClientRect().top;
            singleLines.push({
                "x": tox / $stageObj[0].getBoundingClientRect().width,
                "y": toy / $stageObj[0].getBoundingClientRect().height
            })
            drawLine(document.getElementById("line_canvas").getContext('2d'), fromx, fromy, tox, toy,true);
            origin.x = end.x;
            origin.y = end.y;
        } else {
            var cx = preTransformX + end.x - origin.x;
            var cy = preTransformY + end.y - origin.y;
            var stageWidth = $stageObj[0].getBoundingClientRect().width;
            var stageHeight = $stageObj[0].getBoundingClientRect().height;
            var touchPanelWidth = $targetObj[0].getBoundingClientRect().width;
            var touchPanelHeight = $targetObj[0].getBoundingClientRect().height;
            var diffWidth = stageWidth - touchPanelWidth;
            var diffHeight = stageHeight - touchPanelHeight;
            if (cx >= 0) {
                cx = 0
            }
            if (cx <= -diffWidth) {
                cx = -diffWidth
            }
            if (cy >= 0) {
                cy = 0
            }
            if (cy <= -diffHeight) {
                cy = -diffHeight
            }
            $stageObj.css({
                'transform': 'translate3d(' + cx + 'px, ' + cy + 'px, 0px) scale(' + preScale + ')'
            });
            $lineCanvas.css({
                "left": cx + "px",
                "top": cy + "px"
            })
            moveSignature()
        }



    })
    $("#cache_canvas").bind('mouseup touchend', function (event) {
        if (isDrawModel) {
            if (singleLines.length > 2) {
                drawStatus.lineArr.push({
                    "line": singleLines,
                    "line_width": $("#line-width span.selected").attr("line-width") / $lineCanvas[0].getBoundingClientRect().width,
                    "color": $("#color-marker .color-marker").css("background-color")
                });
                moveStep = moveStep + 1;
                singleLines = [];
                $(".saveImgButton").hasClass("active") ? $(".saveImgButton") : $(".saveImgButton").addClass("active")
            }
        }
        if (dragResource) {
            preTransformX = $stageObj.position().left;
            preTransformY = $stageObj.position().top;
        }
        dragResource = false;
        enablePinch = false;
    })

}

function A(e, t) {
    this.x = e;
    this.y = t;
    this.toString = function () {
        return "(x=" + this.x + ", y=" + this.y + ")"
    };
    this.interpolate = function (e, t) {
        var n = t * this.x + (1 - t) * e.x;
        var r = t * this.y + (1 - t) * e.y;
        return new A(n, r)
    };
    this.distance = function (e) {
        return Math.sqrt(Math.pow(e.y - this.y, 2) + Math.pow(e.x - this.x, 2))
    }
}
function bindPinch(r) {
    var o = r[0];
    var a = r[1];
    var f = lastPinchArray[0];
    var l = lastPinchArray[1];
    var c = new A(o.pageX, o.pageY);
    var h = new A(a.pageX, a.pageY);
    var p = c.distance(h);
    var d = f.distance(l);
    var m = p - d;
    if (Math.abs(m) < 3) return;
    lastPinchArray[0] = c;
    lastPinchArray[1] = h;
    currentScale = currentScale + 0.01 * m;
    currentScale = currentScale < 1 ? 1 : currentScale;
    currentScale = currentScale > 5 ? 5 : currentScale;
    $("#testTouchScale").val(currentScale);
    if (preScale == 1) {
        var scaleTransformX = -($(".word-page:visible").width() * (currentScale - 1)) / 2;
        var scaleTransformY = -($(".word-page:visible").height() * (currentScale - 1)) / 2;
    } else {
        scaleTransformX = preTransformX * ((currentScale - 1) / (preScale - 1))
        scaleTransformY = preTransformY * ((currentScale - 1) / (preScale - 1))
    }

    $stageObj.css({
        'transform-origin': '0px 0px 0px',
        'transform': 'translate3d(' + scaleTransformX + 'px, ' + scaleTransformY + 'px, 0px) scale(' + currentScale + ')',
        '-webkit-transform': 'translate3d(' + scaleTransformX + 'px, ' + scaleTransformY + 'px, 0px)scale(' + currentScale + ')'
    });
    $lineCanvas.css({
        "left": scaleTransformX + "px",
        "top": scaleTransformY + "px",
        "width": $targetObj.width() * currentScale + "px",
        "height": $targetObj.height() * currentScale + "px"
    }).attr({
        "width": $targetObj.width() * currentScale,
        "height": $targetObj.height() * currentScale
    })
    preTransformX = $stageObj.position().left;
    preTransformY = $stageObj.position().top;
    preScale = currentScale;

    redrawSignature();

}
function zoomCanvas(zoomType) {
    if ($(".signatureImg.active").length > 0) {
        saveImgData()
    }
    if ($(".signatureText.active,.signatureText.focus").length > 0) {
        saveTextData()
    }
    var scale = parseFloat($("#scale").val());
    if (zoomType == 1 && scale <= 2) {
        scale = scale + 0.2;
    } else if (zoomType == 0 && scale > 1) {
        scale = scale - 0.2;
    }
    $("#scale").val(scale);
    if (preScale == 1) {
        var scaleTransformX = -($(".word-page:visible").width() * (scale - 1)) / 2;
        var scaleTransformY = -($(".word-page:visible").height() * (scale - 1)) / 2;
    } else {
        scaleTransformX = preTransformX * ((scale - 1) / (preScale - 1))
        scaleTransformY = preTransformY * ((scale - 1) / (preScale - 1))
    }
    $stageObj.css({
        'transform-origin': '0px 0px 0px',
        'transform': 'translate3d(' + scaleTransformX + 'px, ' + scaleTransformY + 'px, 0px) scale(' + scale + ')',
        '-webkit-transform': 'translate3d(' + scaleTransformX + 'px, ' + scaleTransformY + 'px, 0px)scale(' + scale + ')'
    });
    $lineCanvas.css({
        "left": scaleTransformX + "px",
        "top": scaleTransformY + "px",
        "width": $targetObj.width() * scale + "px",
        "height": $targetObj.height() * scale + "px"
    }).attr({
        "width": $targetObj.width() * scale,
        "height": $targetObj.height() * scale
    })
    preTransformX = $stageObj.position().left;
    preTransformY = $stageObj.position().top;
    preScale = scale;

    redrawSignature();
}
function moveSignature() {

    for (var i = 0; i < drawStatus.imgArr.length; i++) {
        var curImgData = drawStatus.imgArr[i];
        $(".signatureImg:eq(" + i + ")").css({
            "top": curImgData.top * $stageObj[0].getBoundingClientRect().height + $stageObj.position().top + "px",
            "left": curImgData.left * $stageObj[0].getBoundingClientRect().width + $stageObj.position().left + "px"
        })
    }
    for (var i = 0; i < drawStatus.textArr.length; i++) {
        var curTextData = drawStatus.textArr[i];
        $(".signatureText:eq(" + i + ")").css({
            "top": curTextData.top * $stageObj[0].getBoundingClientRect().height + $stageObj.position().top + "px",
            "left": curTextData.left * $stageObj[0].getBoundingClientRect().width + $stageObj.position().left + "px"

        }).find("textarea").css({
            "top": curTextData.top * $stageObj[0].getBoundingClientRect().height + $stageObj.position().top + "px",
            "left": curTextData.left * $stageObj[0].getBoundingClientRect().width + $stageObj.position().left + "px"
        })
    }

}
function redrawSignature() {
    // if ($(".signatureImg.active").length > 0) {
    //     saveImgData()
    // }
    // if ($(".signatureText.active,.signatureText.focus").length > 0) {
    //     saveTextData()
    // };
    for (var i = 0; i < drawStatus.imgArr.length; i++) {
        var curImgData = drawStatus.imgArr[i];
        $(".signatureImg:eq(" + i + ")").css({
            "top": curImgData.top * $stageObj[0].getBoundingClientRect().height + $stageObj.position().top + "px",
            "left": curImgData.left * $stageObj[0].getBoundingClientRect().width + $stageObj.position().left + "px",
            "width": curImgData.width * $stageObj[0].getBoundingClientRect().width + "px",
            "height": curImgData.height * $stageObj[0].getBoundingClientRect().height + "px"
        })
    }
    for (var i = 0; i < drawStatus.textArr.length; i++) {
        var curTextData = drawStatus.textArr[i];
        var thisFontSize = Math.round(curTextData.font_size * $stageObj[0].getBoundingClientRect().width);

        var thisLineHeight = Math.round(curTextData.line_height * $stageObj[0].getBoundingClientRect().height);
        $(".signatureText:eq(" + i + ")").css({
            "top": curTextData.top * $stageObj[0].getBoundingClientRect().height + $stageObj.position().top + "px",
            "left": curTextData.left * $stageObj[0].getBoundingClientRect().width + $stageObj.position().left + "px",
            "width": curTextData.width * $stageObj[0].getBoundingClientRect().width + "px",
            "height": curTextData.height * $stageObj[0].getBoundingClientRect().height + "px"
        }).find("textarea").css({
            "top": curTextData.top * $stageObj[0].getBoundingClientRect().height + $stageObj.position().top + "px",
            "left": curTextData.left * $stageObj[0].getBoundingClientRect().width + $stageObj.position().left + "px",
            "width": curTextData.width * $stageObj[0].getBoundingClientRect().width + "px",
            "height": curTextData.height * $stageObj[0].getBoundingClientRect().height + "px",
            "font-size": thisFontSize + "px",
            "line-height": thisLineHeight + "px"
        })
    }
    //draw lineArr
    var this_line_canvasContext = document.getElementById("line_canvas").getContext("2d");
    for (var i = 0; i < drawStatus.lineArr.length; i++) {
        var thisLine = drawStatus.lineArr[i];
        this_line_canvasContext.lineWidth = drawStatus.lineArr[i].line_width * $lineCanvas[0].getBoundingClientRect().width;
        this_line_canvasContext.strokeStyle = drawStatus.lineArr[i].color;
        for (var j = 0; j < thisLine.line.length - 1; j++) {
            drawLine(this_line_canvasContext,
                thisLine.line[j].x * $lineCanvas[0].getBoundingClientRect().width,
                thisLine.line[j].y * $lineCanvas[0].getBoundingClientRect().height,
                thisLine.line[j + 1].x * $lineCanvas[0].getBoundingClientRect().width,
                thisLine.line[j + 1].y * $lineCanvas[0].getBoundingClientRect().height,
                false
            )
        }
    }
}
function saveTextData() {
    //恢复键盘弹出之前保存的高度
    clearInterval(focusTextInterval);
    isDrawModel = false;
    $("#signature_hint").hide();
    if ($(".signatureText.focus").length > 0) $(".signatureText.focus").css("top", currentTextTop + "px");
    var currentTextStatus = $(".signatureText.focus").length > 0 ? $(".signatureText.focus")
        : $(".signatureText.active").length > 0 ? $(".signatureText.active") : $(".signatureText");
    currentTextStatus
    if (currentTextStatus.find("textarea").val() == "") {
        $(".m-popup.fontsize-select,#width-marker,#color-marker,m-popup.color-select").hide()
        currentTextStatus.remove();
        return;
    };
    var this_fontSize = currentTextStatus.find("textarea").css("font-size");
    var this_lineHeight = currentTextStatus.find("textarea").css("line-height");
    var k = parseInt(this_lineHeight.substring(0, this_lineHeight.length - 2));
    var l = parseInt(this_fontSize.substring(0, this_fontSize.length - 2))
    if (currentTextStatus.attr("isCreat")) {
        currentTextStatus.removeAttr("isCreat");
        moveStep++;
        drawStatus.textArr.push({
            "top": (currentTextStatus.position().top - $stageObj.position().top + (k -l) / 2) / $stageObj[0].getBoundingClientRect().height,
            "left": (currentTextStatus.position().left - $stageObj.position().left) / $stageObj[0].getBoundingClientRect().width,
            "width": currentTextStatus.width() / $stageObj[0].getBoundingClientRect().width,
            "height": currentTextStatus.height() / $stageObj[0].getBoundingClientRect().height,
            "textVal": currentTextStatus.find("textarea").val(),
            "font_size": this_fontSize.substring(0, this_fontSize.length - 2) / $stageObj[0].getBoundingClientRect().width,
            "line_height": this_lineHeight.substring(0, this_lineHeight.length - 2) / $stageObj[0].getBoundingClientRect().height,
            "font_color": currentTextStatus.find("textarea").css("color")
        })
    } else {
        drawStatus.textArr[$(".signatureText").index(currentTextStatus)] = {
            "top": (currentTextStatus.position().top - $stageObj.position().top + (k -l) / 2) / $stageObj[0].getBoundingClientRect().height,
            "left": (currentTextStatus.position().left - $stageObj.position().left) / $stageObj[0].getBoundingClientRect().width,
            "width": currentTextStatus.width() / $stageObj[0].getBoundingClientRect().width,
            "height": currentTextStatus.height() / $stageObj[0].getBoundingClientRect().height,
            "textVal": currentTextStatus.find("textarea").val(),
            "font_size": this_fontSize.substring(0, this_fontSize.length - 2) / $stageObj[0].getBoundingClientRect().width,
            "line_height": this_lineHeight.substring(0, this_lineHeight.length - 2) / $stageObj[0].getBoundingClientRect().height,
            "font_color": currentTextStatus.find("textarea").css("color")
        }
    }
    currentTextStatus.find(".saveText_phone").hide();
    $(".signatureText.active .text-rect,.signatureText.active .se-resize").unbind();
    $(document).unbind();
    $(".m-popup.fontsize-select,#width-marker,#color-marker,m-popup.color-select").hide()
    $(".signatureText.focus").removeClass("focus").attr("onclick", "activeText(this)").find("textarea").attr("readonly", "readonly");
    $(".signatureText.active").removeClass("active").find("textarea").attr("readonly", "readonly");


}
function saveImgData() {
    isDrawModel = false;
    $("#signature_hint").hide();
    if ($(".signatureImg.active").attr("isCreat")) {
        $(".signatureImg.active").removeAttr("isCreat");

        drawStatus.imgArr.push({
            "top": ($(".signatureImg.active").position().top - $stageObj.position().top) / $stageObj[0].getBoundingClientRect().height,
            "left": ($(".signatureImg.active").position().left - $stageObj.position().left) / $stageObj[0].getBoundingClientRect().width,
            "width": $(".signatureImg.active").width() / $stageObj[0].getBoundingClientRect().width,
            "height": $(".signatureImg.active").height() / $stageObj[0].getBoundingClientRect().height,
            "imgData": $(".signatureImg.active").find("img").attr("src")
        })
    } else {
        drawStatus.imgArr[$(".signatureImg").index($(".signatureImg.active"))] = {
            "top": ($(".signatureImg.active").position().top - $stageObj.position().top) / $stageObj[0].getBoundingClientRect().height,
            "left": ($(".signatureImg.active").position().left - $stageObj.position().left) / $stageObj[0].getBoundingClientRect().width,
            "width": $(".signatureImg.active").width() / $stageObj[0].getBoundingClientRect().width,
            "height": $(".signatureImg.active").height() / $stageObj[0].getBoundingClientRect().height,
            "imgData": $(".signatureImg.active").find("img").attr("src")
        }
    }
    $(".signatureImg.active img,.signatureImg.active .se-resize").unbind();
    $(document).unbind();
    $(".signatureImg.active").removeClass("active").find(".se-resize,.deleteImageData").hide();
}

function closeSignature_phone() {
    isSignatureModel = false;
    $("#scale").val(1);
    if (t) t = self.setInterval("intervalPage()", 5000);
    $(".word-page:visible,.word-page:visible .word-content").removeClass("hideShadow");

    $("#signatureTool_phone").hide();
    $(".changePage,#zoom,#btnSignature,#download,#btnPrint,.lnk-file-title,#footer,.endPage,#toggleSidebar,#rotateLeft,#rotateRight").show();
    $(".word-page").not(":eq(" + (currentPage - 1) + ")").show();
    $("#zoomCanvas").hide()
    viewport.setAttribute('content', 'width=device-width, initial-scale=1.0');
    $(".signatureImg.active img,.signatureImg.active .se-resize").unbind();
    $(".signatureImg,#cache_canvas,#line_canvas,.signatureText").remove();
    intialDrawData();
    changePage(currentPage);
    renderPage();
    $(".container-fluid-content").scroll(function (e) {
        bindActiveIndex();
    })

}

function startSignature_phone(signatureType) {
    if (signatureType == 0) {
        isDrawModel = false;
        if ($(".signatureText.active,.signatureText.focus").length > 0) {
            saveTextData();
        }
        $("#signature_hint span").html("点击你要插入签名的位置");
        $("#signature_hint").show();

        startBindDocument(signatureType);
    } else if (signatureType == 1) {
        isDrawModel = false;
        if ($(".signatureImg.active").length > 0) {
            saveImgData();
        }
        $("#signature_hint span").html("点击你要插入文本的位置");
        $("#signature_hint").show();
        startBindDocument(signatureType);
    } else if (signatureType == 2) {
        if ($(".signatureText.active,.signatureText.focus").length > 0) {
            saveTextData();
        }
        if ($(".signatureImg.active").length > 0) {
            saveImgData();
        }
        $("#signature_hint span").html("在指定区域绘图");
        $("#signature_hint").show();
        $("#line-width,#color-marker").show()
        isDrawModel = true

    }

}

function cancelSignature_phone() {
    $(".container-fluid-content").unbind();
    $("#signature_hint").hide();
    $("#line-width,#color-marker").hide()
    isDrawModel = false;
}

function initialCanvas() {
    drawArea.x = $(".word-page:visible").width();
    drawArea.y = $(".word-page:visible").height();
    $("#cache_canvas,#line_canvas").css({
        "width": $(".word-page:visible").width() + "px",
        "height": $(".word-page:visible").height() + "px",
    }).attr({
        "width": $(".word-page:visible").width(),
        "height": $(".word-page:visible").height()
    })

};

function open_lineWidth() {
    $(".lineWidth_select").is(":visible") ? $(".lineWidth_select").hide() : $(".lineWidth_select").show()
}

function startBindDocument(signatureType) {
    $(".container-fluid-content").bind('mousedown touchstart', function (event) {
        if ($(".toast-container").is(':visible')) return;
        event.preventDefault();
        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
        coordinate.x = i.clientX - $(".word-page:visible").offset().left;
        coordinate.y = i.clientY - $(".word-page:visible").offset().top;
        if (coordinate.x < 0 || coordinate.y < 0 || coordinate.x > drawArea.x || coordinate.y > drawArea.y) {
            toastr.warning('请点击文件区域!');
        } else {
            $(".container-fluid-content").unbind();
            $("#signature_hint").hide();
            if (signatureType == 0) {
                $(".canvasArea_phone").show();
                resiezeCanvas();
                bindDrawCanvas();
            } else if (signatureType == 1) {
                $(".word-page:visible").append(displayTextTemplate).find(".signatureText.focus").css({
                    "left": coordinate.x + "px",
                    "top": coordinate.y + "px",

                }).find("textarea").css({
                    "color": textFontColor
                })
                bindTextInput();
                currentTextTop = coordinate.y;
                dragResource = false;
                focusTextInterval = setInterval(function () {
                    bindFocusTextInterval();
                }, 100);
                $("#width-marker,#color-marker").show()
                $(".saveImgButton").hasClass("active") ? $(".saveImgButton") : $(".saveImgButton").addClass("active")
            }
        }

    });
};

function editThisText(e) {
    currentTextTop = $(e).parent().parent().position().top;

    $(".signatureText.active .text-rect,.signatureText.active .se-resize").unbind();
    $(document).unbind();
    $(e).parent().parent().removeAttr("onclick").removeClass("active").find(".saveText_phone").hide();
    $("#width-marker,#color-marker").show()
    $(e).parent().parent().addClass("focus").find("textarea").removeAttr("readonly").focus();
    focusTextInterval = setInterval(function () {
        bindFocusTextInterval();
    }, 100)
}
function bindFocusTextInterval() {
    var focusElement = $(".signatureText.focus");
    var scrollElement = $(".container-fluid-content")
    if (focusElement.length == 0) clearInterval(focusTextInterval);
    if (scrollElement.height() - (focusElement.offset().top - $(".navbar").height()) < focusElement.height() + 30) {
        focusElement.css({
            "top": scrollElement.height() + scrollElement.scrollTop() - focusElement.height() - 30 + "px",
        }).find("textarea").focus();
    } else {
        focusElement.css({
            "top": currentTextTop + "px",
        })
    }
}
function deleteThisText(e) {
    $(e).parent().parent().remove();
    var curTextIndex = $(".signatureText").index($(e).parent().parent());
    drawStatus.textArr.splice(curTextIndex, 1);
    moveStep = moveStep - 1;
    if (moveStep == 0) {
        $(".saveImgButton").hasClass("active") ? $(".saveImgButton").removeClass("active") : $(".saveImgButton")
    }
}
function activeText(e) {
    if ($(".signatureImg.active").length > 0) {
        saveImgData();
        return
    }
    if ($(".signatureText.active,.signatureText.focus").length > 0 &&
        $(".signatureText").index($(e)) != $(".signatureText").index($(".signatureText.active"))) {
        saveTextData();
        return
    };

    $(e).addClass("active").find(".saveText_phone").css({
        "left": ($(e).width() - 120) / 2 + "px"
    }).show();
    bindDragText();

}
function bindTextInput() {
    $(".signatureText.focus").find("textarea").focus();
    $('textarea').bind('input propertychange keyup', function (e) {
        var textScrollHeight = $(this).scrollTop();
        var originalHeight = $(this).height();
        if (textScrollHeight == 0) {

        } else {
            $(this).css({
                "height": textScrollHeight + originalHeight + "px"
            }).parent().parent().css({
                "height": textScrollHeight + originalHeight + "px"
            })
        }

    });
}
$(window).resize(function (e) {
    resiezeCanvas();
})
resiezeCanvas();

function intialDrawData() {
    limitArea = { x_min: 0, x_max: 2000, y_min: 0, y_max: 2000 };
    singleLines = [];
    isDrawModel = false;
    lastPinchArray = [];
    preScale = 1;
    currentScale = 1;
    moveStep = 0
    coordinateXArray = [];
    coordinateYArray = [];
    drawStatus = {
        imgArr: [],
        textArr: [],
        lineArr: []
    };
    displayImgData = {
        "left": 0,
        "top": 0,
        "width": 0,
        "height": 0
    };
    $(".saveImgButton").removeClass("active")
}

function bindDrawCanvas() {
    $("#drawCanvas_phone").bind('mousedown touchstart', function (event) {
        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
        drawable = true;
        origin.x = i.clientX;
        origin.y = i.clientY;
        coordinateXArray.push(i.clientX);
        coordinateYArray.push(i.clientY);
        drawColor = $(".color_select.active").attr("data-color");
        drawLineWidth = parseInt($("#size_span").html()) * 2;
        $(".lineWidth_select").hide();
        lastTimestamp = new Date().getTime()
    }).bind('mousemove touchmove', function (event) {

        if (drawable == false) return;

        event.preventDefault();
        var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;
        coordinateXArray.push(i.clientX);
        coordinateYArray.push(i.clientY);
        end.x = i.clientX;
        end.y = i.clientY;


        if (coordinateXArray.length > 2) {

            draw(document.getElementById("drawCanvas_phone").getContext('2d'));

            $(".canvasArea_phone a.right").show();
        }
        origin.x = end.x;
        origin.y = end.y;
    }).bind('mouseup touchend', function (event) {
        drawable = false;

    })
}


function bindDragText() {
    $(".signatureText.active .text-rect,.signatureText.active .se-resize").bind('mousedown touchstart', function (event) {
        dragable = true;
        event.preventDefault();
        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
        origin.x = i.clientX;
        origin.y = i.clientY;
        diplayTextData.left = $(".signatureText.active").position().left;
        diplayTextData.top = $(".signatureText.active").position().top;
        diplayTextData.width = $(".signatureText.active").width();
        diplayTextData.height = $(".signatureText.active").height();
        if ($(event.target).attr("drag-id") == "0") {
            dragType = 0
        } else if ($(event.target).attr("drag-id") == "1") {
            dragType = 1
        };

    });
    $(document).bind('mousemove touchmove', function (event) {
        if (!dragable) return;
        event.preventDefault();
        var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;

        end.x = i.clientX;
        end.y = i.clientY;
        if (dragType == 0) {
            var curLeft = diplayTextData.left + (end.x - origin.x);
            var curTop = diplayTextData.top + (end.y - origin.y);
            if (curLeft <= 10) {
                curLeft = 10
            } else if (curLeft + diplayTextData.width + 10 >= $("#cache_canvas").width()) {
                curLeft = $("#cache_canvas").width() - diplayTextData.width - 10
            } else if (curTop <= 10) {
                curTop = 10
            } else if (curTop + diplayTextData.height + 10 >= $("#cache_canvas").height()) {
                curTop = $("#cache_canvas").height() - diplayTextData.height - 10
            }



            $(".signatureText.active,.signatureText.active textarea").css({
                "left": curLeft + "px",
                "top": curTop + "px"
            })

        } else if (dragType == 1) {
            var curWidth = diplayTextData.width + (end.x - origin.x);
            var curHeight = diplayTextData.height + (end.y - origin.y)
            $(".signatureText.active,.signatureText.active textarea").css({
                "width": diplayTextData.width + (end.x - origin.x) + "px",
                "height": diplayTextData.height + (end.y - origin.y) + "px"
            })
        }
        $(".signatureText.active").find(".saveText_phone").hide();
    });
    $(document).bind('mouseup touchend', function (event) {
        dragable = false;
        if (!$(".saveText_phone").is("visible")) {
            var this_img_width = $('.signatureText.active').width();
            $(".signatureText.active").find(".saveText_phone").css({
                "left": (this_img_width - 120) / 2 + "px"
            }).show()
        }
    })
}
function bindDragImg() {
    $(".signatureImg.active img,.signatureImg.active .se-resize").bind('mousedown touchstart', function (event) {
        dragable = true;
        event.preventDefault();
        var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
        origin.x = i.clientX;
        origin.y = i.clientY;
        displayImgData.left = $(".signatureImg.active").position().left;
        displayImgData.top = $(".signatureImg.active").position().top;
        displayImgData.width = $(".signatureImg.active").width();
        displayImgData.height = $(".signatureImg.active").height();
        currentImgScale = $(".signatureImg.active").width() / $(".signatureImg.active").height();
        if ($(event.target).attr("drag-id") == "0") {
            dragType = 0
        } else if ($(event.target).attr("drag-id") == "1") {
            dragType = 1
        };

    })
    $(document).bind('mousemove touchmove', function (event) {
        if (!dragable) return;
        event.preventDefault();
        var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;
        end.x = i.clientX;
        end.y = i.clientY;
        $(".signatureImg.active").find(".deleteImageData").hide();
        if (dragType == 0) {
            $(".signatureImg.active").css({
                "left": displayImgData.left + (end.x - origin.x) + "px",
                "top": displayImgData.top + (end.y - origin.y) + "px"
            })
        } else if (dragType == 1) {
            $(".signatureImg.active").css({
                "width": displayImgData.width + (end.x - origin.x) + "px",
                "height": (displayImgData.width + end.x - origin.x) / currentImgScale + "px"
            })
        }
    });
    $(document).bind('mouseup touchend', function (event) {
        dragable = false;
        if (!$(".deleteImageData").is("visible")) {
            var this_img_width = $('.signatureImg.active').width();
            $(".signatureImg.active").find(".deleteImageData").css({
                "left": (this_img_width - 50) / 2 + "px"
            }).show()
        }
    })
}

function deleteImageData(e) {
    var curImageIndex = $(".signatureImg").index($(e).parent().parent().parent());
    $(e).parent().parent().parent().remove();
    drawStatus.imgArr.splice(curImageIndex, 1);
    moveStep = moveStep - 1;
    if (moveStep == 0) {
        $(".saveImgButton").hasClass("active") ? $(".saveImgButton").removeClass("active") : $(".saveImgButton")
    }
}

function focusImg(e) {
    if ($(".signatureText.active,.signatureText.focus").length > 0) {
        saveTextData();
        return;
    }
    if ($(".signatureImg.active").length > 0 &&
        $(".signatureImg").index($(e)) != $(".signatureImg").index($(".signatureImg.active"))) {
        saveImgData();
        return
    }
    $(e).addClass("active").find(".se-resize,.deleteImageData").show();
    bindDragImg();

}

function editDrawImage() {
    resiezeCanvas();
    $(".canvasArea_phone").hide();
    $("#drawCanvas_phone").unbind();
}

function submitDrawImage() {
    limitArea.x_min = Math.min.apply(null, coordinateXArray);
    limitArea.x_max = Math.max.apply(null, coordinateXArray);
    limitArea.y_min = Math.min.apply(null, coordinateYArray);
    limitArea.y_max = Math.max.apply(null, coordinateYArray);
    $("#drawCanvas_phone").unbind();
    var clipImage = new Image();
    var thisSrc = document.getElementById("drawCanvas_phone").toDataURL("image/png");
    var picH = limitArea.y_max - limitArea.y_min + 2 * clipBorder;
    var picW = limitArea.x_max - limitArea.x_min + 2 * clipBorder;
    var picScale = picW / picH
    clipImage.src = thisSrc;
    var outputCanvas = document.createElement('canvas');
    outputCanvas.width = picW;
    outputCanvas.height = picH;
    var outputCanvasContext = outputCanvas.getContext('2d');
    clipImage.onload = function () {
        outputCanvasContext.drawImage(clipImage, limitArea.x_min - clipBorder, limitArea.y_min - clipBorder, picW, picH, 0, 0, picW, picH);
        var compressWidth = 50;
        var compressHeight = 50 / picScale;
        $(".canvasArea_phone").hide();
        $(".word-page:visible").append(displayImgTemplate).find(".signatureImg.active").css({
            "top": coordinate.y - compressHeight / 2 + "px",
            "left": coordinate.x + "px",
            "width": compressWidth + "px",
            "height": compressHeight + "px"
        }).find("img").attr({
            "src": outputCanvas.toDataURL("image/png")
        })
        bindDragImg();
        dragResource = false;
        moveStep = moveStep + 1;
        $(".saveImgButton").hasClass("active") ? $(".saveImgButton") : $(".saveImgButton").addClass("active")
    }
}

function saveImg() {
    clearInterval(focusTextInterval);
    if ($(".signatureImg.active").length > 0) saveImgData();
    if ($(".signatureText.active").length > 0 || $(".signatureText.focus").length > 0) saveTextData();
    if (moveStep == 0) return
    $("body").append("<canvas id='save_canvas' style='position:absolute;left:-1000%'></canvas");

    var saveCanvas = document.getElementById('save_canvas');
    var saveCanvasContext = saveCanvas.getContext('2d');
    var factor = 4;
    saveCanvas.width = originalPage.width * factor;
    saveCanvas.height = originalPage.height * factor;

    saveCanvas.style.width = originalPage.width + "px";
    saveCanvas.style.height = originalPage.width + "px";
    saveCanvasContext.scale(factor, factor)
    //draw img
    for (var i = 0; i < drawStatus.imgArr.length; i++) {
        var curImgData = drawStatus.imgArr[i];

        preImage(curImgData.imgData, function (x, y, width, height) {
            saveCanvasContext.save();
            saveCanvasContext.drawImage(this, x, y, width, height);
            saveCanvasContext.restore();

        }, {
                "x": curImgData.left * originalPage.width,
                "y": curImgData.top * originalPage.height,
                "width": curImgData.width * originalPage.width,
                "height": curImgData.height * originalPage.height
            });
    };

    //draw text
    for (var i = 0; i < drawStatus.textArr.length; i++) {
        var curTextData = drawStatus.textArr[i];
        var thisFontSize = parseInt(curTextData.font_size * originalPage.width);
        var thisLineHeight = parseInt(curTextData.line_height * originalPage.height);
        saveCanvasContext.beginPath()
        saveCanvasContext.font = thisFontSize + "px 宋体";
        saveCanvasContext.fillStyle = curTextData.font_color;
        canvasTextAutoLine(
            curTextData.textVal, saveCanvasContext,
            curTextData.width * originalPage.width, curTextData.left * originalPage.width,
            curTextData.top * originalPage.height + thisFontSize
            , thisLineHeight
        );

        saveCanvasContext.save();
    };
    //draw line
    for (var i = 0; i < drawStatus.lineArr.length; i++) {
        var thisLine = drawStatus.lineArr[i];
        saveCanvasContext.lineWidth = drawStatus.lineArr[i].line_width * originalPage.width;
        saveCanvasContext.strokeStyle = drawStatus.lineArr[i].color;
        for (var j = 0; j < thisLine.line.length - 1; j++) {
            drawLine(saveCanvasContext,
                thisLine.line[j].x * originalPage.width,
                thisLine.line[j].y * originalPage.height,
                thisLine.line[j + 1].x * originalPage.width,
                thisLine.line[j + 1].y * originalPage.height,
                false
            )
        }
    }
    $("#loading").show();
    saveSuccessCallBack(saveCanvas);


}
function saveSuccessCallBack(saveCanvas) {
    //window.open(saveCanvas.toDataURL("image/png"), "_blank");
    $.ajax({
        url: signatureUrl,
        data: {
            "page": currentPage,
            "imgData": saveCanvas.toDataURL('image/png'),
            "fileUrl": fileUrl,
            "folderUrl": folderUrl,
            "filePath": filePath,
            "Type": Type
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#loading').hide();
            if (data.result == 0) {
                fileUrl = data.data;
                $("#download").attr("href", data.data);
                var pageImage = new Image();
                var thisSrc = basePath + "/" + currentPage + "." + imgType + "?ver=" + Math.random()
                pageImage.src = thisSrc
                $(pageImage).bind("load", function () {

                    if ("svg" == imgType) {
                        $(".word-content:visible").find("embed").remove();
                        $(".word-content:visible").append('<embed src="' + thisSrc + '" width="100%" height="100%" type="image/svg+xml"></embed>')
                    } else if ("png" == imgType) {
                        $(".word-content:visible").find("img").remove();
                        $(".word-content:visible").append('<img src="' + thisSrc + '" width="100%" height="100%" />')
                    };
                    $("#save_canvas").remove();
                    closeSignature_phone();
                })


            } else {
                toastr.warning(data.message + '!')
            }
        },
        error: function (data) {
            $('#loading').hide();
            toastr.error('网络错误!')
            closeSignature_phone();
        }
    });

}
function canvasTextAutoLine(str, ctx, textWidth, initX, initY, lineHeight) {
    var lineWidth = 0;
    var lastSubStrIndex = 0;
    for (var i = 0; i < str.length; i++) {
        if (str[i] == "\n") {
            ctx.fillText(str.substring(lastSubStrIndex, i), initX, initY);
            initY += lineHeight;
            lineWidth = 0;
            lastSubStrIndex = i + 1;
            lineWidth = 0;
        }
        else {
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
function preImage(url, callback, wo) {
    var img = new Image();
    img.src = url;
    if (img.complete) {
        callback.call(img, wo.x, wo.y, wo.width, wo.height);
        return;
    }
    img.onload = function () {
        callback.call(this, wo.x, wo.y, wo.width, wo.height);
    };
}

function calcLineWidth(t, s) {
    var v = s / t;
    var resultLineWidth;

    if (v <= 0.1) {
        resultLineWidth = drawLineWidth;
    } else if (v >= 5) {
        resultLineWidth = 2
    } else {
        resultLineWidth = drawLineWidth - (v - 0.1) / (5 - 0.1) * (drawLineWidth - 2);
    }
    if (lastLineWidth == -1) {

        return resultLineWidth
    }
    return lastLineWidth * 2 / 3 + resultLineWidth * 1 / 3
}
function calcDistance(loc1, loc2) {
    return Math.sqrt((loc1.x - loc2.x) * (loc1.x - loc2.x) + (loc1.y - loc2.y) * (loc1.y - loc2.y))  //通过起始结束坐标x,y值计算路程长度
}
function draw(context, fromx, fromy, tox, toy) {


        var curTimestamp = new Date().getTime();
        var s = calcDistance(end, origin)
        var t = curTimestamp - lastTimestamp;
        var lineWidth = calcLineWidth(t, s)
        context.strokeStyle = drawColor;
        context.lineWidth = lineWidth;
        var this_origin_x = fromx ? fromx : origin.x;
        var this_origin_y = fromy ? fromy : origin.y;
        var this_end_x = tox ? tox : end.x;
        var this_end_y = toy ? toy : end.y;
        context.beginPath();
        context.moveTo(this_origin_x, this_origin_y);
        context.lineTo(this_end_x, this_end_y);
        context.lineCap = "round"
        context.linJoin = "round"
        context.stroke();
        lastTimestamp = curTimestamp


}
function drawLine(context, fromx, fromy, tox, toy, isSave) {
    if(isSave){
        context.strokeStyle = $("#color-marker .color-marker").css("background-color");
        context.lineWidth = $("#line-width span.selected").attr("line-width");
    }
    context.beginPath();
    context.moveTo(fromx, fromy);
    context.lineTo(tox, toy);
    context.lineCap = "round"
    context.linJoin = "round"
    context.stroke();

}
function resiezeCanvas() {
    limitArea = { x_min: 0, x_max: 2000, y_min: 0, y_max: 2000 };
    coordinateXArray = [];
    coordinateYArray = [];
    $(".canvasArea_phone a.right").hide();
    $("#drawCanvas_phone").attr({
        "width": document.documentElement.clientWidth,
        "height": document.documentElement.clientHeight
    }).css({
        "width": document.documentElement.clientWidth + "px",
        "height": document.documentElement.clientHeight + "px"
    })
}

function size_bar_move(event) {
    var i = event.type == "touchmove" ? event.originalEvent.targetTouches[0] : event;
    var thumb = $("#size_thumb");
    var main_w = $("#size_bar").width();
    var mainLeft = $("#size_bar").offset().left;
    if (i.clientX - mainLeft < 0)
        thumb.css("left", -thumb.width() / 2 + "px");
    else if (i.clientX - mainLeft > main_w)
        thumb.css("left", main_w - thumb.width() / 2 + "px");
    else
        thumb.css("left", i.clientX - mainLeft - thumb.width() / 2 + "px");
    $("#size_span").html(Math.ceil($(thumb).position().left / main_w * 5) + 1);
}
$("#size_bar").bind("mousedown touchstart", function (event) {
    var i = event.type == "touchstart" ? event.originalEvent.targetTouches[0] : event;
    var thumb = $("#size_thumb");
    var main_w = $(this).width();
    var mainLeft = $(this).offset().left;
    thumb.css("left", i.clientX - mainLeft - thumb.width() / 2 + "px");
    $("#size_span").html(Math.ceil($(thumb).position().left / main_w * 5) + 1);
    $(document).bind("mousemove touchmove", size_bar_move);
    $(document).bind("mouseup touchend", function unbind(event) {
        $(document).unbind();
    });
});

function choseDrawColor(e) {
    $(".color_select").removeClass("active");
    $(e).addClass("active")
}
$("#line-width span").click(function (e) {
    $(this).addClass("selected").siblings().removeClass("selected");
})
$(".font-list").click(function (e) {
    $(".hideToolbar,.color-select").hide();
    if ($(".m-popup.fontsize-select").is(":hidden")) {
        $(".m-popup.fontsize-select").show();
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
    $(".signatureText.focus").find("textarea").css("font-size", $(this).text() + "px")
})
$(".color-marker").click(function (e) {
    $(".fontsize-select").hide();
    if ($(".m-popup.color-select").is(":hidden")) {
        $(".m-popup.color-select").css({
            "left": $(e.target).offset().left - 67 < 0 ? 0 : ($(e.target).offset().left - 67) + "px"
        }).show();
    } else {
        $(".m-popup.color-select").hide();
    }
});
$(".color-list li").click(function (e) {
    $(this).addClass("selected").siblings().removeClass("selected");
    $(".m-popup.color-select").hide();
    $(".color-marker").css("background-color", $(this).css("background-color"));
    textFontColor = $(this).css("background-color");
    $(".signatureText.focus").find("textarea").css("color", $(this).css("background-color"))
})