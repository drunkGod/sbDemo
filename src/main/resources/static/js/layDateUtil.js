//若存在日期框，则渲染日期。
function renderDate(obj, dateValue){
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		//默认时间格式
		var _dateFormat = "yyyy-MM-dd";
		//默认时间值
		var _dateValue = null;
		//默认时间类型: date, 包括year,moth,time,date,datetime
		var _dateType = 'date';
		
		if ($(obj).attr('dateFormat') != null) {
			_dateFormat = $(obj).attr('dateFormat');
		}
		
		if ($(this).attr('dateType') != null) {
			_dateType = $(this).attr('dateType');
		}
		
		if (dateValue != null && dateValue != '') {
			//时间值的两种形式：2018-08-08 或 1414155455
			if((dateValue+'').indexOf('-') == -1) {
				_dateValue = new Date(parseInt(dateValue));
			} else {
				_dateValue = dateValue;
			}
		}
		
		laydate.render({
			elem : obj,
			format : _dateFormat,
			value : _dateValue,
			type : _dateType
		});
	})
	
}

//若存在日期框，则渲染日期。
function renderAllDate(){
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		$('.layDate').each(function(){
			//默认时间格式
			var _dateFormat = "yyyy-MM-dd";
			//默认时间值
			var _dateValue = null;
			//默认时间类型: date, 包括year,moth,time,date,datetime
			var _dateType = 'date';
			
			if ($(this).attr('dateFormat') != null) {
				_dateFormat = $(this).attr('dateFormat');
			}
			
			if ($(this).attr('dateType') != null) {
				_dateType = $(this).attr('dateType');
			}
			
			laydate.render({
				elem : this,
				format : _dateFormat,
				value : _dateValue,
				type : _dateType
			});
		})
	})
}

$(function() {
	//初次加载页面，渲染日期框
	renderAllDate();
});


