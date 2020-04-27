var myinfo=new Vue({
    el:"#myInfoApp" ,
    data:{
        goldCount:0,
        answerCount:0,
        questionCount:0,
        taskCount:0
    },
    methods:{
        loadMyInfo:function () {
            var _this=this;
           $.get("/personal/getMyInfo",function (result) {
               if(result.code==200){
                   var data=result.data;
                _this.goldCount=data.goldCount;
                _this.answerCount=data.answerCount;
                _this.questionCount=data.questionCount;
                _this.taskCount=data.taskCount;
               }
           });
        }
    },
    created:function () {
        this.loadMyInfo();
    }
});