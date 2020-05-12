
var vm = new Vue({
    el: '#allNotice',
    data: {
        page: 0,  //显示的是哪一页
        pageSize: 10, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:6, //最大页数
        notices:[],

    },
    methods: {

        pageHandler:function (pageNum) {
            this.page = pageNum;
            var pageSize=this.pageSize;
            var _this=this;
            $.ajax({
                type:"post",
                url:"/notice/getAll",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize
                },
                dataType: "json",
                success:function (res) {
                    console.log(res);
                    if(res.code==200){
                        _this.notices=res.data.list;
                        _this.total=res.data.total;
                        _this.pages=res.data.pages;
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
