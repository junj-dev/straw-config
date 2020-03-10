function addEmp1(){
	//部门
	var department= $("#department").find("option:selected").val();
	if(department=="-1"){
		$("#department_div").addClass("has-error");
		alert("请选择部门");
		return false;
	}
	var name=$("#name").val();
	if(name==undefined||name==""){
		$("#name_div").addClass("has-error");
		alert("请输入员工姓名");
		return false;
	}
	
	var zhiwei=$("#zhiwei").val();
	
	var job=$("#job").find("option:selected").val();
	if(job=="-1"){
		$("#job_div").addClass("has-error");
		alert("请选择岗位!");
		return false;
	}
	var state=$("input[name='state']:checked").val();
	if(state==undefined||state==""){
		$("#state_div").addClass("has-error");
		alert("请选择在职状态!");
		return false;
		
	}
	var phone=$("#phone").val();
	
	var email=$("#email").val();
	
	var entryDate=$("#entryDate").val();
	
	var ID=$("#ID").val();
	
	var sex=$("input[name='sex']:checked").val();
	
	if(sex==undefined||sex==""){
		$("#sex_div").addClass("has-error");
		alert("请选择性别!");
		return false;
	}
	var birthday=$("#birthday").val();
	
	
	$.ajax({
		url:"saveEmp",
		type:"post",
		data:{
			"departmentId":department,
			"empName":name,
			"zhiwei":zhiwei,
			"jobId":job,
			"state":state,
			"phone":phone,
			"email":email,
			"id":ID,
			"entryTimeStr":entryDate,
			"sex":sex,
			"birthdayStr":birthday
		},
		dataType:"json",
		success:function(result){
			if(result.state==0){
				showSuccess("保存成功");
				location.href="index";
			}else{
				alert(result.message);
			}
		},
		error:function(){
			alert("提交失败");
		}
	});
}



