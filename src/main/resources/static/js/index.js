$('#wrapper-file').height(function () {
    return $('#wrapper-file').width() / 2 + 'px'
})
$(window).resize(function () {
    $('#wrapper-file').height(function () {
        return $('#wrapper-file').width() / 2 + 'px'
    });
    if ($('#container').outerHeight(true) < $('.process').outerHeight(true) + $('#wrapper-file').outerHeight(true) + 81) {
        $('#copyright').css('position', 'relative')
    } else {
        $('#copyright').css('position', 'fixed')
    }
});
if ($('#container').outerHeight(true) < $('.process').outerHeight(true) + $('#wrapper-file').outerHeight(true) + 81) {
    $('#copyright').css('position', 'relative')
} else {
    $('#copyright').css('position', 'fixed')
}


var showdiv = function () {
    document.getElementById("bg").style.display = "block";
    document.getElementById("show-box").style.display = "block";
}
var hidediv = function () {
    document.getElementById("bg").style.display = 'none';
    document.getElementById("show-box").style.display = 'none';
    $('.fb').attr('src', 'images/fankui.svg');

}
var hidedivsuccess = function () {
    document.getElementById("bg").style.display = 'none';
    document.getElementById("show-box-success").style.display = 'none';
    $('.fb').attr('src', 'images/fankui.svg');
}
//qq，微信
$(".qqm").on("mouseover", function (e) {
    $('.qqm').attr('src', 'images/gzRed.svg')
    $(".erweimam").show();
    $(".qunm").hide();
    $('.qqmup').attr('src', 'images/lianxi.png')
    $('.fb').attr('src', 'images/fankui.svg');
});

$(".erweimam").on("mouseleave", function (e) {
    $(".erweimam").hide();
    $('.qqm').attr('src', 'images/guanzhu.svg');
});

$(".qqmup").on("mouseover", function (e) {
    $('.qqmup').attr('src', 'images/qqRed.svg')
    $(".qunm").show();
    $(".erweimam").hide();
    $('.qqm').attr('src', 'images/guanzhu.svg');
    $('.fb').attr('src', 'images/fankui.svg');
});
$(".qunm").on("mouseleave", function (e) {
    $(".qunm").hide();
    $('.qqmup').attr('src', 'images/lianxi.png');

});
//反馈
$('.fb').on('mouseover', function (e) {
    $('.fb').attr('src', 'images/fbRed.svg');
    $(".erweimam").hide();
    $('.qqm').attr('src', 'images/guanzhu.svg');
    $(".qunm").hide();
    $('.qqmup').attr('src', 'images/lianxi.png');
});
$('.fb').on('mouseleave', function (e) {
    $('.fb').attr('src', 'images/fankui.svg');
});
// 验证手机号
function isPhoneNo(phone) {
    var pattern = /^((0\d{2,3}\d{7,8})|(0\d{2,3}-\d{7,8})|(^1[3|4|5|6|7|8|9][0-9]{9}))$/;
    return pattern.test(phone);
}

//用户反馈
function showdivsuccess() {
    var type = $("input[name='contact']:checked").next("label").text();
    if (type == "" || type == null) {
        type = "其他"
    }
    var content = $(".show-input textarea").val();
    if (content == "" || content == null) {
        $().toastmessage('showNoticeToast', "请输入您要反馈的内容!");
        return;
    } else if (content.length < 10) {
        $().toastmessage('showNoticeToast', "反馈的内容不能少于10个字!");
        return;
    } else if (content.length > 200) {
        $().toastmessage('showNoticeToast', "反馈的内容不能多于200个字!");
        return;
    }
    var address = $(".show-input input[type='text']").val();

    if (address == "" || address == null) {
        $().toastmessage('showNoticeToast', "请输入您的联系电话!");
        return;
    } else {
        if (isPhoneNo($.trim(address)) == false) {
            $().toastmessage('showNoticeToast', "联系电话格式不正确!");
            return;
        }
    }
    $.ajax({
        url: "feedback",
        dataType: "json",
        type: "post",
        data: {
            "type": type,
            "content": content,
            "address": address
        },
        success: function (data) {
            if (data.errorcode == 0) {
                $("#show-box").hide();
                $("#show-box-success").show();
                $("input[name='contact']").attr("checked", false);
                $(".show-input textarea").val('');
                $(".show-input input[type='text']").val('');
            } else {
                $().toastmessage('showNoticeToast', message);
            }
        }
    });


}
$('.showbtn').on('click', function () {
    if ($(this).hasClass('showred')) {
        showdivsuccess();
    }
});

