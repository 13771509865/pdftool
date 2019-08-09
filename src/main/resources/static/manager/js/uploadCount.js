$(function() {
	getData();
})



function getData() {
	$.ajax({ 
			url:"../statistics/uploadTimes",
			type:"POST",
			data : "",
			success:function (obj){
				if(obj.errorcode == 0){
					var html = '<tr><td>' 
						+ obj.data.successNum + '</td><td>'
						+ obj.data.failNum + '</td><td>'
						+ obj.data.count+"</td></tr>";
					$('.tbody').html(html);
				}else{
					alert(obj.message);
				}
			}
	 });
}





