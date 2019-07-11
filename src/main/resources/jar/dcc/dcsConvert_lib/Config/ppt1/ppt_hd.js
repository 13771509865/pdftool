var nowpage = 0;
$(function() {
    if (isMeeting && !isSpeaker) {
        window.addEventListener('message', function(e) {
            var msg = JSON.parse(e.data);
            switch (msg.type) {
                case 'page':
                    {
                        play(msg.param);
                        break;
                    }
            }
        }, false);
    }



    //初始化ppt element
    (function() {
        var pptElement = '<DIV id="loading" class="loader">' +
            '<DIV></DIV>' +
            '</DIV>' +
            '<DIV id="sidebar"></DIV>' +
            '<DIV id="mainbody">' +
            '    <DIV class="word-page" STYLE="width:100%;height:100%;display:table;">' +
            '        <DIV></DIV>' +
            '        <DIV class="word-content"></DIV>' +
            '        <DIV></DIV>' +
            '    </DIV>' +
            '</DIV>' +
            '<div class="pptPageSwitch">' +
            '    <div class="wrapper">' +
            '        <div class="leftSwitch" title="上一页">' +
            '            <div class="leftSwitchBtn components-icons components-icons-angle-left" id="leftSwitchBtn"></div>' +
            '       </div>' +
            '       <div class="pptPageContent" id="pptPageContent">1/20</div>' +
            '      <div class="rightSwitch" title="下一页">' +
            '          <div class="rightSwitchBtn components-icons components-icons-angle-right" id="rightSwitchBtn"></div>' +
            '      </div>' +
            '  </div>' +
            '  <div class="fullscreenBtb" id="fullscreenBtbIcon" title="全屏">' +
            '</div>' +
            '</div>'
        $("body").append(pptElement)
    })();




    var loader = {
            el: $('#loading div'),
            per: 30,
            set: function(n) {
                this.per = n;
                this.el.width(n + '%');
            },
            fake: function(isfinished) {
                if (isfinished) {
                    this.set(100);
                    clearTimeout(this.timmer);
                    return;
                }
                var self = this;
                this.timmer = setTimeout(function() {
                    self.set(self.per += 10);
                    self.fake();
                }, 400);
            }
        },
        title = $('#title').hide(),
        sidebar = $('#sidebar').hide(),
        mainbody = $('#mainbody').hide(),
        thumbnail,
        datas,
        currentIndex = false,
        play = function(index) {
            nowpage = index;
            var data = datas[index];
            if (typeof(data) == "undefined") return false;
            loader.set((index + 1) * 100 / thumbnail.length);
            if (currentIndex !== false) thumbnail.eq(currentIndex).removeClass("active");
            currentIndex = index;
            $("#pptPageContent").html((currentIndex + 1) + "/" + datas.length);
            var dom = thumbnail.eq(index).addClass("active")[0];
            if (dom.scrollIntoViewIfNeeded) dom.scrollIntoViewIfNeeded();
            $('.word-content').html('<embed src="' + basePath + '/' + (index + 1) + '.svg" width="100%" type="image/svg+xml"></embed>');
            index == 0 ? prevBtn.addClass("unclick") : prevBtn.removeClass("unclick");
            index == thumbnail.length - 1 ? nextBtn.addClass("unclick") : nextBtn.removeClass("unclick");
            changeSourceHeight()
            if (isSpeaker) {
                var msg = {};
                msg.type = 'page';
                msg.param = index;
                parent.postMessage(JSON.stringify(msg), serverhost);
            }
        },
        next = function() {
            play(currentIndex + 1);
        },
        prev = function() {
            play(currentIndex - 1);
        },
        nextBtn = $('#rightSwitchBtn').click(next),
        prevBtn = $('#leftSwitchBtn').click(prev),
        fullScreen = function() {
            var docElm = document.body;

            if (docElm.requestFullscreen) {
                docElm.requestFullscreen();
            } else if (docElm.mozRequestFullScreen) {
                docElm.mozRequestFullScreen();
            } else if (docElm.webkitRequestFullScreen) {
                docElm.webkitRequestFullScreen();
            } else if (docElm.msRequestFullscreen) {
                docElm.msRequestFullscreen();
            }

        },
        editFullScreen = function() {
            var de = document;
            if (de.exitFullscreen) {
                //W3C
                de.exitFullscreen();
            } else if (de.mozCancelFullScreen) {
                //FIREFOX
                de.mozCancelFullScreen();
            } else if (de.webkitCancelFullScreen) {
                //CHROME
                de.webkitCancelFullScreen();
            } else if (de.msExitFullscreen) {
                //MSIE
                de.msExitFullscreen();
            } else if (de.oRequestFullscreen) {
                de.oCancelFullScreen();
            }
        },
        fullScreenChange = function(x) {
            $('body').toggleClass('fullScreen', x);
            $("#fullscreenBtbIcon").toggleClass("exit");
        },
        changeSourceHeight = function() {
            var mainbodyWidth = $("#mainbody").width();
            var mainbodyHeight = $("#mainbody").height();
            var currentPageRatio = datas[nowpage].width / datas[nowpage].height
            if (mainbodyWidth / mainbodyHeight > currentPageRatio) {
                mainbodyHeight = mainbodyHeight >= datas[nowpage].height ? datas[nowpage].height : mainbodyHeight
                $(".word-content").css({
                    "width": mainbodyHeight * currentPageRatio + "px",
                    "height": mainbodyHeight + "px"
                })
            } else {
                mainbodyWidth = mainbodyWidth >= datas[nowpage].width ? datas[nowpage].width : mainbodyWidth
                $(".word-content").css({
                    "width": mainbodyWidth + "px",
                    "height": mainbodyWidth / currentPageRatio + "px"
                })
            }
        };
    $('#fullscreenBtbIcon').click(function(e) {
        $(e.target).hasClass("exit") ? editFullScreen() : fullScreen()
    });

    $('#sideBtn').click(function() {
        $('body').toggleClass('openSide');
        changeSourceHeight()
    });
    document.addEventListener("fullscreenchange", function() {
        fullScreenChange(document.fullscreen);
    });
    document.addEventListener("mozfullscreenchange", function() {
        fullScreenChange(document.mozFullScreen);
    });
    document.addEventListener("webkitfullscreenchange", function() {
        fullScreenChange(document.webkitIsFullScreen);
    });
    document.addEventListener("MSFullscreenChange", function() {
        fullScreenChange(document.msFullscreenElement);
    });
    loader.fake();


    loadData(v);
    $(window).resize(function() {
        var screenHeight = document.documentElement.clientHeight || window.innerHeight;
        var screenWidth = document.documentElement.clientWidth || window.innerWidth;
        $('body').removeClass("openSide");
        var header = $("#header").is(':visible') ? $("#header").height() : 0;
        var bottom = $(".pptPageSwitch").is(':visible') ? $(".pptPageSwitch").height() : 0;
        $("#mainbody").css({
            "height": (screenHeight - 20 - header - bottom) + "px",
            "top": header + "px",
        });
        changeSourceHeight();
    }).resize();

    function loadData(v) {
        loader.fake(true);
        var a = eval('(' + v + ')');
        datas = a.data;
        //title.text(a.name);
        var s = "",
            len = datas.length;
        datas.forEach(function(o, i) {
            s += '<div class="thumbnail" data-index="' + i + '"><img src="' + basePath + '/' + o.index + '.jpg"><div class="side-pager">' + (i + 1 + '/' + len) + '</div></div>';
        });
        thumbnail = sidebar.html(s).children().on('click', function() {
            play($(this).data('index'));
        });
        play(0);
        setTimeout(function() {
            title.fadeIn('fast');
            sidebar.fadeIn();
            mainbody.fadeIn('slow');

        }, 450);

        $(document).keydown(function(event) {
            if (event.which == "37" || event.which == "38") { prev() }
            if (event.which == "39" || event.which == "40") { next() }
        });
    }
});