$('input,#content_text').bind('input propertychange', function () {
    var type = $("input[name='contact']:checked").next("label").text();
    var content = $(".show-input textarea").val();
    var address = $(".show-input input[type='text']").val();
    if (type != "" && content != "" && address != "") {
        $('.showgray').addClass('showred');
    } else {
        $('.showgray').removeClass('showred')
    }
    var len = content.length;

    $(".word").text(len);

});


var filetype = "PDF2DOC";

//上传文件
var up_num = 0;
var fileid = '';
$(".upfilebtn1").fileupload({
    url: 'defaultUpload',
    dataType: 'json',
    //如果需要额外添加参数可以在这里添加
    add: function (e, data) {
        $('.btn-start').show()
        $('.container-box').css('height', '350px')
        if (window.innerWidth <= 767 && window.innerWidth >= 320) {
            $('#wrapper-file').height(
                function () {
                    return 450 + 'px'
                }
            )
        } else if (window.innerWidth >= 768) {
            $('#wrapper-file').height(
                function () {
                    return 490 + 'px'
                }
            )
        }
        $(window).resize(function () {
            if (window.innerWidth <= 767 && window.innerWidth >= 320) {
                $('#wrapper-file').height(
                    function () {
                        return 450 + 'px'
                    }
                )
            } else if (window.innerWidth >= 768) {
                $('#wrapper-file').height(
                    function () {
                        return 490 + 'px'
                    }
                )
            }
        });


        if ($('#container').outerHeight(true) < $('.process').outerHeight(true) + $('#wrapper-file').outerHeight(true) + 81) {
            $('#copyright').css('position', 'relative')
        } else {
            $('#copyright').css('position', 'fixed')
        }

        if (document.querySelectorAll('.fileabbr').length <= 4) {

            var res = true;
            up_num++; //上传数量

            //  if (up_num <= 5) {
            var fileType = data.files[0].name.substring(data.files[0].name.lastIndexOf('.') + 1).toUpperCase();
            var fileSize = data.files[0].size;
            var fid = $.md5("" + data.files[0].lastModified + data.files[0].size + data.files[0].name); //上传判定用的唯一id

            if (fileSize > (50 * 1024 * 1024)) {
                $().toastmessage('showErrorToast', "暂不支持50M以上的文件");
                return;
            }
            if (fileType != "PDF") {
                $().toastmessage('showErrorToast', "文件类型不符合转换要求");
                return;
            }
            if ($("#inputDir_" + fid + "").length > 0) {
                $().toastmessage('showErrorToast', "文件已存在");
                return;
            }

            var dataName;
            if (data.files[0].name.length > 17) {
                dataName = data.files[0].name.substring(0, 17) + '...'
            } else {
                dataName = data.files[0].name
            }

            $('#hr_one').hide()
            $('#wrapper-file').css('background', 'transparent').css('border', 'none').css('box-shadow', 'none')
            $("#svg_pw").hide();
            $("#png_xianshi").hide();
            $("#File").show();
            $("#tuo").hide();
            $("#shang").hide();
            $("#fileinput-button").addClass('fileinput-button-two fileinput');
            $("#choosefile").text('+ 上传文件')
            $('#fileinput-button').removeClass('fileinput-button').removeClass('btn-default')

            fileSize = (data.files[0].size / 1024 / 1024).toFixed(2);

            //显示文件名称
            var html = "";
            var htmlinput = "";

            html += "<tr id=\'file_" + fid + "\' class=\'fileabbr\'>";
            html += "                    <td title=\"" + data.files[0].name + "\">" + dataName + "</td>";
            html += "                    <td>" + fileSize + "M</td>";
            html += "                    <td style=\"position: relative;right: 0px;\">";
            html += "                    <div class=\"jindu-box\" id=\"jindu_" + fid + "\">";
            html += "                    <span class=\"jindu\"></span>";
            html += "                    </div>";
            html += "                    <span id=\"file_state_" + fid + "\">正在上传</span>";
            html += "                    </td>";
            html += "                    <td  style=\"width: 30%;\">";
            html += '                        <img src=\'images/quxiao.png\' id=\'showUrl_' + fid + '\' alt=\'\' class=\'png-delete delete\' onclick="deletefile(\'' + fid + '\')">';
            html += '                        <span class=\'delete\'  style=\'display: none!important\' onclick="deletefile(\'' + fid + '\')"></span>';
            html += "                    </td>";
            html += "                </tr>";

            htmlinput += "<input type=\'hidden\' name=\'inputDir\' class=\'upload\' id=\'inputDir_" + fid + "\'  />";

            $("#pdf_file").append(html);
            $("#pdf_file").before(htmlinput);
            $('.btn-start').removeClass('startred');
            data.submit(); // 文件上传
        } else {
            res = false;
            up_num = 5;
            $().toastmessage('showErrorToast', "您一次最多可处理5个文件");
            $('.btn-start').show()
            return false;
        }
        // 跳出外部循环
        if (!res) {
            $().toastmessage('showErrorToast', "您一次最多可处理5个文件");
            return false
        }
    },
    done: function (e, data) {
        if (data.result.errorcode == 0) {
            $('#txt-file').remove()
            $('.fileinput-button-con').css('display', 'inline-block')

            //显示已完成上传个数
            var num = parseInt($("#file_num").text());
            $("#file_num").text(num + 1);

            var fid = $.md5("" + data.files[0].lastModified + data.files[0].size + data.files[0].name); //上传判定用的唯一id
            fileid = $.md5("" + data.files[0].lastModified + data.files[0].size + data.files[0].name);
            var fileName = data.result.data[0];
            var file_id = fileName.substring(0, fileName.lastIndexOf("\\"));
            var index1 = fileName.lastIndexOf(".");
            var index2 = fileName.length;
            var postf = fileName.substring(index1 + 1, index2); //后缀名
            var filesize = (data.files[0].size / 1024 / 1024).toFixed(2);
            var existed_fileName = "";
            var userJson = {};
//             if (postf !== "pdf") {
//                 deletefile(fid);
//                 $().toastmessage('showErrorToast', "上传文件格式不正确");
//                 return;
//             }
            var status = false
            var arr = []
            var arrMax
            var jnuduWidth = ''

            if (window.innerWidth <= 400 && window.innerWidth >= 320) {
                jnuduWidth = '15px'
            } else if (window.innerWidth <= 767 && window.innerWidth >= 401) {
                jnuduWidth = '25px'
            } else if (window.innerWidth <= 1199 && window.innerWidth >= 768) {
                jnuduWidth = '36px'
            } else {
                jnuduWidth = '86px'
            }
            $('.jindu').each(function () {

                if ($(this).css('width') == jnuduWidth) {
                    status = true
                } else {
                    status = false
                }
                arr.push(Number($(this).parent().parent().prev('td')[0].innerText.split('M')[0]))
                arrMax = Math.max.apply(null, arr);
            })

            var arrJindu = $('.jindu').parent().parent().prev('td')

            for (var index = 0; index < arrJindu.length; index++) {
                var element = arrJindu[index].innerText.split('M')[0];
                if (Number(arrJindu[index].innerText.split('M')[0]) == arrMax) {
                    var aaa = $(arrJindu[index])[0].nextSibling.nextSibling.children[0].children[0]
                    if (($(aaa).css('width') == jnuduWidth) && status) {
                        $('.btn-start').addClass('startred');
                    } else {
                        $('.btn-start').removeClass('startred');
                    }
                }
            }
            $("#inputDir_" + fid + "").val(fileName);
            $("#jindu_" + fid + " .jindu").attr("style", "width:100%");
            $("#file_state_" + fid + "").html("上传完成");
            $("#showUrl_" + fid + "").attr("src", "images/delete.png");

        } else {
            var fid = $.md5("" + data.files[0].lastModified + data.files[0].size + data.files[0].name);
            $("#jindu_" + fid + " .jindu").addClass('bggray').attr("style", "width:100%");
            $("#file_state_" + fid + "").text("上传失败");
            $("#showUrl_" + fid + "").attr("src", "images/delete.png");
            $().toastmessage('showErrorToast', data.result.message);
            $('.btn-start').removeClass('startred');
        }



    },
    error: function () {
        $().toastmessage('showErrorToast', '上传失败！');
    },
    progress: function (e, data) {
        var fid = $.md5("" + data.files[0].lastModified + data.files[0].size + data.files[0].name); //上传判定用的唯一id
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $("#jindu_" + fid + " .jindu").css("width", progress + "%");

    },

});

