var a = $.parseJSON(v);
datas = a.data;
var initialPage = 5;
var fileType = a.fileType;
var n = 1; //每次加载个数
var loadPageNum = datas.length <= initialPage ? datas.length : initialPage;
var scale = 1;
//初始化element
(function() {
    var pageElement = "";
    datas.forEach(function(o, i) {
        pageElement += '<DIV class="word-page">' +
            '<DIV class="word-content"></DIV>' +
            '</DIV>';
    });
    docElement = '<div id="header">ppt文档</div>' +
        '<DIV class="sidebar"></DIV>' +
        '<DIV id="printArea" STYLE="display:none"></DIV>' +
        '<DIV class="container-fluid container-fluid-content">' +
        '<DIV class="row-fluid">' +
        '<DIV class="span12 docArea">' + pageElement +
        '</DIV>' +
        '</DIV>' +
        '</DIV>' +
        '<div class="pageNumber" id="pageNumber">' +
        '<span class="pageNumber-text" id="pageNumber-text"></span>' +
        '</div>';
    $("body").prepend(docElement);
})();
$(window).resize(function(e) {
    renderPage()
}).resize();
for (var i = 0; i < loadPageNum; i++) {
    var activeWordPage = $(".word-page:eq('" + i + "')");
    if (!activeWordPage.attr("data-loaded")) {
        if (fileType == "pdf") {
            activeWordPage.find(".word-content")
                .append('<img src="' + basePath + '/' + (i + 1) + '.png" width="100%" height="100%"/>')
                .parent(".word-page").attr("data-loaded", true);

        } else if (fileType == "word" || fileType == "ppt") {
            activeWordPage.find(".word-content")
                .append('<embed src="' + basePath + '/' + (i + 1) + '.svg" width="100%" height="100%" type="image/svg+xml"/></embed>')
                .parent(".word-page").attr("data-loaded", true);
        }
    }
}
$(".totalPage").html("/" + datas.length);
$("#pageNum span:eq(1)").html(datas.length);
$(".pageNumber-text").html('1/' + datas.length)
$(".container-fluid-content").scroll(function(e) {
    bindActiveIndex();
})
bindActiveIndex();

$(".activePage").bind('keydown', function(event) {
    if (event.keyCode == "13") {
        changePage($(this).val())
    }
});

var timer = self.setInterval("bindToolbar()", 500)
var scrollPosition = $(".container-fluid-content").scrollTop();
var scolling = false;

function bindToolbar() {
    var currentPosition = $(".container-fluid-content").scrollTop()
    if (currentPosition != scrollPosition) {
        scrollPosition = currentPosition;
        $("#pageNumber").addClass("visible");
        scolling = true;
    } else {
        scolling = false;
        setTimeout(
            "ifHide()", 1000)
    }
}

function ifHide() {
    if ($(".container-fluid-content").scrollTop() == scrollPosition && scolling == false) {
        $("#pageNumber").removeClass("visible");
    }
}

function LoadRs(type, filePath) {
    var p = type == "js" ? document.body : document.getElementsByTagName("head")[0];
    var o = type == "js" ? document.createElement("script") : document.createElement("link");
    type == "js" ? (o.type = "text/javascript", o.src = filePath) : (o.type = "text/css", o.rel = "stylesheet", o.href = filePath);
    p.appendChild(o);
}

function renderPage() {
    var screenWidth = document.documentElement.clientWidth || window.innerWidth;
    var screenHeight = document.documentElement.clientHeight || window.innerHeight;
    var bottom_foot = $("#footer").length > 0 ? 40 : 0;
    var header = $(".navbar").is(':visible') ? $(".navbar").height() : 0;
    $(".container-fluid-content").css({
        "width": screenWidth + "px",
        "height": (screenHeight - header - bottom_foot) + "px",
        "top": header + "px"
    });
    var docWidth = screenWidth - 20;
    $("#pageNum").css("left", screenWidth / 2 - 50)
    $(".word-page").each(function(index, e) {
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
    if (!reg.test(index)) {} else if (index > datas.length || index <= 0) {} else {
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
    $(".word-page").each(function(index, e) {
        if (index > 0) {
            if ($(this).position().top - screenHeight - scrollHeight < 0) {
                activeIndex = scrollHeight == 0 ? 0 : index
            }
        }
    });
    $(".activePage").val(activeIndex + 1);
    $("#pageNum span:eq(0)").html(activeIndex + 1);
    $(".pageNumber-text").html((activeIndex + 1) + '/' + datas.length)
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
                } else if (fileType == "word" || fileType == "ppt") {
                    activeWordPage.find(".word-content")
                        .append('<embed src="' + basePath + '/' + (i + 1) + '.svg" width="100%" height="100%" type="image/svg+xml"/></embed>')
                        .parent(".word-page").attr("data-loaded", true);
                }
            }
        }
    }
}