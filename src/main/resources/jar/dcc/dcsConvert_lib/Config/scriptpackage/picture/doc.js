/**
 *     __  ___
 *    /  |/  /___   _____ _____ ___   ____   ____ _ ___   _____
 *   / /|_/ // _ \ / ___// ___// _ \ / __ \ / __ `// _ \ / ___/
 *  / /  / //  __/(__  )(__  )/  __// / / // /_/ //  __// /
 * /_/  /_/ \___//____//____/ \___//_/ /_/ \__, / \___//_/
 *                                        /____/
 *
 * @description MessengerJS, a common cross-document communicate solution.
 * @author biqing kwok
 * @version 2.0
 * @license release under MIT license
 */
var fileType = a.fileType.toLowerCase();
var sUserAgent = navigator.userAgent.toLowerCase();
var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
var bIsMidp = sUserAgent.match(/midp/i) == "midp";
var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
var bIsAndroid = sUserAgent.match(/android/i) == "android";
var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
var deg = 0;
var fg = true;
window.Messenger = (function () {
    var prefix = "[PROJECT_NAME]",
        supportPostMessage = 'postMessage' in window;

    function Target(target, name, prefix) {
        var errMsg = '';
        if (arguments.length < 2) {
            errMsg = 'target error - target and name are both required';
        } else if (typeof target != 'object') {
            errMsg = 'target error - target itself must be window object';
        } else if (typeof name != 'string') {
            errMsg = 'target error - target name must be string type';
        }
        if (errMsg) {
            throw new Error(errMsg);
        }
        this.target = target;
        this.name = name;
        this.prefix = prefix;
    }

    if (supportPostMessage) {
        Target.prototype.send = function (msg) {
            this.target.postMessage(this.prefix + '|' + this.name + '__Messenger__' + msg, '*');
        };
    } else {
        Target.prototype.send = function (msg) {
            var targetFunc = window.navigator[this.prefix + this.name];
            if (typeof targetFunc == 'function') {
                targetFunc(this.prefix + msg, window);
            } else {
                throw new Error("target callback function is not defined");
            }
        };
    }

    function Messenger(messengerName, projectName) {
        this.targets = {};
        this.name = messengerName;
        this.listenFunc = [];
        this.prefix = projectName || prefix;
        this.initListen();
    }

    Messenger.prototype.addTarget = function (target, name) {
        var targetObj = new Target(target, name, this.prefix);
        this.targets[name] = targetObj;
    };

    Messenger.prototype.initListen = function () {
        var self = this;
        var generalCallback = function (msg) {
            if (typeof msg == 'object' && msg.data) {
                msg = msg.data;
            }

            var msgPairs = msg.split('__Messenger__');
            var msg = msgPairs[1];
            var pairs = msgPairs[0].split('|');
            var prefix = pairs[0];
            var name = pairs[1];

            for (var i = 0; i < self.listenFunc.length; i++) {
                if (prefix + name === self.prefix + self.name) {
                    self.listenFunc[i](msg);
                }
            }
        };

        if (supportPostMessage) {
            if ('addEventListener' in document) {
                window.addEventListener('message', generalCallback, false);
            } else if ('attachEvent' in document) {
                window.attachEvent('onmessage', generalCallback);
            }
        } else {
            window.navigator[this.prefix + this.name] = generalCallback;
        }
    };

    Messenger.prototype.listen = function (callback) {
        var i = 0;
        var len = this.listenFunc.length;
        var cbIsExist = false;
        for (; i < len; i++) {
            if (this.listenFunc[i] == callback) {
                cbIsExist = true;
                break;
            }
        }
        if (!cbIsExist) {
            this.listenFunc.push(callback);
        }
    };
    Messenger.prototype.clear = function () {
        this.listenFunc = [];
    };
    Messenger.prototype.send = function (msg) {
        var targets = this.targets,
            target;
        for (target in targets) {
            if (targets.hasOwnProperty(target)) {
                targets[target].send(msg);
            }
        }
    };

    return Messenger;
})();
var messenger = new Messenger('previewArea');
messenger.listen(function (msg) {
    if (msg == "printDoc") {
        printDoc();
    }
});

