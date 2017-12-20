ServerProxy = Class.extend({
    gameServerUrl: "localhost:8091",
    matchMakerJoinUrl: "http://localhost:8080/matchmaker/join",
    matchMakerSignInUrl: "http://localhost:8080/matchmaker/signin",
    matchMakerSignUpUrl: "http://localhost:8080/matchmaker/signup",
    matchMakerSignOutUrl: "http://localhost:8080/matchmaker/signout",
    matchMakerTopUrl: "http://localhost:8080/matchmaker/top",
    matchMakerOnlineListUrl: "http://localhost:8080/matchmaker/onlinelist",
    gameId: "1234",
    currentState: "offline",

    socket: null,

    handler: {},

    init: function () {
        this.handler['REPLICA'] = gMessages.handleReplica;
        this.handler['POSSESS'] = gMessages.handlePossess;
    },

    subscribeEvents: function () {
        var self = this;
        gInputEngine.subscribe('up', function () {
            self.socket.send(gMessages.move('up'))
        });
        gInputEngine.subscribe('down', function () {
            self.socket.send(gMessages.move('down'))
        });
        gInputEngine.subscribe('left', function () {
            self.socket.send(gMessages.move('left'))
        });
        gInputEngine.subscribe('right', function () {
            self.socket.send(gMessages.move('right'))
        });
        gInputEngine.subscribe('bomb', function () {
            self.socket.send(gMessages.plantBomb())
        });
    },

    loginFromMatchMaker: function (login, password) {
        var that = this;
        var msg = "";
        var succ = false;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: "login=" + login + "&password=" + password,
            dataType: 'text',
            success: function (data) {
                msg = data;
                succ = true;
                console.log("Matchmaker sign in");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                msg = jqXHR.responseText;
                console.log(jqXHR.responseText);
            },
            processData: false,
            type: 'POST',
            url: that.matchMakerSignInUrl,
            async: false
        });
        return [succ, msg];
    },

    registryFromMatchMaker: function (login, password) {
        var that = this;
        var msg = "";
        var succ = false;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: "login=" + login + "&password=" + password,
            dataType: 'text',
            success: function (data) {
                msg = data;
                succ = true;
                console.log("Matchmaker registry");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                msg = jqXHR.responseText;
                console.log(jqXHR.responseText);
            },
            processData: false,
            type: 'POST',
            url: that.matchMakerSignUpUrl,
            async: false
        });
        return [succ, msg];
    },

    getSessionIdFromMatchMaker: function (login, password) {
        var login = auth.login;
        var password = auth.password;
        var that = this;
        var msg = "";
        var succ = false;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: "login=" + login + "&password=" + password,
            dataType: 'text',
            success: function (data) {
                that.gameId = data;
                succ = true;
                console.log("Matchmaker returned gameId=" + data);
                that.connectToGameServer(that.gameId, login);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                msg = jqXHR.responseText;
                console.log(jqXHR.responseText);
                console.log(errorThrown);
            },
            processData: false,
            type: 'POST',
            url: that.matchMakerJoinUrl
        });
        return [succ, msg];
    },

    connectToGameServer: function (gameId, login) {
        var self = this;
        this.socket = new WebSocket("ws://" + this.gameServerUrl + "/game/connect?gameId=" + gameId + "&name=" + login);
        this.subscribeEvents();
        this.socket.onopen = function () {
            console.log("Connection established.");
        };

        this.socket.onclose = function (event) {
            if (event.wasClean) {
                console.log('closed');
            } else {
                console.log('alert close');
            }
            console.log('Code: ' + event.code + ' cause: ' + event.reason);
        };

        this.socket.onmessage = function (event) {
            var msg = JSON.parse(event.data);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function (error) {
            console.log("Error " + error.message);
        };
    },

    logout: function (login, password) {
        var that = this;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: "login=" + login + "&password=" + password,
            dataType: 'text',
            success: function (data) {
                console.log("Matchmaker logout");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.responseText);
            },
            type: 'POST',
            url: that.matchMakerSignOutUrl,
            async: false
        });
    },

    TopPlayers: function () {
        var that = this;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            success: function (response) {
                console.log("Matchmaker get Top");
                var players = response.split(", ");
                if (players != "") {
                    for (var i = 0; i < players.length; i++) {
                        var player = players[i].split("=");
                        document.write('<tr><th>' + (i + 1) +
                            '</th><td>' + player[0] +
                            '</td><td>' + player[1] + '</td></tr>');
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.responseText);
            },
            type: 'GET',
            url: that.matchMakerTopUrl,
            async: false
        });
    },

    OnlinePlayers: function () {
        var that = this;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            success: function (response) {
                console.log("Matchmaker get Online players");
                var players = response.split(", ");
                if (players != "") {
                    for (var i = 0; i < players.length; i++) {
                        document.write('<tr><th>' + (i + 1) + '</th><td>' + players[i] + '</td></tr>');
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.responseText);
            },
            type: 'GET',
            url: that.matchMakerOnlineListUrl,
            async: false
        });
    }


});

serverProxy = new ServerProxy();