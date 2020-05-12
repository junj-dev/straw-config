
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
            id:'',
            title: '', //标题内容
            tagNames:[], //所选择的标签
            teacherNames:[], //所选择的老师
            content:'', //问题的内容
            teachers:[], //所有老师
            tags:[], //所有标签
            show: true,
            aletMsg: '',//提示消息
            displayStsates: 'none',//是否隐藏
    },
    computed: {
        //检查标题有没有写
        titleState() {
            return this.title.length >=3 && this.title.length<=50 ? true : false
        },
        availableTags() {
            return this.tags.filter(opt => this.tagNames.indexOf(opt) === -1)
        },
        availableTeachers() {
            return this.teachers.filter(opt => this.teacherNames.indexOf(opt) === -1)
        }
    },
    methods: {
        //根据id查找问题
        loadQuestion:function(){
            var id=$("#questionId").val();
            this.id=id;
            var _this=this;
            $.get("/question/"+id,function (res) {
                if(res.code==200){
                    var data=res.data;
                    _this.title=data.title;
                    _this.tagNames=data.tagNames;
                    _this.teacherNames=data.teacherNames;
                    _this.content=data.content;
                    $('#summernote').summernote('code',data.content);

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
        },
        //加载老师
        loadTeachers:function () {
            var _this=this;
            $.get("/teacher/loadAllTeacherNames",function (result) {
                if(result.code==200){
                    _this.teachers=result.data;
                }else {
                    console.log("加载老师失败!");
                }
            });
        },
        //加载标签
        loadTgs: function () {
            var _this=this;
            $.get("/tag/findAllTagNames",function(result){
                if(result.code==200){
                    _this.tags=result.data;
                    console.log("tags:"+result.data);
                }
            });
        },
        onSubmit(evt) {
            evt.preventDefault()
            var _this=this;
            //校验标题内容
            if(!_this.titleState){
               _this.alertDia("标题字数请控制在3到50字之间",2000);
               return;
            }
            //校验所选标签
            if(_this.tagNames.length==0){
                _this.alertDia("请至少选择一个标签!",2000);
                return;
            }
            //校验所选老师
            if(_this.teacherNames.length==0){
                _this.alertDia("请至少选择一位老师!",2000);
                return;
            }
            //校验问题的内容是否为空
           var content= $('#summernote').summernote('code');
            console.log("content:"+content);
            if(content.length==0||content=='<p><br></p>'){
                _this.alertDia("问题的内容不能为空",2000);
                return;
            }
            var data={
                "id":_this.id,
                "title":_this.title,
                "content":content,
                "tagNames":_this.tagNames,
                "teacherNames": _this.teacherNames
            };
            $.ajax({
                url:"/question/edit",
                type:"post",
                data:JSON.stringify(data),
                dataTye:'json',
                contentType:"application/json",
                success:function (res) {
                    if(res.code==200){
                        _this.alertDia("提交成功!",1300);
                        setTimeout(function () {
                            location.href="/";
                        },1000)
                    }else {
                        _this.alertDia(res.msg,2000);
                    }
                }
            })




        }
    },
    created:function () {
        this.loadTgs();
        this.loadTeachers();
        this.loadQuestion();
    }
})

