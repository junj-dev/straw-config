$(function() {
	$("#example1").DataTable();
	$(".select2").select2();

});
function showModel() {
	$("#addlecturer").modal("show");

}
//保存级别信息
function saveLevel(){
	var name=$("#save_name_model").val();
	console.log("name:"+name);
	if(name==undefined||name==""){
		alert("级别名称不能为空!");
		return false;
	}
	$.ajax({
		url:"save",
		type:"post",
		data:{
			"levelName":name
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
//删除级别信息
function deleteLevel(id){
	var isDelete=confirm("确定删除？");
	if(isDelete){
		$.ajax({
			url:"delete",
			type:"post",
			data:{
				"levelNo":id
			},
			dataType:"json",
			success:function(result){
				if(result.state==0){
					showSuccess("删除成功");
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
//编辑级别信息
function showEditModel(id){
	var name=$("#name"+id).text();
	$("#edit_name_model").val(name);
	$("#edit_name_model").attr("data",id);
	$("#editLevel").modal("show");
}


//保存修改后的级别信息
function saveEditLevel(){
	var id=$("#edit_name_model").attr("data");
	var name=$("#edit_name_model").val();
	console.log("name:"+name);
	if(name==undefined||name==""){
		alert("级别信息不能为空!");
		return false;
	}
	$.ajax({
		url:"update",
		type:"post",
		data:{
			"levelNo":id,
			"levelName":name
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
//暂停使用该级别
function pauseLevel(id){
	
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
//继续使用该级别
function enableLevel(id){
	var isEnable=confirm("确定启用?");
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


