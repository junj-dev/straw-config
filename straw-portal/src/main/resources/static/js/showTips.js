//tip是提示信息，type:'success'是成功信息，'danger'是失败信息,'info'是普通信息,'warning'是警告信息
function showTip(tip, type) {
    var $tip = $('#tip');
    if ($tip.length == 0) {
        $tip = $('<strong id="tip" style="position:absolute;top:50%;left: 55%;z-index:9999"></strong>');
        $('body').append($tip);
    }
    $tip.stop(true).prop('class', 'alert alert-' + type).text(tip).css('margin-left', -$tip.outerWidth() / 2).fadeIn(500).delay(1000).fadeOut(500);
}

function showMsg(msg) {
    showTip(msg, 'info');
}

function showSuccess(msg) {
    showTip(msg, 'success');
}

function showFailure(msg) {
    showTip(msg, 'danger');
}

function showWarn(msg, $focus, clear) {
	showTip(msg, 'warning');
	if($focus){
		$focus.focus();
		if(clear){
			$focus.val('');
		}
	}
	
}