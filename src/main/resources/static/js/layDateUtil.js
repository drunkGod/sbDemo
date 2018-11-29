//若存在日期框，则渲染日期。
function renderDate(obj, dateValue){
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		//默认时间格式
		var _dateFormat = "yyyy-MM-dd";
		//默认时间值
		var _dateValue = null;
		var _dateType = null;
		if ($(obj).attr('dateFormat') != null) {
			_dateFormat = $(obj).attr('dateFormat');
		}
		if (dateValue != null && dateValue != '') {
			_dateValue = new Date(parseInt(dateValue));
		}
		if ($(this).attr('dateType') != null) {
			_dateType = $(this).attr('dateType');
		}
		laydate.render({
			elem : obj,
			format : _dateFormat,
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
			var _dateType = null;
			if ($(this).attr('dateFormat') != null) {
				_dateFormat = $(this).attr('dateFormat');
			}
			if ($(this).attr('dateType') != null) {
				_dateType = $(this).attr('dateType');
			}
			laydate.render({
				elem : this,
				format : _dateFormat,
				type : _dateType
			});
		})
	})
}

$(function() {
	//初次加载页面，渲染日期框
	renderAllDate();
});


