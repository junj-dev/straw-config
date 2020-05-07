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

var noticeCount=new Vue({
    el:"#noticeCount",
    data:{
        count:''
    },
    methods: {
        countNotice:function () {
            var _this=this;
            $.get("/notice/count",function (res) {
                if(res.code==200){
                    _this.count=res.data;
                }
            });
        }
    },
    created:function () {
        this.countNotice();
    }
})

function delHtmlTag(str){
    return str.replace(/<[^>]+>/g,"");
}
//邀请码
var invitecodeModalVM=new Vue({
    el:"#invitecodeModal",
    data:{
        classrooms:[]
    },
    methods:{
        //加载邀请码
        loadInvitecode:function () {
            var _this=this;
            $.get("/classroom/list",function (res) {
                if(res.code==200){
                    _this.classrooms=res.data;
                }
            });
        }
    },
    created:function () {
        this.loadInvitecode();
    }
})