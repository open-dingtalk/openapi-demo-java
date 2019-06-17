DingTalkPC.config({
    agentId : _config.agentId,
    corpId : _config.corpId,
    timeStamp : _config.timeStamp,
    nonceStr : _config.nonceStr,
    signature : _config.signature,
    jsApiList : [ 'runtime.info', 'biz.contact.choose',
        'device.notification.confirm', 'device.notification.alert',
        'device.notification.prompt', 'biz.ding.post',
        'biz.util.openLink' ]
});


DingTalkPC.ready(function() {
    DingTalkPC.biz.navigation.setTitle({
        title: '钉钉demo',
        onSuccess: function(data) {
        },
        onFail: function(err) {
            log.e(JSON.stringify(err));
        }
    });

    DingTalkPC.runtime.info({
        onSuccess : function(info) {
            logger.e('runtime info: ' + JSON.stringify(info));
        },
        onFail : function(err) {
            logger.e('fail: ' + JSON.stringify(err));
        }
    });
    DingTalkPC.ui.pullToRefresh.enable({
        onSuccess: function() {
        },
        onFail: function() {
        }
    })


    DingTalkPC.runtime.permission.requestAuthCode({
        corpId : _config.corpId,
        onSuccess : function(info) {
//			alert('authcode: ' + info.code);
            $.ajax({
                url : 'userinfo?code=' + info.code + '&corpid='
                + _config.corpId,
                type : 'GET',
                success : function(data, status, xhr) {
                    alert(data);
                    var info = JSON.parse(data);

                    document.getElementById("userName").innerHTML = info.name;
                    document.getElementById("userId").innerHTML = info.userid;

                    // 图片
                    if(info.avatar.length != 0){
                        var img = document.getElementById("userImg");
                        img.src = info.avatar;
                        img.height = '100';
                        img.width = '100';
                    }

                },
                error : function(xhr, errorType, error) {
                    logger.e("yinyien:" + _config.corpId);
                    alert(errorType + ', ' + error);
                }
            });

        },
        onFail : function(err) {
            alert('fail: ' + JSON.stringify(err));
        }
    });
});

DingTalkPC.error(function(err) {
    alert('dd error: ' + JSON.stringify(err));
});
