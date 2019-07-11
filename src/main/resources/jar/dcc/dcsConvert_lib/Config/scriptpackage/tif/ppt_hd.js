var nowpage = 0;
var flag = true;
var a = $.parseJSON(v);
datas = a.data;
var scrollFunc = function (e) {}
window.onmousewheel = document.onmousewheel = scrollFunc;
var isZoom = false;
var currrentScale = 1;
var cw, cr
var deg = 0;

function showAttribute() {
	$('.docMark').show()
	$('.more-content').removeClass('selected')
	$('.attr_Title span').show()
}

function offAttribute() {
	$('.docMark').hide()
}
$(function () {
	if (isMeeting && !isSpeaker) {
		window.addEventListener('message', function (e) {
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
	(function () {
		var pptElement = '<DIV id="loading" class="loader">' +
			'<DIV></DIV>' +
			'</DIV>' +
			'<DIV id="sidebar"></DIV>' +
			'<DIV id="mainbody" style="background-color: #E6E6E6;">' +
			'    <DIV class="word-page" STYLE="width:100%;height:100%;display:table;background-color:#E6E6E6;">' +
			'        <DIV></DIV>' +
			'        <DIV class="word-content"></DIV>' +
			'        <DIV></DIV>' +
			'    </DIV>' +
			'</DIV>' +
			'    <div class="wrapper_two">' +
			'        <div class="leftSwitch" title="上一页">' +
			'            <div class="leftSwitchBtn components-icons components-icons-angle-left style_two" id="leftSwitchBtn_two"></div>' +
			'       </div>' +
			'       <div class="pptPageContent_two" id="pptPageContent_two">1/20</div>' +
			'      <div class="rightSwitch" title="下一页">' +
			'          <div class="rightSwitchBtn components-icons components-icons-angle-right style_two" id="rightSwitchBtn_two"></div>' +
			'      </div>' +
			'     </div>'
		'<div class="pptPageSwitch">' +
		'    <div class="wrapper">' +
		'        <div class="leftSwitch" title="上一页">' +
		'            <div class="leftSwitchBtn components-icons components-icons-angle-left" id="leftSwitchBtn"></div>' +
		'       </div>' +
		'       <div class="pptPageContent" id="pptPageContent">1/20</div>' +
		'       <div class="rightSwitch" title="下一页">' +
		'          <div class="rightSwitchBtn components-icons components-icons-angle-right" id="rightSwitchBtn"></div>' +
		'      </div>' +
		'     </div>' +
		'  <div class="fullscreenBtb" id="fullscreenBtbIcon" title="全屏">' +
		'</div>' +
		'  <div class="remarksBtb" id="remarksBtbIcon"><span>备注</span></div>' +
		'</div>'
		$("body").append(pptElement)
		var screen_width = $(window).width()
		var screen_height = $(window).height()
		var width = $('.wrapper').width()
		var mar = (screen_width - width) / 2
		$('.wrapper_two').css('margin-top', screen_height - 70)
		$('.wrapper').css('margin-left', mar)
		$.each(authority, function (index, ele) {
			var html = ''
			if (authority[index].isShow) {
				if (index == 2) {
					html += '<ul>' +
						'<li onClick="showAttribute()"><img src="' + ele.image + '"/><span>' + ele.string + '</span></li>' +
						'</ul>'
				} else if (index == 1) {
					html += '<ul><li><a style="text-decoration:none; color:#373737; font-size: 12px; font-weight: 400;" href="'+ele.url+'"  ><img src="' + ele.image + '"/><span>' + ele.string + '</span></a></li></ul>'
				} else {
					html += '<ul>' +
						'<li><img src="' + ele.image + '"/><span>' + ele.string + '</span></li>' +
						'</ul>'
				}
				$('.more-content').append(html)
			}
		})
		$.each(docAttribute, function (index, ele) {
			var html = '';
			html += '<p><span>' + ele.title + ':</span><span class="attr_detail">' + ele.data + '</span></p>'
			$('.attr_content').append(html)
		})
	})();

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
		button=$('#header button').hide(),
		wrapper_two=$('.wrapper_two').hide(),
		thumbnail,
		//		datas,
		currentIndex = false,
		menu = $('.btnMore').hide(),
		play = function (index) {

			var data = datas[index];
			if (typeof (data) == "undefined") return false;
			nowpage = index;
			loader.set((index + 1) * 100 / thumbnail.length);
			if (currentIndex !== false) thumbnail.eq(currentIndex).removeClass("active");
			currentIndex = index;
			$("#pptPageContent").html((currentIndex + 1) + "/" + datas.length);
			$("#pptPageContent_two").html((currentIndex + 1) + "/" + datas.length);
			var dom = thumbnail.eq(index).addClass("active")[0];
			if (dom.scrollIntoViewIfNeeded) dom.scrollIntoViewIfNeeded();
			$('.word-content').html('<img id="box" src="' + basePath + '/' + (index + 1) + '.png" width="100%"></img>');
			index == 0 ? prevBtn.addClass("unclick") : prevBtn.removeClass("unclick");
			index == thumbnail.length - 1 ? nextBtn.addClass("unclick") : nextBtn.removeClass("unclick");
			index == 0 ? prevBtn_two.addClass("unclick_two") : prevBtn_two.removeClass("unclick_two");
			index == thumbnail.length - 1 ? nextBtn_two.addClass("unclick_two") : nextBtn_two.removeClass("unclick_two");
			changeSourceHeight()
			if (isSpeaker) {
				var msg = {};
				msg.type = 'page';
				msg.param = index;
				parent.postMessage(JSON.stringify(msg), serverhost);
			}
			var imgScale = 1;
			var imgWidth = datas[nowpage].width;
			var imgHeight = datas[nowpage].height;
			$('.word-content').bind('mousewheel', function (event, delta, deltaX, deltaY) {
				var startHeight = event.target.offsetHeight;
				var startWidth = event.target.offsetWidth;
				var startTop = event.target.offsetTop;
				var startLeft = event.target.offsetLeft;
				imgScale = delta > 0 ? imgScale += 0.2 : imgScale -= 0.2
				imgScale = imgScale < 0.2 ? 0.2 : imgScale
				imgScale = imgScale >= 1.2 ? 1.2 : imgScale
				$(this).css({
					"width": imgWidth * imgScale,
					"height": imgHeight * imgScale,
					"top": function () {
						return (startHeight - $(this)[0].offsetHeight) / 2 + startTop + 'px'
					},
					"left": function () {
						return (startWidth - $(this)[0].offsetWidth) / 2 + startLeft + 'px'
					},
				})
				$("#mainbody").css("overflow", "auto");
			})
		},
		next = function () {
			play(currentIndex + 1);
		},
		prev = function () {
			play(currentIndex - 1);
		},
		nextBtn = $('#rightSwitchBtn').click(next),
		nextBtn_two = $('#rightSwitchBtn_two').click(next),
		prevBtn = $('#leftSwitchBtn').click(prev),
		prevBtn_two = $('#leftSwitchBtn_two').click(prev),
		fullScreen = function (e) {
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
			e.target.title='退出全屏';
			$('.more-content').removeClass('selected');
		},
		editFullScreen = function (e) {
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
			e.target.title='全屏';
			$('.more-content').removeClass('selected');
		},
		fullScreenChange = function (x) {
			$('body').toggleClass('fullScreen', x);
			$("#fullscreenBtbIcon").toggleClass("exit");
		},
		changeSourceHeight = function () {
			isZoom = false;
			currrentScale = 1;
			var screenHeight = document.documentElement.clientHeight || window.innerHeight;
			var header = $("#header").is(':visible') ? $("#header").height() : 0;
			var bottom = $(".pptPageSwitch").is(':visible') ? $(".pptPageSwitch").height() : 0;
			$("#mainbody").css({
				"height": (screenHeight - 20 - header - bottom) + "px",
				"top": header + 1 + "px",
			});
			$("#scale_text").html(Math.round(currrentScale * 100) + "%")
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
	$('#fullscreenBtbIcon').click(function (e) {
		$(e.target).hasClass("exit") ? editFullScreen(e) : fullScreen(e)
	});
	menu.on('click', function () {
		$('.more-content').toggleClass("selected")
	})
	$('#sideBtn').click(function () {
		$('body').toggleClass('openSide');
		var mar=$('#sidebar').width()
		changeSourceHeight()
	});
	document.addEventListener("fullscreenchange", function () {
		fullScreenChange(document.fullscreen);
	});
	document.addEventListener("mozfullscreenchange", function () {
		fullScreenChange(document.mozFullScreen);
	});
	document.addEventListener("webkitfullscreenchange", function () {
		fullScreenChange(document.webkitIsFullScreen);
	});
	document.addEventListener("MSFullscreenChange", function () {
		fullScreenChange(document.msFullscreenElement);
	});
	loader.fake();
	setTimeout(function () {
		loadData(v);
	}, 0);

	$(window).resize(function () {

		var screenHeight = document.documentElement.clientHeight || window.innerHeight;
		var screenWidth = document.documentElement.clientWidth || window.innerWidth;
		$('body').removeClass("openSide");
		var header = $("#header").is(':visible') ? $("#header").height() : 0;
		var bottom = $(".pptPageSwitch").is(':visible') ? $(".pptPageSwitch").height() : 0;
		$("#mainbody").css({
			"height": (screenHeight - 20 - header - bottom) + "px",
			"top": header +1+ "px",
		});
		changeSourceHeight();
		var screen_width = $(window).width()
		var width = $('.wrapper').width()
		var mar = (screen_width - width) / 2
		$('.wrapper').css('margin-left', mar)
		if (screen_width < 680) {
			$('.wrapper').siblings().hide()
			$('#zoom').hide()
		} else {
			$('.wrapper').siblings().show()
			$('#zoom').show()
		}
	}).resize();

	function scrollFunc(e) {
		console.log(e)
	}

	function loadData(v) {
		loader.fake(true);
		var a = eval('(' + v + ')');
		datas = a.data;
		//title.text(a.name);
		var s = "",
			len = datas.length;
		datas.forEach(function (o, i) {
			s += '<div class="thumbnail" data-index="' + i + '"><img src="' + basePath + '/' + o.index + '.jpg"><div class="side-pager">' + '</div></div>';
		});
		thumbnail = sidebar.html(s).children().on('click', function () {
			play($(this).data('index'));
		});
		play(0);
		setTimeout(function () {
			title.css("display",'inline-block');
			sidebar.fadeIn();
			button.fadeIn();
			wrapper_two.fadeIn('slow')
			mainbody.fadeIn('slow', function () {

				$(this).bind('mousewheel', function (event, delta) {

				})
			});
			menu.fadeIn('slow');
			$("body").addClass("openSide")
		}, 450);

		$(document).keydown(function (event) {
			if (event.which == "37" || event.which == "38") {
				prev()
			}
			if (event.which == "39" || event.which == "40") {
				next()
			}
		});

	}
});

function rotateLeft() {
	$('.word-content img').css({
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
	$('.word-content img').css({
		"transform": "rotate(" + (deg - 90) + "deg)",
		"-ms-transform": "rotate(" + (deg - 90) + "deg)",
		"-moz-transform": "rotate(" + (deg - 90) + "deg)",
		"-webkit-transform": "rotate(" + (deg - 90) + "deg)",
		"-o-transform": "rotate(" + (deg - 90) + "deg)",
	})
	deg -= 90
	deg = deg == -360 ? 0 : deg
}