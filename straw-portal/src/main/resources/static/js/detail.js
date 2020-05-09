(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();
$(function () {
    $('#summernote').summernote({
        height: 400,
        tabsize: 2,
        lang: 'zh-CN',
        callbacks:{
            onImageUpload: function(files, editor, $editable) {
                UploadFiles(files,insertImg);
            }
        },
    });

})
//删除问题
function deleteQuestion(id) {
    var msg = "您真的确定要删除吗？\n\n请确认！";
    if (confirm(msg)){
       $.get("/question/delete/"+id,function (res) {
           if(res.code==200){
               location.href="/";
           }else {
               alert(res.msg);
           }
       });
    }

}

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
var comment=new Vue({
    el:"#comment",
    data:{
        content:''
    },
    methods:{
        onSubmit:function () {
            console.log(this.content);
        },
        onReset:function () {
            this.content='';
        }
    }
});
var collectApp=new Vue({
    el:"#collectApp",
    data:{
        show:false,
        questionId:''
    },
    methods: {
        checkCollectStatus:function () {
            var _this=this;
            //检查收藏状态
            $.get("/question/checkCollectStatus/"+_this.questionId,function (res) {
                if(res.code==200){
                    _this.show=!res.data;
                }
            });
        },
        //收藏
        collectQuestion:function () {
            var _this=this;
            $.get("/question/collect/"+_this.questionId,function (res) {
                if(res.code==200){
                    var oldStatus=_this.show;
                    _this.show=!oldStatus;
                }else {
                    alert(res.msg);
                }
            });
        },
        //取消收藏
        cancelCollectQuestion:function () {
            var _this=this;
            $.get("/question/cancelCollect/"+_this.questionId,function (res) {
                if(res.code==200){
                    var oldStatus=_this.show;
                    _this.show=!oldStatus;
                }else {
                    alert(res.msg);
                }
            });
        }
    },
    created:function () {
        this.questionId=$("#questionId").val();
        this.checkCollectStatus();
    }
})