var printLoadImg = 0;

function printDoc() {
    var loadImg = $("#printArea img").size();
    if (loadImg == datas.length) {
        bindPrint();
    } else {
        for (var i = 0; i < datas.length; i++) {
            if (fileType == "word") {
                $("#printArea").append('<img src="' + basePath + '/' + (i + 1) + '.svg" style="width:595pt;height:841pt;"/>')
            } else if (fileType == "pdf") {
                $("#printArea").append('<img src="' + basePath + '/' + (i + 1) + '.png" style="width:595pt;height:841pt;"/>')
            } else {
                $("#printArea").append('<img src="' + basePath + '/file0001.' + fileType + '" style="width:595pt;height:841pt;"/>');

            }
        }
    }


    $('#printArea img').load(function () {
        printLoadImg++
        if (printLoadImg == datas.length - loadImg) {
            bindPrint();
            printLoadImg = 0;
        }

    });
}

function bindPrint() {
    $(".navbar,#pageNum,#fullScreen,.container-fluid-content,#zoom,#footer").hide();
    $("body").removeClass("word-body");
    $("#printArea").show();
    if (getExplorer() == "IE") {
        pagesetup_null();
    }
    window.focus();
    window.print();
    $("#printArea").hide();
    $("body").addClass("word-body")
    $(".navbar,#pageNum,#fullScreen,.container-fluid-content,#zoom,#footer").show();

}

function pagesetup_null() {
    var hkey_root, hkey_path, hkey_key;
    hkey_root = "HKEY_CURRENT_USER";
    hkey_path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
    try {
        var RegWsh = new ActiveXObject("WScript.Shell");
        hkey_key = "header";
        RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
        hkey_key = "footer";
        RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
    } catch (e) {}
}

function getExplorer() {
    var explorer = window.navigator.userAgent;
    //ie 
    if (explorer.indexOf("MSIE") >= 0) {
        return "IE";
    }
    //firefox 
    else if (explorer.indexOf("Firefox") >= 0) {
        return "Firefox";
    }
    //Chrome
    else if (explorer.indexOf("Chrome") >= 0) {
        return "Chrome";
    }
    //Opera
    else if (explorer.indexOf("Opera") >= 0) {
        return "Opera";
    }
    //Safari
    else if (explorer.indexOf("Safari") >= 0) {
        return "Safari";
    }
}

if (fileType == "pdf") {
    document.write("<script  src='" + basePath + "/cZoom.js'></script>")
} else if (fileType == "jpg" || fileType == "png" || fileType == "bmp" || fileType == "tiff" || fileType == "gif" || fileType == "svg") {
    document.write("<script  src='" + basePath + "/rotate.js'></script>")
}
$(function () {
    $('.lnk-file-title').css('margin-left', ($('.container-fluid').width() - $('.lnk-file-title').width() - 40) / 2).show()
    if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
        $('.btn_box').show()
    }
    var width = $(window).width()
    var height = $(window).height()
    $('.docMark').width(width)
    $('.attribute').css('margin-top', (height - 229) / 2)
    $('.docMark').height(height)
    $.each(authority, function (index, ele) {
        var html = '';
        if (ele["isShow"]) {
            if (index == 2) {
                html += '<li onClick="showAttribute()"><img src="' + ele.image + '"><span>' + ele.title + '</span></li>'
            } else if (index == 1) {
				html += '<li><a style="text-decoration:none; color:#373737; font-size: 12px; font-weight: 400;" href="'+ele.url+'"  ><img src="' + ele.image + '"/><span>' + ele.title + '</span></a></li>'
			} else {
                html += '<li><img src="' + ele.image + '"><span>' + ele.title + '</span></li>'
            }
            $('.more-content_btn').append(html)
        }
    })
    $.each(docAttribute, function (index, ele) {
        var html = '';
        html += '<p><span>' + ele.title + ':</span><span class="attr_detail">' + ele.data + '</span></p>'
        $('.attr_content').append(html)
    })
})
$(window).resize(function () {
    $('.lnk-file-title').css('margin-left', ($('.container-fluid').width() - $('.lnk-file-title').width() - 40) / 2)
    if ($(window).width() < 630) {
        $('.btn_box').hide()
    } else {
        $('.btn_box').show()
    }
    var width = $(window).width()
    var height = $(window).height()
    $('.docMark').width(width)
    $('.docMark').height(height)
    $('.docMark').width(width)
    $('.attribute').css('margin-top', (height - 229) / 2)
    $('.docMark').height(height)
})

