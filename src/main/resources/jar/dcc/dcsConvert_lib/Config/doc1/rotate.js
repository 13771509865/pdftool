var isRotate;
 $(function() {
    	var appendStr ='<div id="zoom" style="position: fixed; right: 10px; bottom: 10px; width: 24px; height: 140px;">'+
					   	'<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="changeTab(1)"><img src="'+basePath+'/zoomIn.png" style="opacity: 0.5;"/></div>'+
					   	'<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="changeTab(0)"><img src="'+basePath+'/zoomOut.png" style="opacity: 0.5;"/></div>'+
						'<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="rotate(1)"><img src="'+basePath+'/rotate_right.png" width="24" height="24" style="opacity: 0.5;"/></div>'+
						'<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="rotate(0)"><img src="'+basePath+'/rotate_left.png" width="24" height="24" style="opacity: 0.5;"/></div>'+
					   '</div><input id="angle" type="hidden" value="0">';
		$('body').append(appendStr);	
		resizeDiv()			   
    });
    function changeTab(type){
    	var size = parseFloat($("#scale").val());
    	if(type==1){
    		size = size + 0.2;
    	}else if(type==0){
    		size = size - 0.2;
    	}
		$("#scale").val(size)
		$(".span12").css({
			"transform-origin":"0% 0%",
			"-ms-transform-origin":"0% 0%",
			"-webkit-transform-origin":"0% 0%",
			"-moz-transform-origin":"0% 0%",
			"-o-transform-origin":"0% 0%",
			"transform":"scale(" + size + ")",
			"-ms-transform":"scale(" + size + ")",
			"-moz-transform":"scale(" + size + ")",
			"-webkit-transform":"scale(" + size + ")",
			"-o-transform":"scale(" + size + ")"})

    	
    }
	function rotate(direction){
		var angle = parseFloat($("#angle").val());
		if(direction == 1){
    		angle = angle + 90;
    	}else if(direction == 0){
    		angle = angle - 90;
    	};
		$("#angle").val(angle);
		if(angle/90%2 != 0){
			isRotate = true;
		}else{
			isRotate = false;
		};
		resizeDiv()	
		$(".word-page").css({
			"transform-origin":"50% 50%",
			"-ms-transform-origin":"50% 50%",
			"-webkit-transform-origin":"50% 50%",
			"-moz-transform-origin":"50% 50%",
			"-o-transform-origin":"50% 50%",
			"transform":"rotate(" + angle + "deg)",
			"-ms-transform":"rotate(" + angle + "deg)",
			"-moz-transform":"rotate(" + angle + "deg)",
			"-webkit-transform":"rotate(" + angle + "deg)",
			"-o-transform":"rotate(" + angle + "deg)"
		}).attr("data-angle",angle)
	}
	function resizeDiv(){
		var imgWidth = $(".word-page img").width();
		var imgHeight = $(".word-page img").height();
		if(isRotate){
			$(".word-page").css({
				"position":"relative",
				"top":(imgWidth/2 - imgHeight/2 + 50)+ "px"
			})		
		}else{
			$(".word-page").css({
				"position":"relative",
				"top":"50px"
			})
		}
	}