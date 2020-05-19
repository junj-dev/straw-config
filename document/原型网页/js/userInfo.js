const userInfo=new Vue({
    el:"#app",
    data:{
        nickname: '张三', //用户名
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
            let _this=this;
            $.get("/personal/getUserInfo",function (res) {
                if(res.code==200){
                    console.log("成功！")
                    let data=res.data;
                    _this.nickname=data.nickname;
                    _this.birthdayStr=data.birthdayStr;
                    _this.selfIntroduction=data.selfIntroduction;
                    _this.sex=data.sex;
                }
            })
        },
        onSubmit(evt) {
            evt.preventDefault();
            let _this=this;
            if(_this.nickname==''){
                this.alertDia("用户名不能为空！",2000);
                return;
            }
            let data={
                "nickname":_this.nickname,
                "sex":_this.sex,
                "birthdayStr":_this.birthdayStr,
                "selfIntroduction":_this.selfIntroduction
            };
            $.ajax({
                url:"/personal/resetMyInfo",
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