$(function() {
	$("#example1").DataTable();
	$(".select2").select2();

});
function addJob() {
	$("#addJob").modal("show");

}
//保存岗位信息
function saveJob(){
	var jobName=$("#save_name_model").val();
	if(jobName==undefined||jobName==""){
		alert("请输入岗位信息");
		return false;
	}
	console.log("jobName:"+jobName);
	$.ajax({
		url:"save",
		type:"post",
		data:{
			"jobName":jobName
		},
		dataType:"json",
		success:function(result){
			if(result.state==0){
				showSuccess("保存成功");
				location.reload();
			}else{
				alert(result.message);
			}
		},
		error:function(){
			alert("提交失败");
		}
	});
}
//启用
function enableJob(jobNo){
	var isEnable=confirm("确实要启用该岗位?");
	if(isEnable){
		$.ajax({
			url:"enable",
			type:"post",
			data:{
				"jobNo":jobNo
			},
			dataType:"json",
			success:function(result){
				if(result.state==0){
					showSuccess("操作成功");
					location.reload();
				}else{
					alert(result.message);
				}
			},
			error:function(){
				alert("提交失败");
			}
		});
	}
}


//暂停使用
function pauseJob(jobNo){
	var isPause=confirm("确实要停用该岗位?");
	if(isPause){
		$.ajax({
			url:"pause",
			type:"post",
			data:{
				"jobNo":jobNo
			},
			dataType:"json",
			success:function(result){
				if(result.state==0){
					showSuccess("操作成功");
					location.reload();
				}else{
					alert(result.message);
				}
			},
			error:function(){
				alert("提交失败");
			}
		});
	}
}



//删除岗位
function deleteJob(jobNo){
	var isDelete=confirm("确实删除?");
	if(isDelete){
		$.ajax({
			url:"delete",
			type:"post",
			data:{
				"jobNo":jobNo
			},
			dataType:"json",
			success:function(result){
				if(result.state==0){
					showSuccess("保存成功");
					location.reload();
				}else{
					alert(result.message);
				}
			},
			error:function(){
				alert("提交失败");
			}
		});
	}
	
}

//编辑岗位
function editJob(id){
	var name=$("#jobName"+id).text();
	console.log("name:"+name);
	$("#edit_name_model").val(name);
	$("#edit_name_model").attr("data",id);
	$("#editJob").modal("show");
}

//保存修改后的岗位信息
function saveEditJob(){
	var name=$("#edit_name_model").val();
	var jobNo=$("#edit_name_model").attr("data");
	console.log("name:"+name);
	if(name==undefined||name==""){
		alert("岗位信息不能为空！");
		return false;
		
	}
	
	$.ajax({
		url:"update",
		type:"post",
		data:{
			"jobNo":jobNo,
			"jobName":name
		},
		dataType:"json",
		success:function(result){
			if(result.state==0){
				showSuccess("修改成功");
				location.reload();
			}else{
				alert(result.message);
			}
		},
		error:function(){
			alert("提交失败");
		}
	});
	
}