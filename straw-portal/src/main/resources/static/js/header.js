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