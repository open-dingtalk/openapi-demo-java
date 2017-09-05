//dd.config({
//			agentId : _config.agentid,
//			corpId : _config.corpId,
//			timeStamp : _config.timeStamp,
//			nonceStr : _config.nonceStr,
//			signature : _config.signature,
//			jsApiList : [ 'runtime.info', 'biz.contact.choose',
//					'device.notification.confirm', 'device.notification.alert',
//					'device.notification.prompt', 'biz.ding.post',
//					'biz.util.openLink' ]
//		});

$.ajax({
	url : 'contactsinfo?corpid='+ _config.corpId,
	type : 'GET',
	success : function(data, status, xhr) {
		var json = data;
//		alert('data:'+json);
		
		var jj = eval("(" + json + ")");;
		var show ="";
		for(var i=0; i<jj.department.length; i++){
			show +='<div style="text-indent:15px"><h3>'+jj.department[i].name+'</h3>' 
			for(var j=0;j< jj.department[i].member.length;j++){
				show += '<div style="text-indent:25px">'+jj.department[i].member[j].name+'</div>';
			}
			show+='</div>';
			show+='<br >';
		}
//		alert("div:"+show);

		document.getElementById("contactId").innerHTML = show;

	},
	error : function(xhr, errorType, error) {
		logger.e("yinyien:" + _config.corpId);
		alert(errorType + ', ' + error);
	}
});