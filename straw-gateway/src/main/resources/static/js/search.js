var vm = new Vue({
    el: '#app',
    data: {
        page: 0,  //显示的是哪一页
        pageSize: 5, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:6, //最大页数
        questions:[],
        tags:[],
        keyword:''

    },
    methods: {
        delHtmlTag(str){
            return str.replace(/<[^>]+>/g,"");
        }
        ,
        //加载标签
        loadTgs: function () {
            var _this=this;
            $.get("/straw/portal/tag/findAllTags",function(result){
                if(result.code==200){
                    _this.tags=result.data;
                    console.log("tags:"+result.data);
                }
            });
        },
        //查找所有的问题
        pageHandler:function (pageNum) {
            //here you can do custom state update
            //here you can do custom state update
            this.page = pageNum;
            var keyword=this.keyword;
            var pageSize=this.pageSize;
            this.show=true;
            var _this=this;

            $.ajax({
                type:"post",
                url:"/straw/portal/question/search",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize,
                    "keyword":keyword
                },
                dataType: "json",
                success:function (res) {

                    console.log(res);
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
        this.loadTgs();
        //给keyword赋值
        this.keyword=$("#keyword").val();
        this.pageHandler();

    }
});
