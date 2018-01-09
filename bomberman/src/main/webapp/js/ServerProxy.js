ServerProxy = Class.extend({
    gameServerUrl: "localhost:8090",
    matchMakerUrl: "http://localhost:8085/matchmaker/join",
    gameId: "666",

    socket: null,

    handler: {},

    init: function () {
        this.handler['REPLICA'] = gMessages.handleReplica;
        this.handler['POSSESS'] = gMessages.handlePossess;
        this.handler['GAMESTART'] = gMessages.handleGameStart();

    },

    getSessionIdFromMatchMaker: function () {
        var that = this;
        var login = $("#loginInput").val();
        if(!login){
            alert("Please input login");
            console.log("Empty login, retry login");
            gGameEngine.menu.show();
        }
        $.ajax({
            type: 'POST',
            url: that.matchMakerUrl,
            contentType: 'application/x-www-form-urlencoded',
            dataType: 'text',
            //processData: true,
            data: {
                "name": login
            },
            success: function(data){
                that.gameId=data;
                console.log("Matchmaker returned gameId= " + data);
                /*waiting for connections*/
                that.connectToGameServer(that.gameId, login);
                gGameEngine.menu.showConnectionsWaiting();

            },
            error: function(data){
                alert(data.responseText);
                console.log("Matchmaker request failed");
                gGameEngine.menu.show();
            }
        });
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


    connectToGameServer: function (gameId, login) {
        var self = this;
        self.socket = new WebSocket("ws://" + this.gameServerUrl + "/game/connect?gameId=" + gameId + "&name=" + login);

        self.subscribeEvents();
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
            if (!gGameEngine.menu.visible) {
                alert("Closed");
                gGameEngine.menu.show();
            }
        };

        this.socket.onmessage = function (event) {
            console.log(event.data);
            var msg = JSON.parse(event.data);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function (error) {
            alert("Something went wrong on GameServer");
            gGameEngine.menu.show();
            console.log("Error " + error.message);
        };
    }

});
