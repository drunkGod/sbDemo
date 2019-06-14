

//禁用元素，持续3s时间
function disabledThreeSecond(selector){
	//disable元素
	$(selector).attr('disabled', true);
	setTimeout(function (){
		//3s后，如果元素还存在，则使其可用
		if($(selector).length > 0) {
			$(selector).attr('disabled', false);
		}
    },3000);
}

//禁用元素，持续特定时间
function disabledSetSecond(selector, second){
	//disable元素
	$(selector).attr('disabled', true);
	setTimeout(function (){
		//3s后，如果元素还存在，则使其可用
		if($(selector).length > 0) {
			$(selector).attr('disabled', false);
		}
    },second);
}

//给元素添加特定元素，持续2s时间
function redBorderTwoSecond(selector){
	//添加红色边框
	$(selector).addClass('redBorder');
	setTimeout(function (){
		//3s后，如果元素还存在，则使其可用
		$(selector).removeClass('redBorder');
    },2000);
}

//给元素添加特定元素，持续特定时间
function redBorderSetSecond(selector, second){
	//添加红色边框
	$(selector).addClass('redBorder');
	setTimeout(function (){
		//3s后，如果元素还存在，则使其可用
		$(selector).removeClass('redBorder');
    }, second);
}

//无附加条件根据tableId重新加载table
function reloadTable(tableId) {
	layui.table.reload(tableId, {
		where : {
			pageSize : 10,
			currentPage : 1,
		}
	})
}

//重新加载当前的iframe
function reloadCurrentIFrame() {
	var parentIFrame = parent.document.getElementsByClassName('layui-show')[0];
	$(parentIFrame).children()[0].contentWindow.location.reload(true);
}

//加载form表单,填充数据
function loadDataToForm(formId, data) {
	//可以直接赋值的：文本框，文本域，下拉框
	var _textElts = $(formId).find("input[type='text'],input[type='hidden'],input[type='number'],textarea,select");
	_textElts.each(function(){
		var _fullname = $(this).attr('name');
		if(_fullname != null && _fullname != undefined){
			//如果是时间，用渲染的方式赋值
			if($(this).attr('class') !=null && $(this).attr('class') !=undefined 
					&& $(this).attr('class').indexOf('layDate') != -1){
				renderDate(this, data[_fullname]);
				return true;
			}
			//其他的直接赋值
			if(data[_fullname]) {
		  		$(this).val(data[_fullname]);
		  	}
		}
	})
	//单选按钮只能选一个
	var _textElts = $(formId).find("input[type='radio']");
	_textElts.each(function(){
		var _fullname = $(this).attr('name');
  		if(data[_fullname]) {
  			if($(this).attr('value') == data[_fullname]){
	  			this.checked = true;
	  		} else {
	  			this.checked = false;
	  		}
  		}
	})
	//复选框
	var _checkboxElts = $(formId).find("input[type='checkbox']");
	_checkboxElts.each(function(){
		var _fullname = $(this).attr('name');
		if(data[_fullname]) {
			this.checked = true;
		} else {
  			this.checked = false;
  		}
	})
}

//一般打开
function commonOpen(title, openUrl, width, height) {
	parent.layer.open({
		  type: 2,
		  title: title,
		  shadeClose: false,
		  maxmin: true,
		  skin: 'layer-ext-moon',
		  shade: 0.2,
		  area: [width, height],
		  content: openUrl,
	}); 
}

//一般提示
function commonTips(selector, msg){
	//side:1上，2右边，3下，4左；time:秒
	if($(selector).length > 0) {
		$(selector).focus();
		$(selector).tips({
			side:3,
			msg:msg,
			bg:'#AE81FF',
			time:2
		});
	}
}

//一般提示
function commonMsg(msg){
	parent.layer.msg(msg);
}

//一般加载
function commonLoadForm(formId, mode, loadUrl ){
	//add模式下，不需加载表单
	if(mode == 'add'){
		return;
	}
	
	$.ajax({
        url: loadUrl,
        type: "post",
        dataType: "json",
        data: {},
        success: function (res) {
        	if(res.code == 200){
        		//加载Form表单数据
        		loadDataToForm(formId, res.data);
        		//view模式下，Form表单只读
        		if(mode == 'view'){
        			disabledForm(formId);
        			$('#saveBtn').css('display', 'none');
        		}
        	} else {
        		parent.layer.msg('记录不存在！', {icon:5});
        	}
        },
		error: function (res) {
			parent.layer.msg('加载数据失败', {icon:5});
        }
 	});
}

//一般删除的ajax
function commonDelete(delUrl){
	$.ajax({
        url: delUrl,
        type: "post",
        dataType: "json",
        data: {},
        success: function (res) {
        	if(res.code == 200){
        		parent.layer.msg('删除成功', {icon:6});
        		//刷新当前iframe
        		reloadCurrentIFrame();
        	}
        },
		error: function (res) {
			parent.layer.msg('删除失败', {icon:5});
        }
 	})
}

//一般验证数据有效性: 验证required的元素
function commonCheckValid(){
	var _textElts =  $("*").find("input[type='text'],input[type='hidden'],input[type='number'],input[type='password'],textarea");
	let valid = true;
	_textElts.each(function(){
		var _fullname = $(this).attr('name');
		if(_fullname != null && _fullname != undefined){
			//如果是时间，用渲染的方式赋值
			if($(this).attr('required') != null && $(this).attr('required') != undefined){
				if($(this).val() == ''){
					valid = false;
					commonTips(this, '必填信息不能为空！');
					return false;
				}
			}
		}
	});
	return valid;
}

//按一般流程保存save表单：
function commonSubmitForm(formId){
	
	//提示加载
	parent.layer.load(1, {time: 3*1000});
	$(formId).ajaxSubmit(function (res) {
		if(res.code == 200){
			//成功后，1.关闭弹窗，2.刷新页面，3.提示保存成功。
			parent.layer.closeAll();
			reloadCurrentIFrame();
			parent.layer.msg('保存成功！', {icon: 6, time: 2*1000});

		}else{
			//关闭加载，提示失败
			parent.layer.closeAll('loading');
			if(res.message != null && res.message != '') {
				parent.layer.msg( res.message, {icon:5, time: 2*1000});
			} else {
				parent.layer.msg('保存失败！', {icon:5, time: 2*1000});
			}
		}
	});
}

//禁用form表单
function disabledForm(formId) {
	$(formId).find('*').attr('disabled', true);
}

//检验是否为空
function isEmpty(val){
	if(val == null || val == undefined || val == ''){
		return true;
	}
	return false;
}

//检验是否为非空
function isNotEmpty(val){
	return !isEmpty(val);
}

//检验是否是一位以上数字
function isNum(val){
	if(/^\d+$/.test(val)){
		return true;
	}
}

//校验手机号
function isValidPhone(val){
	if(/^[1][3-9][0-9]{9}$/.test(val)){
		return true;
	}
	return false;
}

//校验身份证号
function isValidIdCard(val){
	if(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x))$/.test(val)){
		return true;
	}
	return false;
}