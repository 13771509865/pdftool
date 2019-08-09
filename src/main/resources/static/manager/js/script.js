// 模拟后台接口
function getData(params) {
	var data = [];
	$.ajax({ 
			url:"../statistics/ipConvert",
			type:"POST",
			data : "",
			async : false, 
			success:function (obj){
				if(obj.errorcode == 0){
					data = obj.data;
				}else{
					alert(obj.message);
				}
			}
	 });
	
	var start = (params.current - 1) * params.size;
	var end = params.current *params.size;
	
	return {
		total: data.length,
		list: data.splice( (params.current - 1) * params.size, params.size )
	}
}

// 设置tbody的html
function setTbody (arr) {
	var html = '';
	alert(arr.length);
	for (var i = 0; i < arr.length; i++) {
		var item = arr[i];
		
		html += '<tr><td>' 
			+ item.zeroToThree + '</td><td>' 
			+ item.threeToFive + '</td><td>' 
			+ item.fiveToTen + '</td><td>' 
			+ item.tenToFifteen + '</td><td>' 
			+ item.fifteenToTwenty + '</td><td>' 
			+ item.twentyToThirty + '</td><td>' 
			+ item.thirtyToFourty + '</td><td>' 
			+ item.fourtyToFifty + '</td><td>' 
			+ item.fiftyMore + '</td><td>'
			+getDate(item.createDate)+"</td></tr>";
	}
	$('.tbody').html(html);
}


function getDate(data) {
	  var date = new Date(data)
	  var Y = date.getFullYear() + '-'
	  var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
	  var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
	  return Y + M + D
	}
	

// 初始化分页
$('.box2').MyPaging({
	size: 3,
	total: 0,
	current: 1,
	prevHtml: '上一页',
	nextHtml: '下一页',
	layout: 'total, totalPage, prev, pager, next, jumper',
	jump: function () {
		var _this = this;

		// 模拟ajax获取数据
		setTimeout(function () {
			var res = getData({
				size: _this.size,
				current: _this.current
			})

			setTbody(res.list);

			// 必须调用
			_this.setTotal(res.total);
		}, 100);
	}
});