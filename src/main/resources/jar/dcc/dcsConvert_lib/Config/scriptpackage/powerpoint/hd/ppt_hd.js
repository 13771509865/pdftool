var nowpage = 0;
var flag = true;
var a = $.parseJSON(v);
var scrollFunc = function (e) {}
window.onmousewheel = document.onmousewheel = scrollFunc;
var isZoom = false;
var currrentScale = 1;
var cw, cr
$('.btnMore').click(function () {
	if (flag) {
		$('.more-content').show()
		flag = false;
	} else {
		$('.more-content').hide()
		flag = true;
	}
})

function showAttribute() {
	$('.docMark').show()
	$('.more-content').hide()
}

function offAttribute() {
	$('.docMark').hide()
	flag=true;
}
$(function () {
	var mar = ($(window).width() - 365) / 2
	$('#header p').css('margin-left', mar).css('opacity', 1)
	var height = $(window).height()
	$('.attribute').css('margin-top', (height - 229) / 2)
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
		if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
			$('#header button').show()
		}
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
			'     </div>' +
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
			// '  <div class="fullscreenBtb" id="fullscreenBtbIcon" title="全屏">' +
			'</div>' +
			// '  <div class="remarksBtb" id="remarksBtbIcon"><span>备注</span></div>' +
			//			'  <div class="scaleBtb" id="scaleBtbIcon"><span></span></div>' +
			'</div>'
		$("body").append(pptElement)
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
		var screen_width = $(window).width()
		var mar = (screen_width - 228) / 2
		$('.wrapper').css('margin-left', mar)
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
		wrapper_two=$('.wrapper_two').hide(),
		pptPageSwitch=$('.pptPageSwitch').hide(),
		thumbnail,
		datas,
		currentIndex = false,
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
			$('.word-content').html('<embed src="' + basePath + '/' + (index + 1) + '.svg" width="100%"  type="image/svg+xml"></embed>');
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
			$('.more-content').hide()
			flag=true;
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
			$('.more-content').hide()
			flag=true;
		},
		fullScreenChange = function (x) {
			$('body').toggleClass('fullScreen', x);
			$("#fullscreenBtbIcon").toggleClass("exit");
		},
		changeSourceHeight = function () {
			isZoom = false;
			currrentScale = 1;
			var datas = a.data;
			$("#scale_text").html(Math.round(currrentScale * 100) + "%")
			var mainbodyWidth;
			var mainbodyHeight;
			setTimeout(function(){
				mainbodyWidth = $("#mainbody").width();
			    mainbodyHeight = $("#mainbody").height();
					
			},1)
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
	$('#sideBtn').click(function () {
		$('body').toggleClass('openSide');
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
        setTimeout(function(){
		loadData(v);
	})
	var screen_height = $(window).height()
	$('.wrapper_two').css('margin-top', screen_height - 76)
	$(window).resize(function () {

		var screenHeight = document.documentElement.clientHeight || window.innerHeight;
		var screenWidth = document.documentElement.clientWidth || window.innerWidth;
		$('body').removeClass("openSide");
		var header = $("#header").is(':visible') ? $("#header").height() : 0;
		var bottom = $(".pptPageSwitch").is(':visible') ? $(".pptPageSwitch").height() : 0;
		$("#mainbody").css({
			"height": (screenHeight - 20 - header - bottom) + "px",
			"top": header + 1+ "px",
		});
		changeSourceHeight();
		var screen_width = $(window).width()
		var mar = (screen_width - 228) / 2
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
			s += '<div class="thumbnail" data-index="' + i + '"><img src="' + basePath + '/' + o.index + '.jpg"><div class="side-pager">' + (i + 1) + '</div></div>';
		});
		thumbnail = sidebar.html(s).children().on('click', function () {
			play($(this).data('index'));
		});
		play(0);
		setTimeout(function () {
			title.fadeIn('fast');
                
			sidebar.fadeIn();
			if (showFooter.isShowFooter) {
				pptPageSwitch.fadeIn('slow')
			} else {
				wrapper_two.fadeIn('slow')
			}
			$('.attr_Title').show()
			mainbody.fadeIn('slow', function () {

				$(this).bind('mousewheel', function (event, delta) {

				})
			});
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

function renderPage() {
	//	var screenWidth = document.documentElement.clientWidth || window.innerWidth;
	//	var screenHeight = document.documentElement.clientHeight || window.innerHeight;
	//	var bottom_foot = $("#footer").is(':visible') ? $("#footer").height() :
	//		$("#signatureTool_phone").is(':visible') ? $("#signatureTool_phone").height() : 0;
	//	var position_left = $("body").hasClass('openSidebar') ? screenWidth >= 600 ? 200 : 0 : 0;
	//	var curPage = pageIndex ? $("embed:eq(" + pageIndex + ")") : $("embed");
	//	var header_top = $(".navbar").is(':visible') ? 41 : 0
	//	$("embed").css({
	//		"left": position_left,
	//		"width": (screenWidth - position_left) + "px",
	//		"height": (screenHeight - header_top - bottom_foot) + "px",
	//		"top": header_top + "px"
	//	});
	//	var docWidth = screenWidth - 20 - position_left
	//	var scale = parseFloat($("#scale").val());
	//	var angle = parseFloat($("#angle").val());
	//	$("#pageNum").css("left", screenWidth / 2 - 50)
	//	curPage.each(function(index, e) {
	//		if(pageIndex) {
	//			index = pageIndex
	//		}
	//		var ratio = datas[index].height / datas[index].width;
	//		var factor = ratio > 1 ? -1 : 1;
	//		if(datas[index].width >= docWidth) {
	//			$(this).css({
	//				"width": docWidth * scale + "px",
	//				"height": docWidth * ratio * scale + "px",
	//				"line-height": docWidth * ratio * scale + "px"
	//			}).find("img.loadedImg,embed").css({
	//				"width": docWidth * scale + "px",
	//				"height": docWidth * ratio * scale + "px",
	//			});
	//		} else if(docWidth > datas[index].width) {
	//			$(this).css({
	//				"width": datas[index].width * scale + "px",
	//				"height": datas[index].height * scale + "px",
	//				"line-height": datas[index].height * scale + "px"
	//			}).find("img.loadedImg,embed").css({
	//				"width": datas[index].width * scale + "px",
	//				"height": datas[index].height * scale + "px",
	//			});
	//		}
	//	});

}


//function changePage(index){
// var reg = /^[0-9]*[1-9][0-9]*$/;
// if(!reg.test(index)){
//}else if(index > datas.length || index <= 0){
// }else{
//    var pos = $("embed:eq('"+(index-1)+"')").position().top + 5;
//    $(".activePage").val(index);
//     $(".container-fluid-content").animate({scrollTop: pos},0);
//}
//}