$(function() {
	getData();
})



function getData() {
	$.ajax({ 
			url:"../statistics/convertBySize",
			type:"GET",
			data : "",
			success:function (obj){
				if(obj.errorcode == 0){
					var html = '<tr><td>' 
						+ obj.data.zeroToThree + '</td><td>' 
						+ obj.data.threeToFive + '</td><td>' 
						+ obj.data.fiveToTen + '</td><td>' 
						+ obj.data.tenToFifteen + '</td><td>' 
						+ obj.data.fifteenToTwenty + '</td><td>' 
						+ obj.data.twentyToThirty + '</td><td>' 
						+ obj.data.thirtyToFourty + '</td><td>' 
						+ obj.data.fourtyToFifty + '</td><td>' 
						+ obj.data.fiftyMore +"</td></tr>";
					$('.tbody').html(html);
				}else{
					alert(obj.message);
				}
			}
	 });
}





