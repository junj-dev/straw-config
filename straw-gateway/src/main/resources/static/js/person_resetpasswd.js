var resetpassword=new Vue({
    el:"#app",
    data:{
        oldPasswd: '', //旧密码
        newPasswd: '', //新密码
        comfirmPasswd:'',//确认密码
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
        onSubmit(evt) {
            evt.preventDefault();
            var _this=this;
            //判断原密码是否格式正确
            //只能输入6-20个字母、数字、下划线
            var patrn=/^(\w){6,20}$/;
            if(!patrn.exec(_this.oldPasswd)){
                this.alertDia("原密码设置不合法！只能输入6-20个字母、数字、下划线",2000);
                return;
            }
            //新密码是否合法
            if(!patrn.exec(_this.newPasswd)){
                this.alertDia("新密码设置不合法！只能输入6-20个字母、数字、下划线",2000);
                return;
            }
            //确认密码是否正确
            if(_this.comfirmPasswd!=this.newPasswd){
                this.alertDia("确认密码和新密码不一致！",2000);
                return;
            }

            $.ajax({
                url:"/straw/portal/personal/resetpasswd",
                type:"post",
                data:{
                    "oldpasswd":_this.oldPasswd,
                    "newpasswd":_this.newPasswd
                },
                dataTye: "json",
                success:function (res) {
                    if(res.code==200){
                        _this.alertDia("修改成功！",2000);
                    }else {
                        _this.alertDia(res.msg,2000);
                    }
                }
            })






        }
    }

})