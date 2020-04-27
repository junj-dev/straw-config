var vm = new Vue({
    el: '#app',
    data: {
        page: 0,  //显示的是哪一页
        pageSize: 5, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:6, //最大页数
        questions:[],
        tags:[]

    },
    methods: {

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
        //查找所有的问题
        pageHandler:function (pageNum) {
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

                    console.log(res);
                    if(res.code==200){
                        _this.total=res.data.total;
                        _this.pages=res.data.pages;
                        _this.questions=res.data.list;
                    }
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