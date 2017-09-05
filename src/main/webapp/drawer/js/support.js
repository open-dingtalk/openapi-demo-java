/**
 * Created by liqiao on 1/6/16.
 */
(function() {

  //constants
  var MSG_TYPE_REQ = 1;
  var MSG_TYPE_RES = 2;
  var MSG_TYPE_BACK = 3;

  var STATUS_LOAD_START = 0;
  var STATUS_LOAD_FINISH = 1;
  var STATUS_LOAD_ERROR = -1;

  //req callbacks
  var ReqIdGen = 0;
  var callbacks = {};

  //global error handler
  var preloadingIds = {};

  var processingGo = false;

  var isFirstRun = true;

  var drawerId;

  var frameId;//ios compat
  var preloadUrls = {}; //ios compat

  var fromId;

  var logger = {
    LEVEL: {
      INFO: 0,
      WARNING: 1
    },
    level: 1,
    i: function(msg) {
      console.log(msg);
    },
    w: function(msg) {
      if (this.level > this.LEVEL.INFO) {
        alert(msg);
      }
      else {
        this.i(msg);
      }
    }
  };

  if (!window.onerror) {
    window.onerror = function (msg, url, line, column, errObj) {
      logger.w('Oops!\n' + msg + '\nhappened at ' + url + ' [line ' + line + ', column ' + column + ']')
    };
  }


  document.addEventListener('runtimeready', function() {
    if (dd) dd.on = document.addEventListener;
  });

  var errorHandler =  function(err) {
    logger.w('[nav error] ' + JSON.stringify(err));
  };

  var invokeHandler = function(data, cb) {
    logger.i('[nav invoke] ' + JSON.stringify(data));
  }

  var initNav = function(p) {
    checkEnv();

    if (!p) throw 'parameter for init() is missing';
    if (!p.id) throw 'id is missing';

    if (p.id) frameId = p.id;
    if (p.onError && 'function' === p.onError) errorHandler = p.onError;
    if (p.onInvoke && 'function' === p.onInvoke) invokeHandler = p.onInvoke;

    var handleMessages = function(data) {
      if (data && data.length > 0) {
        var once = true;
        data.forEach(function (message) {
          if (message.from) {
            fromId = message.from;
          }
          if (message.content) {
            var msgType = message.content._type;
            if (!once && (msgType === MSG_TYPE_REQ || msgType === MSG_TYPE_RES)) {
              logger.w('Warning: more than one REQ or RES message are post to this frame: ' +
                  JSON.stringify(message.content));
              return;
            }
            if (MSG_TYPE_REQ === msgType) {
              once = false;
              var reqId = message.content._reqId;
              var handle = invokeHandler;
              var _h = message.content._handler;
              if (_h && 'string' === typeof _h && window[_h]) handle = window[_h];

              handle(message.content._value, function (response) {
                var msgContent = {
                  _type: MSG_TYPE_RES,
                  _reqId: reqId,
                  _value: response
                };
                postMessage({
                  id: message.from,
                  content: msgContent
                });
              });
            }
            else if (MSG_TYPE_RES === msgType) {
              once = false;
              var reqId = message.content._reqId;
              if (reqId !== undefined && callbacks[reqId]) {
                var callback = callbacks[reqId];
                delete callbacks[reqId];
                if (typeof callback === 'function') {
                  callback(message.content._value);
                }
              }
            }
            else if (MSG_TYPE_BACK === msgType) {
              var targetId = message.content._target;
              if (frameId !== targetId) {
                dd.support.nav.close();
              }
            }
          }
        });
      }
    };

    var resumeHandler = function(event) {
      if (shouldDowngrade()) {
        var data = compatMessageFetch(frameId);
        handleMessages(data);
      }
      else {
        dd.runtime.message.fetch({
          onSuccess: function (data) {
            handleMessages(data);
          },
          onFail: function (err) {
            console.log('resume handler fetching messages: ' +
                JSON.stringify(err));
          }
        });
      }
    };

    /*for first run*/
    setTimeout(resumeHandler, 0);
    //resumeHandler();

    dd.on('resume', resumeHandler);
  };

  var preload = function(p) {
    checkEnv();
    if (!p) throw 'parameter for preload() is missing';
    if (!p.pages) throw 'pages is missing';
    if (!(p.pages instanceof Array)) throw 'invalid pages: not an array';
    var ids = [];
    p.pages.forEach(function(page) {
      if (page.id && 'string' === typeof page.id) ids.push(page.id);
    });

    ids.forEach(function(id) {
      var cnt = preloadingIds[p.id];
      preloadingIds[id] = cnt !== undefined ? ++cnt : 0;
      if (cnt > 1) {
        logger.w('Warning: preload id[' + id + '] too frequently');
      }
    });

    var flag = true;

    if (shouldDowngrade()) { //ios compat

      p.pages.forEach(function(page) {
        if ((page.id && 'string' === typeof page.id) &&
            (page.url && 'string' === typeof page.url)) {
          preloadUrls[page.id] = page.url;
        }
      });

      if (p.onSuccess) {
        ids.forEach(function(id) {
          p.onSuccess({
            id: id,
            status: STATUS_LOAD_START,
            extras: {}
          });
          p.onSuccess({
            id: id,
            status: STATUS_LOAD_FINISH,
            extras: {}
          });
        });
      }
      return;
    }

    dd.ui.nav.preload({
      pages: p.pages,
      onSuccess: function(data) {
        if (flag && data && data.id && data.status === STATUS_LOAD_START) {
          flag = false;
          var cnt = preloadingIds[p.id];
          if (cnt > 0) preloadingIds[p.id] = cnt - 1;
        }
        if (p.onSuccess) p.onSuccess(data);
      },
      onFail: function(err) {
        ids.forEach(function(id) {
          var cnt = preloadingIds[p.id];
          if (cnt > 0) preloadingIds[p.id] = cnt - 1;
        });
        if (p.onFail) p.onFail(err);
      }
    });
  };

  var go = function(p) {
    checkEnv();

    if (processingGo) {
      throw 'calling go() for id['+ p.id +'] at ' + location.href + ' too frequently';
    }

    if (!p) throw 'parameter for go() is missing';
    if (!p.id) throw 'id is missing';
    if (p.handler && typeof p.handler !== 'string') throw 'handler is supposed to be a string';
    var id = p.id;

    processingGo = true;

    var params = p.params || {};
    var handler = p.handler;
    var win = p.onSuccess;
    var fail = function(err) {
      processingGo = false;
      var f = p.onFail ? p.onFail : errorHandler;
      f(err);
    };

    var reqId = ReqIdGen++;
    if (callbacks[reqId]) throw 'fatal: reqId[' + reqId + '] is existed';
    callbacks[reqId] = win;

    var args = {
      id: id,
      onSuccess: function() {
        processingGo = false;
      },
      onFail: fail
    };
    if (p.anim !== undefined) args.anim = p.anim;

    var msgContent = {
      _type: MSG_TYPE_REQ,
      _reqId: reqId,
      _handler: handler,
      _value: params
    };

    if (shouldDowngrade()) { //ios compat
      var url = preloadUrls[id];
      if (p.createIfNeeded) {
        if (!url && (!p.url || typeof p.url !== 'string')) {
          processingGo = false;
          throw 'url of go() is missing or invalid';
        }

        url = p.url;
      }
      if (!url) {
        processingGo = false;
        throw 'missing url for go()';
      }
      preloadUrls[id] = url;

      postMessage({
        id: id,
        content: msgContent
      });
      setTimeout(function() {
        processingGo = false;
        dd.biz.util.openLink({
          url: url
        });
      }, 0);
    }
    else {
      var postMessageAndGo = function () {
        postMessage({
          id: id,
          content: msgContent,
          onSuccess: function () {
            dd.ui.nav.go(args);
          },
          onFail: fail
        });
      };

      if (p.createIfNeeded) {
        if (!p.url || typeof p.url !== 'string') {
          processingGo = false;
          throw 'url of go() is missing or invalid';
        }

        var flag = true;
        var timeout = true;

        preload({
          pages: [{
            id: id,
            url: p.url
          }],
          onSuccess: function (data) {
            if (flag && data && data.id === id && data.status === STATUS_LOAD_START) {
              flag = false;
              // /*black magic*/
              // setTimeout(postMessageAndGo, 0);
              timeout = false;
            }
          },
          onFail: fail
        });

        setTimeout(function () {
          if (timeout) {
            logger.w('preload too long: ' + id);
          }
          postMessageAndGo();
        }, 300);
      }
      else {
        postMessageAndGo();
      }
    }
  };

  var backTo = function(p) {

  };

  /**
   * @param p
   * {
   *  id: 'id',
   *  params: {},
   *  handler: 'fn',
   *  onSuccess: function(res) {},
   *  onFail: function(err) {}
   * }
   */
  var call = function(p) {
    checkEnv();

    if (!p) throw 'parameter for call() is missing';
    if (!p.id) throw 'id is missing';
    if (p.handler && typeof p.handler !== 'string') throw 'handler is supposed to be a string';

    var id = p.id;
    var params = p.params || {};
    var handler = p.handler;
    var win = p.onSuccess;
    var fail = function(err) {
      var f = p.onFail ? p.onFail : errorHandler;
      f(err);
    };

    var reqId = ReqIdGen++;
    if (callbacks[reqId]) throw 'fatal: reqId[' + reqId + '] is existed';
    callbacks[reqId] = win;

    var msgContent = {
      _type: MSG_TYPE_REQ,
      _reqId: reqId,
      _handler: handler,
      _value: params
    };

    postMessage({
      id: id,
      content: msgContent,
      onSuccess: function () {
        logger.i('call id[' + id + '] with params [' + JSON.stringify(params) + ']');
      },
      onFail: fail
    });
  };


  var initDrawer = function(p) {
    if (!p) throw 'parameter for init() is missing';
    if (!p.id) throw 'id is missing';

    drawerId = p.id;
    dd.ui.drawer.init(p);
  };


  /**
   * @param p
   * {
   *  params: {},
   *  handler: 'fn',
   *  onSuccess: function(res) {},
   *  onFail: function(err) {}
   * }
   */
  var openDrawer = function(p) {
    checkEnv();

    if (!drawerId) throw 'drawer id is undefined. Has drawer been initialized?';
    if (!p) p = {};
    p.id = drawerId;

    call(p);

    dd.ui.drawer.open({
      onSuccess: function() {},
      onFail: p.onFail ? p.onFail : errorHandler
    });
  };


  function checkEnv() {
    if (!window.nuva) throw 'dd is not ready';
    if (!dd.ios && !dd.android) throw 'env is not dd.android nor dd.ios';
  }


  function postMessage(p) {
    if (!p) throw 'missing paramete for postMessage()';
    if (!p.id) throw 'missing id for postMessage()';

    var id = p.id;
    var content = p.content;

    if (shouldDowngrade()) {
      compatMessagePost(id, content);
    }
    else {
      dd.runtime.message.post({
        to: [id],
        content: content,
        onSuccess: p.onSuccess || function () {},
        onFail: p.onFail || errorHandler
      });
    }
  }


  var STORAGE_MESSAGE_KEY = '__message__5';

  function compatMessagePost(id, content) {
    if (!window.localStorage) alert('localStorage is not supported');
    if (!id) throw 'post message with an undefined id';
    if (!frameId) throw 'thie frame is not initialized with a frame id';

    var m = localStorage[STORAGE_MESSAGE_KEY];
    if (!m) m = JSON.stringify({});

    var messages = JSON.parse(m);

    var msgList = messages[id];
    if (!msgList) msgList = [];

    msgList.push({from: frameId, content: content});
    messages[id] = msgList;

    localStorage[STORAGE_MESSAGE_KEY] = JSON.stringify(messages);

  }

  function compatMessageFetch(id) {
    if (!window.localStorage) alert('localStorage is not supported');
    if (!id) throw 'fetch message with an undefined id';

    var m = localStorage[STORAGE_MESSAGE_KEY];
    if (!m) m = JSON.stringify({});

    var messages = JSON.parse(m);
    var msgList = messages[id];

    messages[id] = [];
    localStorage[STORAGE_MESSAGE_KEY] = JSON.stringify(messages);
    return msgList || [];
  }

  /**
   * compareVersion: function(oldVersion, newVersion, containEqual)
   *
   * oldVersion 老版本
   * newVersion 新版本
   * containEqual 是否包含相等的情况
   * result: newVersion >[=] oldVersion
   **/
  function shouldDowngrade() {
    return dd.ios && dd.compareVersion(dd.version, '2.7.0', true);
  }


  if (dd.support) throw 'dd.support is already defined';

  dd.support = {};

  dd.support.logger = logger;

  dd.support.message = {
    setKey: function(key) {
      if (shouldDowngrade()) {
        STORAGE_MESSAGE_KEY = key;
      }
    },
    clear: function() {
      if (shouldDowngrade() && window.localStorage) {
        localStorage[STORAGE_MESSAGE_KEY] = JSON.stringify({});
      }
    }
  };

  dd.support.call = call;

  dd.support.nav = {
    init: initNav,
    preload: preload,
    go: go,
    recycle: dd.ui.nav.recycle,
    close: dd.ui.nav.close,
    backTo: backTo,
    getCurrentId: dd.ui.nav.getCurrentId
  };

  dd.support.drawer = {
    init: initDrawer,
    config: dd.ui.drawer.config,
    enable: dd.ui.drawer.enable,
    disable: dd.ui.drawer.disable,
    open: openDrawer,
    close: dd.ui.drawer.close,
  };
}) ();