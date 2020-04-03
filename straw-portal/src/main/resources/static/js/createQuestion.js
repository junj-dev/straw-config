
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

