/**
 * Created by liqiao on 10/16/15.
 */

var log = {
    i: function(info) {
        add(info, 'i');
    },
    e: function(err) {
        add(err, 'e');
    }
};

function add(msg, level) {
    var row = document.createElement('div');
    row.setAttribute('class', 'log-row log-' + level);
    row.innerHTML = msg;

    document.querySelector('#log').appendChild(row);
}