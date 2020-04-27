var h=new Vue({
    el:"#searchApp",
    data:{
        keyword:''
    } ,
    methods:{
        search:function () {
            window.open("/question/search.html?keyword="+this.keyword);
        }
    }
});

var noticeModal=new Vue({
    el:"#noticeModal",
    data:{
        notices:[]
    },
    methods: {
        loadNotice:function () {
            var _this=this;
            $.get("/notice/list",function (res) {
                if(res.code==200){
                    _this.notices=res.data;
                }
            });
        }
    },
    created:function () {
        this.loadNotice();
    }

});

var noticeNum=new Vue({
    el:"#noticeCount",
    data:{
        count:''
    },
    methods: {
        loadNotice:function () {
            var _this=this;
            $.get("/notice/list",function (res) {
                if(res.code==200){
                    var data=res.data;
                    _this.count=data.length;
                }
            });
        }
    },
    created:function () {
        this.loadNotice();
    }
})

function delHtmlTag(str){
    return str.replace(/<[^>]+>/g,"");
}