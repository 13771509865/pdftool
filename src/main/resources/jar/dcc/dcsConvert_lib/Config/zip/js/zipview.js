//为true时不做转换，为false时清空data.url做转换
var flag = true;
var configJSON = { "name": "zipFold", "position": "zipFold", "size": "4kb", "children": [{ "name": "1.jpg", "position": "zipFold", "size": "557kb" }, { "name": "doctest.docx", "position": "zipFold", "size": "25kb" }, { "name": "fold1", "position": "zipFold\\fold1", "size": "4kb", "children": [{ "name": "1.jpg", "position": "zipFold\\fold1", "size": "557kb" }, { "name": "doctest.docx", "position": "zipFold\\fold1", "size": "25kb" }, { "name": "fold1A", "position": "zipFold\\fold1\\fold1A", "size": "4kb", "children": [{ "name": "1.jpg", "position": "zipFold\\fold1\\fold1A", "size": "557kb" }, { "name": "doctest.docx", "position": "zipFold\\fold1\\fold1A", "size": "25kb" }, { "name": "pdftest.pdf", "position": "zipFold\\fold1\\fold1A", "size": "368kb" }, { "name": "ppttest.pptx", "position": "zipFold\\fold1\\fold1A", "size": "5891kb" }, { "name": "xlstest.xls", "position": "zipFold\\fold1\\fold1A", "size": "51kb" }, { "name": "示例文档.rar", "position": "zipFold\\fold1\\fold1A", "size": "5213kb" }] }, { "name": "pdftest.pdf", "position": "zipFold\\fold1", "size": "368kb" }, { "name": "ppttest.pptx", "position": "zipFold\\fold1", "size": "5891kb" }, { "name": "xlstest.xls", "position": "zipFold\\fold1", "size": "51kb" }, { "name": "示例文档.rar", "position": "zipFold\\fold1", "size": "5213kb" }] }, { "name": "fold2", "position": "zipFold\\fold2", "size": "0kb", "children": [{ "name": "fold2A", "position": "zipFold\\fold2\\fold2A", "size": "0kb", "children": [{ "name": "fold2AA", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "4kb", "children": [{ "name": "1.jpg", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "557kb" }, { "name": "doctest.docx", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "25kb" }, { "name": "pdftest.pdf", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "368kb" }, { "name": "ppttest.pptx", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "5891kb" }, { "name": "xlstest.xls", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "51kb" }, { "name": "示例文档.rar", "position": "zipFold\\fold2\\fold2A\\fold2AA", "size": "5213kb" }] }] }, { "name": "fold2B", "position": "zipFold\\fold2\\fold2B", "size": "0kb", "children": [] }] }, { "name": "pdftest.pdf", "position": "zipFold", "size": "368kb" }, { "name": "ppttest.pptx", "position": "zipFold", "size": "5891kb" }, { "name": "xlstest.xls", "position": "zipFold", "size": "51kb" }, { "name": "示例文档.rar", "position": "zipFold", "size": "5213kb" }] };
$(function () {
    ZIP = {
        //root:'zips/',
        root: '',
        el: $("#container"),
        stack: $("#stack"),
        files: $("#files"),
        data: configJSON,
        //当前路径
        dir: configJSON,
        //后缀名映射
        extMap: {
            ofd: "ofd",
            doc: "doc,docx",
            xls: "xls,xlsx",
            ppt: "ppt,pptx",
            eml: "eml",
            compress: "zip,rar,7z,tar",
            // dir :"",
            psd: "psd",
            fla: "fla",
            pdf: "pdf",
            html: "html",
            audio: "mp3",
            video: "mp4",
            txt: "txt",
            other: "",
            img: "jpg,gif,bmp,png,jpeg"
        },
        //处理程序
        actionMap: {
            ofd: null,
            doc: null,
            xls: null,
            ppt: null,
            eml: null,
            compress: null,
            dir: null,
            psd: null,
            fla: null,
            pdf: null,
            html: null,
            audio: null,
            video: null,
            txt: null,
            other: null,
            img: null
        },
        action: function (n) {
            var data = this.dir.children[n];
            if (this.actionMap[data.action]) {
                this.actionMap[data.action].call(this, data, n);
            } else {
                this.actionMap["other"].call(this, data, n);
            }

        },
        getAction: function (data) {
            if (data.children) return "dir";

            var name = data.name.toLowerCase(),
                dot = name.lastIndexOf('.');
            if (dot <= 0) {
                dot = "";
            } else {
                dot = name.slice(dot + 1);
            };
            return this.extMap[dot] || "other";
        },
        //刷新当前路径界面
        render: function () {
            var self = this,
                data = this.dir;
            if (data.dir) {
                this.stack.html('<button class="ac-back">返回</button> &nbsp; ' + data.dir.path + data.name).show();
            } else {
                //this.stack.html(data.dir.path+data.name).show();
                this.stack.hide().empty();
            }
            //文件列表
            var s = "";
            $.each(data.children, function (i, o) {
                s += '<a class="file" href="#' + i + '"><span class="fcon ico_file_' + o.action + '"></span><span class="fname">' + o.name + '</span><span class="fsize">' + (o.size || "") + '</span></a>';

            });
            this.files.hide().html(s).show();
        }
    };

    //============================预处理============================
    //生成扩展地图
    var extmap = {};
    $.each(ZIP.extMap, function (k, v) {
        $.each(v.split(','), function (i, o) {
            extmap[o] = k;
        });
    });

    ZIP.extMap = extmap;
    //深度遍历json,补充辅助属性 dir,action
    check = function (dir, path) {
        dir.path = path + dir.name + '/';
        $.each(dir.children, function (i, o) {
            o.dir = dir;
            o.action = ZIP.getAction(o);
            if (o.children) {
                check(o, dir.path);
            }
        });
    }
    // check(ZIP.data, '');  

    //IE6
    if (window.ActiveXObject && !window.XMLHttpRequest) {
        $(window).resize(function () {
            ZIP.el.height($(this).height() - 43);
        });
    }
    //============================绑定点击============================
    ZIP.el.on('click', '.file', function (e) {
        var index = this.hash.slice(1);
        ZIP.action(index);
        e.preventDefault();
    });
    ZIP.el.on('click', '.ac-back', function (e) {
        ZIP.dir = ZIP.dir.dir;
        ZIP.render();
    });
    //============================注册处理事件============================
    //文件夹
    ZIP.actionMap['dir'] = function (data, index) {
        this.dir = data;
        this.render();
    };
    //other
    ZIP.actionMap['other'] = function (data, index) {
        //暂不支持该类型文件的预览
        $().toastmessage('showWarningToast', '该文档类型不支持预览');
    };
    ZIP.actionMap['compress'] = function (data, index) {
        //暂不支持该类型文件的预览
        $().toastmessage('showWarningToast', '不支持内部压缩文件预览');
    };
    //html
    // ZIP.actionMap['html'] = function(data, index) {
    // 	$.picasa({
    // 		thumb: false,
    // 		data: [{
    //             //文档转换地址
    //             iframe: ZIP.root+data.dir.path + data.name,
    //             title: data.name
    //         }],
    //         download: function(data) {
    //         	window.open(data.iframe,"_blank");
    //         }
    //     });
    // };
    //图片
    // ZIP.actionMap['img'] = function(data, index) {
    //     var datas = [],
    //         dir = data.dir;
    // 		active = 0;
    //     $.each(dir.children, function(i, o) {
    //         if (o.action == "img") {
    // 			if(i==index){
    // 				active = datas.length;
    // 			}
    //             datas.push({
    //                 src: ZIP.root+dir.path +  o.name,
    //                 thumb: ZIP.root+dir.path + o.name,
    //                 title: o.name
    //             });
    //         }
    //     });
    //     $.picasa({
    //         data: datas,
    // 		active: active,
    //         download: function(data) {
    //         	window.open(data.src);
    //         }
    //     });
    // };

    //可预览文件
    ZIP.actionMap['html'] = ZIP.actionMap['doc'] = ZIP.actionMap['xls'] = ZIP.actionMap['ppt'] = ZIP.actionMap['txt'] = ZIP.actionMap['pdf'] = ZIP.actionMap['ofd'] = ZIP.actionMap['img'] = function (data, index) {
        dataUrl = basePath + data.previewUrl;
        $.picasa({
            //width: 900, //强制宽度900，不设置则自适应
            thumb: false,
            padding: [30, 60, 50, 60],
            data: [{
                //文档转换地址
                iframe: dataUrl,
                title: "永中文档转换服务"
            }],
            setTitle: function (n, itm) {
                return itm.title;
            },
            // download: function() {
            // 	  window.open(data.name,"_blank");
            // },
            create: function () {
                //把title的位置往下移一点
                this.title.css('bottom', 10);
            }
        });




    };
    //============================start============================
    getQuery();
    // ZIP.render(); 
});
function getQuery() {
    //http://localhost:8080/dcs.web/view.html?MjAxNS0xMi0xNCAxMDo0ODozMDYy==%E6%9C%B1%E7%8E%AE+J2EE%E5%BC%80%E5%8F%91.doc
    var url = window.location.href;
    zipFileName = url.substring(url.lastIndexOf(".html?") + 6);
    // pages = new Array();
    //	 var showName = decodeURI(url.substring(url.indexOf("==")+2));
    // $(".container-fluid a").html(showName);


    if (fileData.result == 0) {
        var configJSON = fileData.foldJson;
        // var staticPath =data.staticPath;
        /* console.info(JSON.stringify(configJSON));
        console.info(ZIP); */
        $("#zipName").html(fileData.foldJson.name);
        ZIP.data = fileData.foldJson;
        ZIP.dir = fileData.foldJson;
        //  ZIP.root = data.staticPath; 
        //  $("#downloadzip").attr("href",basePath+data.staticPath+data.foldJson.position);
        check(ZIP.data, '');
        ZIP.render();
    } else {
        alert(fileData.message);
    }
}
//检测转换类型选择的变化,在success方法中变为true
$("#convertType").change(function () {
    flag = false;
});