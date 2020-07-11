const tagVue=new Vue({
    el:"#tagApp",
    data:{
        tags:[]
    },
    methods:{
        //加载标签
        loadTags: function () {
            let _this=this;
            $.get("/tag/findAllTags",function(result){
                if(result.code==200){
                    _this.tags=result.data;
                    console.log("tags:"+result.data);
                }
            });
        }
    },
    created:function () {
        loadTags();
    }
})