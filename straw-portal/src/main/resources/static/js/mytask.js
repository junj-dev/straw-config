Vue.component('v-select', VueSelect.VueSelect);
$(function () {
    $('.select2').select2();
});

var vm4=new Vue({
    el: '#app4',
    data: function() {
        return {
            options: [],
            placeholder: 'Choose a student..',
            teachers: [1],
            teacher_obj: []
        }
    },
    mounted: function() {
        var teacher_filter = function (obj) {
            return this.teachers.indexOf(obj.id) > -1
        }
        this.teacher_obj = this.options.filter(teacher_filter, this)
    },
    methods:{
        // 提示弹框
        alertDia (msg,timeout) {
            this.displayStsates = 'block'
            this.aletMsg = msg
            // 延迟2秒后消失 自己可以更改时间
            window.setTimeout(() => {
                this.displayStsates = 'none'
            }, timeout)
        },
        select_teacher: function(values){
            this.teachers =values.map(function(obj){
                return obj.id
            })
        },
        //加载老师
        loadTeachers:function () {
            var _this=this;
            $.get("/teacher/loadAllTeachers",function (result) {
                if(result.code==200){
                    _this.options=result.data;
                }else {
                    console.log("加载老师失败!");
                }
            });
        },
        //提交
        submitTransferToTeacher:function(){
            var _this=this;
            var questionIds=vm.checkedQuestionIds;
            if(questionIds.length==0){
                this.alertDia("操作前，请至少选择一个问题！",2000);
                return;
            }
            $.ajax({
                type:"post",
                url:"/question/transferToTeacher",
                data:{
                    "questionIds":questionIds,
                    "teacherIds":_this.teachers,


                },
                dataType: "json",
                success:function (res) {
                    if(res.code==200){
                        _this.alertDia("设置成功！",1500);
                        $("#app4").modal("hide");
                    }else {
                        _this.alertDia(res.msg,1500);
                    }
                }
            });

        }
    },
    created:function () {
        this.loadTeachers();
    }
});


var vm = new Vue({
    el: '#app1',
    data: {
        page: 0,  //显示的是哪一页
        pageSize: 10, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:9, //最大页数
        questions:[],//问题数组
        tags:[],//标签数组ss
        checkedQuestionIds: [],
        checked:false,
        aletMsg: '', // 弹出框中的提示语
        displayStsates: 'none',




    },
    methods: {

        // 提示弹框
        alertDia (msg,timeout) {
            this.displayStsates = 'block'
            this.aletMsg = msg
            // 延迟2秒后消失 自己可以更改时间
            window.setTimeout(() => {
                this.displayStsates = 'none'
            }, timeout)
        },
        changeAllChecked: function() {
            console.log("-------");
            if (this.checked) {
                var questions = this.questions
                for(let key in questions) {
                    console.log(questions[key]);
                    this.checkedQuestionIds.push(questions[key].id);
                }
            } else {
                this.checkedQuestionIds = []
            }
        },
        //pagehandler方法 跳转到page页
        pageHandler: function (pageNum) {
            //here you can do custom state update
            this.page = pageNum;
            var pageSize=this.pageSize;
            var _this=this;
            $.ajax({
                type:"post",
                url:"/question/findMyUnAnwerQuestion",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize
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
        //把问题转发给其他老师
        transferToTeacher:function(){
            var _this=this;
            var questionIds=this.checkedQuestionIds;
            if(questionIds.length==0){
                this.alertDia("操作前，请至少选择一个问题！",2000);
                return;
            }
            //打开模态框
            $("#app4").modal("show");


        },
        //把问题设置为已解决
        setQuestionSolved:function () {
            var _this=this;
            var questionIds=this.checkedQuestionIds;
            if(questionIds.length==0){
                this.alertDia("操作前，请至少选择一个问题！",2000);
                return;
            }
            $.ajax({
                type:"post",
                url:"/question/setQuestionSolved",
                data:{
                    "ids":_this.checkedQuestionIds

                },
                dataType: "json",
                success:function (res) {
                    if(res.code==200){
                     _this.alertDia("设置成功！",1500);
                        _this.pageHandler(1);
                        vm.pageHandler(1);
                        vm3.pageHandler(1);
                    }else {
                        _this.alertDia(res.msg,1500);
                    }
                }
            });

        },
        select_teacher: function(values){
            this.teacherIds =values.map(function(obj){
                return obj.id
            })
        }

    },
    watch: {

        "checkedQuestionIds": function() {
            console.log(this.checkedQuestionIds+"---"+this.questions+"----"+this.checked);
            if (this.checkedQuestionIds.length == this.questions.length) {
                this.checked = true
            } else {
                this.checked = false
            }

        }

    },
    created:function(){
        //created  表示页面加载完毕，立即执行
        this.pageHandler();


    }
});
var vm2 = new Vue({
    el: '#app2',
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
            $.ajax({
                type:"post",
                url:"/question/findMyUnSolveQuestion",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize
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
        //把问题设置为已解决
        setQuestionSolved:function (e) {
            var id=e.target.dataset.id;
            console.log("id:"+id);
            var _this=this;
            $.get("/question/setQuestionSolved/"+id, function(result){
                if(result.code=="200"){
                    console.log("成功设置提问为公开！");
                    _this.pageHandler(1);
                    vm.pageHandler(1);
                    vm3.pageHandler(1);
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

    }
});
var vm3 = new Vue({
    el: '#app3',
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
            $.ajax({
                type:"post",
                url:"/question/findMySolvedQuestion",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize
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
        }

    },
    created:function(){
        //created  表示页面加载完毕，立即执行
        this.pageHandler();

    }
});