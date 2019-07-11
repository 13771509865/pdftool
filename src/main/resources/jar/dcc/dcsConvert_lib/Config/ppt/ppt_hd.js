$(function() {
	if(isMeeting && !isSpeaker) {
		window.addEventListener('message', function(e) {
			var msg = JSON.parse(e.data);
			switch(msg.type) {
				case 'page':
					{
						play(msg.param);
						break;
					}
			}
		}, false);
	}
	var nowpage = 0;
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
	if((bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
		$(".full").css('display', 'none');
		$("#fullScreen").css('display', 'none');
	}
	$(".word-content").swipe({
		swipe: function(event, direction, distance, duration, fingerCount) {
			if(direction == "right" || direction == "down") {
				if(nowpage <= 0) {
					nowpage = 0;
				} else {
					nowpage--;
				}
				play(nowpage);
			} else if(direction == "left" || direction == "up") {
				if(nowpage >= datas.length - 1) {
					nowpage = datas.length - 1;
				} else {
					nowpage++;
				}
				play(nowpage);
			}
		}
	});

	$('body').bind("selectstart", function() {
		return false;
	});
	if(document.documentElement.clientWidth <= 960) {
		$('body').removeClass("openSide")
	} else {
		$('body').addClass("openSide")
	}

    $(window).resize(function () {
        $("#mainbody").height
        if ($("body").hasClass("fullScreen")) {
            $("body").removeClass("openSide")
        } else {
            if (document.documentElement.clientWidth <= 960) {
                $('body').removeClass("openSide")
            } else {
                $('body').addClass("openSide")
            }
        }
    });
    $(window).resize(function (e) {
        var screenHeight = document.documentElement.clientHeight || window.innerHeight;
        var screenWidth = document.documentElement.clientWidth || window.innerWidth;
        $("#mainbody").css({
            "height": (screenHeight - 60) + "px"
        })
    }).resize()
    var loader = {
        el: $('#loading div'),
        per: 30,
        set: function (n) {
            this.per = n;
            this.el.width(n + '%');
        },
        fake: function (isfinished) {
            if (isfinished) {
                this.set(100);
                clearTimeout(this.timmer);
                return;
            }
            var self = this;
            this.timmer = setTimeout(function () {
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
        play = function (index) {
            nowpage = index;
            var data = datas[index];
            if (typeof (data) == "undefined") return false;
            loader.set((index + 1) * 100 / thumbnail.length);
            if (currentIndex !== false) thumbnail.eq(currentIndex).removeClass("active");
            currentIndex = index;
            var dom = thumbnail.eq(index).addClass("active")[0];
            if (dom.scrollIntoViewIfNeeded) dom.scrollIntoViewIfNeeded();
            if (isPhone) {
                $('.box_div').html('<img src="' + data.url + '" width="100%"/>');
            } else {
                $('.box_div').html('<embed src="' + data.url + '" width="100%" type="image/svg+xml"></embed>');
            }
            prevBtn.toggle(index !== 0);
            nextBtn.toggle(index !== thumbnail.length - 1);
            if (isSpeaker) {
                var msg = {};
                msg.type = 'page';
                msg.param = index;
                parent.postMessage(JSON.stringify(msg), serverhost);
            }
			setTimeout(function () {
				$('.box_div div').remove();
                initData();
            }, 50)
        },
        next = function () {
            play(currentIndex + 1);
        },
        prev = function () {
            play(currentIndex - 1);
        },
        nextBtn = $('#next').click(next),
        prevBtn = $('#prev').click(prev),
        fullScreen = function () {
            var docElm = document.body;
            if (docElm.requestFullscreen) {
				docElm.requestFullscreen();
			} else if(docElm.mozRequestFullScreen) {
				docElm.mozRequestFullScreen();
			} else if(docElm.webkitRequestFullScreen) {
				docElm.webkitRequestFullScreen();
			} else if(docElm.msRequestFullscreen) {
				docElm.msRequestFullscreen();
			}
		},
		fullScreenChange = function(x) {
			$('body').toggleClass('fullScreen', x);
			if(navigator.userAgent.toUpperCase().indexOf("Firefox")) {
				if($("body").hasClass("fullScreen")) {
					$('body').removeClass("openSide");
				} else {
					$('body').addClass("openSide");
				}
			}
		};
	$('#fullScreen').click(fullScreen);
	$('#sideBtn').click(function() {
		$('body').toggleClass('openSide');
		timeOut();
	});
	document.addEventListener("fullscreenchange", function() {
		fullScreenChange(document.fullscreen);
		timeOut();
	});
	document.addEventListener("mozfullscreenchange", function() {
		fullScreenChange(document.mozFullScreen);
		timeOut();
	});
	document.addEventListener("webkitfullscreenchange", function() {
		fullScreenChange(document.webkitIsFullScreen);
		timeOut();
	});
	document.addEventListener("MSFullscreenChange", function() {
		fullScreenChange(document.msFullscreenElement);
        timeOut();
	});
	loader.fake();

	function timeOut() {
		setTimeout(function() {
			$('.box_div div').remove();
			initData();
		}, 100)
	}

	loadData(v);

	function loadData(v) {
		loader.fake(true);
		var a = eval('(' + v + ')');
		datas = a.data;
		title.text(a.name);
		var s = "",
			len = datas.length;
		datas.forEach(function(o, i) {
			s += '<div class="thumbnail" data-index="' + i + '"><img src="' + o.thumbUrl + '"><div class="side-pager">' + (i + 1 + '/' + len) + '</div></div>';
		});
		thumbnail = sidebar.html(s).children().on('click', function() {
			play($(this).data('index'));
		});
		play(0);
		setTimeout(function() {
			$('.box_div div').remove();
			title.fadeIn('fast');
			sidebar.fadeIn();
			mainbody.fadeIn('slow');
			initData();
		}, 500);
		$(document).keydown(function(event) {
			if(event.which == "37" || event.which == "38") {
				prev()
			}
			if(event.which == "39" || event.which == "40") {
				next()
			}
		});
	}
});

