


$(function() {
	// 初始化讲师投诉表
	var table1=$('#teacher_complain_table').DataTable({
		 language : lang,
		 	ajax: {
		        url: "/complain/getTeacherComlain",
		        data: {
		            "year": 0,
		            "rank_start_5":0,
		            "rank_start_4":0,
		            "rank_start_3":0,
		            "rank_start_2":0,
		            "rank_start_1":0,
		            "rank_end_5":0,
		            "rank_end_4":0,
		            "rank_end_3":0,
		            "rank_end_2":0,
		            "rank_end_1":0,
		            "isFirsrSerach":"true"
		            
		        }
		    },
		 columns : [  {
				"data" : "id"
			}, {
				"data" : "teacherName"
			}, {
				"data" : "departmentName"
			}, {
				"data" : "departmentCode"
			}, {
				"data" : "studentNum"
			}, {
				"data" : "complainNum"
			}, {
				"data" : "complainPercentStr"
			},{
				"data" : "rank"
			},{
				"data" : "rankPercentStr"
			},{
				"data":"minusScore"
			}]
	});

	
	// 初始化总监投诉表
	var table2=$('#monitor_complain_table').DataTable({
		 language : lang,
		 	ajax: {
		        url: "/complain/getMonitorComplain",
		        data: {
		            "year": 0,
		            "startMonth":0,
		            "endMonth":0
		        }
		    },
		 columns : [  {
				"data" : "id"
			}, {
				"data" : "teacherName"
			}, {
				"data" : "departmentName"
			}, {
				"data" : "departmentCode"
			}, {
				"data" : "studentNum"
			}, {
				"data" : "complainNum"
			}, {
				"data" : "complainPercentStr"
			},{
				"data" : "rank"
			},{
				"data" : "rankPercentStr"
			}]
	});
	
	// 初始化总监投诉表
	var table2=$('#department_complain_table').DataTable({
		 language : lang,
		 	ajax: {
		        url: "/complain/getDepartmentComplain",
		        data: {
		            "year": 0,
		            "startMonth":0,
		            "endMonth":0
		        }
		    },
		 columns : [  {
				"data" : "id"
			}, {
				"data" : "department"
			}, {
				"data" : "departmentCode"
			}, {
				"data" : "sumComplain"
			}, {
				"data" : "totalStudent"
			}, {
				"data" : "complainPercentageStr"
			}]
	});
	
	
	
	
	
	$(".select2").select2();

});


function searchTeacherComplain(){
	//获取年份
	var year=$('#teacher_complain_year option:selected').val();
	console.log("year:"+year);
	var rank_start_5=$("#rank_start_5").val();
	var rank_start_4=$("#rank_start_4").val();
	var rank_start_3=$("#rank_start_3").val();
	var rank_start_2=$("#rank_start_2").val();
	var rank_start_1=$("#rank_start_1").val();
	var rank_end_5=$("#rank_end_5").val();
	var rank_end_4=$("#rank_end_4").val();
	var rank_end_3=$("#rank_end_3").val();
	var rank_end_2=$("#rank_end_2").val();
	var rank_end_1=$("#rank_end_1").val();
	if(rank_start_5!=""&&rank_start_4!=""&&rank_start_3!=""&&rank_start_2!=""&&rank_start_4!=""
		&&rank_end_5!=""&&rank_end_4!=""&&rank_end_3!=""&&rank_end_2!=""&&rank_end_1!=""){
		//绑定保存事件的参数
		$("#generateResult").attr("onclick","generateResult("+year+","+rank_start_5+","+rank_start_4+","+rank_start_3+","+
				rank_start_2+","+
				rank_start_1+","+
				rank_end_5+","+
				rank_end_4+","+
				rank_end_3+","+
				rank_end_2+","+
				rank_end_1+")")
	}else{
		$("#generateResult").attr("onclick","javascript:alert('未填写扣分排名，不能保存')");
	}
	
	
	
	
    var param = {
            "year": year,
            "rank_start_5":rank_start_5,
            "rank_start_4":rank_start_4,
            "rank_start_3":rank_start_3,
            "rank_start_2":rank_start_2,
            "rank_start_1":rank_start_1,
            "rank_end_5":rank_end_5,
            "rank_end_4":rank_end_4,
            "rank_end_3":rank_end_3,
            "rank_end_2":rank_end_2,
            "rank_end_1":rank_end_1,
            "isFirsrSerach":"false"
           
        };
    var table=$('#teacher_complain_table').DataTable();
    table.settings()[0].ajax.data = param;
    table.ajax.reload();

}

