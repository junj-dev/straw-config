$(function () {
    $('#summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN',
        callbacks:{
            onImageUpload: function(files, editor, $editable) {
                UploadFiles(files,insertImg);
            }
        },
    });
    $('.select2').select2();
})
function insertImg(imgeList){
    //for(i in imageUrl){
    console.log("imgeList:"+imgeList);
    for(var i=0;i<imgeList.length;i++){
        $('#summernote').summernote('insertImage', imgeList[i], 'img');
    }


    //}
}

function UploadFiles(files,func){
    //这里files是因为我设置了可上传多张图片，所以需要依次添加到formData中
    var formData = new FormData();
    for(f in files){
        formData.append("file", files[f]);
    }

    $.ajax({
        data: formData,
        type: "POST",
        url: "/question/uploadMultipleFile",
        cache: false,
        contentType: false,
        processData: false,
        success: function(result) {
            if(result.code=="200"){
                console.log("上传成功");
                func(result.data);
            }else if(result.code=='400'){
                console.log("上传失败!");
                alert(result.message);
            }else {
                alert("服务繁忙，图片上传失败，请稍后重试！");
            }


        },
        error: function() {
            alert("服务繁忙，请稍后重试！");
        }
    })
}

var vm=new Vue( {
    el:"#app",
    data: {
        content:'', //回答的内容
        answerId:'',
        show: true,
        aletMsg: '',//提示消息
        displayStsates: 'none',//是否隐藏,
        questionId:'' //问题id
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
        //加载答案
        getAnswer:function(){
            var _this=this;
            $.get("/answer/"+_this.answerId,function (res) {

               if(res.code==200){
                   _this.questionId=res.data.questId;
                   $('#summernote').summernote('code',res.data.content);
               }

            });
        },
        onSubmit(evt) {
            evt.preventDefault()
            var _this=this;

            //校验问题的内容是否为空
            var content= $('#summernote').summernote('code');
            console.log("content:"+content);
            if(content.length==0||content=='<p><br></p>'){
                _this.alertDia("问题的内容不能为空",2000);
                return;
            }
            $.ajax({
                url:"/answer/update",
                type:"post",
                data:{
                    "answerId":_this.answerId,
                    "content":content
                },
                dataTye:'json',
                success:function (res) {
                    if(res.code==200){
                        _this.alertDia("修改成功!",1300);
                        setTimeout(function () {
                            location.href="/question/detail/"+_this.questionId;
                        },1000)
                    }else {
                        _this.alertDia(res.msg,2000);
                    }
                }
            })




        }
    },
    created:function () {
        this.answerId=$("#answerId").val();
        this.getAnswer();
    }
})