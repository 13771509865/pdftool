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
    $(".lnk-file-title").hide();
}
var a = $.parseJSON(widthlist);
datas = a.data;
var initialPage = 5;
var fileType = a.fileType;
var n = 1;//每次加载个数
var loadPageNum = datas.length <= initialPage ? datas.length : initialPage;
for (var i = 0; i < loadPageNum; i++) {
    var activeWordPage = $(".word-page:eq('" + i + "')");
    if (!activeWordPage.attr("data-loaded")) {
        if (fileType == "pdf") {
            activeWordPage.find(".word-content")
                .append('<img src="' + basePath + '/' + (i + 1) + '.png" width="100%" height="100%"/>')
                .parent(".word-page").attr("data-loaded", true);

        } else if (fileType == "word") {
            activeWordPage.find(".word-content")
                .append('<embed src="' + basePath + '/' + (i + 1) + '.svg" width="100%" height="100%" type="image/svg+xml"/></embed>')
                .parent(".word-page").attr("data-loaded", true);
        } else if (fileType == "svg") {
            activeWordPage.find(".word-content")
                .append('<embed src="' + basePath + '/file0001.svg" width="100%" height="100%" type="image/svg+xml"/></embed>')
                .parent(".word-page").attr("data-loaded", true);
        }
    }
}
$(".totalPage").html("/" + datas.length);
$("#pageNum span:eq(1)").html(datas.length);
$(".container-fluid-content").scroll(function (e) {
    bindActiveIndex();
})
bindActiveIndex();
$(".activePage").bind('keydown', function (event) {
    if (event.keyCode == "13") {
        changePage($(this).val())
    }
});
$(window).resize(function (e) {
    renderPage()
}).resize();
function renderPage() {
    var screenWidth = document.documentElement.clientWidth || window.innerWidth;
    var screenHeight = document.documentElement.clientHeight || window.innerHeight;
    var bottom_foot = $("#footer").length > 0 ? 40 : 0;

    $(".container-fluid-content").css({
        "width": screenWidth + "px",
        "height": (screenHeight - 40 - bottom_foot) + "px"
    });
    var docWidth = screenWidth - 20;
    var scale = parseFloat($("#scale").val());
    $("#pageNum").css("left", screenWidth / 2 - 50)
    $(".word-page").each(function (index, e) {
        var ratio = datas[index].height / datas[index].width;
        if (datas[index].width >= docWidth) {
            $(this).css({
                "width": docWidth * scale + "px",
                "height": docWidth * ratio * scale + "px",
                "line-height": docWidth * ratio * scale + "px"
            }).find("img,embed").css({
                "width": docWidth * scale + "px",
                "height": docWidth * ratio * scale + "px",
                "top": "0px",
                "left": "0px"
            });
        } else if (docWidth > datas[index].width) {
            $(this).css({
                "width": datas[index].width * scale + "px",
                "height": datas[index].height * scale + "px",
                "line-height": datas[index].height * scale + "px"
            }).find("img,embed").css({
                "width": datas[index].width * scale + "px",
                "height": datas[index].height * scale + "px",
                "top": "0px",
                "left": "0px"
            });
        }
    });
    bindActiveIndex();
}
function changePage(index) {
    var reg = /^[0-9]*[1-9][0-9]*$/;
    if (!reg.test(index)) {
    } else if (index > datas.length || index <= 0) {
    } else {
        var pos = $(".word-page:eq('" + (index - 1) + "')").position().top + 5;
        $(".activePage").val(index);
        $(".container-fluid-content").animate({ scrollTop: pos }, 0);
    }
}
function slidePage(direction) {
    var currentIndex = $(".activePage").val();
    if (direction == 0) {
        if (currentIndex > 1) {
            currentIndex--;
            changePage(currentIndex)
        }
    } else if (direction == 1) {
        if (currentIndex < datas.length) {
            currentIndex++;
            changePage(currentIndex)
        }
    }
}
function bindActiveIndex() {
    var screenHeight = document.documentElement.clientHeight || window.innerHeight;
    var scrollHeight = $(".container-fluid-content").scrollTop();
    var activeIndex = 0;
    $(".word-page").each(function (index, e) {
        if (index > 0) {
            if ($(this).position().top - screenHeight / 2 - scrollHeight < 0) {
                activeIndex = scrollHeight == 0 ? 0 : index
            }
        }
    });
    $(".activePage").val(activeIndex + 1);
    $("#pageNum span:eq(0)").html(activeIndex + 1);
    loadPage(activeIndex)
}
function loadPage(index) {
    for (var i = index - n; i <= index + n; i++) {
        var activeWordPage = $(".word-page:eq('" + i + "')");
        if (i > 0 && i < datas.length) {
            if (!activeWordPage.attr("data-loaded")) {
                if (fileType == "pdf") {
                    activeWordPage.find(".word-content")
                        .append('<img src="' + basePath + '/' + (i + 1) + '.png" width="100%" height="100%"/>')
                        .parent(".word-page").attr("data-loaded", true);
                } else if (fileType == "word") {
                    activeWordPage.find(".word-content")
                        .append('<embed src="' + basePath + '/' + (i + 1) + '.svg" width="100%" height="100%" type="image/svg+xml"/></embed>')
                        .parent(".word-page").attr("data-loaded", true);
                }
            }
        }
    }
}