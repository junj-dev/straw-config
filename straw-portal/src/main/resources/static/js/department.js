$(function() {
	$("#example1").DataTable();
	$(".select2").select2();

});
function addDepartment() {
	$("#addlecturer").modal("show");

}
//激活使用
function enableDepartment(id){
	var isEnable=confirm("确定启用该部门么使用?");
	if(isEnable){
		$.ajax({
			url:"enable",
			type:"post",
			data:{
				"id":id
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
				alert("提交失败,请刷新重试!");
			}
		});
	}
}

//暂停部门的使用
function pauseDepartment(id){
	var isPause=confirm("确定要暂停使用?");
	if(isPause){
		$.ajax({
			url:"pause",
			type:"post",
			data:{
				"id":id
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
				alert("提交失败,请刷新重试!");
			}
		});
	}
}

//删除部门信息
function deleteDepartment(id){
	var isDelete=confirm("确定要删除么?");
	if(isDelete){
		$.ajax({
			url:"delete",
			type:"post",
			data:{
				"id":id
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
//编辑部门信息
function editDepartment(id){
	var code=$("#code"+id).text();
	console.log("code:"+code);
	var name=$("#deptName"+id).text();
	console.log("name:"+name);
	$("#edit_code_model").val(code);
	$("#edit_name_model").val(name);
	$("#edit_name_model").attr("data",id);
	$("#editDepartmentModel").modal("show");
}

//保存部门信息
function saveDepatment(){
	var code=$("#code").val();
	if(code==''||code==undefined){
		alert("请输入代码");
		return false;
	}
	console.log("code:"+code);
	var deptName=$("#name").val();
	if(deptName==''||deptName==undefined){
		alert("请输入部门名称");
		return false;
	}
	console.log("name:"+deptName);
	
	$.ajax({
		url:"save",
		type:"post",
		data:{
			"code":code,
			"deptName":deptName
		},
		dataType:"json",
		success:function(result){
			if(result.state==0){
				showSuccess("保存成功");
				$("#addlecturer").modal("hide");
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
//保存编辑后的部门信息
function saveEditDepatment(){
	var code=$("#edit_code_model").val();
	if(code==undefined||code==""){
		alert("请输入部门代码");
		return false;
	}
	var deptName=$("#edit_name_model").val();
	if(deptName==undefined||deptName==""){
		alert("请输入部门名称");
		return false;
	}
	
	var deptNo=$("#edit_name_model").attr("data");
	$.ajax({
		url:"update",
		type:"post",
		data:{
			"deptNo":deptNo,
			"code":code,
			"deptName":deptName
		},
		dataType:"json",
		success:function(result){
			if(result.state==0){
				showSuccess("保存成功");
				$("#editDepartmentModel").modal("hide");
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