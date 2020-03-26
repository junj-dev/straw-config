$(function () {
    $('.select2').select2();
    $('#period').daterangepicker(
        {
            // autoApply: true,
            autoUpdateInput: false,
            // alwaysShowCalendars: true,
            ranges: {
                '今天': [moment(),moment()],
                '昨天': [moment().subtract(1, 'days'),moment().subtract(1, 'days')],
                '近7天': [moment().subtract(7, 'days'), moment()],
                '这个月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            locale: {
                format: "YYYY/MM/DD HH:MM:SS",
                separator: " - ",
                applyLabel: "确认",
                cancelLabel: "清空",
                fromLabel: "开始时间",
                toLabel: "结束时间",
                customRangeLabel: "自定义",
                daysOfWeek: ["日","一","二","三","四","五","六"],
                monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            }
        }
    ).on('cancel.daterangepicker', function(ev, picker) {
        $("#period").val("请选择日期范围");

    }).on('apply.daterangepicker', function(ev, picker) {
        $("#period").val(picker.startDate.format('YYYY-MM-DD')+" 至 "+picker.endDate.format('YYYY-MM-DD'));
    });
});
var vm = new Vue({
    el: '#app',
    data: {
        page: 0,  //显示的是哪一页
        pageSize: 10, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:9, //最大页数
        questions:[],//问题数组
        tags:[]//标签数组ss



    },
    methods: {
        //pagehandler方法 跳转到page页
        pageHandler: function (pageNum) {
            //here you can do custom state update
            this.page = pageNum;
            var pageSize=this.pageSize;
            var _this=this;

            var tagId=$("#tagSelect").val();
            console.log("标签："+tagId);
            var questUserName=$("#questionUserName").val();
            console.log("提问者："+questUserName);
            var dateRange=$("#period").val();
            var startDate='';
            var endDate='';
            if(dateRange!=''){
                var dates=dateRange.split("至");
                startDate=dates[0];
                endDate=dates[1];

            }
            console.log("开始日期："+startDate);
            console.log("结束日期："+endDate);
            console.log("日期:"+dates);
            var answerStatus=$("#answerStatusSelected").val();
            console.log("回答状态："+answerStatus);
            var publicStatus=$('#publicStatusSelected').val();
            console.log("公开状态："+publicStatus);

            $.ajax({
                type:"post",
                url:"/question/findQuestionByCondition",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize,
                    "tagId":tagId,
                    "questUserName":questUserName,
                    "startDateStr":startDate,
                    "endDateStr":endDate,
                    "answerStatus":answerStatus,
                    "publicStatus":publicStatus

                },
                dataType: "json",
                success:function (res) {
                    if(res.code==200){
                        _this.total=res.data.total;
                        _this.pages=res.data.totalPage;
                        _this.questions=res.data.list;
                    }
                }
            });
        },
        //加载标签
        loadTgs: function () {
            var _this=this;
            $.get("/tag/findAllTags",function(result){
                if(result.code==200){
                    _this.tags=result.data;
                    console.log("tags:"+result.data);
                }
            });
        },
        //设置提问为公开
        setQuestionPublic:function (e) {

            var id=e.target.dataset.id;
            console.log("id:"+id);
            var _this=this;
            $.get("/question/setQuestionPublic/"+id, function(result){
                if(result.code=="200"){
                    console.log("成功设置提问为公开！");
                    _this.pageHandler(1);
                    alert("设置成功");
                }else {
                    alert("设置失败");
                }
            });


        },
        //取消提问公开
        cancelQuestionPublic:function (e) {
            var id=e.target.dataset.id;
            console.log("id:"+id);
            var _this=this;
            $.get("/question/cancelQuestionPublic/"+id, function(result){
                if(result.code=="200"){
                    console.log("成功设置提问为公开！");
                    _this.pageHandler(1);
                    alert("设置成功");
                }else {
                    alert("设置失败");
                }
            });
        }

    },
    created:function(){
        //created  表示页面加载完毕，立即执行
        this.pageHandler();
        this.loadTgs();
    }
});