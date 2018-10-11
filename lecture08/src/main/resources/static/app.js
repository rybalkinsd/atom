var host = 'localhost';
var port = 8090;
// var path = 'chat';
var sock;

function say() {
    var msg = $('#msg').val();
    var name = $('#name').val();
    var msgData = JSON.stringify(
        {
            "topic": "message",
            "data": {
                "user": name,
                "msg": msg
            }
        });
    sock.send(msgData);
}

function loginUser() {
    document.getElementById("login").disabled = true;
    document.getElementById("name").disabled = true;
    document.getElementById("logout").disabled = false;
    document.getElementById("msg").disabled = false;
    document.getElementById("send").disabled = false;
    sock = new SockJS('http://' + host + ':' + port + '/chat');
    sock.onopen = function() {
        console.log('open');
        userLogin();
        // updateHistory();
    };

    sock.onmessage = function (e) {
        console.log(e.data);
        // check topic if you will provide other than "history"
        setHistory(e.data)
    };

    sock.onclose = function() {
        console.log('close');
        // updateHistory();
    };
}

function logoutUser() {
    document.getElementById("login").disabled = false;
    document.getElementById("name").disabled = false;
    document.getElementById("logout").disabled = true;
    document.getElementById("msg").disabled = true;
    document.getElementById("send").disabled = true;
    userLogout();
}

function userLogin() {
    var name = $('#name').val();
    var newUser = JSON.stringify(
        {
            "topic": "login",
            "data": {
                "user": name
            }
        }
    );
    sock.send(newUser)
}

function userLogout() {
    var name = $('#name').val();
    var logout = JSON.stringify(
        {
            "topic": "logout",
            "data": {
                "user": name
            }
        }
    );
    sock.send(logout)
}

function updateHistory() {
    var name = $('#name').val();
    var history = JSON.stringify(
        {
            "topic" : "history",
            "data": {
                "user": name
            }
        }
    );
    sock.send(history)
}

function setHistory(data) {
    $("#history").append(data).append("<br />");
    $("#history").scrollTop($("#history")[0].scrollHeight);
}