//下载地址
function createUrl(input_fid, obj_value, data, convertType) {
    var file_id = obj_value.substring(0, obj_value.lastIndexOf("\\"));

    var fid = input_fid.substring(input_fid.indexOf("_") + 1);
    var html = "";
    //转换后的文件名
    var fileName = data.data.fileName;
    var wordStoragePath = data.data.wordStoragePath;
    var wordPath = wordStoragePath.substring(wordStoragePath.indexOf("\\") + 1);
    var fileHash = data.data.fileHash
    //下载地址
    var url = "user/download?fileHash=" + fileHash;
    var path = window.location.href + encodeURI(url);

    if (data.errorcode == 0) {
        $("#showUrl_" + fid + "").text("");
        var type;
        html += '<span class=\'download\'><a href=\'' + path + '\' download=\"' + fileName + '\"><img src=\'images/download.png\' alt=\'\' class=\'png-download\'></a></span>';

        $("#showUrl_" + fid + "").attr('src', 'images/delete.png');
        $(".png-delete").css('left', '50%');
        $("#showUrl_" + fid + "").before(html);
    } else {
        $().toastmessage('showErrorToast', data.message);
        $('.download').hide();

    }
}

function keynum() {
    $("span[id^='file_state_']").each(function () {
        if ($(this).text() == '转换完成') {
            $(this).text('转换完成');
            $(this).parent().next('td').children(".png-delete").attr('src', 'images/delete.png');
        } else {
            $(this).html('等待转换');
            $(this).prev('.jindu-box').children('.jindu').addClass('bggray');
            $(this).parent().next('td').children(".png-delete").attr('src', 'images/quxiao.png');
        }
    })
}
//转码
var successCount = 0; //转码完成数
var ajaxTimeOut = null;
var flag = false;
var cancelCount = 0;
function convert() {
    $('.btn-start').removeClass('startred');
    var str = $("span[id^='file_state_']").text()
    var obja = str.split('');
    var key_num = 0

    for (var i = 0, l = str.length; i < l; i++) {

        var key = '转换完成';
        if (obja[i] + obja[i + 1] + obja[i + 2] + obja[i + 3] == key) {
            key_num++
        }
    }
    if (key_num == 5) {
        $("span[id^='file_state_']").text("转换完成");
    } else {
        keynum()
    }

    var inputDir = $('input[name="inputDir"]');
    var concompleted = $('.concompleted');
    var file_num = inputDir - concompleted;

    if (inputDir.length == 0) {
        $().toastmessage('showErrorToast', "未发现需要转码的文件");
        return;
    } else {
        $('input[name="inputDir"]').each(function () {
            $(this).removeClass('upload');
        })
        var convertType = "36";
        var obj = null;
        var obj_value = null;
        var fid = null; //前端文件唯一id
        var file_id = null; //后端文件唯一id
        var input_fid = null;


        function convertfunc(times, count) {
            var numm = times
            /* if (times < 0) {
                return;
            } */
            if (times > count) {
                return;
            }
            obj = $('input[name="inputDir"]').eq(times);
            input_fid = obj.attr("id");
            fid = input_fid.substring(input_fid.indexOf("_") + 1);
            obj_value = $.trim(obj.val());
            file_id = obj_value.substring(0, obj_value.lastIndexOf("\\"));

            // 进度条
            if (obj.hasClass('concompleted')) {
                times++;
                convertfunc(times, count); //递归调用
                return;
            }
            // 进度条
            if (obj.hasClass('quxiao')) {
                times++;
                convertfunc(times, count); //递归调用
                return;
            }
            // if (obj.hasClass('converting')) {
            //     times++;
            //     convertfunc(times); //递归调用
            //     return;
            // }
            $("#jindu_" + fid + " .jindu").attr("style", "width:100%").addClass('bggif').removeClass('bggray');
            $("#file_state_" + fid + "").text("正在转换");
            obj.addClass("converting");
            $("#showUrl_" + fid + "").attr('src', 'images/quxiao.png');
            $("#showUrl_" + fid + "").attr('onclick', 'deleteConvert(\"' + fid + '\",\"' + times + '\",\"' + count + '\")');
            //转码
            ajaxTimeOut = $.ajax({
                url: "mqconvert",
                contentType: "application/json; charset=utf-8",

                data: JSON.stringify({
                    "srcRelativePath": obj_value,
                    "convertType": 36

                }),
                dataType: "json",
                type: "post",
                timeout: 300000,
                success: function (data) {
                    obj.addClass("done");
                    if (flag) {
                        count = count - cancelCount;
                        cancelCount--;
                        flag = false;
                    }
                    if (data.errorcode == 0) {
                        $("#jindu_" + fid + " .jindu").attr("style", "width:100%").removeClass('bggif').removeClass('bggray'); //进度条
                        $("#file_state_" + fid + "").text("转换完成");
                        $("#showUrl_" + fid + "").attr('src', 'images/delete.png');
                        $("#showUrl_" + fid + "").attr('onclick', 'deletefile(\"' + fid + '\")')
                        successCount++;
                        obj.attr("class", "concompleted");
                        obj.removeClass("converting");
                        createUrl(input_fid, obj_value, data, convertType);
                    } else {
                        $("#file_state_" + fid + "").text("转换失败");
                        $("#showUrl_" + fid + "").attr('src', 'images/delete.png');
                        $("#showUrl_" + fid + "").attr('onclick', 'deletefile(\"' + fid + '\")')
                        $().toastmessage('showErrorToast', data.message);
                        obj.removeClass("converting");
                        $("#jindu_" + fid + " .jindu").attr("style", "width:100%").removeClass('bggif').addClass('bggray');
                    }
                    times++;
                    if (times > count) {
                        return;
                    }
                    convertfunc(times, count); //递归调用  
                },

                error: function (data) {
                    times++;
                    obj.addClass("done");
                    if (data.readyState == 0) {
                       if (data.statusText == 'timeout') {
                            $().toastmessage('showErrorToast', '转换超时');
                            $("#file_state_" + fid + "").text("转换失败");
                            $("#showUrl_" + fid + "").attr('src', 'images/delete.png');
                            $("#showUrl_" + fid + "").attr('onclick', 'deletefile(\"' + fid + '\")')
                            obj.removeClass("converting");
                            $("#jindu_" + fid + " .jindu").attr("style", "width:100%").removeClass('bggif').addClass('bggray');
                        }
                        if (data.statusText == 'abort') {
                            $().toastmessage('showErrorToast', '取消成功');
                            $("#file_state_" + fid + "").text("转换失败");
                            $("#showUrl_" + fid + "").attr('src', 'images/delete.png');
                            $("#showUrl_" + fid + "").attr('onclick', 'deletefile(\"' + fid + '\")')
                            obj.removeClass("converting");
                            $("#jindu_" + fid + " .jindu").attr("style", "width:100%").removeClass('bggif').addClass('bggray');
                        }
                        if (times > count) {
                            return;
                        }
                        // $().toastmessage('showErrorToast', '取消成功');
                        convertfunc(times, count); //递归调用
                    } else {
                        if (flag) {
                            count = count - cancelCount;
                            cancelCount--;
                            flag = false;
                        }
                        $("#file_state_" + fid + "").text("转换失败");
                        $("#showUrl_" + fid + "").attr('src', 'images/delete.png');
                        $("#showUrl_" + fid + "").attr('onclick', 'deletefile(\"' + fid + '\")')
                        $().toastmessage('showErrorToast', '转换失败');
                        obj.removeClass("converting");
                        $("#jindu_" + fid + " .jindu").attr("style", "width:100%").removeClass('bggif').addClass('bggray');
                        if (times > count) {
                            return;
                        }
                        convertfunc(times, count); //递归调用
                    }
                },
            });
        }

        convertfunc(0, inputDir.length - 1);
        $("#file_num").text(0);
        $("#file_consume").text(0);
    }
    // }
};
$('.btn-start').on('click', function () {
    if ($(this).hasClass('startred')) {
        convert();
    }
});
// $('.btn-start').on('click', function () {
//     var flag = true;
//     if ($(this).hasClass('startred')) {
//         $("span[id^='file_state_']").each(function () {
//             console.log($(this).text())
//             if ($(this).text() == '正在转换') {