function showMore_content() {
    if (fg) {
        $('.more-content').show()
        fg = false;
    } else {
        $('.more-content').hide()
        fg = true;
    }
}

function showAttribute() {
    $('.more-content').hide()
    $('.docMark').show()
}

function offAttribute() {
    $('.docMark').hide()
    fg = true;
}

function rotateLeft() {
    $('.row-fluid img').css({
        "transform": "rotate(" + (deg + 90) + "deg)",
        "-ms-transform": "rotate(" + (deg + 90) + "deg)",
        "-moz-transform": "rotate(" + (deg + 90) + "deg)",
        "-webkit-transform": "rotate(" + (deg + 90) + "deg)",
        "-o-transform": "rotate(" + (deg + 90) + "deg)",
    })
    deg += 90
    deg = deg == 360 ? 0 : deg
}

function rotateRight() {
    $('.row-fluid img').css({
        "transform": "rotate(" + (deg - 90) + "deg)",
        "-ms-transform": "rotate(" + (deg - 90) + "deg)",
        "-moz-transform": "rotate(" + (deg - 90) + "deg)",
        "-webkit-transform": "rotate(" + (deg - 90) + "deg)",
        "-o-transform": "rotate(" + (deg - 90) + "deg)",
    })
    deg -= 90
    deg = deg == -360 ? 0 : deg
}

var box = document.getElementById("box");
var fa = document.getElementById('father');
box.onmousedown = function (ev) {
    var ev = ev;
    // 浏览器有一些图片的默认事件,这里要阻止
    ev.preventDefault();
    var disX = ev.clientX - box.offsetLeft;
    var disY = ev.clientY - box.offsetTop;
    fa.onmousemove = function (ev) {
        ev = ev;
        ev.preventDefault();
        var x = ev.clientX - disX;
        var y = ev.clientY - disY;
        // 图形移动的边界判断
        // x = x <= 0 ? 0 : x;
        // x = x >= fa.offsetWidth - box.offsetWidth ? fa.offsetWidth - box.offsetWidth : x;
        // y = y <= 0 ? 0 : y;
        // y = y >= fa.offsetHeight - box.offsetHeight ? fa.offsetHeight - box.offsetHeight : y;
        box.style.left = x + 'px';
        box.style.top = y + 'px';
    }
    // 图形移出父盒子取消移动事件,防止移动过快触发鼠标移出事件,导致鼠标弹起事件失效
    fa.onmouseleave = function () {
        fa.onmousemove = null;
        fa.onmouseup = null;
    }
    // 鼠标弹起后停止移动
    fa.onmouseup = function () {
        fa.onmousemove = null;
        fa.onmouseup = null;
    }
}
var imgScale = 1;
var imgWidth = datas[0].width;
var imgHeight = datas[0].height;
$('#box').bind('mousewheel', function (event, delta, deltaX, deltaY) {
    var startHeight = event.target.offsetHeight
    var startWidth = event.target.offsetWidth
    var startTop = event.target.offsetTop
    var startLeft = event.target.offsetLeft
    imgScale = delta > 0 ? imgScale += 0.2 : imgScale -= 0.2
    imgScale = imgScale < 0.2 ? 0.2 : imgScale
    imgScale = imgScale > 3 ? 3 : imgScale
    $(this).css({
        "width": imgWidth * imgScale,
        "height": imgHeight * imgScale,
        "top": function () {
            return (startHeight - $(this)[0].offsetHeight) / 2 + startTop
        },
        "left": function () {
            return (startWidth - $(this)[0].offsetWidth) / 2 + startLeft
        },
    })
})

