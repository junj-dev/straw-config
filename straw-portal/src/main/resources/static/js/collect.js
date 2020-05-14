const collectApp=new Vue({
    el:"#collectApp",
    data:{
        page: 0,  //显示的是哪一页
        pageSize: 5, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:6, //最大页数
        questions:[],
        isShow:false
    },
    methods: {

        //查找所有的问题
        pageHandler:function (pageNum) {
            //here you can do custom state update

            this.page = pageNum;
            let pageSize=this.pageSize;
            let _this=this;
            $.ajax({
                type:"post",
                url:"/personal/findCollectQuestionsPage",
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
                        if(_this.total==0){
                            _this.isShow=true;
                        }else {
                            _this.isShow=false;
                        }
                    }
                }
            });
        },
        cancleCollect:function (id) {
            let _this=this;
            $.get("/question/cancelCollect/"+id,function (res) {
                if(res.code==200){
                    _this.pageHandler(_this.page);
                }else {
                    alert(res.msg);
                }
            });
        }

    },
    created:function(){
        //created  表示页面加载完毕，立即执行
        this.pageHandler();
    }
})