//                 flag = false;
//             }
//         })
//         if(flag){
//             convert();
//         }else{
//             $().toastmessage('showErrorToast', '有正在转换的文件，请稍后');
//         }
//     }
// });


//删除
function deletefile(fid) {
    var uid = uuid(8, 10);
    $("#file_" + fid + "").remove();
    $("#inputDir_" + fid + "").attr("id", uid + fid);
    $("#" + uid + fid + "").attr("name", "inputDir1");
    $("#" + uid + fid + "").addClass("quxiao");
    if ((!$("#" + uid + fid + "").hasClass("done")) && (!$("#" + uid + fid + "").hasClass("upload"))) {
        flag = true;
        cancelCount++;
    }
    up_num--

    if ($(".fileabbr").length == 0) {
        $('.btn-start').removeClass('startred');
    }
};

function deleteConvert(fid, times, count) {
    $("#file_" + fid + "").remove();
    $("#inputDir_" + fid + "").attr("id", "del" + fid);
    $("#del" + fid + "").attr("name", "inputDir");
    $("#del" + fid + "").addClass("quxiao").removeClass('converting');
    up_num--;
    times;
    if (times > count) {
        return;
    }
    if (ajaxTimeOut) {
        ajaxTimeOut.abort();
    }
    if ($(".fileabbr").length == 0) {
        $('.btn-start').removeClass('startred');
    }
};
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [],
        i;
    radix = radix || chars.length;
    if (len) {
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
};




