
var vm = new Vue({
    el: '#app1',
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
        //把问题设置为已解决
        setQuestionSolved:function (e) {
            var id=e.target.dataset.id;
            console.log("id:"+id);
            var _this=this;
            $.get("/question/setQuestionSolved/"+id, function(result){
                if(result.code=="200"){
                    console.log("成功设置提问为公开！");
                    _this.pageHandler(1);
                    vm2.pageHandler(1);
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