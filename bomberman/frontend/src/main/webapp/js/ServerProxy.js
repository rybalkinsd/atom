ServerProxy = Class.extend({
    gameServerUrl: "localhost:8090",
    matchMakerUrl: "http://localhost:8080",
    gameId: "1234",

    socket: null,

    handler: {},

    init: function () {
        this.handler['REPLICA'] = gMessages.handleReplica;
        this.handler['POSSESS'] = gMessages.handlePossess;
    },

    getSessionIdFromMatchMaker: function () {
        var that = this;
        var name = $('#name').serialize();
        console.log(name);
        if(!name){
            alert("Please input login");
            console.log("Empty login, retry login");
        }
        var settings = {
            "method": "POST",
            "crossDomain": true,
            "url": this.matchMakerUrl + "/matchmaker/join",
            "data": name
        }
        $.ajax(settings).done(function(data){
            this.gameId=data;
            console.log("Matchmaker returned gameId=" + data);
            that.connectToGameServer(this.gameId, name);
        }).fail(function(){
            alert("Matchmaker request failed, use default gameId=" + this.gameId);
            console.log("Matchmaker request failed, use default gameId=" + this.gameId);
            that.connectToGameServer(this.gameId, name);
        });
    },

    subscribeEvents: function() {
        var self = this;
        gInputEngine.subscribe('up', function () {
            console.log(gMessages.move('up'));
            self.socket.send(gMessages.move('up'));
        });
        gInputEngine.subscribe('down', function () {
            console.log(gMessages.move('down'));
            self.socket.send(gMessages.move('down'));
        });
        gInputEngine.subscribe('left', function () {
            console.log(gMessages.move('left'));
            self.socket.send(gMessages.move('left'));
        });
        gInputEngine.subscribe('right', function () {
            console.log(gMessages.move('right'));
            self.socket.send(gMessages.move('right'));
        });
        gInputEngine.subscribe('bomb', function () {
            console.log(gMessages.plantBomb());
            self.socket.send(gMessages.plantBomb());
        });
    },

    connectToGameServer : function(gameId, login) {
        var self = this;
        self.socket = new WebSocket("ws://" + this.gameServerUrl + "/events/connect?gameId=" + gameId + "&" + login);
        gGameEngine.menu.hide();

        gGameEngine.playing = true;
        gGameEngine.restart();

        self.subscribeEvents();

        self.socket.onopen = function () {
            console.log("Connection established.");
        };

        self.socket.onclose = function (event) {
            if (event.wasClean) {
                console.log('closed');
            } else {
                console.log('alert close');
            }
            console.log('Code: ' + event.code + ' cause: ' + event.reason);
        };

        self.socket.onmessage = function (event) {
            var msg = JSON.parse(event.data);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function (error) {
            console.log("Error " + error.message);
        };
    }

});