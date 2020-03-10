var table;
$(function() {
	 table=$("#empTable").DataTable({
		"order": [[0, "asc"]],
		"oLanguage": {
			"sLengthMenu": "每页显示 _MENU_ 条记录",
			"sZeroRecords": "抱歉， 没有找到",
			"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
			"sInfoEmpty": "没有数据",
			"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
			"oPaginate": {
			"sFirst": "首页",
			"sPrevious": "前一页",
			"sNext": "后一页",
			"sLast": "尾页",
			},
			
			"sZeroRecords": "没有检索到数据",
			"sProcessing": "<img src='./loading.gif' />"
			}
	});
	
	
	
	$(".select2").select2();
	$('#upLoadEmpInfoExcel').click(
		function() {
			if (checkData()) {
				$('#uploadForm').ajaxSubmit(
					{
						url :'/creditInfo/uploadReceiverCreditInfoExcel.html',
						data : {
							'cacheVersion' : cacheVersion
						},
						dataType : 'text'
					});
			}
		});

});
function deleteEmp(id){
	var isDelete=confirm("确定删除?");
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
					showSuccess("删除成功!");
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
}
function search(){
	var data=$("#searchByDepatment option:selected").text();
	table.column( 1 ).search( data ).draw();
}

function show_add_emp_model() {
	$("#addEmp").modal("show");

}
function show_import_model() {
	$("#importExcel").modal("show");

}
function show_import_model2() {
	$("#importExcel2").modal("show");

}
function show_import_model3() {
	$("#importExcel3").modal("show");

}
function show_import_model4() {
	$("#importExcel4").modal("show");

}
function show_import_model5() {
	$("#importComplainDetailTable").modal("show");

}
function show_import_model6() {
	$("#importExcel6").modal("show");

}
function show_import_model7() {
	$("#importExcel7").modal("show");

}


function show_export_modal(){
	$("#exportDetailLesson").modal("show");
}

function show_export_modal2(){
	$("#exportReport").modal("show");
}