//以下为移动端图片手势操作
$(function () {
    var $targetObj = $('#box');
    //初始化设置
    cat.touchjs.init($targetObj, function (left, top, scale, rotate) {
        $targetObj.css({
            left: left,
            top: top,
            'transform': 'scale(' + scale + ') rotate(' + rotate + 'deg)',
            '-webkit-transform': 'scale(' + scale + ') rotate(' + rotate + 'deg)'
        });
    });
    //初始化缩放手势（不需要就注释掉）
    cat.touchjs.scale($targetObj, function (scale) {
        $('#scale').text(scale);
    });
    // //初始化旋转手势（不需要就注释掉）
    // cat.touchjs.rotate($targetObj, function (rotate) {
    //     $('#rotate').text(rotate);
    // });
});
//保存并刷新
// function save() {
//     var data = {
//         left: cat.touchjs.left,
//         top: cat.touchjs.top,
//         scale: cat.touchjs.scaleVal,
//         rotate: cat.touchjs.rotateVal
//     };
//     //本地存储
//     window.localStorage.cat_touchjs_data = JSON.stringify(data);
//     window.location = window.location;
// };
// //重置
// function reset() {
//     var data = {
//         left: 0,
//         top: 0,
//         scale: 1,
//         rotate: 0
//     };
//     //本地存储
//     window.localStorage.cat_touchjs_data = JSON.stringify(data);
//     window.location = window.location;
// };

//以下为拖动图片
var flag = false;
var cur = {
    x: 0,
    y: 0
}
var nx, ny, dx, dy, x, y;

function down() {
    flag = true;
    var touch;
    if (event.touches) {
        touch = event.touches[0];
    } else {
        touch = event;
    }
    cur.x = touch.clientX;
    cur.y = touch.clientY;
    dx = div2.offsetLeft;
    dy = div2.offsetTop;
}

function move() {
    if (flag) {
        var touch;
        if (event.touches) {
            touch = event.touches[0];
        } else {
            touch = event;
        }
        nx = touch.clientX - cur.x;
        ny = touch.clientY - cur.y;
        x = dx + nx;
        y = dy + ny;
        div2.style.left = x + "px";
        div2.style.top = y + "px";
        //阻止页面的滑动默认事件
        // document.addEventListener("touchmove", function(event) {
        // 	// event.preventDefault();
        // }, false);
    }
}
//鼠标释放时候的函数
function end() {
    flag = false;
}
var div2 = document.getElementById("box");
div2.addEventListener("mousedown", function () {
    down();
}, false);
div2.addEventListener("touchstart", function () {
    down();
}, false)
div2.addEventListener("mousemove", function () {
    move();
}, false);
div2.addEventListener("touchmove", function () {
    move();
}, false)
document.body.addEventListener("mouseup", function () {
    end();
}, false);
div2.addEventListener("touchend", function () {
    end();
}, false);

function FullScreen(el,e) {
    var isFullscreen = document.fullScreen || document.mozFullScreen || document.webkitIsFullScreen;
    if (!isFullscreen) { //进入全屏,多重短路表达式
        (el.requestFullscreen && el.requestFullscreen()) ||
        (el.mozRequestFullScreen && el.mozRequestFullScreen()) ||
        (el.webkitRequestFullscreen && el.webkitRequestFullscreen()) || (el.msRequestFullscreen && el.msRequestFullscreen());
        e.currentTarget.title='退出全屏'
        $('.more-content').hide()
    } else { //退出全屏,三目运算符
        document.exitFullscreen ? document.exitFullscreen() :
        document.mozCancelFullScreen ? document.mozCancelFullScreen() :
        document.webkitExitFullscreen ? document.webkitExitFullscreen() : '';
        e.currentTarget.title='全屏'
        $('.more-content').hide()
    }
}

function toggleFullScreen(e) {
    var el = document.getElementsByClassName('row-fluid')[0];
    FullScreen(el,e);
}