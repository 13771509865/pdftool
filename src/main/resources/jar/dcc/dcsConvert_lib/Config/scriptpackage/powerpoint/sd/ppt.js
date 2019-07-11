$(function () {
  var sUserAgent = navigator.userAgent.toLowerCase();
  var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
  var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
  var bIsMidp = sUserAgent.match(/midp/i) == "midp";
  var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
  var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
  var bIsAndroid = sUserAgent.match(/android/i) == "android";
  var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
  var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
  if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM)) {
    $('.btnMore').show()
  }
  var height = $(window).height()
  $('.attribute').css('margin-top', (height - 229) / 2)
  var faWidth = $('#header').width()
  $('#title').css('margin-left', (faWidth - 150) / 2)
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
  var nowpage = 0;
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
    $(".full").css('display', 'none');
    $("#fullScreen").css('display', 'none');
    $(".ppt-turn-left-mask,.ppt-turn-right-mask").remove();
    $(".showPic").swipe({
      swipe: function (event, direction, distance, duration, fingerCount) {
        if (direction == "right" || direction == "down") {
          if (nowpage <= 0) {
            nowpage = 0;
          } else {
            nowpage--;
          }
          play(nowpage);
        } else if (direction == "left" || direction == "up") {
          if (nowpage >= datas.length - 1) {
            nowpage = datas.length - 1;
          } else {
            nowpage++;
          }
          play(nowpage);
        }
      }
    });
  }
  $('body').bind("selectstart", function () {
    return false;
  });
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
    playImg,
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
      $("#pageNum span:eq(0)").html((currentIndex + 1) + "/" + datas.length);
      var dom = thumbnail.eq(index).addClass("active")[0];
      if (dom.scrollIntoViewIfNeeded) dom.scrollIntoViewIfNeeded();
      if (!playImg) {
        playImg = $('<img class="playimg" src="' + data.url + '">').appendTo($(".showPic"));
      } else {
        playImg.attr('src', data.url);
      }
      prevBtn.toggle(index !== 0);
      nextBtn.toggle(index !== thumbnail.length - 1);
      if (isSpeaker) {
        var msg = {};
        msg.type = 'page';
        msg.param = index;
        parent.postMessage(JSON.stringify(msg), serverhost);
      }
    },
    next = function () {
      if (currentIndex < thumbnail.length - 1) {
        play(currentIndex + 1);
      }
    },
    prev = function () {
      play(currentIndex - 1);
    },
    nextBtn = $('.ppt-turn-right-mask').click(next),
    prevBtn = $('.ppt-turn-left-mask').click(prev),
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
      $('#fullScreen').attr('title', '退出放映')
      flag = true;
      $('body').removeClass('openSide')
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
      $('#fullScreen').attr('title', '放映')
      flag = true;
    },
    bindOpenSide = function () {
      var screenWidth = document.documentElement.clientWidth || window.innerWidth;
      if ($("body").hasClass("fullScreen")) {
        $('body').removeClass("openSide")
      } else {
        if (screenWidth <= 960) {
          $('body').removeClass("openSide")
        } 
      }
      $(".openSide").length > 0 ? $("#pageNum").css("left", screenWidth * 0.58 - 50) : $("#pageNum").css("left",
        screenWidth / 2 - 50)
    },
    fullScreenChange = function (x) {
      $('body').toggleClass('fullScreen', x);
    };
  var fg = true;
  $('#fullScreen').click(function (e) {
    if (fg) {
      fullScreen(e)
      fg = false;
    } else {
      editFullScreen(e)
      fg = true;
    }
  });
  $(window).resize(bindOpenSide).resize();
  $('#sideBtn').click(function () {
    var screenWidth = document.documentElement.clientWidth || window.innerWidth;
    $('body').toggleClass('openSide');
    $(".openSide").length > 0 ? $("#pageNum").css("left", screenWidth * 0.58 - 50) : $("#pageNum").css("left",
      screenWidth / 2 - 50)
  });
  if (document.addEventListener) {
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
  }
  loader.fake();

  loadData(v);

  function loadData(v) {
    loader.fake(true);
    var a = jQuery.parseJSON(v);
    datas = a.data;
    title.text(a.name);
    var s = "",
      len = datas.length;
    jQuery.each(datas, function (i, o) {
      s += '<div class="thumbnail" data-index="' + i + '"><img src="' + o.thumbUrl +
        '"><div class="side-pager">' + (i + 1 + '/' + len) + '</div></div>';
    });
    thumbnail = sidebar.html(s).children().on('click', function () {
      play($(this).data('index'));
    });
    play(0);
    setTimeout(function () {
      title.fadeIn('fast');
      sidebar.fadeIn();
      mainbody.fadeIn('slow');
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
  var ie = window.ActiveXObject ? window.atob ? 10 : document.addEventListener ? 9 : document.querySelector ? 8 :
    window.XMLHttpRequest ? 7 : 6 : undefined;
  if (ie < 9) {
    var mediaWidth = 960;
    $(window).resize(function (e) {
      var h = $(window).height() - 40;
      sidebar.height(h);
      mainbody.height(h - 20);
      $('body').toggleClass('media', $(window).width() > mediaWidth);
    }).resize();
  }
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
  $('body').addClass("openSide")
});
$(window).resize(function () {
  var faWidth = $('#header').width()
  $('#title').css('margin-left', (faWidth - 190) / 2)
  var height = $(window).height()
  $('.attribute').css('margin-top', (height - 229) / 2)
})
var flag = true;
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
  flag = true;
}