function generateResult(year,rank_start_5,rank_start_4,rank_start_3,
		rank_start_2,rank_start_1,rank_end_5,rank_end_4,rank_end_3,rank_end_2,rank_end_1){
	 var param = {
	            "year": year,
	            "rank_start_5":rank_start_5,
	            "rank_start_4":rank_start_4,
	            "rank_start_3":rank_start_3,
	            "rank_start_2":rank_start_2,
	            "rank_start_1":rank_start_1,
	            "rank_end_5":rank_end_5,
	            "rank_end_4":rank_end_4,
	            "rank_end_3":rank_end_3,
	            "rank_end_2":rank_end_2,
	            "rank_end_1":rank_end_1
	        };
	$.ajax({
		url:"/complain/saveTeacherComplainRsult",
		type:"post",
		data:param,
		dataType:"json",
		success:function(result){
			if(result.state==0){
				alert("保存报表成功");
			}else{
				alert(result.message);
			}
		},
		error:function(){
			alert("保存失败，刷新重试");
		}
	})
	
	
}
function searchMonitorComplain(){
	//获取年份
	var year=$('#monitor_complain_year option:selected').val();
	console.log("year:"+year);
//	var startMonth=$('#monitor_complain_startMonth option:selected').val();
//	console.log("startMonth:"+startMonth);
//	var endMonth=$('#monitor_complain_endMonth option:selected').val();
//	console.log("endMonth:"+endMonth);
    var param = {
            "year": year,
            "startMonth":1,
            "endMonth":12
        };
    var table=$('#monitor_complain_table').DataTable();
    table.settings()[0].ajax.data = param;
    table.ajax.reload();

}

function searchDepartmentComplain(){
	//获取年份
	var year=$('#department_complain_year option:selected').val();
	console.log("year:"+year);
//	var startMonth=$('#department_complain_startMonth option:selected').val();
//	console.log("startMonth:"+startMonth);
//	var endMonth=$('#department_complain_endMonth option:selected').val();
//	console.log("endMonth:"+endMonth);
    var param = {
            "year": year,
            "startMonth":1,
            "endMonth":12
        };
    var table=$('#department_complain_table').DataTable();
    table.settings()[0].ajax.data = param;
    table.ajax.reload();

}
var lang = {
		"sProcessing" : "处理中...",
		"sLengthMenu" : "每页 _MENU_ 项",
		"sZeroRecords" : "没有匹配结果",
		"sInfo" : "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
		"sInfoEmpty" : "当前显示第 0 至 0 项，共 0 项",
		"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
		"sInfoPostFix" : "",
		"sSearch" : "搜索:",
		"sUrl" : "",
		"sEmptyTable" : "表中数据为空",
		"sLoadingRecords" : "载入中...",
		"sInfoThousands" : ",",
		"oPaginate" : {
			"sFirst" : "首页",
			"sPrevious" : "上页",
			"sNext" : "下页",
			"sLast" : "末页",
			"sJump" : "跳转"
		},
		"oAria" : {
			"sSortAscending" : ": 以升序排列此列",
			"sSortDescending" : ": 以降序排列此列"
		}
	};

function exportYearReport(){
	$("#exportYearReport").modal("show");
}