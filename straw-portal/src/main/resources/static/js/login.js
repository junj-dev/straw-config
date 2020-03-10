$(document).ready(function(){
	//回车按钮
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			//触发btn-login绑定的submit事件
			$("#btn-login").trigger("click");
		}
	});
	//点击登录按钮
	$('#btn-login').click(doLogin)
})
function doLogin(){

	var userName = $('#username').val();
	var userPwd = $('#userpwd').val();
	var validate=$("#validateInput").val();
	console.log("validate"+validate);
	if(userName==''){
		$('#errorMessage').parent().parent().css('display','block');
		$('#errorMessage').text('用户名不能为空！');
		return false;
	}
	if(userPwd==''){
		$('#errorMessage').parent().parent().css('display','block');
		$('#errorMessage').text('密码不能为空！');
		return false;
	}
//	if(validate==''){
//		$('#errorMessage').parent().parent().css('display','block');
//		$('#errorMessage').text('验证码不能为空！');
//		return false;
//		
//	}
	
	$.ajax({
		url : "/system/tologon",
		type : "post",
		data : {
			"username" : userName,
			"password" : userPwd,
			"validate" : validate
			
		},
		dataType : "json",
		success : function(result) {
		//	console.log(result);
			if (result.state == 0) {
				//console.log("登录成功！");
				location.href='/system/index';

			} else {
				refreshMe();
				$('#errorMessage').parent().parent().css('display','block');
				$('#errorMessage').text(result.message);
				
			}

		},
		error : function() {
			$('#errorMessage').parent().parent().css('display','block');
			$('#errorMessage').text("帐号或密码错误！！");
			
		}
	});
}
function refreshMe(){
	var timestamp = Date.parse(new Date());
	console.log(timestamp);
	$("#validateImg").attr("src","/system/validateImg?id="+timestamp);
}