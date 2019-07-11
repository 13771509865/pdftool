var isRotate;

    	
	$(window).resize(function(){
		resizeDiv()
	})
	window.onload = function(){
		resizeDiv()
		$(".row-fluid img").mousewheel(function(event,delta){
			
		})
	}	   

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
		var navHeight = $(".navbar").is(':visible') ? $(".navbar").height() : 0
		var radio = datas[0].width / datas[0].height;
		var areaW = document.documentElement.clientWidth
		var areaH = document.documentElement.clientHeight - navHeight;
		var zoneRadio = areaW / areaH
		$(".container-fluid-content").css({
			"position":"absolute",
			"width":areaW + "px",
			"height":areaH + "px",
			"top":navHeight + "px"
			
		})
		if(radio > zoneRadio){
			var imgWidth = datas[0].width > areaW ? areaW : datas[0].width
			var imgHeight = imgWidth / radio
		}else{
			var imgHeight = datas[0].height > areaH ? areaH : datas[0].height;
			var imgWidth = imgHeight * radio
		}
		$(".row-fluid img,.row-fluid .box_div").css({
			"width":imgWidth+ "px",
			"height":imgHeight + "px",
			"top":(areaH - imgHeight) > 0 ? (areaH - imgHeight) / 2 : 0 + "px",
			"left":(areaW - imgWidth) > 0 ? (areaW - imgWidth) / 2 : 0 + "px"  
		})	
		// var imgWidth = $(".word-page img").width();
		// var imgHeight = $(".word-page img").height();
		// if(isRotate){
		// 	$(".word-page").css({
		// 		"position":"relative",
		// 		"top":(imgWidth/2 - imgHeight/2 + 50)+ "px"
		// 	})		
		// }else{
		// 	$(".word-page").css({
		// 		"position":"relative",
		// 		"top":"50px"
		// 	})
		// }
	}