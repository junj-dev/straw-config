var userInfo=new Vue({
    el:"#app",
    data:{
        nickname: '', //用户名
        birthdayStr: '', //生日
        selfIntroduction: '', //自我介绍
        sex: '', //性别
        show: true,
        displayStsates:'none',
        aletMsg:''
    },
    methods: {
        // 提示弹框
        alertDia (msg,timeout) {
            this.displayStsates = 'block'
            this.aletMsg = msg
            // 延迟2秒后消失 自己可以更改时间
            window.setTimeout(() => {
                this.displayStsates = 'none'
            }, timeout)
        },
        //加载用户信息
        loadMyInfo:function(){
            var _this=this;
            $.get("/straw/portal/personal/getUserInfo",function (res) {
                if(res.code==200){
                    console.log("成功！")
                    var data=res.data;
                    _this.nickname=data.nickname;
                    _this.birthdayStr=data.birthdayStr;
                    _this.selfIntroduction=data.selfIntroduction;
                    _this.sex=data.sex;
                }
            })
        },
        onSubmit(evt) {
            evt.preventDefault();
            var _this=this;
            if(_this.nickname==''){
                this.alertDia("用户名不能为空！",2000);
                return;
            }
            var data={
                "nickname":_this.nickname,
                "sex":_this.sex,
                "birthdayStr":_this.birthdayStr,
                "selfIntroduction":_this.selfIntroduction
            };
            $.ajax({
                url:"/straw/portal/personal/resetMyInfo",
                type:"post",
                data:JSON.stringify(data),
                dataType:"json",
                contentType:"application/json",
                success:function (res) {
                    if(res.code==200){
                        _this.alertDia("修改成功！",1500);

                    }else {
                        _this.alertDia(res.msg,1500);
                    }
                }
            })



        }
    },
    created:function () {
        this.loadMyInfo();
    },
    computed: {
        nameState() {
            return this.nickname.length >= 2&&this.nickname.length<=30 ? true : false
        }
    }
})