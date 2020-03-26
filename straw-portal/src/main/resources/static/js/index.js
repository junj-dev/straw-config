// $(document).ready(function(){
// 	if(event.keyCode ===13){
// 		vm.pageHandler(1);
// 	}
// });

var vm = new Vue({
    el: '#app',
    data: {
        page: 0,  //显示的是哪一页
        pageSize: 5, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:9, //最大页数
        keyword:'',
        questions:[],
        tags:[],
        show:false,
        tagId:null



    },
    methods: {
        //pagehandler方法 跳转到page页
        pageHandler: function (pageNum) {
            //here you can do custom state update
            this.page = pageNum;
            var keyword=this.keyword;
            var pageSize=this.pageSize;
            this.show=true;
            var _this=this;

            $.ajax({
                type:"post",
                url:"/question/search",
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
        //根据标签加载问题
        pageHandler2:function (pageNum) {
            //here you can do custom state update

            this.page = pageNum;
            var pageSize=this.pageSize;
            var tagId=this.tagId;
            this.show=false;
            var _this=this;
            $.ajax({
                type:"post",
                url:"/question/findQuestionByTagId",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize,
                    "tagId":tagId
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
        },
        pageHandler3:function (ev,pageNum) {
            //here you can do custom state update
            console.log(ev.target.dataset.id);

            this.page = pageNum;
            var pageSize=this.pageSize;
            var tagId=ev.target.dataset.id;
            this.tagId=tagId;
            this.show=false;
            var _this=this;
            $.ajax({
                type:"post",
                url:"/question/findQuestionByTagId",
                data:{
                    "pageNum":pageNum,
                    "pageSize":pageSize,
                    "tagId":tagId
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
        this.tagId=0;
        this.pageHandler2();
        this.loadTgs();
    }
});