var uaaPath = "http://auth.yozocloud.cn";
var uaaLogin = "http://auth.yozocloud.cn/account/signin.html?success=http://pdf.yozocloud.cn:8080";
var uaasetting = "http://auth.yozocloud.cn/account/index.html";
var userId = null;
function getUerInfo(){
	 $.ajax({ 
			url:"../detail",
			type:"GET",
			data : "",
			success:function (data){
				userId = data.data.userId;
				if(data.errorcode == 0 && userId!=null && "" !=userId && undefined !=userId ){
					if(document.getElementById('uaa')) {
						document.getElementById('uaa').removeAttribute("href");
					}
					$("#uaa").text(data.data.name);
					
					document.getElementById('uiamge').src = uaaPath+data.data.avatar;
					
				}else{
					document.getElementById('uaa').href= uaaLogin ;
				}
			},
		 error:function(){  
			 document.getElementById('uaa').href = uaaLogin;
	        }
		});
}	

document.onmouseup=function(event){ 
if(userId != null && userId != "" && userId != undefined){
	if(document.getElementById('uaa_memu').style.display = "block"){
		document.getElementById('uaa_memu').style.display = "none";
	}
 }
}


$('#uaa').click(function() {
if(userId != null && userId != "" && userId != undefined){
	if(document.getElementById('uaa_memu').style.display = "none"){
		document.getElementById('uaa_memu').style.display = "block";
	}
}
})


$("#setting").unbind("click").bind("click", function(){
window.open(uaasetting);
})


$("#logout").unbind("click").bind("click", function(){
	 $.ajax({ 
			url:"../logout",
			type:"GET",
			data : "",
			success:function (data){
			//	alert(data.message);
				location.reload();
				
				
			}
});

})
