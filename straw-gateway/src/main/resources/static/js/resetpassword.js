var vm=new Vue({
    el:"#app",
    data:{
        phone:'',
        code:'',
        password:'',
        comfirmPassword:'',
        message:"发送验证码",
        disabled:false ,//是否禁用发送按钮
        aletMsg: '', // 弹出框中的提示语
        displayStsates: 'none'

    },
    methods:{
        //发送验证码
        sendMessage:function () {
            var _this=this;
            var phone=_this.phone;
            //先对phone格式做判断
            var reg = new RegExp(/^1\d{10}$/);
            if(!reg.test(phone)){
                this.alertDia("请输入正确的手机号",1500);
                 return;
            }
            //发送验证码
            $.get("/straw/portal/aliyunMessage/sendResetPasswordCode?phone="+phone, function(result){
                if(result.code==200){
                    _this.alertDia("短信发送成功！",2000);

                   _this.disabled=true;
                   let time=60;
                   var s= setInterval(function() {

                        if (time > 0) {
                            time = time - 1;
                            _this.message="发送（" + time + "s）";
                        } else {

                            _this.message="发送验证码";
                            _this.disabled=false;
                            clearInterval(s);
                        }


                    }, 1000);



                }else{
                    _this.alertDia(result.msg,2000);
                    return;
                }
            });



        },
        //注册账号
        register:function () {
            var _this=this;
            //电话号码
            var phone=_this.phone;
            var reg = new RegExp(/^1\d{10}$/);
            if(!reg.test(phone)){
                this.alertDia("请输入正确的手机号",2000);
                return;
            }
            //验证码
            var code=_this.code;
            var reg2 = new RegExp("^[0-9]{4}$");
            if(!reg2.test(code)){
                this.alertDia("验证码格式不正确！请输入4位的数字的验证码！",2500);
                return;
            }

            //密码
            var password=_this.password;
            //只能输入6-20个字母、数字、下划线
            var patrn=/^(\w){6,20}$/;
            if(!patrn.exec(password)){
                this.alertDia("密码设置不合法！只能输入6-20个字母、数字、下划线",2500);
                return;
            }
            //确认密码
            var comfirmPassword=_this.comfirmPassword;
            if(comfirmPassword!=password){
                this.alertDia("确认密码和密码不一致，请重新设置！",2000);
                return;
            }
            //发送注册请求
            $.ajax({
               "url":"/straw/portal/resetpassword",
               "type":"post",
               "data":{
                   "phone":phone,
                   "code":code,
                   "password":password
               } ,
                "dataType":"json",
                success:function(result){
                   if(result.code==200){
                       _this.alertDia("密码修改成功！正在跳转到登录界面..",3000);
                       setTimeout(function () {
                           location.href="/login.html";
                       },3000);
                   }else{
                       _this.alertDia(result.msg,2000);
                   }
                },
                error:function(){
                  location.reload();
                }
            });


        },
        // 提示弹框
        alertDia (msg,timeout) {
            this.displayStsates = 'block'
            this.aletMsg = msg
            // 延迟2秒后消失 自己可以更改时间
            window.setTimeout(() => {
                this.displayStsates = 'none'
            }, timeout)
        }

    }

});