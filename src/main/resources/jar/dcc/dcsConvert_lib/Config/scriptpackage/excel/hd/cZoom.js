 $(function() {
    	var appendStr ='<div id="zoom" style="position: fixed; right: 30px; bottom:6px; width: 80px; height: 24px; z-index:1000000;display:none">'+
						   '<div style="float:right;border-radius:12px;width: 15px;height: 15px;text-align: center;font-size: 16px;cursor: pointer;" onclick="changeTab(1)"><img src="zoomIn.png" style="position: relative;top: 2px;opacity: 0.5;"/></div>'+
						     '<span class="scaleNum" style="display: inline-block;margin-top: 4px;margin-left:6px;margin-right:5px;width: 38px;height: 16px;text-align: center;overflow: hidden;"></span>'+
					   	   '<div style="float:left;border-radius:12px;width: 15px;height: 15px;text-align: center;font-size: 16px;cursor: pointer;" onclick="changeTab(0)"><img src="zoomOut.png" style="position: relative;top: 2px;opacity: 0.5;"/></div>'+
						'</div>';
		$('body').append(appendStr);	
		$(".scaleNum").text(Math.round(parseInt($("#scale").val())*100)+'%')			   
    });
    function changeTab(type){
    	var size = parseFloat($("#scale").val());
    	if(type==1 && size <= 4){
    		size = size + 0.2;
    	}else if(type==0 && size >= 0.4){
    		size = size - 0.2;
    	}
		$("#scale").val(size);
		$(".scaleNum").text(Math.round(size*100)+'%')
		$("#tbCol thead th").each(function(index,e){
			$(this).css("width",scrollX[index] * size+"px")
		})
		$("#tbRaw tbody tr").each(function(index,e){
			$(this).css("height",scrollY[index] * size+"px")
		})
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