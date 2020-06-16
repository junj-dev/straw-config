const myinfo=new Vue({
    el:"#myInfoApp" ,
    data:{
        goldCount:0,
        answerCount:0,
        questionCount:0,
        taskCount:0,
        collectCount:0
    },
    methods:{
        loadMyInfo:function () {
            let _this=this;
           $.get("/personal/getMyInfo",function (result) {
               if(result.code==200){
                   let data=result.data;
                _this.goldCount=data.goldCount;
                _this.answerCount=data.answerCount;
                _this.questionCount=data.questionCount;
                _this.taskCount=data.taskCount;
                _this.collectCount=data.collectCount;
               }
           });
        }
    },
    created:function () {
        this.loadMyInfo();
    }
});